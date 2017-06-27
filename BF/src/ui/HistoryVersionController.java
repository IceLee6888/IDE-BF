package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import rmi.RemoteHelper;

public class HistoryVersionController {

	@FXML
	private ComboBox<String> history;
	@FXML
	private Button open;

	private String version="";

	private  Stage stage;

	public void setStage(Stage s){
		stage = s;
	}

	public void setFileList(ComboBox<String> l){
		history = l;
	}

	@FXML
	private void clickonOpen(){
		version = history.getValue();
		String s = "Failed";
		if(checkvalid()){
			try{
				s = RemoteHelper.getInstance().getIOService().readhistoryFile(MainWindowController.currentUsername,MainWindowController.currentFilename,MainWindowController.currentFiletype,version);

			}catch(Exception e){
				e.printStackTrace();
			}
		}
		MainWindowController.readfile = s;
		stage.close();
	}

	private boolean checkvalid(){
		if(version == null || version.equals("")){
			return false;
		}
		else{
			return true;
		}
	}
}
