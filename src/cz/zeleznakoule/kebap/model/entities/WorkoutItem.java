package cz.zeleznakoule.kebap.model.entities;

import java.util.ArrayList;
import java.util.List;

public class WorkoutItem {

	private long Id;
	
	private int Repeats;
	
	private String Note;
	
	private Workout Workout;
	
	private List<Serie> Series;
	
	private Drill Drill;

	public WorkoutItem() {
		super();
		this.Series = new ArrayList<Serie>();
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public int getRepeats() {
		return Repeats;
	}

	public void setRepeats(int repeats) {
		Repeats = repeats;
	}

	public String getNote() {
		return Note;
	}

	public void setNote(String note) {
		Note = note;
	}

	public Workout getWorkout() {
		return Workout;
	}

	public void setWorkout(Workout workout) {
		Workout = workout;
	}

	public List<Serie> getSeries() {
		return Series;
	}

	public void setSeries(List<Serie> series) {
		Series = series;
	}

	public Drill getDrill() {
		return Drill;
	}

	public void setDrill(Drill drill) {
		Drill = drill;
	}
	
}
