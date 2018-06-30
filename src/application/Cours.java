package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cours {

	private IntegerProperty idCours;
    private StringProperty groupe;
    private StringProperty session;
    private StringProperty matiere;
    private StringProperty salle;
    private StringProperty date;
    private StringProperty heureDebut;
    private StringProperty heureFin;
    private StringProperty enseignant;
    
    public Cours() {
    }

	public Cours(int idCours, String groupe, String session, String matiere,
			String salle, String date, String heureDebut, String heureFin,
			String enseignant) {
		this.idCours = new SimpleIntegerProperty(idCours);
		this.groupe =new SimpleStringProperty(groupe);
		this.session = new SimpleStringProperty(session);
		this.matiere = new SimpleStringProperty(matiere);
		this.salle = new SimpleStringProperty(salle);
		this.date = new SimpleStringProperty(date);
		this.heureDebut = new SimpleStringProperty(heureDebut);
		this.heureFin = new SimpleStringProperty(heureFin);
		this.enseignant = new SimpleStringProperty(enseignant);
	}

	public IntegerProperty getIdCours() {
		return idCours;
	}

	public void setIdCours(IntegerProperty idCours) {
		this.idCours = idCours;
	}

	public String getGroupe() {
		return groupe.get();
	}

	public void setGroupe(StringProperty groupe) {
		this.groupe = groupe;
	}

	public String getSession() {
		return session.get();
	}

	public void setSession(StringProperty session) {
		this.session = session;
	}

	public String getMatiere() {
		return matiere.get();
	}

	public void setMatiere(StringProperty matiere) {
		this.matiere = matiere;
	}

	public String getSalle() {
		return salle.get();
	}

	public void setSalle(StringProperty salle) {
		this.salle = salle;
	}

	public String getDate() {
		return date.get();
	}

	public void setDate(StringProperty date) {
		this.date = date;
	}

	public String getHeureDebut() {
		return heureDebut.get();
	}

	public void setHeureDebut(StringProperty heureDebut) {
		this.heureDebut = heureDebut;
	}

	public String getHeureFin() {
		return heureFin.get();
	}

	public void setHeureFin(StringProperty heureFin) {
		this.heureFin = heureFin;
	}

	public String getEnseignant() {
		return enseignant.get();
	}

	public void setEnseignant(StringProperty enseignant) {
		this.enseignant = enseignant;
	}
    
    
}
