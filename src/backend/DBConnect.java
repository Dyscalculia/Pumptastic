package backend;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import utils.Workout;
import utils.Exercise;

public interface DBConnect {
	Workout getWorkout(int id) throws SQLException;

    void insertWorkout(Workout workout) throws SQLException;

	List<Workout> getWorkoutsLabels(Date newerThan) throws SQLException;
	
	Exercise getExercise(String name) throws SQLException;

	List<Exercise> getExercisesLabels(Integer parentGroupId) throws SQLException;

	List<Workout> getWorkouts(Date newerThan) throws SQLException;
	
	Integer getNumberExercises(Date newerThan) throws SQLException;
	
	Integer getSumDuration(Date newerThan) throws SQLException;
	
	Double getAvgDuration(Date newerThan) throws SQLException;
}
