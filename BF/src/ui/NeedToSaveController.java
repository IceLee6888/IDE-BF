package ui;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class NeedToSaveController {

	private Stage stage;

	public void setStage(Stage s){
		stage = s;
	}
	@FXML
	private void clickonYes(){
		MainWindowController.tosave = true;
		stage.close();
	}

	@FXML
	private void clickonNo(){
		MainWindowController.tosave = false;
		stage.close();
	}
}
