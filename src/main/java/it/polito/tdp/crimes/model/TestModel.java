package it.polito.tdp.crimes.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		m.creaGrafo(2015);
		System.out.println(""+m.getVertici()+"\n");
		System.out.println(""+m.getArchi());
		System.out.println(m.getDistrettiAdiacenti(2));
	}

}
