/**
 * Paquet de définition
 **/

import java.util.HashMap;
import java.util.Map;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.github.biconou.cmis.CMISUtils;
import junit.framework.Assert;

/**
 * Description: Merci de donner une description du service rendu par cette classe
 **/
public class SimpleCMISTests {

  private static final String TEST_FOLDER_NAME = "testFolderJUnit";

  private Session session = null;

  @Before
  public void init() {
    session = CMISUtils.connect();
  }

  @After
  public void cleanUpResources() {
    session.clear();
  }

  private void createTestFolder() {
    Folder root = session.getRootFolder();
    Map<String, Object> folderProperties = new HashMap<String, Object>();
    folderProperties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
    folderProperties.put(PropertyIds.NAME, TEST_FOLDER_NAME);
    root.createFolder(folderProperties);
  }

  private Folder getFolderByPath(String folderPath) {
    try {
      CmisObject cmisObject = session.getObjectByPath(folderPath);
      return (Folder) cmisObject;
    } catch (CmisObjectNotFoundException e) {
      return null;
    }
  }

  private Document getDocumentByPath(String documentPath) {
    try {
      CmisObject cmisObject = session.getObjectByPath(documentPath);
      return (Document) cmisObject;
    } catch (CmisObjectNotFoundException e) {
      return null;
    }
  }


  @Test
  public void ping() {
    RepositoryInfo info = CMISUtils.connect().getRepositoryInfo();
    Assert.assertEquals("CMIS_1_0",info.getCmisVersion().toString());
  }

  @Test
  public void testCreateFolder() {
    createTestFolder();
    Folder folder = getFolderByPath("/"+TEST_FOLDER_NAME);
    Assert.assertNotNull(folder);
  }

  @Test
  public void testCreateDocument() {
    createTestFolder();
    Folder folder = getFolderByPath("/"+TEST_FOLDER_NAME);

    String documentName = "Premier document de test";
    String documentPath = "/" + TEST_FOLDER_NAME + "/" + documentName;
    Map<String, Object> props = new HashMap<String, Object>();
    props.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
    props.put(PropertyIds.NAME, documentName);

    folder.createDocument(props,null,null);

    Document foundDocument = getDocumentByPath(documentPath);

    Assert.assertNotNull(foundDocument);
  }




}
 
