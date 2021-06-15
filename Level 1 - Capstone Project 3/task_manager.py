from datetime import date


# funtion confirms if a valid int has been passed
# the funtion will continues to loop until a valid int is entered
# entering letters instead of numbers will print an error
def get_valid_int(user_request):
    not_valid_int = True
    while not_valid_int:
        string_to_int = input(f"\n{user_request}\n\n-> ")
        try:
            string_to_int = int(f"\n{string_to_int}")
            return string_to_int

        except ValueError:
            print("\n", "*"*50, "\n\t\t INVALID VALUE!\n", "*"*50)
            print("\n Please enter a number using only numeric keys.")
            print(" For example: \"5\" or \"8\".")


def standard_input(string_to_ask):
    answer = input(string_to_ask).strip()
    answer = answer.lower()
    return answer


# creates a variable called today
# assigns the value of today's date
today = date.today()


# month list used to convert numerical value into the correct month
month_dict = {1: "Jan",
              2: "Feb",
              3: "Mar",
              4: "Apr",
              5: "May",
              6: "Jun",
              7: "Jul",
              8: "Aug",
              9: "Sep",
              10: "Oct",
              11: "Nov",
              12: "Dec"
              }

# saves current year, month and day as variables with an int value
# current date list stores all 3 values for checking user input
# the current date list will be used to check for valid dates when creating tasks
current_year = int(today.strftime("%Y"))
current_month = int(today.strftime("%m"))
current_day = int(today.strftime("%d"))
current_date = []
current_date.append(current_day)
current_date.append(current_month)
current_date.append(current_year)


# funtion converts a string containing a month to a list of ints
# the string is split by spaces
# the month is then compared to the month dictionary to return the month number
# the date list is then enumerated and each value is converted into an int
def date_to_list(date):
    global month_dict
    date = date.split(" ")
    for number, month in enumerate(month_dict, start=1):
        if month_dict[month] == date[1]:
            date[1] = number
    for number, value in enumerate(date):
        date[number] = int(value)
    return date


# creates a variable to store today's date
# month int is adjusted to match the correct index
# this is automatically added to tasks for their creation date
today = f"{current_day} {month_dict[current_month]} {current_year}"


# funtion reads a file and then returns all values in a list
# values for the list are seperated by new lines only
def read_file(filename):
    # opens the file with the file name passed
    user_file = open(f"{filename}.txt", "r")
    # reads data from file and saves it as a variable
    file_data = user_file.read()
    # splits each value when a new line is detected
    file_data = file_data.split("\n")
    # checks if a new line is detected at the end
    # if it is but with a blank value, the value is removed
    if file_data[-1] == "":
        del file_data[-1]
    # file is closed and the list is returned
    user_file.close()
    return file_data


# slipts string values seperated by a comma and a space
# the values are then returned in the from of a list
def string_to_list(string_value):
    list_to_return = string_value.split(", ")
    return list_to_return


# takes in a list that contains string values seperated by commas
# commas are used as index indicators for values
# the funtion then returns a list with all the same values
# the values are based on the selected value_index
# if an int is passed that's higher than the number of items in the list
# and out of index error will be returned
# for excample -> same_value_list(user_login_list, 2)
def same_value_list(value_list, value_index):
    list_to_return = []
    current_value_list = []
    for value in range(len(value_list)):

        current_value_list = string_to_list(value_list[value])
        list_to_return.append(current_value_list[value_index])
    return list_to_return


# funtion takes in the active user's username and assigned task list
# the assigned task list in enumerated
# each task index belonging to the user is added to the dictionary
def index_dict(active_user, assigned_tasks):
    task_index_dict = {}
    tasks_num = 1
    for index, user in enumerate(assigned_tasks):
        if user == active_user:
            task_index_dict[tasks_num] = index
            tasks_num += 1

    return task_index_dict


