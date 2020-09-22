package lidija.onlinerasadnik.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Proizvodi")
public class Proizvod implements Serializable {

    private static final long serialVersionUID = -1000119078147252957L;

    @Id
    @Column(name = "Sifra", length = 20, nullable = false)
    private String sifra;

    @Column(name = "Naziv", length = 255, nullable = false)
    private String naziv;

    @Column(name = "Cijena", nullable = false)
    private double cijena;

    @Lob
    @Column(name = "Slika", length = Integer.MAX_VALUE, nullable = true)
    private byte[] slika;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Datum", nullable = false)
    private Date datum;

    public Proizvod() {
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

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public byte[] getSlika() {
        return slika;
    }

    public void setSlika(byte[] slika) {
        this.slika = slika;
    }

}