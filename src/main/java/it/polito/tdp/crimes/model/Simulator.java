package it.polito.tdp.crimes.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.db.EventsDao;

public class Simulator {

	//stato del mondo
	Graph<Integer, DefaultWeightedEdge> grafo;
	Map<Integer, Integer> agentiCentrale;
	
	private PriorityQueue<Evento> queue;
	
	//parametri
	int nAgenti;
	int anno;
	int mese;
	int giorno;
	
	//output
	int malGestiti;
	
	public void init(Integer nAgenti, Integer anno, Integer mese, Integer giorno, Graph<Integer, DefaultWeightedEdge> grafo) {
		this.nAgenti = nAgenti;
		this.anno = anno;
		this.mese = mese;
		this.giorno = giorno;
		this.grafo = grafo;
		
		//stato iniziale
		malGestiti = 0;
		agentiCentrale = new HashMap<>();
		for(Integer distretto : grafo.vertexSet()) {
			this.agentiCentrale.put(distretto, 0);
		}
		
		EventsDao dao = new EventsDao();
		Integer distrettoMinorCriminalita = dao.getDistrettoMinoreCriminalita(anno);
		this.agentiCentrale.put(distrettoMinorCriminalita, nAgenti);
		
		queue = new PriorityQueue<Evento>();
		
		for(Event)
	}
}
