package org.tgh.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rens_contact database table.
 * 
 */
@Entity
@Table(name="rens_contact")
public class RensContact implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String adresse;

	private String adresse2;

	private String adresse3;

	private String adresse4;

	private String bulltri;

	@Column(name="categorie_presse")
	private String categoriePresse;

	@Column(name="code_postal")
	private String codePostal;

    @Temporal( TemporalType.DATE)
	private Date datecreation;

    @Temporal( TemporalType.DATE)
	private Date datemaj;

	private String email;

	private String invite;

	@Column(name="lib_titre")
	private String libTitre;

	private String newsletter;

	private String nom;

	@Column(name="nom_ent")
	private String nomEnt;

	@Column(name="nom_etat")
	private String nomEtat;

	@Column(name="nom_pays")
	private String nomPays;

	@Column(name="nom_source")
	private String nomSource;

	private String numfax;

	private String numport;

	private String numtel;

	private String observation;

	private String prenom;

	private String profession;

	private String siteweb;

	@Column(name="total_don")
	private double totalDon;

	private String ville;

    public RensContact() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getAdresse2() {
		return this.adresse2;
	}

	public void setAdresse2(String adresse2) {
		this.adresse2 = adresse2;
	}

	public String getAdresse3() {
		return this.adresse3;
	}

	public void setAdresse3(String adresse3) {
		this.adresse3 = adresse3;
	}

	public String getAdresse4() {
		return this.adresse4;
	}

	public void setAdresse4(String adresse4) {
		this.adresse4 = adresse4;
	}

	public String getBulltri() {
		return this.bulltri;
	}

	public void setBulltri(String bulltri) {
		this.bulltri = bulltri;
	}

	public String getCategoriePresse() {
		return this.categoriePresse;
	}

	public void setCategoriePresse(String categoriePresse) {
		this.categoriePresse = categoriePresse;
	}

	public String getCodePostal() {
		return this.codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public Date getDatecreation() {
		return this.datecreation;
	}

	public void setDatecreation(Date datecreation) {
		this.datecreation = datecreation;
	}

	public Date getDatemaj() {
		return this.datemaj;
	}

	public void setDatemaj(Date datemaj) {
		this.datemaj = datemaj;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInvite() {
		return this.invite;
	}

	public void setInvite(String invite) {
		this.invite = invite;
	}

	public String getLibTitre() {
		return this.libTitre;
	}

	public void setLibTitre(String libTitre) {
		this.libTitre = libTitre;
	}

	public String getNewsletter() {
		return this.newsletter;
	}

	public void setNewsletter(String newsletter) {
		this.newsletter = newsletter;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNomEnt() {
		return this.nomEnt;
	}

	public void setNomEnt(String nomEnt) {
		this.nomEnt = nomEnt;
	}

	public String getNomEtat() {
		return this.nomEtat;
	}

	public void setNomEtat(String nomEtat) {
		this.nomEtat = nomEtat;
	}

	public String getNomPays() {
		return this.nomPays;
	}

	public void setNomPays(String nomPays) {
		this.nomPays = nomPays;
	}

	public String getNomSource() {
		return this.nomSource;
	}

	public void setNomSource(String nomSource) {
		this.nomSource = nomSource;
	}

	public String getNumfax() {
		return this.numfax;
	}

	public void setNumfax(String numfax) {
		this.numfax = numfax;
	}

	public String getNumport() {
		return this.numport;
	}

	public void setNumport(String numport) {
		this.numport = numport;
	}

	public String getNumtel() {
		return this.numtel;
	}

	public void setNumtel(String numtel) {
		this.numtel = numtel;
	}

	public String getObservation() {
		return this.observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getProfession() {
		return this.profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getSiteweb() {
		return this.siteweb;
	}

	public void setSiteweb(String siteweb) {
		this.siteweb = siteweb;
	}

	public double getTotalDon() {
		return this.totalDon;
	}

	public void setTotalDon(double totalDon) {
		this.totalDon = totalDon;
	}

	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

}