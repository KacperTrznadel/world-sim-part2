public abstract class Roslina extends Organizm {

    public Roslina(Swiat swiat, int sila, int x, int y, String nazwa) {
        super(swiat, sila, 0, x, y, nazwa); // Rośliny mają inicjatywę 0
    }

    @Override
    public void akcja() {
        wiekInkrementacja();

        if (Math.random() < 0.1) { // 10% szans na rozsianie
            int[] dx = {0, 1, 0, -1};
            int[] dy = {-1, 0, 1, 0};

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

                if (newX >= 0 && newX < getSwiat().getSzerokosc() &&
                        newY >= 0 && newY < getSwiat().getWysokosc() &&
                        getSwiat().getOrganizmNaPolu(newX, newY) == null) {

                    Organizm nowa = klonuj(getSwiat(), newX, newY);
                    getSwiat().dodajOrganizm(nowa);
                    getSwiat().dodajLog(getNazwa() + " rozsiał się na (" + newX + "," + newY + ")");
                    return;
                }
            }
        }
    }

    @Override
    public void kolizja(Organizm inny, int oldX, int oldY) {
        getSwiat().dodajLog(inny.getNazwa() + " zjada " + getNazwa() + " na polu (" + getX() + "," + getY() + ")");
        inny.setPozycja(getX(), getY());
        this.zabij();
    }
}