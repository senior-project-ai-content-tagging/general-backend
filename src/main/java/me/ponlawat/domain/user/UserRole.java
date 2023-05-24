package me.ponlawat.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserRole {
    @JsonProperty("admin")
    ADMIN,
    @JsonProperty("member")
    MEMBER;

    @JsonCreator
    public static UserRole fromString(String key) {
        for(UserRole role : UserRole.values()) {
            if(role.name().equalsIgnoreCase(key)) {
                return role;
            }
        }
        return null;
    }
}
