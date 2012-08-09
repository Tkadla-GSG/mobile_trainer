package cz.zeleznakoule.kebap.model.entities;

public class Excercise {

	private long Id;
	
	private String Name;
	
	private String Tags;
	
	private boolean Starred;
	
	private ExcerciseCategory ExcerciseCategory;

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

	public String getTags() {
		return Tags;
	}

	public void setTags(String tags) {
		Tags = tags;
	}

	public boolean isStarred() {
		return Starred;
	}

	public void setStarred(boolean starred) {
		Starred = starred;
	}

	public ExcerciseCategory getExcerciseCategory() {
		return ExcerciseCategory;
	}

	public void setExcerciseCategory(ExcerciseCategory excerciseCategory) {
		ExcerciseCategory = excerciseCategory;
	}

	@Override
	public String toString() {
		return Name;
	}
}
