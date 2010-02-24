package se.mikael_langer.gwt.couch.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.hiramchirino.restygwt.client.Method;
import com.hiramchirino.restygwt.client.MethodCallback;

import se.mikael_langer.gwt.couch.client.domain.AllDocuments;
import se.mikael_langer.gwt.couch.client.domain.CouchDocument;
import se.mikael_langer.gwt.couch.client.domain.Row;
import se.mikael_langer.gwt.couch.client.domain.ServerResponse;
import se.mikael_langer.gwt.couch.client.override.restygwt.Resource;
import se.mikael_langer.gwt.couch.client.service.DocumentServices;
import se.mikael_langer.gwt.couch.client.service.DocumentServices.Service;

public class Database {
	
	public interface DoneCallback<T> {
		public void onFailure(Throwable exception);
		public void onSuccess(T document);
	}
	
	private static final String ALL_DOCS = "_all_docs?include_docs=true";
	private static final String REV = "?rev=";
	
	private final DocumentServices services = GWT.create(DocumentServices.class);
	
	private String basePath;
	
        public Database(String name) {
		basePath = "/" + name + "/";
	}

	public <T extends CouchDocument> void save(final T document, final DoneCallback<T> callback) {
		Resource resource = new Resource(basePath, 201);
		Service<T> service = services.getService(document);
		service.getProxy().setResource(resource);
		service.postDocument(document, new MethodCallback<ServerResponse>() {
			@Override
			public void onFailure(Method method, Throwable exception) {
				callback.onFailure(exception);
			}
			@Override
			public void onSuccess(Method method, ServerResponse response) {
				if (!response.ok) {
					callback.onFailure(new Exception("Save not ok"));
				} else {
					document._id = response.id;
					document._rev = response.rev;
					callback.onSuccess(document);
				}
			}
		});
	}

	public <T extends CouchDocument> void get(T document, final DoneCallback<T> callback) {
		Service<T> service = services.getService(document);
		Resource resource = new Resource(basePath + document._id); 
		service.getProxy().setResource(resource);
		service.getDocument(new MethodCallback<T>() {
			@Override
			public void onFailure(Method method, Throwable exception) {
				callback.onFailure(exception);
			}
			@Override
			public void onSuccess(Method method, T response) {
				callback.onSuccess(response);
			}
		});
	}
	
	public <T extends CouchDocument> void delete(final T document, final DoneCallback<T> callback) {
		Service<T> service = services.getService(document);
		Resource resource = new Resource(basePath + document._id + REV + document._rev);
		service.getProxy().setResource(resource);
		service.deleteDocument(document, new MethodCallback<ServerResponse>() {
			@Override
			public void onFailure(Method method, Throwable exception) {
				callback.onFailure(exception);
			}
			@Override
			public void onSuccess(Method method, ServerResponse response) {
				if (!response.ok) {
					callback.onFailure(new Exception("Delete not ok"));
				} else {
					document._rev = response.rev;
					callback.onSuccess(document);
				}
			}
		});
	}
	
	public <T extends CouchDocument> void getAll(T document, final DoneCallback<List<T>> callback) {
		Service<T> service = services.getService(document);
		Resource resource = new Resource(basePath + ALL_DOCS);
		service.getProxy().setResource(resource);
		service.getAll(new MethodCallback<AllDocuments<T>>() {
			@Override
			public void onFailure(Method method, Throwable exception) {
				callback.onFailure(exception);
			}
			@Override
			public void onSuccess(Method method, AllDocuments<T> response) {
				List<T> ret = new ArrayList<T>(response.total_rows);
				for (Row<T> row : response.theRows()) {
					ret.add(row.theDoc());
				}
				callback.onSuccess(ret);
			}
		});
	} 
}
