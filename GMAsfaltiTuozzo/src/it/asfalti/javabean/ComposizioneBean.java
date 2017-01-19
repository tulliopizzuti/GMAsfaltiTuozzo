package it.asfalti.javabean;

import it.asfalti.javabean.ProdottoBean;
/**
 * 
 * @author tullio
 * Un oggetto che rappresenta una composizione in un ordine, ovvero il prodotto e la sua quantità, riferita ad un'operazione.
 * Contiene i metodo getter e setter per prelevare le informazione e impostare i campi
 */
public class ComposizioneBean {
    private String idOperazione;
    private float quantita;
    private ProdottoBean prodotto;

    public ComposizioneBean(String idOperazione, float quantita, ProdottoBean prodotto) {
        this.idOperazione = idOperazione;
        this.quantita = quantita;
        this.prodotto = prodotto;
    }

    public String getIdOperazione() {
        return this.idOperazione;
    }

    public void setIdOperazione(String idOperazione) {
        this.idOperazione = idOperazione;
    }

    public float getQuantita() {
        return this.quantita;
    }

    public void setQuantita(float quantita) {
        this.quantita = quantita;
    }

    public ProdottoBean getProdotto() {
        return this.prodotto;
    }

    public void setProdotto(ProdottoBean prodotto) {
        this.prodotto = prodotto;
    }
}