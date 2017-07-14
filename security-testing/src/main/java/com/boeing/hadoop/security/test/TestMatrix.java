/**
 * 
 */
package com.boeing.hadoop.security.test;

import java.util.Map;
import java.util.stream.Stream;

/**
 * @author tharter
 *
 */
public class TestMatrix {
	private final Map<String,Subject> subjects;
	private final Map<String,Resource> resources;
	private final Map<String,Action> actions;
	private final Map<String,Rule> rules;
	
	/**
	 * 
	 */
	public TestMatrix(final Map<String,Subject> subjects, final Map<String,Resource> resources, 
			final Map<String,Action> actions, final Map<String,Rule> rules ) {
		this.subjects = subjects;
		this.resources = resources;
		this.actions = actions;
		this.rules = rules;
	}

	@Override
	public String toString() {
		return "TestMatrix [subjects=" + subjects + ", resources=" + resources + ", actions=" + actions + ", rules="
				+ rules + "]";
	}

	public Stream<Subject> subjects() {
		return subjects.values().stream();
	}
	
	public Stream<Resource> resources() {
		return resources.values().stream();
	}
	
	public Stream<Action> actions() {
		return actions.values().stream();
	}
	
	public Stream<Rule> rules() {
		return rules.values().stream();
	}
	
	public Action getAction(String aClass) {
		return actions.get(aClass);
	}
	
	public Resource getResource(String id) {
		return resources.get(id);
	}
	
	public Subject getSubject(String id) {
		return subjects.get(id);
	}
	
	public Rule getRule(String id) {
		return rules.get(id);
	}
}
