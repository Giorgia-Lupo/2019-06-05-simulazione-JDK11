package it.polito.tdp.crimes.model;

public class Adiacenti implements Comparable<Adiacenti> {

	Integer d;
	Double distanza;
	
	public Adiacenti(Integer d, Double distanza) {
		super();
		this.d = d;
		this.distanza = distanza;
	}
	
	public void setD(Integer d) {
		this.d = d;
	}

	public Integer getD() {
		return d;
	}


	public Double getDistanza() {
		return distanza;
	}

	public void setDistanza(Double distanza) {
		this.distanza = distanza;
	}

	@Override
	public int compareTo(Adiacenti o) {
		return this.distanza.compareTo(o.distanza);
	}

	@Override
	public String toString() {
		return "d = " + d + ", distanza = " + distanza;
	}
	
	
}
