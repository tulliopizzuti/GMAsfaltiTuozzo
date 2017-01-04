package it.asflati.javabean;

import it.asflati.javabean.ComposizioneBean;
import it.asflati.javabean.OperazioneCompletataBean;
import java.util.ArrayList;
import java.util.Date;

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