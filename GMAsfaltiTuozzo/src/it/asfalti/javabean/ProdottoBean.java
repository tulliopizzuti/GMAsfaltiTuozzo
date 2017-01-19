package it.asfalti.javabean;
/**
 * JavaBean contenente le informazioni di un prodotto. 
 * Contiene i metodo getter e setter per prelevare le informazione e impostare i campi
 */
public class ProdottoBean {
    private String id;
    private String descrizione;
    private String misura;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getMisura() {
        return this.misura;
    }

    public void setMisura(String misura) {
        this.misura = misura;
    }

    public ProdottoBean(String id, String descrizione, String misura) {
        this.id = id;
        this.descrizione = descrizione;
        this.misura = misura;
    }
}