# funtion used to confirm a user's choice
# this is used to confirm if the user is ready to return to the menu
# or confirm choices before writing them to the file
# the funtion continues to loop until the user enters either yes or no
def user_confirmation(string_to_confirm):
    valid_choice = False
    user_confirmation = ["yes", "no"]
    while not valid_choice:
        print(string_to_confirm)
        print(" Would you like to proceed with this?\n")
        confirm_choice = input(" Enter \"yes\" to confirm or \"no\" to cancel.\n\n-> ").strip()
        if confirm_choice == user_confirmation[0]:
            return True
        elif confirm_choice == user_confirmation[1]:
            return False
        else:
            print("\n", "*"*50, "\n\t\t INVALID SELECTION!\n", "*"*50)
            print("\n Please enter either \"yes\" to confirm or \"no\" to confirm your choice.\n")


# this funtion writes the current username_list and password_list to file
# when called it opens the file and writes the current variable values
def write_to_user(username_list, password_list):
    user_file = open("user.txt", "w")
    for num in range(len(username_list)):
        user_file.write(f"{username_list[num]}, {password_list[num]}\n")
    user_file.close()


# this funtion writes the task values to file
# when called it opens the file and writes the current variable values
def write_to_task(task_assigned_user, task_title, task_description, task_creation_date, task_due_date, task_status):
    user_file = open("tasks.txt", "w")
    for num in range(len(task_assigned_user)):
        user_file.write(f"{task_assigned_user[num]}, {task_title[num]}, {task_description[num]}, {task_creation_date[num]}, {task_due_date[num]}, {task_status[num]}\n")
    user_file.close()


# this funtion writes the task stats to file
# when called it opens the file and writes the current variable values
def write_reportfile(task_stats_list, filename):
    user_file = open(f"{filename}.txt", "w")
    for stat in range(len(task_stats_list)):
        user_file.write(f"{task_stats_list[stat]}\n")
    user_file.close()


# this funtion is used to check if a valid date was entered by the user
# if a value in the past is entered an error is printed
# if an invalid value is entered an error is printed
# the funtion keeps looping on each value until a correct value is entered
# after a valid dated is ented the date is returned
def get_valid_date():
    global current_date
    global month_dict
    is_valid_year = False
    while not is_valid_year:
        user_year = get_valid_int(" Please enter the year number of the task's due date.")
        if user_year < current_date[2]:
            print("\n", "*"*50, "\n\t\t INVALID DATE!\n", "*"*50)
            print(" Task due dates for new tasks cannot be in the past.")
            print(f" Select a year value greater than or equal to {current_date[2]}\n", "*"*50)
        else:
            is_valid_month = False
            while not is_valid_month:
                user_month = get_valid_int(" Please enter the month number of the task's due date.")
                if user_month >= 13 or user_month <= 0:
                    print(" Please enter a month number between 1 and 12.")
                elif user_month < current_date[1] and user_year == current_date[2]:
                    print("\n", "*"*50, "\n\t\t INVALID DATE!\n", "*"*50)
                    print(" Task due dates for new tasks cannot be in the past.")
                    print(f" Select a month value greater than or equal to {current_date[1]}\n", "*"*50)
                else:
                    is_valid_date = False
                    while not is_valid_date:
                        user_day = get_valid_int(" Please enter the day number of the task's due date.")
                        if user_day <= 0:
                            print(" Please enter a day number greater than 0.")
                        elif user_day < current_date[0] and user_month == current_date[1] and user_year == current_date[2]:
                            print("\n", "*"*50, "\n\t\t INVALID DATE!\n", "*"*50)
                            print(" Task due dates for new tasks cannot be in the past.")
                            print(f" Select a day value greater than or equal to {current_date[0]}\n", "*"*50)
                        else:
                            # checks if the year selected by the user is a leap year
                            if user_year % 4 == 0:
                                # if it is, the bool is set to true
                                leap_year = True
                            else:
                                leap_year = False
                            if leap_year and user_month == 2 and user_day >= 30:
                                print(" Please enter a day range between 0 and 29.")
                            elif not leap_year and user_month == 2 and user_day >= 29:
                                print(" Please enter a day range between 0 and 28.")
                            elif user_month <= 7:
                                if user_month % 2 == 0 and user_day >= 31:
                                    print(" Please enter a day range between 0 and 30.")
                                elif user_month % 2 == 1 and user_day >= 32:
                                    print(" Please enter a day range between 0 and 31.")
                                else:
                                    date = f"{user_day} {month_dict[user_month-1]} {user_year}"
                                    return date
                            elif user_month > 7:
                                if user_month % 2 == 0 and user_day >= 32:
                                    print(" Please enter a day range between 0 and 31.")
                                elif user_month % 2 == 1 and user_day >= 31:
                                    print(" Please enter a day range between 0 and 30.")
                                else:
                                    date = f"{user_day} {month_dict[user_month]} {user_year}"
                                    return date


