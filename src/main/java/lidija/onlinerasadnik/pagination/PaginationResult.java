package lidija.onlinerasadnik.pagination;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.query.Query;

public class PaginationResult<E> {

    private int ukupnoZapisa;
    private int trenutnaStranica;
    private List<E> list;
    private int maxRezultat;
    private int ukupnoStranica;

    private int maxNavigationPage;

    private List<Integer> navigationPages;

    // @page: 1, 2, ..
    public PaginationResult(Query<E> query, int stranica, int maxRezultat, int maxNavigationPage) {
        final int stranicaIndex = stranica - 1 < 0 ? 0 : stranica - 1;

        int fromRecordIndex = stranicaIndex * maxRezultat;
        int maxRecordIndex = fromRecordIndex + maxRezultat;

        ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

        List<E> rezultat = new ArrayList<>();

        boolean imaRezultat = resultScroll.first();

        if (imaRezultat) {
            // Scroll to position:
            imaRezultat = resultScroll.scroll(fromRecordIndex);

            if (imaRezultat) {
                do {
                    E record = (E) resultScroll.get(0);
                    rezultat.add(record);
                } while (resultScroll.next()//
                        && resultScroll.getRowNumber() >= fromRecordIndex
                        && resultScroll.getRowNumber() < maxRecordIndex);

            }

            // Go to Last record.
            resultScroll.last();
        }

        // Total Records
        this.ukupnoZapisa = resultScroll.getRowNumber() + 1;
        this.trenutnaStranica = stranicaIndex + 1;
        this.list = rezultat;
        this.maxRezultat = maxRezultat;

        if (this.ukupnoZapisa % this.maxRezultat == 0) {
            this.ukupnoStranica = this.ukupnoZapisa / this.maxRezultat;
        } else {
            this.ukupnoStranica = (this.ukupnoZapisa / this.maxRezultat) + 1;
        }

        this.maxNavigationPage = maxNavigationPage;

        if (maxNavigationPage < ukupnoStranica) {
            this.maxNavigationPage = maxNavigationPage;
        }

        this.calcNavigationPages();
    }

    private void calcNavigationPages() {

        this.navigationPages = new ArrayList<Integer>();

        int trenutna = this.trenutnaStranica > this.ukupnoStranica ? this.ukupnoStranica : this.trenutnaStranica;

        int begin = trenutna - this.maxNavigationPage / 2;
        int end = trenutna + this.maxNavigationPage / 2;

        // The first page
        navigationPages.add(1);
        if (begin > 2) {

            // Using for '...'
            navigationPages.add(-1);
        }

        for (int i = begin; i < end; i++) {
            if (i > 1 && i < this.ukupnoStranica) {
                navigationPages.add(i);
            }
        }

        if (end < this.ukupnoStranica - 2) {


            navigationPages.add(-1);
        }

        navigationPages.add(this.ukupnoStranica);
    }

    public int getUkupnoStranica() {
        return ukupnoStranica;
    }

    public int getUkupnoZapisa() {
        return ukupnoZapisa;
    }

    public int getTrenutnaStranica() {
        return trenutnaStranica;
    }

    public List<E> getList() {
        return list;
    }

    public int getMaxRezultat() {
        return maxRezultat;
    }

    public List<Integer> getNavigationPages() {
        return navigationPages;
    }

}
