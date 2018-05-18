package it.ncorti.emgvisualizer.DTO;

public class Player {
    private String name;
    private String emailid;
    private String password;
    private int age;
    private String gender;
    private String handedness;
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
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getHandedness() {
        return handedness;
    }
    public void setHandedness(String handedness) {
        this.handedness = handedness;
    }
}
