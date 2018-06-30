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
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

public class GestionIndividusController implements Initializable {

	@FXML
	private Button btnRetour;
	
	@FXML private Button deleteSelectionRow;

	@FXML
	private TableView<Individu> tabView;
	@FXML
	private TableColumn<Individu, String> tabNom;
	@FXML
	private TableColumn<Individu, String> tabPrenom;
	@FXML
	private TableColumn<Individu, String> tabStatut;
	@FXML
	private TableColumn<Individu, String> tabEmail;
	@FXML
	private TableColumn<Individu, String> tabTel;
	@FXML
	private TableColumn<Individu, String> tabAdresse;
	@FXML
	private ComboBox<String> comboStatut;

	ObservableList<String> options = FXCollections.observableArrayList("Administrateur", "Enseignant", "Directeur",
			"Secrétaire", "Etudiant");

	private ObservableList<Individu> data;

	@FXML
	private TextField textNom;
	@FXML
	private TextField textPrenom;
	@FXML
	private TextField textEmail;
	@FXML
	private TextField textTel;
	@FXML
	private TextField textAdresse;
	
	@FXML
	private ImageView imageClose;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		Connection conn2 = null;

		try {
			conn2 = ConnectionBD.getConnection();
			data = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = conn2.createStatement().executeQuery("SELECT * FROM individu");

			while (rs.next()) {
				// get string from db,whichever way
				data.add(new Individu(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(8), rs.getString(9)));
			}

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		tabNom.setCellValueFactory(new PropertyValueFactory<>("nomInd"));
		tabPrenom.setCellValueFactory(new PropertyValueFactory<>("prenomInd"));
		tabStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
		tabEmail.setCellValueFactory(new PropertyValueFactory<>("emailInd"));
		tabTel.setCellValueFactory(new PropertyValueFactory<>("telephoneInd"));
		tabAdresse.setCellValueFactory(new PropertyValueFactory<>("adresseInd"));

		tabView.setItems(null);
		tabView.setItems(data);

		setCellValueFromTableToTextField();

		comboStatut.setItems(options);
		comboStatut.setValue("Administrateur");
		
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
	private void DeleteDataFromDatabase(ActionEvent event) {

		Connection conn = null;
		PreparedStatement ps = null;

		int idSup = 0;

		try {

			idSup = tabView.getSelectionModel().getSelectedItem().getIdInd().getValue();

			conn = ConnectionBD.getConnection();

			String queryDelete = "DELETE FROM individu WHERE idIndividu = '" + idSup + "' ";
			ps = conn.prepareStatement(queryDelete);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Vous n'avez sélectionné aucun individu a supprimé !");

			alert.showAndWait();

		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Notification");
		alert.setHeaderText(null);
		alert.setContentText("L'individu " + textNom.getText() + " " + textPrenom.getText() + " a bien été supprimé.");

		alert.showAndWait();

		tabView.getItems().removeAll(tabView.getSelectionModel().getSelectedItem());
		textNom.clear();
		textPrenom.clear();
		textTel.clear();
		textAdresse.clear();
		textEmail.clear();

	}

	@FXML
	private void handleButtonRetour(ActionEvent event) throws IOException, SQLException {

		Stage stage = (Stage) btnRetour.getScene().getWindow();
		stage.close();
	}

	@SuppressWarnings("null")
	@FXML
	private void modifierIndividu(ActionEvent event) {

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = ConnectionBD.getConnection();
			int idSup = tabView.getSelectionModel().getSelectedItem().getIdInd().getValue();
			String query = "Update individu set nomInd=?, prenomInd=?, adresseInd=?, telInd=?, emailInd=?, statutInd=? where idIndividu= '"
					+ idSup + "' ";


			ps = conn.prepareStatement(query);

			ps.setString(1, textNom.getText());
			ps.setString(2, textPrenom.getText());
			ps.setString(3, textAdresse.getText());
			ps.setString(4, textTel.getText());
			ps.setString(5, textEmail.getText());
			ps.setString(6, comboStatut.getValue());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Connection conn2 = null;

		try {
			conn2 = ConnectionBD.getConnection();
			data = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = conn2.createStatement().executeQuery("SELECT * FROM individu");

			while (rs.next()) {
				// get string from db,whichever way
				data.add(new Individu(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(8), rs.getString(9)));
			}

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		tabNom.setCellValueFactory(new PropertyValueFactory<>("nomInd"));
		tabPrenom.setCellValueFactory(new PropertyValueFactory<>("prenomInd"));
		tabStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
		tabEmail.setCellValueFactory(new PropertyValueFactory<>("emailInd"));
		tabTel.setCellValueFactory(new PropertyValueFactory<>("telephoneInd"));
		tabAdresse.setCellValueFactory(new PropertyValueFactory<>("adresseInd"));

		tabView.setItems(null);
		tabView.setItems(data);

		setCellValueFromTableToTextField();

		comboStatut.setItems(options);
		comboStatut.setValue("Administrateur");

	}

	private void setCellValueFromTableToTextField() {
		tabView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Individu ind = tabView.getItems().get(tabView.getSelectionModel().getSelectedIndex());
				textNom.setText(ind.getNomInd());
				textPrenom.setText(ind.getPrenomInd());
				textEmail.setText(ind.getEmailInd());
				textTel.setText(ind.getTelephoneInd());
				textAdresse.setText(ind.getAdresseInd());
				comboStatut.setValue(ind.getStatut());
			}
		});
	}

}
