package application.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;

import application.Server.SSLSocketClient;
import application.Server.SocketClient;

public class MainCommand {
  //��Ӱ���� ������ ���� ������?
	protected SocketClient client;
	protected Connection con;
	protected PreparedStatement pstmt;
	
	
	
	public MainCommand(SocketClient client,Connection con) {
		this.client=client;
		this.con=con;
		
	}
}
