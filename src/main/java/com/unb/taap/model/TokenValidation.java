package com.unb.taap.model;

public class TokenValidation {

  private boolean isValid;
  private UserType userType;
  private String labSessionName;

  public TokenValidation(boolean isValid, UserType userType, String labSessionName) {
    this.isValid = isValid;
    this.userType = userType;
    this.labSessionName = labSessionName;
  }

  public void setValid(boolean valid) {
    isValid = valid;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }

  public boolean isValid() {
    return isValid;
  }

  public UserType getUserType() {
    return userType;
  }

  public String getLabID() {
    return labSessionName;
  }

  public void setLabSessionName(String labSessionName) {
    this.labSessionName = labSessionName;
  }

}
