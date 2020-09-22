package lidija.onlinerasadnik.model;

import lidija.onlinerasadnik.form.KupacForma;
import lidija.onlinerasadnik.form.KupacForma;

public class KupacInfo {

    private String ime;
    private String adresa;
    private String mail;
    private String tel;

    private boolean jeOK;

    public KupacInfo() {

    }

    public KupacInfo(KupacForma kupacForma) {
        this.ime = kupacForma.getIme();
        this.adresa = kupacForma.getAdresa();
        this.mail = kupacForma.getMail();
        this.tel = kupacForma.getTel();
        this.jeOK = kupacForma.jeOK();
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAddress(String address) {
        this.adresa = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public boolean jeOk() {
        return jeOK;
    }

    public void setJeOk(boolean jeOK) {
        this.jeOK = jeOK;
    }

}
