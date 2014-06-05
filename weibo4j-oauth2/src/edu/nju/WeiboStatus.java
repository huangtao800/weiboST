package edu.nju;

public class WeiboStatus {
	private String id;
	private String text;
	private String timestamp;
	
	public WeiboStatus(String id, String text,String timestamp){
		this.id=id;
		this.text=text;
		this.timestamp=timestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
