package it.asfalti.db;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



import it.asfalti.javabean.ComposizioneBean;
import it.asfalti.javabean.ComposizioneCaricoBean;
import it.asfalti.javabean.DisponibilitaBean;
import it.asfalti.javabean.MagazzinoBean;
import it.asfalti.javabean.OperazioneCompletataBean;
import it.asfalti.javabean.OperazioneSospesaBean;
import it.asfalti.javabean.OrdineCaricoBean;
import it.asfalti.javabean.ProdottoBean;

public class DBInformation implements GetInformation {
	
	@Override
	public synchronized MagazzinoBean checkLogin(String username, String password) {
		Connection connection=null;
		MagazzinoBean user=null;
		String sql="select * from magazzino where idM=? and passwordM=?";
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			if(rs!=null && rs.next()){
				user=new MagazzinoBean(rs.getString("idM"),
						rs.getString("descrizioneM"),
						rs.getString("citta"),
						rs.getString("via"),
						rs.getString("cap"),
						rs.getString("nCivico"),
						rs.getString("passwordM"),
						rs.getString("tipo")
						);
			}
			rs.close();
			ps.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}
		return user;
	}

	@Override
	public synchronized ArrayList<DisponibilitaBean> getDisponibilita(String magID) {
		Connection connection=null;
		ArrayList<DisponibilitaBean> disp=null;
		String sql="select prodotto.idProduct,unitaDiMisura,descrizioneP,quantita"
				+ " from prodotto,disponibilita"
				+ " where idM=?"
				+ " and prodotto.idProduct=disponibilita.idProduct;";
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1, magID);
			ResultSet rs=ps.executeQuery();
			if(rs!=null){
				disp=new ArrayList<DisponibilitaBean>();
				while(rs.next()){
					disp.add(new DisponibilitaBean(
							new ProdottoBean(rs.getString("idProduct"),
									rs.getString("descrizioneP"),
									rs.getString("unitaDiMisura"))
							,rs.getFloat("quantita")));
				}
			}
			rs.close();
			ps.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}
		
		
		return disp;
	}

	@Override
	public synchronized ArrayList<OperazioneCompletataBean> getOperazioniComp(String magID) {	
		ArrayList<OperazioneCompletataBean> op=null;
		Connection connection=null;
		
		String selectOp="select * from operazionicompletate where  operazionicompletate.idM=?;";
		String selectComp= "select * from composizioneopcompl,prodotto"
				+ " where  idOperazione=? and idOperazione=composizioneopcompl.idOperazione "
				+ "and composizioneopcompl.idProduct=prodotto.idProduct;";
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(selectOp);
			ps.setString(1, magID);
			ResultSet rs=ps.executeQuery();
			if(rs!=null){
				op=new ArrayList<OperazioneCompletataBean>();
				while(rs.next()){
					op.add(new OperazioneCompletataBean(rs.getString("idOperazione"),
							rs.getString("idM"),
							rs.getString("tipo"),
							rs.getDate("data"), 
							rs.getString("da_a"), 
							null));
				}
			}
			ps=connection.prepareStatement(selectComp);
			for(OperazioneCompletataBean operation: op){
				ps.setString(1, operation.getIdOp());
				rs=ps.executeQuery();
				if(rs!=null){
					ArrayList<ComposizioneBean> comp=new ArrayList<ComposizioneBean>();
					while(rs.next()){
					comp.add(new ComposizioneBean(rs.getString("idOperazione"),
							rs.getFloat("quantita"),
							new ProdottoBean(rs.getString("idProduct"),
									rs.getString("descrizioneP"),
									rs.getString("unitaDiMisura"))));
					}
					operation.setListaProdotti(comp);
				}
				
			}
			rs.close();
			ps.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		return op;
	}

	@Override
	public synchronized boolean scaricaMerce(String idM, String idP, float q) {
		Connection connection=null;
		String sql="update disponibilita set quantita=quantita-? "
				+ "where idM=? and idProduct=?;";
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(2, idM);
			ps.setFloat(1, q);
			ps.setString(3, idP);
			ps.executeUpdate();
			connection.commit();
			ps.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
			return false;
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		
		return true;
	}
	
	@Override
	public synchronized boolean registraScarico(OperazioneCompletataBean op) {
		String registraOp= "insert into operazioniCompletate(idM,tipo,data,da_a) values (?,?,?,?); ";
		String ultimaOp="select max(idOperazione) as max from operazionicompletate;";
		String registraComp="insert into composizioneOpCompl values(?,?,?);";
		Connection connection=null;
		Date data=new Date(System.currentTimeMillis());
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(registraOp);
			ps.setString(1, op.getIdM());
			ps.setString(2, "Scarico");
			ps.setDate(3, data);
			ps.setString(4, op.getDa_a());
			ps.executeUpdate();
			ps=connection.prepareStatement(ultimaOp);
			ResultSet rs =ps.executeQuery();
			Integer max=null;
			if(rs!=null && rs.next())
				max=rs.getInt("max");
			ArrayList<ComposizioneBean> composizione=op.getListaProdotti();
			for(ComposizioneBean c:composizione){
				ps=connection.prepareStatement(registraComp);
				ps.setInt(1,max);
				ps.setString(2, c.getProdotto().getId());
				ps.setFloat(3, c.getQuantita());
				ps.executeUpdate();
				scaricaMerce( op.getIdM(), 
						c.getProdotto().getId(), 
						c.getQuantita());
			}
			connection.commit();
			ps.close();
			cleanDisp(op.getIdM());
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
			return false;
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		return true;
	}

	@Override
	public synchronized void cleanDisp(String idM) {
		String sql="delete from disponibilita where idM=? and quantita=0";
		Connection connection=null;
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1, idM);
			ps.executeUpdate();
			connection.commit();
			ps.close();
			
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
	}

	@Override
	public synchronized ArrayList<OperazioneSospesaBean> getOperazioniSosp(String idM) {
		ArrayList<OperazioneSospesaBean> op=null;
		Connection connection=null;
		
		String selectOp="select * from operazioniInSospeso where  operazioniInSospeso.idM=? and tipo='Carico';";
		String selectComp= "select * from composizioneOpSosp,prodotto"
				+ " where  idOperazione=? and idOperazione=composizioneopsosp.idOperazione "
				+ "and composizioneopsosp.idProduct=prodotto.idProduct;";
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(selectOp);
			ps.setString(1, idM);
			ResultSet rs=ps.executeQuery();
			if(rs!=null){
				op=new ArrayList<OperazioneSospesaBean>();
				while(rs.next()){
					op.add(new OperazioneSospesaBean(rs.getString("idOperazione"),
							rs.getString("idM"),
							rs.getString("tipo"),
							rs.getString("stato"),
							rs.getString("da_a"),
							rs.getDate("data"), 
							null));
				}
			}
			ps=connection.prepareStatement(selectComp);
			for(OperazioneCompletataBean operation: op){
				ps.setString(1, operation.getIdOp());
				rs=ps.executeQuery();
				if(rs!=null){
					ArrayList<ComposizioneBean> comp=new ArrayList<ComposizioneBean>();
					while(rs.next()){
					comp.add(new ComposizioneBean(rs.getString("idOperazione"),
							rs.getFloat("quantita"),
							new ProdottoBean(rs.getString("idProduct"),
									rs.getString("descrizioneP"),
									rs.getString("unitaDiMisura"))));
					}
					operation.setListaProdotti(comp);
				}
			}
			rs.close();
			ps.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		return op;
	}

	@Override
	public synchronized boolean registraOpSosp(String idOp,String idM) {
		Connection connection=null;
		ArrayList<OperazioneSospesaBean> operations=getOperazioniSosp(idM);
		OperazioneSospesaBean operation=null;
		for(OperazioneSospesaBean o:operations)
			if(o.getIdOp().equals(idOp)){
				operation=o;
				break;
			}
		if(operation==null) return false;
		String insertOpC="insert into operazioniCompletate(idM,tipo,data,da_a) values (?,1,?,?);";
		String insertOpCompos="insert into composizioneOpCompl values(?,?,?);";

		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(insertOpC);
			ps.setString(1, idM);
			ps.setDate(2, new Date(System.currentTimeMillis()));
			ps.setString(3, operation.getDa_a());
			ps.executeUpdate();
			ps=connection.prepareStatement(insertOpCompos);
			int max=getUltimaOpCompl()+1;
			for(ComposizioneBean c:operation.getListaProdotti()){
				ps.setInt(1, max);
				ps.setString(2, c.getProdotto().getId());
				ps.setFloat(3, c.getQuantita());
				ps.executeUpdate();
				modifyDisp("+", c.getProdotto().getId(), idM, c.getQuantita());
			}
			removeOperationSosp(idOp);
			
			updateDa_aOperation(operation.getDa_a(), idM);
		
			connection.commit();
			ps.close();
			return true;
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
			return false;
		}
		finally{
			try{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}	
		
	}
	
	@Override
	public synchronized int getUltimaOpCompl() {
		String ultimaOp="select max(idOperazione) as max from operazionicompletate;";
		Connection connection=null;
		
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(ultimaOp);
			ResultSet rs =ps.executeQuery();
			Integer max=null;
			if(rs!=null && rs.next())
				max=rs.getInt("max");
			if(max!=null)
				return max;
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}
		return 0;		
	}

	@Override
	public synchronized void removeOperationSosp(String idOp) {
		String sql="delete from operazioniInSospeso where idOperazione=?";
		Connection connection=null;
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1,idOp);
			ps.executeUpdate();
			connection.commit();
			ps.close();
			
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		
	}

	@Override
	public synchronized void modifyDisp(String operation, String idP, String idM, float q) {
		String sql="update disponibilita set quantita=quantita"+operation+"? "
				+ "where idM=? and idProduct=?;";
		Connection connection=null;
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(2, idM);
			ps.setFloat(1, q);
			ps.setString(3, idP);
			ps.executeUpdate();
			connection.commit();
			ps.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		
		}
		finally{
			try{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
	}

	@Override
	public synchronized ArrayList<OperazioneSospesaBean> getOrdiniScarico(String idM) {
		ArrayList<OperazioneSospesaBean> op=null;
		Connection connection=null;
		
		String selectOp="select * from operazioniInSospeso where  operazioniInSospeso.idM=? and tipo='Scarico';";
		String selectComp= "select * from composizioneOpSosp,prodotto"
				+ " where  idOperazione=? and idOperazione=composizioneopsosp.idOperazione "
				+ "and composizioneopsosp.idProduct=prodotto.idProduct;";
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(selectOp);
			ps.setString(1, idM);
			ResultSet rs=ps.executeQuery();
			if(rs!=null){
				op=new ArrayList<OperazioneSospesaBean>();
				while(rs.next()){
					op.add(new OperazioneSospesaBean(rs.getString("idOperazione"),
							rs.getString("idM"),
							rs.getString("tipo"),
							rs.getString("stato"),
							rs.getString("da_a"),
							rs.getDate("data"), 
							null));
				}
			}
			ps=connection.prepareStatement(selectComp);
			for(OperazioneCompletataBean operation: op){
				ps.setString(1, operation.getIdOp());
				rs=ps.executeQuery();
				if(rs!=null){
					ArrayList<ComposizioneBean> comp=new ArrayList<ComposizioneBean>();
					while(rs.next()){
					comp.add(new ComposizioneBean(rs.getString("idOperazione"),
							rs.getFloat("quantita"),
							new ProdottoBean(rs.getString("idProduct"),
									rs.getString("descrizioneP"),
									rs.getString("unitaDiMisura"))));
					}
					operation.setListaProdotti(comp);
				}
			}
			rs.close();
			ps.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		return op;
	}

	@Override
	public synchronized boolean updateDa_aOperation(String idM,String da_a) {
		ArrayList<OperazioneSospesaBean> operations= getOrdiniScarico(idM);
		OperazioneSospesaBean operation=null;
		for(OperazioneSospesaBean o:operations){
			if(o.getDa_a().equals(da_a)){
				operation=o;
				break;
			}
		}
		if(operation==null) return false;
		registraScarico(operation);
		removeOperationSosp(operation.getIdOp());
		return true;

	}

	@Override
	public synchronized boolean scaricaMerce(String id) {
		Connection connection=null;
		String st="update operazioniinsospeso set stato=2 where idOperazione=? or idOperazione=?;";
		
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(st);
			ps.setInt(1,Integer.parseInt(id));
			ps.setInt(2, Integer.parseInt(id)+1);
			ps.executeUpdate();
			connection.commit();
			ps.close();			
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
			return false;
		}
		finally{
			try{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		return true;
	}

	@Override
	public synchronized ArrayList<ProdottoBean> getAllProduct() {
		ArrayList<ProdottoBean> prod=null;
		Connection connection=null;
		String sql="select * from prodotto;";
		try{
			connection=DriverManagerConnectionPool.getConnection();
			Statement st=connection.createStatement();
			ResultSet rs=st.executeQuery(sql);
			if(rs!=null){
				prod=new ArrayList<ProdottoBean>();
				while(rs.next()){
					prod.add(new ProdottoBean(rs.getString("idProduct"),
							rs.getString("descrizioneP"), 
							rs.getString("unitaDiMisura")));
				}
			}
			rs.close();
			st.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		return prod;
	}

	@Override
	public synchronized boolean insertOpSosp(OperazioneSospesaBean op) {
		Connection connection=null;
		String insertOpSosp="insert into operazioniInSospeso(idM,tipo,stato,data,da_a) values (?,1,1,?,'admin0');";
		String insertOpCompos="insert into composizioneOpSosp values(?,?,?);";
		
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(insertOpSosp);
			ps.setString(1, op.getIdM());
			ps.setDate(2, new Date(System.currentTimeMillis()));
			ps.executeUpdate();
			connection.commit();

			int max=getUltimaOpSosp();
			ps=connection.prepareStatement(insertOpCompos);
			for(ComposizioneBean c:op.getListaProdotti()){
				ps.setInt(1, max);
				ps.setString(2, c.getProdotto().getId());
				ps.setFloat(3, c.getQuantita());
				ps.executeUpdate();
			}
			connection.commit();
			ps.close();
			return true;
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
			return false;
		}
		finally{
			try{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}	
	}

	@Override
	public synchronized int getUltimaOpSosp() {
		String ultimaOp="select max(idOperazione) as max from operazioniInSospeso;";
		Connection connection=null;
		
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(ultimaOp);
			ResultSet rs =ps.executeQuery();
			Integer max=null;
			if(rs!=null && rs.next())
				max=rs.getInt("max");
			if(max!=null)
				return max;
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}
		return 0;
	}


	//FUNZIONI AMMINISTRATORE
	
	
	@Override
	public synchronized ArrayList<MagazzinoBean> getAllMag() {
		String sql="select * from Magazzino where not(idM='admin0' or idM='client' or idM='fornit');";
		ArrayList<MagazzinoBean> mags=null;
		Connection connection=null;
		try{
			connection=DriverManagerConnectionPool.getConnection();
			Statement st=connection.createStatement();
			ResultSet rs =st.executeQuery(sql);
			
			if(rs!=null){
				mags=new ArrayList<MagazzinoBean>();
				while(rs.next()){
					mags.add(new MagazzinoBean(rs.getString("idM"),
							rs.getString("descrizioneM"),
							rs.getString("citta"),
							rs.getString("via"), 
							rs.getString("cap"), 
							rs.getString("nCivico"), 
							rs.getString("passwordM"),
							rs.getString("tipo")));
				}
			}
			
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}
		return mags;
	}

	@Override
	public synchronized boolean creaProd(String id, String desc, String mis) {
		String sql= "insert into prodotto values(?,?,?);";
		Connection connection=null;
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2,mis);
			ps.setString(3,desc);
			ps.executeUpdate();
			connection.commit();
			ps.close();
			
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
			return false;
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		return true;
	}

	@Override
	public synchronized boolean eliminaProd(String id) {
		String sql="delete from prodotto where idProduct=?";
		Connection connection=null;
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();
			connection.commit();
			ps.close();
			return true;
			
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
			return false;
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
	}

	@Override
	public synchronized ArrayList<ProdottoBean> getAllProductEliminabile() {
		ArrayList<ProdottoBean> prod=null;
		Connection connection=null;
		String sql="SELECT * FROM gmasfalti.prodotto where idProduct not in (SELECT distinct idProduct from gmasfalti.composizioneopcompl);";
		try{
			connection=DriverManagerConnectionPool.getConnection();
			Statement st=connection.createStatement();
			ResultSet rs=st.executeQuery(sql);
			if(rs!=null){
				prod=new ArrayList<ProdottoBean>();
				while(rs.next()){
					prod.add(new ProdottoBean(rs.getString("idProduct"),
							rs.getString("descrizioneP"), 
							rs.getString("unitaDiMisura")));
				}
			}
			rs.close();
			st.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		return prod;
	}

	@Override
	public synchronized boolean updateProd(String id, String value, String campo) {
		Connection connection=null;
		String sql="update prodotto set "+campo+"=? "
				+ "where idProduct=?;";
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1, value);
			ps.setString(2,id);
			ps.executeUpdate();
			connection.commit();
			ps.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
			return false;
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		
		return true;
	}

	@Override
	public synchronized boolean creaMag(MagazzinoBean mag) {
		String sql="insert into magazzino values (?,?,?, ?,?,?,?,'mag');";
		Connection connection=null;
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1, mag.getIdM());
			ps.setString(2,mag.getDescrizione());
			ps.setString(3, mag.getCitta());
			ps.setString(4, mag.getVia());
			ps.setString(5, mag.getCap());
			ps.setString(6, mag.getnCivico());
			ps.setString(7, mag.getPassword());
			ps.executeUpdate();
			connection.commit();
			ps.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
			return false;
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		
		return true;
	}

	@Override
	public synchronized boolean updateMag(String id, String value, String campo) {
		Connection connection=null;
		String sql="update magazzino set "+campo+"=? "
				+ "where idM=?;";
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1, value);
			ps.setString(2,id);
			ps.executeUpdate();
			connection.commit();
			ps.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
			return false;
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		
		return true;
	}

	@Override
	public synchronized boolean eliminaMag(String idM) {
		String sql="delete from magazzino where idM=?";
		Connection connection=null;
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1, idM);
			ps.executeUpdate();
			connection.commit();
			ps.close();
			return true;
			
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
			return false;
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}
	}

	@Override
	public synchronized ArrayList<OperazioneSospesaBean> getAllOrdForn() {
		ArrayList<OperazioneSospesaBean> op=null;
		Connection connection=null;
		
		String selectOp="select * from operazioniInSospeso where  operazioniInSospeso.idM='fornit' and tipo='Scarico';";
		String selectComp= "select * from composizioneOpSosp,prodotto"
				+ " where  idOperazione=? and idOperazione=composizioneopsosp.idOperazione "
				+ "and composizioneopsosp.idProduct=prodotto.idProduct;";
		try{
			connection=DriverManagerConnectionPool.getConnection();
			Statement st=connection.createStatement();
			ResultSet rs=st.executeQuery(selectOp);
			if(rs!=null){
				op=new ArrayList<OperazioneSospesaBean>();
				while(rs.next()){
					op.add(new OperazioneSospesaBean(rs.getString("idOperazione"),
							rs.getString("idM"),
							rs.getString("tipo"),
							rs.getString("stato"),
							rs.getString("da_a"),
							rs.getDate("data"), 
							null));
				}
			}
			PreparedStatement ps=connection.prepareStatement(selectComp);
			for(OperazioneCompletataBean operation: op){
				ps.setString(1, operation.getIdOp());
				rs=ps.executeQuery();
				if(rs!=null){
					ArrayList<ComposizioneBean> comp=new ArrayList<ComposizioneBean>();
					while(rs.next()){
					comp.add(new ComposizioneBean(rs.getString("idOperazione"),
							rs.getFloat("quantita"),
							new ProdottoBean(rs.getString("idProduct"),
									rs.getString("descrizioneP"),
									rs.getString("unitaDiMisura"))));
					}
					operation.setListaProdotti(comp);
				}
			}
			rs.close();
			ps.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		return op;
	}

	@Override
	public synchronized boolean merceFornSpedita(String idOp) {
		String sql="update operazioniInsospeso set stato=2 where idM='fornit' and tipo='Scarico'"
				+" and idOperazione=?;";
		String sql2="update operazioniInsospeso set stato=2 where"
				+" idOperazione=?;";
		Connection connection=null;
		try{
			connection=DriverManagerConnectionPool.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1, idOp);
			ps.executeUpdate();
			ps=connection.prepareStatement(sql2);
			ps.setString(1, String.valueOf(Integer.parseInt(idOp)+1));
			ps.executeUpdate();
			connection.commit();
			ps.close();
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
			return false;
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		
		return true;
	
	
	}

	@Override
	public synchronized ArrayList<OrdineCaricoBean> getAllOrdCar() {
		String getOrds="select * from operazioniinsospeso where da_a='admin0';";
		String getCompOrd="select * from composizioneopsosp where  idOperazione=?;";
		String getMagsDisp="select distinct(idM) from composizioneopsosp,disponibilita"
				+ " where composizioneopsosp.idOperazione=?"
				+ " and composizioneopsosp.idProduct=? "
				+ "and composizioneopsosp.idProduct=disponibilita.idProduct "
				+ "and disponibilita.quantita>composizioneopsosp.quantita;";
		Connection connection=null;
		ArrayList<OrdineCaricoBean> ops=null;
		try{
			connection=DriverManagerConnectionPool.getConnection();
			Statement st=connection.createStatement();
			ResultSet allOpRs=st.executeQuery(getOrds);
			
			if(allOpRs!=null){
				ops=new ArrayList<OrdineCaricoBean>();
				while(allOpRs.next()){
					String idOperazione=allOpRs.getString("idOperazione");
					ops.add(new OrdineCaricoBean(idOperazione,
							allOpRs.getString("idM"), 
							allOpRs.getString("tipo"),
							allOpRs.getDate("data"),
							allOpRs.getString("da_a"),
							new ArrayList<ComposizioneCaricoBean>(),
							allOpRs.getString("stato")));
				}
			}
			allOpRs.close();
			st.close();
			for(OrdineCaricoBean or:ops){
				PreparedStatement psComp=connection.prepareStatement(getCompOrd);
				psComp.setString(1, or.getIdOp());
				ResultSet rs=psComp.executeQuery();
				if(rs!=null){
					while(rs.next()){
						or.getListaProdotti().add(
								new ComposizioneCaricoBean(rs.getString("idOperazione"),
										rs.getFloat("quantita"),
										new ProdottoBean(
												rs.getString("idProduct"),
												"",
												""),
										new ArrayList<MagazzinoBean>())
								);
					}
				}
				rs.close();
				psComp.close();
			}
			
			for(OrdineCaricoBean or:ops){
				for(ComposizioneCaricoBean p:or.getListaProdotti()){
					PreparedStatement psMag=connection.prepareStatement(getMagsDisp);
					psMag.setString(1,or.getIdOp());
					psMag.setString(2, p.getProdotto().getId());
					ResultSet rsMag=psMag.executeQuery();
					p.getMags().add(new MagazzinoBean("fornit",
							"", "", "", "", "", "", ""));
					if(rsMag!=null){
						while(rsMag.next()){
							p.getMags().add(new MagazzinoBean(rsMag.getString("idM"),
									"", "", "", "", "", "", ""));
						}
					}
						
					rsMag.close();
					psMag.close();
				}
				
			}
		}
		catch(SQLException e){
			System.out.println("SQLError: "+e.getMessage());
		}
		finally{
			try{
			
				DriverManagerConnectionPool.releaseConnection(connection);
			}
			catch(SQLException e){
				System.out.println("SQLError: "+e.getMessage());
			}
		}		
		return ops;
	}





}
