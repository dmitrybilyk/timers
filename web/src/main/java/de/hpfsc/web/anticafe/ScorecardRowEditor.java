package de.hpfsc.web.anticafe;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.RowEditorEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.RowEditor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Element;
//import cz.zoom.scorecard.webui.client.i18n.ScorecardConstants;
//import cz.zoom.scorecard.webui.client.utils.ObjectIds;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ScorecardRowEditor<M extends ModelData> extends RowEditor<M> {
//  private static final ScorecardConstants i18nConstants = GWT.create(ScorecardConstants.class);

  private Set<Field> disabledToolTips = new HashSet<Field>();
  private M newRow; // deleted if changes are discarded

  public Button getSaveButton() {
    return saveBtn;
  }

  public void setIds() {
//    this.setId(ObjectIds.SCORECARDROWEDITOR_ID);
//    saveBtn.setId(ObjectIds.SCORECARDROWEDITOR_SAVE_BUTTON_ID);
//    cancelBtn.setId(ObjectIds.SCORECARDROWEDITOR_CANCEL_BUTTON_ID);
  }

  public void stopEditing(boolean saveChanges) {
    if (disabled || !isEditing()) {
      return;
    }
    modifiedStopEditing(saveChanges);

    if (newRow != null && !saveChanges) {
      grid.getStore().remove(newRow);
    }
    newRow = null;
  }

  // modified stopEditing from the RowEditor class, but without clearing the combobox
  // delete as soon as gxt provides a new release with the fix
  private void modifiedStopEditing(boolean saveChanges) {
    if (disabled || !isEditing()) {
      return;
    }
    setEditing(false);

    Map<String, Object> data = new FastMap<Object>();
    boolean hasChange = false;
    ColumnModel cm = grid.getColumnModel();
    Record record = grid.getStore().getRecord(getModel());
    for (int i = 0, len = cm.getColumnCount(); i < len; i++) {
      if (!cm.isHidden(i)) {
        Component c = getItem(i);
        if (c instanceof LabelField) {
          continue;
        } else if (c instanceof Field<?>) {
          Field<?> f = (Field<?>) c;

          // store may be filtered from previous edit
          if (f instanceof ComboBox<?>) {
            ((ComboBox<?>) f).getStore().clearFilters();
          }

          String dindex = cm.getDataIndex(i);
          Object oldValue = record.get(dindex);

          CellEditor ed = cm.getEditor(i);
          Object value = ed != null ? ed.postProcessValue(f.getValue()) : f.getValue();
          if ((oldValue == null && value != null) || (oldValue != null && !oldValue.equals(value))) {
            data.put(dindex, value);
            hasChange = true;
          }

          /* gxt problem: it clears the value of the combobox, which causes trouble. see:
             http://www.sencha.com/forum/showthread.php?291105-GXT-2.3.1a-In-RowEditor-stopEditing-ComboBoxes-are-invalid&s=61d7b71f92d1aaa4efcf9d50fd7bdfff

          // clear current value
          if (f instanceof ComboBox<?>) {
            ((ComboBox<?>) f).clear();
          }
          */
        }
      }
    }
    RowEditorEvent ree = new RowEditorEvent(this, rowIndex);
    ree.setRecord(record);
    ree.setChanges(data);

      record.beginEdit();
      setValues(data, record);
      record.endEdit();
      fireEvent(Events.AfterEdit, ree);
    hide();
  }

  protected void setValues(Map<String, Object> data, Record record) {
    for (String k : data.keySet()) {
      record.set(k, data.get(k));
    }
  }

  @Override
  protected void onRender(Element target, int index) {
    super.onRender(target, index);
    setIds();
  }

  @Override
  public void startEditing(int rowIndex, boolean doFocus) {
    startEditing(rowIndex, doFocus ? 0 : null);
  }

  public void startEditing(int rowIndex, Integer focusColumnIndex) {
    for (Component i : getItems()) {
      Field<?> f = (Field<?>) i;
      if ("".equals(f.getOriginalValue())) {
        f.setOriginalValue(null); // RowEditor treats empty strings as nulls, when checking for dirty fields
      }
    }
    super.startEditing(rowIndex, false);
    if(focusColumnIndex != null) {
      deferFocus(focusColumnIndex);
    }
  }

  /**
   * Add the specified new model to the store and immediately start editing it,
   * as if was completely blank. If changes are not saved, the row will be removed from the store.
   *
   * @param newRow  the new model to {@link ListStore#add(ModelData) add} to the store before editing
   * @param doFocus true to focus the field
   * @see #startEditing(int, boolean)
   * @see #startEditingNew(int, boolean)
   */
  public void startEditingNew(final M newRow, boolean doFocus) {
    startEditingNew(newRow, grid.getStore().getCount(), doFocus);
  }

  /**
   * Add the specified new model to the store at the specified index and immediately start editing it,
   * as if was completely blank. If changes are not saved, the row will be removed from the store.
   *
   * @param newRow   the new model to add to the store before editing
   * @param rowIndex the index to {@link ListStore#insert(ModelData, int) insert} new row at
   * @param doFocus  true to focus the field
   * @see #startEditing(int, boolean)
   * @see #startEditingNew(int, boolean)
   */
  public void startEditingNew(final M newRow, int rowIndex, boolean doFocus) {
    startEditingNew(newRow, rowIndex, doFocus? 0 : null);
  }

  public void startEditingNew(final M newRow, int rowIndex, final Integer focusColumnIndex) {
    grid.getStore().insert(newRow, rowIndex);
    // trigger later to avoid collisions with events that call stopEditing
    Scheduler.get().scheduleEntry(new Command() {
      @Override
      public void execute() {
        startEditingNew(grid.getStore().indexOf(newRow), false);
        if(focusColumnIndex != null) {
          deferFocus(focusColumnIndex);
        }
      }
    });
  }

  /**
   * Starts editing the specified row as if it was a new row.
   * If changes are not saved, it will be deleted from the store.
   *
   * @param rowIndex the row to edit
   * @param doFocus  true to focus the field
   * @see #startEditing(int, boolean)
   */
  private void startEditingNew(int rowIndex, boolean doFocus) {
    startEditing(rowIndex, doFocus);
    newRow = getModel();
    if (newRow == null) {
      return;
    }
    if (!isEditing()) { // e.g., BeforeEdit event was cancelled
      grid.getStore().remove(newRow); // cleanup the added model, if any
      newRow = null;
      return;
    }
    // make sure fields are dirty
    for (Component i : getItems()) {
      Field<?> f = (Field<?>) i;
      f.setOriginalValue(null);
      f.clearInvalid();
    }
  }

  @Override
  public void onComponentEvent(ComponentEvent ce) {
    super.onComponentEvent(ce);
  }

  @Override
  protected void onEnter(ComponentEvent ce) {
    // ignore - don't stop editing
  }

  protected boolean isDirty() {
    if (isEditingNew()) {
      return !isValid();
    }
    return super.isDirty();
  }

  /**
   * @return true if editing a new row that should be removed on cancelled edit; false otherwise
   */
  public boolean isEditingNew() {
    return newRow != null;
  }

  public M getModel() {
    return grid.getStore().getAt(rowIndex);
  }

  /**
   * SC-2020 - localization for Save / Close buttons (Add Media in EvaluationPlanner )
   */
  @Override
  public RowEditorMessages getMessages() {
    return new RowEditorMessages() {
      @Override
      public String getCancelText() {
        return "Cancel";
      }

      @Override
      public String getSaveText() {
        return "Save";
      }
    };
  }

  /**
   * If the editors messageTarget is "side" or "title", change it to "tooltip". Other values are not affected.
   * Super changes everything to "tooltip", making it impossible to turn the message off.
   */
  @Override
  protected void initFields() {
    super.initFields();
    ColumnModel cm = grid.getColumnModel();
    for (int i = 0, len = cm.getColumnCount(); i < len; i++) {
      ColumnConfig c = cm.getColumn(i);
      CellEditor ed = c.getEditor();
      if (ed == null) {
        ed = new CellEditor(new LabelField());
        c.setEditor(ed);
      }
      Field<?> f = ed.getField();
      if (disabledToolTips.contains(f)) {
        f.setMessageTarget("none");
      }
    }
  }

  public void disableValidationToolTip(Field editor) {
    disabledToolTips.add(editor);
  }

  protected void positionButtons() {
    if (isRendered()) {
      super.positionButtons();
    }
  }

  public native void setEditing(boolean editing) /*-{
      this.@com.extjs.gxt.ui.client.widget.grid.RowEditor::editing = editing;
  }-*/;


}
