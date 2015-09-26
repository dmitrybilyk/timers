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
import com.google.gwt.user.client.Element;  
import com.google.gwt.user.client.ui.FlexTable;  
  
public class BorderLayoutExample extends LayoutContainer {  
  
  protected void onRender(Element target, int index) {  
    super.onRender(target, index);  
    final BorderLayout layout = new BorderLayout();
    setHeight(1200);
    setLayout(layout);  
//    setStyleAttribute("padding", "10px");
  
    ContentPanel north = new ContentPanel();
    north.addStyleName("content-panel");
    north.setWidth(600);
    north.setHeight(300);
    north.add(new LabelField("north"));
    ContentPanel west = new ContentPanel();
    west.setHeight("100%");
    west.setHeaderVisible(true);
    west.setHeadingHtml("Инфо");
    west.add(new LabelField("Имя пользователя здесь"));
    west.add(new Button("Выход"));

    ContentPanel center = new ContentPanel();
    center.setHeaderVisible(true);
    center.setHeight("100%");
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
    east.setHeight("100%");
    east.add(new LabelField("east"));
    ContentPanel south = new ContentPanel();
//    south.setWidth(600);
    south.setHeaderVisible(false);
    south.add(new LabelField("south"));
  
    BorderLayoutData northData = new BorderLayoutData(LayoutRegion.NORTH, 300);
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