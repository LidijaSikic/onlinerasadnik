package lidija.onlinerasadnik.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lidija.onlinerasadnik.model.KosaricaInfo;
import lidija.onlinerasadnik.model.KosaricaStavakInfo;
import lidija.onlinerasadnik.model.KupacInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import lidija.onlinerasadnik.entity.Narudzba;
import lidija.onlinerasadnik.entity.DetaljiNarudzbe;
import lidija.onlinerasadnik.entity.Proizvod;
import lidija.onlinerasadnik.model.KosaricaInfo;
import lidija.onlinerasadnik.model.KosaricaStavakInfo;
import lidija.onlinerasadnik.model.KupacInfo;
import lidija.onlinerasadnik.model.DetaljiNarudzbeInfo;
import lidija.onlinerasadnik.model.NarudzbaInfo;
import lidija.onlinerasadnik.pagination.PaginationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class NarudzbaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ProizvodDAO proizvodDAO;

    private int getMaxOrderNum() {
        String sql = "Select max(n.brNarudzbe) from " + Narudzba.class.getName() + " n ";
        Session session = this.sessionFactory.getCurrentSession();
        Query<Integer> query = session.createQuery(sql, Integer.class);
        Integer value = (Integer) query.getSingleResult();
        if (value == null) {
            return 0;
        }
        return value;
    }

    @Transactional(rollbackFor = Exception.class)
    public void spremiNarudzbu(KosaricaInfo kosaricaInfo) {
        Session session = this.sessionFactory.getCurrentSession();

        int brNarudzbe = this.getMaxOrderNum() + 1;
        Narudzba narudzba = new Narudzba();

        narudzba.setId(UUID.randomUUID().toString());
        narudzba.setBrNarudzbe( brNarudzbe);
        narudzba.setDatumNarudzbe(new Date());
        narudzba.setIznos(kosaricaInfo.getUkKolicinu());

        KupacInfo kupacInfo = kosaricaInfo.getKupacInfo();
        narudzba.setImeKupca(kupacInfo.getIme());
        narudzba.setKupacMail(kupacInfo.getMail());
        narudzba.setKupacTel(kupacInfo.getTel());
        narudzba.setKupacAdresa(kupacInfo.getAdresa());

        session.persist(narudzba);

        List<KosaricaStavakInfo> lines = kosaricaInfo.getKosaricaStavak();

        for (KosaricaStavakInfo line : lines) {
            DetaljiNarudzbe detalji = new DetaljiNarudzbe();
            detalji.setId(UUID.randomUUID().toString());
            detalji.setNarudzba(narudzba);
            detalji.setIznos(line.getIznos());
            detalji.setCijena(line.getProizvodiInfo().getCijena());
            detalji.setKolicina(line.getKolicina());


            String sifra = line.getProizvodiInfo().getSifra();
            Proizvod proizvod = this.proizvodDAO.nadiProizvod(sifra);
            detalji.setProizvod(proizvod);

            session.persist(detalji);
        }

        // Order Number!
        kosaricaInfo.setBrNar(brNarudzbe);
        // Flush
        session.flush();
    }

    // @page = 1, 2, ...
    public PaginationResult<NarudzbaInfo> listaNarudzbiInfo(int stranica, int maxRezultat, int maxNavigationPage) {
        String sql = "Select new " + DetaljiNarudzbe.class.getName()//
                + "(nar.id, nar.datumNarudzbe, nar.brojNarudzbe, nar.iznos, "
                + " nar.imeKupca, nar.kupacAdresa, nar.imeKupca, nar.kupacTel) " + " from "
                + Narudzba.class.getName() + " nar "//
                + " order by nar.orderNum desc";

        Session session = this.sessionFactory.getCurrentSession();
        Query<NarudzbaInfo> query = session.createQuery(sql, NarudzbaInfo.class);
        return new PaginationResult<NarudzbaInfo>(query, stranica, maxRezultat, maxNavigationPage);
    }

    public Narudzba nadiNarudzbu(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.find(Narudzba.class, id);
    }

    public NarudzbaInfo nadiDetaljeNarudzbe(String id) {
        Narudzba narudzba = this.nadiNarudzbu(id);
        if (narudzba == null) {
            return null;
        }
        return new NarudzbaInfo(narudzba.getId(), narudzba.getDatumNarudzbe(), //
                narudzba.getBrNarudzbe(), narudzba.getIznos(), narudzba.getImeKupca(), //
                narudzba.getKupacAdresa(), narudzba.getKupacMail(), narudzba.getKupacTel());
    }

    public List<DetaljiNarudzbeInfo> listaDetaljiNarudzbeInfo(String Id) {
        String sql = "Select new " + DetaljiNarudzbeInfo.class.getName() //
                + "(d.id, d.proizvod.sifra, d.proizvod.naziv , d.kolicina,d.cijena,d.iznos) "//
                + " from " + DetaljiNarudzbe.class.getName() + " d "//
                + " where d.narudzba.id = :id ";

        Session session = this.sessionFactory.getCurrentSession();
        Query<DetaljiNarudzbeInfo> query = session.createQuery(sql, DetaljiNarudzbeInfo.class);
        query.setParameter("Id", Id);

        return query.getResultList();
    }

}