# funtion used to register a new user
# the active username is passed so that only the admin will be able to register a user
def reg_user(active_username, username_list):
    # bool remains true until end of the funtion
    # this prevents returning to the menu if duplicate user is entered
    user_selection_active = True
    while user_selection_active:
        # if the active user is the admin they are allowed to register a user
        if active_username == "admin":
            new_user = standard_input("\n Enter a username for the new user.\n\n-> ")
            # strips blank space
            # this prevents username doubles
            # for example "bill" and "bill   "
            new_user = new_user.strip()
            if new_user in username_list:
                # if the user is already registered the admin is given an error
                print("\n", "*"*50, "\n\t USER ALREADY REGISTERED!\n", "*"*50)
                print(f"\n \"{new_user}\" is already registered!\n Please use a different username.")

            else:
                # if the input is valid it's appended to the username and password lists
                # the password needs to be entered again to confirm it's correct
                # passwords are not stripped to allow space to be used
                new_password = input("\n Enter a password for the new user.\n\n-> ")
                confirm_password = input("\n Please enter the password again to confirm.\n\n-> ")
                if new_password != confirm_password:
                    print("\n", "*"*50, "\n\t PASSWORDS DO NOT MATCH!\n", "*"*50)
                    print(" Please try again")

                elif new_password == confirm_password:
                    username_list.append(new_user)
                    password_list.append(new_password)

                    # confirms the user credentials to the admin
                    print(f"\n", "-"*40, "\n\tYou're about to add:\n")
                    print(f"\tUsername: {username_list[-1]}")
                    print(f"\tPassword: {password_list[-1]}\n", "-"*40, "\n")

                    # if the user confirms it's correct
                    # the credentials are saved to file and the loops is ended
                    if user_confirmation(" The user will be added to the database."):
                        write_to_user(username_list, password_list)
                        user_selection_active = False

                    else:
                        # if the user denies the selection the credentials are deleted
                        # the user then get's asked if they would like to return to the main menu
                        # if the user enters no, they will be looped to enter a new user again
                        del username_list[-1]
                        del password_list[-1]
                        print("\n", "*"*50, "\n\t\t USER NOT ADDED!\n", "*"*50)
                        print(f"\n \"{new_user}\" will not be registered.\n")
                        if user_confirmation(f" You'll now be returned to the main menu."):
                            user_selection_active = False

        # if the active user is not the admin they are presented with an error
        elif active_username != "admin":
            print("\n", "*"*40, "\n PERMISSION ERROR!\n", "*"*40)
            print("\n Only the admin can register new users!")
            if user_confirmation(" You will now be returned to the main menu.\n"):
                user_selection_active = False


# returns false of the user is not in the username list and the user decides not to proceed
# it compares the username entered to see if the name is already on the list that's passed
def check_reg_users(username_list):
    # assigned user forced into lowercase to avoid errors due to case sensitivitiy
    assigned_user = standard_input("\n Enter the username of the user you'd like to assign the task to.\n\n-> ")

    if assigned_user not in username_list:
        # if the assigned user is not registered, the following error is displayed
        print("\n", "*"*50, "\n\t\t USER NOT FOUND!\n", "*"*50)
        if user_confirmation("\n Your specified user is not currently registered!"):
            # if the user confirms it's correct the error is bypassed
            return assigned_user
        else:
            return False
    elif assigned_user in username_list:
        return assigned_user


