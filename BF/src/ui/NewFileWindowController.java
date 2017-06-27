package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rmi.RemoteHelper;

public class NewFileWindowController {

	private static  Stage mStage;
	@FXML
	private  ChoiceBox<String> filetype;

	private String fn;//文件名
	private String ft;//文件类型
	private String un;//用户名
	@FXML
	private Button ok;
	@FXML
	private TextField filename;

	public void setStage(Stage s){

		mStage = s;
	}

	public void setbox(ChoiceBox<String> b){
		filetype = b;
	}

	@FXML
	private void clickonOK(){
		un = MainWindowController.currentUsername;
		fn = filename.getText();
		ft = filetype.getValue();

		//检查状态，登录，文件名，文件类型
		//如果状态正常
		if(checkvalid()){
			try{
				boolean res = RemoteHelper.getInstance().getIOService().newFile(fn, ft, un);
				//如果文件名不重复
				if(res){
					try{
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(NewFileWindowController.class.getResource("NewFileSucceed.fxml"));
						AnchorPane pane = (AnchorPane) loader.load();

						Stage stage = new Stage();
						stage.setTitle("Do you want a new dog?");
						Scene scene = new Scene(pane);
						stage.setScene(scene);

						NewFileSucceedController c = loader.getController();
						c.setStage(stage);

						stage.show();

						MainWindowController.currentFilename = fn;
						MainWindowController.currentFiletype = ft;

						MainWindowController.newed = true;

						mStage.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				//如果文件名重复
				//跳出窗口要求改变文件名
				else{
					//
					try{
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(NewFileWindowController.class.getResource("ChangeFileName.fxml"));
						AnchorPane pane = (AnchorPane) loader.load();

						Stage stage = new Stage();
						stage.setTitle(" ");
						Scene scene = new Scene(pane);
						stage.setScene(scene);

						ChangeFileNameController c = loader.getController();
						c.setStage(stage);

						MainWindowController.newed = false;

						stage.show();

					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//登录异常或没有新建文件
		else{
			filename.setText("");
		}
	}

	//检查用户是否登录，文件名是否设置，文件类型是否已选
	private boolean checkvalid(){
		if(un.equals("") || fn.equals("") || ft.equals("")){
			return false;
		}
		else{
			return true;
		}
	}

}