package it.asfalti.db;

import java.util.ArrayList;

import it.asfalti.javabean.DisponibilitaBean;
import it.asfalti.javabean.MagazzinoBean;
import it.asfalti.javabean.OperazioneCompletataBean;
import it.asfalti.javabean.OperazioneSospesaBean;
import it.asfalti.javabean.ProdottoBean;

public interface GetInformation {
	public MagazzinoBean checkLogin(String username, String password);
	public ArrayList<DisponibilitaBean> getDisponibilita(String magID);
	public ArrayList<OperazioneCompletataBean> getOperazioniComp(String magID);
	public boolean scaricaMerce(String idM,String idP,float q);
	public boolean registraScarico(OperazioneCompletataBean op);
	public void cleanDisp(String idM);
	public int getUltimaOpCompl();
	public int getUltimaOpSosp();
	public void removeOperationSosp(String idOp);
	public void modifyDisp(String operation,String idP,String idM,float q);
	public ArrayList<OperazioneSospesaBean> getOperazioniSosp(String idM);
	public boolean registraOpSosp(String idOp,String idM);
	public ArrayList<OperazioneSospesaBean> getOrdiniScarico(String idM);
	public boolean updateDa_aOperation(String idM,String da_a);
	public boolean scaricaMerce(String id);
	public ArrayList<ProdottoBean> getAllProduct();
	public boolean insertOpSosp(OperazioneSospesaBean op);

	//FUNZIONI AMMINISTRATORE
	
	public ArrayList<MagazzinoBean> getAllMag();
	public boolean creaProd(String id,String desc,String mis);
	public boolean eliminaProd(String id);
	public ArrayList<ProdottoBean> getAllProductEliminabile();
}
