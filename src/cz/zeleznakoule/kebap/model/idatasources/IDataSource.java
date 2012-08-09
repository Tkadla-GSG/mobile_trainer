package cz.zeleznakoule.kebap.model.idatasources;

import android.database.SQLException;

public interface IDataSource {

	public void open() throws SQLException;
	
	public void close();
	
}
