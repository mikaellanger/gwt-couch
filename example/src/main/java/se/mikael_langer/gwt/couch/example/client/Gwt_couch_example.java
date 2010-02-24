package se.mikael_langer.gwt.couch.example.client;

import java.util.List;

import se.mikael_langer.gwt.couch.client.Database;
import se.mikael_langer.gwt.couch.client.Database.DoneCallback;
import se.mikael_langer.gwt.couch.example.client.samples.Person;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwt_couch_example implements EntryPoint {

	private Database db;
	
	public void onModuleLoad() {
		createDocument();
	}

	private void createDocument() {
		db = new Database("test");
		
		Person p = new Person();
		p.name = "John";
		p.age = 31;
		db.save(p, new DoneCallback<Person>() {
			@Override
			public void onFailure(Throwable exception) {
				GWT.log("Save failed: ", exception);
			}
			@Override
			public void onSuccess(Person document) {
				editDocument(document);
			}
		});
	}
	
	private void editDocument(Person p) {
		p.age = 32;
		db.save(p, new DoneCallback<Person>() {
			@Override
			public void onFailure(Throwable exception) {
				GWT.log("Edit failed: ", exception);
			}
			@Override
			public void onSuccess(Person document) {
				getDocument(document._id);
			}
		});
	}
	
	private void getDocument(String id) {
		Person p = new Person();
		p._id = id;
		db.get(p, new DoneCallback<Person>() {
			@Override
			public void onFailure(Throwable exception) {
				GWT.log("Get failed: ", exception);
			}
			@Override
			public void onSuccess(Person p) {
				GWT.log("Got " + p.age, null);
				getAll();
			}
		});		
	}

	private void getAll() {
		//TODO: maybe use class literal instead 
		db.getAll(new Person(), new DoneCallback<List<Person>>() {
			@Override
			public void onFailure(Throwable exception) {
				GWT.log("Get all failed: ", exception);
			}
			@Override
			public void onSuccess(List<Person> all) {
				for (Person p : all) {
					GWT.log("GOT: " + p.age, null);
					delete(p);
				}
			}
		});
	}
	
	private void delete(Person p) {
		db.delete(p, new DoneCallback<Person>() {
			@Override
			public void onFailure(Throwable exception) {
				GWT.log("Delete failed: ", exception);
			}
			@Override
			public void onSuccess(Person document) {
				GWT.log("Deleted " + document._id, null);
			}
		});
	}
	
	// TempView view = db.newTempView("view", "view", "if (doc.docType && doc.docType == 'person') emit(doc.age, doc);");
	// Person p = new Person();
	// Query.from(p).in(view).where(p.getAge()).gt(30).select(callback)
	// Query.from(p).in(view).where(p.getAge()).eq(30).or(p.getAge()).eq(31).select(callback);
}
