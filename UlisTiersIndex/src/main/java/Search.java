import freemarker.template.TemplateException;

import java.io.IOException;

public class Search {

    private static ElasticsearchHelper elasticsearchHelper = new ElasticsearchHelper();





    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            // TODO : il faut une factory d'objets
            elasticsearchHelper.extractObjects("thierry",null);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TemplateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }


}
