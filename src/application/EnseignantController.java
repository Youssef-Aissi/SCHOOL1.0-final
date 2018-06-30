package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class EnseignantController implements Initializable {

	@FXML
	private ImageView imageClose;

	@FXML
	private Label login;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
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

	@FXML
	private void handleAjoutCours(ActionEvent event) throws IOException, SQLException {

		Parent root = FXMLLoader.load(getClass().getResource("/View/ajoutCours.fxml"));
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(new Scene(root));
		stage.show();

	}
	
	public void sendText(String text) {
		login.setText(text);
	}

}