# adds a new task to the task file
# it first compares to see if the user is in the registered user list
# if it's not the user is asked if they want to proceed
# if confirmed the funtion will continue as normal and still create the task
# the date selected for the list can only be in the future
# if the user confirms the task, it get's writen to file
def add_task(username_list):
    # the option will looped over
    # this prevents returning to the menu during invalid input
    user_selection_active = True
    while user_selection_active:
        task_user = check_reg_users(username_list)

        if task_user is not False:
            task_assigned_user.append(task_user)
            task_creation_loop = True
            while task_creation_loop:
                # assigns a title to the task
                assigned_title = input("\n Enter a title for the task.\n\n-> ")
                task_title.append(assigned_title)
                # assigns tasks description and creation date
                # creation date defaults to the current date
                assigned_description = input("\n Please enter a description for the task.\n\n-> ")
                task_description.append(assigned_description)
                task_creation_date.append(today)
                # assigns due date and status
                # status defaults to "No"
                assigned_date = get_valid_date()
                task_due_date.append(assigned_date)
                task_status.append("No")

                # confirms the user's input
                # used to display the most recently entered task
                # the index of -1 is selected to select the most recently created task
                print("\n", "*"*40, "\n You are about to add the following task\n")
                display_task(-1)
                if user_confirmation(""):
                    # if the user confirms their selection, it's saved to file and the loop is ended
                    write_to_task(task_assigned_user, task_title, task_description, task_creation_date, task_due_date, task_status)
                    user_selection_active = False
                    task_creation_loop = False
                else:
                    # if the user denies their selection it's deleted
                    del task_assigned_user[-1]
                    del task_title[-1]
                    del task_description[-1]
                    del task_creation_date[-1]
                    del task_due_date[-1]
                    del task_status[-1]
                    print("\n", "*"*50, "\n\t\t TASK NOT ADDED!\n", "*"*50)
                    # the user is asked if they want to return to the main menu
                    # if they confirm, the loop is ended
                    # if they deny, the loop is restarted
                    if user_confirmation(" You'll now be returned to the main menu."):
                        user_selection_active = False
                        task_creation_loop = False
                    else:
                        task_creation_loop = False


# funtion to view all tasks
# it loops over all the assigned tasks
# after being displayed it asks the user if they want to return to menu
def view_all(task_assigned_user):
    view_user_tasks = True
    while view_user_tasks:
        for index_num in range(len(task_assigned_user)):
            if index_num == 0:
                print("\n")
            print(f"Task Number: {index_num + 1}", "*"*80)
            display_task(index_num)
        # confirms if the user is ready to return to the main menu
        if user_confirmation("\n You will now be returned to the main menu."):
            view_user_tasks = False


# funtion used to display a task with a given index number
def display_task(index_num):

    global task_assigned_user
    global task_title
    global task_description
    global task_creation_date
    global task_due_date
    global task_status

    print(f"\n Assignee \t- \t{task_assigned_user[index_num]}")
    print(f" Title \t\t- \t{task_title[index_num]}")
    print(f" Description \t- \t{task_description[index_num]}")
    print(f" Creation Date \t- \t{task_creation_date[index_num]}")
    print(f" Due Date \t- \t{task_due_date[index_num]}")
    print(f" Task Completed - \t{task_status[index_num]}\n")


def edit_task(task_num, task_index_dict, task_to_edit):
    print(f"This section is currently set to{task_to_edit[task_index_dict[task_num]]}")
    if user_confirmation("Are you sure you'd like to edit this section?"):
        new_value = input("Please enter the new information for this section.")
        return new_value
    else:
        return


# list stores the main menu selections for the user
user_options = ["r", "a", "va", "vm", "gr", "ds", "e"]
# list stores the descriptions for each selection
option_description = [
    "register a new user", "add a new task",
    "view all tasks", "view all your tasks",
    "generate reports",
    "display statistics", "exit the application"
    ]

# convertes all user login details into a single list
user_login_list = read_file("user")

