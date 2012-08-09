package cz.zeleznakoule.kebap.model.idatasources;

import java.util.List;
import cz.zeleznakoule.kebap.model.entities.ExcerciseCategory;

public interface IExcerciseCategoryDataSource extends IDataSource {
		
	public List<ExcerciseCategory> getAll();
	
}
