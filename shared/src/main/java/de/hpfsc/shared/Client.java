package de.hpfsc.shared;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.core.FastSet;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BeanModelTag;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.NestedModelUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dmitry on 11.07.15.
 */
public class Client extends BaseModel implements Serializable, Comparable {
  private Long id;
  WhoseSessionEnum whoseSession = WhoseSessionEnum.ADMIN;

  private long creationalTime;
  private Long stopTime = (long) 0;
  private String name;
  private String comment = "";
  private long startTime;
  private long totalSum;
  private boolean isInProgress;
  private boolean isExpired;
  private long limitTime = 1000 * 60 * 60 * 5;
  private boolean accepted;
  private boolean isDeleted;


  public enum Fields {
    NAME("name");

    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field : Fields.values())
        if (field.getFieldName().equals(fieldName))
          return field;
      return null;
    }
  }


  public Client(WhoseSessionEnum whoseSession, long id, String name, String comment, long startTime) {
    this.creationalTime = System.currentTimeMillis();
    this.whoseSession = whoseSession;
    this.id = id;
    this.name = name;
    this.comment = comment;
    this.startTime = startTime;

  }

  public Client() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getTotalSum() {
    return totalSum;
  }

  public void setTotalSum(long totalSum) {
    this.totalSum = totalSum;
  }

  public boolean isInProgress() {
    return isInProgress;
  }

  public void setInProgress(boolean isInProgress) {
    this.isInProgress = isInProgress;
  }

  public boolean isExpired() {
    return isExpired;
  }

  public void setIsExpired(boolean isExpired) {
    this.isExpired = isExpired;
  }

  public long getLimitTime() {
    return limitTime;
  }

  public void setLimitTime(long limitTime) {
    this.limitTime = limitTime;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
  }

  public long getCreationalTime() {
    return creationalTime;
  }

  public void setCreationalTime(long creationalTime) {
    this.creationalTime = creationalTime;
  }

  public long getStopTime() {
    return stopTime;
  }

  public void setStopTime(long stopTime) {
    this.stopTime = stopTime;
  }

  public WhoseSessionEnum getWhoseSession() {
    return whoseSession;
  }

  public void setWhoseSession(WhoseSessionEnum whoseSession) {
    this.whoseSession = whoseSession;
  }

  public int compareTo(Object o) {
    Client compared = (Client) o;
    if (getCreationalTime() > compared.getCreationalTime()) {
     return 1;
    }
    return -1;
  }

//  @Override
//  public <X> X get(String property) {
//    return null;
//  }
//
//  @Override
//  public Map<String, Object> getProperties() {
//    return null;
//  }
//
//  @Override
//  public Collection<String> getPropertyNames() {
//    return null;
//  }
//
//  @Override
//  public <X> X remove(String property) {
//    return null;
//  }
//
//  @Override
//  public <X> X set(String property, X value) {
//    return null;
//  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Client client = (Client) o;

    return id.equals(client.id);

  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