# converts all user tasks into a single list
user_task_list = read_file("tasks")

# creates 2 lists from user_login_list
# 1 list stores all usernames
# 1 list stores all passowrd
# the index of matching credentials are the same]
# for example, admin and adm1n are both on index 0
username_list = same_value_list(user_login_list, 0)
password_list = same_value_list(user_login_list, 1)


# creates lists for all task items from user_task_list
# all task values have matching indexes across all lists
task_assigned_user = same_value_list(user_task_list, 0)
task_title = same_value_list(user_task_list, 1)
task_description = same_value_list(user_task_list, 2)
task_creation_date = same_value_list(user_task_list, 3)
task_due_date = same_value_list(user_task_list, 4)
task_status = same_value_list(user_task_list, 5)


# funtion for viewing all tasks assigned to the current active user
# the user is given the option to mark a task as complete or not
# if the task is compelte, they will not be able to edit it
# if it's not complete they will be able to edit it
# users will be able to edit the date or assigned user
# the assigned user is checked with the registered users
# if the user doesn't exist an error is returned
# dates selected need to be in the future
# if an option is selected that's not available an error is displayed
def view_mine(active_user, task_assigned_user, task_title, task_description, task_creation_date, task_due_date, task_status, username_list):

    task_assigned_user = task_assigned_user
    task_title = task_title
    task_description = task_description
    task_creation_date = task_creation_date
    task_due_date = task_due_date
    task_status = task_status
    view_user_tasks = True
    edit_selected_task = False
    # list stores the main menu selections for the user
    user_options = ["et", "mc", "e"]
    # list stores the descriptions for each selection
    option_description = [
        "edit a selected task",
        "mark task as complete or uncomplete",
        "exit the edit menu"
        ]

    while view_user_tasks:
        user_task_dict = index_dict(active_user, task_assigned_user)
        if len(user_task_dict) == 0:
            print("\n You currently have no tasks assigned to you.")
        else:
            for task_num in range(len(user_task_dict)):
                # adds an empty line at the start of the list
                if task_num == 0:
                    print("\n")

                print(f"Task Number: {task_num + 1}", "*"*80)
                display_task(user_task_dict[task_num + 1])
            # confirms if the user is ready to return to the main menu
            selected_task = get_valid_int(" Please enter the task number you'd like to edit\n Enter \"-1\" to exit.")
            if selected_task == -1:
                if user_confirmation("\n You will now be returned to the main menu."):
                    view_user_tasks = False

            elif selected_task > len(user_task_dict) or selected_task == 0:
                print("\n", "*"*50, "\n\t\t TASK NOT AVAILABLE!\n", "*"*50)
                print(" Sorry the selected task is not available.\n Please select as valid task number.")

            # edit selected task
            else:
                edit_selected_task = True
                while edit_selected_task:
                    print("\n", "-"*40, "\n\tEDIT MENU\n", "-"*40)
                    print(" You have the following options:\n")
                    for item in range(len(user_options)):
                        # if the task is
                        if task_status[user_task_dict[selected_task]] == "Yes" and user_options[item] == "et":
                            print(f"TASK CANNOT BE EDITED IF MARKED AS COMPLETED!\n")
                        else:
                            print(f" {user_options[item]} - {option_description[item].capitalize()}")

                    user_selection = standard_input("\n Please enter your selection here\n\n-> ")
                    # passed as true if the user wants to change the completion status of a task
                    if user_selection == user_options[1]:
                        if task_status[user_task_dict[selected_task]] == "No":
                            if user_confirmation("\n The task will be marked as complete."):
                                # passes the selected number to the dictionary to convert it to the relevant index number
                                task_status[user_task_dict[selected_task]] = "Yes"
                                write_to_task(task_assigned_user, task_title, task_description, task_creation_date, task_due_date, task_status)
                                edit_selected_task = False
                        else:
                            if user_confirmation("\n The task will be marked as uncomplete."):
                                # passes the selected number to the dictionary to convert it to the relevant index number
                                task_status[user_task_dict[selected_task]] = "No"
                                write_to_task(task_assigned_user, task_title, task_description, task_creation_date, task_due_date, task_status)
                                edit_selected_task = False
                    elif user_selection == user_options[0] and task_status[user_task_dict[selected_task]] == "No":
                        # list stores the main menu selections for the user
                        task_options = ["eu", "ed"]
                        # list stores the descriptions for each selection
                        task_option_description = [
                            "edit the assigned user",
                            "edit date of selected"
                            ]
                        print("\n You can edit the following sections of the task.\n")
                        for item in range(len(task_options)):
                            print(f" {task_options[item]} - {task_option_description[item].capitalize()}")
                        task_section_edit = standard_input("\n Please enter the section you'd like to enter.\n\n->")
                        if task_section_edit == "eu":
                            task_user = check_reg_users(username_list)

                            if task_user is not False:
                                task_assigned_user[user_task_dict[selected_task]] = task_user
                                write_to_task(task_assigned_user, task_title, task_description, task_creation_date, task_due_date, task_status)
                                edit_selected_task = False

                        elif task_section_edit == "ed":
                            task_due_date[user_task_dict[selected_task]] = get_valid_date()
                            write_to_task(task_assigned_user, task_title, task_description, task_creation_date, task_due_date, task_status)
                            edit_selected_task = False
                        else:
                            print("\n", "*"*50, "\n\t\t INVALID SELECTION!\n", "*"*50)
                            print(" Please select an item form the menu\n", "*"*50)

                    # exit edit selected task menu
                    elif user_selection == user_options[2]:
                        edit_selected_task = False
                    else:
                        print("\n", "*"*50, "\n\t\t INVALID SELECTION!\n", "*"*50)
                        print(" Please select an item form the menu\n", "*"*50)


