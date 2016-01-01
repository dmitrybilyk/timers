package de.hpfsc.web;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hpfsc.shared.*;

import java.util.ArrayList;

/**
 * Created by dmitry on 13.07.15.
 */
@RemoteServiceRelativePath("clients")
public interface ClientsService extends RemoteService {
  public Long addClient(WhoseSessionEnum whoseSessionEnum, long id, String name, String comment, long totalTime, long totalSum);
  public ArrayList<de.hpfsc.shared.Client> getClients(WhoseSessionEnum whoseSession, Boolean value, boolean isToShowAccepted);

  void addSession(long id, String name, String comment, long totalTime, long totalSum);

  void updateSession(long id, String name, String comment);
  void removeSession(long id);
//  void sendCompleteNotification(long id, String name, String comment, long totalTime, long totalSum);

  void updateSessionOwner(long id, WhoseSessionEnum whoseSession);

  void stopSession(long id, long totalSum);

  void acceptSession(Client acceptedClient);

  void startSession(long id, long startTime);

  void updateClient(Client updatedClient);

  void markSessionAsDeleted(long id);

  void updateClientSum(Client updatedClient);
}
