package edu.nju.controller;

import java.util.ArrayList;

import weibo4j.Timeline;
import weibo4j.model.Status;
import edu.nju.WeiboStatus;
import edu.nju.apiHelper.SinaAPIHelper;
import edu.nju.apiHelper.TencentAPIHelper;
import edu.nju.database.DBHelper;

public class ViewController {
	private TencentAPIHelper tencentAPIHelper=TencentAPIHelper.getInstance();
	private SinaAPIHelper sinaAPIHelper=SinaAPIHelper.getInstance();
	private DBHelper dbHelper = DBHelper.getInstance();
	
	private static ViewController instance;
	private ViewController() {
		// TODO Auto-generated constructor stub
	}
	
	public static ViewController getInstance(){
		if(instance==null){
			instance=new ViewController();
		}
		return instance;
	}
	
	public void synchronize(){
		try {
			ArrayList<WeiboStatus> tencentList = tencentAPIHelper.getWeibo();
			ArrayList<WeiboStatus> sinaList = sinaAPIHelper.getWeibo();
			dbHelper.saveWeiboStatusList(tencentList);
			dbHelper.saveWeiboStatusList(sinaList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean postWeibo(String text){
		Status sinaStatus = sinaAPIHelper.postWeibo(text);
		WeiboStatus tencentStatus=tencentAPIHelper.postWeibo(text);
		
		
		if(sinaStatus!=null&&tencentStatus!=null){
			dbHelper.saveWeiboStatus(tencentStatus);
			return true;
		}else {
			return false;
		}
	}
}
