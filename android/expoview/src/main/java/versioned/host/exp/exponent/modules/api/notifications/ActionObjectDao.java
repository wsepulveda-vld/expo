package versioned.host.exp.exponent.modules.api.notifications;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

public interface ActionObjectDao {
  @Query("SELECT * FROM actions where action_id = (:id) LIMIT 1")
  ActionObject findById(String id);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertActions(ActionObject... actionObjects);
}
