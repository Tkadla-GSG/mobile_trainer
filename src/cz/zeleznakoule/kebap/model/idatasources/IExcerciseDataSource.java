package cz.zeleznakoule.kebap.model.idatasources;

import java.util.List;

import cz.zeleznakoule.kebap.model.entities.Excercise;
import android.database.SQLException;

public interface IExcerciseDataSource {

	public void open() throws SQLException;
	
	public void close();
	
	public Excercise create(String name);
	
	public void delete(Excercise excercise);
	
	public List<Excercise> getAll();
}
