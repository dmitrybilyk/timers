/*
 * Ext GWT 2.2.1 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.hpfsc.web.anticafe;

import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import de.hpfsc.shared.Client;

import java.util.List;

public class DialogExample extends Window {


  private Button saveButton;
  private Button cancelButton;

  public DialogExample(Client client, List<Client> models) {
    setModal(true);
    setHeadingHtml("Создание сессии");
    setSize(380, 440);
//    setScrollMode(Scroll.AUTOY);

    add(new AddEditSessionForm(client, models));
//
//    saveButton = new Button("Сохранить");
//    cancelButton = new Button("Отмена");
//    ButtonBar buttonBar = new ButtonBar();
//    buttonBar.add(saveButton);
//    buttonBar.add(cancelButton);
//    add(buttonBar);

  }
}
