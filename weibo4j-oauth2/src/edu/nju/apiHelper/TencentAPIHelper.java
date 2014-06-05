package edu.nju.apiHelper;

import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.IO;
import com.tencent.weibo.api.StatusesAPI;
import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.api.UserAPI;
import com.tencent.weibo.oauthv2.OAuthV2;
import com.tencent.weibo.oauthv2.OAuthV2Client;
import com.tencent.weibo.utils.QHttpClient;

import edu.nju.User;
import edu.nju.UserXML;
import edu.nju.WeiboStatus;
import edu.nju.WeiboXML;

public class TencentAPIHelper {

	public static TencentAPIHelper instance;

	// 腾讯api
	public static String TENCENT_API_KEY = "801508152";
	public static String TENCENT_SECRET_KEY = "2fb1a347c179a2b17a04fb23c54abf6c";
	private OAuthV2 oAuth = new OAuthV2();

	private TencentAPIHelper() {
		init(oAuth);

		// 自定制http连接管理器
		QHttpClient qHttpClient = new QHttpClient(2, 2, 5000, 5000, null, null);
		OAuthV2Client.setQHttpClient(qHttpClient);
		// 调用外部浏览器，请求用户授权，并读入授权码等参数
		openBrowser(oAuth);

		// 检查是否正确取得授权码
		if (oAuth.getStatus() == 2) {
			System.out.println("Get Authorization Code failed!");
			return;
		}

		// 换取access token
		oAuth.setGrantType("authorize_code");
		try {
			OAuthV2Client.accessToken(oAuth);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// 检查是否正确取得access token
		if (oAuth.getStatus() == 3) {
			System.out.println("Get Access Token failed!");
			return;
		}
		qHttpClient.shutdownConnection();

	};

	public User getUser() throws Exception {
		UserAPI userAPI = new UserAPI(oAuth.getOauthVersion());
		String user_xml = userAPI.info(oAuth, "xml");

		UserXML userXML = new UserXML(user_xml);
		return userXML.getUser();
	}

	public ArrayList<WeiboStatus> getWeibo() throws Exception {
		User user = getUser();
		int weiboNum = user.getTweetnum();
		int times = (int) Math.ceil(weiboNum / 50.0);

		StatusesAPI statusesAPI = new StatusesAPI(oAuth.getOauthVersion());

		String xml = statusesAPI.broadcastTimeline(oAuth, "xml", "0", "0",
				"50", "0", "3", "0x80");
		ArrayList<WeiboStatus> resultList = new ArrayList<WeiboStatus>();
		ArrayList<WeiboStatus> tempResultList = WeiboXML
				.getWeiboStatusList(xml);
		resultList.addAll(tempResultList);

		for (int i = 0; i < times - 1; i++) {
			int temp_length = tempResultList.size();
			WeiboStatus last_weibo = tempResultList.get(temp_length - 1);
			String last_id = last_weibo.getId();
			String last_timestamp = last_weibo.getTimestamp();
			String currentXML = statusesAPI.broadcastTimeline(oAuth, "xml",
					"1", last_timestamp, "50", last_id, "3", "0x80");
			tempResultList = WeiboXML.getWeiboStatusList(currentXML);
			resultList.addAll(tempResultList);
		}
		return resultList;
	}

	public WeiboStatus postWeibo(String text){
		TAPI tAPI = new TAPI(oAuth.getOauthVersion());
		String resultXML;
		WeiboStatus resultStatus=null;
		try {
			resultXML = tAPI.add(oAuth, "xml", text, "127.0.0.1");
			resultStatus = WeiboXML.getWeiboStatus(resultXML, text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStatus;
	}
	
	public static TencentAPIHelper getInstance() {
		if (instance == null) {
			instance = new TencentAPIHelper();
		}
		return instance;
	}

	private static void init(OAuthV2 oAuth) {
		oAuth.setClientId(TENCENT_API_KEY);
		oAuth.setClientSecret(TENCENT_SECRET_KEY);
		oAuth.setRedirectUri("http://www.tencent.com/zh-cn/index.shtml");
	}

	private static void openBrowser(OAuthV2 oAuth) {

		String authorizationUrl = OAuthV2Client.generateAuthorizationURL(oAuth);

		// 调用外部浏览器
		if (!java.awt.Desktop.isDesktopSupported()) {

			System.err.println("Desktop is not supported (fatal)");
			System.exit(1);
		}
		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
		if (desktop == null
				|| !desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {

			System.err
					.println("Desktop doesn't support the browse action (fatal)");
			System.exit(1);
		}
		try {
			desktop.browse(new URI(authorizationUrl));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.out
				.println("Input the authorization information (eg: code=CODE&openid=OPENID&openkey=OPENKEY) :");
		Scanner in = new Scanner(System.in);
		String responseData = in.nextLine();
		in.close();

		if (OAuthV2Client.parseAuthorization(responseData, oAuth)) {
			System.out.println("Parse Authorization Information Successfully");
		} else {
			System.out.println("Fail to Parse Authorization Information");
			return;
		}
	}
}
