package it.asfalti.db;

import java.util.ArrayList;

import it.asfalti.javabean.DisponibilitaBean;
import it.asfalti.javabean.MagazzinoBean;
import it.asfalti.javabean.OperazioneCompletataBean;

public interface GetInformation {
	public MagazzinoBean checkLogin(String username, String password);
	public ArrayList<DisponibilitaBean> getDisponibilita(String magID);
	public ArrayList<OperazioneCompletataBean> getOperazioniComp(String magID);
	public boolean scaricaMerce(String idM,String idP,float q);
	public boolean registraScarico(OperazioneCompletataBean op);
	public void cleanDisp(String idM);
}
