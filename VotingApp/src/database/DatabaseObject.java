package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import objects.*;

public class DatabaseObject {
	
	private Connection conn = null;
	private Statement stmt = null;
	
	public DatabaseObject() {
		try {		         
			conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/votingApp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "voting", "votingApp");  
		     stmt = conn.createStatement();
	      } catch(SQLException ex) {
	    	 JOptionPane.showMessageDialog(null, "Connection did not work!");
	         ex.printStackTrace();
	    }
	}
	
	public boolean hasRegisteredUser(String theUsername) {
		try {
			String strSelect = "SELECT * FROM RegisteredUser WHERE username = '" + theUsername + "'";
	        ResultSet rset = stmt.executeQuery(strSelect);
	        
	        if (rset.next())
	    		return true;
		} catch (SQLException a) {
			return false;
		}

        return false;
	}
	
	public RegisteredUser getRegisteredUser(String theUsername) {
		RegisteredUser user = null;
		
		try {
			String strSelect = "SELECT * FROM RegisteredUser WHERE username = '" + theUsername + "'";
	        ResultSet rset = stmt.executeQuery(strSelect);
	        
	        if (rset.next()) {   // Move the cursor to the next row, return false if no more row
	    		String userName = rset.getString("username");
	    		String email = rset.getString("email");
	    		String password = rset.getString("password");
	    		String name = rset.getString("name");
	    		String birthday = rset.getString("birthday");
	    		int numThreads = rset.getInt("numThread");
	    		java.sql.Timestamp userSince = rset.getTimestamp("user_since");
	    		user = new RegisteredUser(userName, email, password, name, birthday, numThreads, userSince);
	         }
		} catch (SQLException a) {
			return null;
		}

        return user;
	}
	
	public VotingThread getThread(String theUser, int theID) {
		VotingThread thread = null;
		
		try {
			String strSelect = "SELECT * FROM Thread WHERE username = '" + theUser + "' AND thread_ID = " + theID;
	        ResultSet rset = stmt.executeQuery(strSelect);
	        
	        if (rset.next()) {   // Move the cursor to the next row, return false if no more rows
	    		String username = rset.getString("username");
	    		String title = rset.getString("title");
	    		String description = rset.getString("description");
	    		int numCandidates = rset.getInt("numCandidates");
	    		String privateStatus = rset.getString("private_status");
	    		String openStatus = rset.getString("open_status");
	    		int ID = rset.getInt("thread_ID");
	    		java.sql.Timestamp date = rset.getTimestamp("date_created");
	    		
	    		thread = new VotingThread(title, description, numCandidates, ID, username, privateStatus, openStatus, date);  		
	         }
		} catch (SQLException a) {
			return null;
		}
		
		return thread;
	}
	
	public void addRegisteredUser(RegisteredUser theUser) {
		try {
		String sqlAdd = "INSERT into RegisteredUser values ('" + theUser.getName() + "', '" + theUser.getUser() + "', '" +
				theUser.getPassword() + "', '" + theUser.getEmail() + "', '"+ theUser.getBirthday() + "', '" + 
				theUser.getUserSince() + "', " + theUser.getNumThreads() + ")";
		
		stmt.executeUpdate(sqlAdd);
		} catch (SQLException a) { }
	}
	
	public void updateRegisteredUser(RegisteredUser theUser) {
		try {
			String sqlUpdate = "Update RegisteredUser set name = '" + theUser.getName() + "', email = '" + theUser.getEmail() + 
				"', birthday =  '"+ theUser.getBirthday() + "' where username = '" + theUser.getUser() + "'";
			stmt.executeUpdate(sqlUpdate);
		} catch (SQLException a) { }
	}
	
	public void addVotingThread(VotingThread theThread) {
		try {
			String sqlAdd = "INSERT into Thread values ('" + theThread.getTitle() + "', '" + theThread.getDescription() + "', " +
				theThread.getNumCandidates() + ", " + theThread.getThreadID() + ", '" + theThread.getDate() + "', '" + 
				theThread.getUsername() + "', '" + theThread.getPrivateStatus() + "', '" + theThread.getOpenStatus() + "')";
			stmt.executeUpdate(sqlAdd);
		} catch (SQLException a) { }
	}
	
	public void addCandidate(Candidate theCandidate) {
		try {
			String sqlAdd = "INSERT into Candidate values ('" + theCandidate.getDescription() + "', " +
				theCandidate.getThreadID() + ", '" + theCandidate.getUsername() + "', "+ theCandidate.getVotes() + ")";
			stmt.executeUpdate(sqlAdd);
		} catch (SQLException a) { }
	}
	
	public int createID(String username) {
		try {
			String sqlSelect = "SELECT id FROM (SELECT username, MAX(thread_ID) id FROM Thread Group By username) AS a WHERE username = '" + username + "'";
			ResultSet rset = stmt.executeQuery(sqlSelect);
        
			if (rset.next()) {   // Move the cursor to the next row, return false if no more row
				int id = rset.getInt("id");
    		
				return id + 1;
			} else
				return 0;
		} catch (SQLException a) { }
		
		return 0;
	}
	
	public List<VotingThread> getThreads() {
		List<VotingThread> threads = new ArrayList<VotingThread>();
		
		try {
			String strSelect = "SELECT * FROM Thread";
	        ResultSet rset = stmt.executeQuery(strSelect);
	        
	        while (rset.next()) {   // Move the cursor to the next row, return false if no more row
	    		String username = rset.getString("username");
	    		String title = rset.getString("title");
	    		String description = rset.getString("description");
	    		int numCandidates = rset.getInt("numCandidates");
	    		String privateStatus = rset.getString("private_status");
	    		String openStatus = rset.getString("open_status");
	    		int ID = rset.getInt("thread_ID");
	    		java.sql.Timestamp date = rset.getTimestamp("date_created");
	    		
	    		threads.add(new VotingThread(title, description, numCandidates, ID, username, privateStatus, openStatus, date));  		
	         }
		} catch (SQLException a) {
			return null;
		}
		
		return threads;
	}
	
	public List<VotingThread> getUserThreads(RegisteredUser theUser) {
		List<VotingThread> threads = new ArrayList<VotingThread>();
		
		try {
			String strSelect = "SELECT * FROM Thread WHERE username = '" + theUser.getUser() + "'";
	        ResultSet rset = stmt.executeQuery(strSelect);
	        
	        while (rset.next()) {   // Move the cursor to the next row, return false if no more row
	    		String username = rset.getString("username");
	    		String title = rset.getString("title");
	    		String description = rset.getString("description");
	    		int numCandidates = rset.getInt("numCandidates");
	    		String privateStatus = rset.getString("private_status");
	    		String openStatus = rset.getString("open_status");
	    		int ID = rset.getInt("thread_ID");
	    		java.sql.Timestamp date = rset.getTimestamp("date_created");
	    		
	    		threads.add(new VotingThread(title, description, numCandidates, ID, username, privateStatus, openStatus, date));
	         }
		} catch (SQLException a) {
			return null;
		}
		
		return threads;
	}
	
	public List<Candidate> getThreadCandidates(VotingThread theThread) {
		List<Candidate> candidates = new ArrayList<Candidate>();
		
		try {
			String strSelect = "SELECT * FROM Candidate WHERE Thread_ID = " + theThread.getThreadID() + 
					" AND username = '" + theThread.getUsername() + "'";
	        ResultSet rset = stmt.executeQuery(strSelect);
	        
	        while (rset.next()) {   // Move the cursor to the next row, return false if no more row
	    		String description = rset.getString("description");
	    		int ID = rset.getInt("thread_ID");
	    		String username = rset.getString("username");
	    		int votes = rset.getInt("votes");
	    		
	    		candidates.add(new Candidate(description, ID, username, votes));	
	        }
		} catch (SQLException a) {
			return null;
		}
		
		return candidates;
	}
	
	public void updateCandidate(String theCandidate, VotingThread theThread) {
		try {
		String sqlUpdate = "Update Candidate set votes = votes + 1 WHERE Candidate.thread_ID = " + theThread.getThreadID() + 
				" AND Candidate.username = '" + theThread.getUsername() + "' AND description = '" + theCandidate + "'";
		
		stmt.executeUpdate(sqlUpdate);
		} catch (SQLException a) {
			JOptionPane.showMessageDialog(null, "Database Error: Update to candidate failed!");
		}
	}
	
	public boolean checkIfUserVoted(RegisteredUser theUser, VotingThread theThread) {	
		try {
			String strSelect = "SELECT * FROM Voted WHERE voter = '" + theUser.getUser() + "' AND thread_ID = " +
		theThread.getThreadID() + " AND creator = '" + theThread.getUsername() + "'";
			
	        ResultSet rset = stmt.executeQuery(strSelect);
	        
	        if (rset.next()) {   // Move the cursor to the next row, return false if no more row
	    		String voter = rset.getString("voter");
	    		String creator = rset.getString("creator");
	    		int thread_ID = rset.getInt("thread_ID");
	    		
	    		if (voter.equals(theUser.getUser()) && creator.equals(theThread.getUsername()) && thread_ID == theThread.getThreadID())
	    			return true;
	        }
		} catch (SQLException a) {
			return false;
		}
		
		return false;
	}
	
	public void addVote(RegisteredUser theUser, VotingThread theThread) {
		try {
			String sqlAdd = "INSERT into Voted values ('" + theUser.getUser() + "', '" + 
						theThread.getUsername() + "', " + theThread.getThreadID() + ")";
			stmt.executeUpdate(sqlAdd);
		} catch (SQLException a) { 
			JOptionPane.showMessageDialog(null, "Database Error: Insertion of vote proof failed!");
		}
	}
	
	public boolean checkIfThreadIsOpen(VotingThread theThread) {
		try {
			String strSelect = "SELECT * FROM Thread WHERE username = '" + theThread.getUsername() + 
					"' AND thread_ID = " + theThread.getThreadID();
	        ResultSet rset = stmt.executeQuery(strSelect);
	        
	        if (rset.next()) {   // Move the cursor to the next row, return false if no more row
	    		String status = rset.getString("open_status");
	    		
	    		if (status.equals("TRUE"))
	    			return true;
	        }
		} catch (SQLException a) {
			return false;
		}
		
		return false;	
	}
	
	public boolean checkIfThreadIsPrivate(VotingThread theThread) {
		try {
			String strSelect = "SELECT * FROM Thread WHERE username = '" + theThread.getUsername() + 
					"' AND thread_ID = " + theThread.getThreadID();
	        ResultSet rset = stmt.executeQuery(strSelect);
	        
	        if (rset.next()) {   // Move the cursor to the next row, return false if no more row
	    		String status = rset.getString("private_status");
	    		
	    		if (status.equals("TRUE"))
	    			return true;
	        }
		} catch (SQLException a) {
			return false;
		}
		
		return false;	
	}
	
	public void closeVotingOnThread(VotingThread theThread) {
		try {
			String sqlUpdate = "Update Thread set open_status = 'FALSE' WHERE Thread.thread_ID = " + theThread.getThreadID() + 
				" AND Thread.username = '" + theThread.getUsername() + "'";
			
			stmt.executeUpdate(sqlUpdate);
		} catch (SQLException a) {
			JOptionPane.showMessageDialog(null, "Database Error: Update to thread failed!");
		}
	}
	
	public List<Group> getPrivateGroups(RegisteredUser user) {
		List<Group> groups = new ArrayList<Group>();
		
		try {
			String strSelect = "SELECT * FROM GroupMember WHERE  username = '" + user.getUser() + "'";
	        ResultSet rset = stmt.executeQuery(strSelect);
	        
	        while (rset.next()) {   // Move the cursor to the next row, return false if no more row
	    		String groupName = rset.getString("group_name");
	    		String groupCreator = rset.getString("group_creator");
	    		
	    		groups.add(new Group(groupName, groupCreator));  		
	         }
		} catch (SQLException a) {
			return null;
		}
		
		return groups;
	}
	
	public List<GroupMember> getGroupMembers(Group theGroup) {
		List<GroupMember> groupMembers = new ArrayList<GroupMember>();
		
		try {
			String strSelect = "SELECT * FROM GroupMember WHERE  group_name = '" + theGroup.getGroupName() + 
					"' AND group_creator = '" + theGroup.getAdminName() + "'";
	        ResultSet rset = stmt.executeQuery(strSelect);
	        
	        while (rset.next()) {   // Move the cursor to the next row, return false if no more row
	        	
	    		String groupName = rset.getString("group_name");
	    		String groupCreator = rset.getString("group_creator");
	    		String username = rset.getString("username");
	    		
	    		groupMembers.add(new GroupMember(groupName, groupCreator, username));  		
	         }
		} catch (SQLException a) {
			return null;
		}
		
		return groupMembers;
	}
	
	public List<GroupMember> getGroupThreads2(Group theGroup) {
		List<GroupMember> groupMembers = new ArrayList<GroupMember>();
		
		try {
			String strSelect = "SELECT * FROM GroupMember WHERE  group_name = '" + theGroup.getGroupName() + 
					"' AND group_creator = '" + theGroup.getAdminName() + "'";
	        ResultSet rset = stmt.executeQuery(strSelect);
	        
	        while (rset.next()) {   // Move the cursor to the next row, return false if no more row
	    		String groupName = rset.getString("group_name");
	    		String groupCreator = rset.getString("group_creator");
	    		String username = rset.getString("username");
	    		
	    		groupMembers.add(new GroupMember(groupName, groupCreator, username));  		
	         }
		} catch (SQLException a) {
			return null;
		}
		
		return groupMembers;
	}
	
	public List<GroupThread> getGroupThreads(Group theGroup) {
		List<GroupThread> groupThreads = new ArrayList<GroupThread>();
		
		try {
			String strSelect = "SELECT * FROM GroupThread WHERE group_name = '" + theGroup.getGroupName() + 
					"' AND group_creator = '" + theGroup.getAdminName() + "'";
	        ResultSet rset = stmt.executeQuery(strSelect);
	        
	        while (rset.next()) {   // Move the cursor to the next row, return false if no more row
	    		int threadID = rset.getInt("thread_ID");
	    		String username = rset.getString("thread_creator");
	    		String title = rset.getString("title");
	    		
	    		groupThreads.add(new GroupThread(theGroup.getGroupName(), theGroup.getAdminName(), threadID, username, title));
	         }
		} catch (SQLException a) {
			JOptionPane.showMessageDialog(null, "Database Error: Error retrieving GroupThreads!");
			return null;
		}
		
		return getGroupThreadsHelper(groupThreads);
	}
	
	public List<GroupThread> getGroupThreadsHelper(List<GroupThread> theGroupThreads) {
		List<GroupThread> votingThreads = new ArrayList<GroupThread>();
		
		for (GroupThread thread : theGroupThreads) {
			//votingThreads.add(getThread(thread.getThreadCreator(), thread.getThreadID()));
		}
		
		return votingThreads;
	}
	
	public void addGroup(Group theGroup) {
		try {
			String sqlAdd = "INSERT into PrivateGroup values ('" + theGroup.getGroupName() + "', '" + 
						theGroup.getAdminName() + "')";
			stmt.executeUpdate(sqlAdd);
		} catch (SQLException a) { 
			JOptionPane.showMessageDialog(null, "Database Error: Insertion of vote proof failed!");
		}
	}
	
	public void addGroupMember(GroupMember theMember) {
		try {
			String sqlAdd = "INSERT into GroupMember values ('" + theMember.getGroupName() + "', '" + 
						theMember.getGroupCreator() + "', '" + theMember.getUsername() + "')";
			stmt.executeUpdate(sqlAdd);
		} catch (SQLException a) { 
			JOptionPane.showMessageDialog(null, "Database Error: Insertion of vote proof failed!");
		}
	}
	
	public void addGroupThread(VotingThread theThread, Group theGroup) {
		try {
			String sqlAdd = "INSERT into GroupThread values ('" + theGroup.getGroupName() + "', '" + 
						theGroup.getAdminName() + "', '" + theThread.getTitle() +"', " + theThread.getThreadID() + ", '" + theThread.getUsername() + "')";
			stmt.executeUpdate(sqlAdd);
		} catch (SQLException a) { 
			JOptionPane.showMessageDialog(null, "Database Error: Insertion of group thread proof failed!");
		}
	}
}