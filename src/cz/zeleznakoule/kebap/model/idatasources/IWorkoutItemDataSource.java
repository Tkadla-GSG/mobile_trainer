package cz.zeleznakoule.kebap.model.idatasources;

import java.util.List;

import cz.zeleznakoule.kebap.model.entities.Drill;
import cz.zeleznakoule.kebap.model.entities.Workout;
import cz.zeleznakoule.kebap.model.entities.WorkoutItem;
import android.database.SQLException;

public interface IWorkoutItemDataSource {

	public void open() throws SQLException;
	
	public void close();
	
	public WorkoutItem create(Drill drill, Workout workout, int repeats, String note);
	
	public void delete(WorkoutItem workoutItem);
	
	public List<WorkoutItem> getAll();
}
