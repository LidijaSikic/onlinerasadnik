package lidija.onlinerasadnik.model;

import java.util.Date;
import java.util.List;

public class NarudzbaInfo {

    private String id;
    private Date datumNarudzbe;
    private int brNarudzbe;
    private double iznos;

    private String imeKupca;
    private String kupacAdresa;
    private String kupacMail;
    private String kupacTel;

    private List<DetaljiNarudzbeInfo> detalji;

    public NarudzbaInfo() {

    }

    // Using for Hibernate Query.
    public NarudzbaInfo(String id, Date datumNarudzbe, int brNarudzbe, //
                     double iznos, String imeKupca, String kupacAdresa, //
                     String kupacMail, String kupacTel) {
        this.id = id;
        this.datumNarudzbe = datumNarudzbe;
        this.brNarudzbe = brNarudzbe;
        this.iznos = iznos;

        this.imeKupca = imeKupca;
        this.kupacAdresa = kupacAdresa;
        this.kupacMail = kupacMail;
        this.kupacTel = kupacTel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDatumNarudzbe() {
        return datumNarudzbe;
    }

    public void setDatumNarudzbe(Date datumNarudzbe) {
        this.datumNarudzbe = datumNarudzbe;
    }

    public int getBrNarudzbe() {
        return brNarudzbe;
    }

    public void setBrNarudzbe(int brNarudzbe) {
        this.brNarudzbe = brNarudzbe;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public String getImeKupca() {
        return imeKupca;
    }

    public void setImeKupca(String imeKupca) {
        this.imeKupca = imeKupca;
    }

    public String getKupacAdresa() {
        return kupacAdresa;
    }

    public void setKupacAdresa(String kupacAdresa) {
        this.kupacAdresa = kupacAdresa;
    }

    public String getKupacMail() {
        return kupacMail;
    }

    public void setKupacMail(String kupacMail) {
        this.kupacMail = kupacMail;
    }

    public String getKupacTel() {
        return kupacTel;
    }

    public void setKupacTel(String kupacTel) {
        this.kupacTel = kupacTel;
    }

    public List<DetaljiNarudzbeInfo> getDetalji() {
        return detalji;
    }

    public void setDetalji(List<DetaljiNarudzbeInfo> detalji) {
        this.detalji = detalji;
    }

}