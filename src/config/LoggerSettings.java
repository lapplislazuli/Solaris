package config;

import org.json.JSONObject;
import org.pmw.tinylog.Level;
public class LoggerSettings {
	//public enum LogLevel{
	//	ERROR,WARN,INFO,DEBUG,TRACE
	//}
	
	public Level level;
	public String logfile;
	public boolean append; //Appends to the existing logfile if true, else write new one
	
	public LoggerSettings(JSONObject configJSON) {
		level = tryParseLogLevel(configJSON.getString("level"));
		logfile = configJSON.getString("logfile");
		append = configJSON.getBoolean("append");
	}
	
	private Level tryParseLogLevel(String s){
		String normed = s.toLowerCase(); //TODO: CLear Whitespaces
		switch (normed){
			case "error":	return Level.ERROR;
			case "warn": 	return Level.WARNING;
			case "info": 	return Level.INFO;
			case "debug": 	return Level.DEBUG;
			case "trace": 	return Level.TRACE;
		}
		return Level.ERROR; //Default is highest loglevel
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("level", level.toString()); //Should work
		obj.put("logfile",logfile);
		obj.put("append", append);
		return obj;
	}
}
