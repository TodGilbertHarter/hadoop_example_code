/**
 * 
 */
package com.boeing.hadoop.security.test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author tharter
 *
 */
public class Rule {
	private final TestMatrix testMatrix;
	private final String id;
	private final Resource resource;
	private final Set<Action> actions;
	private final Set<Subject> subjects;
	
	/**
	 * 
	 */
	public Rule(final String id, final Resource resource, final Set<Action> actions, 
			final Set<Subject> subjects, final TestMatrix testMatrix) {
		this.id = id;
		this.resource = resource;
		this.actions = new HashSet<Action>(actions);
		this.subjects = new HashSet<Subject>(subjects);
		this.testMatrix = testMatrix;
	}

	public Resource getResource() {
		return resource;
	}

	public String getId() {
		return id;
	}

	public boolean hasAction(final Action a) {
		return actions.contains(a);
	}
	
	public boolean hasSubject(final Subject s) {
		return subjects.contains(s);
	}
	
	public Stream<Action> actions() {
		return actions.stream();
	}
	
	public Stream<Subject> subjects() {
		return subjects.stream();
	}

	@Override
	public String toString() {
		return "Rule [id=" + id + ", resource=" + resource + ", actions=" + actions + ", subjects=" + subjects + "]";
	}

	public TestMatrix getTestMatrix() {
		return testMatrix;
	}
}
