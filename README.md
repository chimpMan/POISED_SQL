# POISED SQL Capstone Project
My Ninth Capstone Project
 A project management program that uses MySql for inputs and outputs

## WHAT THIS PROJECT DOES
* Manages projects, duh!
* *In addition to that,a user can*
	* **add new projects**
	* **edit existing projects**
	* **view projects**
	* **edit particular contractors**
	* **Finalize projects**

## HOW DOES IT WORK?
In a basic summary, the program works by
1. Connecting to a database, in this case, MySQL through JDBC connector

## DETAILED SUMMARY
### But what is a JDBC connection?
        A JDBC connection is a method through which Java classes can connect through to MySQL servers.
        There is a seperate class called JDBC that I created that allows this connection is made.

2. Creating a list of type ArrayList that stores Objects of type Projects.
This list is what is used by the program to retrieve and save our projects.

3. The list is checked if it is either empty or not.
If the former is true, the list is populated by the records in the MySQL server.
The server comes preloaded with two projects.
If the list is not empty, the projects are loaded and can now be manipulated from the menu.

4. A menu is presented at all times until the user exits.
This menu shows the previously mentioned capabilities as options.

### how is the project created?

    A method that sets the details of a projects is called by asking for user inputs and these details
        are saved one at a time into a project object.
    This project is then added into the list and consequently into the database for future retrieval.

### how is the project edited?


    At the moment, only the total amount paid and deadline dates are editable.
    This is done by calling a method to edit the project and asks for the project number of the desired project.
    With the project number, the program parses through the list and finds the desired project.
    From there the user is prompted on which variable they wish to change and user input
        is taken and ammended. 
    The database is then updated.

### how is the project viewed?


    The user has multiple options
* **view specific projects**
    This is done by calling a method to view specific projects and asks for the project number or name of the desired project.
    With the project number or name, the program parses through the list and finds the desired project. 
    The project is presented to screen.


* **view unfinished projects**
    This is done by calling a method to view unfinished projects.
    The program parses through the list and finds the desired projects that have a completion Status of less than 100%. 
    The project(s) is presented to screen.


* **view finished projects**
    This is done by calling a method to view finished projects.
    The program parses through the list and finds the desired projects that have a completion Status of equal to 100%. 
    The project(s) is presented to screen.


* **view overdue projects**
    This is done by calling a method to view overdue projects.
    The program parses through the list and finds the desired projects that have a deadline date older than the current date%. 
    The project(s) is presented to screen.


* **view all projects**
    This is done by calling a method to view all projects.. 
    The entire list is printed to screen.


### how is the contractor edited?

    This is done by calling a method to edit a specific contractor and asks for the project number or name of the desired project.
    With the project number, the program parses through the list and finds the desired project and retrieves the contractor of that project. 
    The user then chooses between telephone number or email.
    The changes are input by the user and the records are updated.

### how is the project finalized?

    This is done by calling a method to finalize a project and asks for the project number or name of the desired project.
    With the project number, the program parses through the list and finds the desired project and retrieves the contractor of that project. 
    If the project has been fully paid for, the completion date is input by the user and a new table is created for completed projects
        where the completion status is set to "Finalized".
    If the project is not fully paid for, the customer is charged with an invoice that shows their name, project name and all their
    contact info together with the balance.


