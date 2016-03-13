package backend;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.Time;
import utils.Workout;
import utils.Exercise;

public class JavaToSQL implements DBConnect {
	private Statement statement;
	private String url;
	private String user;
	private String password;
	
	public JavaToSQL() {
		try {
			Connection connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/** Saves a Workout to database
	 * 
	 * @param workout			A Workout object
	 * @throws SQLException 
	 */
	@Override
	public void insertWorkout(Workout workout) throws SQLException {
		Workout w = workout;
		int id;
		String query = formatInsertQuery("Trening", "dato, tidpunkt, vargihet, form, log" , w.getDate() + ", " + w.getTime() + ", " + w.getDuration() + ", " + w.getPerformance() + ", " + w.getLog());
		statement.executeUpdate(query);
		ResultSet generatedKeys = statement.getGeneratedKeys();
        id = generatedKeys.getInt("id");
		if (workout.isOutside() != null) {
			if (workout.isOutside()) {
				query = formatInsertQuery("Utendors", null , id + ", " + w.getTemperature() + ", " + w.getWeather());
				statement.executeUpdate(query);
			}
			else {
				query = formatInsertQuery("Innendors", null , id + ", " + w.getAir() + ", " + w.getAudience());
				statement.executeUpdate(query);
			}
		}
		List<Exercise> exercises = workout.getExercises();
		if (exercises != null) {
			for (Exercise exercise : exercises) {
				Integer eId = exercise.getId();
				if (eId != null) {
					query = formatInsertQuery("ovelserITrening", null , id + ", " + exercise.getId());
					statement.executeUpdate(query);
				}
			}
		}
	}
	
	/** Gives a list of Workout objects only containing id, date and time
	 * 
	 * @param Date				An SQL Date indicating that any work-out before given date should not be included (null for any work-out)
	 * @return					A List<Workout> if database contains work-outs, an empty list otherwise
	 */
	@Override
	public List<Workout> getWorkoutsLabels(Date newerThan) throws SQLException {
		String where = newerThan == null ? null : "dato >= '" + newerThan + "'";
		String query = formatGetQuery("id, dato, tidspunkt", "Treninger", where, "dato DESC");
		ResultSet results = statement.executeQuery(query);
		List<Workout> workouts = new ArrayList<Workout>();
		while (results.next()) {
			workouts.add(new Workout(results.getInt("id"), results.getDate("date"), new Time(results.getString("time"))));
		}
		return workouts;
	}
	
	/** Gives a list of complete Workout objects containing every attribute as well as a list of exercises
	 * 
	 * @param Date				An SQL Date indicating that any work-out before given date should not be included (null for any work-out)
	 * @return					A List<Workout> if database contains work-outs, an empty list otherwise
	 */
	@Override
	public List<Workout> getWorkouts(Date newerThan) throws SQLException {
		List<Workout> workouts = getWorkoutsLabels(newerThan);
		for (Workout workout: workouts) {
			workout.setWorkout(getWorkout(workout.getId()));
		}
		return workouts;
	}
	
	/** Gives a complete Workout object containing every attribute as well as a list of exercises
	 * 
	 * @return					A Workout object if database contains a work-out with given id
	 */
	@Override
	public Workout getWorkout(int id) throws SQLException {
		String query = formatGetQuery("*", "Treninger", "id = '" + id + "'", null);
		ResultSet results = statement.executeQuery(query);
		Workout workout = new Workout(results.getInt("id"), results.getDate("dato"), new Time(results.getString("tidspunkt")), results.getInt("varighet"), results.getInt("form"), results.getString("log"));
		query = formatGetQuery("id, navn, beskrivelse, gruppeId", "ovelser, ovelserITrening", "id = ovelseId AND treningsId = '" + id + "'", null);
		results = statement.executeQuery(query);
		while (results.next()) {
			workout.addExercise(new Exercise(results.getInt("id"), results.getString("navn"), results.getString("beskrivelse"), results.getInt("gruppeId")));
		}
		return workout;
	}
	
	/** Gives a list of Exercise objects only containing id and name
	 * 
	 * @param parentGroupId		An id indicating which group exercise belong to (null if it belongs to no group)
	 * @return					A List<Exercise> if database contains exercises with given parentGroupid, empty list otherwise
	 */
	@Override
	public List<Exercise> getExercisesLabels(Integer parentGroupId) throws SQLException {
		String where = parentGroupId == null ? null : "gruppeId = '" + parentGroupId + "'";
		String query = formatGetQuery("id, navn", "Øvelse", where, null);
		ResultSet results = statement.executeQuery(query);
		List<Exercise> exercises = new ArrayList<Exercise>();
		while (results.next()) {
			exercises.add(new Exercise(results.getInt("id"), results.getString("navn")));
		}
		return exercises;
	}
	
	/** Gives a complete Exercise object
	 * 
	 * @param name				An name for the exercise
	 * @return					The first exercise it finds with the given name
	 */
	@Override
	public Exercise getExercise(String name) throws SQLException {
		String query = formatGetQuery("*", "ovelser", "navn = " + name, null);
		ResultSet results = statement.executeQuery(query);
		return new Exercise(results.getInt("id"), results.getString("navn"), results.getString("beskrivelse"), results.getInt("gruppeId"));
	}
	
	/** Gives total number exercises from given date to the present
	 * 
	 * @param newerThan			An SQL Date indicating that any work-out before given date should not be counted (null for any work-out)
	 * @return					An integer for the number of exercises
	 */
	public Integer getNumberExercises(Date newerThan) throws SQLException {
		String where = newerThan == null ? "" : " AND dato >= '" + newerThan + "'";
		String query = formatGetQuery("COUNT(*)", "ovelser AS O, ovelserITrening, Trening AS T", "O.id = ovelseId AND T.id = treningsId" + where, null);
		ResultSet results = statement.executeQuery(query);
		return results.getInt(0);
	}
	
	/** Gives the sum of time spent on all work-outs from given date to the present
	 * 
	 * @param newerThan			An SQL Date indicating that any work-out before given date should not be calculated (null for any work-out)
	 * @return					An integer representing the sum for the duration of all defined work-outs
	 */
	public Integer getSumDuration(Date newerThan) throws SQLException {
		String where = newerThan == null ? null : "dato >= '" + newerThan + "'";
		String query = formatGetQuery("SUM(varighet)", "Trening", where, null);
		ResultSet results = statement.executeQuery(query);
		return results.getInt(0);
	}
	
	/** Gives the average of time spent on all work-outs from given date to the present
	 * 
	 * @param newerThan			An SQL Date indicating that any work-out before given date should not be calculated (null for any work-out)
	 * @return					A decimal representing the average for the duration of all defined work-outs
	 */
	public Double getAvgDuration(Date newerThan) throws SQLException {
		String where = newerThan == null ? null : "dato >= '" + newerThan + "'";
		String query = formatGetQuery("AVG(varighet)", "Trening", where, null);
		ResultSet results = statement.executeQuery(query);
		return results.getDouble(0);
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
	
	//Not very flexible for now, adjusted to currently needed queries
	private String formatInsertQuery(String table, String definitions, String values) {
		String query = "INSERT INTO " + table + " ";
		if (definitions != null) {
			query += "(" + definitions + ") ";
		}
		query += "VALUES(" + values + ")";
		return query;
	}
}
