package model;

import java.util.*;

import static database.Constants.Roles.ROLES;

// BEAN

public class User {

    private Long id;
    private String username;
    private String password;
    private List<Role> roles = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }
    public Role getRole() {
        return roles.get(0);
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public void addRole(Long roleId) {
        Role role = new Role(roleId, ROLES[(roleId.intValue()-1)], null);
        roles.add(role);
    }
    @Override
    public String toString() {
        StringBuilder rolesStringBuilder = new StringBuilder("[");
        for (Role role : roles) {
            rolesStringBuilder.append(role.toString()).append(", ");
        }
        if (!roles.isEmpty()) {
            rolesStringBuilder.setLength(rolesStringBuilder.length() - 2);
        }
        rolesStringBuilder.append("]");

        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + rolesStringBuilder.toString() +
                '}';
    }

}
