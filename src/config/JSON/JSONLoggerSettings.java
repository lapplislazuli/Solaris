package config.JSON;

import org.json.JSONObject;
import org.pmw.tinylog.Level;

import config.interfaces.LoggerSettings;
public class JSONLoggerSettings implements LoggerSettings {
	
	private Level level;
	private String logfile;
	private boolean append; //Appends to the existing logfile if true, else write new one
	
	public JSONLoggerSettings(JSONObject configJSON) {
		level = tryParseLogLevel(configJSON.getString("level"));
		logfile = configJSON.getString("logfile");
		append = configJSON.getBoolean("append");
	}
	
	public static Level tryParseLogLevel(String s){
		String normed = s.toLowerCase().trim();
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

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
	
	public void setLevel(String level) {
		this.level = tryParseLogLevel(level);
	}

	public String getLogfile() {
		return logfile;
	}

	public void setLogfile(String logfile) {
		this.logfile = logfile;
	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}
}
