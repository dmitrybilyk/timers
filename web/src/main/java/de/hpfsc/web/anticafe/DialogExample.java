/*
 * Ext GWT 2.2.1 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.hpfsc.web.anticafe;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FlowData;
import com.google.gwt.user.client.Element;
import de.hpfsc.shared.Session;

public class DialogExample extends Window {


  private Button saveButton;
  private Button cancelButton;

  public DialogExample(Session session) {
    setModal(true);
    setHeadingHtml("Создание сессии");
    setSize(380, 440);
//    setScrollMode(Scroll.AUTOY);

    add(new CustomFormExample(session));
//
//    saveButton = new Button("Сохранить");
//    cancelButton = new Button("Отмена");
//    ButtonBar buttonBar = new ButtonBar();
//    buttonBar.add(saveButton);
//    buttonBar.add(cancelButton);
//    add(buttonBar);

  }
}
