package de.hpfsc.web.anticafe;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import de.hpfsc.shared.Client;
import de.hpfsc.shared.WhoseSessionEnum;
import de.hpfsc.web.ClientsService;
import de.hpfsc.web.ClientsServiceAsync;

import java.math.BigDecimal;


/**
 * Created by dmitry on 11.07.15.
 */
public class ClientSessionPanel extends Composite {

  private long clientId;

  private Audio notificationEmailAudio;

  private long creationalTime;


  private WhoseSessionEnum whoseSession;
  private WhoseSessionEnum whoIsOwner;
  final Label totalTimeValue = new Label("00:00:00");
  private HorizontalPanel mainPanel;
  private Label clientNameLabel;
  private ListBox clientNameInput;
//  private TextBox clientNameInput;
  private ListBox whoseBox;
  private Label commentLabel;
  private TextArea clientCommentInput;
  private CheckBox remindAfterCheckBox;
  private TextBox remindAfterInput;
  private Button startSessionButton;
  private Button closeTheSessionButton;
  private Button stopSessionButton;
  private Label totalSumLabel;
  private Label totalSumValue;
  private Timer remindAfterTimer;
  private Timer remindTimeTimer;
  private Timer updateTotalsTimer;
  private Timer updateCurrentStateTimer;
  private long scheduledToRemindTime = 10000; // remind in 100 seconds
  private long startTime;

  private boolean isToShowAsAccepted;
  private boolean isSelfVisible;
  private long fromDateFilterValue;
  private long toDateFilterValue;

  private long finishTime;
  private long minTime = 60000; // minimum period 1 minutes
  private long minPayment = 3500;
  private long maxLength = 1000 * 60 * 4;

  private final ClientsServiceAsync clientsServiceAsync = GWT.create(ClientsService.class);
  private long currentTimeValue;
  private long totalSumCurrentValue;

  public long getTotalSumCurrentValue() {
    return totalSumCurrentValue;
  }

  private boolean isInProgress;
  private boolean isAccepted;

  public boolean isInProgress() {
    return isInProgress;
  }

  public void setInProgress(boolean isInProgress) {
    this.isInProgress = isInProgress;
  }

  public boolean isAccepted() {
    return isAccepted;
  }

  public void setAccepted(boolean isAccepted) {
    this.isAccepted = isAccepted;
  }

