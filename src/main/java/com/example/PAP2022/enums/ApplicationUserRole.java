package com.example.PAP2022.enums;

import com.example.PAP2022.exceptions.InvalidApplicationUserRoleException;

public enum ApplicationUserRole {
  USER,
  TEAM_LEADER,
  ADMIN;

  public static ApplicationUserRole parse(String userRole) throws InvalidApplicationUserRoleException {
    return switch (userRole) {
      case "USER" -> ApplicationUserRole.USER;
      case "TEAM_LEADER" -> ApplicationUserRole.TEAM_LEADER;
      case "ADMIN" -> ApplicationUserRole.ADMIN;
      default -> throw new InvalidApplicationUserRoleException("Role o user can be only USER, TEAM_LEADER or ADMIN");
    };
  }
}