package it.asfalti.db;

import java.util.ArrayList;

import it.asfalti.javabean.DisponibilitaBean;
import it.asfalti.javabean.MagazzinoBean;
import it.asfalti.javabean.OperazioneCompletataBean;
import it.asfalti.javabean.OperazioneSospesaBean;
import it.asfalti.javabean.OrdineCaricoBean;
import it.asfalti.javabean.ProdottoBean;
	/**
	 * 
	 * @author tullio
	 *	Interfaccia utilizzata per prelevare le informazioni nel database.
	 */
public interface GetInformation {
	/**
	 * Controlla che l'utente esista nel database
	 * @param username Nome utente	
	 * @param password Password utente
	 * @return Un MagazzinoBean se viene trovato l'utente, null altrimenti
	 */
	public MagazzinoBean checkLogin(String username, String password);
	/**
	 * Preleva le informazioni riguardanti la disponibilità nel magazzino
	 * @param magID Id magazzino
	 * @return un'array contenente le disponibilità, null altrimenti
	 */
	public ArrayList<DisponibilitaBean> getDisponibilita(String magID);
	/**
	 * Preleva le operazioni completate di un magazzino
	 * @param magID id magazzino
	 * @return un'array contenente le operazioni, null altrimenti
	 */
	public ArrayList<OperazioneCompletataBean> getOperazioniComp(String magID);
	/**
	 * Riduce la quantità di merce nella disponibilità del magazzino
	 * @param idM id magazzino
	 * @param idP id prodotto
	 * @param q quantità
	 * @return true se ha successo, false altrimenti
	 */
	public boolean scaricaMerce(String idM,String idP,float q);
	/**
	 * Registra un'operazione di scarico effettuata da un magazzino
	 * @param op OperazioneCompletataBean, contenente tutte le informazioni che tiguardano lo scarico
	 * @return true se ha successo, false altrimenti
	 */
	public boolean registraScarico(OperazioneCompletataBean op);
	/**
	 * Elimina dalla disponibilità del magazzino i prodotti terminati (quantità = 0)
 	 * @param idM id magazzino
	 */
	public void cleanDisp(String idM);
	/**
	 * Legge l'ultima operazione completata
	 * @return id dell'ultima operazione completata
	 */
	public int getUltimaOpCompl();
	/**
	 * Legge l'ultima operazione sospesa
	 * @return id dell'ultima operazione sospesa
	 */
	public int getUltimaOpSosp();
	/**
	 * Rimuove un'operazione sospesa dal database
	 * @param idOp id dell'operazione da rimuovere
	 */
	public void removeOperationSosp(String idOp);
	/**
	 * Aggiunge o rimuove una quantità ad un prodotto nel magazzino
	 * @param operation "+" o "-" in base all'operazione da fare (aggiungere/sottrarre) 
	 * @param idP id prodotto
	 * @param idM id magazzino
	 * @param q quantità
	 */
	public void modifyDisp(String operation,String idP,String idM,float q);
	/**
	 * Preleva le operazioni sospese di un magazzino
	 * @param idM id magazzino
	 * @return un'array contenente le operazioni, null altrimenti
	 */
	public ArrayList<OperazioneSospesaBean> getOperazioniSosp(String idM);
	/**
	 * Registra un'operazione sospesa, rimuovendola dalla tabella alla fine
	 * @param idOp id operazione sospesa
	 * @param idM id magazzino
	 * @return true se ha successo, false altrimenti
	 */
	public boolean registraOpSosp(String idOp,String idM);
	/**
	 * Preleva gli ordini di scarico di un magazzino
	 * @param idM id magazzino
	 * @return un'array contenente gli ordini di scarico, null altrimenti
	 */
	public ArrayList<OperazioneSospesaBean> getOrdiniScarico(String idM);
	/**
	 * Prende l'oridne di scarico da inviare, registra lo scarico e rimuove l'operazione
	 * @param idM id Magazzino
	 * @param da_a id magazzino a cui inviare
	 * @return true se ha successo, false altrimenti
	 */
	public boolean updateDa_aOperation(String idM,String da_a);
	/**
	 * Setta Spedita all'ordine di scarico id
	 * @param id ordine di scarico inviato
	 * @return true se ha successo, false altrimenti
	 */
	public boolean scaricaMerce(String id);
	/**
	 * Legge tutti i prodotti dal database
	 * @return un'array contenente i prodotti, null altrimenti
	 */
	public ArrayList<ProdottoBean> getAllProduct();
	/**
	 * Inserisce una nuova operazione sospesa 
	 * @param op oreazione sospesa da registrare contenente tutti i dati
	 * @return true se ha successo, false altrimenti
	 */
	public boolean insertOpSosp(OperazioneSospesaBean op);

