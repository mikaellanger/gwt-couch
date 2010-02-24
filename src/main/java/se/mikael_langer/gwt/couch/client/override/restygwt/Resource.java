package se.mikael_langer.gwt.couch.client.override.restygwt;

import com.google.gwt.http.client.RequestException;
import com.hiramchirino.restygwt.client.Method;

public class Resource extends com.hiramchirino.restygwt.client.Resource {

	private int status;

	public Resource(String uri) {
		super(uri);
		expect(200);
	}

	public Resource(String uri, int status) {
		super(uri);
		expect(status);
	}
	
	public void expect(int status) {
		this.status = status;
	}
	
	@Override
	public Method get() {
		Method ret = super.get();
		try { ret.expect(status); } catch (RequestException e) {}
		return ret;
	}
	
	@Override
	public Method put() {
		Method ret = super.put();
		try { ret.expect(status); } catch (RequestException e) {}
		return ret;
	}
	
	@Override
	public Method post() {
		Method ret = super.post();
		try { ret.expect(status); } catch (RequestException e) {}
		return ret;
	}
	
	@Override
	public Method head() {
		Method ret = super.head();
		try { ret.expect(status); } catch (RequestException e) {}
		return ret;
	}

	@Override
	public Method options() {
		Method ret = super.options();
		try { ret.expect(status); } catch (RequestException e) {}
		return ret;
	}
}

