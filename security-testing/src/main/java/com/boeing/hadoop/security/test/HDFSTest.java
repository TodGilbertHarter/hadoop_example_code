/**
 * 
 */
package com.boeing.hadoop.security.test;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.hadoop.gateway.shell.BasicResponse;
import org.apache.hadoop.gateway.shell.Hadoop;
import org.apache.hadoop.gateway.shell.hdfs.Hdfs;
import org.apache.http.HttpStatus;

/**
 * Performs tests of HDFS access rules. 
 * @author tharter
 *
 */
public class HDFSTest extends Test {

	public HDFSTest(Rule rule) {
		super(rule);
	}

	private Set<Subject> expandGroup(Group g, Set<Subject> users) {
		Set<Subject> groupUsers = getRule().subjects().filter((s) -> (s instanceof User)).filter((s) -> (((User)s).hasGroup(g.getId()) )).collect(Collectors.toSet());
		return groupUsers;
	}
	
	private Map<String,Set<Subject>> buildGroupUsers(Set<Subject> groups, Set<Subject> users) {
		Map<String,Set<Subject>> usersByGroup = new HashMap<String,Set<Subject>>();
		for(Subject rg : groups) {
			Group g = (Group) rg;
			Set<Subject> groupUsers = expandGroup(g,users);
			usersByGroup.put(g.getId(), groupUsers);
		}
		return usersByGroup;
	}
	
	private void launchTest(Subject us, String endPoint, boolean isUserPresent, Action action, boolean isActionPresent) throws TestGeneratorException {
		User user = (User) us;
		Hadoop session = null;
		try {
				System.out.println("Logging in as user "+user.getName());
				session = Hadoop.login(endPoint, user.getCredential1(),user.getCredential2());
			} catch (URISyntaxException e) {
				e.printStackTrace();
				throw new TestGeneratorException("Test Failed: bad endpoint "+endPoint,e);
			}
		String rPath = getRule().getResource().getName();
		BasicResponse response = null;
		try {
			response = makeRequest(session,rPath,action);
			if(response != null)
				analyzeResponse(isUserPresent,isActionPresent,response);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Test failed with exception "+e.getLocalizedMessage());
		}
	}

	private BasicResponse makeRequest(Hadoop session, String rPath, Action action) throws TestGeneratorException {
		if(action.getaClass().equals("hdfsdir")) {
			if(action.getName().equals("read")) {
				return Hdfs.ls(session).dir(rPath).now();
			} else if(action.getName().equals("write")) {
				return Hdfs.mkdir(session).dir(rPath+"/subtest").now();
			} else if(action.getName().equals("execute")) {
				return null;
			}
		}
		throw new TestGeneratorException("No test exists for this action");
	}
	
	private void analyzeResponse(boolean isUserPresent, boolean isActionPresent, BasicResponse response) {
		boolean isSuccess = response.getStatusCode() == HttpStatus.SC_OK; // Not sure if this is sufficient...
		boolean expected = isUserPresent && isActionPresent;
		if(isSuccess == expected) {
			System.out.println("Got expected result for rule "+getRule()+" with isUserPresent of "+isUserPresent+", and isActionPresent of "+isActionPresent);
		} else {
			System.out.println("Failed to get expected result for rule "+getRule()+" with isUserPresent of "+isUserPresent+", and isActionPresent of "+isActionPresent);
		}
	}
	
	private Map<Subject, Boolean> makeUserPresenceMap(Set<Subject> ruleUsers, Set<Subject> users) {
		Map<Subject,Boolean> userPresenceMap = new HashMap<Subject,Boolean>();
		for(Subject user : users) {
			userPresenceMap.put(user, ruleUsers.contains(user));
		}
		return userPresenceMap;
	}

	/* (non-Javadoc)
	 * @see com.boeing.hadoop.security.test.Test#runTest()
	 */
	@Override
	public void runTest(String endPoint) throws TestGeneratorException {
		System.out.println("Running Test for "+getRule().toString());
		Set<Subject> users = getRule().getTestMatrix().subjects().filter((s) -> (s instanceof User)).collect(Collectors.toSet());
		Set<Subject> ruleUsers = getRule().subjects().filter((s) -> (s instanceof User)).collect(Collectors.toSet());
//		Set<Subject> ruleGroups = getRule().subjects().filter((s) -> (s instanceof Group)).collect(Collectors.toSet());
//		Map<String,Set<Subject>> usersByGroup = buildGroupUsers(ruleGroups,users);
		Map<Subject,Boolean> isUserPresentinRule = makeUserPresenceMap(ruleUsers,users);
		
		for(Subject foob : users) {
			Set<Action> actions = getRule().getTestMatrix().actions().collect(Collectors.toSet());
			Set<Action> ruleActions = getRule().actions().collect(Collectors.toSet());
			Map<Action,Boolean> isActionPresentInRule = makeActionPresenceMap(ruleActions,actions);
			for(Action action : isActionPresentInRule.keySet()) {
				launchTest(foob,endPoint,isUserPresentinRule.get(foob), action, isActionPresentInRule.get(action));
			}
		}
	}

	private Map<Action, Boolean> makeActionPresenceMap(Set<Action> ruleActions, Set<Action> actions) {
		Map<Action,Boolean> actionPresenceMap = new HashMap<Action,Boolean>();
		for(Action action : actions) {
			actionPresenceMap.put(action, ruleActions.contains(action));
		}
		return actionPresenceMap;
	}

}
