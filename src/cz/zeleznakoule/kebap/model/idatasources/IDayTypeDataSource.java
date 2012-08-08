package cz.zeleznakoule.kebap.model.idatasources;

import java.util.List;

import cz.zeleznakoule.kebap.model.entities.DayType;

public interface IDayTypeDataSource extends IDataSource {
		
	public List<DayType> getAll();
	
}
