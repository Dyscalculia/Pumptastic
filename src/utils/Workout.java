package utils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Workout {
	private List<Exercise> exercises = new ArrayList<Exercise>();
	private Integer id;
	private Date date;
	private Time time;
	private int duration;
	private Integer performance;
	private String log;
	private String air;
	private int audience;
	private Integer temperature;
	private String weather;
	private Boolean isOutside;
	
	public Workout(Integer id, Date date, Time time) {
		this.id = id;
		this.date = date;
		this.time = time;
	}
	
	public Workout(Integer id, Date date, Time time, int duration, Integer performance, String log) {
		this(id, date, time);
		this.duration = duration;
		this.performance = performance;
		this.log = log;
	}

	public Workout(Integer id, Date date, Time time, int duration, Integer performance, String log, String air, int audience) {
		this(id, date, time, duration, performance, log);
		this.air = air;
		this.audience = audience;
		this.isOutside = false;
	}

	public Workout(Integer id, Date date, Time time, int duration, Integer performance, String log, Integer temperature, String weather) {
		this(id, date, time, duration, performance, log);
		this.temperature = temperature;
		this.weather = weather;
		this.isOutside = true;
	}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public Date getDate() {
		return date;
	}

	public Time getTime() {
		return time;
	}

	public int getDuration() {
		return duration;
	}

	public Integer getPerformance() {
		return performance;
	}

	public String getLog() {
		return log;
	}
	
	public int getId() {
		return id;
	}
	
	public String getAir() {
		return air;
	}

	public int getAudience() {
		return audience;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public String getWeather() {
		return weather;
	}
	
	public Boolean isOutside() {
		return isOutside;
	}
	
	public void setWorkout(Workout workout) {
		date = workout.getDate();
		time = workout.getTime();
		duration = workout.getDuration();
		performance = workout.getPerformance();
		log = workout.getLog();
		exercises = workout.getExercises();
	}
	
	public void addExercise(Exercise exercise) {
		exercises.add(exercise);
	}
}
