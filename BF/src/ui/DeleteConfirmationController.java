package ui;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import rmi.RemoteHelper;

public class DeleteConfirmationController {

private Stage stage;

	public void setStage(Stage s){
		stage = s;
	}

	@FXML
	private void onYes(){
		String fn = MainWindowController.currentFilename;
		String ft = MainWindowController.currentFiletype;
		String un = MainWindowController.currentUsername;
		System.out.println("filename " + fn);
		System.out.println("username " + un);
		System.out.println("filetype " + ft);
		try{
			boolean res = RemoteHelper.getInstance().getIOService().deleteFile(fn, un, ft);
			MainWindowController.deleted = res;
			stage.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@FXML
	private void onNo(){
		stage.close();
	}
}
