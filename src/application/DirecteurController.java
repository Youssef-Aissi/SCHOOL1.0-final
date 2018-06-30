package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class DirecteurController implements Initializable {

	@FXML
	private Button buttonCreer;
	@FXML
	private Button btnRetour;

	@FXML
	private Button btnValider;
	
	@FXML
	private ImageView imageClose;
	
	@FXML
	private Label login;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//on récupère le login du directeur
		String log = AccueilController.log;
		login.setText(log);
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
					// get a handle to the stage
					Stage stage = (Stage) imageClose.getScene().getWindow();
					// do what you have to do
					stage.close();
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

	@FXML
	private void handleButtonInscrire(ActionEvent event) throws IOException, SQLException {

		Parent root = FXMLLoader.load(getClass().getResource("/View/inscriptionDirecteur.fxml"));
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(new Scene(root));
		stage.show();

	}
	
	@FXML
	private void handleButtonIndividus(ActionEvent event) throws IOException, SQLException {

		Parent root = FXMLLoader.load(getClass().getResource("/View/gestionIndividus.fxml"));
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	@FXML
	private void handleButtonGroupes(ActionEvent event) throws IOException, SQLException {

		Parent root = FXMLLoader.load(getClass().getResource("/View/gestionGroupes.fxml"));
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public void sendText(String text) {
		login.setText(text);
	}

}
