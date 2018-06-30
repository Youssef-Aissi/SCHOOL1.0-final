package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AccueilDirecteur extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/accueilDirecteur.fxml"));
				
		Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.UNDECORATED);
        //scene.getStylesheets().addAll(this.getClass().getResource("accueil.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
