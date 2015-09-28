//package de.hpfsc.web.anticafe;
//
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.event.logical.shared.ValueChangeEvent;
//import com.google.gwt.event.logical.shared.ValueChangeHandler;
//import com.google.gwt.user.client.Timer;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.CheckBox;
//import com.google.gwt.user.client.ui.Composite;
//import com.google.gwt.user.client.ui.HorizontalPanel;
//import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.TextBox;
//import com.google.gwt.user.client.ui.VerticalPanel;
//import com.google.gwt.user.client.ui.Widget;
//import com.google.gwt.user.datepicker.client.DatePicker;
//import de.hpfsc.shared.WhoseSessionEnum;
//import de.hpfsc.web.ClientsService;
//import de.hpfsc.web.ClientsServiceAsync;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * Created by dmitry on 11.07.15.
// */
//public class MainPanel extends Composite {
//  private VerticalPanel verticalLoginPanel;
//  private Label loginMessageLabel;
//  private TextBox userNameInput;
//  private TextBox passwordInput;
//  private Button loginButton;
//  private Button logoutButton;
//  final VerticalPanel sessionsPanel;
//  private CheckBox isShowAcceptedCheckbox = new CheckBox("Показывать архивные");
//  private Label totalSumLabel = new Label();
//  private HorizontalPanel datesPanel = new HorizontalPanel();
//  private DatePicker fromDateWidget = new DatePicker();
//  private DatePicker toDateWidget = new DatePicker();
//
//  private WhoseSessionEnum whoseSession = WhoseSessionEnum.NOBODY;
//
//  private Button addClientSessionButton;
//  private VerticalPanel mainPanel;
//  private HorizontalPanel mostMainPanel;
//  private List<ClientSessionPanel> clientSessionPanelList;
//  private final ClientsServiceAsync clientsServiceAsync = GWT.create(ClientsService.class);
//
//  public MainPanel() {
////    addFirstClientToCheck();
//
//
//    HorizontalPanel addInfoPanel = new HorizontalPanel();
//    Label fromDatesLabel = new Label("От");
//    datesPanel.add(fromDatesLabel);
//    datesPanel.add(fromDateWidget);
//    fromDateWidget.setValue(new Date());
//    fromDateWidget.addValueChangeHandler(new ValueChangeHandler<Date>() {
//      public void onValueChange(ValueChangeEvent<Date> event) {
//        setFromDateRangeValue();
//      }
//    });
//
//
//    final Label toLDatesLabel = new Label("До");
//    datesPanel.add(toLDatesLabel);
//    toDateWidget.addValueChangeHandler(new ValueChangeHandler<Date>() {
//      public void onValueChange(ValueChangeEvent<Date> event) {
//        setToDateRangeValue();
//      }
//    });
//
//
//    datesPanel.add(toDateWidget);
//    toDateWidget.setValue(new Date());
//
//    datesPanel.setVisible(false);
//    addInfoPanel.addStyleName("top-panel");
//    addInfoPanel.setWidth("100%");
//
//    sessionsPanel = new VerticalPanel();
//    sessionsPanel.setVisible(false);
////    getExistingSessions();
//
//    addClientSessionButton = new Button("Добавить сессию");
//    addClientSessionButton.addStyleName("add-session-button");
//    addClientSessionButton.addClickHandler(new ClickHandler() {
//      public void onClick(ClickEvent event) {
//        addSessionToUser();
//      }
//    });
//
//    Timer updateCurrentStateTimer = new Timer() {
//      @Override
//      public void run() {
////        sessionsPanel.clear();
//        clientsServiceAsync.getClients(new AsyncCallback<ArrayList<Client>>() {
//          public void onFailure(Throwable caught) {
//            String s = "fdsf";
//          }
//
//          public void onSuccess(ArrayList<Client> result) {
//            for (Client client: result) {
//              ClientSessionPanel clientSessionPanel = getClientSessionByClientId(client.getId());
//
//              if (clientSessionPanel == null) {
//                clientSessionPanel = new ClientSessionPanel(client.getWhoseSession(), client.getId(), client.getName(), client.getComment(),
//                        client.getStartTime(), client.isInProgress());
//                clientSessionPanel.setVisible(!client.isAccepted());
//                clientSessionPanel.setStartTime(client.getStartTime());
//                clientSessionPanel.setCreationalTime(client.getCreationalTime());
//                sessionsPanel.add(clientSessionPanel);
//              } else {
//                clientSessionPanel.setStartTime(client.getStartTime());
//                clientSessionPanel.setClientCommentValue(client.getComment());
//                clientSessionPanel.setClientNameValue(client.getName());
//              }
//              clientSessionPanel.setAccepted(client.isAccepted());
//              clientSessionPanel.setInProgress(client.isInProgress());
//              clientSessionPanel.setWhoIsOwner(client.getWhoseSession());
//              clientSessionPanel.setWhoseSession(whoseSession);
//              clientSessionPanel.setProperOwner();
////              clientSessionPanel.updateTotalSum();
//              clientSessionPanel.setFromDateFilterValue(fromDateWidget.getValue().getTime());
//              clientSessionPanel.setToDateFilterValue(toDateWidget.getValue().getTime());
//              clientSessionPanel.setVisible(clientSessionPanel.isSelfVisible());
//              clientSessionPanel.setToShowAsAccepted(isShowAcceptedCheckbox.getValue());
//            }
//          }
//        });
//      }
//    };
//    updateCurrentStateTimer.scheduleRepeating(2000);
//
//    mainPanel = new VerticalPanel();
//    mainPanel.addStyleName("default-color");
//    mostMainPanel = new HorizontalPanel();
//    mostMainPanel.addStyleName("main-panel");
//    initWidget(mostMainPanel);
//
//    verticalLoginPanel = new VerticalPanel();
//    verticalLoginPanel.addStyleName("vertical-login-panel");
//    mostMainPanel.add(verticalLoginPanel);
//    mostMainPanel.add(mainPanel);
//
//    mainPanel.add(sessionsPanel);
//
//    sessionsPanel.setVisible(false);
//    addClientSessionButton.setVisible(false);
//
//    addInfoPanel.add(addClientSessionButton);
//
//    totalSumLabel.setVisible(false);
//    totalSumLabel.addStyleName("total-sum-style");
//    addInfoPanel.add(totalSumLabel);
//
//    final Timer updateTotalSumTimer = createTotalSumTimer(sessionsPanel);
//
////    mainPanel.add(verticalLoginPanel);
//
//
//    mainPanel.setWidth("100%");
//
//    mainPanel.getElement().setId("mainPanel");
//    mainPanel.addStyleName("main-panel-style");
//    mainPanel.add(addInfoPanel);
//
//    createLogoutButton(addInfoPanel, updateTotalSumTimer);
//
//    clientSessionPanelList = new ArrayList<ClientSessionPanel>();
//
//
//    loginMessageLabel = new Label("Введите имя и пароль:");
//    loginMessageLabel.addStyleName("login-message");
//    verticalLoginPanel.add(loginMessageLabel);
//
//    userNameInput = new TextBox();
//    userNameInput.addStyleName("login-input");
//    verticalLoginPanel.add(userNameInput);
//
//    passwordInput = new TextBox();
//    passwordInput.addStyleName("login-input");
//    verticalLoginPanel.add(passwordInput);
//
//    loginButton = new Button("Войти");
//    loginButton.addStyleName("login-button-style");
//    verticalLoginPanel.add(loginButton);
//
//    loginButton.addClickHandler(new ClickHandler() {
//      public void onClick(ClickEvent event) {
//        updateTotalSumTimer.scheduleRepeating(1000);
//        Timer delaySumTimer = new Timer() {
//          @Override
//          public void run() {
//            totalSumLabel.setVisible(true);
//          }
//        };
//        delaySumTimer.schedule(2000);
//        sessionsPanel.setVisible(true);
//        logoutButton.setVisible(true);
//        addClientSessionButton.setVisible(true);
//        verticalLoginPanel.setVisible(false);
//        mainPanel.setVisible(true);
//        datesPanel.setVisible(true);
//        isShowAcceptedCheckbox.setVisible(true);
//
//        logoutButton.setText("Выход пользователя " + " " + userNameInput.getValue());
//
//        if (userNameInput.getValue().equals("1")) {
//          MainPanel.this.removeStyleName("second-admin-style");
//          MainPanel.this.removeStyleName("super-admin-style");
//          mainPanel.addStyleName("first-admin-style");
//          whoseSession = WhoseSessionEnum.FIRST;
//        } else if (userNameInput.getValue().equals("2")) {
//          MainPanel.this.removeStyleName("first-admin-style");
//          MainPanel.this.removeStyleName("super-admin-style");
//          mainPanel.addStyleName("second-admin-style");
//          whoseSession = WhoseSessionEnum.SECOND;
//        } else if (userNameInput.getValue().equals("admin")) {
//          MainPanel.this.removeStyleName("first-admin-style");
//          MainPanel.this.removeStyleName("second-admin-style");
//          mainPanel.addStyleName("super-admin-style");
//          whoseSession = WhoseSessionEnum.ADMIN;
//        } else {
//          MainPanel.this.removeStyleName("first-admin-style");
//          MainPanel.this.removeStyleName("second-admin-style");
//          MainPanel.this.removeStyleName("super-admin-style");
//          whoseSession = WhoseSessionEnum.NOBODY;
//        }
//
//        Iterator<Widget> vPanelWidgets = sessionsPanel.iterator();
//        while (vPanelWidgets.hasNext()) {
//          Widget childWidget = vPanelWidgets.next();
//          if (childWidget instanceof ClientSessionPanel) {
//            resolveOwnerForSession(childWidget);
//          }
//        }
////        addFirstClientToCheck();
//      }
//    });
//
//    logoutButton.setVisible(false);
//    sessionsPanel.setVisible(false);
//    datesPanel.setWidth("100%");
//    mainPanel.add(datesPanel);
//
//    isShowAcceptedCheckbox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
//      public void onValueChange(ValueChangeEvent<Boolean> event) {
//        Iterator<Widget> vPanelWidgets = sessionsPanel.iterator();
//        while (vPanelWidgets.hasNext()) {
//          Widget childWidget = vPanelWidgets.next();
//          if (childWidget instanceof ClientSessionPanel){
//            ((ClientSessionPanel) childWidget).setToShowAsAccepted(isShowAcceptedCheckbox.getValue());
//          }
//        }
//      }
//    });
//    isShowAcceptedCheckbox.addStyleName("is-show-as-accepted-style");
//    isShowAcceptedCheckbox.setVisible(false);
//    HorizontalPanel bottomPanel = new HorizontalPanel();
//    bottomPanel.addStyleName("bottom-panel");
//    bottomPanel.add(isShowAcceptedCheckbox);
//    bottomPanel.add(logoutButton);
//    mainPanel.add(bottomPanel);
//
//  }
//
//  public void createLogoutButton(HorizontalPanel addInfoPanel, final Timer updateTotalSumTimer) {
//    logoutButton = new Button("Выход");
//    logoutButton.addStyleName("logout-button-style");
////    addInfoPanel.add(logoutButton);
//    logoutButton.addClickHandler(new ClickHandler() {
//      public void onClick(ClickEvent event) {
////       MainPanel.this.setVisible(false);
//        verticalLoginPanel.setVisible(true);
//        addClientSessionButton.setVisible(false);
//        sessionsPanel.setVisible(false);
//        logoutButton.setVisible(false);
//        totalSumLabel.setVisible(false);
//        userNameInput.setValue("");
////        updateTotalSumTimer.cancel();
//        datesPanel.setVisible(false);
//        isShowAcceptedCheckbox.setVisible(false);
//        mainPanel.setVisible(false);
//      }
//    });
//  }
//
//  public void setFromDateRangeValue() {
//    Iterator<Widget> vPanelWidgets = sessionsPanel.iterator();
//    while (vPanelWidgets.hasNext()) {
//      Widget childWidget = vPanelWidgets.next();
//      if (childWidget instanceof ClientSessionPanel){
//        ((ClientSessionPanel) childWidget).setFromDateFilterValue(fromDateWidget.getValue().getTime());
//      }
//    }
//  }
//
//  public void setToDateRangeValue() {
//    Iterator<Widget> vPanelWidgets = sessionsPanel.iterator();
//    while (vPanelWidgets.hasNext()) {
//      Widget childWidget = vPanelWidgets.next();
//      if (childWidget instanceof ClientSessionPanel){
//        ((ClientSessionPanel) childWidget).setToDateFilterValue(toDateWidget.getValue().getTime());
//      }
//    }
//  }
//
//  public void resolveOwnerForSession(Widget childWidget) {
//    if (whoseSession == WhoseSessionEnum.FIRST) {
//      childWidget.removeStyleName("second-admin-style");
//      childWidget.removeStyleName("super-admin-style");
//      childWidget.addStyleName("first-admin-style");
//    } else if (whoseSession == WhoseSessionEnum.SECOND) {
//      childWidget.removeStyleName("first-admin-style");
//      childWidget.removeStyleName("super-admin-style");
//      childWidget.addStyleName("second-admin-style");
//    } else if (whoseSession == WhoseSessionEnum.ADMIN) {
//      childWidget.removeStyleName("second-admin-style");
//      childWidget.removeStyleName("first-admin-style");
//      childWidget.addStyleName("super-admin-style");
//    } else {
//      childWidget.removeStyleName("second-admin-style");
//      childWidget.removeStyleName("super-admin-style");
//      childWidget.removeStyleName("first-admin-style");
//    }
//    ((ClientSessionPanel)childWidget).setWhoseSession(whoseSession);
//  }
//
//  private ClientSessionPanel getClientSessionByClientId(long id) {
//    Iterator<Widget> vPanelWidgets = sessionsPanel.iterator();
//    while (vPanelWidgets.hasNext()){
//
//      Widget childWidget = vPanelWidgets.next();
//      if (childWidget instanceof ClientSessionPanel) {
//        if (((ClientSessionPanel) childWidget).getClientId() == id) {
//          return (ClientSessionPanel)childWidget;
//        }
//      }
//    }
//    return null;
//  }
//
//   private void addSessionToUser(Client client) {
//      sessionsPanel.add(new ClientSessionPanel(whoseSession, client.getId(), client.getName(), client.getComment(), client.getStartTime(), client.isInProgress()));
//  }
//
//  private void addSessionToUser() {
//    final ClientSessionPanel clientSessionPanel;
//    clientSessionPanel = new ClientSessionPanel(whoseSession, 0, "", "", 0, false);
//    clientSessionPanel.setFromDateFilterValue(fromDateWidget.getValue().getTime());
//    clientSessionPanel.setToDateFilterValue(toDateWidget.getValue().getTime());
//    sessionsPanel.add(clientSessionPanel);
//
//    clientsServiceAsync.addClient(whoseSession, 0, "", "",
//            System.currentTimeMillis(), 0, new AsyncCallback<Long>() {
//              public void onFailure(Throwable caught) {
//                // Show the RPC error message to the user
//                String s = "dfd";
//              }
//
//              public void onSuccess(Long result) {
//                clientSessionPanel.setClientId(result);
//              }
//            });
//  }
//
//  private Timer createTotalSumTimer(final VerticalPanel sessionsPanel) {
//    final Timer updateTotalSumTimer = new Timer() {
//      @Override
//      public void run() {
//        long totalSum = 0;
//        Iterator<Widget> vPanelWidgets = sessionsPanel.iterator();
//        while (vPanelWidgets.hasNext()){
//
//          Widget childWidget = vPanelWidgets.next();
//          if (((ClientSessionPanel) childWidget).isSelfVisible()) {
//            totalSum += ((ClientSessionPanel) childWidget).getTotalSumCurrentValue();
//          }
//        }
//        totalSumLabel.setText(getPrettyMoney(totalSum));
//      }
//    };
//    updateTotalSumTimer.scheduleRepeating(1000);
//    return updateTotalSumTimer;
//  }
//
//  private void addFirstClientToCheck() {
//    clientsServiceAsync.addClient(WhoseSessionEnum.FIRST, 0, "Бах", "test comment", System.currentTimeMillis(), 1000, new AsyncCallback<Long>() {
//      public void onFailure(Throwable caught) {
//        String s = "dfd";
//      }
//
//      public void onSuccess(Long result) {
////        getExistingSessions();
//      }
//    });
//  }
//
////  private void getExistingSessions() {
////    clientsServiceAsync.getClients(new AsyncCallback<ArrayList<toknow.shared.Client>>() {
////      public void onFailure(Throwable caught) {
////        String s = "dfd";
////      }
////
////      public void onSuccess(ArrayList<Client> result) {
////        for (Client client: result) {
////          addSessionToUser(client);
////        }
////      }
////    });
////  }
//
//  private String getPrettyMoney(long minPayment) {
//    return new BigDecimal(minPayment).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
//  }
//
//}
