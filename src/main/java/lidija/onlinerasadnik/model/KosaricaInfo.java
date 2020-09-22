package lidija.onlinerasadnik.model;

import java.util.ArrayList;
import java.util.List;

public class KosaricaInfo {

    private int brNar;

    private KupacInfo kupacInfo;

    private final List<KosaricaStavakInfo> kosaricaStavak = new ArrayList<KosaricaStavakInfo>();

    public KosaricaInfo() {

    }

    public int getBrNar() {
        return brNar;
    }

    public void setBrNar(int brNar) {
        this.brNar = brNar;
    }

    public KupacInfo getKupacInfo() {
        return kupacInfo;
    }

    public void setKupacInfo(KupacInfo kupacInfo) {
        this.kupacInfo = kupacInfo;
    }

    public List<KosaricaStavakInfo> getKosaricaStavak() {
        return this.kosaricaStavak;
    }

    private KosaricaStavakInfo findLineByCode(String sifra) {
        for (KosaricaStavakInfo line : this.kosaricaStavak) {
            if (line.getProizvodiInfo().getSifra().equals(sifra)) {
                return line;
            }
        }
        return null;
    }

    public void addProduct(ProizvodiInfo proizvodiInfo, int kolicina) {
        KosaricaStavakInfo line = this.findLineByCode(proizvodiInfo.getSifra());

        if (line == null) {
            line = new KosaricaStavakInfo();
            line.setKolicina(0);
            line.setProizvodiInfo(proizvodiInfo);
            this.kosaricaStavak.add(line);
        }
        int novaKol = line.getKolicina() + kolicina;
        if (novaKol <= 0) {
            this.kosaricaStavak.remove(line);
        } else {
            line.setKolicina(novaKol);
        }
    }

    public void provjeri() {

    }

    public void updateProizvod(String sifra, int kolicina) {
        KosaricaStavakInfo line = this.findLineByCode(sifra);

        if (line != null) {
            if (kolicina <= 0) {
                this.kosaricaStavak.remove(line);
            } else {
                line.setKolicina(kolicina);
            }
        }
    }

    public void ukloniProizvod(ProizvodiInfo proizvodiInfo) {
        KosaricaStavakInfo line = this.findLineByCode(proizvodiInfo.getSifra());
        if (line != null) {
            this.kosaricaStavak.remove(line);
        }
    }

    public boolean jePrazan() {
        return this.kosaricaStavak.isEmpty();
    }

    public boolean jeOkKupac() {
        return this.kupacInfo != null && this.kupacInfo.jeOk();
    }

    public int getUkKolicinu() {
        int kolicina = 0;
        for (KosaricaStavakInfo line : this.kosaricaStavak) {
            kolicina += line.getKolicina();
        }
        return kolicina;
    }

    public double getUkIznos() {
        double ukupno = 0;
        for (KosaricaStavakInfo line : this.kosaricaStavak) {
            ukupno += line.getKolicina();
        }
        return ukupno;
    }

    public void updateKolicina(KosaricaInfo kosarica) {
        if (kosarica != null) {
            List<KosaricaStavakInfo> lines = kosarica.getKosaricaStavak();
            for (KosaricaStavakInfo line : lines) {
                this.updateProizvod(line.getProizvodiInfo().getSifra(), line.getKolicina());
            }
        }

    }

}
