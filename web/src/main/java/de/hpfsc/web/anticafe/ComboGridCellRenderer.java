package de.hpfsc.web.anticafe;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.WidgetComponent;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import de.hpfsc.shared.ClientNamesEnum;
import de.hpfsc.shared.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ComboGridCellRenderer implements GridCellRenderer<BeanModel> {
  private int width = 200;
  private int COMBOBOX_LIST_WIDTH = width + 80;

  public ComboGridCellRenderer() {
  }

  public void setWidth(int _width) {
    width = _width;
  }

  @Override
  public Object render(final BeanModel model, String property, ColumnData config, final int rowIndex,
                       final int colIndex, ListStore<BeanModel> store, Grid<BeanModel> grid) {

    final SimpleComboBox<String> namesCombo = new SimpleComboBox<String>();

    namesCombo.setDisplayField("DisplayField");
    namesCombo.setWidth(width);
    namesCombo.setFireChangeEventOnSetValue(true);
    namesCombo.setMinListWidth(COMBOBOX_LIST_WIDTH);


    BeanModelFactory factory = BeanModelLookup.get().getFactory(String.class);

    List<String> clientNamesList = new ArrayList<>();
    for (ClientNamesEnum clientNamesEnum: ClientNamesEnum.values()) {
      namesCombo.add(clientNamesEnum.name());
    }

//    List<Session> answerList = factory.createModel(clientNamesList);

//    ListStore<String> answerComboStore = new ListStore<String>();
//    answerComboStore.setSortField("");
//    answerComboStore.add(clientNamesList);
//    namesCombo.setStore(answerComboStore);

    LayoutContainer container = new LayoutContainer();
    HBoxLayout layout = new HBoxLayout();
    layout.setHBoxLayoutAlign(HBoxLayout.HBoxLayoutAlign.MIDDLE);
    container.setLayout(layout);
    container.add(namesCombo, new HBoxLayoutData(new Margins(0, 5, 0, 5)));
    return container;
  }

}
