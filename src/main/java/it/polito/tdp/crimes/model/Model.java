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
	
	EventsDao dao; 
	private Graph<Integer, DefaultWeightedEdge> grafo;
	
	public Model() {
		dao = new EventsDao();
	}	
	
	public List <Integer> getAnni(){
		return dao.getAnni();
	}
	
	public void creaGrafo(int anno) {
		this.grafo = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//Vertici
		Graphs.addAllVertices(this.grafo, dao.getDistretti());
		//Archi
		for(Integer i1 : this.grafo.vertexSet()) {
			for(Integer i2 : this.grafo.vertexSet()) {
				if(!i1.equals(i2)) {
					if(this.grafo.getEdge(i1, i2) == null) {
						Double latMedia1 = dao.getLatMedia(anno, i1);
						Double latMedia2 = dao.getLatMedia(anno, i2);
						
						Double lonMedia1 = dao.getLonMedia(anno, i1);
						Double lonMedia2 = dao.getLonMedia(anno, i2);
						
						LatLng centro1 = new LatLng(latMedia1, lonMedia1);
						LatLng centro2 = new LatLng(latMedia2, lonMedia2);
						
						
						Double distanzaMedia = LatLngTool.distance(centro1, centro2, LengthUnit.KILOMETER);
						
						
						Graphs.addEdgeWithVertices(this.grafo, i1, i2, distanzaMedia);
					}
				}
				
			}
		}				
	}
	
	public int getVertici() {
		return (int)this.grafo.vertexSet().size();
	}
	
	public Set<Integer> veritici(){
		return this.grafo.vertexSet();
	}
	
	public int getArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<DistrettiAdiacenti> getDistrettiAdiacenti(Integer distretto) {
	
		List<Integer> vicini = Graphs.neighborListOf(this.grafo, distretto); ;
		
		List<DistrettiAdiacenti> adiacenti = new ArrayList<>();
	
		DefaultWeightedEdge e;
		Double peso = 0.0;
	
		for(Integer v : vicini) {
				e = this.grafo.getEdge(distretto, v);
				peso = this.grafo.getEdgeWeight(e);
				adiacenti.add(new DistrettiAdiacenti(v, peso));
		}
		
		Collections.sort(adiacenti);
		return adiacenti;
	}
	
	public int simula(Integer anno, Integer mese, Integer giorno, Integer N) {
		Simulator sim = new Simulator();
		sim.init(N, anno, mese, giorno, grafo);
		return sim.run();
	}
		
	public List<Integer> getMesi(){
		return dao.getMesi();
	}
	
	public List<Integer> getGiorni(){
		return dao.getGiorni();
	}
	
}
