package lidija.onlinerasadnik.dao;

import java.io.IOException;
import java.util.Date;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import lidija.onlinerasadnik.entity.Proizvod;
import lidija.onlinerasadnik.form.ProizvodForma;
import lidija.onlinerasadnik.model.ProizvodiInfo;
import lidija.onlinerasadnik.pagination.PaginationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ProizvodDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Proizvod nadiProizvod(String sifra) {
        try {
            String sql = "Select e from " + Proizvod.class.getName() + " e Where e.sifra =:sifra ";

            Session session = this.sessionFactory.getCurrentSession();
            Query<Proizvod> query = session.createQuery(sql, Proizvod.class);
            query.setParameter("sifra", sifra);
            return (Proizvod) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ProizvodiInfo findProductInfo(String sifra) {
        Proizvod proizvod = this.nadiProizvod(sifra);
        if (proizvod == null) {
            return null;
        }
        return new ProizvodiInfo(proizvod.getSifra(), proizvod.getNaziv(), proizvod.getCijena());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(ProizvodForma proizvodForma) {

        Session session = this.sessionFactory.getCurrentSession();
        String sifra = proizvodForma.getSifra();

        Proizvod proizvod = null;

        boolean jeNov = false;
        if (sifra != null) {
            proizvod = this.nadiProizvod(sifra);
        }
        if (proizvod == null) {
            jeNov = true;
            proizvod = new Proizvod();
            proizvod.setDatum(new Date());
        }
        proizvod.setSifra(sifra);
        proizvod.setNaziv(proizvodForma.getNaziv());
        proizvod.setCijena(proizvodForma.getCijena());

        if (proizvodForma.getFileData() != null) {
            byte[] slika = null;
            try {
                slika = proizvodForma.getFileData().getBytes();
            } catch (IOException e) {
            }
            if (slika != null && slika.length > 0) {
                proizvod.setSlika(slika);
            }
        }
        if (jeNov) {
            session.persist(proizvod);
        }
        // If error in DB, Exceptions will be thrown out immediately
        session.flush();
    }

    public PaginationResult<ProizvodiInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
                                                       String likeName) {
        String sql = "Select new " + ProizvodiInfo.class.getName() //
                + "(p.sifra, p.naziv, p.cijena) " + " from "//
                + Proizvod.class.getName() + " p ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(p.naziv) like :likeName ";
        }
        sql += " order by p.datum desc ";
        //
        Session session = this.sessionFactory.getCurrentSession();
        Query<ProizvodiInfo> query = session.createQuery(sql, ProizvodiInfo.class);

        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<ProizvodiInfo>(query, page, maxResult, maxNavigationPage);
    }

    public PaginationResult<ProizvodiInfo> queryProducts(int stranica, int maxRezultat, int maxNavigationPage) {
        return queryProducts(stranica, maxRezultat, maxNavigationPage, null);
    }

}
