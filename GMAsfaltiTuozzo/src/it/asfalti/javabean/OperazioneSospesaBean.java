package it.asfalti.javabean;

import it.asfalti.javabean.ComposizioneBean;
import it.asfalti.javabean.OperazioneCompletataBean;
import java.util.ArrayList;
import java.sql.Date;

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