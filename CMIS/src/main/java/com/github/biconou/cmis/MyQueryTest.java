/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.github.biconou.cmis;

import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.impl.IOUtils;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MyQueryTest {

    private static Logger log = LoggerFactory.getLogger(MyQueryTest.class);

    private static final String CMIS_ENDPOINT_TEST_SERVER = "http://localhost:8080/nuxeo/json/cmis";

    //private static final String CMIS_ENDPOINT_TEST_SERVER = "https://cmis.alfresco.com/alfresco/api/-default-/public/cmis/versions/1.1/browser";

    private Session session;

    private void getCmisClientSession() {
        // default factory implementation
        SessionFactory factory = SessionFactoryImpl.newInstance();
        Map<String, String> parameters = new HashMap<String, String>();
        // user credentials
        parameters.put(SessionParameter.USER, "Administrator");
        parameters.put(SessionParameter.PASSWORD, "Administrator");
        // connection settings
        parameters.put(SessionParameter.BROWSER_URL,
                CMIS_ENDPOINT_TEST_SERVER);
        parameters.put(SessionParameter.BINDING_TYPE, BindingType.BROWSER
                .value());
        // create session
        session = factory.getRepositories(parameters).get(0).createSession();
    }

    /**
     * @throws Exception
     */
    public void createTestArea() throws Exception {

        //creating a new folder
        Folder root = session.getRootFolder();
        Map<String, Object> folderProperties = new HashMap<String, Object>();
        folderProperties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
        folderProperties.put(PropertyIds.NAME, "testdata");

        Folder newFolder = root.createFolder(folderProperties);
        //create a new content in the folder
        String name = "testdata1.txt";
        // properties
        // (minimal set: name and object type id)
        Map<String, Object> contentProperties = new HashMap<String, Object>();
        contentProperties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        contentProperties.put(PropertyIds.NAME, name);

        // content
        byte[] content = "CMIS Testdata One".getBytes();
        InputStream stream = new ByteArrayInputStream(content);
        ContentStream contentStream = new ContentStreamImpl(name, new BigInteger(content), "text/plain", stream);

        // create a major version
        Document newContent1 = newFolder.createDocument(contentProperties, contentStream, null);
        System.out.println("Document created: " + newContent1.getId());
    }

    private String findFolderByName(String folderName) {
        ItemIterable<QueryResult> results = session.query("SELECT * FROM cmis:folder WHERE cmis:name='" + folderName + "'", true);
        if (results.getTotalNumItems() != 1) {
            throw new RuntimeException("On doit trouver un dossier");
        } else {
            return results.iterator().next().getPropertyValueById(PropertyIds.OBJECT_ID);
        }
    }


    private void doQuery() {

        //ItemIterable<QueryResult> results = session.query("SELECT * FROM cmis:document WHERE IN_FOLDER('2e4ee460-8726-443f-b7f7-1fb785f09d6d')", false);
        //ItemIterable<QueryResult> results = session.query("SELECT * FROM cmis:document", true);
        //ItemIterable<QueryResult> results = session.query("SELECT * FROM cmis:folder WHERE cmis:name='testdata'", false);

        String folderId = findFolderByName("Test CMIS IKOS");
        ItemIterable<QueryResult> results = session.query("SELECT * FROM cmis:document WHERE IN_FOLDER('" + folderId + "') AND cmis:name LIKE '%cartographie%'", true);

        System.out.println("Nombre de documents trouvés : " + results.getTotalNumItems());
        for (QueryResult result : results) {
            System.out.println("+++ found document +++");
            final String id = result.getPropertyValueById(PropertyIds.OBJECT_ID);
            System.out.println("doQuery() found id: " + id);
            String name = result.getPropertyValueById(PropertyIds.NAME);
            System.out.println("doQuery() found name: " + name);

            CmisObject foundObj = session.getObject(new ObjectId() {
                @Override
                public String getId() {
                    return id;
                }
            });
            if ((foundObj instanceof Document)) {
                Document document = (Document) foundObj;
                // On recherche les parents du document
                document.getParents().stream().forEach(folder -> System.out.println(folder.getPropertyValue(PropertyIds.NAME).toString()));

                ContentStream cs = document.getContentStream();

                try {
                    String fileName = "d:\\tmp\\" + cs.getFileName();
                    System.out.println("Création du fichier " + fileName);
                    OutputStream out = new FileOutputStream(new File(fileName));
                    InputStream in = cs.getStream();
                    IOUtils.copy(in, out);
                    in.close();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            //result.getPropertyValueById(PropertyIds.);
        }
    }

    /**
     * @param args
     */
    public static void main(String args[]) {
        MyQueryTest o = new MyQueryTest();
        try {
            o.getCmisClientSession();
            //o.createTestArea();
            o.doQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