	//FUNZIONI AMMINISTRATORE
	
	/**
	 * Preleva i magazzini dal database, ad esclusione dell'admin, del fornitore e de ìl cliente
	 * @return un'arrat
	 */
	public ArrayList<MagazzinoBean> getAllMag();
	/**
	 * Crea un nuovo prodotto nel database
	 * @param id id nuovo prodotto
	 * @param desc descrizione nuovo prodotto
	 * @param mis misura nuovo prodotto
	 * @return true se l'operazione è andata a buon fine, false altrimenti
	 */
	public boolean creaProd(String id,String desc,String mis);
	/**
	 * Elimina un prodotto dal database
	 * @param id id prodotto
	 * @return true se l'operazione è andata a buon fine, false altrimenti
	 */
	public boolean eliminaProd(String id);
	/**
	 * Ritorna una lista di tutti i prodotti che non sono contenuti in un'operazione
	 * @return un'array contente la lista di prodotti eliminabili
	 */
	public ArrayList<ProdottoBean> getAllProductEliminabile();
	/**
	 * Aggiorna i campi del prodotto
	 * @param id id prodotto
	 * @param value nuovo valore da inserire
	 * @param campo il campo da modificare nella tabella
	 * @return true se l'operazione è andata a buon fine, false altrimenti
	 */
	public boolean updateProd(String id, String value, String campo);
	/**
	 * Crea un nuovo magazzino nel database
	 * @param mag Un bean contenente le informazioni del nuovo magazzino
	 * @return true se l'operazione è andata a buon fine, false altrimenti
	 */
	public boolean creaMag(MagazzinoBean mag);
	/**
	 * Aggiorna i campi del magazzino
	 * @param id id magazzino
	 * @param value nuovo valore da inserire
	 * @param campo il campo da modificare nella tabella
	 * @return true se l'operazione è andata a buon fine, false altrimenti
	 */
	public boolean updateMag(String id, String value, String campo);
	/**
	 * Elimina un magazzino dal database
	 * @param idM id magazzino da eliminare
	 * @return true se l'operazione è andata a buon fine, false altrimenti
	 */
	public boolean eliminaMag(String idM);
	/**
	 * Preleva dal database tutti gli ordini che riguardano il fornitore 
 	 * @return un'array contente la lista degli ordini del fornitore
	 */
	public ArrayList<OperazioneSospesaBean> getAllOrdForn();
	/**
	 * Aggiorna lo stato dell'operazione del fornitore impostandolo a spedita 
	 * @param idOp id dell'operazione
	 * @return true se l'operazione è andata a buon fine, false altrimenti
	 */
	public boolean merceFornSpedita(String idOp);
	/**
	 * Preleva tutti gli oridni di carico dal database
	 * @return un'array contente la lista degli ordini di carico 
	 */
	public ArrayList<OrdineCaricoBean> getAllOrdCar();
	/**
	 * Registra nel database lo scarico al magazzino interessato
	 * @param ops operazione sospesa da registrare
	 * @param oldOp la vecchia operazione da eliminare
	 * @return true se l'operazione è andata a buon fine, false altrimenti
	 */
	public boolean inviaScarichi(ArrayList<OperazioneSospesaBean> ops,String oldOp);
}
