package lidija.onlinerasadnik.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Detalji_Narudzbe")
public class DetaljiNarudzbe implements Serializable {

    private static final long serialVersionUID = 7550745928843183535L;

    @Id
    @Column(name = "ID", length = 50, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NARUDZBA_ID", nullable = false, //
            foreignKey = @ForeignKey(name = "DETALJI_NARUDZBE_NAR_FK"))
    private Narudzba narudzba;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROIZVOD_ID", nullable = false, //
            foreignKey = @ForeignKey(name = "DETALJI_NARUDZBE_PROIZV_FK"))
    private Proizvod proizvod;

    @Column(name = "Kolicina", nullable = false)
    private int kolicina;

    @Column(name = "Cijena", nullable = false)
    private double cijena;

    @Column(name = "Iznos", nullable = false)
    private double iznos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Narudzba getNarudzba() {
        return narudzba;
    }

    public void setNarudzba(Narudzba narudzba) {
        this.narudzba = narudzba;
    }

    public Proizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvod proizvod) {
        this.proizvod = proizvod;
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