# checks if a due date is behind a due date
# first the year, then month, then day are compared
# if one of the conditions are true, the funtion returns true
# if not, else returns false
def over_due(due_date, current_date):

    # current date is already in list format
    due_date = date_to_list(due_date)
    # if current year is greater, the date is overdue
    if current_date[2] > due_date[2]:
        return True
    # if current year equal to the due date year, the task month is greater, the date if over due
    elif current_date[2] == due_date[2] and current_date[1] > due_date[1]:
        return True
    # if current year and month are equal to the due date year and month, but the due day is greater, the date is overdue
    elif current_date[2] == due_date[2] and current_date[1] == due_date[1] and current_date[0] > due_date[0]:
        return True
    # if non of these are true the date is not overdue
    else:
        return False


# creates a report for all current tasks
# the values are all returned as a list
# the list can then be printed or written to a file
def task_report(task_status, task_due_date, current_date):
    task_rep_list = []
    # title for statistics
    task_rep_list.append("\n")
    task_rep_list.append("-"*40)
    task_rep_list.append("Current Task Statistics:")
    task_rep_list.append("-"*40)
    # displays the total number of tasks created
    number_of_tasks = len(task_status)
    task_rep_list.append(f"Number of tasks created in total: {number_of_tasks}")
    # displays the total number of tasks completed
    completed_tasks = 0
    for status in task_status:
        if status == "Yes":
            completed_tasks += 1
    task_rep_list.append(f"Number of tasks completed: {completed_tasks}")
    # displays the total number of tasks uncompleted
    uncomplete_tasks = number_of_tasks - completed_tasks
    task_rep_list.append(f"Number of tasks uncompleted: {uncomplete_tasks}")

    # displays the total number of tasks that are overdue
    tasks_overdue = 0
    for date in task_due_date:
        if over_due(date, current_date):
            tasks_overdue += 1

    task_rep_list.append(f"Number of tasks overdue: {tasks_overdue}")

    # displays the percentage of tasks that uncompleted
    percentage_uncompleted = round((100 / number_of_tasks) * uncomplete_tasks, 2)
    task_rep_list.append(f"Percentage of tasks uncompleted: {percentage_uncompleted}%")
    # displays the total percentage of tasks that are overdue
    percentage_overdue = round((100 / number_of_tasks) * tasks_overdue, 2)
    task_rep_list.append(f"Percentage of tasks overdue: {percentage_overdue}%")

    return task_rep_list


