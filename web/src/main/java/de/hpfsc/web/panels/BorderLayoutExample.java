package de.hpfsc.web.panels;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;  
import com.extjs.gxt.ui.client.event.ButtonEvent;  
import com.extjs.gxt.ui.client.event.SelectionListener;  
import com.extjs.gxt.ui.client.util.Margins;  
import com.extjs.gxt.ui.client.widget.ContentPanel;  
import com.extjs.gxt.ui.client.widget.LayoutContainer;  
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;
import de.hpfsc.shared.WhoseSessionEnum;
import de.hpfsc.web.anticafe.ClientSessionPanel;
import de.hpfsc.web.anticafe.WidgetRenderingExample;

public class BorderLayoutExample extends LayoutContainer {  
  
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
        north.add(new ClientSessionPanel(WhoseSessionEnum.ADMIN, -1, "", "", System.currentTimeMillis(), false));
        north.layout();
      }
    });
    north.add(new WidgetRenderingExample(), new BorderLayoutData(LayoutRegion.CENTER, 350));

    north.add(createSessionButton, new BorderLayoutData(LayoutRegion.SOUTH, 100));
    ContentPanel west = new ContentPanel();
    west.setHeight(200);
    west.setHeaderVisible(true);
    west.setHeadingHtml("Инфо");
    west.add(new LabelField("Имя пользователя здесь"));
    west.add(new Button("Выход"));

    ContentPanel center = new ContentPanel();
    center.setHeaderVisible(true);
    center.setHeight(200);
    center.setWidth(500);
    center.add(new LabelField("center"));
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
    east.setHeaderVisible(true);
    east.setHeadingHtml("Итоги");
    east.setHeight(200);
    east.add(new LabelField("east"));
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