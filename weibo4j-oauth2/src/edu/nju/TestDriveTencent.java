package edu.nju;

import java.net.URI;
import java.util.Scanner;

import com.tencent.weibo.api.StatusesAPI;
import com.tencent.weibo.oauthv2.OAuthV2;
import com.tencent.weibo.oauthv2.OAuthV2Client;
import com.tencent.weibo.utils.QHttpClient;

public class TestDriveTencent {
	
	public static String TENCENT_API_KEY = "801508152";
	public static String TENCENT_SECRET_KEY = "2fb1a347c179a2b17a04fb23c54abf6c";
	
	private static OAuthV2 oAuth = new OAuthV2();
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
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
		
		StatusesAPI statusesAPI = new StatusesAPI(oAuth.getOauthVersion());
		String format = "xml";
		String pageflag = "0";
		String pagetime = "0";
		String reqnum = "5";
		String lastid = "'0";
		String type = "0";
		String contenttype = "0";
		String resultString = statusesAPI.broadcastTimeline(oAuth, format, pageflag, pagetime,
				reqnum, lastid, type, contenttype);
		System.out.println();
		System.out.println(resultString);
		
	}

	private static void init(OAuthV2 oAuth) {
		oAuth.setClientId(TENCENT_API_KEY);
		oAuth.setClientSecret(TENCENT_SECRET_KEY);
		oAuth.setRedirectUri("http://www.tencent.com/zh-cn/index.shtml");
		// oAuth.setAccessToken("02204e5e59c2d22b73fb9b688e9f0c02");
		// oAuth.setOpenid("42A2ACA51C28782E854A50C5FA14160A");
		// oAuth.setOpenkey("ECBBC3B17F4190C8824DB652436E5DFA");
		// oAuth.setExpiresIn("604800");
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
