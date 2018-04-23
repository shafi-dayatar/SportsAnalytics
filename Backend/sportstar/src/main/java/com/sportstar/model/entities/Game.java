package com.sportstar.model.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "game")
@NamedQueries({
    @NamedQuery(name="Game.getGameDetails",query="SELECT g FROM Game g WHERE g.gameid= :gameid"),
    @NamedQuery(name="Game.getGames", query="SELECT g FROM Game g WHERE g.player= :player")
})
public class Game implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GAMEID", unique = true, nullable = false)
	private int gameid;
	
	@ManyToOne
	@JoinColumn(name="EMAILID")
	private Player player;
	
	@NotNull(message = "played date must not be null")
	@Column(name = "PLAYEDON")
	private Date playedOn;
	
	@Column(name = "LOCATION")
	private String location;
	
	@NotNull(message = "start time must not be null")
	@Column(name = "STARTTIME")
	private Time startTime;
	
	@Column(name = "ENDTIME")
	private Time endTime;
	
	@Column(name = "TOTALSTROKES")
	private int totalStrokes;
	
	@Column(name = "BACKHANDSLICE")
	private int backhandSlice;
	
	@Column(name = "BACKHANDTOPSPIN")
	private int backhandTopspin;
	
	@Column(name = "FOREHANDTOPSPIN")
	private int forehandTopspin;
	
	@Column(name = "FOREHANDSLICE")
	private int forehandSlice;
	
	@Column(name = "SERVE")
	private int serve;

	public Game(int gameid, Player player, Date playedOn, String location, Time startTime, Time endTime,
			int totalStrokes, int backhandSlice, int backhandTopspin, int forehandTopspin, int forehandSlice,
			int serve) {
		super();
		this.gameid = gameid;
		this.player = player;
		this.playedOn = playedOn;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalStrokes = totalStrokes;
		this.backhandSlice = backhandSlice;
		this.backhandTopspin = backhandTopspin;
		this.forehandTopspin = forehandTopspin;
		this.forehandSlice = forehandSlice;
		this.serve = serve;
	}

	public Game() {
		super();
	}

	public int getGameid() {
		return gameid;
	}

	public void setGameid(int gameid) {
		this.gameid = gameid;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Date getPlayedOn() {
		return playedOn;
	}

	public void setPlayedOn(Date playedOn) {
		this.playedOn = playedOn;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
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
