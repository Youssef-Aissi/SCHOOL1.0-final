package application;

import java.io.IOException;

import java.net.URL;
import java.rmi.server.LoaderHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class AccueilController implements Initializable {

	Scene scene2;

	@FXML
	private Button btnConn;

	@FXML
	private Button btnClose;

	@FXML
	private ImageView imageClose;

	@FXML
	private TextField textId;

	@FXML
	private TextField textMdp;
	
	public static String log;

	@FXML
	private void handleButtonAction1(ActionEvent event) throws IOException, SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		try {
			conn = ConnectionBD.getConnection();

			int idStatut = 0;

			String statut = comboStatut.getValue();
			String queryVerif = "Select * from statut where nomStatut = '" + statut + "' ";
			ps = conn.prepareStatement(queryVerif);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				idStatut = rs.getInt("idStatut");
			}

			ps.close();
			String query = "Select * from individu where loginInd = '" + textId.getText() + "' AND mdpInd = '"
					+ textMdp.getText() + "' AND statutInd = '" + comboStatut.getValue() + "' ";

			ps2 = conn.prepareStatement(query);
			ResultSet rs2 = ps2.executeQuery();

			if (rs2.next()) {
				Node source = (Node) event.getSource();
				Window theStage = source.getScene().getWindow();
				theStage.hide();
				
				FXMLLoader loader = null;
				EtudiantController etController = null;
				AdminController adController = null;
				SecretaireController seController = null;
				EnseignantController esController = null;
				DirecteurController diController = null;
				log = textId.getText();


				switch (idStatut) {

				case 1:

					loader = new FXMLLoader(getClass().getResource("/View/accueilAdmin.fxml"));
					Parent root2 = (Parent) loader.load();
					
					adController = loader.getController();
					
					Stage stage = new Stage();
					stage.initStyle(StageStyle.UNDECORATED);
					stage.setScene(new Scene(root2));
					stage.show();
					//System.out.println("Connecté");

					break;

				case 2:

					
					loader = new FXMLLoader(getClass().getResource("/View/accueilEtudiant.fxml"));
					Parent root3 = (Parent) loader.load();
					etController = loader.getController();
					
					Stage stage3 = new Stage();
					stage3.initStyle(StageStyle.UNDECORATED);
					stage3.setScene(new Scene(root3));
					stage3.show();
					//System.out.println("Connecté");

					break;

				case 3:

					loader = new FXMLLoader(getClass().getResource("/View/accueilSecretaire.fxml"));
					Parent root4 = (Parent) loader.load();
					
					seController = loader.getController();
					
					Stage stage4 = new Stage();
					stage4.initStyle(StageStyle.UNDECORATED);
					stage4.setScene(new Scene(root4));
					stage4.show();
					//System.out.println("Connecté");

					break;
					
				case 4:

					loader = new FXMLLoader(getClass().getResource("/View/accueilDirecteur.fxml"));
					Parent root5 = (Parent) loader.load();
					
					diController = loader.getController();

					Stage stage5 = new Stage();
					stage5.initStyle(StageStyle.UNDECORATED);
					stage5.setScene(new Scene(root5));
					stage5.show();
					//System.out.println("Connecté");

					break;

					
				case 5:

					loader = new FXMLLoader(getClass().getResource("/View/accueilEnseignant.fxml"));
					Parent root6 = (Parent) loader.load();
					
					esController = loader.getController();
					
					Stage stage6 = new Stage();
					stage6.initStyle(StageStyle.UNDECORATED);
					stage6.setScene(new Scene(root6));
					stage6.show();
					//System.out.println("Connecté");

					break;


				default:

				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Problème de connexion");
				alert.setContentText("Un de vos identifiants est incorrect !");

				alert.showAndWait();
				//System.out.println("Non connecté");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private ComboBox<String> comboStatut;

	ObservableList<String> options = FXCollections.observableArrayList("Administrateur", "Enseignant", "Directeur",
			"Secrétaire", "Etudiant");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		comboStatut.setItems(options);
		comboStatut.setValue("Administrateur");

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
				if (result.get() == btnOui){
					// close application
					Platform.exit();
				} else {
				    alert.close();
				}
			}
		});*/

	}

}
