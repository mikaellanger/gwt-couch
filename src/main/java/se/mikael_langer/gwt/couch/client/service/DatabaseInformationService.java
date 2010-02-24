package se.mikael_langer.gwt.couch.client.service;

import javax.ws.rs.GET;

import se.mikael_langer.gwt.couch.client.domain.DatabaseInformation;

import com.hiramchirino.restygwt.client.MethodCallback;
import com.hiramchirino.restygwt.client.RestService;

public interface DatabaseInformationService extends RestService {
	@GET
	public void getDocument(MethodCallback<DatabaseInformation> callback);
}
