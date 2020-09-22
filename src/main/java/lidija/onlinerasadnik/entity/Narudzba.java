package lidija.onlinerasadnik.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Narudzbe", //
        uniqueConstraints = { @UniqueConstraint(columnNames = "Detalji_Narudzbe") })
public class Narudzba implements Serializable {

    private static final long serialVersionUID = -2576670215015463100L;

    @Id
    @Column(name = "ID", length = 50)
    private String id;

    @Column(name = "Datum_Narudzbe", nullable = false)
    private Date datumNarudzbe;

    @Column(name = "Broj_Narudzbe", nullable = false)
    private int brNarudzbe;

    @Column(name = "Iznos", nullable = false)
    private double iznos;

    @Column(name = "Kupac_Ime", length = 255, nullable = false)
    private String imeKupca;

    @Column(name = "Kupac_Adresa", length = 255, nullable = false)
    private String kupacAdresa;

    @Column(name = "Kupac_Mail", length = 128, nullable = false)
    private String kupacMail;

    @Column(name = "Kupac_Tel", length = 128, nullable = false)
    private String kupacTel;

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

}