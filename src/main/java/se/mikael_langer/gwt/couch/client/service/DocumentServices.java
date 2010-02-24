package se.mikael_langer.gwt.couch.client.service;

import java.util.HashMap;

import com.hiramchirino.restygwt.client.MethodCallback;
import com.hiramchirino.restygwt.client.RestServiceProxy;

import se.mikael_langer.gwt.couch.client.domain.AllDocuments;
import se.mikael_langer.gwt.couch.client.domain.CouchDocument;
import se.mikael_langer.gwt.couch.client.domain.ServerResponse;

@SuppressWarnings("unchecked")
public abstract class DocumentServices {

	public interface Service<T extends CouchDocument> {
		public void getDocument(MethodCallback<T> callback);
		public void putDocument(T p, MethodCallback<ServerResponse> callback);
		public void postDocument(T p, MethodCallback<ServerResponse> callback);
		public void deleteDocument(T p, MethodCallback<ServerResponse> callback);
		public void getAll(MethodCallback<AllDocuments<T>> callback);
		public RestServiceProxy getProxy();
	}
	
	private HashMap<Class<? extends CouchDocument>, Service> serviceMap = new HashMap<Class<? extends CouchDocument>, Service>();
	
	protected <T extends CouchDocument> void registerService(Class<T> docType, Service<T> service) {
		serviceMap.put(docType, service);
	}
	
	public <T extends CouchDocument> Service<T> getService(T document) {
		return serviceMap.get(document.getClass());
	}
}
