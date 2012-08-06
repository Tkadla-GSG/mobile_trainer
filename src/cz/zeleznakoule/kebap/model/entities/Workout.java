package cz.zeleznakoule.kebap.model.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Workout {

	private long Id;
	
	private Date Date;
	
	private int Duration;
	
	private String Note;
	
	private DayType DateType;
	
	private List<WorkoutItem> WorkoutItems;
	
	public Workout() {
		super();
		this.WorkoutItems = new ArrayList<WorkoutItem>();
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public int getDuration() {
		return Duration;
	}

	public void setDuration(int duration) {
		Duration = duration;
	}

	public String getNote() {
		return Note;
	}

	public void setNote(String note) {
		Note = note;
	}

	public DayType getDateType() {
		return DateType;
	}

	public void setDateType(DayType dateType) {
		DateType = dateType;
	}

	public List<WorkoutItem> getWorkoutItems() {
		return WorkoutItems;
	}

	public void setWorkoutItems(List<WorkoutItem> workoutItems) {
		WorkoutItems = workoutItems;
	}
	
	
	
}
