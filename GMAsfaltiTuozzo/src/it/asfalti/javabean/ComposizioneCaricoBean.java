package it.asfalti.javabean;

import java.util.ArrayList;

public class ComposizioneCaricoBean extends ComposizioneBean {
	private ArrayList<MagazzinoBean> mags;
	public ComposizioneCaricoBean(String idOperazione, float quantita, ProdottoBean prodotto, ArrayList<MagazzinoBean> mags) {
		super(idOperazione, quantita, prodotto);
		this.mags=mags;
	}
	public ArrayList<MagazzinoBean> getMags() {
		return mags;
	}
	public void setMags(ArrayList<MagazzinoBean> mags) {
		this.mags = mags;
	}

}
