package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO dao;
	private Simulatore sim;
	
	public Model() {
		
		this.dao = new RiversDAO();

	}
	
	
	public List<River> getAllRivers(){
	
		return this.dao.getAllRivers();
		
	}
	
	public double getMediaRilevazioni(River river) {
		return this.dao.getMediaFlusso(river);
	}
	
	public List<Flow> getFlowsRiver(River river){
		return this.dao.getRilevazioniFiume(river);
	}
	
	public String getDateMin(River river) {
		
		LocalDate date = this.getFlowsRiver(river).get(0).getDay();
		String data = date.toString();
		
		return data;
	}
	
	public String getDateMax(River river) {
		
		LocalDate date = this.getFlowsRiver(river).get(this.getFlowsRiver(river).size()-1).getDay();
		String data = date.toString();
		
		return data;
	}
	
	public int nMisurazioni(River river) {
		
		return this.dao.getRilevazioniFiume(river).size();
	
	}
	
	public void simula(River river, int k) {
		
		sim = new Simulatore(river);
		sim.init(k);
		sim.run();
		sim.setcMed();
	}
	
	public Simulatore getSimulatore() {
		return this.sim;
	}

}
