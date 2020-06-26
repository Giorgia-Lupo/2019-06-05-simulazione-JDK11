package it.polito.tdp.crimes.model;

public class DistrettiAdiacenti implements Comparable<DistrettiAdiacenti> {
	
	private Integer distretto;
	private Double distanza;
	
	public DistrettiAdiacenti(Integer distretto, Double distanza) {
		super();
		this.distretto = distretto;
		this.distanza = distanza;
	}

	public Integer getDistretto() {
		return distretto;
	}

	public void setDistretto(Integer distretto) {
		this.distretto = distretto;
	}

	public Double getDistanza() {
		return distanza;
	}

	public void setDistanza(Double distanza) {
		this.distanza = distanza;
	}

	@Override
	public int compareTo(DistrettiAdiacenti other) {
		return this.distanza.compareTo(other.distanza);
	}

	@Override
	public String toString() {
		return "DistrettiAdiacenti [distretto=" + distretto + ", distanza=" + distanza + "]";
	}
	
	

}
