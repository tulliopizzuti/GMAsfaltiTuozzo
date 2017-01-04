package it.asfalti.javabean;

public class MagazzinoBean {
    private String idM;
    private String descrizione;
    private String citta;
    private String via;
    private String cap;
    private String nCivico;
    private String password;
    private String tipo;

    public MagazzinoBean(String idM, String descrizione, String citta, String via, String cap, String nCivico, String password,String tipo) {
        this.idM = idM;
        this.descrizione = descrizione;
        this.citta = citta;
        this.via = via;
        this.cap = cap;
        this.nCivico = nCivico;
        this.password = password;
        this.tipo=tipo;
    }

    public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdM() {
        return this.idM;
    }

    public void setIdM(String idM) {
        this.idM = idM;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCitta() {
        return this.citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getVia() {
        return this.via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCap() {
        return this.cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getnCivico() {
        return this.nCivico;
    }

    public void setnCivico(String nCivico) {
        this.nCivico = nCivico;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}