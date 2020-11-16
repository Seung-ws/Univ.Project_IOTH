package application.Message;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import application.MainUnit;
import application.DataBase.DBImageLog;
import application.DataBase.TempHumGasData;
import application.Server.ClientsList;

public class homeServerChat {

	public String MsgType(String msg)
	{
		String res=null;
		switch(msg)
		{
				case "#!sys:getWebCamlist"://Ȩ ��ķ���� ����� ����Ʈ�� ��ȯ�ϱ� ����
				{
					res=new DBImageLog().getImageLog();					
					break;
				}
				/*case "#!sys:test":
				{
					TempHumGasData.�½�������(TempHumGasData.�°����ӽ��߰�());
					TempHumGasData.�½�������(TempHumGasData.�°����ӽ��߰�());
					TempHumGasData.�½�������(TempHumGasData.�°����ӽ��߰�());
					break;
				}
				case "#!sys:AddTHG":
				{
					
					break;
				}*/
				case "#!sys:GetTHG"://�½��� �� �ֱ����� 7�� ���� ��ȯ�ϱ�..
				{
					res=TempHumGasData.temp_and_hue_type_F();
					break;
				}
				case "#!sys:gettemp&gasValue":{//�½��� ������ �ֱ��Ѱ� ��ȯ�ϱ�
					System.out.println("#!sys:gettemp&gasValue����");
					res=TempHumGasData.temp_and_hue_type_m();
					break;
				}
		
				case "#!sys:getcount":
				{
					System.out.println("#!sys:getcount����");
					
					res=ClientsList.deviceCount();
					
					
					break;
				}
						
				case "#!sys:getlist":
				{
					System.out.println("#!sys:getlist����");
					
					res=ClientsList.getList();
					
					break;
				}
				case "#!sys:getrelaylist":
				{
					System.out.println("sys:getrelaylist����");
					res=ClientsList.getRelayList();
					break;
				}
				default :
				{
					if(msg.contains("#!toMsg"))
					{
						try
						{
							JSONObject totalObject=(JSONObject) new JSONParser().parse(msg);
							JSONArray jsonArr=(JSONArray)totalObject.get("#!toMsg");
							JSONObject object=(JSONObject)jsonArr.get(0);					
							ClientsList.toSend(object.get("client_name").toString(), object.get("sign").toString());
							MainUnit.MainMsg("user=>"+object.get("client_name").toString()+" sign=>"+object.get("sign").toString());
						}catch(Exception e){
							System.out.println("#!toMsgError"+ e);
						}
					}else if(msg.contains("#!saveTHG")){
						System.out.println("#!saveTHG");
						TempHumGasData.referesh_temp_hue(msg);
						System.out.println("#!saveTHG=success");
					}
				
					break;
				}
		}
		return res;
	}

	public String getlist() {
		ClientsList.isAliveClient();
		return ClientsList.getList();
	}
}