package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class FileTypeReminderController {


	@FXML
	private ComboBox<String> filetype;
	@FXML
	private Button ok;

	private String type="";

	private  Stage stage;


	public void setStage(Stage stag){
		this.stage=stag;
	}

	public void setFiletype(ComboBox<String> l){
		filetype=l;

	}

	@FXML
	private void onOk(){
		type=filetype.getValue();
		if(type.equals("")){

		}
		else{
			System.out.println(type);
			stage.close();

		}
	}

}
