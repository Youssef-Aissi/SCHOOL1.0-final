package application;

import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class AjoutCoursController implements Initializable {

	@FXML
	private ImageView imageClose;

	@FXML
	private Button btnCreer;

	@FXML
	private TextField session;
	@FXML
	private TextField matiere;
	@FXML
	private TextField salle;
	@FXML
	private TextField enseignant;
	@FXML
	private DatePicker date;

	@FXML
	private Button btnRetour;

	@FXML
	private ComboBox<String> comboDebut;

	ObservableList<String> optionsDebut = FXCollections.observableArrayList("8h", "9h", "10h", "11h", "12h", "13h",
			"14h", "15h", "16h", "17h");

	@FXML
	private ComboBox<String> comboFin;

	ObservableList<String> optionsFin = FXCollections.observableArrayList("9h", "10h", "11h", "12h", "13", "14h", "15h",
			"16h", "17h", "18h");

	@FXML
	private ComboBox<String> comboGroupe;

	ObservableList<String> options = FXCollections.observableArrayList();

	// Factory to create Cell of DatePicker
	private Callback<DatePicker, DateCell> getDayCellFactory() {

		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						// Disable Monday, Tueday, Wednesday.
						if (item.getDayOfWeek() == DayOfWeek.SUNDAY //
								|| item.getDayOfWeek() == DayOfWeek.SATURDAY) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}
					}
				};
			}
		};
		return dayCellFactory;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		comboDebut.setItems(optionsDebut);
		comboDebut.setValue("8h");

		comboFin.setItems(optionsFin);
		comboFin.setValue("9h");

		//DatePicker datePicker = new DatePicker();
		//date.setValue(LocalDate.of(2016, 7, 25));
		date.setShowWeekNumbers(true);

		// Factory to create Cell of DatePicker
		Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
		date.setDayCellFactory(dayCellFactory);

		Connection conn = null;

		try {
			conn = ConnectionBD.getConnection();
			String log = AccueilController.log;
			ResultSet rsNom = conn.createStatement()
					.executeQuery("SELECT nomInd, prenomInd FROM individu Where loginInd='" + log + "' ");
			rsNom.next();
			String nomPrenEns = rsNom.getString(1) + " " + rsNom.getString(2);
			enseignant.setText(nomPrenEns);
		} catch (SQLException e) {
			System.err.println("Error" + e);
		}

		try {
			// Execute query and store result in a resultset
			ResultSet rs = conn.createStatement().executeQuery("Select Libelle_Groupe from groupe");

			while (rs.next()) {
				options.add(rs.getString(1));
			}

			comboGroupe.setItems(options);
			comboGroupe.setValue(options.get(0));

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

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
	private void handleButtonCreer(ActionEvent event) throws IOException, SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		// DatePicker datePicker = new DatePicker();
		date.setShowWeekNumbers(true);

		// Converter
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};
		date.setConverter(converter);
		date.setPromptText("dd-MM-yyyy");
		
		Date dateRecup = new Date(0); 
		
		if (!session.getText().equals("") && !matiere.getText().equals("") && !salle.getText().equals("") 
				&& date.getValue()!=null) {
			try {
				conn = ConnectionBD.getConnection();

				String query = "INSERT INTO cours(groupe, session, matiere, salle, date, heure_debut, heure_fin, enseignant)"
						+ "VALUES (?,?,?,?,?,?,?,?)";

				ps = conn.prepareStatement(query);

				ps.setString(1, comboGroupe.getValue());
				ps.setString(2, session.getText());
				ps.setString(3, matiere.getText());
				ps.setString(4, salle.getText());
				ps.setDate(5, dateRecup.valueOf(date.getValue()));
				ps.setString(6, comboDebut.getValue());
				ps.setString(7, comboFin.getValue());
				ps.setString(8, enseignant.getText());

				ps.executeUpdate();

				session.clear();
				matiere.clear();
				salle.clear();
				comboGroupe.setValue(options.get(0));
				comboDebut.setValue(optionsDebut.get(0));
				comboFin.setValue(optionsFin.get(0));
				date.setValue(null);

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Notification");
				alert.setHeaderText(null);
				alert.setContentText("Le cours a bien été créé !");

				alert.showAndWait();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Problème de création du cours");
			alert.setContentText("Vous n'avez pas rempli tous les champs !");

			alert.showAndWait();
		}

	}

	@FXML
	private void handleButtonRetour(ActionEvent event) throws IOException, SQLException {

		Stage stage = (Stage) btnRetour.getScene().getWindow();
		stage.close();

	}

}
