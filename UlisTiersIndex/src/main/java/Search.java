import java.util.List;

public class Search {

    private static ElasticsearchHelper elasticsearchHelper = new ElasticsearchHelper();





    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        List<Tiers> listeDeTiersTrouves = elasticsearchHelper.extractObjects("thierry", null, Tiers.class);

    }


}
