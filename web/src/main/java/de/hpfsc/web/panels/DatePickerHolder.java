package de.hpfsc.web.panels;

//import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.DatePicker;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;

/**
 * Created by dmitry on 17.02.16.
 */
public class DatePickerHolder extends Widget {
  @Override
  public Widget asWidget() {
    CountryProperties countryProps = GWT.create(CountryProperties.class);
    CssFloatLayoutContainer container = new CssFloatLayoutContainer();
//    container.setWidth(300);
//    container.setHeight(300);
    ComboBox<TestClient> comboBox = new ComboBox<TestClient>(new ListStore<>(countryProps.name()), countryProps.age());
    comboBox.getStore().add(new TestClient("Dima", 4));
    comboBox.getStore().add(new TestClient("Ruslan", 4));
    container.add(comboBox);
    container.add(new DatePicker(), new CssFloatLayoutContainer.CssFloatData(500, new Margins(10)));
    container.add(new DatePicker(), new CssFloatLayoutContainer.CssFloatData(500, new Margins(10)));
    container.add(comboBox);
    container.add(comboBox);
    return container;
  }

  interface CountryProperties extends PropertyAccess<TestClient> {
    ModelKeyProvider<TestClient> name();

    LabelProvider<TestClient> age();
  }


  public class TestClient{
    private String name;
    private long age;

    public TestClient(String dima, int i) {
      this.name = dima;
      this.age = i;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public long getAge() {
      return age;
    }

    public void setAge(long age) {
      this.age = age;
    }
  }

}
