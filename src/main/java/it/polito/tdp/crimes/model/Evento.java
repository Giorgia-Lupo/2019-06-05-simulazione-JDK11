package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento> {
	
	public enum EventType{
		CRIMINE,
		ARRIVO_AGENTE,
		FINE
	}
	
	private LocalDateTime time;
	private EventType tipo;
	private Event crimine;
	
	public Evento(LocalDateTime time, EventType tipo, Event crimine) {
		super();
		this.time = time;
		this.tipo = tipo;
		this.crimine = crimine;
	}
	
	public LocalDateTime getTime() {
		return time;
	}
	
	public EventType getTipo() {
		return tipo;
	}

	public Event getCrimine() {
		return crimine;
	}

	@Override
	public String toString() {
		return "Evento [time=" + time + ", tipo=" + tipo + "]";
	}

	@Override
	public int compareTo(Evento other) {
		return this.crimine.getReported_date().compareTo(other.crimine.getReported_date());
	}

	
}
