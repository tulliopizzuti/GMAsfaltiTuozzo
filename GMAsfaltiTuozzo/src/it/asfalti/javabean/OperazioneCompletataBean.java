package it.asfalti.javabean;

import it.asfalti.javabean.ComposizioneBean;

import java.sql.Date;
import java.util.ArrayList;


public class OperazioneCompletataBean {
    private String idOp;
    private String idM;
    private String tipo;
    private Date data;
    private String da_a;
    private ArrayList<ComposizioneBean> listaProdotti = new ArrayList<ComposizioneBean>();

    public OperazioneCompletataBean(String idOp, String idM, String tipo, Date data, String da_a, ArrayList<ComposizioneBean> listaProdotti) {
        this.idOp = idOp;
        this.idM = idM;
        this.tipo = tipo;
        this.data = data;
        this.da_a = da_a;
        this.listaProdotti = listaProdotti;
    }

    public String getIdOp() {
        return this.idOp;
    }

    public void setIdOp(String idOp) {
        this.idOp = idOp;
    }

    public String getIdM() {
        return this.idM;
    }

    public void setIdM(String idM) {
        this.idM = idM;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDa_a() {
        return this.da_a;
    }

    public void setDa_a(String da_a) {
        this.da_a = da_a;
    }

    public ArrayList<ComposizioneBean> getListaProdotti() {
        return this.listaProdotti;
    }

    public void setListaProdotti(ArrayList<ComposizioneBean> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }

    public void addProdotto(ComposizioneBean toAdd) {
        this.listaProdotti.add(toAdd);
    }
}