package versioned.host.exp.exponent.modules.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import host.exp.expoview.BuildConfig;

class NotificationActionCenter {

  private static final String SHARED_PREFERENCES_FILE = "com.expo.notification.action" + BuildConfig.APPLICATION_ID;
  private static final String KEY_TEXT_REPLY = "notification_remote_input";

  public synchronized static void put(String categoryId, ArrayList<HashMap<String, Object>> actions, Context context) { // collapse textInput
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    TreeSet<String> actionIds = new TreeSet<>();

    for(HashMap<String, Object> action: actions) {
      String actionId = (String)action.get("actionId");
      actionIds.add(actionId);

      TreeSet<String> actionParams = new TreeSet<>();
      for(Map.Entry<String, Object> param : action.entrySet()) {
        String actionParam = param.getKey() + ":"  + (String)param.getValue();
        actionParams.add(actionParam);
      }

      editor.putStringSet(actionId, actionParams);
    }

    editor.putStringSet(categoryId, actionIds);

    editor.apply();
  }

  public synchronized static void setCategory(String categoryId, NotificationCompat.Builder builder, Context context, IntentProvider intentProvider) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
    Set<String> actions = sharedPreferences.getStringSet(categoryId, null);

    for(String actionId : actions) {
      addAction(builder, sharedPreferences.getStringSet(actionId, null), intentProvider);
    }
  }

  private static void addAction(NotificationCompat.Builder builder, Set<String> actionSet, IntentProvider intentProvider) {
    TreeMap<String, String> action = new TreeMap<>();
    Intent intent = intentProvider.provide();

    for(String param : actionSet) {
      String[] entry = param.split(":");
      String key = entry[0];
      String value = entry[1];
      action.put(key, value);
    }

    NotificationCompat.Action.Builder actionBuilder = new NotificationCompat.Action.Builder(expo.core.R.drawable.notification_icon, (String)action.get("buttonTitle"));

    if (((String)action.get("isRemote")).equals("true")) {
      RemoteInput.Builder remoteInputBuilder = new RemoteInput.Builder(KEY_TEXT_REPLY);
      if (action.containsKey("placeholder")) {
        remoteInputBuilder.setLabel((String)action.get("placeholder"));
      }
      RemoteInput remoteInput = remoteInputBuilder.build();
      actionBuilder.addRemoteInput(remoteInput);
    }

    NotificationCompat.Action notificationAction = actionBuilder.build();
    builder.addAction(notificationAction);
  }

}
