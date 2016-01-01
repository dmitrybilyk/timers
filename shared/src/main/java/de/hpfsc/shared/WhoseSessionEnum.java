package de.hpfsc.shared;

/**
 * Created by dmitry on 18.07.15.
 */
public enum WhoseSessionEnum {

  FIRST("1", 0), SECOND("2", 1), ADMIN("A", 2), NOBODY("N", -1);

  private int order;
  private String shortName;


  WhoseSessionEnum(String shortName, int order) {
    this.shortName = shortName;
    this.order = order;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }
}
