package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.Event;



public class EventsDao {
	
	public List<Event> listAllEvents(Integer anno, Integer mese, Integer giorno){
		String sql = "SELECT * " + 
				"FROM `events` AS e " + 
				"WHERE YEAR(e.reported_date) = ? AND " + 
				"MONTH(e.reported_date) = ? AND DAY(e.reported_date) = ? " ;
		
		List<Event> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, anno);
			st.setInt(2, mese);
			st.setInt(3, giorno);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> getAnni(){
		String sql = "SELECT DISTINCT year(e.reported_date) as anno " + 
				"FROM `events` AS e " + 
				"ORDER BY e.reported_date ASC ";
		List<Integer> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;		
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(res.getInt("anno"));
			}
			conn.close();
			return result ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> getDistretti(){
		String sql="SELECT DISTINCT e.district_id as distretto " + 
				"FROM events AS e " + 
				"ORDER BY e.district_id ASC ";
		List<Integer> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;		
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(res.getInt("distretto"));
			}
			conn.close();
			return result ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	
	public Double getLonMedia (Integer anno, Integer distretto) {
		String sql = "SELECT AVG(e.geo_lon) AS lon " + 
				"FROM events AS e " + 
				"WHERE YEAR(e.reported_date) = ? AND e.district_id = ? ";		
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;		
			
			st.setInt(1, anno);
			st.setInt(2, distretto);
			
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				conn.close();
				return res.getDouble("lon") ;
			}else {
				conn.close();
				return null;
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}	
	
	public Double getLatMedia (Integer anno, Integer distretto) {
		String sql = "SELECT AVG(e.geo_lat) AS lat " + 
				"FROM events AS e " + 
				"WHERE YEAR(e.reported_date) = ? AND e.district_id = ? ";		
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;		
			
			st.setInt(1, anno);
			st.setInt(2, distretto);
			
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				conn.close();
				return res.getDouble("lat") ;
			}else {
				conn.close();
				return null;
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	
	public List<Integer> getMesi(){
		String sql = "SELECT distinct MONTH(e.reported_date) as mese " + 
				"FROM `events` AS e " + 
				"ORDER BY e.reported_date ";
		
		List<Integer> mesi = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;		
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				mesi.add(res.getInt("mese"));
			}
			conn.close();
			return mesi ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> getGiorni(){
		String sql = "SELECT distinct Day(e.reported_date) AS giorno " + 
				"FROM `events` AS e " + 
				"ORDER BY giorno ";
		List<Integer> giorni = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;		
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				giorni.add(res.getInt("giorno"));
			}
			conn.close();
			return giorni ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	
	public Integer getDistrMinorCriminalita(Integer anno) {
		String sql = "SELECT e.district_id " + 
				"FROM `events` AS e " + 
				"WHERE YEAR(e.reported_date) = ? " + 
				"GROUP BY e.district_id " + 
				"ORDER BY COUNT(e.incident_id) asc " + 
				"LIMIT 1";
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;	
			
			st.setInt(1, anno);
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				conn.close();
				return res.getInt("district_id");
			}else
				conn.close();
			return null;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
}
