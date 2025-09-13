package my.edu.utar;

public class User {
	private String id;
	private String name;
	private String email;
	private String phoneNumber;
	
	public User(String id, String name, String email, String phoneNumber) {
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
		if(name== null || name.trim().isEmpty())
			throw new IllegalArgumentException("name cannot be empty or null");
		
		this.name = name;
	}
	
	
	public String getId() {
		if(id== null || id.trim().isEmpty())
			throw new IllegalArgumentException("id cannot be empty or null");
		
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if(email== null || email.trim().isEmpty())
			throw new IllegalArgumentException("email cannot be empty or null");
		
		if(!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$"))
			throw new IllegalArgumentException("Invalid email format") ;
		
		this.email = email;
	}
    
    
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		if(phoneNumber== null || phoneNumber.trim().isEmpty())
			throw new IllegalArgumentException("phoneNumber cannot be empty or null");
		
		if(!phoneNumber.matches("\\d{10,12}") )
			throw new IllegalArgumentException("Invalid phone number format");
		
		this.phoneNumber = phoneNumber;
	}
    
	@Override
	public String toString() {
	    return id + "|" + name + "|" + email + "|" + phoneNumber;
	}
}
