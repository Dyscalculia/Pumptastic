package utils;

import java.sql.Date;

public class Workout {
	
	//Simple constructor for objects used in listing of training objects
	public Workout(int id, Date date, Time time) {
		/*For now I just use a very simple Time class I made a while back that maintain the format hour:minute
		because it looks like we are not using Unix-time which account for both date in one (this is what java.sql.Time seems to use)*/
	}

	public Workout(int id, Date date, Time time, int duration, int performance, String log) {
	}
}
