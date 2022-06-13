package poise;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

/**
 * This code creates a class for projects
 * <p>
 * 
 * @author Samuel Wendi Kariuki
 * @version 3.1 11 June 2022
 */
public class Projects {


	// attributes
	private String projectName;
	private String buildType;
	private int projectNumber;
	private String projectAddress;
	private int erfNumber;
	private float totalFee;
	private float totalpaid;
	private String deadline;
	private int completionStatus;
	private String completionDate;
	private Customer customer;
	private Architect architect;
	private Contractor contractor;
	private Engineer engineer;
	private Manager manager;

	// constructors
	public Projects(String projectName, String buildType, int projectNumber, String projectAddress, int erfNumber,
			float totalFee, float totalpaid, String deadline, int completionStatus, String completionDate,
			Customer customer, Architect architect, Contractor contractor, Engineer engineer, Manager manager) {
		this.projectName = projectName;
		this.buildType = buildType;
		this.projectNumber = projectNumber;
		this.projectAddress = projectAddress;
		this.erfNumber = erfNumber;
		this.totalFee = totalFee;
		this.totalpaid = totalpaid;
		this.deadline = deadline;
		this.completionStatus = completionStatus;
		this.completionDate = completionDate;
		this.customer = customer;
		this.architect = architect;
		this.contractor = contractor;
		this.engineer = engineer;
		this.manager = manager;
	}

