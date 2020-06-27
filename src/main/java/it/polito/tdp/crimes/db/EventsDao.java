package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.Event;



public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
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

	public List<Integer> getAnno(){
		String sql="SELECT distinct YEAR(e.reported_date) as anno " + 
				"FROM `events` AS e " + 
				"ORDER BY YEAR(e.reported_date) " ;
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
	
	public List<Integer> getVertici(){
		String sql = "SELECT DISTINCT e.district_id " + 
				"FROM `events` AS e " + 
				"ORDER BY e.district_id ";
		List<Integer> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(res.getInt("district_id"));
			}
			conn.close();
			return result ;
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public Double getLon(Integer distretto, Integer anno) {
		String sql="SELECT AVG(e.geo_lon) as lon " + 
				"FROM `events` AS e " + 
				"WHERE e.district_id = ? AND YEAR(e.reported_date) = ? " ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;		
			
			st.setInt(1, distretto);
			st.setInt(2, anno);
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
	
	public Double getLat(Integer distretto, Integer anno) {
		String sql = "SELECT AVG(e.geo_lat) as lat " + 
				"FROM `events` AS e " + 
				"WHERE e.district_id = ? AND YEAR(e.reported_date) = ? " ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;		
			
			st.setInt(1, distretto);
			st.setInt(2, anno);
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
	
	public List<Integer> getMesi(Integer anno){
		String sql = "SELECT distinct MONTH(e.reported_date) as mese " + 
				"FROM `events` AS e " + 
				"WHERE YEAR(e.reported_date) = ? " + 
				"ORDER BY MONTH(e.reported_date) " ;
		List<Integer> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;			
			st.setInt(1, anno);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(res.getInt("mese"));
			}
			conn.close();
			return result ;
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> getGiorni(Integer anno){
		String sql = "SELECT distinct day(e.reported_date) as giorno " + 
				"FROM `events` AS e " + 
				"WHERE YEAR(e.reported_date) = ? " + 
				"ORDER BY day(e.reported_date) " ;
		List<Integer> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;			
			st.setInt(1, anno);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(res.getInt("giorno"));
			}
			conn.close();
			return result ;
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public Integer getDistrettoMinoreCriminalita(Integer anno) {
		String sql = "SELECT e.district_id " + 
				"FROM `events` AS e " + 
				"WHERE YEAR(e.reported_date) = ? " + 
				"GROUP BY e.district_id " + 
				"ORDER BY COUNT(e.incident_id) ASC " + 
				"LIMIT 1 ";
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;	
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				conn.close();
				return res.getInt("district_id") ;
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
}
