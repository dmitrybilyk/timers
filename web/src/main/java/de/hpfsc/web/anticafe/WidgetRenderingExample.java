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
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import de.hpfsc.shared.Session;

import java.util.ArrayList;
import java.util.List;

public class WidgetRenderingExample extends LayoutContainer {

  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    setHeight(300);
//    setLayout(new FlowLayout(10));

    GridCellRenderer<Session> buttonRenderer = new GridCellRenderer<Session>() {

      private boolean init;

      public Object render(final Session model, String property, ColumnData config, final int rowIndex,
          final int colIndex, ListStore<Session> store, Grid<Session> grid) {
        if (!init) {
          init = true;
          grid.addListener(Events.ColumnResize, new Listener<GridEvent<Session>>() {

            public void handleEvent(GridEvent<Session> be) {
              for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
                if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
                    && be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent) {
                  ((BoxComponent) be.getGrid().getView().getWidget(i, be.getColIndex())).setWidth(be.getWidth() - 10);
                }
              }
            }
          });
        }

        Button b = new Button((String) model.get(property), new SelectionListener<ButtonEvent>() {
          @Override
          public void componentSelected(ButtonEvent ce) {
            Info.display(model.getName(), "<ul><li>" + model.getSymbol() + "</li></ul>");
          }
        });
        b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
        b.setToolTip("Click for more information");

        return b;
      }
    };

    final NumberFormat currency = NumberFormat.getCurrencyFormat();
    final NumberFormat number = NumberFormat.getFormat("0.00");
//    final NumberCellRenderer<Grid<Session>> numberRenderer = new NumberCellRenderer<Grid<Session>>(currency);

    GridCellRenderer<Session> change = new GridCellRenderer<Session>() {
      public String render(Session model, String property, ColumnData config, int rowIndex, int colIndex,
          ListStore<Session> store, Grid<Session> grid) {
        double val = (Double) model.get(property);
        String style = val < 0 ? "red" : "green";
        return "<span style='color:" + style + "'>" + number.format(val) + "</span>";
      }
    };

//    GridCellRenderer<Session> gridNumber = new GridCellRenderer<Session>() {
//      public String render(Session model, String property, ColumnData config, int rowIndex, int colIndex,
//          ListStore<Session> stor, Grid<Session> grid) {
//        return numberRenderer.render(null, property, model.get(property));
//      }
//    };

    List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

    ColumnConfig column = new ColumnConfig();
    column.setId("name");
    column.setHeaderHtml("Company");
    column.setWidth(100);
    configs.add(column);

//    column = new ColumnConfig();
//    column.setId("name2");
//    column.setRenderer(new ComboGridCellRenderer());
//    column.setHeaderHtml("Имя combo");
//    column.setWidth(50);
//    configs.add(column);

    column = new ColumnConfig();
    column.setId("symbol");
    column.setHeaderHtml("Symbol");
    column.setWidth(100);
    column.setRenderer(buttonRenderer);
    configs.add(column);

    column = new ColumnConfig();
    column.setId("last");
    column.setHeaderHtml("Last");
    column.setAlignment(HorizontalAlignment.RIGHT);
    column.setWidth(75);
//    column.setRenderer(gridNumber);
    configs.add(column);

    column = new ColumnConfig();
    column.setId("last");
    column.setHeaderHtml("Last");
    column.setAlignment(HorizontalAlignment.RIGHT);
    column.setWidth(75);
//    column.setRenderer(gridNumber);
    configs.add(column);

    column = new ColumnConfig();
    column.setId("last");
    column.setHeaderHtml("Last");
    column.setAlignment(HorizontalAlignment.RIGHT);
    column.setWidth(75);
//    column.setRenderer(gridNumber);
    configs.add(column);

    column = new ColumnConfig("change", "Change", 100);
    column.setAlignment(HorizontalAlignment.RIGHT);
    column.setRenderer(change);
    configs.add(column);

    column = new ColumnConfig("date", "Last Updated", 100);
    column.setAlignment(HorizontalAlignment.RIGHT);
    column.setDateTimeFormat(DateTimeFormat.getShortDateFormat());
    configs.add(column);

    final ListStore<Session> store = new ListStore<Session>();
    store.add(TestData.getSessions());

    ColumnModel cm = new ColumnModel(configs);

    ContentPanel cp = new ContentPanel();
    cp.setBodyBorder(false);
//    cp.setIcon(Resources.ICONS.table());
    cp.setHeadingHtml("Widget Renderer Grid");
    cp.setButtonAlign(HorizontalAlignment.CENTER);
    cp.setLayout(new FitLayout());
    cp.setSize(1100, 300);

    Grid<Session> grid = new Grid<Session>(store, cm);
//    grid.setWidth("100%");
    grid.setStyleAttribute("borderTop", "none");
    grid.setAutoExpandColumn("name");
    grid.setBorders(true);
    cp.add(grid);

    add(cp);
  }
}
