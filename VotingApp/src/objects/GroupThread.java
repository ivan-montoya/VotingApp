package objects;

public class GroupThread {

	private String myGroupName;
	private String myGroupCreator;
	private int myThreadID;
	private String myThreadCreator;
	private String myTitle;
	
	public GroupThread(String theGroupName, String theGroupCreator, int theThreadID, String theThreadCreator, String theTitle)
	{
		myGroupName = theGroupName;
		myGroupCreator = theGroupCreator;
		myThreadID = theThreadID;
		myThreadCreator = theThreadCreator;
		myTitle = theTitle;
	}
	
	public String getGroupName()
	{
		return myGroupName;
	}
	
	public String getGroupCreator()
	{
		return myGroupCreator;
	}
	
	public int getThreadID()
	{
		return myThreadID;
	}
	
	public String getThreadCreator()
	{
		return myThreadCreator;
	}
	
	public String getTitle()
	{
		return myTitle;
	}
}
