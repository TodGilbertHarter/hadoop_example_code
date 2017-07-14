/**
 * 
 */
package com.boeing.hadoop.security.test;

/**
 * @author tharter
 *
 */
public class Action {
	private final String aClass;
	private final String name;
	
	/**
	 * 
	 */
	public Action(final String aClass, final String name) {
		this.aClass = aClass;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Action [aClass=" + aClass + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aClass == null) ? 0 : aClass.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (aClass == null) {
			if (other.aClass != null)
				return false;
		} else if (!aClass.equals(other.aClass))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getaClass() {
		return aClass;
	}

	public String getName() {
		return name;
	}

}
