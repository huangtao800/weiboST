package edu.nju.apiHelper;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import edu.nju.WeiboGetter;
import edu.nju.WeiboStatus;
import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.http.AccessToken;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;

public class SinaAPIHelper {

	public static SinaAPIHelper instance;
	public static AccessToken accessToken;
	
	private SinaAPIHelper(){
		Oauth oauth = new Oauth();
		try {
			BareBonesBrowserLaunch.openURL(oauth.authorize("code",null, null));
			String code =JOptionPane.showInputDialog("请输入URL网址末尾的code：");
			accessToken= oauth.getAccessTokenByCode(code);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static SinaAPIHelper getInstance(){
		if(instance==null){
			instance = new SinaAPIHelper();
		}
		return instance;
	}
	
	public ArrayList<WeiboStatus> getWeibo(){
		ArrayList<WeiboStatus> resultList = new ArrayList<WeiboStatus>();
		ArrayList<Status> tempResult = WeiboGetter.getUserTimeLine(accessToken.getAccessToken(),accessToken.getUid());
		
		for(int i=0;i<tempResult.size();i++){
			Status currentStatus = tempResult.get(i);
			String id = currentStatus.getId();
			String text = currentStatus.getText();
			resultList.add(new WeiboStatus(id, text,"s"));
			Date createDate = currentStatus.getCreatedAt();
		}
		return resultList;
	}
	
	public Status postWeibo(String text){
		Timeline tm = new Timeline();
		tm.client.setToken(accessToken.getAccessToken());
		Status resultStatus =null;
		try {
			resultStatus = tm.UpdateStatus(text);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStatus;
	}
}
