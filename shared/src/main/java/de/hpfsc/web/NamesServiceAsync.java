package de.hpfsc.web;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hpfsc.shared.Client;
import de.hpfsc.shared.WhoseSessionEnum;

import java.util.ArrayList;
import java.util.List;

public interface NamesServiceAsync {

  void getAllNames(AsyncCallback<List<String>> async);

  void getUsedNames(AsyncCallback<List<String>> async);

  void getFreeNames(AsyncCallback<List<String>> async);

  void saveAllNames(List<String> allNames, AsyncCallback<Void> async);

  void saveUsedNames(List<String> usedNames, String oldName, AsyncCallback<Void> async);

  void getAllOwnersNames(AsyncCallback<List<String>> async);

  void getUsedOwnersNames(AsyncCallback<List<String>> async);

  void getFreeOwnersNames(AsyncCallback<List<String>> async);

  void saveAllOwnersNames(List<String> allNames, AsyncCallback<Void> async);

  void saveUsedOwnersNames(List<String> usedNames, String oldName, AsyncCallback<Void> async);
}
