public class Wilk extends Zwierze {

    public Wilk(Swiat swiat, int x, int y) {
        super(swiat, 9, 5, x, y, "Wilk"); // Wilk ma siłę 9 i inicjatywę 5
    }

    @Override
    public char rysowanie() {
        return 'W';
    }

    @Override
    public Organizm klonuj(Swiat swiat, int x, int y) {
        String nowyLog = "Wilk rozmnaża się wspólnie z innym wilkiem. Nowy wilk na polu: " + x + ", " + y;
        swiat.dodajLog(nowyLog);
        return new Wilk(swiat, x, y);
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        return false; // Wilk nie odbija ataków
    }
}