import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by remi on 03/02/2016.
 */
public class PodcastsAnalyser {

    public static Connection getConnection() {

        Connection conn = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conn = DriverManager.getConnection("jdbc:hsqldb:hsql://192.168.0.11/subsonic",
                    "sa",                     // username
                    "");                      // password


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public void testConnection() {
        try {

            Connection conn = getConnection();

            ResultSet rs = conn.createStatement().executeQuery("select * from PODCAST_CHANNEL");
            while (rs.next()) {
                System.out.println(rs.getString(3));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public  static void main(String[] args) {

      /*  Properties props = new Properties();
        props.setProperty("hibernate.connection.driver_class","org.hsqldb.jdbcDriver");
        props.setProperty("hibernate.connection.url","jdbc:hsqldb:hsql://192.168.0.11/subsonic");
        props.setProperty("hibernate.connection.username","sa");
        props.setProperty("hibernate.connection.password","");
        props.setProperty("hibernate.connection.pool_size","1");

        Configuration cfg = new Configuration()
                .addClass(PodcastChannel.class)
                .setProperties(props);

        SessionFactory sessions = cfg.buildSessionFactory();
        Session session = sessions.openSession();

        List labels = session.createQuery("from PodcastChannel").list();
        */

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("podcasts");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //entityManager.getTransaction().begin();
        List<PodcastChannel> liste = entityManager.createQuery("from PodcastChannel", PodcastChannel.class).getResultList();
        for (PodcastChannel channel : liste) {
            System.out.println(channel.getTitle());

            TypedQuery<PodcastEpisode> q = entityManager.createQuery("SELECT x FROM PodcastEpisode x WHERE x.channelId = :xx", PodcastEpisode.class);
            q.setParameter("xx",channel.getId());
            List<PodcastEpisode> listeEpisodes = q.getResultList();

            for (PodcastEpisode episode : listeEpisodes) {
                System.out.println("         "+episode.getTitle());
            }
        }
        //entityManager.getTransaction().commit();
        entityManager.close();

    }
}
