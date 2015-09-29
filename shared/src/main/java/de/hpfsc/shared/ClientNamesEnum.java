package de.hpfsc.shared;

/**
 * Created by dmitry on 18.07.15.
 */
public enum ClientNamesEnum {

  BAH(0), MOCART(1), BILYK(2), GORBATOV(3);

  private int order;


  ClientNamesEnum(int order) {
    this.order = order;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }
}