# creates a report for the current user and their tasks
# the values are all returned as a list
# the list can then be printed or written to a file
def user_report(active_user, username_list, task_assigned_user, task_status, task_due_date, current_date):

    user_rep_list = []
    # displays the title for the statistics
    user_rep_list.append("\n")
    user_rep_list.append("-"*40)
    user_rep_list.append("Current User Statistics:")
    user_rep_list.append("-"*40)

    # displays the total number of users registered to date with the app
    number_of_users = len(username_list)
    user_rep_list.append(f"Number of users registered in total: {number_of_users}")

    # displays the total number of tasks created in the app
    number_of_tasks = len(task_status)
    user_rep_list.append(f"Number of tasks created in total: {number_of_tasks}")

    # creates a dictionary with the indexes of all the tasks assigned to the user
    user_assigned_tasks = index_dict(active_user, task_assigned_user)
    number_of_user_tasks = len(user_assigned_tasks)
    user_rep_list.append(f"Number of tasks assigned to the user are: {number_of_user_tasks}")

    # percentage of tasks assigned to user
    percentage_uncompleted = round((100 / number_of_tasks) * number_of_user_tasks, 2)
    user_rep_list.append(f"Percentage of the total created tasks assigned to the user is: {percentage_uncompleted}%")

    # calculates the number of assigned tasks completed and the number that are overdue
    tasks_overdue = 0
    tasks_completed = 0
    for task in user_assigned_tasks:
        if task_status[user_assigned_tasks[task]] == "Yes":
            tasks_completed += 1
        else:
            if over_due(task_due_date[user_assigned_tasks[task]], current_date):
                tasks_overdue += 1

    # percentage of tasks assigned to user that are completed
    percentage_assigned_completed = round((100 / number_of_user_tasks) * tasks_completed, 2)
    user_rep_list.append(f"Percentage of assigned tasks the user completed are: {percentage_assigned_completed}%")

    # percentage of tasks assigned to user still uncompleted
    uncompleted_tasks = number_of_user_tasks - tasks_completed
    percentage_assigned_uncompleted = round((100 / number_of_user_tasks) * uncompleted_tasks, 2)
    user_rep_list.append(f"Percentage of assigned tasks to the user that are uncompleted are: {percentage_assigned_uncompleted}%")

    # percentage of uncompleted tasks that are overdue
    percentage_assigned_overdue = round((100 / uncompleted_tasks) * tasks_overdue, 2)
    user_rep_list.append(f"Percentage of assigned tasks to the user that are overdue are: {percentage_assigned_overdue}%")

    return user_rep_list


# bools for valid credentials
# both remain false until the user logs-in
valid_username = False
valid_password = False

# while loop continues to loop until the user enters valid credentials
# if the user
while not valid_username or not valid_password:
    # usernames are set to lower to prevent it from being case sensative
    active_username = input("\n Please enter your username.\n\n-> ").lower()
    active_username = active_username.strip()
    if active_username in username_list:
        valid_username = True
        # stores the index value of the username
        # this values is then compared with the password index
        # both values on the same index number need to match user input
        # for example username[3] and password[3]
        username_index = username_list.index(active_username)

        print("\n Please enter your password.")
        active_password = input(" Passwords are case sensative!\n\n-> ")
        if active_password == password_list[username_index]:

            print(f"\n\n You're now logged in as \"{active_username}\".")
            valid_password = True
        else:
            print("\n", "*"*50, "\n\t\t LOGIN ERROR!\n", "*"*50)
            print("\n Password and Username do not match, please try again\n")
    else:
        print("\n", "*"*50, "\n\t\t LOGIN ERROR!\n", "*"*50)
        print("\n Username not found, please try again\n")


# sets bool for main loop of the app
# once this is set to false the app will terminate
main_loop = True
# used to loop over a specific selection
user_selection_active = False
# set to true if the user selects to bypass an error
bypass_error = False

