/**
 * 
 */
package com.boeing.hadoop.security.test;

/**
 * @author tharter
 *
 */
public class Resource {
	private final String id;
	private final String name;
	private final String rClass;

	/**
	 * 
	 */
	public Resource(String id, String name, String rClass) {
		this.id = id;
		this.name = name;
		this.rClass = rClass;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", name=" + name + ", rClass=" + rClass + "]";
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getrClass() {
		return rClass;
	}
}
