package utils;

public class Exercise implements Comparable<Exercise> {
	private Integer id;
	private String name;
	private String descrition;
	private int set;
    private int reps;
    private int weight;
	
	public Exercise(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Exercise(Integer id, String name, String description) {
		this(id, name);
		this.name = name;
		this.descrition = description;
	}
	
	public Exercise(Integer id, String name, int set, int reps, int weight) {
		this(id, name);
		this.set = set;
		this.reps = reps;
		this.weight = weight;
	}
	
	public Exercise(Integer id, String name, String description, int set, int reps, int weight) {
		this(id, name, set, reps, weight);
		this.descrition = description;
	}
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescrition() {
		return descrition;
	}

    public int getSet() {
        return set;
    }

    public int getReps() {
        return reps;
    }

    public int getWeight(){
        return weight;
    }

    @Override
    public int compareTo(Exercise o) {
        return this.getName().equals(o.getName()) ? 0 : 1;
    }
}
