public abstract class Organizm {
    private int sila;
    private int inicjatywa;
    private int x, y;
    private int wiek = 0;
    private String nazwa;
    private Swiat swiat;
    private boolean zywy = true;

    public Organizm(Swiat swiat, int sila, int inicjatywa, int x, int y, String nazwa) {
        this.swiat = swiat;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.x = x;
        this.y = y;
        this.nazwa = nazwa;
    }

    public int getSila() {
        return sila;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWiek() {
        return wiek;
    }

    public Swiat getSwiat() {
        return swiat;
    }

    public String getNazwa() {
        return nazwa;
    }

    public boolean czyZywy() {
        return zywy;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public void setPozycja(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public void wiekInkrementacja() {
        this.wiek++;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }

    public void zabij() {
        this.zywy = false;
    }

    // Metody abstrakcyjne
    public abstract void akcja();
    public abstract void kolizja(Organizm inny, int oldX, int oldY);
    public abstract boolean czyOdbilAtak(Organizm atakujacy);
    public abstract char rysowanie();
    public abstract Organizm klonuj(Swiat swiat, int x, int y);
}