package edu.nju;

import java.util.ArrayList;

import weibo4j.Timeline;
import weibo4j.model.Paging;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

public class WeiboGetter {
	public static ArrayList<Status> getUserTimeLine(String access_token, String uid) {
		ArrayList<Status> weiboStatusList = new ArrayList<Status>();

		Timeline tm = new Timeline();
		tm.client.setToken(access_token);
		try {
			StatusWapper status = tm.getUserTimelineByUid(
					uid, new Paging(), 50, 0, 0);
			int time = (int) Math.ceil(status.getTotalNumber() / (50.0)) - 1;
			System.out.println(time);
			for (Status s : status.getStatuses()) {
//				System.out.println(s.getText());
				weiboStatusList.add(s);
			}
			
			int size = status.getStatuses().size();
			String max_id = status.getStatuses().get(size-1).getId();
			for (int i = 0; i < time; i++) {
				status = tm.getUserTimelineByUid(
						uid, new Paging(), 50,
						max_id, 0, 0);
				size = status.getStatuses().size();
				System.out.println(size);
				System.out.println(max_id);
				if(size ==0){
					
					break;
				}
				max_id = status.getStatuses().get(size-1).getId();

				for (Status s : status.getStatuses()) {
					if(s.getId().equals(max_id)){
						continue;
					}
					weiboStatusList.add(s);
					// System.out.println(s.getText());
				}
			}
			
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		return weiboStatusList;
	}
}
