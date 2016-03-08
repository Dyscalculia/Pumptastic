package Backend;

import java.sql.SQLException;
import java.util.List;

import utils.Group;
import utils.Workout;
import utils.Exercise;

public interface DBConnect
{
    List<Workout> getWorkouts() throws SQLException;

    Workout getWorkout(int id) throws SQLException;

    void createWorkout(Workout workout);

    List<Group> getGroups(Integer parentId) throws SQLException;

    List<Exercise> getExercises(int parentGroupId) throws SQLException;
}
