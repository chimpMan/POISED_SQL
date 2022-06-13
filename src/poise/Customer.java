package poise;

/**
 * This code creates a class for Customers
 * <p>
 * 
 * The class extends the Person class
 * 
 * @author Samuel Wendi Kariuki
 * @version 3.1 11 June 2022
 * @see Person
 */
public class Customer extends Person{

	// Constructor
	public Customer(String firstName, String surname, int personID, long telephone, String email, String address,
			String type) {
		super(firstName, surname, personID, telephone, email, address, type);
	}
    

	/**
	 *
	 * toString method<br>
	 * The method presents the class information on the console.
	 *
	 * @return a String variable containing all the information of the class
	 */
	@Override
	public String toString() {
		return super.getFirstName() + "|" + super.getSurname() + "|" + super.getPersonID() + "|" + super.getTelephone()
				+ "|" + super.getEmail() + "|" + super.getAddress() + "|" + super.getType();
	}
	
	

}
