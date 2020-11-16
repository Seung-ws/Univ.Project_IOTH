package application.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.Server.SSLSocketClient;
import application.Server.SocketClient;

public class FileCommand extends MainCommand { 

	public FileCommand(SocketClient client,Connection con) {
		super(client,con);
	}

	public void SetImageFile(DataInputStream dis) {
			
		
			String query="Insert into UserImage1 values(?,?,?,?,?)";
			String filename=null;
			String filetype=null;
		
			System.out.println("�����׽�Ʈ");
			
			BufferedInputStream bis=new BufferedInputStream(dis);
			
			
			ByteArrayOutputStream baos=new ByteArrayOutputStream(); 
			
			
			System.out.println("��ü��������");
			int len=-1;
			int sum=0;
			
			byte[] data =new byte[4096];
			try {
				int maxSum=Integer.parseInt(dis.readUTF().toString().replace("Size=", ""));
				filename=dis.readUTF().replace("filename=", "");
				filetype=dis.readUTF().replace("filetype=", "");
				while(!(sum==maxSum)&&(len=bis.read(data))!=-1)
				{
					sum+=len;
					baos.write(data,0,len);					
				}
				
				
				
			}catch(IOException e)
			{
				System.out.println("���Ͼ��� ����½���");
			}
			
			
			ByteArrayInputStream bais=new ByteArrayInputStream(baos.toByteArray());
			
			try {
				pstmt=con.prepareStatement(query);
				pstmt.setString(1, filename);
				pstmt.setBinaryStream(2, bais);
				pstmt.setString(3, filetype);
				pstmt.setDate(4, new Date(System.currentTimeMillis()));
				pstmt.setString(5, null);
				pstmt.executeQuery();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				baos.close();
				bais.close();
				//bis.close(); dis�� ���� ����Ǳ⶧���� �ǵ�������
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("��ҽ���");
				e.printStackTrace();
				
			}
			
		
		System.out.println("���Ͼ���Ϸ�");
		
	}
}
