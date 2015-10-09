package de.hpfsc.server;


import de.hpfsc.shared.Client;
import de.hpfsc.shared.WhoseSessionEnum;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dmitry on 13.07.15.
 */
public class ClientsHolder {
  public ClientsHolder() {
//    Timer timer = new Timer();
//    timer.schedule(new TimerTask() {
//      @Override
//      public void run() {
//        // Your database code here
//      }
//    }, 2*60*1000);
//    clientMap.put(getClientId(), new Client(true, true, getClientId(), "first name", "first comment", 1000, 1000));
  }

  private Map<Long, Client> clientMap = new LinkedHashMap<Long, Client>();
  public Long addClient(WhoseSessionEnum whoseSession, long id, String name, String comment, long startTime, long sum) {
    long nextId = getClientId();
//    try {
//      runQuartz();
//    } catch (SchedulerException e) {
//      e.printStackTrace();
//    }
    clientMap.put(nextId, new Client(whoseSession, nextId, name, comment, startTime));
    return nextId;
  }

  public Client getClientById(long id) {
    return clientMap.get(id);
  }

  public void removeClient(long id) {
    clientMap.remove(id);
  }

  public void acceptClientSession(long id) {
    clientMap.get(id).setAccepted(true);
  }

  public ArrayList<Client> getClients(boolean isToShowAccepted) {
    ArrayList<Client> clientArrayList = new ArrayList<Client>();
    for (Client client: clientMap.values()) {
      clientArrayList.add(client);
    }
    return clientArrayList;
  }

  private long getClientId(){
    long id = 0;
    for (Client client: clientMap.values()) {
      if (client.getId() > id) {
        id = client.getId();
      }
    }
    return id + 1;
  }

}
