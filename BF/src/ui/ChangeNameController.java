package ui;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ChangeNameController {
	
	private Stage stage;
	
	public void setStage(Stage s){
		stage=s;
	}

	@FXML
	private void onOk(){
		stage.close();
	}
}
