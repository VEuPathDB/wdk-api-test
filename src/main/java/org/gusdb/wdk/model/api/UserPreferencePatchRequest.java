package org.gusdb.wdk.model.api;

import java.util.HashMap;
import java.util.Map;

public class UserPreferencePatchRequest {
  
  String action;
  Map<String, String> updates = new HashMap<String, String>();
  
  public UserPreferencePatchRequest(String action) {
    this.action = action;
    
  }
  public String getAction() {
    return action;
  }

  public Map<String, String> getUpdates() {
    return updates;
  }
  public void setUpdates(Map<String, String> updates) {
    this.updates = updates;
  }
  

}
