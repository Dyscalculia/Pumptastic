package Backend;

import java.util.List;

public class DBConnectMock implements DBConnect {
    @Override
    public List<Trening> getTreninger() {
        return null;
    }

    @Override
    public Trening getTrening(int id) {
        return null;
    }

    @Override
    public void createTrening(Trening trening) {

    }

    @Override
    public List<Gruppe> getGroups(Integer parentId) {
        return null;
    }

    @Override
    public List<Øvelse> getExercises(int parentGroupId) {
        return null;
    }
}
