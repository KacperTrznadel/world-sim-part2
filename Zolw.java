public class Zolw extends Zwierze {

    public Zolw(Swiat swiat, int x, int y) {
        super(swiat, 2, 1, x, y, "Zolw"); // Żółw ma siłę 2 i inicjatywę 1
    }

    @Override
    public char rysowanie() {
        return 'Z';
    }

    @Override
    public Organizm klonuj(Swiat swiat, int x, int y) {
        return new Zolw(swiat, x, y);
    }

    @Override
    public void akcja() {
        if (Math.random() < 0.75) { // 75% szans na brak ruchu
            wiekInkrementacja();
            return;
        }
        super.akcja();
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        if (atakujacy.getSila() < 5) {
            getSwiat().dodajLog(getNazwa() + " odbił atak " + atakujacy.getNazwa() + " na polu (" + getX() + "," + getY() + ")");
            return true;
        }
        return false;
    }
}