package com.pennapps.breakit;


public class StackSite {

	private String name;
	private String link;
	private String about;
	private String imgUrl;
	private String entryId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public String getEntryId() {
		return entryId;
	}
	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getImgUrl() {
		return imgUrl;
		
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Override
	public String toString() {
		return "StackSite [name=" + name + ", link=" + link + ", about="
				+ about + ", imgUrl=" + imgUrl + ", entryID=" + entryId + "]";
	}
}

