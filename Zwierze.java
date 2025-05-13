public abstract class Zwierze extends Organizm {

    public Zwierze(Swiat swiat, int sila, int inicjatywa, int x, int y, String nazwa) {
        super(swiat, sila, inicjatywa, x, y, nazwa);
    }

    @Override
    public void akcja() {
        wiekInkrementacja();

        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};

        int[] kierunki = {0, 1, 2, 3};

        // Tasowanie kierunków
        for (int i = 3; i > 0; --i) {
            int j = (int) (Math.random() * (i + 1));
            int temp = kierunki[i];
            kierunki[i] = kierunki[j];
            kierunki[j] = temp;
        }

        Swiat swiat = getSwiat();

        for (int i = 0; i < 4; ++i) {
            int k = kierunki[i];

            int newX = getX() + dx[k];
            int newY = getY() + dy[k];

            int oldX = getX();
            int oldY = getY();

            if (newX >= 0 && newX < swiat.getSzerokosc() && newY >= 0 && newY < swiat.getWysokosc()) {
                Organizm cel = swiat.getOrganizmNaPolu(newX, newY);
                if (cel == null) {
                    swiat.dodajLog(getNazwa() + " przeszedł na pole (" + newX + "," + newY + ")");
                    setPozycja(newX, newY);
                    return;
                } else {
                    swiat.dodajLog(getNazwa() + " próbuje wejść na pole zajmowane przez " + cel.getNazwa() + " na polu (" + newX + "," + newY + ")");
                    kolizja(cel, oldX, oldY);
                    return;
                }
            }
        }
    }

    @Override
    public void kolizja(Organizm inny, int oldX, int oldY) {
        Swiat swiat = getSwiat();

        // Jeśli kolizja jest z rośliną, przekaz jej kontrolę nad kolizją
        if (inny instanceof Roslina) {
            inny.kolizja(this, oldX, oldY);
            return;
        }

        // Jeśli to ten sam gatunek, próbujemy rozmnożenia
        if (this.getClass() == inny.getClass()) {
            int[] dx = {0, 1, 0, -1};
            int[] dy = {-1, 0, 1, 0};

            for (int i = 0; i < 4; i++) {
                int newX = getX() + dx[i];
                int newY = getY() + dy[i];
                if (newX >= 0 && newX < swiat.getSzerokosc() && newY >= 0 && newY < swiat.getWysokosc() &&
                        swiat.getOrganizmNaPolu(newX, newY) == null) {

                    Organizm potomek = this.klonuj(swiat, newX, newY);
                    swiat.dodajOrganizm(potomek);
                    swiat.dodajLog(getNazwa() + " rozmnożył się na polu (" + newX + "," + newY + ")");
                    return;
                }
            }
            return;
        }

        // Próba ataku
        if (inny.czyOdbilAtak(this)) {
            swiat.dodajLog(getNazwa() + " został odepchnięty z ataku przez " + inny.getNazwa());
            setPozycja(oldX, oldY);
            return;
        }

        swiat.dodajLog(getNazwa() + " atakuje " + inny.getNazwa());

        // Zwykła walka
        if (this.getSila() >= inny.getSila()) {
            swiat.dodajLog(getNazwa() + " zabija " + inny.getNazwa());
            inny.zabij();
            setPozycja(inny.getX(), inny.getY());
        } else {
            swiat.dodajLog(getNazwa() + " zostaje zabity przez " + inny.getNazwa());
            this.zabij();
        }
    }
}