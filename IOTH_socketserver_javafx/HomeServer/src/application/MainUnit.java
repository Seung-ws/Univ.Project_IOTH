package application;


import java.awt.Panel;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import com.sun.javafx.tk.Toolkit;

import application.Server.MainServer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainUnit implements Initializable{
	private Stage primaryStage=new Stage();
	private MainServer mainServer;
	private BackgroundSet bs;
	public static TextArea MainMsg=null;
	public static ImageView Mainbg=null;
	@FXML private BorderPane mainform;
	@FXML private BorderPane SubPane;
	@FXML private TextArea stateres;
	@FXML private ImageView backgroundImage;
	@FXML private Label DateLabel;
	@FXML private Label TimeLabel;
	@FXML private Label ServerStateLabel;
	@FXML private Button Exitbtn;
	
	public synchronized static void MainMsg(String str) {
		MainMsg.setText(MainMsg.getText().concat(""+str+"\n"));	

	}
	public synchronized static void Mainbg(Image i)
	{
		Mainbg.setImage(i);
	}
	@Override
	public void initialize(URL location,ResourceBundle resource)
	{	

		primaryStage.setScene(new Scene(mainform));
		primaryStage.setFullScreen(false);
		primaryStage.show();
		
		MainMsg=stateres;
		Mainbg=backgroundImage;
		
		bs=new BackgroundSet(primaryStage);
		mainServer=new MainServer(1025);
		ShowTimer();
	//	onBackgroundboard();

	}
	public void ShowTimer() {
		SimpleDateFormat date=new SimpleDateFormat("yyyy. MMM. dd. EE",new Locale("en", "US"));
		SimpleDateFormat time=new SimpleDateFormat("hh:mm",new Locale("en", "US"));
		new Thread(new Runnable() {
			public void run() {
				while(primaryStage.isShowing())
				{
					Platform.runLater(new Runnable() {
						public void run() {
							SubPane.setPrefWidth(primaryStage.getWidth());
							SubPane.setPrefHeight(primaryStage.getHeight());
							
							DateLabel.setText(date.format(System.currentTimeMillis()));
							TimeLabel.setText(time.format(System.currentTimeMillis()));		
							if(mainServer.serverOn){ServerStateLabel.setText("동작중");}else{ServerStateLabel.setText("대기중");};
						}
					});
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
				}
				
			}
		}).start();
		
		
	}
	public void onBackgroundboard() {
		
		bs.SetImage(backgroundImage);
		
	}
	
	
	
	public void onExitbtn(ActionEvent event) {
		Platform.exit();		
	}
	public void onServer(ActionEvent event) {
		mainServer.start();
	}
	public void offServer(ActionEvent event) {
		//onBackgroundboard();
		mainServer.stop();
		
	}

}
