package de.hpfsc.web.anticafe.oldClient;

import de.hpfsc.shared.WhoseSessionEnum;

import java.io.Serializable;

/**
 * Created by dmitry on 11.07.15.
 */
public class Client implements Serializable{
  private String name;
  private String comment;
  private long id;
  private WhoseSessionEnum whoseSession;
  private long startTime;
  private boolean inProgress;
  private boolean accepted;
  private long creationalTime;

  public Client(String name, String comment) {
    this.name = name;
    this.comment = comment;
  }

  public Client() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public WhoseSessionEnum getWhoseSession() {
    return whoseSession;
  }

  public void setWhoseSession(WhoseSessionEnum whoseSession) {
    this.whoseSession = whoseSession;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public boolean isInProgress() {
    return inProgress;
  }

  public void setInProgress(boolean inProgress) {
    this.inProgress = inProgress;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  public long getCreationalTime() {
    return creationalTime;
  }

  public void setCreationalTime(long creationalTime) {
    this.creationalTime = creationalTime;
  }
}
