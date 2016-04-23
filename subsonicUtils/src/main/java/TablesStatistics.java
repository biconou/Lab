import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by remi on 22/04/2016.
 */
public class TablesStatistics {


    public Map<String, Integer> recordsInAllTables() {
        List<String> tableNames = SubsonicConnectionFactory.jdbcTemplate().queryForList("" +
                        "select table_name " +
                        "from information_schema.system_tables " +
                        "where table_name not like 'SYSTEM%'"
                , String.class);
        Map<String, Integer> nbRecords =
                tableNames.stream()
                        .collect(Collectors.toMap(table -> table, table -> recordsInTable(table)));

        return nbRecords;
    }

    public Integer recordsInTable(String tableName) {
        return SubsonicConnectionFactory.jdbcTemplate().queryForObject("select count(1) from " + tableName, Integer.class);
    }


    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        TablesStatistics stats = new TablesStatistics();
        Map<String, Integer> nbRecords = stats.recordsInAllTables();
        nbRecords.keySet().stream().forEach(table -> System.out.println(table+"\t"+nbRecords.get(table)));
    }


}
