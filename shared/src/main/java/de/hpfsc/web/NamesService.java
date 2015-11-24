package de.hpfsc.web;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

/**
 * Created by dmitry on 13.07.15.
 */
@RemoteServiceRelativePath("names")
public interface NamesService extends RemoteService{
  public List<String> getAllNames();
  public List<String> getUsedNames();
  public List<String> getFreeNames();

  void saveAllNames(List<String> allNames);

  void saveUsedNames(List<String> usedNames, String oldName);

  List<String> getAllOwnersNames();

  List<String> getUsedOwnersNames();

  List<String> getFreeOwnersNames();

  void saveAllOwnersNames(List<String> allNames);

  void saveUsedOwnersNames(List<String> usedNames, String oldName);
}