  public ClientSessionPanel(WhoseSessionEnum whoseSession, long id, String name, String comment, final long startTime, boolean inProgress) {
    this.whoseSession = whoseSession;
    this.whoIsOwner = whoseSession;
    this.creationalTime = System.currentTimeMillis();
    notificationEmailAudio = Audio.createIfSupported();
//    notificationEmailAudio.setSrc("sounds/email_notification.wav");
    this.isInProgress = inProgress;
    this.clientId = id;
    mainPanel = new HorizontalPanel();
    initWidget(mainPanel);
    mainPanel.setWidth("500px");
    setHeight("20px");
    mainPanel.addStyleName("session-panel");


    createWhoseListBox();
    mainPanel.add(whoseBox);

    createClientNameLabel();
    mainPanel.add(clientNameLabel);

    createClientNameListBox(name);

    setProperOwner();
//    clientNameInput.getElement().getStyle().setBackgroundColor("blue");
    mainPanel.add(clientNameInput);

    createCommentLabel();
    mainPanel.add(commentLabel);

    createCommentInput(comment);
    mainPanel.add(clientCommentInput);

    createRemindAfterCheckBox();
    Label remindLabel = createRemindLabel();
    mainPanel.add(remindLabel);
    mainPanel.add(remindAfterCheckBox);

//    final Label remindAfterLabel = new Label("через: ");
//    remindAfterLabel.addStyleName("name-label");
//    mainPanel.add(remindAfterLabel);
    createRemindAfterInput();
    mainPanel.add(remindAfterInput);
//    Label minutesLabel = new Label("мин.");
//    minutesLabel.addStyleName("name-label");
//    mainPanel.add(minutesLabel);

    final Label totalTimeLabel = new Label();
    totalTimeLabel.addStyleName("total-time-style");
    totalTimeLabel.addStyleName("time-style");
    mainPanel.add(totalTimeLabel);

    totalTimeValue.addStyleName("total-time-style");
    mainPanel.add(totalTimeValue);

    createStartSessionButton();
    createTotalSumSection();

    this.startTime = startTime;
    createUpdateCurrentStateTimer();
    updateCurrentStateTimer.scheduleRepeating(1000);
    createRefreshTotalSumTimer();
//    if (clientId != 0) {
//      currentTimeValue = System.currentTimeMillis() - this.startTime;
//      totalTimeValue.setText(getMinutesString(currentTimeValue));
//      long currentIntervalSeconds = getSeconds(currentTimeValue);
//      if (currentIntervalSeconds <= getSeconds(minTime)) {
////          totalSumCurrentValue = minPayment;
//        totalSumValue.setText(getPrettyMoney(minPayment));
//        totalSumCurrentValue = minPayment;
//      } else {
//
////            BigDecimal totalSum = BigDecimal.valueOf(totalSumCurrentValue + 50);
////            totalSumCurrentValue = totalSum.longValue();
//          totalSum = minPayment + 50 * (currentIntervalSeconds - minTime / 1000) / 60;
//          totalSumCurrentValue = totalSum;
//          totalSumValue.setText(getPrettyMoney(totalSum));
//      }
//
////      if (getSeconds(currentTimeValue) < maxLength/ 1000) {
//      if (isInProgress) {
//        updateTotalsTimer.scheduleRepeating(1000);
//      } else {
//        stopSessionOnServer();
//      }
//    }



    mainPanel.add(startSessionButton);
    createStopSessionButton();
    mainPanel.add(stopSessionButton);

    createCloseSessionButton();
    mainPanel.add(closeTheSessionButton);
    toggleStartStopButtonsAvailability();
  }

  public void createRefreshTotalSumTimer() {
    updateTotalsTimer = new Timer() {
      @Override
      public void run() {
        updateTotalSum();
      }
    };
  }

  public void createUpdateCurrentStateTimer() {
    updateCurrentStateTimer = new Timer() {
      @Override
      public void run() {
        updateCurrentState();
      }
    };
  }

