package ui;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class LoginReminderController {

private Stage stage;

	public void setStage(Stage stag){
		this.stage=stag;
	}

	@FXML
	private void onOk(){
		stage.close();
	}
}