	// getters and setters
	public String getProjectName() {
		return projectName;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Architect getArchitect() {
		return architect;
	}

	public void setArchitect(Architect architect) {
		this.architect = architect;
	}

	public Contractor getContractor() {
		return contractor;
	}

	public Engineer getEngineer() {
		return engineer;
	}

	public void setEngineer(Engineer engineer) {
		this.engineer = engineer;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}

	public void setProjectName(String projectName) {

		this.projectName = projectName;
	}

	public String getBuildType() {
		return buildType;
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

	public int getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(int projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}

	public int getErfNumber() {
		return erfNumber;
	}

	public void setErfNumber(int erfNumber) {
		this.erfNumber = erfNumber;
	}

	public float getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(float totalFee) {
		this.totalFee = totalFee;
	}

	public float getTotalpaid() {
		return totalpaid;
	}

	public void setTotalpaid(float totalpaid) {
		this.totalpaid = totalpaid;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public int getCompletionStatus() {
		return completionStatus;
	}

	public void setCompletionStatus(int completionStatus) {
		this.completionStatus = completionStatus;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	/**
	 *
	 * toString method example. The method presents the class information on the console.
	 *
	 * @return output String variable containing all the information of the class
	 * 
	 */
	public String toString() {
		String output = ("\nProject Name: " + projectName);
		output += ("\nBuildType: " + buildType + "\n" + "ProjectNumber: " + projectNumber + "\n" + "ProjectAddress: "
				+ projectAddress);
		output += ("\nerfNumber: " + erfNumber + "\n" + "TotalFee: " + totalFee + "\n" + "Totalpaid: " + totalpaid);
		output += ("\nDeadline: " + deadline + "\n" + "CompletionStatus: " + completionStatus);
		output += ("\nCompletionDate: " + completionDate + "\n");
		output += (customer + "\n" + architect + "\n" + contractor + "\n"+ engineer + "\n" + manager + "\n");

		return output;

	}

	
	/**
	 *
	 * method to modify projects <br>
	 * User is prompted for a projectID. 
	 * The ID is manipulated to get a desired project. 
	 * Objects in the list are parsed through one at a time.
	 * The object with a matching project number is picked.
	 * Necessary modification is performed.
	 * 
	 * @param arbitraryList3 List variable to access existing projects
	 *
	 */
	static void modifyProject(List<Projects> arbitraryList3) {

		int modProject;
		int modchoice;
		String newDate;
		Float newPaid;

		System.out.println("Which project do you want to modify? Enter the projectID\n");
		modProject = Poised.KEYBOARD.nextInt();

		try {
			// parsing through the list for the matching project number
			for (int i = 0; i < arbitraryList3.size(); i++) {
				Projects modifiedProjects = arbitraryList3.get(i); // instantiating new project object

				if (modifiedProjects.getProjectNumber() == modProject) {
					System.out.println("modify: 1. Due Date \n2. Total amount paid");// prompting specific change
					modchoice = Poised.KEYBOARD.nextInt();

					if (modchoice == 1) {
						Poised.KEYBOARD.nextLine();
						System.out.println("enter your new due date. Enter a date in the format: dd MMMM YYYY");
						newDate = Poised.KEYBOARD.nextLine();
						modifiedProjects.setDeadline(newDate);

						// updating the project in the database
						Jdbc.statement.executeUpdate(
								"update projects set deadline='" + newDate + "' where projectNum='" + modProject + "'");

					} else if (modchoice == 2) {
						System.out.println("enter your new amount paid ");
						newPaid = Poised.KEYBOARD.nextFloat();
						modifiedProjects.setTotalpaid(newPaid);

						// updating the project in the database
						Jdbc.statement.executeUpdate("update projects set totalPaid='" + newPaid
								+ "' where projectNum='" + modProject + "'");
					}

				} else {
					continue;
				}
			}
		} catch (SQLException e) {
			Poised.logger.log(Level.WARNING, "invalid entry\n");
			e.printStackTrace();
		}
	}

	/**
	 *
	 * method to check for overdue projects <br>
	 * Objects in the list are parsed through one at a time.
	 * The object with a matching project number is picked.
	 * Overdue projects are printed to screen.
	 * 
	 * @param arbitraryList2 List variable to access existing projects
	 * @param currentDate Date variable to get today's date
	 * @param dateFormat SimpleDateFormat variable to get a desired format
	 *
	 */
	static void deadlineDate(List<Projects> arbitraryList2, Date currentDate, SimpleDateFormat dateFormat) {
		try {

			for (int i = 0; i < arbitraryList2.size(); i++) {
				Projects thisProject = arbitraryList2.get(i);
				Date importDate = dateFormat.parse(thisProject.getDeadline());// converting string date to date type

				// printing the overdue dates if the current date is after the deadline date
				if (currentDate.after(importDate) && thisProject.getCompletionStatus() != 100) {
					System.out.println(thisProject);
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * method to view finished projects <br>
	 * Objects in the list are parsed through one at a time.
	 * The object with a matching project number is picked.
	 * Completed projects are printed to screen.
	 * 
	 * @param arbitraryList2 List variable to access existing projects
	 *
	 */
	static void finishedProjects(List<Projects> arbitraryList2) {
		for (Projects thisProject : arbitraryList2) {
			if (thisProject.getCompletionStatus() == 100) {
				System.out.println(thisProject);
			}
		}
	}

	/**
	 *
	 * method to view unfinished projects <br>
	 * Objects in the list are parsed through one at a time.
	 * The object with a matching project number is picked.
	 * Incomplete projects are printed to screen.
	 * 
	 * @param arbitraryList2 List variable to access existing projects
	 *
	 */
	static void unfinshedProjects(List<Projects> arbitraryList2) {
		for (Projects thisProject : arbitraryList2) {
			if (thisProject.getCompletionStatus() < 100) {
				System.out.println(thisProject);
			}
		}
	}

	/**
	 *
	 * method to view a specific project <br>
	 * The method prints series of prompts message on the console.
	 * It also allows for entry of user values in decision making.
	 * The user can view a specific project.
	 * A specific project can be selected by its name or unique number.
	 *
	 * @param arbitraryList2 List variable to access existing projects
	 *
	 */
	static void specificProject(List<Projects> arbitraryList2) {

		int viewProject;
		int chooseBy;

		System.out.println("Do you want to choose via  \n1. project name  \n2. project number?");
		chooseBy = Poised.KEYBOARD.nextInt();
		Poised.KEYBOARD.nextLine();

		if (chooseBy == 1) {
			System.out.println("enter the project name");
			String projectname = Poised.KEYBOARD.nextLine();

			for (int i = 0; i < arbitraryList2.size(); i++) {
				Projects thisProject = arbitraryList2.get(i);
				if (thisProject.getProjectName().equals(projectname)) {
					System.out.println(thisProject);

				}
			}
		}

		else if (chooseBy == 2) {
			System.out.println("enter the project number\n");
			viewProject = Poised.KEYBOARD.nextInt();

			for (int i = 0; i < arbitraryList2.size(); i++) {
				Projects thisProject = arbitraryList2.get(i);

				if (thisProject.getProjectNumber() == viewProject) {
					System.out.println(thisProject);
				}
			}

		} else {
			Poised.logger.log(Level.WARNING, "invalid choice\n");
		}
	}

	/**
	 *
	 * method to view a project in multiple ways <br>
	 * 
	 * The method prints series of prompts message on the console It also allows for
	 * entry of user values in decision making.
	 * 
	 * 1. The user can view a specific project - A specific project can be selected
	 * by its name or unique number - A call to the method specificProject is made.
	 * 
	 * 2. The user can view unfinished projects - A call to the method
	 * unfinishedProject is made.
	 * 
	 * 3. The user can view finished projects - A call to the method finishedProject
	 * is made.
	 * 
	 * 4. The user can view overdue projects.
	 *
	 * @param arbitraryList2 List variable to access existing projects
	 *
	 */
	static void viewProjects(List<Projects> arbitraryList2) {

		int viewChoice; // selecting the view options from the menu

		System.out.println("""
				Do you wish to view
				1. A specific Project
				2. Unfinished Projects
				3. Finished Projects
				4. Overdue Projects
				5. All projects
				""");

		viewChoice = Poised.KEYBOARD.nextInt();// user input that chooses a specific choice

		// viewing a specific project
		if (viewChoice == 1) {
			Projects.specificProject(arbitraryList2);

			// viewing a unfinished projects
		} else if (viewChoice == 2) {
			Projects.unfinshedProjects(arbitraryList2);

			// viewing a finished projects
		} else if (viewChoice == 3) {
			Projects.finishedProjects(arbitraryList2);

			// viewing a overdue projects
		} else if (viewChoice == 4) {
			Date currentDate = new Date(); // new date object to compare dates

			// formatting our desired dates
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
			Projects.deadlineDate(arbitraryList2, currentDate, dateFormat);

			// viewing all projects
		} else if (viewChoice == 5) {
			for (int i = 0; i < arbitraryList2.size(); i++) {
				System.out.println(arbitraryList2.get(i));
			}

		} else {
			Poised.logger.log(Level.WARNING, "invalid choice\n");
		}
	}

	/**
	 *
	 * method to get the project details <br>
	 * The method prints series of prompts message on the console.
	 * It also allows for entry of user values.
	 * Inputs are manipulated to generate a Projects object. 
	 * The data is inserted into the database.
	 * The object is printed to screen.
	 *
	 * @param arbitraryList List variable to store Projects objects
	 */
	public static void setProjectDetails(List<Projects> arbitraryList) {

		Poised.KEYBOARD.nextLine(); // this line allows console to shift from integer to string stream

		System.out.println("Enter the name of your Project");
		String projectName = (Poised.KEYBOARD.nextLine());

		System.out.println("Enter the type of building of your project.\nE.g. House, apartment block or store, etc.");
		String buildType = (Poised.KEYBOARD.nextLine());

		System.out.println("Enter the Project Number of your Project");
		int projectNumber = (Poised.KEYBOARD.nextInt());

		System.out.println("Enter the ERF number of your Project");
		int erfNumber = (Poised.KEYBOARD.nextInt());

		System.out.println("Enter the Total fee of your Project");
		float totalFee = (Poised.KEYBOARD.nextFloat());

		System.out.println("Enter the Total amount paid of your Project");
		float totalpaid = (Poised.KEYBOARD.nextFloat());

		// workaround to read the next line with a different type of String
		Poised.KEYBOARD.nextLine();

		System.out.println("Enter the address of your Project");
		String projectAddress = (Poised.KEYBOARD.nextLine());

		System.out.println("Enter the Deadline of your project in the format dd MMMM YYYY");
		String deadline = (Poised.KEYBOARD.nextLine());

		String completionDate;
		System.out.println("Enter the Completion Status of your project as a percentage");

		int completionStatus = (Poised.KEYBOARD.nextInt());
		if (completionStatus < 100) {
			Poised.KEYBOARD.nextLine();
			completionDate = "Incomplete"; // temporarily set the date to incomplete until it's completed

		} else {
			Poised.KEYBOARD.nextLine();
			System.out.println("Enter the Completion Date of your project in the format dd MMMMM yyyy");
			completionDate = (Poised.KEYBOARD.nextLine());
		}

		// empty project name is set to the building type and customer surname
		if (projectName.equals("")) {
			String surname;
			System.out.println("\nWhat is the surname of the customer?");
			surname = Poised.KEYBOARD.nextLine();
			projectName = (buildType + " " + surname);

		}
		// setting the customer details in an array
		String[] customerInfo = Person.setPersonDetails("Customer");
		int customerIdInt = Integer.parseInt(customerInfo[6]); // the constructor needs an integer value
		long customerTelephoneInt = Long.parseLong(customerInfo[3]); // the constructor needs a long value

		Customer customer = new Customer(customerInfo[0], customerInfo[1], customerIdInt, customerTelephoneInt,
				customerInfo[4], customerInfo[5], customerInfo[2]);// instantiating the customer

		// setting the architect details in an array
		String[] architectInfo = Person.setPersonDetails("Architect");
		int architectIdInt = Integer.parseInt(architectInfo[6]); // the constructor needs an integer value
		long architectTelephoneInt = Long.parseLong(architectInfo[3]); // the constructor needs a long value

		Architect architect = new Architect(architectInfo[0], architectInfo[1], architectIdInt, architectTelephoneInt,
				architectInfo[4], architectInfo[5], architectInfo[2]); // instantiating the architect

		// setting the Contractor details in an array
		String[] contractorInfo = Person.setPersonDetails("Contractor");
		int contractorIdInt = Integer.parseInt(contractorInfo[6]); // the constructor needs an integer value
		long contractorTelephoneInt = Long.parseLong(contractorInfo[3]);// the constructor needs a long value

		Contractor contractor = new Contractor(contractorInfo[0], contractorInfo[1], contractorIdInt,contractorTelephoneInt, 
				contractorInfo[4], contractorInfo[5], contractorInfo[2]); // instantiating the contractor

		// setting the Engineer details in an array
		String[] engineerInfo = Person.setPersonDetails("Engineer");
		int engineerIdInt = Integer.parseInt(engineerInfo[6]); // the constructor needs an integer value
		long engineerTelephoneInt = Long.parseLong(engineerInfo[3]);// the constructor needs a long value

		Engineer engineer = new Engineer(engineerInfo[0], engineerInfo[1], engineerIdInt, engineerTelephoneInt,
				engineerInfo[4], engineerInfo[5], engineerInfo[2]); // instantiating the engineer

		// setting the Manager details in an array
		String[] managerInfo = Person.setPersonDetails("Manager");
		int managerIdInt = Integer.parseInt(managerInfo[6]); // the constructor needs an integer value
		long managerTelephoneInt = Long.parseLong(managerInfo[3]);// the constructor needs a long value

		Manager manager = new Manager(managerInfo[0], managerInfo[1], managerIdInt, managerTelephoneInt, managerInfo[4],
				managerInfo[5], managerInfo[2]); // instantiating the manager

		// Project object that is populated by user input
		Projects createdProject = new Projects(projectName, buildType, projectNumber, projectAddress, erfNumber,
				totalFee, totalpaid, deadline, completionStatus, completionDate, customer, architect, contractor,
				engineer, manager);

		String printedProjects = createdProject.toString();

		// inserting and updating the database with relevant fields
		try {
			Jdbc.statement.executeUpdate("insert into projects value('" + projectNumber + "','" + projectName + "','"
					+ buildType + "','" + deadline + "','" + projectAddress + "','" + erfNumber + "','" + totalFee
					+ "','" + totalpaid + "','" + completionStatus + "','" + completionDate
					+ "',NULL,NULL,NULL,NULL,NULL);");

			System.out.println("PROJECT VALUES ENTERED SUCCESFULLY");

			Jdbc.statement.executeUpdate("insert into customer value('" + customerInfo[0] + "','" + customerInfo[1]
					+ "','" + customerIdInt + "','" + customerTelephoneInt + "','" + customerInfo[4] + "','"
					+ customerInfo[5] + "','" + projectNumber + "','" + customerInfo[2] + "');");

			System.out.println("CUSTOMER VALUES ENTERED SUCCESFULLY");

			Jdbc.statement.executeUpdate("insert into architect value('" + architectInfo[0] + "','" + architectInfo[1]
					+ "','" + architectIdInt + "','" + architectTelephoneInt + "','" + architectInfo[4] + "','"
					+ architectInfo[5] + "','" + projectNumber + "','" + architectInfo[2] + "');");

			System.out.println("ARCHITECT VALUES ENTERED SUCCESFULLY");

			Jdbc.statement
					.executeUpdate("insert into contractor value('" + contractorInfo[0] + "','" + contractorInfo[1]
							+ "','" + contractorIdInt + "','" + contractorTelephoneInt + "','" + contractorInfo[4]
							+ "','" + contractorInfo[5] + "','" + projectNumber + "','" + contractorInfo[2] + "');");

			System.out.println("CONTRACTOR VALUES ENTERED SUCCESFULLY");

			Jdbc.statement.executeUpdate("insert into engineer value('" + engineerInfo[0] + "','" + engineerInfo[1]
					+ "','" + engineerIdInt + "','" + engineerTelephoneInt + "','" + engineerInfo[4] + "','"
					+ engineerInfo[5] + "','" + projectNumber + "','" + engineerInfo[2] + "');");

			System.out.println("ENGINEER VALUES ENTERED SUCCESFULLY");

			Jdbc.statement.executeUpdate("insert into manager value('" + managerInfo[0] + "','" + managerInfo[1] + "','"
					+ managerIdInt + "','" + managerTelephoneInt + "','" + managerInfo[4] + "','" + managerInfo[5]
					+ "','" + projectNumber + "','" + managerInfo[2] + "');");

			System.out.println("MANAGER VALUES ENTERED SUCCESFULLY");

			Jdbc.statement.executeUpdate("UPDATE projects" + " set customerId ='" + customerIdInt + "',architectId='"
					+ architectIdInt + "',contractorId='" + contractorIdInt + "'" + ",engineerId='" + engineerIdInt
					+ "',managerId='" + managerIdInt + "' WHERE projectNum='" + projectNumber + "'");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("VALUES ENTERED SUCCESFULLY");
		Poised.logger.log(Level.INFO, printedProjects);// logging the printed project object

		arbitraryList.add(createdProject);// adding each project to a list
	}
}
