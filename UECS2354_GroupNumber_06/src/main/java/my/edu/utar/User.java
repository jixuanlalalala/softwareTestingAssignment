package my.edu.utar;

public class User {
	private String id;
	private String name;
	private String email;
	private String phoneNumber;
	
	public User(String id, String name, String email, String phoneNumber) {
		if(id== null || id.trim().isEmpty())
			throw new IllegalArgumentException("id cannot be empty or null");
		
		if(name== null || name.trim().isEmpty())
			throw new IllegalArgumentException("name cannot be empty or null");

		if(email== null || email.trim().isEmpty())
			throw new IllegalArgumentException("email cannot be empty or null");
		
		if(!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$"))
			throw new IllegalArgumentException("Invalid email format") ;
		
		if(phoneNumber== null || phoneNumber.trim().isEmpty())
			throw new IllegalArgumentException("phoneNumber cannot be empty or null");
		
		if(!phoneNumber.matches("\\d{10,12}") )
			throw new IllegalArgumentException("Invalid phone number format");
		
		this.id = id;
	    this.name = name;
	    this.email = email;
	    this.phoneNumber = phoneNumber;
	}
	// Default constructor
    public User() {
    }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
    
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
    
	@Override
	public String toString() {
	    return id + "|" + name + "|" + email + "|" + phoneNumber;
	}
	
//public class user {
//	private String name;
//	private int id;
//	private String email;
//	private int phoneNum;
//	
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//	
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public int getPhoneNum() {
//		return phoneNum;
//	}
//
//	public void setPhoneNum(int phoneNum) {
//		this.phoneNum = phoneNum;
//	}
//
//	
//	public user() {
//		setName("");
//		id = 0;
//		email = "";
//		phoneNum = 0;
//	}
//	
//	public user(String name, int id, String email, int phone) {
//		name = this.name;
//		id = this.id;
//		email = this.email;
//		phoneNum = phone;
//	}
//
//	public String toString(String aName, int aId, String aEmail, int aPhone) {
//		user newUser = new user(aName, aId, aEmail, aPhone);
//		return "User [Name= "+ aName+ "\n ID= "+ aId+"\n Email= "+ aEmail+ "\n Phone number= "+ aPhone;
//	}
	
}
