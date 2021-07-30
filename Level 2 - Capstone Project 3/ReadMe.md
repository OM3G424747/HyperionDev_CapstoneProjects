# hyperionDev Capstone Project 3 - Level 2 :clipboard: :house:

## Poised project management system

The purpose of this app is to create a basic program for managing projects for a small structural engineering firm.

In it's current state the app allows the following funtions:

1) - Create a new project
2) - Lookup and edit projects
3) - Update/Create a profile for an Architect, Contractor or Customer
4) - Set the year range available when selecting a Due Date


## Creating or updating contact profiles

If the user attempts to create a project with no available contact profiles, the user will be presented with an error and requested to create the missing contact profile.
There needs to be at least of each of the following contact profile types:
1) - Architect
2) - Contractor
3) - Customer

after these are created the user will be able to select the available profile for each and create a project to assign to each of these.

The app saves an internal account number to each profile created. This ensures that even if all the contact details on a profile change, the relevant profiles will still remain associated to the relevant project.

If the user attempts to "update" a profile from the option to update/create a profile, they will only be given the option to "create" a profile if no contact profiles have been created yet.
After at least 1 profile is created, the option to edit will become available. 
However, if the user selects an account type that's not been created yet, they will be presented with an error and requested if they would like to create on of those profiles first.

## Creating projects or editing existing projects
Attempting to edit the due date, total payed or finalize a project will display an error to the user if no projects are currently available / created.

The user needs to create a project before they will be able to edit projects.
A project cannot be created without the user registering at least 1 Architect, 1 Contractor and 1 Customer to associtate with the project 

After at least 1 project has been created, the options to edit the due date, total payed or finalize a project will no longer display errors when selected.


## Looking up existing projects
This will provide several methods of looking up a project.
The project can either be located by reviewing the list of overdue projects, or reviewing the list on unfinalized projects.
Lastly, the project can also be searched for using the project name, project number, cellphone number of a personnel account associated with the project or email of a personnel account associated with the project.

Once a project has been selected it can then be edited, finalized/unfinalized, or have an invoice created.
Invoices will always ask for a custom name to save the invoice as, but will have the project name filled as a default values.

## Settings
The setting menu can be used to limit the number of years available for selection from the current deadline.

