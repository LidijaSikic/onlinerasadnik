package lidija.onlinerasadnik.model;

import lidija.onlinerasadnik.entity.Proizvod;

public class ProizvodiInfo {
    private String sifra;
    private String naziv;
    private double cijena;

    public ProizvodiInfo() {
    }

    public ProizvodiInfo(Proizvod proizvod) {
        this.sifra = proizvod.getSifra();
        this.naziv = proizvod.getNaziv();
        this.cijena = proizvod.getCijena();
    }

    // Using in JPA/Hibernate query
    public ProizvodiInfo(String sifra, String naziv, double cijena) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.cijena = cijena;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getCijena() {
        return cijena;
    }

    public void setCijena(double cijena) {
        this.cijena = cijena;
    }

}
