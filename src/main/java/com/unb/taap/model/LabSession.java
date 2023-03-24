package com.unb.taap.model;

import com.unb.taap.core.iterator.StudentCollection;
import com.unb.taap.core.observer.EventManager;
import com.unb.taap.core.observer.EventType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class LabSession {

  private final String labID;
  private final String labName;
  private final Queue<Student> studentsRequestQueue;
  private final Map<String, TeachingAssistant> teachingAssistants;
  private final Map<String, Student> students;

  private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

  private final String userToken;
  private final String taToken;
  private final EventManager eventManager;

  private final StudentCollection studentCollection = new StudentCollection();

  public LabSession(String labName) {
    this.labID = getHash();
    this.labName = labName;
    this.taToken = String.format("%s.Z", this.labID);
    this.userToken = getHash();
    this.teachingAssistants = new HashMap<>();
    this.students = new HashMap<>();
    studentsRequestQueue = new LinkedList<>();
    eventManager = new EventManager();
  }

  public void registerTeachingAssistant(TeachingAssistant teachingAssistant) {
    teachingAssistants.put(teachingAssistant.getId(), teachingAssistant);
    eventManager.subscribe(EventType.REQ_SUBMITTED, teachingAssistant);
    eventManager.subscribe(EventType.REQ_REVERTED, teachingAssistant);
  }

  public void registerStudent(Student student) {
    studentCollection.addStudent(student);
    students.put(student.getId(), student);
    eventManager.subscribe(EventType.REQ_EVALUATED, student);
  }

  public void end() {
    students.clear();
    teachingAssistants.clear();
    studentsRequestQueue.clear();
    for (SseEmitter sseEmitter : sseEmitters.values()) {
      sseEmitter.complete();
    }
    sseEmitters.clear();
  }

  public String getLabID() {
    return labID;
  }

  public String getLabName() {
    return labName;
  }

  public String getUserToken() {
    return userToken;
  }

  public String getTaToken() {
    return taToken;
  }

  public TeachingAssistant getTeachingAssistant(String id) {
    return teachingAssistants.get(id);
  }

  public Student getStudent(String id) {
    return students.get(id);
  }

  public StudentCollection getStudentCollection() {
    return studentCollection;
  }

  public int submitReviewRequest(Student student) {
    studentsRequestQueue.add(student);
    eventManager.notify(EventType.REQ_SUBMITTED);
    return studentsRequestQueue.size();
  }

  public int revertReviewRequest(Student student) {
    studentsRequestQueue.remove(student);
    eventManager.notify(EventType.REQ_REVERTED);
    return studentsRequestQueue.size();
  }

  public void markReviewAsComplete(Student student) {
    student.setQueuePosition(-1);
    eventManager.notify(EventType.REQ_EVALUATED);
  }

  public synchronized Student getNextStudentReviewRequest() {
    return studentsRequestQueue.poll();
  }

  public int getStudentsQueueSize() {
    return studentsRequestQueue.size();
  }

  public SseEmitter createEmitter(String emitterKey) {
    sseEmitters.put(emitterKey, new SseEmitter(-1L));
    sseEmitters.get(emitterKey).onError((ex) -> {
      System.out.println(
          "Removing emitter with key " + emitterKey + " because of error: " + ex.getMessage());
      sseEmitters.remove(emitterKey);
    });
    return sseEmitters.get(emitterKey);
  }

  public SseEmitter getEmitter(String emitterKey) {
    return sseEmitters.get(emitterKey);
  }

  public void removeEmitter(String emitterKey) {
    sseEmitters.remove(emitterKey);
  }

  private String getHash() {
    Random random = new Random();
    return String.valueOf(random.nextInt(9000) + 1000);
  }
}
