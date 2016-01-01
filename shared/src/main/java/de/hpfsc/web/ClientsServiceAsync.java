package de.hpfsc.web;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hpfsc.shared.*;

import java.util.ArrayList;

public interface ClientsServiceAsync {
  void addClient(WhoseSessionEnum whoseSessionEnum, long id, String name, String comment, long startTime, long totalSum, AsyncCallback<Long> async);
  void updateClient(Client updatedClient, AsyncCallback<Void> async);
  void updateClientSum(Client updatedClient, AsyncCallback<Void> async);
  void addSession(long id, String name, String comment, long totalTime, long totalSum, AsyncCallback<Void> async);
  void updateSession(long id, String name, String comment, AsyncCallback<Void> async);
  void updateSessionOwner(long id, WhoseSessionEnum whoseSession, AsyncCallback<Void> async);

  void getClients(WhoseSessionEnum whoseSession, Boolean value, boolean isToShowAccepted, AsyncCallback<ArrayList<Client>> async);

  void removeSession(long id, AsyncCallback<Void> async);
  void markSessionAsDeleted(long id, AsyncCallback<Void> async);
  void acceptSession(Client acceptedClient, AsyncCallback<Void> async);
  void stopSession(long id, long totalSum, boolean isExpired, AsyncCallback<Void> async);
  void startSession(long id, long startTime, AsyncCallback<Void> async);

//  void sendCompleteNotification(long id, String name, String comment, long totalTime, long totalSum, AsyncCallback<Void> async);

}
