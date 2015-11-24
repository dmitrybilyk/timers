package de.hpfsc.server;


import de.hpfsc.shared.Client;
import de.hpfsc.shared.ClientNamesEnum;
import de.hpfsc.shared.WhoseSessionEnum;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dmitry on 13.07.15.
 */
public class NamesHolder {
  public NamesHolder() {
    for (ClientNamesEnum clientName: ClientNamesEnum.values()) {
      allNames.put(clientName.name(), clientName.name());
    }
    for (WhoseSessionEnum clientName: WhoseSessionEnum.values()) {
      allOwnersNames.put(clientName.name(), clientName.name());
    }
  }

  private Map<String, String> allNames = new LinkedHashMap<String, String>();
  private Map<String, String> usedNames = new LinkedHashMap<String, String>();

  private Map<String, String> allOwnersNames = new LinkedHashMap<String, String>();
  private Map<String, String> usedOwnersNames = new LinkedHashMap<String, String>();

  public Map<String, String> getAllOwnersNames() {
    return allOwnersNames;
  }

  public Map<String, String> getUsedOwnersNames() {
    return usedOwnersNames;
  }

  public List<String> getFreeOwnersNames() {
    List<String> freeNames = new ArrayList<>();
    for (String name: allOwnersNames.keySet()) {
      if (!usedOwnersNames.values().contains(name)) {
        freeNames.add(name);
      }
    }
    return freeNames;
  }


  public Map<String, String> getAllNames() {
    return allNames;
  }

  public Map<String, String> getUsedNames() {
    return usedNames;
  }

  public List<String> getFreeNames() {
    List<String> freeNames = new ArrayList<>();
    for (String name: allNames.keySet()) {
      if (!usedNames.values().contains(name)) {
        freeNames.add(name);
      }
    }
    return freeNames;
  }

}
