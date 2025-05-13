public class Lis extends Zwierze {

    public Lis(Swiat swiat, int x, int y) {
        super(swiat, 3, 7, x, y, "Lis"); // Lis ma siłę 3 i inicjatywę 7
    }

    @Override
    public char rysowanie() {
        return 'L';
    }

    @Override
    public Organizm klonuj(Swiat swiat, int x, int y) {
        return new Lis(swiat, x, y);
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        return false;
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

        for (int i = 0; i < 4; i++) {
            int k = kierunki[i];
            int newX = getX() + dx[k];
            int newY = getY() + dy[k];

            if (newX >= 0 && newX < swiat.getSzerokosc() && newY >= 0 && newY < swiat.getWysokosc()) {
                Organizm cel = swiat.getOrganizmNaPolu(newX, newY);

                if (cel == null) {
                    swiat.dodajLog("Lis przeszedł na pole (" + newX + "," + newY + ")");
                    setPozycja(newX, newY);
                    return;
                } else if (cel.getSila() <= getSila()) {
                    swiat.dodajLog("Lis ruszył na pole (" + newX + "," + newY + ") gdzie stoi " + cel.getNazwa());
                    kolizja(cel, getX(), getY());
                    return;
                } else {
                    swiat.dodajLog("Lis unika silniejszego organizmu " + cel.getNazwa() + " na (" + newX + "," + newY + ")");
                }
            }
        }

        swiat.dodajLog("Lis nie znalazł bezpiecznego pola do ruchu.");
    }
}