package de.hpfsc.web.panels;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import de.hpfsc.shared.Client;
import de.hpfsc.shared.WhoseSessionEnum;
import de.hpfsc.web.anticafe.DialogExample;
import de.hpfsc.web.anticafe.WidgetRenderingExample;
import de.hpfsc.web.dialogs.LoginDialog;

public class BorderLayoutExample extends LayoutContainer {

  String userName;
  LayoutContainer eastContainer;

  public LayoutContainer getEast() {
    return eastContainer;
  }

  LayoutContainer centerLayoutContainer;
  public LayoutContainer getCenter() {
    return centerLayoutContainer;
  }

  LayoutContainer westLayoutContainer;
  public LayoutContainer getWest() {
    return westLayoutContainer;
  }

  public BorderLayoutExample(String userName) {
    this.userName = userName;
  }

  public BorderLayoutExample() {

  }

  protected void onRender(Element target, int index) {
    super.onRender(target, index);  
    final BorderLayout layout = new BorderLayout();
    setHeight(1200);
    setLayout(layout);  
//    setStyleAttribute("padding", "10px");
  
    final ContentPanel north = new ContentPanel();
//    north.setLayout(new CenterLayout());
    north.addStyleName("content-panel");
    north.setWidth(600);
    north.setHeight("100%");
    Button createSessionButton = new Button("Создать сессию");
    createSessionButton.setWidth("100%");
    createSessionButton.setHeight(50);
    createSessionButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent ce) {
        DialogExample dialogExample = new DialogExample(WhoseSessionEnum.valueOf(userName.toUpperCase()), new Client(), null);
//        dialogExample.setWidth(300);
//        dialogExample.setHeight(200);
        dialogExample.show();
//        north.add(new ClientSessionPanel(WhoseSessionEnum.ADMIN, -1, "", "", System.currentTimeMillis(), false));
//        north.layout();
      }
    });
//    north.add(new SessionsGrid(), new BorderLayoutData(LayoutRegion.CENTER, 350));
    north.removeStyleName("admin-style");
    north.removeStyleName("first-user-style");
    north.removeStyleName("second-user-style");
    if (userName.equals("admin")) {
      north.addStyleName("admin-style");
    } else if (userName.equals("first")){
      north.addStyleName("first-user-style");
    } else if (userName.equals("second")) {
      north.addStyleName("second-user-style");
    }
    north.add(new WidgetRenderingExample(userName, BorderLayoutExample.this), new BorderLayoutData(LayoutRegion.CENTER, 350));

    north.add(createSessionButton, new BorderLayoutData(LayoutRegion.SOUTH, 100));
    ContentPanel west = new ContentPanel();
    westLayoutContainer = new LayoutContainer();
    westLayoutContainer.addStyleName("align-center");
    west.setHeight(200);
    west.setHeaderVisible(true);
    west.setHeadingHtml("Инфо");
    westLayoutContainer.add(new LabelField("Имя пользователя здесь"));
    west.add(westLayoutContainer);
    Button logoutButton = new Button("Выход");
    logoutButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent ce) {
        RootPanel.get().clear();
        ContentPanel centerPanel = new ContentPanel();
        centerPanel.setHeaderVisible(false);
        centerPanel.setLayout(new CenterLayout());
        centerPanel.add(new LoginDialog());
        centerPanel.setHeight(500);
        centerPanel.setWidth("100%");
        RootPanel.get().add(centerPanel);
      }
    });
    west.add(logoutButton);

    ContentPanel center = new ContentPanel();
    center.setHeaderVisible(true);
    center.setHeight(200);
    center.setWidth(500);
    centerLayoutContainer = new HorizontalPanel();
    centerLayoutContainer.addStyleName("align-center");
    center.add(centerLayoutContainer);
    center.setHeadingHtml("Диапазон дат");
//    center.setScrollMode(Scroll.AUTOX);
  
//    FlexTable table = new FlexTable();
//    table.getElement().getStyle().setProperty("margin", "10px");
//    table.setCellSpacing(8);
//    table.setCellPadding(4);
  
//    for (int i = 0; i < LayoutRegion.values().length; i++) {
//      final LayoutRegion r = LayoutRegion.values()[i];
//      if (r == LayoutRegion.CENTER) {
//        continue;
//      }
//      SelectionListener<ButtonEvent> sl = new SelectionListener<ButtonEvent>() {
//
//        @Override
//        public void componentSelected(ButtonEvent ce) {
//          String txt = ce.getButton().getHtml();
//          if (txt.equals("Expand")) {
//            layout.expand(r);
//          } else if (txt.equals("Collapse")) {
//            layout.collapse(r);
//          } else if (txt.equals("Show")) {
//            layout.show(r);
//          } else {
//            layout.hide(r);
//          }
//
//        }
//      };
////      table.setHTML(i, 0, "<div style='font-size: 12px; width: 100px'>" + r.name() + ":</span>");
////      table.setWidget(i, 1, new Button("Expand", sl));
////      table.setWidget(i, 2, new Button("Collapse", sl));
////      table.setWidget(i, 3, new Button("Show", sl));
////      table.setWidget(i, 4, new Button("Hide", sl));
//    }
//    center.add(table);
  
    ContentPanel east = new ContentPanel();
    eastContainer = new LayoutContainer();
    eastContainer.addStyleName("align-center");
    eastContainer.getElement().getStyle().setTextAlign(Style.TextAlign.CENTER);
    eastContainer.getElement().getStyle().setFontSize(20, Style.Unit.PX);
    east.getElement().getStyle().setTextAlign(Style.TextAlign.CENTER);
    east.getElement().getStyle().setFontSize(16, Style.Unit.PX);
    east.setHeaderVisible(true);
    east.setHeadingHtml("Итоги");
    east.setHeight(200);
    east.add(eastContainer);
    eastContainer.add(new LabelField("Сумма всего:"));
    ContentPanel south = new ContentPanel();
//    south.setWidth(600);
    south.setHeaderVisible(false);
    south.add(new LabelField("south"));
  
    BorderLayoutData northData = new BorderLayoutData(LayoutRegion.NORTH, 400);
    north.setHeaderVisible(true);
    north.setHeadingHtml("Таймеры");
//    northData.setCollapsible(true);
//    northData.setFloatable(true);
//    northData.setHideCollapseTool(true);
    northData.setSplit(true);
//    northData.setMargins(new Margins(0, 0, 5, 0));
  
    BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST);
//    westData.setSplit(true);
//    westData.setCollapsible(true);
//    westData.setMargins(new Margins(0,5,0,0));
  
    BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
//    centerData.setMargins(new Margins(0));
  
    BorderLayoutData eastData = new BorderLayoutData(LayoutRegion.EAST);
//    eastData.setSplit(true);
//    eastData.setCollapsible(true);
//    eastData.setMargins(new Margins(0,0,0,5));
  
    BorderLayoutData southData = new BorderLayoutData(LayoutRegion.SOUTH);
//    southData.setSplit(true);
//    southData.setCollapsible(true);
//    southData.setFloatable(true);
//    southData.setMargins(new Margins(5, 0, 0, 0));
  
    add(north, northData);  
    add(west, westData);
    add(center, centerData);  
    add(east, eastData);
//    add(south, southData);
  }  
}  