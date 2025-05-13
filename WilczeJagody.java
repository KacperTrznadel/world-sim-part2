public class WilczeJagody extends Roslina {

    public WilczeJagody(Swiat swiat, int x, int y) {
        super(swiat, 99, x, y, "Wilcze Jagody");
    }

    @Override
    public char rysowanie() {
        return 'J';
    }

    @Override
    public Organizm klonuj(Swiat swiat, int x, int y) {
        return new WilczeJagody(swiat, x, y);
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        return false;
    }

    @Override
    public void kolizja(Organizm inny, int oldX, int oldY) {
        getSwiat().dodajLog(inny.getNazwa() + " zjada Wilcze Jagody na polu (" + getX() + "," + getY() + ")");
        getSwiat().dodajLog(inny.getNazwa() + " umiera na polu (" + getX() + "," + getY() + ")");
        inny.setPozycja(getX(), getY());
        this.zabij();
        inny.zabij();
    }
}