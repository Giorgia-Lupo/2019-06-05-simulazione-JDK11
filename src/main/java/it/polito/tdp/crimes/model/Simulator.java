package it.polito.tdp.crimes.model;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.db.EventsDao;
import it.polito.tdp.crimes.model.Evento.EventType;

public class Simulator {
	
	//Stato del mondo
	private Graph<Integer, DefaultWeightedEdge> grafo;	
	private Map<Integer, Integer> agenti; //quanti agenti in un distretto --> HashMap
	
	//Tipo di evento
	private PriorityQueue<Evento> queue;
	
	//Parametri di simulazione
	private int nAgenti = 10;
	private Integer anno;
	private Integer mese;
	private Integer giorno;	

	//Valori in output
	int malGestiti = 0;

	public void init(int nAgenti, Integer anno, Integer mese, Integer giorno, 
			Graph<Integer, DefaultWeightedEdge> grafo) {
		
		this.nAgenti = nAgenti;
		this.anno = anno;
		this.mese = mese;
		this.giorno = giorno;
		this.grafo = grafo;
		
		//stato iniziale
		this.malGestiti = 0;
		
		this.agenti = new HashMap<>();
		for(Integer distretto : this.grafo.vertexSet()) {
			this.agenti.put(distretto, 0);
		}
		
		//vedere la centrale, nel distretto a minore criminalità in quell'anno
		EventsDao dao = new EventsDao();
		Integer DisMinorCriminalita = dao.getDistrMinorCriminalita(anno);
		this.agenti.put(DisMinorCriminalita, nAgenti);
		
		//creo la coda
		this.queue = new PriorityQueue<Evento>();
		
		//inserisco il primo elemento
		for(Event e : dao.listAllEvents(anno, mese, giorno)) {
			this.queue.add(new Evento(e.getReported_date(), EventType.CRIMINE,  e));
		}		
	}
	
	public int run() {
		//Fino a quando la coda non si svuota, estraggo un evento per volta e lo eseguo
		Evento e;
		while((e = this.queue.poll()) != null) {
			switch(e.getTipo()) {
			case CRIMINE:
				//Agente piu vicino:
				Integer partenza = null;
				partenza = this.cercaAgente(e.getCrimine().getDistrict_id()); 
				if(partenza!=null) {
					//C'è un agente libero da mandare, e lo setto come occupato
					this.agenti.put(partenza, this.agenti.get(partenza)-1);
					//Quanto ci mette ad arrivare al crimine?
					Double distanza;
					if(partenza.equals(e.getCrimine().getDistrict_id())) //è già nel distretto
						distanza = 0.0;
					else
						distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(partenza, e.getCrimine().getDistrict_id()));
					
					Long seconds = (long) ((distanza * 1000)/(60/3.6));
					this.queue.add(new Evento(e.getTime().plusSeconds(seconds), EventType.ARRIVO_AGENTE, e.getCrimine()));
				}
				else
				{
				//non c'è nessun agente --> crimine mal gestito
				this.malGestiti++;
				}
				break;
			case ARRIVO_AGENTE:
				Long duration = getDurata(e.getCrimine().getOffense_category_id());
				this.queue.add(new Evento(e.getTime().plusSeconds(duration), EventType.FINE, e.getCrimine()));
				//controllo se è mal gestito
				if(e.getTime().isAfter(e.getCrimine().getReported_date().plusMinutes(15))) {
					this.malGestiti++;
				}
				break;
			case FINE:
				this.agenti.put(e.getCrimine().getDistrict_id(), this.agenti.get(e.getCrimine().getDistrict_id())+1);
				break;
			}
		}
		return this.malGestiti;
	}
	
	private Long getDurata(String offense_category_id) {
		if(offense_category_id.equals("all_other_crimes")) {
			Random r = new Random();
			if(r.nextDouble() > 0.5)
				return Long.valueOf(2*60*60);
			else
				return Long.valueOf(1*60*60);
		}else
			return Long.valueOf(2*60*60);
	}
	
	private Integer cercaAgente(Integer distretto) {
		Double distanza = Double.MAX_VALUE;
		Integer distr = null;
		
		for(Integer dis : this.agenti.keySet()) {
			if(this.agenti.get(dis)>0) { //c'è un agente
				if(distretto.equals(dis)) {
					distanza = 0.0;
					distr = dis;
				}else if(this.grafo.getEdgeWeight(this.grafo.getEdge(distretto, dis))< distanza) {
					distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(distretto, dis));
					distr = dis;
				}
			}
		}
		return distr;
	}
	
	
	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Integer getMese() {
		return mese;
	}

	public void setMese(Integer mese) {
		this.mese = mese;
	}

	public Integer getGiorno() {
		return giorno;
	}

	public void setGiorno(Integer giorno) {
		this.giorno = giorno;
	}

	public int getnAgenti() {
		return nAgenti;
	}

	public void setnAgenti(int nAgenti) {
		this.nAgenti = nAgenti;
	}

	public int getMalGestiti() {
		return malGestiti;
	}
}
