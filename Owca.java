public class Owca extends Zwierze {

    public Owca(Swiat swiat, int x, int y) {
        super(swiat, 4, 4, x, y, "Owca"); // Owca ma siłę 4 i inicjatywę 4
    }

    @Override
    public char rysowanie() {
        return 'O';
    }

    @Override
    public Organizm klonuj(Swiat swiat, int x, int y) {
        return new Owca(swiat, x, y);
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        return false; // Owca nie odbija ataków
    }
}