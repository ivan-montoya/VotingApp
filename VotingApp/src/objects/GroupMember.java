package objects;

public class GroupMember {
	private String myGroupName;
	private String myGroupCreator;
	private String myUsername;
	
	public GroupMember(String theGroupName, String theCreator, String theUsername) {
		this.myGroupName = theGroupName;
		this.myGroupCreator = theCreator;
		this.myUsername = theUsername;
	}
	
	public String getGroupName() {
		return this.myGroupName;
	}
	
	public String getGroupCreator() {
		return this.myGroupCreator;
	}
	
	public String getUsername() {
		return this.myUsername;
	}
}
