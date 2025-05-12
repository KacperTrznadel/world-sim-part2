import java.util.ArrayList;
import java.util.List;

public class Swiat {
    private int szerokoscPlanszy;
    private int wysokoscPlanszy;
    private List<Organizm> organizmy;
    private List<Organizm> noweOrganizmy;
    private List<String> logi;

    // Konstruktor
    public Swiat(int szerokoscPlanszy, int wysokoscPlanszy) {
        this.szerokoscPlanszy = szerokoscPlanszy;
        this.wysokoscPlanszy = wysokoscPlanszy;
        this.organizmy = new ArrayList<>();
        this.noweOrganizmy = new ArrayList<>();
        this.logi = new ArrayList<>();
    }

    // Metody
    public void zapiszDoPliku(String nazwaPliku) {
        // TODO: Implementacja zapisu do pliku
    }

    public void wczytajZPliku(String nazwaPliku) {
        // TODO: Implementacja wczytywania z pliku
    }

    public void wykonajTure() {
        sortujOrganizmy();
        wykonajWszystkieAkcje();
        usunWszystkieMartwe();

        organizmy.addAll(noweOrganizmy);
        noweOrganizmy.clear();
        sortujOrganizmy();
    }

    public void rysujSwiat() {
        // TODO: Implementacja rysowania świata (ncurses)
    }

    public void dodajOrganizm(Organizm nowy) {
        if (nowy.getX() >= 0 && nowy.getX() < szerokoscPlanszy && nowy.getY() >= 0 && nowy.getY() < wysokoscPlanszy) {
            noweOrganizmy.add(nowy);
        } else {
            nowy = null; // W Javie obiekt zostanie usunięty przez GC
        }
    }

    public void dodajLog(String tekst) {
        logi.add(tekst);
    }

    public void wypiszLogi(int offset) {
        // TODO: Implementacja wypisywania logów (ncurses)
    }

    public Organizm getOrganizmNaPolu(int x, int y) {
        for (Organizm org : organizmy) {
            if (org.getX() == x && org.getY() == y && org.czyZywy()) {
                return org;
            }
        }
        for (Organizm org : noweOrganizmy) {
            if (org.getX() == x && org.getY() == y && org.czyZywy()) {
                return org;
            }
        }
        return null;
    }

    public int getSzerokosc() {
        return szerokoscPlanszy;
    }

    public int getWysokosc() {
        return wysokoscPlanszy;
    }

    // Prywatne metody pomocnicze
    private void wykonajWszystkieAkcje() {
        for (Organizm org : organizmy) {
            if (org.czyZywy()) {
                org.akcja();
            }
        }
    }

    private void usunWszystkieMartwe() {
        organizmy.removeIf(org -> !org.czyZywy());
    }

    private void sortujOrganizmy() {
        organizmy.sort((o1, o2) -> {
            if (o1.getInicjatywa() != o2.getInicjatywa()) {
                return Integer.compare(o2.getInicjatywa(), o1.getInicjatywa());
            }
            return Integer.compare(o1.getWiek(), o2.getWiek());
        });
    }
}