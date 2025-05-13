public class Guarana extends Roslina {

    public Guarana(Swiat swiat, int x, int y) {
        super(swiat, 0, x, y, "Guarana");
    }

    @Override
    public char rysowanie() {
        return 'G';
    }

    @Override
    public Organizm klonuj(Swiat swiat, int x, int y) {
        return new Guarana(swiat, x, y);
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        return false;
    }

    @Override
    public void kolizja(Organizm inny, int oldX, int oldY) {
        getSwiat().dodajLog(inny.getNazwa() + " zjada Guaranę na polu (" + getX() + "," + getY() + ")");
        getSwiat().dodajLog(inny.getNazwa() + " zwiększa siłę o 3. Jest ona teraz równa " + (inny.getSila() + 3));
        inny.setSila(inny.getSila() + 3);
        inny.setPozycja(getX(), getY());
        this.zabij();
    }
}