/**
 * Paquet de définition
 **/
package com.github.biconou.cmis;

import java.util.HashMap;
import java.util.Map;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

/**
 * Description: Merci de donner une description du service rendu par cette classe
 **/
public class CMISUtils {

  private static final String CMIS_ENDPOINT_TEST_SERVER = "http://localhost:8081/inmemory/atom";


  public static Session connect() {
    // default factory implementation
    SessionFactory factory = SessionFactoryImpl.newInstance();
    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put(SessionParameter.ATOMPUB_URL, CMIS_ENDPOINT_TEST_SERVER);
    parameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
    return factory.getRepositories(parameters).get(0).createSession();

  }
}
 
