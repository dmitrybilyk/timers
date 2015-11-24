package de.hpfsc.web.anticafe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;  
import java.util.List;  
  
//import com.extjs.gxt.samples.resources.client.TestData;
//import com.extjs.gxt.samples.resources.client.model.Stock;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;  
import com.extjs.gxt.ui.client.core.El;  
import com.extjs.gxt.ui.client.core.XDOM;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;  
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Component;  
import com.extjs.gxt.ui.client.widget.ComponentPlugin;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Slider;
import com.extjs.gxt.ui.client.widget.VerticalPanel;  
import com.extjs.gxt.ui.client.widget.button.Button;  
import com.extjs.gxt.ui.client.widget.form.CheckBox;  
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;  
import com.extjs.gxt.ui.client.widget.form.ComboBox;  
import com.extjs.gxt.ui.client.widget.form.DateField;  
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;  
import com.extjs.gxt.ui.client.widget.form.FormPanel;  
import com.extjs.gxt.ui.client.widget.form.Radio;  
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.SliderField;
import com.extjs.gxt.ui.client.widget.form.TextArea;  
import com.extjs.gxt.ui.client.widget.form.TextField;  
import com.extjs.gxt.ui.client.widget.form.TimeField;  
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;  
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import de.hpfsc.shared.Client;
import de.hpfsc.shared.ClientName;
import de.hpfsc.shared.ClientNamesEnum;
import de.hpfsc.shared.Session;
import de.hpfsc.shared.WhoseSessionEnum;
import de.hpfsc.shared.model.Stock;
import de.hpfsc.web.ClientsService;
import de.hpfsc.web.ClientsServiceAsync;
import de.hpfsc.web.NamesService;
import de.hpfsc.web.NamesServiceAsync;
import de.hpfsc.web.panels.BasicTabExample;

public class AddEditSessionForm extends LayoutContainer {
    
  private VerticalPanel vp;
  private FormData formData;
  private Client currentClient;
  final SimpleComboBox<String> simpleNameCombo = new SimpleComboBox<>();
  final TextArea comment = new TextArea();
  final SimpleComboBox<String> simpleOwnerCombo = new SimpleComboBox<>();
  FormPanel simple;
  String oldName;
  String oldOwnerName;
  private ClientsServiceAsync clientsServiceAsync = GWT.create(ClientsService.class);
  private NamesServiceAsync namesServiceAsync = GWT.create(NamesService.class);

  public AddEditSessionForm(Client client, List<Client> models) {
    oldName = client.getName();
    oldOwnerName = client.getWhoseSession().name();
    this.currentClient = client;
    namesServiceAsync.getFreeNames(new AsyncCallback<List<String>>() {
      @Override
      public void onFailure(Throwable throwable) {
        System.out.println("fail load free names");
      }

      @Override
      public void onSuccess(List<String> freeNames) {
        for (String name: freeNames) {
          simpleNameCombo.add(name);
        }
      }
    });

    namesServiceAsync.getFreeOwnersNames(new AsyncCallback<List<String>>() {
      @Override
      public void onFailure(Throwable throwable) {
        System.out.println("fail load free owners names");
      }

      @Override
      public void onSuccess(List<String> freeNames) {
        for (String name : freeNames) {
          simpleOwnerCombo.add(name);
        }
        simpleOwnerCombo.setSimpleValue(currentClient.getWhoseSession().name());
      }
    });
//    for (WhoseSessionEnum whoseSessionEnum: WhoseSessionEnum.values()) {
//      simpleOwnerCombo.add(whoseSessionEnum.name());
//    }


//    List<String> usedNames = new ArrayList<>();
//    if (models != null) {
//      for (Client usingClient: models) {
//        usedNames.add(usingClient.getName());
//      }
//    }

    simpleNameCombo.setTriggerAction(TriggerAction.ALL);
//    for (ClientNamesEnum clientName: ClientNamesEnum.values()) {
//      if (!usedNames.contains(clientName.name())) {
//        simpleNameCombo.add(clientName.name());
//      }
//    }
    simpleNameCombo.setSimpleValue(currentClient.getName());
    comment.setValue(currentClient.getComment());

  }

  @Override  
  protected void onRender(Element parent, int index) {  
    super.onRender(parent, index);  
    formData = new FormData("-20");  
    vp = new VerticalPanel();
    vp.setSpacing(10);
    createForm1();  
    add(vp);  
  }  
  
