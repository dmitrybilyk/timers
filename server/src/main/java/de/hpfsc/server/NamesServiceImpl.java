package de.hpfsc.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hpfsc.web.NamesService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitry on 24.11.15.
 */

public class NamesServiceImpl extends RemoteServiceServlet implements NamesService {

//  NamesHolder ownersHolder = new NamesHolder();
  NamesHolder namesHolder = new NamesHolder();

  @Override
  public List<String> getAllNames() {
    List<String> allNames = new ArrayList<>();
    allNames.addAll(namesHolder.getAllNames().values());
    return allNames;
  }

  @Override
  public List<String> getUsedNames() {
    List<String> usedNames = new ArrayList<>();
    usedNames.addAll(namesHolder.getUsedNames().values());
    return usedNames;
  }

  @Override
  public List<String> getFreeNames() {
    return namesHolder.getFreeNames();
  }

  @Override
  public void saveAllNames(List<String> allNames) {
    for (String name: allNames) {
      namesHolder.getAllNames().put(name, name);
    }
  }

  @Override
  public void saveUsedNames(List<String> usedNames, String oldName) {
    for (String name: usedNames) {
      namesHolder.getUsedNames().put(name, name);
    }
    if (oldName != null) {
      namesHolder.getUsedNames().remove(oldName);
    }
  }


  @Override
  public List<String> getAllOwnersNames() {
    List<String> allNames = new ArrayList<>();
    allNames.addAll(namesHolder.getAllOwnersNames().values());
    return allNames;
  }

  @Override
  public List<String> getUsedOwnersNames() {
    List<String> usedNames = new ArrayList<>();
    usedNames.addAll(namesHolder.getUsedOwnersNames().values());
    return usedNames;
  }

  @Override
  public List<String> getFreeOwnersNames() {
    return namesHolder.getFreeOwnersNames();
  }

  @Override
  public void saveAllOwnersNames(List<String> allNames) {
    for (String name: allNames) {
      namesHolder.getAllOwnersNames().put(name, name);
    }
  }

  @Override
  public void saveUsedOwnersNames(List<String> usedNames, String oldName) {
    for (String name: usedNames) {
      namesHolder.getUsedOwnersNames().put(name, name);
    }
    if (oldName != null) {
      namesHolder.getUsedOwnersNames().remove(oldName);
    }
  }

}
