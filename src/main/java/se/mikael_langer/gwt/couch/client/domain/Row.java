package se.mikael_langer.gwt.couch.client.domain;

public abstract class Row<T extends CouchDocument> {
	public String id;
	public String key;
	public Value value;
	public abstract T theDoc();
}
