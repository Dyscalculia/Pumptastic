package utils;

public class Exercise implements Comparable<Exercise> {
	private Integer id;
	private String name;
	private String description;
	private int sett;
    private int reps;
    private int weight;
	
	public Exercise(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Exercise(Integer id, String name, String description) {
		this(id, name);
		this.name = name;
		this.description = description;
	}
	
	public Exercise(Integer id, String name, int sett, int reps, int weight) {
		this(id, name);
		this.sett = sett;
		this.reps = reps;
		this.weight = weight;
	}
	
	public Exercise(Integer id, String name, String description, int sett, int reps, int weight) {
		this(id, name, sett, reps, weight);
		this.description = description;
	}
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

    public int getSett() {
        return sett;
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
