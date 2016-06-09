package org.tgh.beans.JPAgenerated;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the achats database table.
 * 
 */
@Entity
@Table(name="achats")
public class Achat implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AchatPK id;

	@Column(name="mode_paiement")
	private String modePaiement;

	private double montant;

	private String observation;

	private String type;

    public Achat() {
    }

	public AchatPK getId() {
		return this.id;
	}

	public void setId(AchatPK id) {
		this.id = id;
	}
	
	public String getModePaiement() {
		return this.modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getObservation() {
		return this.observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}