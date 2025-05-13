public class Mlecz extends Roslina {

    public Mlecz(Swiat swiat, int x, int y) {
        super(swiat, 0, x, y, "Mlecz");
    }

    @Override
    public char rysowanie() {
        return 'M';
    }

    @Override
    public Organizm klonuj(Swiat swiat, int x, int y) {
        return new Mlecz(swiat, x, y);
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        return false;
    }

    @Override
    public void akcja() {
        wiekInkrementacja();
        for (int i = 0; i < 3; i++) { // Mlecz próbuje rozsiewać się 3 razy
            if (Math.random() < 0.1) { // 10% szans na rozsianie
                int[] dx = {0, 1, 0, -1};
                int[] dy = {-1, 0, 1, 0};
                int[] kierunki = {0, 1, 2, 3};

                for (int j = 3; j > 0; --j) {
                    int k = (int) (Math.random() * (j + 1));
                    int temp = kierunki[j];
                    kierunki[j] = kierunki[k];
                    kierunki[k] = temp;
                }

                for (int j = 0; j < 4; ++j) {
                    int k = kierunki[j];
                    int newX = getX() + dx[k];
                    int newY = getY() + dy[k];

                    if (newX >= 0 && newX < getSwiat().getSzerokosc() && newY >= 0 && newY < getSwiat().getWysokosc() &&
                            getSwiat().getOrganizmNaPolu(newX, newY) == null) {
                        Organizm nowa = klonuj(getSwiat(), newX, newY);
                        getSwiat().dodajOrganizm(nowa);
                        getSwiat().dodajLog("Mlecz rozsiał się na (" + newX + "," + newY + ")");
                        return;
                    }
                }
            }
        }
    }
}