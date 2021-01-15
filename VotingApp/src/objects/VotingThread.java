package objects;

import java.util.Calendar;

public class VotingThread
{
	private String myTitle;
	private String myDescription;
	private int myNumCandidates;
	private int myThread_ID;
	private java.sql.Timestamp myDate;
	private String myUsername;
	private String myOpenStatus;
	private String myPrivateStatus;
	
	public VotingThread(String title, String description, int numCandidates, int ID, String username, String privateStatus) {
		this.myTitle = title;
		this.myDescription = description;
		this.myNumCandidates = numCandidates;
		this.myThread_ID = ID;
		this.myUsername = username;
		this.myOpenStatus = "TRUE";
		this.myPrivateStatus = privateStatus;
		this.myDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		
	}
	
	public VotingThread(String title, String description, int numCandidates, int ID, String username, 
			String privateStatus, String openStatus, java.sql.Timestamp date) {
		this.myTitle = title;
		this.myDescription = description;
		this.myNumCandidates = numCandidates;
		this.myThread_ID = ID;
		this.myUsername = username;
		this.myOpenStatus = openStatus;
		this.myPrivateStatus = privateStatus;
		this.myDate = date;
	}
	
	public String getTitle() {
		return this.myTitle;
	}
	
	public int getNumCandidates() {
		return this.myNumCandidates;
	}
	
	public String getDescription() {
		return this.myDescription;
	}
	
	public String getUsername() {
		return this.myUsername;
	}
	
	public String getOpenStatus() {
		return this.myOpenStatus;
	}
	
	public String getPrivateStatus() {
		return this.myPrivateStatus;
	}
	
	public int getThreadID() {
		return this.myThread_ID;
	}
	
	public java.sql.Timestamp getDate() {
		return this.myDate;
	}

}