  public void createCloseSessionButton() {
    closeTheSessionButton = new Button();
    closeTheSessionButton.addStyleName("remove-button");
    closeTheSessionButton.setTitle("В архив");
    closeTheSessionButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        Client acceptedClient = new Client();
        acceptedClient.setId(clientId);
        clientsServiceAsync.acceptSession(acceptedClient, new AsyncCallback<Void>() {
          public void onFailure(Throwable caught) {
            String s = "dfd";
          }

          public void onSuccess(Void result) {
            ClientSessionPanel.this.setAccepted(true);
            ClientSessionPanel.this.setVisible(resolveSelfVisibility());

            System.out.println("accepted");
          }
        });
//        ClientSessionPanel.this.removeFromParent();
      }
    });
  }

  public void createStopSessionButton() {
    stopSessionButton = new Button();
    stopSessionButton.addStyleName("stop-button");
    stopSessionButton.setEnabled(false);
    stopSessionButton.addStyleName("disabled-style");
    stopSessionButton.setTitle("Стоп");
    stopSessionButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        stopSession();
        clientsServiceAsync.stopSession(clientId, 0, new AsyncCallback<Void>() {
          public void onFailure(Throwable caught) {
            String s = "sdfsd";
          }

          public void onSuccess(Void result) {
            ClientSessionPanel.this.isInProgress = false;
            toggleStartStopButtonsAvailability();
          }
        });

      }
    });
  }

  public void createStartSessionButton() {
    startSessionButton = new Button();
    startSessionButton.addStyleName("start-button");
    startSessionButton.setTitle("Старт");
    startSessionButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {

          ClientSessionPanel.this.startTime = System.currentTimeMillis();
          clientsServiceAsync.startSession(clientId, ClientSessionPanel.this.startTime,  new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                      // Show the RPC error message to the user
                      String s = "dfd";
                    }

                    public void onSuccess(Void result) {
//                      clientId = result;
                      ClientSessionPanel.this.isInProgress = true;
//                      updateTotalsTimer.scheduleRepeating(1000);
//                      toggleStartStopButtonsAvailability();
                    }
                  });

        finishTime = ClientSessionPanel.this.startTime + Long.valueOf(remindAfterInput.getValue()) * 1000;

        remindTimeTimer = new Timer() {
          @Override
          public void run() {
            long timeLeft = finishTime - System.currentTimeMillis();
            if (timeLeft > 0) {
              remindAfterInput.setText(getMinutesString(timeLeft));
            } else {
              notificationEmailAudio.play();
              remindAfterInput.setText("ALERT");
              remindAfterCheckBox.setValue(false);
//              remindAfterInput.getElement().getStyle().setBackgroundColor("red");
              remindTimeTimer.cancel();
            }
          }
        };
        if (remindAfterCheckBox.getValue()) {
          remindTimeTimer.scheduleRepeating(1000);
        }

        updateTotalsTimer.scheduleRepeating(1000);
      }
    });
  }

  public void createRemindAfterInput() {
    remindAfterInput = new TextBox();
    remindAfterInput.addStyleName("remind-input");
    remindAfterInput.setValue(String.valueOf(scheduledToRemindTime / 1000));
    remindAfterInput.setWidth("50px");
    remindAfterInput.setEnabled(false);
  }

  public Label createRemindLabel() {
    Label remindLabel = new Label();
    remindLabel.addStyleName("remind-style");
    return remindLabel;
  }

  public void createRemindAfterCheckBox() {
    remindAfterCheckBox = new CheckBox();
    remindAfterCheckBox.addStyleName("custom-checkbox");
    remindAfterCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      public void onValueChange(ValueChangeEvent<Boolean> event) {
//        remindAfterInput.setEnabled(remindAfterCheckBox.getValue());
        if (!remindAfterCheckBox.getValue()) {
          remindAfterTimer.cancel();
        } else {
          if (updateTotalsTimer.isRunning()) {
            remindTimeTimer.schedule(Integer.valueOf(remindAfterInput.getValue()));
          }
        }
      }
    });
  }

  public void createCommentInput(String comment) {
    clientCommentInput = new TextArea();
    clientCommentInput.addStyleName("custom-input");
    clientCommentInput.setWidth("170px");
    clientCommentInput.setHeight("30px");
    clientCommentInput.setText(comment);
    clientCommentInput.addKeyUpHandler(new KeyUpHandler() {
      public void onKeyUp(KeyUpEvent event) {
        updateSession();
      }

    });
  }

  public void createCommentLabel() {
    commentLabel = new Label("Комментарий: ");
    commentLabel.addStyleName("name-label");
  }

  public void setProperOwner() {
    if (WhoseSessionEnum.ADMIN == whoIsOwner) {
      whoseBox.setSelectedIndex(2);
    } else if (WhoseSessionEnum.FIRST == whoIsOwner) {
      whoseBox.setSelectedIndex(0);
    } else if (WhoseSessionEnum.SECOND == whoIsOwner) {
      whoseBox.setSelectedIndex(1);
    }
  }

  public void createClientNameListBox(String name) {
    clientNameInput = new ListBox();
    clientNameInput.setWidth("150px");
    clientNameInput.addStyleName("custom-input");
    clientNameInput.addItem("Выберите имя");
    clientNameInput.addItem("Моцарт");
    clientNameInput.addItem("Бах");
    clientNameInput.addItem("Билык");
    clientNameInput.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        updateSession();
      }
    });
    setClientNameValue(name);
  }

  public void createClientNameLabel() {
    clientNameLabel = new Label("Имя: ");
    clientNameLabel.addStyleName("name-label");
  }

  public void createWhoseListBox() {
    whoseBox = new ListBox();
    whoseBox.setWidth("35px");
    whoseBox.addStyleName("custom-input");
    whoseBox.addItem(WhoseSessionEnum.FIRST.getShortName());
    whoseBox.addItem(WhoseSessionEnum.SECOND.getShortName());
    whoseBox.addItem(WhoseSessionEnum.ADMIN.getShortName());
    setProperOwner();

    whoseBox.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        if (whoseBox.getSelectedIndex() == 0) {
          whoIsOwner = WhoseSessionEnum.FIRST;
        } else if (whoseBox.getSelectedIndex() == 1) {
          whoIsOwner = WhoseSessionEnum.SECOND;
        } else if (whoseBox.getSelectedIndex() == 2) {
          whoIsOwner = WhoseSessionEnum.ADMIN;
        }
        clientsServiceAsync.updateSessionOwner(clientId, whoIsOwner, new AsyncCallback<Void>() {
                  public void onFailure(Throwable caught) {
                    String s = "dfsddf";
                  }

                  public void onSuccess(Void result) {
                    ClientSessionPanel.this.setProperOwner();
                    System.out.println("client is updated");
                  }
                });
      }
    });
  }

  public void updateSession() {
    clientsServiceAsync.updateSession(clientId, getClientNameValue(), clientCommentInput.getValue(), new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        String s = "fdsfsd";
      }

      public void onSuccess(Void result) {
        System.out.println("Session is updated");
      }
    });
  }

  public void setClientNameValue(String name) {
    for (int i = 0; i < clientNameInput.getItemCount(); i++) {
      if(clientNameInput.getItemText(i).equals(name)) {
        clientNameInput.setSelectedIndex(i);
      }
    }
  }

  public String getClientNameValue() {
    return clientNameInput.getValue(clientNameInput.getSelectedIndex());
  }

  public void setClientCommentValue(String commentValue) {
    clientCommentInput.setText(commentValue);
  }

  public void stopSessionOnServer() {
    clientsServiceAsync.stopSession(clientId, 0, new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        String s = "dfdsf";
      }

      public void onSuccess(Void result) {
//        totalTimeValue.setText("00:00:00");
        System.out.println("Session is stopped");
      }
    });
  }

  public void stopSession() {
    if (remindTimeTimer != null && remindTimeTimer.isRunning()) {
      remindTimeTimer.cancel();
    }
    updateTotalsTimer.cancel();
    toggleStartStopButtonsAvailability();
  }

  public void startSession() {
    if (remindTimeTimer != null && remindTimeTimer.isRunning()) {
      remindTimeTimer.cancel();
    }
    updateTotalsTimer.scheduleRepeating(1000);
    toggleStartStopButtonsAvailability();
  }

  public void updateTotalSum() {
    currentTimeValue = System.currentTimeMillis() - this.startTime;
    totalTimeValue.setText(getMinutesString(currentTimeValue));
    long currentIntervalSeconds = getSeconds(currentTimeValue);
    //TODO
//    if (currentIntervalSeconds > maxLength/1000) {
//      stopSession();
//      stopSessionOnServer();
//      return;
//    }
    if (currentIntervalSeconds <= getSeconds(minTime)) {
//          totalSumCurrentValue = minPayment;
      totalSumValue.setText(getPrettyMoney(minPayment));
      totalSumCurrentValue = minPayment;
    } else {
      if ((currentIntervalSeconds - minTime / 1000) % 60 == 0) {

//            BigDecimal totalSum = BigDecimal.valueOf(totalSumCurrentValue + 50);
//            totalSumCurrentValue = totalSum.longValue();
        long totalSum = minPayment + 50 * (currentIntervalSeconds - minTime / 1000) / 60;
        totalSumCurrentValue = totalSum;
        totalSumValue.setText(getPrettyMoney(totalSum));
      }
    }
  }


  private void updateCurrentState() {

    if (this.isInProgress && !updateTotalsTimer.isRunning()) {
      updateTotalsTimer.scheduleRepeating(1000);
    } else if (!this.isInProgress) {
      updateTotalsTimer.cancel();
    }
    toggleStartStopButtonsAvailability();

    resolveSelfVisibility();

  }

  private boolean resolveSelfVisibility() {
    return true;
//
//    boolean isVisibleByAccept = false;
//    if (!this.isAccepted || isToShowAsAccepted) {
//      isVisibleByAccept = true;
//    }
//
//    long creationalTimeDay = creationalTime / 1000 / 60 / 60 / 24;
//    boolean isVisibleByDate = creationalTimeDay >= fromDateFilterValue/ 1000 / 60 / 60 / 24 && creationalTimeDay <= toDateFilterValue/ 1000 / 60 / 60 / 24 + 1;
//
//    boolean isVisibleByOwner = this.whoseSession == WhoseSessionEnum.ADMIN || this.whoseSession == this.whoIsOwner;
//
//    isSelfVisible = isVisibleByAccept && isVisibleByDate && isVisibleByOwner;
//
//    ClientSessionPanel.this.setVisible(isSelfVisible);
//    return isSelfVisible;
  }


  private void createTotalSumSection() {
    totalSumLabel = new Label();
    totalSumLabel.addStyleName("total-sum");
    totalSumLabel.addStyleName("money-style");
    mainPanel.add(totalSumLabel);

    totalSumValue = new Label("00.00");
    totalSumValue.addStyleName("total-sum");
    mainPanel.add(totalSumValue);
  }

  private String getPrettyMoney(long minPayment) {
    return new BigDecimal(minPayment).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
  }

  private long getMinutes(long timeLeft) {
    return (timeLeft/(1000*60))%60;
  }

  private long getSeconds(long timeLeft) {
    return (timeLeft/(1000));
  }

  private String getMinutesString(long timeLeft) {
    long minutes = (timeLeft / (1000 * 60)) % 60;
    String minutesString = padTimeValue(minutes);
    long hours = (timeLeft / (1000 * 60 * 60)) % 60;
    String hoursString = padTimeValue(hours);
    long seconds = (timeLeft / 1000) % 60;
    String secondsString = padTimeValue(seconds);
    return hoursString + ":"+ minutesString + ":" + secondsString;
  }

  private String padTimeValue(long timeUnit) {
    return timeUnit<10? "0"+timeUnit: String.valueOf(timeUnit);
  }

  public void toggleStartStopButtonsAvailability() {
    boolean isCountInProgress = updateTotalsTimer.isRunning();
    stopSessionButton.setEnabled(isCountInProgress);
    startSessionButton.setEnabled(!isCountInProgress);
    if (isCountInProgress) {
      stopSessionButton.removeStyleName("disabled-style");
      startSessionButton.addStyleName("disabled-style");
    } else {
      stopSessionButton.addStyleName("disabled-style");
      startSessionButton.removeStyleName("disabled-style");
    }
  }


  public long getClientId() {
    return clientId;
  }

  public void setClientId(long clientId) {
    this.clientId = clientId;
  }

  public long getCreationalTime() {
    return creationalTime;
  }

  public void setCreationalTime(long creationalTime) {
    this.creationalTime = creationalTime;
  }


  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public boolean isToShowAsAccepted() {
    return isToShowAsAccepted;
  }

  public void setToShowAsAccepted(boolean isToShowAsAccepted) {
    this.isToShowAsAccepted = isToShowAsAccepted;
  }

  public long getFromDateFilterValue() {
    return fromDateFilterValue;
  }

  public void setFromDateFilterValue(long fromDateFilterValue) {
    this.fromDateFilterValue = fromDateFilterValue;
  }

  public long getToDateFilterValue() {
    return toDateFilterValue;
  }

  public void setToDateFilterValue(long toDateFilterValue) {
    this.toDateFilterValue = toDateFilterValue;
  }


  public WhoseSessionEnum getWhoseSession() {
    return whoseSession;
  }

  public void setWhoseSession(WhoseSessionEnum whoseSession) {
    this.whoseSession = whoseSession;
  }

  public boolean isSelfVisible() {
    return isSelfVisible;
  }

  public void setSelfVisible(boolean isSelfVisible) {
    this.isSelfVisible = isSelfVisible;
  }

  public WhoseSessionEnum getWhoIsOwner() {
    return whoIsOwner;
  }

  public void setWhoIsOwner(WhoseSessionEnum whoIsOwner) {
    this.whoIsOwner = whoIsOwner;
  }
}