  private void createForm1() {  
    simple = new FormPanel();

    simple.setHeaderVisible(false);
    simple.setFrame(false);
    simple.setWidth(340);
    simple.setHeight(400);
  
    ComponentPlugin plugin = new ComponentPlugin() {  
      public void init(Component component) {  
        component.addListener(Events.Render, new Listener<ComponentEvent>() {  
          public void handleEvent(ComponentEvent be) {  
            El elem = be.getComponent().el().findParent(".x-form-element", 3);  
            // should style in external CSS  rather than directly  
            elem.appendChild(XDOM.create("<div style='color: #615f5f;padding: 1 0 2 0px;'>" + be.getComponent().getData("text") + "</div>"));  
          }  
        });  
      }  
    };



    simpleOwnerCombo.setFieldLabel("Кому принадлежит");
//    for (WhoseSessionEnum whoseSessionEnum: WhoseSessionEnum.values()) {
//      simpleOwnerCombo.add(whoseSessionEnum.name());
//    }
    simpleOwnerCombo.setSimpleValue(WhoseSessionEnum.ADMIN.name());
    simple.add(simpleOwnerCombo);


    simpleNameCombo.setFieldLabel("Псевдоним");
//    for (ClientNamesEnum clientName: ClientNamesEnum.values()) {
//      simpleNameCombo.add(clientName.name());
//    }
    simple.add(simpleNameCombo);


    comment.setFieldLabel("Комментарий");
    comment.setEmptyText("Введите комментарий");
    simple.add(comment, formData);
  
//    TextField<String> email = new TextField<String>();
//    email.setFieldLabel("Email");
//    email.setAllowBlank(false);
//    email.addPlugin(plugin);
//    email.setData("text", "Enter you email (required)");
//    simple.add(email, formData);
//
//    List<Session> stocks = TestData.getSessions();
//    Collections.sort(stocks, new Comparator<Session>() {
//      public int compare(Session arg0, Session arg1) {
//        return arg0.getName().compareTo(arg1.getName());
//      }
//    });
//
//    ListStore<Session> store = new ListStore<Session>();
//    store.add(stocks);
//
//    ComboBox<Session> combo = new ComboBox<Session>();
//    combo.setFieldLabel("Company");
//    combo.setDisplayField("name");
//    combo.setTriggerAction(TriggerAction.ALL);
//    combo.setStore(store);
//    combo.addPlugin(plugin);
//    combo.setData("text", "Choose the company");
//    simple.add(combo, formData);
//
//    DateField date = new DateField();
//    date.setFieldLabel("Birthday");
//    date.addPlugin(plugin);
//    date.setData("text", "Enter your birthday");
//    simple.add(date, formData);
//
//    TimeField time = new TimeField();
//    time.setFieldLabel("Time");
//    time.addPlugin(plugin);
//    time.setData("text", "Enter the time");
//    simple.add(time, formData);
//
//    Slider slider = new Slider();
//    slider.setMinValue(40);
//    slider.setMaxValue(90);
//    slider.setValue(60);
//    slider.setIncrement(1);
//    slider.setMessage("{0} inches tall");
//    slider.addPlugin(plugin);
//    slider.setData("text", "Select your height");
//
//    final SliderField sf = new SliderField(slider);
//    sf.setFieldLabel("Size");
//    simple.add(sf, formData);
//
//    CheckBox check1 = new CheckBox();
//    check1.setBoxLabel("Classical");
//
//    CheckBox check2 = new CheckBox();
//    check2.setBoxLabel("Rock");
//    check2.setValue(true);
//
//    CheckBox check3 = new CheckBox();
//    check3.setBoxLabel("Blues");
//
//    CheckBoxGroup checkGroup = new CheckBoxGroup();
//    checkGroup.setFieldLabel("Music");
//    checkGroup.add(check1);
//    checkGroup.add(check2);
//    checkGroup.add(check3);
//    checkGroup.addPlugin(plugin);
//    checkGroup.setData("text", "Select your favorite music type");
//    simple.add(checkGroup, formData);
//
//    Radio radio = new Radio();
//    radio.setBoxLabel("Red");
//    radio.setValue(true);
//
//    Radio radio2 = new Radio();
//    radio2.setBoxLabel("Blue");
//
//    RadioGroup radioGroup = new RadioGroup();
//    radioGroup.setFieldLabel("Favorite Color");
//    radioGroup.add(radio);
//    radioGroup.add(radio2);
//    radioGroup.addPlugin(plugin);
//    radioGroup.setData("text", "Select your favorite color");
//    simple.add(radioGroup, formData);
//
//    Radio radio3 = new Radio();
//    radio3.setBoxLabel("Apple");
//    radio3.setValue(true);
//
//    Radio radio4 = new Radio();
//    radio4.setBoxLabel("Banana");
//
//    RadioGroup radioGroup2 = new RadioGroup();
//    radioGroup2.setFieldLabel("Favorite Fruit");
//    radioGroup2.add(radio3);
//    radioGroup2.add(radio4);
//    radioGroup2.addPlugin(plugin);
//    radioGroup2.setData("text", "Select you favorite fruit");
//    simple.add(radioGroup2, formData);
//
//    TextArea description = new TextArea();
//    description.setPreventScrollbars(true);
//    description.setFieldLabel("Description");
//    simple.add(description, formData);
//
    Button saveButton = new Button("Сохранить");
    saveButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent ce) {
        if (currentClient.getId() == null) {
          clientsServiceAsync.addClient(WhoseSessionEnum.valueOf(simpleOwnerCombo.getValue().getValue()), 0,
                  simpleNameCombo.getValue().getValue(), comment.getValue(), 0, 0, new AsyncCallback<Long>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                      System.out.println("fail");
                    }

                    @Override
                    public void onSuccess(Long result) {
                      RootPanel.get().clear();
                      RootPanel.get().add(new BasicTabExample());
                      System.out.println(result);
                      namesServiceAsync.saveUsedNames(Arrays.asList(simpleNameCombo.getValue().getValue()),
                              oldName, new AsyncCallback<Void>() {
                                @Override
                                public void onFailure(Throwable throwable) {
                                  System.out.println("fail save used names");
                                }

                                @Override
                                public void onSuccess(Void aVoid) {
                                  System.out.println("used name is saved ");
                                }
                              });
                      namesServiceAsync.saveUsedOwnersNames(Arrays.asList(simpleOwnerCombo.getValue().getValue()),
                              oldOwnerName, new AsyncCallback<Void>() {
                                @Override
                                public void onFailure(Throwable throwable) {
                                  System.out.println("fail save used owners names");
                                }

                                @Override
                                public void onSuccess(Void aVoid) {
                                  System.out.println("used owner name is saved ");
                                }
                              });
                    }
                  });
        } else {
          final Client updatedClient = new Client();
          updatedClient.setId(currentClient.getId());
          updatedClient.setName(simpleNameCombo.getValue().getValue());
          updatedClient.setComment(comment.getValue());
          updatedClient.setWhoseSession(WhoseSessionEnum.valueOf(simpleOwnerCombo.getValue().getValue()));
          clientsServiceAsync.updateClient(updatedClient, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
              System.out.println("fail update");
            }

            @Override
            public void onSuccess(Void result) {
              RootPanel.get().clear();
              RootPanel.get().add(new BasicTabExample());
              Info.display("Updated", "client " + updatedClient.getName() + " is updated");
            }
          });
          namesServiceAsync.saveUsedNames(Arrays.asList(simpleNameCombo.getValue().getValue()),
                  oldName, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                      System.out.println("fail save used names");
                    }

                    @Override
                    public void onSuccess(Void aVoid) {
                      System.out.println("used name is saved ");
                    }
                  });
          namesServiceAsync.saveUsedOwnersNames(Arrays.asList(simpleOwnerCombo.getValue().getValue()),
                  oldOwnerName, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                      System.out.println("fail save used owners names");
                    }

                    @Override
                    public void onSuccess(Void aVoid) {
                      System.out.println("used owner name is saved ");
                    }
                  });
        }

      }
    });
    simple.addButton(saveButton);
    Button cancelButton = new Button("Отмена", new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent ce) {
        AddEditSessionForm.this.hide();
      }
    });
    simple.addButton(cancelButton);
  
    simple.setButtonAlign(HorizontalAlignment.CENTER);  
  
    FormButtonBinding binding = new FormButtonBinding(simple);  
    binding.addButton(saveButton);
  
    vp.add(simple);  
  }  
  
    
}  