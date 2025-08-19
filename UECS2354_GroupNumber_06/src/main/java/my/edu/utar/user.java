package my.edu.utar;

public class user {
	private String name;
	private int id;
	private String email;
	private int phoneNum;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(int phoneNum) {
		this.phoneNum = phoneNum;
	}

	
	public user() {
		setName("");
		id = 0;
		email = "";
		phoneNum = 0;
		
	}
	
	public user(String name, int id, String email, int phone) {
		name = this.name;
		id = this.id;
		email = this.email;
		phoneNum = phone;
	}

	public String toString(String aName, int aId, String aEmail, int aPhone) {
		user newUser = new user(aName, aId, aEmail, aPhone);
		return "User [Name= "+ aName+ "\n ID= "+ aId+"\n Email= "+ aEmail+ "\n Phone number= "+ aPhone;
	}
	
}
