package config;

import org.json.JSONObject;

public class LoggerSettings {
	public enum LogLevel{
		ERROR,WARN,INFO,DEBUG,TRACE
	}
	
	public LogLevel level;
	public String logfile;
	public boolean append; //Appends to the existing logfile if true, else write new one
	
	public LoggerSettings(JSONObject configJSON) {
		level = tryParseLogLevel(configJSON.getString("level"));
		logfile = configJSON.getString("logfile");
		append = configJSON.getBoolean("append");
	}
	
	private LogLevel tryParseLogLevel(String s){
		String normed = s.toLowerCase(); //TODO: CLear Whitespaces
		switch (normed){
			case "error":	return LogLevel.ERROR;
			case "warn": 	return LogLevel.WARN;
			case "info": 	return LogLevel.INFO;
			case "debug": 	return LogLevel.DEBUG;
			case "trace": 	return LogLevel.TRACE;
		}
		return LogLevel.ERROR; //Default is highest loglevel
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("level", level.toString()); //Should work
		obj.put("logfile",logfile);
		obj.put("append", append);
		return obj;
	}
}
