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
				case "#!sys:getWebCamlist"://홈 웹캠에서 저장된 리스트를 반환하기 위함
				{
					res=new DBImageLog().getImageLog();					
					break;
				}
				/*case "#!sys:test":
				{
					TempHumGasData.온습도갱신(TempHumGasData.온가스임시추가());
					TempHumGasData.온습도갱신(TempHumGasData.온가스임시추가());
					TempHumGasData.온습도갱신(TempHumGasData.온가스임시추가());
					break;
				}
				case "#!sys:AddTHG":
				{
					
					break;
				}*/
				case "#!sys:GetTHG"://온습도 의 최근정보 7개 까지 반환하기..
				{
					res=TempHumGasData.temp_and_hue_type_F();
					break;
				}
				case "#!sys:gettemp&gasValue":{//온습도 정보의 최근한개 반환하기
					System.out.println("#!sys:gettemp&gasValue받음");
					res=TempHumGasData.temp_and_hue_type_m();
					break;
				}
		
				case "#!sys:getcount":
				{
					System.out.println("#!sys:getcount받음");
					
					res=ClientsList.deviceCount();
					
					
					break;
				}
						
				case "#!sys:getlist":
				{
					System.out.println("#!sys:getlist받음");
					
					res=ClientsList.getList();
					
					break;
				}
				case "#!sys:getrelaylist":
				{
					System.out.println("sys:getrelaylist받음");
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