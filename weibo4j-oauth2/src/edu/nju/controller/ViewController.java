package edu.nju.controller;

import java.util.ArrayList;

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
			dbHelper.saveWeiboStatus(tencentList);
			dbHelper.saveWeiboStatus(sinaList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
