import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by remi on 22/04/2016.
 */
public class SubsonicConnectionFactory {

    private static Connection cnx = null;

    private static JdbcTemplate jdbcTemplate = null;

    public static JdbcTemplate jdbcTemplate() {
        if (jdbcTemplate == null) {
            Connection cnx = subsonicConnection();
            jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(cnx, false));
        }
        return jdbcTemplate;
    }


    public static Connection subsonicConnection() {
        if (cnx == null) {
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                cnx = DriverManager.getConnection("jdbc:hsqldb:hsql://192.168.0.11/subsonic",
                        "sa",                     // username
                        "");                      // password

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cnx;
    }
}
