public class Antylopa extends Zwierze {

    public Antylopa(Swiat swiat, int x, int y) {
        super(swiat, 4, 4, x, y, "Antylopa"); // Antylopa ma siłę 4 i inicjatywę 4
    }

    @Override
    public char rysowanie() {
        return 'A';
    }

    @Override
    public Organizm klonuj(Swiat swiat, int x, int y) {
        return new Antylopa(swiat, x, y);
    }

    @Override
    public void akcja() {
        wiekInkrementacja();
        int[] dx = {0, 2, 0, -2};
        int[] dy = {-2, 0, 2, 0};
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

            if (newX >= 0 && newX < swiat.getSzerokosc() && newY >= 0 && newY < swiat.getWysokosc()) {
                Organizm cel = swiat.getOrganizmNaPolu(newX, newY);
                if (cel == null) {
                    swiat.dodajLog(getNazwa() + " przeszedł na pole (" + newX + "," + newY + ")");
                    setPozycja(newX, newY);
                    return;
                } else {
                    swiat.dodajLog(getNazwa() + " próbuje wejść na pole zajmowane przez " + cel.getNazwa() + " na polu (" + newX + "," + newY + ")");
                    kolizja(cel, getX(), getY());
                    return;
                }
            }
        }
    }

    @Override
    public void kolizja(Organizm inny, int oldX, int oldY) {
        if (Math.random() < 0.5) { // 50% szans na ucieczkę
            int[] dx = {0, 1, 0, -1};
            int[] dy = {-1, 0, 1, 0};
            for (int i = 0; i < 4; i++) {
                int newX = getX() + dx[i];
                int newY = getY() + dy[i];
                Swiat swiat = getSwiat();
                if (newX >= 0 && newX < swiat.getSzerokosc() && newY >= 0 && newY < swiat.getWysokosc() && swiat.getOrganizmNaPolu(newX, newY) == null) {
                    swiat.dodajLog(getNazwa() + " uciekła przed atakiem na pole (" + newX + "," + newY + ")");
                    setPozycja(newX, newY);
                    return;
                }
            }
        }
        super.kolizja(inny, oldX, oldY);
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        if (Math.random() < 0.5) { // 50% szans na ucieczkę
            int[] dx = {0, 1, 0, -1};
            int[] dy = {-1, 0, 1, 0};
            for (int i = 0; i < 4; i++) {
                int newX = getX() + dx[i];
                int newY = getY() + dy[i];
                Swiat swiat = getSwiat();
                if (newX >= 0 && newX < swiat.getSzerokosc() && newY >= 0 && newY < swiat.getWysokosc() && swiat.getOrganizmNaPolu(newX, newY) == null) {
                    setPozycja(newX, newY);
                    swiat.dodajLog(getNazwa() + " uciekła przed atakiem na pole (" + newX + "," + newY + ")");
                    return true;
                }
            }
        }
        return false;
    }
}