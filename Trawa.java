public class Trawa extends Roslina {

    public Trawa(Swiat swiat, int x, int y) {
        super(swiat, 0, x, y, "Trawa");
    }

    @Override
    public char rysowanie() {
        return 'T';
    }

    @Override
    public Organizm klonuj(Swiat swiat, int x, int y) {
        return new Trawa(swiat, x, y);
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        return false;
    }
}