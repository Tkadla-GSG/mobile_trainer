package cz.zeleznakoule.kebap.model.entities;

import java.util.List;
import java.util.ArrayList;

public class ExcerciseCategory {

	private long Id;
	
	private String Name;
	
	private List<Excercise> Excercises;

	public ExcerciseCategory() {
		super();
		this.Excercises = new ArrayList<Excercise>();
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public List<Excercise> getExcercises() {
		return Excercises;
	}

	public void setExcercises(List<Excercise> excercises) {
		Excercises = excercises;
	}
	
}
