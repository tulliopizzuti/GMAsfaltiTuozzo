package it.asfalti.javabean;

import java.sql.Date;
import java.util.ArrayList;
/**
 * Simile ad OperazioneCompletataBean, si differenzia nella composizione, in quanto i prodotti possono essere inviati da diversi magazzini
 * Contiene i metodo getter e setter per prelevare le informazione e impostare i campi
 */

public class OrdineCaricoBean  {
	private String idOp;
    private String idM;
    private String tipo;
    private Date data;
    private String da_a;
    private ArrayList<ComposizioneCaricoBean> compCar;
	private String stato;
    
    public OrdineCaricoBean(String idOp, String idM, String tipo, Date data, String da_a,
			ArrayList<ComposizioneCaricoBean> compCar, String stato) {
		super();
		this.idOp = idOp;
		this.idM = idM;
		this.tipo = tipo;
		this.data = data;
		this.da_a = da_a;
		this.compCar = compCar;
		this.stato = stato;
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

    public ArrayList<ComposizioneCaricoBean> getListaProdotti() {
        return this.compCar;
    }

    public void setListaProdotti(ArrayList<ComposizioneCaricoBean> listaProdotti) {
        this.compCar = listaProdotti;
    }

    public void addProdotto(ComposizioneCaricoBean toAdd) {
        this.compCar.add(toAdd);
    }

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public ArrayList<ComposizioneCaricoBean> getCompCar() {
		return compCar;
	}

	public void setCompCar(ArrayList<ComposizioneCaricoBean> compCar) {
		this.compCar = compCar;
	}
}
