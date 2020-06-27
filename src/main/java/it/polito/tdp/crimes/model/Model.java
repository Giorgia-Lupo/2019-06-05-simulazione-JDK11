package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	Graph<Integer,DefaultWeightedEdge> grafo;
	
	public Model() {
		dao = new EventsDao();
	}
	
	public List<Integer> getAnni(){
		return dao.getAnno();
	}
	
	public void creaGrafo(Integer anno) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, dao.getVertici());
		
		for(Integer d1 : this.grafo.vertexSet()) {
			for(Integer d2 : this.grafo.vertexSet()) {
				if(!d1.equals(d2)){
					if(grafo.getEdge(d1, d2) == null) {
						Double lat1 = dao.getLat(d1, anno);
						Double lat2 = dao.getLat(d2, anno);
						
						Double lon1 = dao.getLon(d1, anno);
						Double lon2 = dao.getLon(d2, anno);
						
						LatLng centro1 = new LatLng(lat1, lon1);
						LatLng centro2 = new LatLng(lat2, lon2);
						
						Double peso = LatLngTool.distance(centro1, centro2, LengthUnit.KILOMETER);
						Graphs.addEdgeWithVertices(this.grafo, d1, d2, peso);
					}
				}
			}
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	public Set<Integer> vertici(){
		return this.grafo.vertexSet();
	}
	
	public List<Adiacenti> getAdiacenti(Integer distretto){
		double distanza = 0.0;
		List<Adiacenti> adiacenti = new ArrayList<>();
		for(Integer a : Graphs.neighborListOf(this.grafo, distretto)) {
			DefaultWeightedEdge e = this.grafo.getEdge(distretto, a);
			distanza = this.grafo.getEdgeWeight(e);
			Adiacenti ad = new Adiacenti(a, distanza);
			adiacenti.add(ad);
		}
		Collections.sort(adiacenti);
		return adiacenti;
	}
	
	public List<Integer> getMesi(Integer anno){
		return dao.getMesi(anno);
	}
	public List<Integer> getGiorni(Integer anno){
		return dao.getGiorni(anno);
	}
}
