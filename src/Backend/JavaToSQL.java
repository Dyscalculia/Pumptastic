package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.Group;
import utils.Time;
import utils.Workout;
import utils.Exercise;

public class JavaToSQL implements DBConnect {
	private Connection connection;
	private String url;
	private String user;
	private String password;
	
	public JavaToSQL() {
		try {
			connection = DriverManager.getConnection(url, user, password);
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public List<Workout> getWorkouts() throws SQLException {
		String query = formatGetQuery("id, dato, tidspunkt", "Treninger", null, "dato DESC");
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(query);
		List<Workout> workouts = new ArrayList<Workout>();
		while (results.next()) {
			workouts.add(new Workout(results.getInt("id"), results.getDate("date"), new Time(results.getString("time"))));
		}
		return workouts;
	}
	
	@Override
	public Workout getWorkout(int id) throws SQLException {
		String query = formatGetQuery("*", "Treninger", "id = '" + id + "'", null);
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(query);
		return new Workout(results.getInt("id"), results.getDate("date"), new Time(results.getString("time")), results.getInt("duration"), results.getInt("performance"), results.getString("log"));
	}

	@Override
	public void createWorkout(Workout workout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Group> getGroups(Integer parentId) throws SQLException {
		String where = parentId == null ? "parentGruppeId IS NULL" : "parentGruppeId = '" + parentId + "'";
		String query = formatGetQuery("id, navn", "Gruppe", where, null);
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(query);
		List<Group> groups = new ArrayList<Group>();
		while (results.next()) {
			groups.add(new Group(results.getInt("id"), results.getString("navn")));
		}
		return groups;
	}

	@Override
	public List<Exercise> getExercises(int parentGroupId) throws SQLException {
		String query = formatGetQuery("id, navn", "Øvelse", "gruppeId = '" + parentGroupId + "'", null);
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(query);
		List<Exercise> exercises = new ArrayList<Exercise>();
		while (results.next()) {
			exercises.add(new Exercise(results.getInt("id"), results.getString("navn")));
		}
		return exercises;
	}
	
	//Not very flexible for now, adjusted to currently needed queries
	private String formatGetQuery(String select, String from, String where, String sortBy) {
		String query = "SELECT " + select + " ";
		query += "FROM " + from + " ";
		if (where != null) {
			query += "WHERE " + where + " ";
		}
		if (sortBy != null) {
			query += "SORT BY " + sortBy + " ";
		}
		return query;
	}
}
