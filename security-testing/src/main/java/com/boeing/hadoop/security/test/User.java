/**
 * 
 */
package com.boeing.hadoop.security.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * @author tharter
 *
 */
public class User extends Subject {
	private final String credential1;
	private final String credential2;

	private final Map<String,Group> groups;
	/**
	 * @param id
	 * @param name
	 */
	public User(final String id, final String name, final String credential1, final String credential2, final Map<String,Group> groups) {
		super(id, name);
		this.groups = new HashMap<String,Group>(groups);
		this.credential1 = credential1;
		this.credential2 = credential2;
	}
	
	public Stream<Entry<String,Group>> groups() {
		return groups.entrySet().stream();
	}
	
	public boolean hasGroup(String groupId) {
		return groups.containsKey(groupId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		return true;
	}

	public String getCredential1() {
		return credential1;
	}

	public String getCredential2() {
		return credential2;
	}

}