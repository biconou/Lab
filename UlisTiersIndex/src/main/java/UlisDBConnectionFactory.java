import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UlisDBConnectionFactory {

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
                Class.forName("oracle.jdbc.OracleDriver");
                cnx = DriverManager.getConnection("jdbc:oracle:thin://@llyoninteg03:1575/I3FNGL",
                        "ulism",                     // username
                        "ulism");                      // password

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cnx;
    }
}
