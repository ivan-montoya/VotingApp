package objects;

import java.util.Calendar;

public class RegisteredUser
{
	
	private String userName;
	private String email;
	private String password;
	private String name;
	private String birthday;
	private java.sql.Timestamp myUserSince;
	private int numThreads;
	
	public RegisteredUser(String userName, String email, String password, String name, String birthday, int numThreads)
	{
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		this.numThreads = numThreads;
		this.myUserSince = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		
	}
	
	public RegisteredUser(String userName, String email, String password, String name, String birthday, int numThreads, java.sql.Timestamp userSince)
	{
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		this.numThreads = numThreads;
		this.myUserSince = userSince;
		
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getUser() {
		return this.userName;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}
	
	public String getBirthday() {
		return this.birthday;
	}
	
	public int getNumThreads()
	{
		return this.numThreads;
	}
	
	public void setNumThreads(int numThreads)
	{
		this.numThreads = numThreads;
	}
	
	public java.sql.Timestamp getUserSince()
	{
		return this.myUserSince;
	}
	
}
