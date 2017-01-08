package it.asfalti.db;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.asfalti.javabean.ComposizioneBean;
import it.asfalti.javabean.DisponibilitaBean;
import it.asfalti.javabean.MagazzinoBean;
import it.asfalti.javabean.OperazioneCompletataBean;
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
	public ArrayList<DisponibilitaBean> getDisponibilita(String magID) {
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
	public ArrayList<OperazioneCompletataBean> getOperazioniComp(String magID) {		ArrayList<OperazioneCompletataBean> op=null;
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
	public boolean scaricaMerce(String idM, String idP, float q) {
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
	public boolean registraScarico(OperazioneCompletataBean op) {
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
			ps.setString(4, "client");
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
	public void cleanDisp(String idM) {
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

	

}
