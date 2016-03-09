package Backend;

import java.util.List;

import utils.Group;
import utils.Workout;
import utils.Exercise;

public class DBConnectMock implements DBConnect {
    @Override
    public List<Workout> getTreninger() {
        return null;
    }

    @Override
    public Workout getTrening(int id) {
        return null;
    }

    @Override
    public void createTrening(Workout trening) {

    }

    @Override
    public List<Group> getGroups(Integer parentId) {
        return null;
    }

    @Override
    public List<Exercise> getExercises(int parentGroupId) {
        return null;
    }
}
