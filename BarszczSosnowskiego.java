public class BarszczSosnowskiego extends Roslina {

    public BarszczSosnowskiego(Swiat swiat, int x, int y) {
        super(swiat, 10, x, y, "Barszcz Sosnowskiego");
    }

    @Override
    public char rysowanie() {
        return 'S';
    }

    @Override
    public Organizm klonuj(Swiat swiat, int x, int y) {
        return new BarszczSosnowskiego(swiat, x, y);
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

        // Usuwanie sąsiednich zwierząt
        for (int i = 0; i < 4; ++i) {
            int newX = getX() + dx[i];
            int newY = getY() + dy[i];

            if (newX >= 0 && newX < getSwiat().getSzerokosc() && newY >= 0 && newY < getSwiat().getWysokosc()) {
                Organizm cel = getSwiat().getOrganizmNaPolu(newX, newY);
                if (cel != null && cel instanceof Zwierze) {
                    getSwiat().dodajLog(getNazwa() + " zabija " + cel.getNazwa() + " na polu (" + newX + "," + newY + ")");
                    cel.zabij();
                }
            }
        }

        // Zwykła akcja rośliny
        if (Math.random() < 0.1) { // 10% szans na rozsianie
            int[] kierunki = {0, 1, 2, 3};
            for (int i = 3; i > 0; --i) {
                int j = (int) (Math.random() * (i + 1));
                int temp = kierunki[i];
                kierunki[i] = kierunki[j];
                kierunki[j] = temp;
            }

            for (int i = 0; i < 4; ++i) {
                int k = kierunki[i];
                int newX = getX() + dx[k];
                int newY = getY() + dy[k];

                if (newX >= 0 && newX < getSwiat().getSzerokosc() && newY >= 0 && newY < getSwiat().getWysokosc() &&
                        getSwiat().getOrganizmNaPolu(newX, newY) == null) {
                    Organizm nowa = klonuj(getSwiat(), newX, newY);
                    getSwiat().dodajOrganizm(nowa);
                    getSwiat().dodajLog("Barszcz Sosnowskiego rozsiał się na (" + newX + "," + newY + ")");
                    return;
                }
            }
        }
    }

    @Override
    public void kolizja(Organizm inny, int oldX, int oldY) {
        getSwiat().dodajLog(inny.getNazwa() + " zjada " + getNazwa() + " na polu (" + getX() + "," + getY() + ")");
        getSwiat().dodajLog(inny.getNazwa() + " umiera na polu (" + getX() + "," + getY() + ")");
        inny.setPozycja(getX(), getY());
        this.zabij();
        inny.zabij();
    }
}