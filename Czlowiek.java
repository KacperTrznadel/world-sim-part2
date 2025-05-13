public class Czlowiek extends Zwierze {
    private boolean tarczaAktywna = false;
    private int dzialanieTarczy = 5;
    private int cooldown = 0;

    public Czlowiek(Swiat swiat, int x, int y) {
        super(swiat, 5, 4, x, y, "Czlowiek"); // Człowiek ma siłę 5 i inicjatywę 4
    }

    @Override
    public char rysowanie() {
        return 'C';
    }

    @Override
    public Organizm klonuj(Swiat swiat, int x, int y) {
        return new Czlowiek(swiat, x, y);
    }

    @Override
    public void akcja() {
        Swiat swiat = getSwiat();
        wiekInkrementacja();

        // TODO: Obsługa wejścia użytkownika (np. strzałki lub klawisz 'a')
        int klawisz = -1; // Zastąp odpowiednią obsługą wejścia w Javie

        int oldX = getX();
        int oldY = getY();
        int newX = oldX;
        int newY = oldY;

        // Aktualizacja działania i cooldownu tarczy
        if (dzialanieTarczy > 0) {
            dzialanieTarczy--;
        }
        if (cooldown > 0) {
            tarczaAktywna = false;
            cooldown--;
            swiat.dodajLog("Tarcza Alzura będzie dostępna za " + cooldown + " tur!");
        }
        if (dzialanieTarczy == 0 && tarczaAktywna) {
            tarczaAktywna = false;
            cooldown = 5;
            swiat.dodajLog("Tarcza Alzura przestała działać! Ponowne użycie będzie możliwe za " + cooldown + " tur!");
        }

        switch (klawisz) {
            case 38: // KEY_UP (strzałka w górę)
                newY = oldY - 1;
                break;
            case 40: // KEY_DOWN (strzałka w dół)
                newY = oldY + 1;
                break;
            case 37: // KEY_LEFT (strzałka w lewo)
                newX = oldX - 1;
                break;
            case 39: // KEY_RIGHT (strzałka w prawo)
                newX = oldX + 1;
                break;
            case 'a': // Aktywacja tarczy
                if (tarczaAktywna) {
                    swiat.dodajLog("Człowiek próbuje użyć Tarczy Alzura... ale umiejętność już działa!");
                } else if (cooldown > 0) {
                    swiat.dodajLog("Człowiek próbuje użyć Tarczy Alzura... ale umiejętność niedostępna jeszcze przez najbliższe " + cooldown + " tur!");
                } else {
                    swiat.dodajLog(getNazwa() + " używa umiejętności Tarcza Alzura");
                    tarczaAktywna = true;
                    dzialanieTarczy = 5;
                }
                return;
            default:
                return;
        }

        if (newX >= 0 && newX < swiat.getSzerokosc() && newY >= 0 && newY < swiat.getWysokosc()) {
            Organizm cel = swiat.getOrganizmNaPolu(newX, newY);
            if (cel == null) {
                swiat.dodajLog(getNazwa() + " przeszedł na pole (" + newX + "," + newY + ")");
                setPozycja(newX, newY);
            } else {
                swiat.dodajLog(getNazwa() + " próbuje wejść na pole zajmowane przez " + cel.getNazwa() + " na polu (" + newX + "," + newY + ")");
                kolizja(cel, oldX, oldY);
            }
        }
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        Swiat swiat = getSwiat();
        if (tarczaAktywna && cooldown == 0 && dzialanieTarczy > 0) {
            swiat.dodajLog("Człowiek odbił atak za pomocą Tarczy Alzura!");
            return true;
        }
        return false;
    }

    @Override
    public void kolizja(Organizm atakujacy, int oldX, int oldY) {
        Swiat swiat = getSwiat();

        // Jeśli kolizja jest z rośliną, przekaz jej kontrolę nad kolizją
        if (atakujacy instanceof Roslina) {
            atakujacy.kolizja(this, oldX, oldY);
            return;
        }

        if (atakujacy.czyOdbilAtak(this)) {
            return;
        }

        // Czy tarcza aktualnie działa - jeśli tak, odbij
        if (tarczaAktywna) {
            int[] dx = {0, 1, 0, -1};
            int[] dy = {-1, 0, 1, 0};
            int[] kierunki = {0, 1, 2, 3};

            // Tasowanie kierunków
            for (int i = 3; i > 0; --i) {
                int j = (int) (Math.random() * (i + 1));
                int temp = kierunki[i];
                kierunki[i] = kierunki[j];
                kierunki[j] = temp;
            }

            for (int i = 0; i < 4; i++) {
                int k = kierunki[i];
                int newX = atakujacy.getX() + dx[k];
                int newY = atakujacy.getY() + dy[k];
                if (newX >= 0 && newX < swiat.getSzerokosc() && newY >= 0 && newY < swiat.getWysokosc() && swiat.getOrganizmNaPolu(newX, newY) == null) {
                    atakujacy.setPozycja(newX, newY);
                    swiat.dodajLog(atakujacy.getNazwa() + " został odepchnięty przez Tarcze Alzura na pole (" + newX + "," + newY + ")");
                    return;
                }
            }
            return;
        }

        if (atakujacy.getSila() > this.getSila()) {
            swiat.dodajLog(getNazwa() + " został zabity przez " + atakujacy.getNazwa() + " na polu (" + atakujacy.getX() + "," + atakujacy.getY() + ")");
            this.zabij();
        } else {
            swiat.dodajLog(getNazwa() + " pokonał " + atakujacy.getNazwa() + " na polu (" + atakujacy.getX() + "," + atakujacy.getY() + ")");
            setPozycja(atakujacy.getX(), atakujacy.getY());
            atakujacy.zabij();
        }
    }

    public boolean czyTarczaAktywna() {
        return tarczaAktywna;
    }

    public void setTarczaAktywna(boolean aktywna) {
        this.tarczaAktywna = aktywna;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getDzialanieTarczy() {
        return dzialanieTarczy;
    }

    public void setDzialanieTarczy(int dzialanie) {
        this.dzialanieTarczy = dzialanie;
    }
}