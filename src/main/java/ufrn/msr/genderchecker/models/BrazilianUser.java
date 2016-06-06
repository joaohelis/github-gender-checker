package ufrn.msr.genderchecker.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="brazilian_user")
public class BrazilianUser {
	
	@Id
	private Integer id;
	private String name;
	private String email;
	private String login;
	private String location;
	private Integer followers;
	private String company;
	private String gender;
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getFollowers() {
		return followers;
	}
	public void setFollowers(Integer followers) {
		this.followers = followers;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "BrazilianUser [id=" + id + ", name=" + name + ", login=" + login + ", followers=" + followers
				+ ", gender=" + gender + "]";
	}
}
