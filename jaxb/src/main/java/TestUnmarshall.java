import com.example.myschema.ImportTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * Created by remi on 22/01/2016.
 */
public class TestUnmarshall {

    public static void main(String args[]) {
        try {

            InputStream in = TestUnmarshall.class.getResourceAsStream("/template.xml");

            JAXBContext jaxbContext = JAXBContext.newInstance(ImportTemplate.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ImportTemplate template = (ImportTemplate) jaxbUnmarshaller.unmarshal(in);
            System.out.println(template);


        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
