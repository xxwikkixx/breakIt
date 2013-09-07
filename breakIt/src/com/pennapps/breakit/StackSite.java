package com.pennapps.breakit;

public class StackSite {

	private String title;
	private String link;
	private String img;
	
	public String getName(){
		return title;
	}
	
	public void setName(String title){
		this.title = title;
	}
	
	public String getLink(){
		return link;
	}
	
	public void setLink(String link){
		this.link = link;
	}
	
	public String getImgUrl(){
		return img;
	}
	public void setImgUrl(String img){
		this.img = img;
	}
	
	@Override
	public String toString() {
		return "StackSite [title=" + title + ", link=" + link + ", img=" + img + "]";
	}
}
