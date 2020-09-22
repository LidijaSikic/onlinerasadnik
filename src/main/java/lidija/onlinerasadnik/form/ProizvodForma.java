package lidija.onlinerasadnik.form;

import lidija.onlinerasadnik.entity.Proizvod;
import org.springframework.web.multipart.MultipartFile;

public class ProizvodForma {
    private String sifra;
    private String naziv;
    private double cijena;

    private boolean noviProizvod = false;

    // Upload file.
    private MultipartFile fileData;

    public ProizvodForma() {
        this.noviProizvod= true;
    }

    public ProizvodForma(Proizvod proizvod) {
        this.sifra = proizvod.getSifra();
        this.naziv = proizvod.getNaziv();
        this.cijena = proizvod.getCijena();
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

    public MultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }

    public boolean jeNoviProizvod() {
        return noviProizvod;
    }

    public void setNoviProizvod(boolean noviProizvod) {
        this.noviProizvod = noviProizvod;
    }

}
