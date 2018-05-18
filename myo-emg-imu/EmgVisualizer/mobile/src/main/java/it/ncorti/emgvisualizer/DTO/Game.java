package it.ncorti.emgvisualizer.DTO;

import java.sql.Date;
import java.sql.Time;


public class Game {
	private int gameid;
	private String emailid;
	private String playedOn;
	private String location;
	private String startTime;
	private String endTime;
	private int totalStrokes;
	private int backhandSlice;
	private int backhandTopspin;
	private int forehandTopspin;
	private int forehandSlice;
	private int serve;
	public Game(){}
	public Game(String emailid, String playedOn, String location, String startTime) {
		super();
		this.emailid = emailid;
		this.playedOn = playedOn;
		this.location = location;
		this.startTime = startTime;
	}
	public int getGameid() {
		return gameid;
	}
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getPlayedOn() {
		return playedOn;
	}
	public void setPlayedOn(String playedOn) {
		this.playedOn = playedOn;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getTotalStrokes() {
		return totalStrokes;
	}
	public void setTotalStrokes(int totalStrokes) {
		this.totalStrokes = totalStrokes;
	}
	public int getBackhandSlice() {
		return backhandSlice;
	}
	public void setBackhandSlice(int backhandSlice) {
		this.backhandSlice = backhandSlice;
	}
	public int getBackhandTopspin() {
		return backhandTopspin;
	}
	public void setBackhandTopspin(int backhandTopspin) {
		this.backhandTopspin = backhandTopspin;
	}
	public int getForehandTopspin() {
		return forehandTopspin;
	}
	public void setForehandTopspin(int forehandTopspin) {
		this.forehandTopspin = forehandTopspin;
	}
	public int getForehandSlice() {
		return forehandSlice;
	}
	public void setForehandSlice(int forehandSlice) {
		this.forehandSlice = forehandSlice;
	}
	public int getServe() {
		return serve;
	}
	public void setServe(int serve) {
		this.serve = serve;
	}
}
