package it.asfalti.db;

import java.util.ArrayList;

import it.asfalti.javabean.DisponibilitaBean;
import it.asfalti.javabean.MagazzinoBean;
import it.asfalti.javabean.OperazioneCompletataBean;

public interface GetInformation {
	public MagazzinoBean checkLogin(String username, String password);
	public ArrayList<DisponibilitaBean> getDisponibilita(String magID);
	public ArrayList<OperazioneCompletataBean> getOperazione(String magID);
}
