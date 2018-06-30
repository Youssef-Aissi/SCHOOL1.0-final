package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class EtudiantController implements Initializable {

	@FXML
	private Button btnRetour;

	@FXML
	private Label login;

	@FXML
	private ImageView imageClose;
	@FXML
	private TableView<Individu> tabView;
	@FXML
	private TableColumn<Individu, String> tabNom;
	@FXML
	private TableColumn<Individu, String> tabPrenom;
	@FXML
	private TableColumn<Individu, String> tabEmail;
	@FXML
	private TableColumn<Individu, String> tabGroupe;
	@FXML
	private TableView<Cours> tabView2;
	@FXML
	private TableColumn<Cours, String> tabMatiere;
	@FXML
	private TableColumn<Cours, String> tabSalle;
	@FXML
	private TableColumn<Cours, String> tabSession;
	@FXML
	private TableColumn<Cours, String> tabDate;
	@FXML
	private TableColumn<Cours, String> tabDebut;
	@FXML
	private TableColumn<Cours, String> tabFin;
	@FXML
	private TableColumn<Cours, String> tabEnseignant;

	private ObservableList<Individu> data;
	
	private ObservableList<Cours> data2;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Connection conn = null;

		//remplissage des membres du groupe
		try {
			conn = ConnectionBD.getConnection();
			data = FXCollections.observableArrayList();
			//on récupère le login de l'étudiant pour récupérer ensuite son groupe
			String log = AccueilController.log;
			login.setText(log);
			// Execute query and store result in a resultset
			ResultSet rsGroupe = conn.createStatement()
					.executeQuery("SELECT groupe FROM individu Where loginInd='" + log + "' ");
			rsGroupe.next();
			String groupe = rsGroupe.getString(1);
			
			//on récupère mtn tous les étudiants faisant parti de son groupe
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM individu Where groupe='" + groupe + "' ");

			while (rs.next()) {
				// get string from db,whichever way
				data.add(new Individu(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
			}

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		tabNom.setCellValueFactory(new PropertyValueFactory<>("nomInd"));
		tabPrenom.setCellValueFactory(new PropertyValueFactory<>("prenomInd"));
		tabEmail.setCellValueFactory(new PropertyValueFactory<>("emailInd"));
		tabGroupe.setCellValueFactory(new PropertyValueFactory<>("groupe"));

		tabView.setItems(null);
		tabView.setItems(data);

		//remplissage de l'emploi du temps
		try {
			conn = ConnectionBD.getConnection();
			data2 = FXCollections.observableArrayList();
			//on récupère le login de l'étudiant pour récupérer ensuite son groupe
			String log = AccueilController.log;
			// Execute query and store result in a resultset
			ResultSet rsGroupe = conn.createStatement()
					.executeQuery("SELECT groupe FROM individu Where loginInd='" + log + "' ");
			rsGroupe.next();
			String groupe = rsGroupe.getString(1);
			//on récupère mtn tous les cours correspondant au groupe de l'étudiant
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM cours Where groupe='" + groupe + "' ");

			while (rs.next()) {
				// get string from db,whichever way
				data2.add(new Cours(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
			}

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}
		
		tabSession.setCellValueFactory(new PropertyValueFactory<>("session"));
		tabMatiere.setCellValueFactory(new PropertyValueFactory<>("matiere"));
		tabSalle.setCellValueFactory(new PropertyValueFactory<>("salle"));
		tabDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		tabDebut.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));
		tabFin.setCellValueFactory(new PropertyValueFactory<>("heureFin"));
		tabEnseignant.setCellValueFactory(new PropertyValueFactory<>("enseignant"));

		tabView2.setItems(null);
		tabView2.setItems(data2);


		/*imageClose.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("Attention !");
				alert.setContentText("Étes-vous sûr de vouloir fermer l'application ?");

				ButtonType btnOui = new ButtonType("Oui");
				ButtonType btnNon = new ButtonType("Non");

				alert.getButtonTypes().setAll(btnOui, btnNon);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == btnOui) {
					// close application
					Platform.exit();
				} else {
					alert.close();
				}
			}
		});*/
	}

	@FXML
	private void handleButtonRetour(ActionEvent event) throws IOException, SQLException {

		Node source = (Node) event.getSource();
		Window theStage = source.getScene().getWindow();
		theStage.hide();

		Parent root = FXMLLoader.load(getClass().getResource("/View/accueil.fxml"));
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(new Scene(root));
		stage.show();

	}

}
