package se.mikael_langer.gwt.couch.client.service;

import javax.ws.rs.GET;

import se.mikael_langer.gwt.couch.client.domain.Welcome;

import com.hiramchirino.restygwt.client.MethodCallback;
import com.hiramchirino.restygwt.client.RestService;

public interface CouchService extends RestService {
	@GET
	public void getDocument(MethodCallback<Welcome> callback);
}
