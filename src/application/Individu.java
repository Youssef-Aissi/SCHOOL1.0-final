package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Individu{

    private IntegerProperty idInd;
    private StringProperty nomInd;
    private StringProperty prenomInd;
    private StringProperty adresseInd;
    private StringProperty telephoneInd;
    private StringProperty emailInd;
    private StringProperty statut;
    private StringProperty identifiant;
    private StringProperty motDePasseInd;
    private StringProperty groupe;

    public Individu() {
    }

    public Individu(int idInd, String nomInd, String prenomInd, String adresseInd, String telephoneInd, String emailInd, String statut, String identifiant, String motDePasseInd, String groupe) {
        this.idInd = new SimpleIntegerProperty(idInd);
        this.nomInd = new SimpleStringProperty(nomInd);
        this.prenomInd = new SimpleStringProperty(prenomInd);
        this.adresseInd = new SimpleStringProperty(adresseInd);
        this.telephoneInd = new SimpleStringProperty(telephoneInd);
        this.emailInd = new SimpleStringProperty(emailInd);
        this.statut = new SimpleStringProperty(statut);
        this.identifiant = new SimpleStringProperty(identifiant);
        this.motDePasseInd = new SimpleStringProperty(motDePasseInd);
        this.groupe = new SimpleStringProperty(groupe);
    }


	public IntegerProperty getIdInd() {
		return idInd;
	}

	public void setIdInd(IntegerProperty idInd) {
		this.idInd = idInd;
	}

	public String getNomInd() {
		return nomInd.get();
	}

	public void setNomInd(StringProperty nomInd) {
		this.nomInd = nomInd;
	}

	public String getPrenomInd() {
		return prenomInd.get();
	}

	public void setPrenomInd(StringProperty prenomInd) {
		this.prenomInd = prenomInd;
	}

	public String getAdresseInd() {
		return adresseInd.get();
	}

	public void setAdresseInd(StringProperty adresseInd) {
		this.adresseInd = adresseInd;
	}

	public String getTelephoneInd() {
		return telephoneInd.get();
	}

	public void setTelephoneInd(StringProperty telephoneInd) {
		this.telephoneInd = telephoneInd;
	}

	public String getEmailInd() {
		return emailInd.get();
	}

	public void setEmailInd(StringProperty emailInd) {
		this.emailInd = emailInd;
	}

	public String getStatut() {
		return statut.get();
	}

	public void setStatut(StringProperty statut) {
		this.statut = statut;
	}

	public String getIdentifiant() {
		return identifiant.get();
	}

	public void setIdentifiant(StringProperty identifiant) {
		this.identifiant = identifiant;
	}

	public String getMotDePasseInd() {
		return motDePasseInd.get();
	}

	public void setMotDePasseInd(StringProperty motDePasseInd) {
		this.motDePasseInd = motDePasseInd;
	}
	
	public String getGroupe() {
		return groupe.get();
	}

	public void setGroupe(StringProperty groupe) {
		this.groupe = groupe;
	}

   
}