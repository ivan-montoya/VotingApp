package objects;

public class Candidate {
	public String myDescription;
	public int myThread_ID;
	public int myVotes;
	private String myUsername;
	
	public Candidate(String description, int thread_ID, String username, int votes) {
		this.myDescription = description;
		this.myThread_ID = thread_ID;
		this.myVotes = votes;
		this.myUsername = username;
		
	}
	
	public String getDescription() {
		return this.myDescription;
	}
	
	public int getThreadID() {
		return this.myThread_ID;
	}
	
	public String getUsername() {
		return this.myUsername;
	}
	
	public int getVotes() {
		return this.myVotes;
	}

}
