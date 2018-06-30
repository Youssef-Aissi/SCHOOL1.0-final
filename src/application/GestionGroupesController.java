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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GestionGroupesController implements Initializable {

	@FXML
	private Button btnRetour;
	
	@FXML
	private ImageView imageClose;
	
	@FXML
	private TableView<Individu> tabView;
	@FXML
	private TableColumn<Individu, String> tabNom;
	@FXML
	private TableColumn<Individu, String> tabPrenom;
	@FXML
	private TableColumn<Individu, String> tabGroupe;
	
	@FXML
	private ComboBox<String> comboGroupe;
	
	ObservableList<String> options = FXCollections.observableArrayList();

	private ObservableList<Individu> data;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		
		try {
			conn = ConnectionBD.getConnection();
			data = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM individu Where statutInd='Etudiant' ");
			ResultSet rs2 = conn.createStatement().executeQuery("Select Libelle_Groupe from groupe");
			while (rs.next()) {
				// get string from db,whichever way
				data.add(new Individu(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
			}
			
			while(rs2.next()) {
				options.add(rs2.getString(1));
			}

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}
		
		tabNom.setCellValueFactory(new PropertyValueFactory<>("nomInd"));
		tabPrenom.setCellValueFactory(new PropertyValueFactory<>("prenomInd"));
		tabGroupe.setCellValueFactory(new PropertyValueFactory<>("groupe"));

		tabView.setItems(null);
		tabView.setItems(data);
		
		comboGroupe.setItems(options);
		comboGroupe.setValue(options.get(0));
		
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
	
	@FXML private Button creerGroupe;
	
	@FXML
	private TextField nomGroupe;

	@FXML
	private void handleCreerGroupe(ActionEvent event) throws IOException, SQLException {

		Connection conn = null;
		PreparedStatement ps = null;

		if (!nomGroupe.getText().equals("")) {
			try {
				conn = ConnectionBD.getConnection();

				String query = "INSERT INTO groupe(Libelle_Groupe) VALUES (?)";

				ps = conn.prepareStatement(query);

				ps.setString(1, nomGroupe.getText());

				ps.executeUpdate();

				nomGroupe.clear();
				
				ResultSet rs2 = conn.createStatement().executeQuery("Select Libelle_Groupe from groupe");

				options.clear();
				while(rs2.next()) {
					options.add(rs2.getString(1));
				}
				
				
				comboGroupe.setItems(options);
				comboGroupe.setValue(options.get(0));

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Notification");
				alert.setHeaderText(null);
				alert.setContentText("Le groupe a bien été créé !");

				alert.showAndWait();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Problème de création du groupe");
			alert.setContentText("Veuillez saisir un nom de groupe !");

			alert.showAndWait();
		}
	}
	
	@FXML
	private void modifierGroupe(ActionEvent event) {

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = ConnectionBD.getConnection();
			int idSup = tabView.getSelectionModel().getSelectedItem().getIdInd().getValue();
			String query = "Update individu set groupe=? where idIndividu= '"
					+ idSup + "' ";


			ps = conn.prepareStatement(query);

			ps.setString(1, comboGroupe.getValue());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Connection conn2 = null;

		try {
			conn2 = ConnectionBD.getConnection();
			data = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = conn2.createStatement().executeQuery("SELECT * FROM individu Where statutInd='Etudiant' ");

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
		tabGroupe.setCellValueFactory(new PropertyValueFactory<>("groupe"));


		tabView.setItems(null);
		tabView.setItems(data);

		setCellValueFromTableToTextField();

		comboGroupe.setItems(options);
		comboGroupe.setValue(options.get(0));

	}

	private void setCellValueFromTableToTextField() {
		tabView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Individu ind = tabView.getItems().get(tabView.getSelectionModel().getSelectedIndex());
				
			}
		});
	}
	
	@FXML
	private void handleButtonRetour(ActionEvent event) throws IOException, SQLException {

		Stage stage = (Stage) btnRetour.getScene().getWindow();
		stage.close();
	}

	
	
	
}
