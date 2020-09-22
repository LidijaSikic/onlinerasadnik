package lidija.onlinerasadnik.model;

public class KosaricaStavakInfo {

    private ProizvodiInfo proizvodiInfo;
    private int kolicina;

    public KosaricaStavakInfo() {
        this.kolicina = 0;
    }

    public ProizvodiInfo getProizvodiInfo() {
        return proizvodiInfo;
    }

    public void setProizvodiInfo(ProizvodiInfo proizvodiInfo) {
        this.proizvodiInfo = proizvodiInfo;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getIznos() {
        return this.proizvodiInfo.getCijena() * this.kolicina;
    }

}
