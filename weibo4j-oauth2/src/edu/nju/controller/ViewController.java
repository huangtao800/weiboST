package edu.nju.controller;

import java.util.ArrayList;

import edu.nju.WeiboStatus;
import edu.nju.apiHelper.TencentAPIHelper;

public class ViewController {
	private TencentAPIHelper tencentAPIHelper=TencentAPIHelper.getInstance();
	
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
			for(int i=0;i<tencentList.size();i++){
				System.out.println(tencentList.get(i).getId());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
