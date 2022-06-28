package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {
	
	
	
	public RiversDAO() {
	
	}

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
				//idMap.put(res.getInt("id"),new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public double getMediaFlusso(River river) {
		
		final String sql = "SELECT f.river, AVG (f.flow) AS media "
				+ "FROM flow f "
				+ "WHERE f.river=? "
				+ "GROUP BY f.river ";

		double avg = 0.0;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				avg = (res.getDouble("media")) / (3600*24);
				//idMap.get(river.getId()).setFlowAvg(avg);
				river.setFlowAvg(avg);
				
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return avg;
	}
	
	public List<Flow> getRilevazioniFiume(River river){
		
		
		final String sql = "SELECT * "
				+ "FROM flow f "
				+ "WHERE f.river=? "
				+ "ORDER BY f.day ";

		//List<Flow> result = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				//River r = idMap.get(res.getInt("river"));
				Flow f = new Flow(res.getDate("day").toLocalDate(),(res.getDouble("flow"))/(3600*24), river);
				river.getFlows().add(f);
				//result.add(f);
				
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return river.getFlows();
	}
	
	/*public List<River> getRivers(){
		return this.fiumi;
	}
	
	public List<Flow> getRilFiume(River r){
		River fiume=null;
		for(River ri : this.fiumi) {
			if(ri.equals(r)) {
				fiume = ri;
			}
		}
		return fiume.getFlows();
	}*/
	
}
