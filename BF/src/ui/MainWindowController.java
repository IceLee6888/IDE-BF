package ui;


import java.awt.Label;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rmi.RemoteHelper;

public class MainWindowController extends Application {
	/**
	 * 当前用户信息
	 */
	public static String currentUsername = "";
	public static String currentFiletype = "";
	public static String currentFilename = "";

	public static String readfile="";//读文件时被小窗口调用
	public static boolean tosave=false;
	public static boolean deleted=false;
	public static boolean opened=false;
	public static boolean newed=false;

	/**
	 * 可执行文件类型
	 */
	private static ArrayList<String> filetypes;

	private String file = "";
	private String input = "";



	/**
	 * FXML引用
	 */
	@FXML
	public TextArea codearea;
	@FXML
	private TextArea inputarea;
	@FXML
	private TextArea outputarea;
	@FXML
	private Label username;
	@FXML
	private ComboBox<String> filetype;

	private Stage primaryStage;
	private AnchorPane mainWindow;
	private ComboBox<String> history;
	private String showing="";//当前显示的记录

	/**
	 * 主界面显示
	 */
	public void initMainWindow() {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainWindowController.class.getResource("MainWindow.fxml"));

		try{
			mainWindow = (AnchorPane)loader.load();
			Scene scene = new Scene(mainWindow);
			this.primaryStage.setScene(scene);

			this.primaryStage.show();
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

	/**
	 * 新建文件（new）界面
	 */
	@FXML
	public void clickonNew(){
		//检查用户是否登录

		//用户未登录
		if(currentUsername.equals("")){
			try{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainWindowController.class.getResource("LoginReminder.fxml"));
				BorderPane pane = (BorderPane) loader.load();

				Stage stage = new Stage();
				stage.setTitle("Login!");
				Scene scene = new Scene(pane);
				stage.setScene(scene);
				LoginReminderController controller = loader.getController();
				controller.setStage(stage);

				stage.showAndWait();

			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else{
			try{
				ObservableList<String> list=FXCollections.observableArrayList();
				for(String s:filetypes){
					list.add(s);
				}
				filetype = new ComboBox<String>(list);
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(MainWindowController.class.getResource("NewFileWindow.fxml"));
				AnchorPane pane=(AnchorPane) loader.load();
				NewFileWindowController controller=loader.getController();
				Stage stage=new Stage();
				pane.getChildren().add(filetype);
				AnchorPane.setRightAnchor(filetype, 122.0);
				AnchorPane.setLeftAnchor(filetype, 127.0);
				AnchorPane.setTopAnchor(filetype, 127.0);
				Scene scene=new Scene(pane);
				stage.setScene(scene);
				stage.setTitle("New File");
				controller.setStage(stage);


				stage.showAndWait();

				if(newed){
					filetype.setPromptText(currentFiletype);
					String showing = codearea.getText();
				}

			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 保存文件（save)界面
	 */
	@FXML
	public void clickonSave(){
		//检查用户是否登录

		//如果用户未登录
		if(currentUsername.equals("")){
			//提醒登录
			try{
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(MainWindowController.class.getResource("LoginReminder.fxml"));
				BorderPane pane=(BorderPane) loader.load();

				Stage stage=new Stage();
				stage.setTitle(" ");
				Scene scene=new Scene(pane);
				stage.setScene(scene);
				LoginReminderController controller=loader.getController();
				controller.setStage(stage);

				stage.showAndWait();

			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//如果文件名和文件类型为空
		else if(currentFiletype.equals("") || currentFilename.equals("")){
			//提醒创建文件
			this.clickonNew();
			System.out.println("要新建文件才能保存");
		}
		//保存文件
		else{
			try{
				file=this.codearea.getText();
				RemoteHelper.getInstance().getIOService().clickonSave(currentFilename, currentUsername,file , currentFiletype);
				try{
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(MainWindowController.class.getResource("SaveSucceed.fxml"));
					BorderPane pane = (BorderPane) loader.load();

					Stage stage = new Stage();
					stage.setTitle(" ");
					Scene scene = new Scene(pane);
					stage.setScene(scene);

					SaveSucceedController c = loader.getController();
					c.setStage(stage);

					stage.show();

				}catch(Exception e){
					e.printStackTrace();
				}

			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 运行（run）界面
	 */
	@FXML
	public void clickonRun(){
		//如果没有设置文件类型
		if(currentFiletype.equals("")){
			//提示选择文件类型
			try{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainWindowController.class.getResource("FileTypeReminder.fxml"));
				AnchorPane pane = (AnchorPane) loader.load();
				FileTypeReminderController controller=loader.getController();
				Stage stage = new Stage();
				pane.getChildren().add(filetype);
				AnchorPane.setRightAnchor(filetype, 79.0);
				AnchorPane.setLeftAnchor(filetype, 79.0);
				AnchorPane.setTopAnchor(filetype, 107.0);
				Scene scene = new Scene(pane);
				stage.setScene(scene);
				stage.setTitle("File Type");
				controller.setStage(stage);
				controller.setFiletype(filetype);


				stage.showAndWait();

			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			String code = codearea.getText();
			String param = inputarea.getText();
			if(code.equals("")){
				outputarea.setText("Please input your code");
			}
			else{
				try{
					String result = RemoteHelper.getInstance().getExecuteService().execute(code, param);
					outputarea.setText(result);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}



	}

	/**
	 * 关闭当前文件（close）
	 */
	@FXML
	public void clickonClose(){

	}

	/**
	 * LogIn界面
	 *
	 */
	@FXML
	public void clickonLogin(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainWindowController.class.getResource("LoginController"));
			BorderPane pane = (BorderPane)loader.load();

			Stage stage = new Stage();
			stage.setTitle("Login Please!");
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			LoginController controller = (LoginController)loader.getController();
			controller.setStage(stage);

			stage.showAndWait();

			if(!currentUsername.equals("")){
			    username.setText(currentUsername);
			}


		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Logout界面
	 */
	@FXML
	public void clickonLogout(){
		if(currentUsername.equals("")){
			try{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainWindowController.class.getResource("LoginReminder.fxml"));
				BorderPane pane = (BorderPane) loader.load();

				Stage stage = new Stage();
				stage.setTitle(" ");
				Scene scene = new Scene(pane);
				stage.setScene(scene);
				LoginReminderController controller = loader.getController();
				controller.setStage(stage);

				stage.show();
				username.setText("Please Login");

			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			currentUsername="";
			currentFilename="";
			currentFiletype="";
			username.setText("Please Login");
			codearea.setText("");
			inputarea.setText("");
			outputarea.setText("");


			//显示提示窗口
			try{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainWindowController.class.getResource("LogoutSucceed.fxml"));
				BorderPane pane = (BorderPane) loader.load();

				Stage stage = new Stage();
				stage.setTitle(" ");
				Scene scene = new Scene(pane);
				stage.setScene(scene);

				LogoutSucceedController c = loader.getController();
				c.setStage(stage);

				stage.show();

			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}


	/**
	 * Register界面
	 */
	@FXML
	public void clickonRegister(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainWindowController.class.getResource("SigninWindow.fxml"));
			BorderPane pane = (BorderPane) loader.load();

			Stage stage = new Stage();
			stage.setTitle("Register");
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			RegisterWindowController controller = loader.getController();
			controller.setStage(stage);

			stage.show();

		}catch(Exception e){
			e.printStackTrace();
		}
	}



	/**
	 * 查看历史版本（Version）
	 */
//	@FXML
//	public void clickonHistory(){
//		//文件未保存的情况下没有历史版本
//		if(this.currentUsername.equals("")||this.currentFilename.equals("")||this.currentFiletype.equals("")){
//
//		}
//		else{
//			try{
//				String historyfiles = RemoteHelper.getInstance().getIOService().history(currentFilename, currentFiletype, currentUsername);
//				String[] historylist = historyfiles.split('\n'+"");
//				if(historylist.length == 0){
//					//没有历史
//				}
//				else{
//					ObservableList<String> hl = FXCollections.observableArrayList();
//				for(String s:historylist){
//					s = s.split("\\.")[0];
//					hl.add(s);
//				}
//				this.history = new ComboBox<String>(hl);
//
//				try{
//					FXMLLoader loader = new FXMLLoader();
//					loader.setLocation(MainWindowController.class.getResource("HistoryVersion.fxml"));
//					AnchorPane pane = (AnchorPane) loader.load();
//					HistoryVersionController controller = loader.getController();
//					Stage stage = new Stage();
//					pane.getChildren().add(history);
//					AnchorPane.setRightAnchor(history, 68.0);
//					AnchorPane.setLeftAnchor(history, 68.0);
//					AnchorPane.setTopAnchor(history, 95.0);
//					Scene scene=new Scene(pane);
//					stage.setScene(scene);
//					stage.setTitle("History");
//					controller.setStage(stage);
//					controller.setFileList(history);
//
//
//					stage.showAndWait();
//
//					this.codearea.setText(readfile);
//					showing = codearea.getText();
//
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//
//				}
//
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//	}

	/**
	 * 退出程序
	 */
	@FXML
	public void clickonExit(){
		primaryStage.close();
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Welcome to Bachelor's house!");
		initMainWindow();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
