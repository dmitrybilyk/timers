/*
 * Ext GWT 2.2.1 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.hpfsc.shared;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.util.DateWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//sessionCreator, clientName, comment, timer, sum, timerOnButton, timerOffButton, acceptSessionButton

public class Session extends BaseModel {

  public Session() {
  }

  public Session(String name, double open, double change, double pctChange, Date date, String industry) {
    initPossibleNames();
    set("name", name);
    set("open", open);
    set("change", change);
    set("percentChange", pctChange);
    set("date", date);
    set("industry", industry);
    set("split", new Boolean(Math.random() > .5));
    set("type", getType());
  }

  public Session(String name, String symbol, double open, double last) {
    initPossibleNames();
    set("name", name);
    set("symbol", symbol);
    set("open", open);
    set("last", last);
    set("date", new DateWrapper().addDays(-(int)(Math.random() * 100)).asDate());
    set("change", last - open);
    set("split", new Boolean(Math.random() > .5));
    set("type", getType());
  }

  private void initPossibleNames() {
    for (ClientNamesEnum clientName: ClientNamesEnum.values()) {
      possibleNames.add(clientName.name());
    }
  }

  private List<String> possibleNames = new ArrayList<>();

  public List<String> getPossibleNames() {
    return possibleNames;
  }

  public void setPossibleNames(List<String> possibleNames) {
    this.possibleNames = possibleNames;
  }

  public double getChange() {
    return getLast() - getOpen();
  }

  public String getIndustry() {
    return get("industry");
  }

  public double getLast() {
    Double open = (Double) get("last");
    return open.doubleValue();
  }

  public Date getLastTrans() {
    return (Date) get("date");
  }

  public String getName() {
    return (String) get("name");
  }

  public double getOpen() {
    Double open = (Double) get("open");
    return open.doubleValue();
  }

  public double getPercentChange() {
    return getChange() / getOpen();
  }

  public String getSymbol() {
    return (String) get("symbol");
  }

  public void setIndustry(String industry) {
    set("industry", industry);
  }

  public String toString() {
    return getName();
  }

  private String getType() {
    double r = Math.random();
    if (r <= .25) {
      return "Auto";
    } else if (r > .25 && r <= .50) {
      return "Media";
    } else if (r > .5 && r <= .75) {
      return "Medical";
    } else {
      return "Tech";
    }
  }

}
