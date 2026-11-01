
package com.stockmanagement.server.dto;

import com.stockmanagement.server.entity.User;

public class UserResponse {

    private Long id;
    private String username;
    private User.Role role;

    public UserResponse(Long id, String username, User.Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public User.Role getRole() {
        return role;
    }

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}
