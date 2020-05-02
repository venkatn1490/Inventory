package com.venkat.app.model;


import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
 
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private Integer roleId;
 
    public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		System.out.println("User Role Get Name "+ name);
        return name;
    }
 
    public void setName(String name) {
    	System.out.println("User Role Set Name "+ name);
        this.name = name;
    }
 
    
    public String getAuthority() {
        return this.name;
    }
 
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Role [name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }
}