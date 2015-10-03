package de.hpfsc.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hpfsc.shared.Client;
import de.hpfsc.shared.WhoseSessionEnum;
import de.hpfsc.web.ClientsService;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by dmitry on 13.07.15.
 */
public class ClientsServiceImpl extends RemoteServiceServlet implements ClientsService {

  private ClientsHolder holder = new ClientsHolder();

  public Long addClient(WhoseSessionEnum whoseSession, long id, String name, String comment, long startTime, long totalSum) {
//    sendNotificationEmail(id, name, comment, totalTime, totalSum);
    return holder.addClient(whoseSession, id, name, comment, startTime, totalSum);
  }



  public ArrayList<Client> getClients() {
    ArrayList<Client> clients = holder.getClients();
    Collections.sort(clients);
    return clients;
  }

  public void addSession(long id, String name, String comment, long totalTime, long totalSum) {

  }

  public void updateSession(long id, String name, String comment) {
    Client client = holder.getClientById(id);
    client.setName(name);
    client.setComment(comment);
  }

  public void removeSession(long id) {
    holder.removeClient(id);
  }

//  public void sendCompleteNotification(long id, String name, String comment, long totalTime, long totalSum) {
//    sendNotificationEmail(id, name, comment, totalTime, totalSum);
//  }

  public void updateSessionOwner(long id, WhoseSessionEnum whoseSession) {
    Client client = holder.getClientById(id);
    client.setWhoseSession(whoseSession);
  }

  public void stopSession(long id, long totalSum) {
    Client client = holder.getClientById(id);
    client.setInProgress(false);
    client.setTotalSum(totalSum);
    client.setStopTime(System.currentTimeMillis());
  }

  public void acceptSession(long id) {
    holder.getClientById(id).setAccepted(true);
//    stopSession(id);
  }

  public void startSession(long id, long startTime) {
    Client client = holder.getClientById(id);
    if (client != null) {
      client.setStartTime(startTime);
      client.setStopTime(0);
      client.setInProgress(true);
    }
    ;
  }

  @Override
  public void updateClient(Client updatedClient) {
    Client client = holder.getClientById(updatedClient.getId());
    if (client != null) {
      client.setName(updatedClient.getName());
      client.setComment(updatedClient.getComment());
      client.setTotalSum(updatedClient.getTotalSum());
      client.setWhoseSession(updatedClient.getWhoseSession());
    }
  }


//  private void sendNotificationEmail(long id, String name, String comment, long totalTime, long totalSum) {
//    final String username = "dmitry.bilyk@gmail.com";
//    final String password = "03564503gG";
//
//    Properties props = new Properties();
//    props.put("mail.smtp.auth", "true");
//    props.put("mail.smtp.starttls.enable", "true");
//    props.put("mail.smtp.host", "smtp.gmail.com");
//    props.put("mail.smtp.port", "587");
//
//    Session session = Session.getInstance(props,
//            new javax.mail.Authenticator() {
//              protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//              }
//            });
//
//    try {
//
//      Message message = new MimeMessage(session);
//      message.setFrom(new InternetAddress("dmitry.bilyk@gmail.com"));
//      message.setRecipients(Message.RecipientType.TO,
//              InternetAddress.parse("dik81@mail.ru"));
//      message.setSubject("Session complete");
//      message.setText("Имя -" + name + " Комментарий - " + comment + " Время - " + totalTime/1000 + " Сумма - " + totalSum/100);
////              + "\n\n No spam to my email, please!");
//
//      try{
//        Transport.send(message);
//      } catch (com.google.gwt.user.server.rpc.UnexpectedException ex) {
//
//      }
//
//      System.out.println("Done");
//
//    } catch (MessagingException e) {
//      throw new RuntimeException(e);
//    }
//
//  }

}
