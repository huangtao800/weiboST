package edu.nju;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import weibo4j.Oauth;
import weibo4j.http.AccessToken;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;

public class TestDrive {

	public static AccessToken accessToken;
	//m;m
	public static void main(String[] args) throws WeiboException, IOException{
		// TODO Auto-generated method stub
		Oauth oauth = new Oauth();
		BareBonesBrowserLaunch.openURL(oauth.authorize("code",null, null));
		String code =JOptionPane.showInputDialog("请输入URL网址末尾的code：");
//		String code = "f42a4b564e9fc4a25fafc5c2b30e58d2";
		accessToken= oauth.getAccessTokenByCode(code);
		ArrayList<Status> weiboStatusList = new WeiboGetter().getUserTimeLine(accessToken.getAccessToken());
		for(Status status : weiboStatusList){
			System.out.println(status.getText());
		}
		System.out.println(weiboStatusList.size());
		System.exit(0);
	}

}
