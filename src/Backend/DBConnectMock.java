package backend;

import java.sql.SQLException;
import java.util.List;

import utils.Group;
import utils.Workout;
import utils.Exercise;

public class DBConnectMock implements DBConnect {

    @Override
    public List<Workout> getWorkouts() throws SQLException {
        return null;
    }

    @Override
    public Workout getWorkout(int id) throws SQLException {
        return null;
    }

    @Override
    public void createWorkout(Workout workout) {

    }

    @Override
    public List<Group> getGroups(Integer parentId) throws SQLException {
        return null;
    }

    @Override
    public List<Exercise> getExercises(int parentGroupId) throws SQLException {
        return null;
    }
}
