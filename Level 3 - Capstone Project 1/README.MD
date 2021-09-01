# hyperionDev Capstone Project 1 - Level 3 :clipboard: :house:

## Poised project management system

The purpose of this app is to create a basic program for managing projects for a small structural engineering firm.

In it's current state the app allows the following funtions:

1) - Create a new project
2) - Lookup and edit projects
3) - Update/Create a profile for a Manager, Architect, Engineer or Customer
4) - Set the year range available when selecting a Due Date


## Creating or updating contact profiles

If the user attempts to create a project with no available contact profiles, the user will be presented with an error and requested to create the missing contact profile.
There needs to be at least of each of the following contact profile types:
1) - Manager
1) - Architect
2) - Engineer
3) - Customer

after these are created the user will be able to select the available profile for each and create a project to assign to each of these.

The app saves an internal account number to each profile created. This ensures that even if all the contact details on a profile change, the relevant profiles will still remain associated to the relevant project.

If the user attempts to "update" a profile from the option to update/create a profile, they will only be given the option to "create" a profile if no contact profiles have been created yet.
After at least 1 profile is created, the option to edit will become available. 
However, if the user selects an account type that's not been created yet, they will be presented with an error and requested if they would like to create on of those profiles first.

## Creating projects or editing existing projects
Attempting to edit the due date, total paid or finalize a project will display an error to the user if no projects are currently available / created.

The user needs to create a project before they will be able to edit projects.
A project cannot be created without the user registering at least 1 Manager, 1 Architect, 1 Engineer and 1 Customer to associate with the project 

After at least 1 project has been created, the options to edit the due date, total paid or finalize a project will no longer display errors when selected.


## Looking up existing projects
This will provide several methods of looking up a project.
The project can either be located by reviewing the list of overdue projects, or reviewing the list on unfinalized projects.
Lastly, the project can also be searched for using the project name, project number, cellphone number of a personnel account associated with the project or email of a personnel account associated with the project.

Once a project has been selected it can then be edited, finalized/unfinalized, or have an invoice created.
Invoices will always ask for a custom name to save the invoice as, but will have the project name filled as a default values.

## Settings
The setting menu can be used to limit the number of years available for selection from the current deadline.

## Login and Saving Projects or Personnel
The root SQL account will need to be logged into in order to use the app.
On first login if no database exists the app will give the user the option to create the required SQL Database and tables automatically.
This will result in blank tables that will return an error when the app attempts to load them.
After creating at least 1 project the error will no longer be displayed when the app is successfully opened and logged into.

After first use, these errors could indicate issues with the database or tables if displayed 