package com.unb.taap.core.singleton;

import com.unb.taap.core.common.Utils;
import com.unb.taap.core.state.FreeState;
import com.unb.taap.model.LabSession;
import com.unb.taap.model.Student;
import com.unb.taap.model.TeachingAssistant;
import com.unb.taap.model.TokenValidation;
import com.unb.taap.model.UserType;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class TAAPManager {

  private static TAAPManager instance;
  private final Map<String, LabSession> labSessions;
  private final Map<String, String> userTokenLabIDMapping;

  private TAAPManager() {
    this.labSessions = new HashMap<>();
    this.userTokenLabIDMapping = new HashMap<>();
  }

  public static TAAPManager getInstance() {
    if (instance == null) {
      instance = new TAAPManager();
    }
    return instance;
  }

  public LabSession createLabSession(String labName) {
    LabSession labSession = new LabSession(labName);
    labSessions.put(labSession.getLabID(), labSession);
    userTokenLabIDMapping.put(labSession.getUserToken(), labSession.getLabID());
    return labSession;
  }

  public LabSession getLabSession(String labID) {
    return labSessions.get(labID);
  }

  public void removeLabSession(String labID) {
    labSessions.remove(labID);
  }

  public void registerParticipant(
      String name, String id, String seat, String token, UserType userType, String labID) {
    LabSession labSession = labSessions.get(labID);
    if (labSession != null) {
      if (UserType.TA.equals(userType)) {
        TeachingAssistant teachingAssistant = new TeachingAssistant(name, id, token);
        labSession.registerTeachingAssistant(teachingAssistant);
      } else {
        Student student = new Student(name, id, seat, token);
        labSession.registerStudent(student);
      }
    }
  }

  public TokenValidation validateToken(String token) {
    TokenValidation tokenValidation = new TokenValidation(false, null, null);
    String labID = getLabID(token);
    LabSession labSession = labSessions.get(labID);
    if (labSession != null) {
      tokenValidation.setLabSessionName(labID);
      if (labSession.getTaToken().equals(token)) {
        tokenValidation.setValid(true);
        tokenValidation.setUserType(UserType.TA);
      } else if (labSession.getUserToken().equals(token)) {
        tokenValidation.setValid(true);
        tokenValidation.setUserType(UserType.STUDENT);
      }
    }
    return tokenValidation;
  }

  public SseEmitter createEmitter(String name, String userToken, String userID) {
    String labID = getLabID(userToken);
    LabSession labSession = labSessions.get(labID);
    if (labSession != null) {
      return labSession.createEmitter(Utils.getEmitterKey(name, userToken, userID));
    }
    return null;
  }

  public SseEmitter getEmitter(String name, String userToken, String userID) {
    String labID = getLabID(userToken);
    LabSession labSession = labSessions.get(labID);
    if (labSession != null) {
      return labSession.getEmitter(Utils.getEmitterKey(name, userToken, userID));
    }
    return null;
  }

  public void removeEmitter(String name, String userToken, String userID) {
    String labID = getLabID(userToken);
    LabSession labSession = labSessions.get(labID);
    if (labSession != null) {
      labSession.removeEmitter(Utils.getEmitterKey(name, userToken, userID));
    }
  }

  public void enableTAForNextEvaluation(String token, String id) {
    TokenValidation validation = validateToken(token);
    if (UserType.TA.equals(validation.getUserType())) {
      LabSession labSession = labSessions.get(validation.getLabID());
      TeachingAssistant teachingAssistant = labSession.getTeachingAssistant(id);
      teachingAssistant.changeState(new FreeState(teachingAssistant));
    }
  }

  public void endLab(String labID) {
    LabSession labSession = TAAPManager.getInstance().getLabSession(labID);
    if (labSession != null) {
      labSession.end();
    }
    removeLabSession(labID);
  }

  public String getLabID(String token) {
    if (token.endsWith("Z")) {
      return token.split("\\.")[0];
    } else {
      return userTokenLabIDMapping.get(token);
    }
  }
}
