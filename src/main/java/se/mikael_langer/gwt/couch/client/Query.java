package se.mikael_langer.gwt.couch.client;

import se.mikael_langer.gwt.couch.client.domain.CouchDocument;

public class Query {
	
	private View view;
	
	public static Query from(View view) {
		return new Query(view);
	}
	
	protected Query(View view) {
		this.view = view;
	}
	
	public Query select(CouchDocument doc) {
		return null;
	}
}
