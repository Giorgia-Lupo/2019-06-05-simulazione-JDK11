package it.polito.tdp.crimes.db;

import it.polito.tdp.crimes.model.Event;

public class TestDao {

	public static void main(String[] args) {
		EventsDao dao = new EventsDao();
		/*for(Event e : dao.listAllEvents())
			System.out.println(e);*/
		
		System.out.println(dao.getAnno());
		System.out.println(dao.getVertici());
		System.out.println(dao.getLat(2, 2015));
		System.out.println(dao.getLon(2, 2015));
	}

}
