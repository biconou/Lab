/**
 * Paquet de définition
 **/

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: Merci de donner une description du service rendu par cette classe
 */
public class SQLQueryHelper {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SQLQueryHelper.class);


    private Map<String, Template> queryTemplates = new HashMap<>();
    private Configuration freeMarkerConfiguration = null;

    public SQLQueryHelper() {
        freeMarkerConfiguration = new Configuration(Configuration.VERSION_2_3_23);
        freeMarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/query");
    }


    /**
     * @param queryName
     * @param vars
     * @return
     * @throws java.io.IOException
     * @throws freemarker.template.TemplateException
     */
    public String getQuery(String queryName, Map<String, String> vars) throws IOException, TemplateException {

        Template template = queryTemplates.get(queryName);
        if (template == null) {
            synchronized (queryTemplates) {
                template = queryTemplates.get(queryName);
                if (template == null) {
                    template = freeMarkerConfiguration.getTemplate(queryName + ".sql");
                    queryTemplates.put(queryName, template);
                }
            }
        }
        StringWriter result = new StringWriter();

        if (vars != null) {
            vars.forEach((k, v) -> vars.put(k, escapeForSQL(v)));
        }
        template.process(vars, result);

        return result.toString();
    }

    /**
     * @param source
     * @return
     */
    private String escapeForSQL(String source) {
        if (source == null) {
            return "";
        } else {
            String target = source.replace("'", "''");
            return target;
        }
    }


}
 
