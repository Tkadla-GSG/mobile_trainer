package cz.zeleznakoule.kebap.model.entities;

public class Serie {

	private long Id;
	
	private int Weight;
	
	private int Duration;
	
	private int RestDuration;
	
	private int Breaths;
	
	private WorkoutItem WorkoutItem;
	
	private Excercise Excercise;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public int getWeight() {
		return Weight;
	}

	public void setWeight(int weight) {
		Weight = weight;
	}

	public int getDuration() {
		return Duration;
	}

	public void setDuration(int duration) {
		Duration = duration;
	}

	public int getRestDuration() {
		return RestDuration;
	}

	public void setRestDuration(int restDuration) {
		RestDuration = restDuration;
	}

	public int getBreaths() {
		return Breaths;
	}

	public void setBreaths(int breaths) {
		Breaths = breaths;
	}

	public WorkoutItem getWorkoutItem() {
		return WorkoutItem;
	}

	public void setWorkoutItem(WorkoutItem workoutItem) {
		WorkoutItem = workoutItem;
	}

	public Excercise getExcercise() {
		return Excercise;
	}

	public void setExcercise(Excercise excercise) {
		Excercise = excercise;
	}
	
}
