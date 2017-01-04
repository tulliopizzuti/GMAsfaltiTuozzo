package it.asfalti.db;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.asfalti.javabean.DisponibilitaBean;
import it.asfalti.javabean.MagazzinoBean;
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

}
