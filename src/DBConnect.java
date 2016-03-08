import java.util.List;

public interface DBConnect
{
    List<Trening> getTreninger();

    Trening getTrening(int id);

    void createTrening(Trening trening);

    List<Gruppe> getGroups(Integer parentId);

    List<Øvelse> getExercises(int parentGroupId);
}
