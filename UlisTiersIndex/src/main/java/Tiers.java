import java.math.BigDecimal;

/**
 * Paquet de définition
 **/

public class Tiers {

    private String codeTiers;
    private String qualiteTiers;
    private String libelleTiers;
    private String adresseNumeroVoie;
    private String adresseTypeVoie;
    private String adresseNomVoie;
    private String adresseCodePostal;
    private String adresseLibelleCommune;
    private BigDecimal adresseGeoPointLat;
    private BigDecimal adresseGeoPointLng;
    private String role;




    public String getAdresse() {
        return adresseNumeroVoie==null?"":adresseNumeroVoie
                +" "+adresseTypeVoie==null?"":adresseTypeVoie
                +" "+adresseNomVoie==null?"":adresseNomVoie
                +" "+adresseCodePostal==null?"":adresseCodePostal
                +" "+adresseLibelleCommune==null?"":adresseLibelleCommune;
    }

    public String getAdresseGeoPoint() {
        if (adresseGeoPointLng == null || adresseGeoPointLat == null) {
            return null;
        } else {
            return adresseGeoPointLat + "," + adresseGeoPointLng;
        }
    }

    public String getCodeTiers() {
        return codeTiers;
    }

    public void setCodeTiers(String codeTiers) {
        this.codeTiers = codeTiers;
    }

    public String getQualiteTiers() {
        return qualiteTiers;
    }

    public void setQualiteTiers(String qualiteTiers) {
        this.qualiteTiers = qualiteTiers;
    }

    public String getLibelleTiers() {
        return libelleTiers;
    }

    public void setLibelleTiers(String libelleTiers) {
        this.libelleTiers = libelleTiers;
    }

    public String getAdresseNumeroVoie() {
        return adresseNumeroVoie;
    }

    public void setAdresseNumeroVoie(String adresseNumeroVoie) {
        this.adresseNumeroVoie = adresseNumeroVoie;
    }

    public String getAdresseTypeVoie() {
        return adresseTypeVoie;
    }

    public void setAdresseTypeVoie(String adresseTypeVoie) {
        this.adresseTypeVoie = adresseTypeVoie;
    }

    public String getAdresseNomVoie() {
        return adresseNomVoie;
    }

    public void setAdresseNomVoie(String adresseNomVoie) {
        this.adresseNomVoie = adresseNomVoie;
    }

    public String getAdresseCodePostal() {
        return adresseCodePostal;
    }

    public void setAdresseCodePostal(String adresseCodePostal) {
        this.adresseCodePostal = adresseCodePostal;
    }

    public String getAdresseLibelleCommune() {
        return adresseLibelleCommune;
    }

    public void setAdresseLibelleCommune(String adresseLibelleCommune) {
        this.adresseLibelleCommune = adresseLibelleCommune;
    }

    public BigDecimal getAdresseGeoPointLat() {
        return adresseGeoPointLat;
    }

    public void setAdresseGeoPointLat(BigDecimal adresseGeoPointLat) {
        this.adresseGeoPointLat = adresseGeoPointLat;
    }

    public BigDecimal getAdresseGeoPointLng() {
        return adresseGeoPointLng;
    }

    public void setAdresseGeoPointLng(BigDecimal adresseGeoPointLng) {
        this.adresseGeoPointLng = adresseGeoPointLng;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
 
