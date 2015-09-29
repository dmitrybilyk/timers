package de.hpfsc.web;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hpfsc.shared.*;

import java.util.ArrayList;

public interface ClientsServiceAsync {
  void addClient(WhoseSessionEnum whoseSessionEnum, long id, String name, String comment, long startTime, long totalSum, AsyncCallback<Long> async);
  void addSession(long id, String name, String comment, long totalTime, long totalSum, AsyncCallback<Void> async);
  void updateSession(long id, String name, String comment, AsyncCallback<Void> async);
  void updateSessionOwner(long id, WhoseSessionEnum whoseSession, AsyncCallback<Void> async);

  void getClients(AsyncCallback<ArrayList<de.hpfsc.shared.Client>> async);

  void removeSession(long id, AsyncCallback<Void> async);
  void acceptSession(long id, AsyncCallback<Void> async);
  void stopSession(long id, AsyncCallback<Void> async);
  void startSession(long id, long startTime, AsyncCallback<Void> async);

//  void sendCompleteNotification(long id, String name, String comment, long totalTime, long totalSum, AsyncCallback<Void> async);

}