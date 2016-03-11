package utils;

public class ExerciseTrainingInstance implements Comparable<ExerciseTrainingInstance> {



    private  String exerciseName;
    private int sett;
    private int reps;
    private int weight;

    public ExerciseTrainingInstance(String exerciseName, int sett, int reps, int weight) {
        this.exerciseName = exerciseName;
        this.sett = sett;
        this.reps = reps;
        this.weight = weight;
    }

    public String getName(){
        return exerciseName;

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
    public int compareTo(ExerciseTrainingInstance o) {
        return this.getName().equals(o.getName()) ? 0 : 1;
    }
}
