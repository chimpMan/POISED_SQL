package poise;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This code manages projects for a company called Poised
 * <p>
 * 
 * @author Samuel Wendi Kariuki
 * @version 3.1 11 June 2022
 */

public class Poised {

	/**
	 * Constant value for user input
	 */
	static final Scanner KEYBOARD = new Scanner(System.in);

	/**
	 * logger object
	 */
	static Logger logger = Logger.getLogger(Poised.class.getName());

	public static void main(String[] args) {

		// using a list to store the projects
		List<Projects> projectsList = new ArrayList<>();

		// connect to the sql server
		Jdbc myProject = new Jdbc();
		myProject.createConnection();
		System.out.println("CONNECTION ESTALISHED");

		// populate the list by readProjects method
		if (projectsList.isEmpty()) {
			projectsList = readProjects();
		}

		// main menu
		String menu = """
				Choose an option
				1. Create a project
				2. Edit an existing project
				3. View a project
				4. Change contractor's details
				5. Finalize a project
				6. Exit
				""";

		while (true) { // the main menu will persist until a user exits
			System.out.println(menu);
			int menuSelect = KEYBOARD.nextInt();

			// navigating the menu
			try {
				
				// Create a project
				if (menuSelect == 1) {
					Projects.setProjectDetails(projectsList);

					// modify a project
				} else if (menuSelect == 2) {
					Projects.modifyProject(projectsList);

					// View a project
				} else if (menuSelect == 3) {
					Projects.viewProjects(projectsList);

					// Modify a project's contractor
				} else if (menuSelect == 4) {
					Contractor.modifyContractor(projectsList);

					// Finalize a project
				} else if (menuSelect == 5) {
					finalise(projectsList);

					// exit
				} else if (menuSelect == 6) {
					System.out.println("\nGoodbye!\n");
					break;
					
				} else {
					System.out.println("Your entry is not on the list. Try again");
					Jdbc.results.close();
					Jdbc.statement.close();
					Jdbc.connection.close();
				}

			} catch (InputMismatchException e ) {
				logger.log(Level.WARNING, "Incorrect input");
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}		
	}

	/**
	 *
	 * A Method to finalize projects <br>
	 * User is prompted for a projectID. 
	 * The ID is manipulated to get a desired project. 
	 * Objects in the list are parsed through one at a time.
	 * The object with a matching project number is picked.
	 * Project object is confirmed to be complete or incomplete. 
	 * Completed projects are marked as Finalized and saved in a new table. 
	 * Incomplete projects are invoiced.
	 * 
	 * @param arbitraryList5 List variable to access existing projects
	 *
	 */
	private static void finalise(List<Projects> arbitraryList5) {

		System.out.println("Enter the project ID of the project you wish to finalise");
		int finalProject = KEYBOARD.nextInt();

		try {
			// parsing through each project in the list
			for (int i = 0; i < arbitraryList5.size(); i++) {
				Projects finalisedProject = arbitraryList5.get(i);

				// matching the user entry with project number
				if (finalisedProject.getProjectNumber() == finalProject) {
					int desiredProject = i;
					finalisedProject = arbitraryList5.get(desiredProject);

					float totalFee = finalisedProject.getTotalFee();
					float totalpaid = finalisedProject.getTotalpaid();
					String projname = finalisedProject.getProjectName();
					KEYBOARD.nextLine();

					if ((totalFee - totalpaid) > 0) {
						generateInvoice(finalisedProject, totalFee, totalpaid, projname);

					} else {
						finalisedProject.setCompletionStatus(100);

						if (finalisedProject.getCompletionStatus() == 100) {
							String completionStat = "Finalized";
							completedProject(finalisedProject, completionStat);

						}
					}
					
				} else {
					continue;
				}
			}
		} catch (InputMismatchException e) {
			logger.log(Level.WARNING, "The project number you have entered is invalid");
		}
	}

	/**
	 *
	 * method to generate invoices <br>
	 * An invoice is put together using customer details.
	 * Invoice is printed to screen.
	 * 
	 * @param finalisedProject Projects variable is an instantiated project object
	 * @param totalFee float variable is the cost of a project
	 * @param totalPaid float variable is the amount paid for a project
	 * @param projname String variable stores a project name
	 *
	 */
	private static void generateInvoice(Projects finalisedProject, float totalFee, float totalpaid, String projname) {

		Customer thisCustomer = finalisedProject.getCustomer();// instantiating a customer

		String fullname = thisCustomer.getFirstName() + " " + thisCustomer.getSurname();

		String contactDetails = String.format("Telephone: %s%nEmail: %s%nAddress: %s%n", thisCustomer.getTelephone(),
				thisCustomer.getEmail(), thisCustomer.getAddress());

		float remainder = (totalFee - totalpaid);

		String invoice = "\nCustomer name: " + fullname + "\nProject name: " + projname + "\n" + contactDetails
				+ "\nTotal Fee: " + totalFee + "\nTotalPaid: " + totalpaid + "\nBalance: " + remainder + "\n";

		System.out.println(invoice);
	}

	/**
	 *
	 * method to complete a project <br>
	 * Completion status of a project is checked 
	 * User is prompted to enter a completion date if it is 100% complete.
	 * Project object is marked as finalized.
	 * A CompletedProject is created.
	 * Project object is saved to a CompletedProject table
	 * 
	 * @param finalisedProject Projects variable is a project object
	 * @param completionStatString variable is set to Finalized for completed projects
	 *
	 */
	private static void completedProject(Projects finalisedProject,String completionStat) {

		try {
			if (completionStat.equals("Finalized")) {

				System.out.println("When was the project completed? Enter a date in the format dd MMMM YYYY");
				finalisedProject.setCompletionDate(KEYBOARD.nextLine());
				
				//updating completion date upon finalization
				Jdbc.statement.executeUpdate(
						"update projects set completionDate='" + finalisedProject.getCompletionDate() + "'");

				// creating a table to save completed projects
				Jdbc.statement.executeUpdate("create table if not exists completedProjects("
						+ "projectNum int PRIMARY KEY , projectName varchar(50),buildType varchar(50),deadline varchar(50),"
						+ "address varchar(50),erf int,totalFee float,totalPaid float,completionStatus varchar(50),completionDate varchar(50),"
						+ "customerId int,architectId int,contractorId int,engineerId int,managerId int,"
						+ "FOREIGN KEY (customerId) REFERENCES customer(customerId),FOREIGN KEY (architectId) REFERENCES architect(architectId),"
						+ "FOREIGN KEY (contractorId) REFERENCES contractor(contractorId),FOREIGN KEY (engineerId) REFERENCES engineer(engineerId),"
						+ "FOREIGN KEY (managerId) REFERENCES manager(managerId));");

				// instantiating the persons to get their IDs
				Customer thisCustomer = finalisedProject.getCustomer();
				Architect thisArchitect = finalisedProject.getArchitect();
				Contractor thisContractor = finalisedProject.getContractor();
				Engineer thisEngineer = finalisedProject.getEngineer();
				Manager thisManager = finalisedProject.getManager();

				// saving relevant table values
				Jdbc.statement
						.executeUpdate("insert into completedProjects value('" + finalisedProject.getProjectNumber()
								+ "','" + finalisedProject.getProjectName() + "','" + finalisedProject.getBuildType()
								+ "','" + finalisedProject.getDeadline() + "','" + finalisedProject.getProjectAddress()
								+ "','" + finalisedProject.getErfNumber() + "','" + finalisedProject.getTotalFee()
								+ "','" + finalisedProject.getTotalpaid() + "','" + completionStat + "','"
								+ finalisedProject.getCompletionDate() + "','" + thisCustomer.getPersonID() + "','"
								+ thisArchitect.getPersonID() + "','" + thisContractor.getPersonID() + "','"
								+ thisEngineer.getPersonID() + "','" + thisManager.getPersonID() + "');");

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * method to allow reading of projects from the database <br>
	 * A list is created to store values from the database. 
	 * A join statement is used to pick values similar to our constructor.
	 * The data is retrieved as one string having a comma as a delimiter.
	 * Each value is stored in an array by splitting at the comma. 
	 * Array values are passed into the Projects constructor.
	 * The constructed project is added to our created list.
	 * 
	 * @return the list of projects read from a text file
	 *
	 */
	public static List<Projects> readProjects() {

		List<Projects> projList = new ArrayList<>();
		try {
			ResultSet query;

			// join method to get the required data sets
			String joinQuery = "SELECT "
					+ "a.projectName,a.buildType,a.projectNum,a.address,a.erf,a.totalFee,a.totalPaid,"
					+ "a.deadline,a.completionStatus,a.completionDate,"
					+ "b.firstname,b.surname,b.customerId,b.telephone,b.email,b.address,b.personType,"
					+ "c.firstname,c.surname,c.architectId,c.telephone,c.email,c.address,c.personType,"
					+ "d.firstname,d.surname,d.contractorId, d.telephone,d.email,d.address,d.personType,"
					+ "e.firstname,e.surname,e.engineerId,e.telephone,e.email,e.address,e.personType,"
					+ "f.firstname,f.surname,f.managerId,f.telephone,f.email,f.address,f.personType "
					+ "FROM projects a " + "INNER JOIN customer b " + "ON a.projectNum = b.projectNum "
					+ "INNER JOIN architect c " + "ON a.projectNum = c.projectNum " + "INNER JOIN contractor d "
					+ "ON a.projectNum = d.projectNum " + "INNER JOIN engineer e " + "ON a.projectNum = e.projectNum "
					+ "INNER JOIN manager f " + "ON a.projectNum = f.projectNum ";

			query = Jdbc.statement.executeQuery(joinQuery);

			// calling the metadata to give us column counts
			ResultSetMetaData rsmd = query.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

			// iterating after each row of values
			while (query.next()) {
				String comboColumnValue = "";
				String columnValue = "";
				
				// building the string with table attributes
				StringBuilder bld = new StringBuilder();
				for (int i = 0; i < columnsNumber; i++) {

					columnValue = query.getString(i + 1);
					bld.append(columnValue + ",");
					comboColumnValue = bld.toString();
				}

				// splitting the string by a delimiter
				String[] items = comboColumnValue.split("\\,");

				String projectName = items[0];
				String buildType = items[1];
				int projectNumber = Integer.parseInt(items[2]);
				String projectAddress = items[3];
				int erfNumber = Integer.parseInt(items[4]);
				float totalFee = Float.parseFloat(items[5]);
				float totalpaid = Float.parseFloat(items[6]);
				String deadline = items[7];
				int completionStatus = Integer.parseInt(items[8]);
				String completionDate = items[9];

				// storing extracted data into the respective ojects
				Customer thisCustomer = new Customer(items[10], items[11], Integer.parseInt(items[12]),
						Long.parseLong(items[13]), items[14], items[15], items[16]);

				Architect thisArchitect = new Architect(items[17], items[18], Integer.parseInt(items[19]),
						Long.parseLong(items[20]), items[21], items[22], items[23]);

				Contractor thisContractor = new Contractor(items[24], items[25], Integer.parseInt(items[26]),
						Long.parseLong(items[27]), items[28], items[29], items[30]);

				Engineer thisEngineer = new Engineer(items[31], items[32], Integer.parseInt(items[33]),
						Long.parseLong(items[34]), items[35], items[36], items[37]);

				Manager thisManager = new Manager(items[38], items[39], Integer.parseInt(items[40]),
						Long.parseLong(items[41]), items[42], items[43], items[44]);

				Projects newProjects = new Projects(projectName, buildType, projectNumber, projectAddress, erfNumber,
						totalFee, totalpaid, deadline, completionStatus, completionDate, thisCustomer, thisArchitect,
						thisContractor, thisEngineer, thisManager);

				// adding the projects to the list
				projList.add(newProjects);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projList;
	}
}
