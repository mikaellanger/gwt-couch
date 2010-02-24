package se.mikael_langer.gwt.couch.example.client.samples;

import se.mikael_langer.gwt.couch.client.domain.CouchDocument;

public class Person extends CouchDocument {
	public String name;
	public Integer age;
		
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
