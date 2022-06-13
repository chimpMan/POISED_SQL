package poise;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * This code creates a class for Contractors
 * <p>
 * 
 * The class extends the Person class
 * 
 * @author Samuel Wendi Kariuki
 * @version 3.1 11 June 2022
 * @see Person
 */
public class Contractor extends Person {

	// constructor
	public Contractor(String firstName, String surname, int personID, long telephone, String email, String address,
			String type) {
		super(firstName, surname, personID, telephone, email, address, type);
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
		return super.getFirstName() + "|" + super.getSurname() + "|" + super.getPersonID() + "|" + super.getTelephone()
				+ "|" + super.getEmail() + "|" + super.getAddress() + "|" + super.getType();
	}

	/**
	 *
	 * method to modify contractor details <br>
	 * User is prompted for a projectID. 
	 * The ID is manipulated to get a desired project. 
	 * Objects in the list are parsed through one at a time.
	 * The object with a matching project number is picked.
	 * Matching project number is picked necessary modification is performed.
	 * 
	 * @param arbitraryList4 List variable to access existing projects
	 *
	 */
	static void modifyContractor(List<Projects> arbitraryList4) {

		int modProject;
		int modchoice;
		String newMail;
		Long newTelephone;

		System.out.println("Enter the project ID of the project containing the contractor you wish to modify");
		modProject = Poised.KEYBOARD.nextInt();

		// updating the database
		try {
			// parsing through the list
			for (int i = 0; i < arbitraryList4.size(); i++) {
				Projects thisProject = arbitraryList4.get(i);

				// matching the user entry with the project number
				if (thisProject.getProjectNumber() == modProject) {
					int desiredProject = i;
					thisProject = arbitraryList4.get(desiredProject);

					Contractor thisContractor = thisProject.getContractor();// new contractor object instantiated
					System.out.println(thisContractor.toString());

					System.out.println("\nmodify: 1. Telephone \n2. Email");// prompting specific change
					modchoice = Poised.KEYBOARD.nextInt();

					// changing telephone
					if (modchoice == 1) {
						System.out.println("Enter the new telephone of the contractor you wish to modify");
						newTelephone = Poised.KEYBOARD.nextLong();
						thisContractor.setTelephone(newTelephone);
						Jdbc.statement.executeUpdate("update contractor set telephone='" + newTelephone+ "' where projectNum='" + modProject+"'");

						// changing mail
					} else if (modchoice == 2) {
						Poised.KEYBOARD.nextLine();
						System.out.println("Enter the new Email of the contractor you wish to modify");
						newMail = Poised.KEYBOARD.nextLine();
						thisContractor.setEmail(newMail);
						Jdbc.statement.executeUpdate(
								"update contractor set email='" + newMail + "' where projectNum='" + modProject + "'");
					}

					System.out.println((thisProject.getContractor()));
				} else {
					continue;
				}
			}
		} catch (SQLException e) {
			Poised.logger.log(Level.WARNING, "Incorrect choice");
			e.printStackTrace();
		}
		}
	}
