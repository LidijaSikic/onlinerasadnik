package lidija.onlinerasadnik.form;

import lidija.onlinerasadnik.model.KupacInfo;

public class KupacForma {

    private String ime;
    private String adresa;
    private String mail;
    private String tel;

    private boolean jeOK;

    public KupacForma() {

    }

    public KupacForma(KupacInfo kupacInfo) {
        if (kupacInfo != null) {
            this.ime = kupacInfo.getIme();
            this.adresa = kupacInfo.getAdresa();
            this.mail = kupacInfo.getMail();
            this.tel = kupacInfo.getTel();
        }
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

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public boolean jeOK() {
        return jeOK;
    }

    public void setJeOK(boolean jeOK) {
        this.jeOK = jeOK;
    }

}
