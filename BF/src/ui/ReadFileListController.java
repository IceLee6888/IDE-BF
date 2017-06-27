package ui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import rmi.RemoteHelper;

public class ReadFileListController {

	@FXML
	private ComboBox<String> filelist;

	private String filename="";
	private String content="";

	private Stage stage;


	public void setStage(Stage s){
		this.stage = s;
	}

	public void setFileList(ComboBox<String> l){
		filelist = l;

	}

	@FXML
	private void clickonOpen(){
		filename = filelist.getValue();


		if(check()){
			try{
				String[] inf = filename.split("\\.");//
				content = RemoteHelper.getInstance().getIOService().readFile(MainWindowController.currentUsername, inf[0] );
				MainWindowController.readfile = content;
				MainWindowController.currentFilename = inf[0];
				MainWindowController.currentFiletype = "." + inf[1];
				MainWindowController.opened = true;
				stage.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			MainWindowController.opened = false;
		}
	}

	private boolean check(){
		if(filename == null || filename.equals("")){
			return false;
		}
		else{
			return true;
		}
	}
}
