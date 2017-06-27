package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import rmi.RemoteHelper;

public class RegisterWindowController {

	@FXML
	private TextField username;
	@FXML
	private TextField password;

	private String name = "";
	private String mima = "";

	private Stage stage;

	public void setStage(Stage s){
		this.stage = s;
	}

	@FXML
	private void clickonOK(){
		name = username.getText();
		mima = password.getText();
		if(checkinput()){
			try{
				boolean res = RemoteHelper.getInstance().getUserService().newUser(name, mima);

				//ע
				if(res){

					//
					try{
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(RegisterWindowController.class.getResource("RegisterSucceed.fxml"));
						BorderPane pane = (BorderPane) loader.load();

						Stage stage = new Stage();
						stage.setTitle("Wolf!");
						Scene scene = new Scene(pane);
						stage.setScene(scene);

						RegisterSucceedController c = loader.getController();
						c.setStage(stage);


						stage.show();

					}catch(Exception e){
						e.printStackTrace();
					}
					stage.close();
				}
				//
				else{
					try{
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(RegisterWindowController.class.getResource("ChangeName.fxml"));
						BorderPane pane = (BorderPane) loader.load();

						Stage stage = new Stage();
						stage.setTitle(" ");
						Scene scene = new Scene(pane);
						stage.setScene(scene);
						ChangeNameController c = loader.getController();
						c.setStage(stage);

						stage.show();

					}catch(Exception e){
						e.printStackTrace();
					}
					username.setText("");
					password.setText("");
				}


			}catch(Exception e){
				e.printStackTrace();
			}

		}
		else{
			username.setText("");
			password.setText("");
		}
	}

	private boolean checkinput(){
		if(name.equals("") || mima.equals("")){
			return false;
		}
		else{
			return true;
		}
	}

}
