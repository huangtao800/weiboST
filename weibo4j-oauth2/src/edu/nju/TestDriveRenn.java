package edu.nju;

import javax.swing.JOptionPane;

import weibo4j.util.BareBonesBrowserLaunch;

import com.renren.api.AuthorizationException;
import com.renren.api.RennClient;
import com.renren.api.RennException;
import com.renren.api.service.Status;
import com.renren.api.service.User;

public class TestDriveRenn {

	public static String RENN_API_KEY = "d0029ade3b854f58bb2ad2b5368c3dd2";
	public static String RENN_SECRET_KEY = "3717706eb22b476b97153abe19c074b2";
//	https://graph.renren.com/oauth/authorize?
//	     client_id=YOUR_API_KEY&redirect_uri=YOUR_CALLBACK_URL&response_type=code
	public static void main(String[] args) throws RennException {
		// TODO Auto-generated method stub
		RennClient client = new RennClient(RENN_API_KEY, RENN_SECRET_KEY);
		BareBonesBrowserLaunch.openURL("https://graph.renren.com/oauth/authorize?client_id="+RENN_API_KEY
				+"&redirect_uri=http://graph.renren.com/oauth/login_success.html&response_type=code&scope=read_user_status+read_user_feed&display=page");
		
		String code = JOptionPane.showInputDialog("请输入code:");
		client.authorizeWithAuthorizationCode(code, "http://graph.renren.com/oauth/login_success.html");
		client.authorizeWithClientCredentials();
//		User user = client.getUserService().getUser(new Long(297128498));
		long userID = 297128498;
		Status [] status = client.getStatusService().listStatus (userID, 50, 1);
		for(Status s: status){
			System.out.println(s.toString());
		}
		
	}

}
