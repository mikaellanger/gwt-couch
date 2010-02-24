package se.mikael_langer.gwt.couch.rebind;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import se.mikael_langer.gwt.couch.client.domain.AllDocuments;
import se.mikael_langer.gwt.couch.client.domain.CouchDocument;
import se.mikael_langer.gwt.couch.client.domain.Row;
import se.mikael_langer.gwt.couch.client.domain.ServerResponse;
import se.mikael_langer.gwt.couch.client.service.DocumentServices.Service;
import se.mikael_langer.gwt.generator.ClassDefinition;
import se.mikael_langer.gwt.generator.Generator;
import se.mikael_langer.gwt.generator.GeneratedClassDefinition;
import se.mikael_langer.gwt.generator.InterfaceDefinition;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.generator.ast.Expression;
import com.hiramchirino.restygwt.client.MethodCallback;
import com.hiramchirino.restygwt.client.RestService;
import com.hiramchirino.restygwt.client.RestServiceProxy;

public class ServiceGenerator extends Generator {

	private String getServiceName(JClassType type) {
		return type.getSimpleSourceName() + "Service"; 
	}
	private String getAllDocumentsName(JClassType type) {
		return "All" + type.getSimpleSourceName() + "s";
	}
	private String getRowTypeName(JClassType type) {
		return type.getSimpleSourceName() + "Row";
	}
	
	private ClassDefinition generateRowTypeFor(JClassType type) {
		return defineClass(getRowTypeName(type)).thatExtends(template(type(Row.class), type)).withFields(
						defineField(type, "doc")
					).withMethods(
						defineMethod(type, "theDoc").withBody(returnValue("doc")
					)
				);
	}
	
	private ClassDefinition generateAllDocumentsTypeFor(JClassType type) {
		return defineClass(getAllDocumentsName(type)).thatExtends(template(type(AllDocuments.class), type)).withFields(
						defineField(template(type(List.class), type(getRowTypeName(type))), "rows")
					).withMethods(
						defineMethod(type(List.class), "theRows").withBody(returnValue("rows"))
				);
	}
	
	private InterfaceDefinition generateServiceInterfaceFor(JClassType type) {
		return defineInterface(getServiceName(type)).thatExtends(RestService.class).withMethods(
				defineMethod("getDocument").annotatedWith(GET.class).withParams(
					defineParam(template(type(MethodCallback.class), type), "callback")
				),
				defineMethod("putDocument").annotatedWith(PUT.class).withParams(
					defineParam(type, "document"),
					defineParam(template(type(MethodCallback.class), type(ServerResponse.class)), "callback")
				),
				defineMethod("postDocument").annotatedWith(POST.class).withParams(
					defineParam(type, "document"),
					defineParam(template(type(MethodCallback.class), type(ServerResponse.class)), "callback")
				),
				defineMethod("deleteDocument").annotatedWith(DELETE.class).withParams(
					defineParam(type, "document"),
					defineParam(template(type(MethodCallback.class), type(ServerResponse.class)), "callback")
				),
				defineMethod("getAll").annotatedWith(GET.class).withParams(
					defineParam(template(type(MethodCallback.class), type(getAllDocumentsName(type))), "callback")
				)
    		);
	}
	
	private Expression generateServiceRegistrationFor(JClassType type) {
		return callMethod("registerService", classLiteral(type),
				newAnonymousClass(template(type(Service.class), type)).withFields(
						defineField(type(getServiceName(type)), "service").assignValue(callMethod(GWT.class, "create", getServiceName(type) + ".class"))
					).withMethods(
						defineMethod("getDocument").withParams(
								defineParam(template(type(MethodCallback.class), type), "callback")
							).withBody(
								callMethod("service.getDocument", "callback")
						),
						defineMethod("putDocument").withParams(
								defineParam(type, "document"),
				    			defineParam(template(type(MethodCallback.class), type(ServerResponse.class)), "callback")
				    		).withBody(
				    			callMethod("service.putDocument", "document", "callback")
				    	),
						defineMethod("postDocument").withParams(
								defineParam(type, "document"),
						    	defineParam(template(type(MethodCallback.class), type(ServerResponse.class)), "callback")
							).withBody(
								callMethod("service.postDocument", "document", "callback")
						),
						defineMethod("deleteDocument").annotatedWith(DELETE.class).withParams(
								defineParam(type, "document"),
								defineParam(template(type(MethodCallback.class), type(ServerResponse.class)), "callback")
							).withBody(
								callMethod("service.deleteDocument", "document", "callback")
						),
						defineMethod("getAll").annotatedWith(GET.class).withParams(
								defineParam(template(type(MethodCallback.class), template(type(AllDocuments.class), type)), "callback")
							).withBody(
								callMethod("service.getAll", cast(template(type(MethodCallback.class), type(getAllDocumentsName(type))), "callback"))
						),
						defineMethod(type(RestServiceProxy.class), "getProxy").withBody(
								returnValue(cast(type(RestServiceProxy.class), "service"))
						)
				)
			);
	}
	
	@Override
	protected void generate(GeneratedClassDefinition classDef) {
		JClassType couchDocumentType = type(CouchDocument.class);
		JClassType couchDocumentSubtypes[] = couchDocumentType.getSubtypes();
		for (JClassType type : couchDocumentSubtypes) {
			classDef.addNestedClass(generateRowTypeFor(type));
			classDef.addNestedClass(generateAllDocumentsTypeFor(type));
			classDef.addNestedInterface(generateServiceInterfaceFor(type));
			classDef.getConstructor().addToBody(generateServiceRegistrationFor(type));			
		}
	}
}
