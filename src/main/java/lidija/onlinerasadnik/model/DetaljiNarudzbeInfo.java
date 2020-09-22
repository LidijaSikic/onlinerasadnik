package lidija.onlinerasadnik.model;

public class DetaljiNarudzbeInfo {
    private String id;

    private String proizvodSifra;
    private String proizvodNaziv;

    private int kolicina;
    private double cijena;
    private double iznos;

    public DetaljiNarudzbeInfo() {

    }

    // Using for JPA/Hibernate Query.
    public DetaljiNarudzbeInfo(String id, String proizvodSifra, //
                           String proizvodNaziv, int kolicina, double cijena, double iznos) {
        this.id = id;
        this.proizvodSifra = proizvodSifra;
        this.proizvodNaziv = proizvodNaziv;
        this.kolicina = kolicina;
        this.cijena = cijena;
        this.iznos = iznos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProizvodSifra() {
        return proizvodSifra;
    }

    public void setProizvodSifra(String proizvodSifra) {
        this.proizvodSifra = proizvodSifra;
    }

    public String getProizvodNaziv() {
        return proizvodNaziv;
    }

    public void setProizvodNaziv(String proizvodNaziv) {
        this.proizvodNaziv = proizvodNaziv;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCijena() {
        return cijena;
    }

    public void setCijena(double cijena) {
        this.cijena = cijena;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }
}