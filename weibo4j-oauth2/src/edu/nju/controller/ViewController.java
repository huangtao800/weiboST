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
			
			synchronizeToALL();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean postWeibo(String text){
		Status sinaStatus = sinaAPIHelper.postWeibo(text);
		WeiboStatus tencentStatus=tencentAPIHelper.postWeibo(text);
		
		
		if(sinaStatus!=null&&tencentStatus!=null){
			dbHelper.saveTencentWeiboStatus(tencentStatus);
			return true;
		}else {
			return false;
		}
	}
	
	private void synchronizeToALL(){
		ArrayList<WeiboStatus> unSynchronizedList=dbHelper.getUnSynchronizedWeiboStatusList();
		for(int i=0;i<unSynchronizedList.size();i++){
			WeiboStatus currentStatus = unSynchronizedList.get(i);
			if(currentStatus.getSource().equals("s")){
				WeiboStatus tencentStatus = tencentAPIHelper.postWeibo(currentStatus.getText());
				dbHelper.saveTencentWeiboStatus(tencentStatus);
				
			}else{
				Status status = sinaAPIHelper.postWeibo(currentStatus.getText());
				WeiboStatus sinaWeiboStatus = new WeiboStatus(status);
				dbHelper.saveSinaWeiboStatus(sinaWeiboStatus);
				
			}
			
			dbHelper.changeIsSynchronize(currentStatus);
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
