/*
 * Ext GWT 2.2.1 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.hpfsc.web.anticafe;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hpfsc.shared.Client;
import de.hpfsc.shared.Session;
import de.hpfsc.web.ClientsService;
import de.hpfsc.web.ClientsServiceAsync;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WidgetRenderingExample extends LayoutContainer {

  private  Grid<Client> grid;
  final ListStore<Client> store = new ListStore<Client>();;
  private ClientsServiceAsync clientsServiceAsync = GWT.create(ClientsService.class);

  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    setHeight(300);
    Timer updateClientsTimer = new Timer() {
      @Override
      public void run() {
        clientsServiceAsync.getClients(new AsyncCallback<ArrayList<Client>>() {
          @Override
          public void onFailure(Throwable throwable) {
            System.out.println("fail get clients from timer");
          }

          @Override
          public void onSuccess(ArrayList<Client> clients) {


//            for (Client client: clients) {
//              for (Client gridClient: store.getModels()) {
//                if (client.getId() == gridClient.getId()) {
//                  gridClient.setName(gridClient.getName());
//                  client.setName(gridClient.getName()+"-");
//                }
//              }
//            }
            store.removeAll();
            store.add(clients);
//            for (Client client: clients) {
//              for (Client gridClient: store.getModels()) {
//                if (client.getId() == gridClient.getId()) {
//                  gridClient.setName(gridClient.getName()+"-");
//                }
//              }
//            }
          }

        });


      }
    };
    updateClientsTimer.scheduleRepeating(1000);
//    setLayout(new FlowLayout(10));


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
        final Button b = new Button("Play", new SelectionListener<ButtonEvent>() {
          @Override
          public void componentSelected(ButtonEvent ce) {
//            final Client selectedItem = grid.getSelectionModel().getSelectedItem();
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
        toggleInprogressButtonStyle(model, b);
        b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
        b.setToolTip("Click for more information");

        return b;
      }
    };

    GridCellRenderer<Client> stopButtonRenderer = new GridCellRenderer<Client>() {

      public Object render(final Client model, String property, ColumnData config, final int rowIndex,
                           final int colIndex, ListStore<Client> store, Grid<Client> grid) {
        Button b = new Button("Stop", new SelectionListener<ButtonEvent>() {
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

    GridCellRenderer<Client> timeRenderer = new GridCellRenderer<Client>() {

      public Object render(final Client model, String property, ColumnData config, final int rowIndex,
                           final int colIndex, ListStore<Client> store, Grid<Client> grid) {
        LabelField timeLabel = new LabelField();
        Long currentTime = System.currentTimeMillis();
        timeLabel.setValue(getMinutesString(currentTime - model.getStartTime()));
        return timeLabel;
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
    column.setId("name");
    column.setHeaderHtml("Имя");
    column.setRenderer(nameCellRenderer);
    column.setWidth(100);
    configs.add(column);

//    column = new ColumnConfig();
//    column.setId("name2");
//    column.setRenderer(new ComboGridCellRenderer());
//    column.setHeaderHtml("Имя combo");
//    column.setWidth(50);
//    configs.add(column);

    column = new ColumnConfig();
    column.setId("name");
    column.setHeaderHtml("Имя");
    column.setWidth(100);
    column.setRenderer(buttonRenderer);
    configs.add(column);


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
//    column = new ColumnConfig();
//    column.setId("last");
//    column.setHeaderHtml("Last");
//    column.setAlignment(HorizontalAlignment.RIGHT);
//    column.setWidth(75);
////    column.setRenderer(gridNumber);
//    configs.add(column);
//
//    column = new ColumnConfig();
//    column.setId("last");
//    column.setHeaderHtml("Last");
//    column.setAlignment(HorizontalAlignment.RIGHT);
//    column.setWidth(75);
////    column.setRenderer(gridNumber);
//    configs.add(column);
//
//    column = new ColumnConfig();
//    column.setId("last");
//    column.setHeaderHtml("Last");
//    column.setAlignment(HorizontalAlignment.RIGHT);
//    column.setWidth(75);
////    column.setRenderer(gridNumber);
//    configs.add(column);

//    column = new ColumnConfig("change", "Change", 100);
//    column.setAlignment(HorizontalAlignment.RIGHT);
////    column.setRenderer(change);
//    configs.add(column);

//    column = new ColumnConfig("date", "Last Updated", 100);
//    column.setAlignment(HorizontalAlignment.RIGHT);
//    column.setDateTimeFormat(DateTimeFormat.getShortDateFormat());
//    configs.add(column);

    clientsServiceAsync.getClients(new AsyncCallback<ArrayList<Client>>() {
      @Override
      public void onFailure(Throwable throwable) {
        System.out.println("faile getting clients");
      }

      @Override
      public void onSuccess(ArrayList<Client> clients) {
        store.add(clients);
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

    grid = new Grid<Client>(store, cm);
//    grid.setWidth("100%");
    grid.setStyleAttribute("borderTop", "none");
    grid.setAutoExpandColumn("name");
    grid.setBorders(true);
    cp.add(grid);

    add(cp);
  }

  private void toggleInprogressButtonStyle(Client model, Button b) {
    if (model.isInProgress()) {
      b.getElement().getStyle().setBackgroundColor("lightgreen");
    } else {
      b.getElement().getStyle().setBackgroundColor("black");
    }
    b.repaint();
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



}
