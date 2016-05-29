package org.tgh.business.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tgh.beans.JPAgenerated.Achat;
import org.tgh.beans.JPAgenerated.AchatPK;
import org.tgh.beans.JPAgenerated.Contact;

public class AjoutAchatService {

	private static Logger logger = LoggerFactory.getLogger(AjoutAchatService.class);

	private final EntityManagerFactory entityManagerFactory;
	
	
	public AjoutAchatService(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
	/**
	 * 
	 * @return
	 */
	EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}
	
	/**
	 * 
	 * @return
	 */
	EntityManager getEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}
	
	
	/**
	 * 
	 * @param contactId
	 */
	public void process(Long contactId, Date dateAchat, Double montant, String modePaiement, String typeAchat, String observation) throws Exception {
		EntityManager entityManager = getEntityManager();
		// Recherche du contact.
		Query query = entityManager.createQuery("select contact from RensContact contact where contact.id=?1");
		query.setParameter(1, contactId);
		
		
		try {
			Contact contactTrouve = (Contact)query.getSingleResult();
			logger.warn("Contact trouvé");
			logger.warn(contactTrouve.toString());
			
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				transaction.begin();
				contactTrouve.setNomEnt("Sopra Group");
				
				// Ajout d'une achat 
				AchatPK key = new AchatPK();
				key.setIdContact(contactTrouve.getId());
				key.setDateAchat(dateAchat);
				
				Achat nouvelAchat = new Achat();
				nouvelAchat.setId(key);
				nouvelAchat.setModePaiement(modePaiement);
				nouvelAchat.setMontant(montant);
				nouvelAchat.setObservation(observation);
				nouvelAchat.setType(typeAchat);
				
				
				List<Achat> achatsExistants = contactTrouve.getAchats();
				if (achatsExistants == null) {
					achatsExistants = new ArrayList<Achat>();
				}
				achatsExistants.add(nouvelAchat);
				contactTrouve.setAchats(achatsExistants);
				
				entityManager.persist(contactTrouve);
				transaction.commit();
				
				logger.info("La transaction s'est déroulée correctement");
				
			} catch (Exception e2) {
				transaction.rollback();
				logger.warn("La transaction a échoué");
				throw e2;
			}

		} catch (NoResultException e) {
			logger.error("Contact pas trouvé a la première tentative");
			throw e;
		} 
		finally {
			entityManager.clear();
			entityManager.close();
		}

	}
}