# main loop continues until the user selects to exit
# displays all options in the main menu for the user
while main_loop:
    print("\n", "-"*40, "\n\tMAIN MENU\n", "-"*40)
    print(" You have the following options:\n")
    # for loop used to display menu options
    # if the user is not the admin, "st" is not displayed
    for item in range(len(user_options)):
        if user_options[item] == "ds" and active_username == "admin":
            print(f" {user_options[item]} - {option_description[item].capitalize()}")
        elif user_options[item] != "ds":
            print(f" {user_options[item]} - {option_description[item].capitalize()}")

    # saves the user's input as a variable
    # the variable is stripped to remove accidental spaces
    user_selection = input("\n Please enter your selection here\n-> ").strip()

    # returns true if the user selects to register a new user
    if user_selection == user_options[0]:
        # active_username is passed to the reg_user funtion to confirm user privileges
        # username_list is also passed to confirm if the user already exists on the database
        reg_user(active_username, username_list)

    # condition returns true if the user selects to add a new task
    elif user_selection == user_options[1]:
        # username_list is passed to the reg_user funtion
        # this is used to confirm if the task is assigned to a registered user
        add_task(username_list)

    # if the user selects to view all tasks, this condtion returns true
    # all tasks are displayed to the user
    # afterwards the user is asked if they want to return to the main menu
    # if the deny, they will be shown all tasks again
    elif user_selection == user_options[2]:
        view_all(task_assigned_user)

    # if the user selects to view all tasks assigned to them, this condtion returns true
    # all tasks assigned to the user are displayed
    # if no tasks are assigned to the user they are informed no tasks are assigned to them
    # afterwards the user is asked if they want to return to the main menu
    # if the deny, they will be shown all tasks again
    elif user_selection == user_options[3]:
        view_mine(active_username, task_assigned_user, task_title, task_description, task_creation_date, task_due_date, task_status, username_list)

    # if the user selects to generate a report, this selection returns true
    # after the files are created and saved, the funtion displays that they are created
    # the user is then given the option to return to the main menu
    elif user_selection == user_options[4]:

        user_selection_active = True
        while user_selection_active:
            write_reportfile(task_report(task_status, task_due_date, current_date), "task_overview")
            write_reportfile(user_report(active_username, username_list, task_assigned_user, task_status, task_due_date, current_date), "user_overview")
            print("\n")
            print("-"*100)
            print(" The user reports have now been created and saved as \"task_overview.txt\" and \"user_overview.txt\".")
            print("-"*100)

            if user_confirmation("\n You will now be returned to the main menu."):
                user_selection_active = False

    # if the user selects to view statistics this section returns true
    # this option is only available to the admin account
    # statistics are read from the txt files and displayed
    # if not files are present an error is displayed
    # afterwards the user is asked if they want to return to the main menu
    # if the deny, they will be shown all tasks again

    elif user_selection == user_options[5] and active_username == "admin":
        user_selection_active = True
        while user_selection_active:
            try:
                task_overview = read_file("task_overview")
                for line in task_overview:
                    print(f" {line}")

                user_overview = read_file("user_overview")
                for line in user_overview:
                    print(f" {line}")
            except FileNotFoundError:
                print("\n", "*"*75)
                print(" Reports have not yet been generated.\n Please generate a report first from the \"gr\" option in the main menu.")
                print("", "*"*75)
            if user_confirmation("\n You will now be returned to the main menu."):
                user_selection_active = False

    # if the user selects to exit, the following condition retuns true
    # if they deny they will be returned to the main menu
    # if they confirm, the loop will be termined and the app will close
    elif user_selection == user_options[6]:
        if user_confirmation("\n The application will now close."):
            main_loop = False

    # else statement is passed if the user enters an option not available
    # the error message is displayed and the loop repeated
    else:
        print("\n", "*"*50, "\n\t\t INVALID SELECTION!\n", "*"*50)
        print(" Please select an item form the menu\n", "*"*50)
