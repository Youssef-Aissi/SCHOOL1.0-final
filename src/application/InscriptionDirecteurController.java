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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

public class InscriptionDirecteurController implements Initializable {

	@FXML
	private Button btnValider;
	
	@FXML private Button btnRetour;

	@FXML
	private TextField textNom;

	@FXML
	private TextField textPrenom;

	@FXML
	private TextField textAdresse;

	@FXML
	private TextField textTel;

	@FXML
	private TextField textEmail;

	@FXML
	private TextField textLogin;

	@FXML
	private TextField textMdp;
	
	@FXML
	private ImageView imageClose;
	
	@FXML
	private ComboBox<String> comboStatut;

	ObservableList<String> options = FXCollections.observableArrayList("Directeur", "Enseignant","Etudiant", "Secrétaire");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		comboStatut.setItems(options);
		comboStatut.setValue("Directeur");

		textTel.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("\\d*")) {
					textTel.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}

		});
		
	/*	imageClose.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {				
				
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("Attention !");
				alert.setContentText("Étes-vous sûr de vouloir fermer l'application ?");

				ButtonType btnOui = new ButtonType("Oui");
				ButtonType btnNon = new ButtonType("Non");
				
				alert.getButtonTypes().setAll(btnOui, btnNon);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == btnOui){
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

		Stage stage = (Stage) btnRetour.getScene().getWindow();
	    stage.close();
	    
	}

	@FXML
	private void handleButtonInscrire(ActionEvent event) throws IOException, SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		if (!textNom.getText().equals("") && !textPrenom.getText().equals("") && !textAdresse.getText().equals("")
				&& !textEmail.getText().equals("") && !textTel.getText().equals("") && !textLogin.getText().equals("")
				&& !textMdp.getText().equals("")) {
			try {
				conn = ConnectionBD.getConnection();

				String query = "INSERT INTO individu(nomInd, prenomInd, adresseInd, telInd, emailInd, statutInd, loginInd, mdpInd) VALUES (?,?,?,?,?,?,?,?)";

				ps2 = conn.prepareStatement(query);

				ps2.setString(1, textNom.getText());
				ps2.setString(2, textPrenom.getText());
				ps2.setString(3, textAdresse.getText());
				ps2.setString(4, textTel.getText());
				ps2.setString(5, textEmail.getText());
				ps2.setString(6, comboStatut.getValue());
				ps2.setString(7, textLogin.getText());
				ps2.setString(8, textMdp.getText());

				ps2.executeUpdate();

				textNom.clear();
				textPrenom.clear();
				textAdresse.clear();
				textTel.clear();
				textEmail.clear();
				textLogin.clear();
				textMdp.clear();
				comboStatut.setValue("Directeur");

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Notification");
				alert.setHeaderText(null);
				alert.setContentText("L'individu a bien été inscrit !");

				alert.showAndWait();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Problème d'inscription");
			alert.setContentText("Vous n'avez pas rempli tous les champs !");

			alert.showAndWait();
		}

	}

	
}
