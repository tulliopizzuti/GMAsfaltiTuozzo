package it.asfalti.javabean;
/**
 * Rappresenta una riga della tabella disponibilità nel database.
 * Contiene i metodo getter e setter per prelevare le informazione e impostare i campi.
 * 
 */
public class DisponibilitaBean {
	private ProdottoBean prod;
	private float q;
	public ProdottoBean getProd() {
		return prod;
	}
	public void setProd(ProdottoBean prod) {
		this.prod = prod;
	}
	public float getQ() {
		return q;
	}
	public void setQ(float q) {
		this.q = q;
	}
	public DisponibilitaBean(ProdottoBean prod, float q) {
		super();
		this.prod = prod;
		this.q = q;
	}
	


}
