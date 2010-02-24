package se.mikael_langer.gwt.couch.client.domain;

import com.hiramchirino.restygwt.client.ExcludeNull;

public class CouchDocument {
	public String doctype;
	@ExcludeNull public String _id;
	@ExcludeNull public String _rev;
	public CouchDocument() {
		doctype = this.getClass().getName();
	}
}
