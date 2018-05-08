package com.sportstar.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.sportstar.model.Gender;
import com.sportstar.model.Handedness;

@Entity
@Table(name = "player")
@NamedQueries({
    @NamedQuery(name="Player.getPlayerDetails",query="SELECT p FROM Player p WHERE p.emailid= :emailid"),
    
    @NamedQuery(name="Player.checkPlayer",query="SELECT p FROM Player p WHERE p.emailid= :emailid AND p.password = :password")

})
public class Player implements Serializable{
	
	@NotNull(message = "Player Name must not be null")
	@Column(name = "NAME")
	private String name;

    @Id
	@NotNull(message = "emailid must not be null")
	@Column(name = "EMAILID")
	private String emailid;

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotNull(message = "password must not be null")
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "AGE")
	private int age;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER")
	private Gender gender;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "HANDEDNESS")
	private Handedness handedness;

	public Player() {
		super();
	}

	public Player(String name, String emailid, String password, int age, Gender gender, Handedness handedness) {
		super();
		this.name = name;
		this.emailid = emailid;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.handedness = handedness;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Handedness getHandedness() {
		return handedness;
	}

	public void setHandedness(Handedness handedness) {
		this.handedness = handedness;
	}
}
