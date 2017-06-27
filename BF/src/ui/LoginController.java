package ui;

import java.awt.Button;
import java.awt.Label;
import java.awt.TextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import rmi.RemoteHelper;

public class LoginController {

	@FXML
	Pane Login;

	@FXML
	Label Username;

	@FXML
	Label Password;
	@FXML
	Button LogIn;
	@FXML
	Button Register;
	@FXML
	TextField username;
	@FXML
	TextField password;




	private Stage stage;
	private String name="";
	private String mima="";//密码

	/**
	 *
	 * @param stag
	 */
	public void setStage(Stage stag) {
		stage = stag;

	}


	/**
	 * 点击登录时
	 * 注意检查
	 */
	@FXML
	private void clickonLogin(){
		name = username.getText();
		mima = password.getText();

		//输入格式正确
		if(checkInput()){
			try{
				String pass = RemoteHelper.getInstance().getUserService().getPassword(name);

				//如果用户不存在
				if(pass.equals("")){
					try{
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(LoginController.class.getResource("UserNotExist.fxml"));
						BorderPane pane = (BorderPane) loader.load();

						Stage stage = new Stage();
						stage.setTitle(" ");
						Scene scene = new Scene(pane);
						stage.setScene(scene);

						UserNotExistController c = loader.getController();
						c.setStage(stage);

						stage.show();

					}catch(Exception e){
						e.printStackTrace();
					}

					//用户名和密码重置为空
					username.setText("");
					password.setText("");


				}
				//如果用户存在
				else{
					//密码正确
					if(pass.equals(mima)){

						MainWindowController.currentUsername = name;

						try{
							FXMLLoader loader=new FXMLLoader();
							loader.setLocation(LoginController.class.getResource("LoginSucceed.fxml"));
							BorderPane pane=(BorderPane) loader.load();

							Stage stage = new Stage();
							stage.setTitle(" ");
							Scene scene = new Scene(pane);
							stage.setScene(scene);

							LoginSucceedController c = loader.getController();
							c.setStage(stage);

							stage.show();

						}catch(Exception e){
							e.printStackTrace();
						}

						//关闭Login界面
						this.stage.close();

					}
					else{
						//密码错误，显示密码错误页面
						//用户名和密码重新清空
						try{
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(LoginController.class.getResource("WrongPassword.fxml"));
							BorderPane pane = (BorderPane) loader.load();

							Stage stage = new Stage();
							stage.setTitle(" ");
							Scene scene = new Scene(pane);
							stage.setScene(scene);

							WrongPasswordController c = loader.getController();
							c.setStage(stage);

							stage.show();

						}catch(Exception e){
							e.printStackTrace();
						}

						//用户名和密码重置
						username.setText("");
						password.setText("");

					}
				}

			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	//检查用户名和密码输入不为空
	private boolean checkInput(){
		if(name.equals("") || mima.equals("")){
			return false;
		}
		else{
			return true;
		}
	}




}
