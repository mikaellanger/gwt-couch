package se.mikael_langer.gwt.couch.client.domain;

import java.util.List;

public abstract class AllDocuments<T extends CouchDocument> {
	public Integer total_rows;
	public Integer offset;
	public abstract List<Row<T>> theRows();
}
