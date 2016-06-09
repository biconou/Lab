package org.tgh.beans.JPAgenerated;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the achats database table.
 * 
 */
@Embeddable
public class AchatPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_contact")
	private String idContact;

    @Temporal( TemporalType.DATE)
	@Column(name="date_achat")
	private java.util.Date dateAchat;

    public AchatPK() {
    }
	public String getIdContact() {
		return this.idContact;
	}
	public void setIdContact(String idContact) {
		this.idContact = idContact;
	}
	public java.util.Date getDateAchat() {
		return this.dateAchat;
	}
	public void setDateAchat(java.util.Date dateAchat) {
		this.dateAchat = dateAchat;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AchatPK)) {
			return false;
		}
		AchatPK castOther = (AchatPK)other;
		return 
			this.idContact.equals(castOther.idContact)
			&& this.dateAchat.equals(castOther.dateAchat);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idContact.hashCode();
		hash = hash * prime + this.dateAchat.hashCode();
		
		return hash;
    }
}