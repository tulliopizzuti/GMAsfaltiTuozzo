package it.asfalti.javabean;

import it.asfalti.javabean.ComposizioneBean;
import it.asfalti.javabean.OperazioneCompletataBean;
import java.util.ArrayList;
import java.sql.Date;
/**
 * Rappresenta un'operazione sospesa nel database, contiene anche la relativa composizione.
 * E' una sottoclasse di Operazione completata bean, in quanto si differenziano solo per il campo aggiuntivo stato, contenuto nella tabella operazioni sospese
 * Contiene i metodo getter e setter per prelevare le informazione e impostare i campi
 */
public class OperazioneSospesaBean
extends OperazioneCompletataBean {
    private String stato;

    public String getStato() {
        return this.stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public OperazioneSospesaBean(String idOp, String idM, String tipo, String stato, String da_a, Date data, ArrayList<ComposizioneBean> listaProdotti) {
        super(idOp, idM, tipo, data, da_a, listaProdotti);
        this.stato = stato;
    }
}