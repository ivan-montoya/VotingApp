package objects;

public class Group {
	
	private String myName;
	private String myCreator;
	
	
	public Group(String name, String creator)
	{
		this.myName = name;
		this.myCreator = creator;
	}
	
	public String getGroupName()
	{
		return this.myName;
	}
	
	public String getAdminName()
	{
		return this.myCreator;
	}

}
