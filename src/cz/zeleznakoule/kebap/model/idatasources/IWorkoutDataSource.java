package cz.zeleznakoule.kebap.model.idatasources;

import java.util.Date;
import java.util.List;

import cz.zeleznakoule.kebap.model.entities.DayType;
import cz.zeleznakoule.kebap.model.entities.Workout;
import android.database.SQLException;

public interface IWorkoutDataSource {

	public void open() throws SQLException;
	
	public void close();
	
	public Workout create(Date date, int duration, String note, DayType dayType);
	
	public void delete(Workout workout);
	
	public List<Workout> getAll();
}
