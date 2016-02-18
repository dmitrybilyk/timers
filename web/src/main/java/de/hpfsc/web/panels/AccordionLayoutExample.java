/**
 * Sencha GXT 4.0.0 - Sencha for GWT
 * Copyright (c) 2006-2015, Sencha Inc.
 *
 * licensing@sencha.com
 * http://www.sencha.com/products/gxt/license/
 *
 * ================================================================================
 * Evaluation/Trial License
 * ================================================================================
 * This version of Sencha GXT is licensed commercially for a limited period for
 * evaluation purposes only. Production use or use beyond the applicable evaluation
 * period is prohibited under this license.
 *
 * Please see the Sencha GXT Licensing page at:
 * http://www.sencha.com/products/gxt/license/
 *
 * For clarification or additional options, please contact:
 * licensing@sencha.com
 * ================================================================================
 *
 *
 *
 *
 *
 *
 *
 * ================================================================================
 * Disclaimer
 * ================================================================================
 * THIS SOFTWARE IS DISTRIBUTED "AS-IS" WITHOUT ANY WARRANTIES, CONDITIONS AND
 * REPRESENTATIONS WHETHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, MERCHANTABLE QUALITY,
 * FITNESS FOR A PARTICULAR PURPOSE, DURABILITY, NON-INFRINGEMENT, PERFORMANCE AND
 * THOSE ARISING BY STATUTE OR FROM CUSTOM OR USAGE OF TRADE OR COURSE OF DEALING.
 * ================================================================================
 */
package de.hpfsc.web.panels;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.IconProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.DatePicker;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.ExpandMode;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.tree.Tree;

public class AccordionLayoutExample extends Composite {

  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 200;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
//      DatePicker.DatePickerAppearance datePickerAppearance = GWT.create(DatePicker.DatePickerAppearance.class);
      CountryProperties countryProps = GWT.create(CountryProperties.class);
//      AccordionLayoutAppearance appearance = GWT.<AccordionLayoutAppearance> create(AccordionLayoutAppearance.class);
//
      Label dateRangeLabel = new Label("Date range:");
      ComboBox<TestClient> comboBox = new ComboBox<TestClient>(new ListStore<>(countryProps.name()), countryProps.age());
      comboBox.getStore().add(new TestClient("Dima", 4));
      comboBox.getStore().add(new TestClient("Ruslan", 4));

      AccordionLayoutContainer accordion = new AccordionLayoutContainer();
      accordion.setExpandMode(ExpandMode.SINGLE_FILL);
//      accordion.add(new DatePickerHolder());
      ContentPanel contentPanel = new ContentPanel();
      VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
      verticalLayoutContainer.setSize("500", "600");
      Margins margins = new Margins(5);
      HorizontalLayoutContainer dateRangeContainer = new HorizontalLayoutContainer();
      HorizontalLayoutContainer.HorizontalLayoutData layoutData3 = new HorizontalLayoutContainer.HorizontalLayoutData();
      layoutData3.setMargins(margins);
      dateRangeContainer.add(dateRangeLabel, layoutData3);
      dateRangeContainer.add(comboBox, layoutData3);
      verticalLayoutContainer.add(dateRangeContainer);
//      datePickerAppearance.onMonthSelected(Element);
      DatePicker datePicker = new DatePicker();
      datePicker.setWidth(270);
//      datePicker.setHeight(200);
      VerticalLayoutContainer.VerticalLayoutData layoutData = new VerticalLayoutContainer.VerticalLayoutData();
      VerticalLayoutContainer.VerticalLayoutData datePickerLayoutData1 = new VerticalLayoutContainer.VerticalLayoutData();
      VerticalLayoutContainer.VerticalLayoutData datePickerLayoutData2 = new VerticalLayoutContainer.VerticalLayoutData();
      datePickerLayoutData1.setMargins(new Margins(30,0,0, 30));
      datePickerLayoutData2.setMargins(new Margins(2,0,0, 30));
      layoutData.setMargins(margins);
      verticalLayoutContainer.add(datePicker, datePickerLayoutData1);
      DatePicker datePicker2 = new DatePicker();
      datePicker2.setWidth(270);
      VerticalLayoutContainer.VerticalLayoutData layoutData1 = new VerticalLayoutContainer.VerticalLayoutData();
      layoutData1.setMargins(margins);
      verticalLayoutContainer.add(datePicker2, datePickerLayoutData2);
//      DatePickerHolder datePickerHolder = new DatePickerHolder();
//      contentPanel.add(datePickerHolder);

      HorizontalLayoutContainer horizontalLayoutContainer = new HorizontalLayoutContainer();
      Label label = new Label("From:");
      Label label2 = new Label("To:");
      ComboBox<TestClient> comboBox2 = new ComboBox<TestClient>(new ListStore<>(countryProps.name()), countryProps.age());
      comboBox2.setWidth(50);
      comboBox2.getStore().add(new TestClient("Dima", 4));
      comboBox2.getStore().add(new TestClient("Ruslan", 4));
      ComboBox<TestClient> comboBox3 = new ComboBox<TestClient>(new ListStore<>(countryProps.name()), countryProps.age());
      comboBox3.setWidth(50);
      comboBox3.getStore().add(new TestClient("Dima", 4));
      comboBox3.getStore().add(new TestClient("Ruslan", 4));
      HorizontalLayoutContainer.HorizontalLayoutData layoutData2 = new HorizontalLayoutContainer.HorizontalLayoutData();
      layoutData2.setMargins(margins);
      horizontalLayoutContainer.add(label, layoutData2);
      horizontalLayoutContainer.add(comboBox2, layoutData2);
      horizontalLayoutContainer.add(label2, layoutData2);
      horizontalLayoutContainer.add(comboBox3, layoutData2);
      verticalLayoutContainer.add(horizontalLayoutContainer);
      contentPanel.add(verticalLayoutContainer);
      accordion.add(contentPanel);
//      accordion.add(new DatePickerHolder());
      accordion.setActiveWidget(contentPanel);

      panel = new ContentPanel();
      panel.setHeading("Accordion Layout");
      panel.setBodyBorder(false);
      panel.add(accordion);
    }

    return panel;
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
