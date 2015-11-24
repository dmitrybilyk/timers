/*
 * Ext GWT 2.2.1 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.hpfsc.web.anticafe;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.grid.AggregationRenderer;
import com.extjs.gxt.ui.client.widget.grid.AggregationRowConfig;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.RowEditor;
import com.extjs.gxt.ui.client.widget.grid.SummaryType;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hpfsc.shared.Client;
import de.hpfsc.shared.ClientNamesEnum;
import de.hpfsc.web.ClientsService;
import de.hpfsc.web.ClientsServiceAsync;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WidgetRenderingExample extends LayoutContainer {

  private  Grid<Client> grid;
  private RowEditor<ModelData> rowEditor;
  private  SimpleComboBox<String> nameEditor;
  final ListStore<Client> store = new ListStore<Client>();
  private ClientsServiceAsync clientsServiceAsync = GWT.create(ClientsService.class);
  private boolean isToShowAccepted;
  private CheckBox showAcceptedCheckBox;
  private LabelField totalSumLabel = new LabelField("0.00");

  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    setHeight(300);
    final Timer updateClientsTimer = new Timer() {
      @Override
      public void run() {
        clientsServiceAsync.getClients(showAcceptedCheckBox.getValue(), new AsyncCallback<ArrayList<Client>>() {
          @Override
          public void onFailure(Throwable throwable) {
            System.out.println("fail get clients from timer");
          }

          @Override
          public void onSuccess(ArrayList<Client> clients) {

            if (clients.isEmpty()) {
              store.removeAll();
            }
            for (Client client : clients) {

              Client alreadyPresentModel = store.findModel(client);

              if (alreadyPresentModel == null && !client.isDeleted()) {
                store.add(client);
              } else {
                updateClientInStore(client, alreadyPresentModel);
              }

              long totalSumValue = 0l;
              for (Client storeClient: store.getModels()) {
                totalSumValue += storeClient.getTotalSum();
              }
              totalSumLabel.setValue(getPrettyMoney(totalSumValue));

//              if (alreadyPresentModel == null) {
//                if (showAcceptedCheckBox.getValue()) {
//                  store.add(client);
//                } else {
//                  if (!client.isAccepted()) {
//                    store.add(client);
//                  }
//                }
//              } else {
//                if (isToShowAccepted) {
//                  updateClientInStore(client, alreadyPresentModel);
//                } else {
//                  if (client.isAccepted()) {
//                    store.remove(client);
//                  } else {
//                    updateClientInStore(client, alreadyPresentModel);
//                  }
//                }
//              }
            }
          }

        });


      }
    };

//    setLayout(new FlowLayout(10));

    GridCellRenderer<Client> ownerCellRenderer = new GridCellRenderer<Client>() {

      public Object render(final Client model, String property, ColumnData config, final int rowIndex,
                           final int colIndex, ListStore<Client> store, Grid<Client> grid) {
        return new LabelField(model.getWhoseSession().name());
      }
    };


    GridCellRenderer<Client> nameCellRenderer = new GridCellRenderer<Client>() {

      public Object render(final Client model, String property, ColumnData config, final int rowIndex,
                           final int colIndex, ListStore<Client> store, Grid<Client> grid) {
        return new LabelField(model.getName());
      }
    };

    GridCellRenderer<Client> buttonRenderer = new GridCellRenderer<Client>() {

      private boolean init;

      public Object render(final Client model, String property, ColumnData config, final int rowIndex,
          final int colIndex, ListStore<Client> store, Grid<Client> grid) {
        if (!init) {
          init = true;
          grid.addListener(Events.ColumnResize, new Listener<GridEvent<Client>>() {

            public void handleEvent(GridEvent<Client> be) {
              for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
                if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
                    && be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent) {
                  ((BoxComponent) be.getGrid().getView().getWidget(i, be.getColIndex())).setWidth(be.getWidth() - 10);
                }
              }
            }
          });
        }

        Button b = new Button((String) model.getName(), new SelectionListener<ButtonEvent>() {
          @Override
          public void componentSelected(ButtonEvent ce) {
            Info.display(model.getName(), "<ul><li>" + model.getId() + "</li></ul>");
          }
        });
        b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
        b.setToolTip("Click for more information");

        return b;
      }
    };


    GridCellRenderer<Client> playButtonRenderer = new GridCellRenderer<Client>() {

      public Object render(final Client model, String property, ColumnData config, final int rowIndex,
                           final int colIndex, final ListStore<Client> store, final Grid<Client> grid) {
        final Button b = new Button("Старт");
        b.addSelectionListener(new SelectionListener<ButtonEvent>() {
          @Override
          public void componentSelected(ButtonEvent ce) {
//            final Client selectedItem = grid.getSelectionModel().getSelectedItem();
            b.setEnabled(false);
            final Client selectedItem = store.getAt(rowIndex);
            clientsServiceAsync.startSession(selectedItem.getId(), System.currentTimeMillis(), new AsyncCallback<Void>() {
              @Override
              public void onFailure(Throwable throwable) {
                System.out.println("faile start");
              }

              @Override
              public void onSuccess(Void aVoid) {
                Info.display("Started alert", selectedItem.getName() + " is started");
              }
            });
            Info.display("", selectedItem.getName());
          }
        });
        b.setEnabled(model.getStartTime() == 0);
        b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
        b.setToolTip("Click for more information");

        return b;
      }
    };

    GridCellRenderer<Client> stopButtonRenderer = new GridCellRenderer<Client>() {

      public Object render(final Client model, String property, ColumnData config, final int rowIndex,
                           final int colIndex, ListStore<Client> store, Grid<Client> grid) {
        final Client selectedItem = store.getAt(rowIndex);
        Button b = new Button("Stop", new SelectionListener<ButtonEvent>() {
          @Override
          public void componentSelected(ButtonEvent ce) {
            clientsServiceAsync.stopSession(model.getId(), model.getTotalSum(), new AsyncCallback<Void>() {
              @Override
              public void onFailure(Throwable throwable) {
                System.out.println("fail stop");
              }

              @Override
              public void onSuccess(Void aVoid) {
                Info.display("Stop alert", selectedItem.getName() + " is stopped");
              }
            });
          }
        });
        b.setEnabled(model.isInProgress());
        b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
        b.setToolTip("Click for more information");

        return b;
      }
    };

    GridCellRenderer<Client> timeRenderer = new GridCellRenderer<Client>() {

      public Object render(final Client model, String property, ColumnData config, final int rowIndex,
                           final int colIndex, ListStore<Client> store, Grid<Client> grid) {
        LabelField timeLabel = new LabelField();
        Long stopTime = model.getStopTime();

        Long endTime = null;

        if (!model.isInProgress() && stopTime != 0) {
          endTime = stopTime;
          timeLabel.setValue(getMinutesString(endTime - model.getStartTime()));
        } else if (model.isInProgress()) {
          endTime = System.currentTimeMillis();
          timeLabel.setValue(getMinutesString(endTime - model.getStartTime()));
        } else {
          timeLabel.setValue("00:00:00");
        }


        return timeLabel;
      }
    };

    GridCellRenderer<Client> sumRenderer = new GridCellRenderer<Client>() {

      public Object render(final Client model, String property, ColumnData config, final int rowIndex,
                           final int colIndex, ListStore<Client> store, Grid<Client> grid) {
        LabelField sumLabel = new LabelField("00.00");

        if (model.getStartTime() != 0) {
          renderSumLabel(model, sumLabel);
        }
        return sumLabel;
      }
    };


    GridCellRenderer<Client> acceptColumnRenderer = new GridCellRenderer<Client>() {

      public Object render(final Client model, String property, ColumnData config, final int rowIndex,
                           final int colIndex, final ListStore<Client> store, final Grid<Client> grid) {
        Button b = new Button("В архив", new SelectionListener<ButtonEvent>() {
          @Override
          public void componentSelected(ButtonEvent ce) {
            model.setAccepted(true);
            clientsServiceAsync.acceptSession(model, new AsyncCallback<Void>() {
              @Override
              public void onFailure(Throwable throwable) {
                System.out.println("fail update from accept");
              }

              @Override
              public void onSuccess(Void aVoid) {
                Info.display("", "Model is accepted");
              }
            });
          }
        });
        if (model.isAccepted()) {
          setClientAcceptedButton(b);
        }
        b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
        b.setToolTip("Click to add to archive");
        return b;
      }

      private void setClientAcceptedButton(Button b) {
        b.setHtml("В архиве");
        b.getElement().getStyle().setBackgroundColor("green");
        b.setEnabled(false);
      }
    };


    GridCellRenderer<Client> editButtonRenderer = new GridCellRenderer<Client>() {

      public Object render(final Client model, String property, ColumnData config, final int rowIndex,
                           final int colIndex, final ListStore<Client> store, Grid<Client> grid) {
        final Client selectedItem = store.getAt(rowIndex);
        Button b = new Button("Редактировать", new SelectionListener<ButtonEvent>() {
          @Override
          public void componentSelected(ButtonEvent ce) {
            DialogExample dialogExample = new DialogExample(model, store.getModels());
            dialogExample.show();
          }
        });
        b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
        b.setToolTip("Click for more information");

        return b;
      }
    };

    GridCellRenderer<Client> removeButtonRenderer = new GridCellRenderer<Client>() {

      public Object render(final Client model, String property, ColumnData config, final int rowIndex,
                           final int colIndex, final ListStore<Client> store, final Grid<Client> grid) {
        final Button b = new Button("Удалить", new SelectionListener<ButtonEvent>() {
          @Override
          public void componentSelected(ButtonEvent ce) {
//            final Client selectedItem = grid.getSelectionModel().getSelectedItem();
            final Client selectedItem = store.getAt(rowIndex);
            clientsServiceAsync.markSessionAsDeleted(selectedItem.getId(), new AsyncCallback<Void>() {
              @Override
              public void onFailure(Throwable throwable) {
                System.out.println("fail deletion start");
              }

              @Override
              public void onSuccess(Void aVoid) {
                Info.display("Session ", selectedItem.getName() + " is deleted");
              }
            });
//            Info.display("", selectedItem.getName());
          }
        });
        if (model.isDeleted()) {
          b.setEnabled(false);
        }
        toggleInprogressButtonStyle(model, b);
        b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
        b.setToolTip("Click for more information");

        return b;
      }
    };




    final NumberFormat currency = NumberFormat.getCurrencyFormat();
    final NumberFormat number = NumberFormat.getFormat("0.00");
//    final NumberCellRenderer<Grid<Session>> numberRenderer = new NumberCellRenderer<Grid<Session>>(currency);

//    GridCellRenderer<Client> change = new GridCellRenderer<Client>() {
//      public String render(Client model, String property, ColumnData config, int rowIndex, int colIndex,
//          ListStore<Client> store, Grid<Client> grid) {
//        double val = (Double) model.get(property);
//        String style = val < 0 ? "red" : "green";
//        return "<span style='color:" + style + "'>" + number.format(val) + "</span>";
//      }
//    };

//    GridCellRenderer<Session> gridNumber = new GridCellRenderer<Session>() {
//      public String render(Session model, String property, ColumnData config, int rowIndex, int colIndex,
//          ListStore<Session> stor, Grid<Session> grid) {
//        return numberRenderer.render(null, property, model.get(property));
//      }
//    };

    List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

    ColumnConfig column = new ColumnConfig();
    column.setId("owner");
    column.setHeaderHtml("Кому принадлежит");
    column.setRenderer(ownerCellRenderer);
    column.setWidth(90);
    configs.add(column);

    column = new ColumnConfig();
    column.setId("name");
    column.setHeaderHtml("Псевдоним");
    column.setRenderer(nameCellRenderer);
    nameEditor = new SimpleComboBox<>();
    column.setEditor(new CellEditor(nameEditor));
    column.setWidth(100);
    configs.add(column);

//    column = new ColumnConfig();
//    column.setId("name");
//    column.setHeaderHtml("Имя");
//    column.setWidth(100);
//    column.setRenderer(buttonRenderer);
//    configs.add(column);


    column = new ColumnConfig();
    column.setId("play");
    column.setResizable(false);
    column.setHeaderHtml("Начало");
    column.setWidth(50);
    column.setRenderer(playButtonRenderer);
    configs.add(column);

    column = new ColumnConfig();
    column.setId("stop");
    column.setResizable(false);
    column.setHeaderHtml("Стоп");
    column.setWidth(50);
    column.setRenderer(stopButtonRenderer);
    configs.add(column);

    column = new ColumnConfig();
    column.setId("time");
    column.setResizable(false);
    column.setHeaderHtml("Время");
    column.setWidth(60);
    column.setRenderer(timeRenderer);
    configs.add(column);

    column = new ColumnConfig();
    column.setId("sum");
    column.setResizable(false);
    column.setHeaderHtml("Деньги");
    column.setWidth(60);
    column.setRenderer(sumRenderer);
    configs.add(column);

//    column = new ColumnConfig();
//    column.setId("accept");
//    column.setResizable(false);
//    column.setHeaderHtml("В архив");
//    column.setWidth(50);
//    column.setRenderer(acceptButtonRenderer);
//    configs.add(column);

    column = new ColumnConfig();
    column.setId("edit");
    column.setResizable(false);
    column.setHeaderHtml("Редактировать");
    column.setWidth(100);
    column.setRenderer(editButtonRenderer);
    configs.add(column);

    column = new ColumnConfig();
    column.setId("accept");
    column.setResizable(false);
    column.setHeaderHtml("Архив");
    column.setWidth(80);
    column.setRenderer(acceptColumnRenderer);
    configs.add(column);

//    column = new ColumnConfig();
//    column.setId("Remove");
//    column.setResizable(false);
//    column.setHeaderHtml("Удалить");
//    column.setWidth(70);
//    column.setRenderer(removeButtonRenderer);
//    configs.add(column);

    clientsServiceAsync.getClients(isToShowAccepted, new AsyncCallback<ArrayList<Client>>() {
      @Override
      public void onFailure(Throwable throwable) {
        System.out.println("faile getting clients");
      }

      @Override
      public void onSuccess(ArrayList<Client> clients) {
        store.add(clients);
        updateClientsTimer.scheduleRepeating(1000);
      }
    });

//    store.add(TestData.getSessions());

    ColumnModel cm = new ColumnModel(configs);

    ContentPanel cp = new ContentPanel();
    cp.setBodyBorder(false);
//    cp.setIcon(Resources.ICONS.table());
//    cp.setHeadingHtml("Widget Renderer Grid");
    cp.setHeaderVisible(false);
    cp.setButtonAlign(HorizontalAlignment.CENTER);
    cp.setLayout(new FitLayout());
    cp.setSize(1100, 300);



    LayoutContainer totalSumContainer = new LayoutContainer();
    totalSumContainer.getElement().getStyle().setTextAlign(Style.TextAlign.CENTER);
    HBoxLayoutData layoutData = new HBoxLayoutData();
    layoutData.setMargins(new Margins(2));
    totalSumContainer.setLayoutData(layoutData);
//    totalSumContainer.setSize(60, 30);
//    totalSumLabel = new LabelField();
    LabelField sumLabel = new LabelField("Итого: ");
//    totalSumContainer.add(sumLabel, layoutData);
    totalSumLabel.getElement().getStyle().setFontSize(20, Style.Unit.PX);
    totalSumLabel.getElement().getStyle().setColor("darkblue");
    totalSumContainer.add(totalSumLabel, layoutData);
    ToolBar toolBar = new ToolBar();
    toolBar.getAriaSupport().setLabel("Grid Options");

    toolBar.add(new LabelToolItem("Показывать архивные: "));
    showAcceptedCheckBox = new CheckBox();
    showAcceptedCheckBox.setFieldLabel("Показывать принятые");
    showAcceptedCheckBox.addListener(Events.Select, new Listener<BaseEvent>() {
      @Override
      public void handleEvent(BaseEvent be) {
        isToShowAccepted = showAcceptedCheckBox.getValue();
      }
    });

    toolBar.add(showAcceptedCheckBox);
    totalSumContainer.add(toolBar);
    cp.setTopComponent(totalSumContainer);


    grid = new Grid<Client>(store, cm);
//    grid.setWidth("100%");
    grid.setStyleAttribute("borderTop", "none");
    grid.setAutoExpandColumn("name");
    rowEditor = new RowEditor<>();
    rowEditor.addListener(Events.BeforeEdit, new Listener<BaseEvent>() {
      @Override
      public void handleEvent(BaseEvent be) {
        nameEditor.removeAll();
        for (ClientNamesEnum clientName: ClientNamesEnum.values()) {
          if (!isStoreContainsName(clientName.name())) {
            nameEditor.add(clientName.name());
          }
        }
      }
    });
    grid.addPlugin(rowEditor);
    grid.setBorders(true);
    cp.add(grid);

    add(cp);
  }

  private boolean isStoreContainsName(String name) {
    for (Client client: store.getModels()) {
      if (client.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  private void updateClientInStore(Client client, Client alreadyPresentModel) {
    if (client.isDeleted()) {
      clientsServiceAsync.removeSession(client.getId(), new AsyncCallback<Void>() {
        @Override
        public void onFailure(Throwable throwable) {
          System.out.println("faile remove");
        }

        @Override
        public void onSuccess(Void aVoid) {
          System.out.println("session is removed");
        }
      });
      store.remove(client);
    } else {
      alreadyPresentModel.setName(client.getName());
      alreadyPresentModel.setComment(client.getComment());
      alreadyPresentModel.setTotalSum(client.getTotalSum());
      alreadyPresentModel.setWhoseSession(client.getWhoseSession());
      alreadyPresentModel.setAccepted(client.isAccepted());
      alreadyPresentModel.setCreationalTime(client.getCreationalTime());
      alreadyPresentModel.setInProgress(client.isInProgress());
      alreadyPresentModel.setStartTime(client.getStartTime());
      alreadyPresentModel.setStopTime(client.getStopTime());
      alreadyPresentModel.setLimitTime(client.getLimitTime());
      store.update(alreadyPresentModel);
    }
  }

  private void renderSumLabel(Client model, LabelField sumLabel) {
    Long minTime = 60000l; // minimum period 1 minutes
    Long minPayment = 3500l;
    Long stopTime = model.getStopTime();
    Long endTime = null;
    if (!model.isInProgress() && stopTime != 0) {
      endTime = stopTime;
    } else if (model.isInProgress()) {
      endTime = System.currentTimeMillis();
    }
    Long currentTimeValue = endTime - model.getStartTime();
//        totalTimeValue.setText(getMinutesString(currentTimeValue));
    long currentIntervalSeconds = getSeconds(currentTimeValue);
    //TODO
//    if (currentIntervalSeconds > maxLength/1000) {
//      stopSession();
//      stopSessionOnServer();
//      return;
//    }

    long totalSum = 0l;
    if (currentIntervalSeconds <= getSeconds(minTime)) {
//          totalSumCurrentValue = minPayment;

      totalSum = minPayment;
      sumLabel.setValue(getPrettyMoney(minPayment));
//          totalSumCurrentValue = minPayment;
    } else {
//          if ((currentIntervalSeconds - minTime / 1000) % 60 == 0) {

//            BigDecimal totalSum = BigDecimal.valueOf(totalSumCurrentValue + 50);
//            totalSumCurrentValue = totalSum.longValue();
        totalSum = minPayment + 50 * (currentIntervalSeconds - minTime / 1000) / 60;
//            totalSumCurrentValue = totalSum;
        sumLabel.setValue(getPrettyMoney(totalSum));
//          }
    }
    model.setTotalSum(totalSum);
    clientsServiceAsync.updateClientSum(model, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable throwable) {
        System.out.println("fail update client");
      }

      @Override
      public void onSuccess(Void aVoid) {
        System.out.println("client is updated");
      }
    });
  }

  private void toggleInprogressButtonStyle(Client model, Button b) {
    if (model.isInProgress()) {
      b.getElement().getStyle().setBackgroundColor("lightgreen");
    } else {
      b.getElement().getStyle().setBackgroundColor("black");
    }
//    store.update(model);
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
    return timeUnit<10 ? "0"+timeUnit : String.valueOf(timeUnit);
  }

}
