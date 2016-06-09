package Tests;

import java.util.Date;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tgh.business.services.AjoutAchatService;

public class TestTgh {

	private static Logger logger = LoggerFactory.getLogger(TestTgh.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// On crée une instance de EntityManagerFactory : une seule instance par application.
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("tgh");

		try {
			AjoutAchatService service = new AjoutAchatService(entityManagerFactory);
			
			service.process(1L,new Date(),Double.valueOf(157),"Chèque","T shirt","rien à signaler");
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			// Fermeture de l'entityManagerFactory
			entityManagerFactory.close();
		}
	}
}
