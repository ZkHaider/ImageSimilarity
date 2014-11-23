package model;

public class Media {
	
	private int id;
	private int eventId;
	private String socialId;
	private String mediaUrl;
	
	public Media(int id, int eventId, String socialId, String mediaUrl) {
		this.id = id;
		this.eventId = eventId;
		this.socialId = socialId;
		this.mediaUrl = mediaUrl;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getEventId() {
		return eventId;
	}
	
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	public String getSocialId() {
		return socialId;
	}
	
	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}
	
	public String getMediaUrl() {
		return mediaUrl;
	}
	
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

}
