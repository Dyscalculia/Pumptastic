package utils;

public class Time {
	private Integer hour;
	private Integer minute;
	
	public Time(String time) {
		if (isFormated(time)) {
			String[] parts = time.split(":");
			hour = Integer.parseInt(parts[0]);
			minute = Integer.parseInt(parts[1]);
		}
		else {
			throw new IllegalArgumentException("Time is invalid! Format: hour:minutes:seconds");
		}
	}
	
	public Time(int hour, int minute) {
		if ((hour < 24 && hour >= 0) && (minute > 60 && minute >= 0)) {
			this.hour = hour;
			this.minute = minute;
		}
		else {
			throw new IllegalArgumentException("Time is invalid!");
		}
	}
	
	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	
	public Integer getMinute() {
		return minute;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public int getHour() {
		return hour;
	}

	public int compare(Time aTime) {
		int hourNum = (int) Math.signum(hour - aTime.getHour());
		if (hourNum == 0) {
			return (int) Math.signum(minute - aTime.getMinute());
		}
		return hourNum;
	}
	
	private static boolean isFormated(String time) {
		return time.matches("([01]*?[0-9]|2[0-3]):[0-5]*[0-9]:[0-5]*[0-9]");
	}
}
