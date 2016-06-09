import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

public class TestFreeMarker1 {

  public static void main(String[] args) {

    //Freemarker configuration object
    Configuration cfg = new Configuration(new Version("2.3.23"));

    try {

      cfg.setTemplateLoader(new ClassTemplateLoader(TestFreeMarker1.class,"/"));
      Template template = cfg.getTemplate("test1.ftl");

      // Build the data-model
      Map<String, Object> data = new HashMap<String, Object>();
      data.put("message", "Hello World!");

      //List parsing
      List<String> countries = new ArrayList<String>();
      countries.add("India");
      countries.add("United States");
      countries.add("Germany");
      countries.add("France");

      data.put("countries", countries);


      // Console output
      Writer out = new OutputStreamWriter(System.out);
      template.process(data, out);
      out.flush();

  /*    // File output
      Writer file = new FileWriter (new File("C:\\FTL_helloworld.txt"));
      template.process(data, file);
      file.flush();
      file.close();
      */

    } catch (IOException e) {
      e.printStackTrace();
    } catch (TemplateException e) {
      e.printStackTrace();
    }
  }
}