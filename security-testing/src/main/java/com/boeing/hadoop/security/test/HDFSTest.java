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

/**
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
	
	private void launchTest(Subject us, String endPoint, boolean isPresent) throws TestGeneratorException {
		User user = (User) us;
		Hadoop session = null;
		try {
				System.out.print("Logging in as user "+user.getName());
				session = Hadoop.login(endPoint, user.getCredential1(),user.getCredential2());
			} catch (URISyntaxException e) {
				e.printStackTrace();
				throw new TestGeneratorException("Test Failed ",e);
			}
		String rPath = getRule().getResource().getName();
		
		BasicResponse response = Hdfs.ls(session).dir(rPath).now();
		analyzeResponse(isPresent,response);
	}

	private void analyzeResponse(boolean isPresent, BasicResponse response) {
		//TODO: analyze the response from HDFS
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
		Set<Subject> ruleGroups = getRule().subjects().filter((s) -> (s instanceof Group)).collect(Collectors.toSet());
//		Map<String,Set<Subject>> usersByGroup = buildGroupUsers(ruleGroups,users);
		Map<Subject,Boolean> isUserPresentinRule = makeUserPresenceMap(ruleUsers,users);
		
		for(Subject foob : users) {
			launchTest(foob,endPoint,isUserPresentinRule.get(foob));
		}
	}

}
