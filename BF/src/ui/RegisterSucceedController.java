package ui;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class RegisterSucceedController {

private Stage stage;

	public void setStage(Stage s){
		stage = s;
	}

	@FXML
	private void clickonOk(){
		stage.close();
	}
}
