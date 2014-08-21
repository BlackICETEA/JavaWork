package cn.gyyx.testLcg.model;

public class User {
	@Override
	public String toString() {
		return "User [id=" + id + ", role=" + role + ", name=" + name
				+ ", pwd=" + pwd + "]";
	}
	public int id;
	public String role;
	public String name;
	public String pwd;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
