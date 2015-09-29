package de.hpfsc.web.anticafe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
  
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.BeanModelMarker;
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
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;  
import com.google.gwt.user.client.Element;
import de.hpfsc.shared.Client;


public class WidgetRenderingExample extends LayoutContainer {  
  
  @Override  
  protected void onRender(Element parent, int index) {  
    super.onRender(parent, index);

    setHeight(300);
//    setLayout(new FlowLayout(10));
  
    GridCellRenderer<BeanModel> buttonRenderer = new GridCellRenderer<BeanModel>() {
  
      private boolean init;  
  
      public Object render(final BeanModel model, String property, ColumnData config, final int rowIndex,
          final int colIndex, ListStore<BeanModel> store, Grid<BeanModel> grid) {
        if (!init) {  
          init = true;  
          grid.addListener(Events.ColumnResize, new Listener<GridEvent<BeanModel>>() {
  
            public void handleEvent(GridEvent<BeanModel> be) {
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
            Info.display(model.<Client>getBean().getName(), "<ul><li>" + model.<Client>getBean().getName() + "</li></ul>");
          }  
        });  
        b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);  
        b.setToolTip("Click for more information");  
  
        return b;  
      }  
    };  
  
    final NumberFormat currency = NumberFormat.getCurrencyFormat();  
    final NumberFormat number = NumberFormat.getFormat("0.00");  
  
    GridCellRenderer<BeanModel> change = new GridCellRenderer<BeanModel>() {
      public String render(BeanModel model, String property, ColumnData config, int rowIndex, int colIndex,
          ListStore<BeanModel> store, Grid<BeanModel> grid) {
        double val = model.<Client>getBean().getId();
        String style = val < 0 ? "red" : "green";  
        return "<span style='color:" + style + "'>" + number.format(val) + "</span>";  
      }  
    };  
  
    GridCellRenderer<BeanModel> gridNumber = new GridCellRenderer<BeanModel>() {
      public String render(BeanModel model, String property, ColumnData config, int rowIndex, int colIndex,
          ListStore<BeanModel> stor, Grid<BeanModel> grid) {
        Number value = model.<Client>getBean().getId();
        return currency.format(model.<Number>get(property));
      }  
    };  
  
    List<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
  
    ColumnConfig column = new ColumnConfig();  
    column.setId("name2");
    column.setRenderer(new ComboGridCellRenderer());
    column.setHeaderHtml("Имя");
    column.setWidth(50);
    configs.add(column);

    column = getSpeechTagNameColumn();
//    column.setId("name");
//    column.setHeaderHtml("Имя");
//    column.setWidth(50);
    configs.add(column);

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
    column.setRenderer(gridNumber);  
    configs.add(column);  
  
    column = new ColumnConfig("change", "Change", 100);  
    column.setAlignment(HorizontalAlignment.RIGHT);  
    column.setRenderer(change);  
    configs.add(column);  
  
    column = new ColumnConfig("date", "Last Updated", 100);  
    column.setAlignment(HorizontalAlignment.RIGHT);  
    column.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/yyyy"));  
    configs.add(column);  
  
    final ListStore<BeanModel> store = new ListStore<BeanModel>();



    Client client = new Client();
    client.setName("test name");
    BeanModelFactory factory = BeanModelLookup.get().getFactory(Client.class);
    List<BeanModel> beanModels = new ArrayList<>();
    BeanModel model = factory.createModel(client);
    beanModels.add(model);
    store.add(beanModels);
  
    ColumnModel cm = new ColumnModel(configs);  
  
    ContentPanel cp = new ContentPanel();
    cp.getElement().getStyle().setTop(10, Style.Unit.PX);
    cp.setBodyBorder(false);
    cp.setWidth("100%");
//    cp.setHeight(400);
//    cp.setIcon(Resources.ICONS.table());
    cp.setHeaderVisible(false);
    cp.setButtonAlign(HorizontalAlignment.CENTER);
    cp.setLayout(new FitLayout());
//    cp.setSize(800, 250);
  
    Grid<BeanModel> grid = new Grid<BeanModel>(store, cm);
    grid.setHeight(250);
    grid.setWidth(1000);
    grid.getElement().getStyle().setTop(10, Style.Unit.PX);
    grid.setStyleAttribute("borderTop", "none");
    grid.setAutoExpandColumn("name");  
    grid.setBorders(true);  
    cp.add(grid);  
  
    add(cp);  
  }

  private ColumnConfig getSpeechTagNameColumn() {
    GridCellRenderer<BeanModel> nameRenderer = new GridCellRenderer<BeanModel>() {
      @Override
      public Object render(BeanModel model, String property, ColumnData config, int rowIndex,
                           int colIndex, ListStore<BeanModel> store, Grid<BeanModel> grid) {

        LayoutContainer container = new LayoutContainer();
        container.addText(model.<Client>getBean().getName());
        return container;
      }
    };

    ColumnConfig nameColumn = new ColumnConfig(Client.Fields.NAME.getFieldName(),
            "Name", 50);
    nameColumn.setRenderer(nameRenderer);
    nameColumn.setSortable(false);
    nameColumn.setFixed(false);
    nameColumn.setMenuDisabled(true);
    nameColumn.setResizable(false);
    return nameColumn;
  }

}  