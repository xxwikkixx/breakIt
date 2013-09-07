package com.pennapps.breakit;

public class StackSite {

	private String name;
	private String link;
	private String imgUrl;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getLink(){
		return link;
	}
	
	public void setLink(String link){
		this.link = link;
	}
	
	public String getImgUrl(){
		return imgUrl;
	}
	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}
	
	@Override
	public String toString() {
		return "StackSite [name=" + name + ", link=" + link + ", imgUrl=" + imgUrl + "]";
	}
}