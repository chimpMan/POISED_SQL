package poise;

/**
 * This code creates a class for persons
 * <p>
 * 
 * @author Samuel Wendi Kariuki
 * @version 3.1 11 June 2022
 */
public class Person {
	// attributes
	private String firstName;
	private String surname;
	private int personId;
	private long telephone;
	private String email;
	private String address;
	private String type;

	// methods
	public Person(String firstName, String surname, int personID, long telephone, String email, String address,
			String type) {
		this.firstName = firstName;
		this.surname = surname;
		this.personId = personID;
		this.telephone = telephone;
		this.email = email;
		this.address = address;
		this.type = type;
	}

	// getters and setters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getPersonID() {
		return personId;
	}

	public void setPersonID(int personID) {
		this.personId = personID;
	}

	public long getTelephone() {
		return telephone;
	}

	public void setTelephone(long telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 *
	 * toString method <br>
	 * The method presents the class information on the console.
	 *
	 * @return output String variable containing all the information of the class
	 */
	@Override
	public String toString() {
		String output = ("\nFirstName: " + firstName);
		output += ("\nSurname: " + surname + "\n" +getType()+ " ID: " + personId + "\n" + "Telephone: " + telephone);
		output += ("\nEmail: " + email + "\n" + "Address: " + address + "\n" + "Type: " + type + "\n");
		return output;
	}

    /**
     *
     * method to set a persons details <br>
     * The method prints series of prompts message on the console. 
     * It also allows for entry of user values. 
     * Inputs are manipulated to generate a Person object.
     * The object is printed to screen.
     *
     * @param arbitraryList List variable to store Person objects
     * @return A string containing the type value of a person
     */
    public static String[] setPersonDetails(String type) {
    
    	System.out.println("Enter the first name of the " + type);
    	String fname = Poised.KEYBOARD.nextLine();
    
    	System.out.println("Enter the second name of the " + type);
    	String sname = Poised.KEYBOARD.nextLine();
    
    	String fullname = String.format("%s %s", fname, sname);
    
    	System.out.println("Enter the telephone of " + fullname + " Enter a 9 digit number: 7xxxxxxxx");
    	long telephone = Poised.KEYBOARD.nextLong();
    	String telephoneString = Long.toString(telephone);
    
    	System.out.println("Enter the "+ type+" ID of " + fullname);
		int personId = Poised.KEYBOARD.nextInt();
    	String typeIdString = Integer.toString(personId);
    
    	Poised.KEYBOARD.nextLine();
    
    	System.out.println("Enter the email of  " + fullname);
    	String email = Poised.KEYBOARD.nextLine();
    
    	System.out.println("Enter the address of " + fullname);
    	String address = Poised.KEYBOARD.nextLine();
    
    	// storing the details in an array
    	String[] contentsArray = new String[7];
    	contentsArray[0] = fname;
    	contentsArray[1] = sname;
    	contentsArray[2] = type;
    	contentsArray[3] = telephoneString;
    	contentsArray[4] = email;
    	contentsArray[5] = address;
    	contentsArray[6] = typeIdString;
    
    	return contentsArray;
    }
    
}
