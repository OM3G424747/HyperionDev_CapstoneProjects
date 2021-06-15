#imported math to calculate monthly bond repayment 
import math
#imported colored and cprint from termcolor for formating 
from termcolor import colored, cprint

#creates a funtion that loops until a valid float is provided by the user 
#the funtion takes in a string and displays it as a question for the input to the user
#the funtion also takes in a 3rd string to change the try to a float or an int. if "int" is entered it tries to change it to an int. All other input will default to a float
def valid_num_input(question_to_ask, repeat_question , int_or_float):
    loop_active = True
    while loop_active:
        #requests input from the user with the string that was passed to the funtion
        user_input = input(question_to_ask)
        try:
            if int_or_float == "int":
                user_input = int(user_input)
                return user_input
            else:
                user_input = round(float(user_input), 2)
                return user_input
        #if an exception is raised the following error message is displayed to the user 
        except:
            cprint(colored("\nPlease enter a numerical value using only numbers!\n", "yellow", attrs=['bold']))
            print(repeat_question)

#funtion to take in a string for displaying a user option or example, and another string for description
#the int that get's passed determines the number of "tab spaces" between the name and the description
def format_option(option_name, option_description, number_of_tabs):
    cprint(option_name,attrs=['bold'], end="")
    number_of_tabs = "\t" * number_of_tabs
    print(f"{number_of_tabs} {option_description}")

#funtion takes in a string to display with a specific colour.
#the bool that get's passed displays the string as bold text if True. 
#the printed text does not pass a new line to allow for combining with regular print statements to help special text stand out 
def change_format(string, color, is_bold):
    string_color = color
    if is_bold:
        cprint(string,string_color,attrs=['bold'], end="")
    else:
        cprint(string,color, end="") 

#user selection bool sets while loop for specific block of text to true, to keep looping until the user provides a valid input
user_selection = True
#sets coloured string for showing the user where to enter their selection
text_selection = colored("Enter your selection here: ","green")
#list with all the available calculator options
calculator_options =  ["investment","bond"]
#list with all the possible investment types
interest_types = ["simple", "compound"]

#sets while loop to true, so block keeps repeating until the user selects a valid calculator option
while user_selection:
    
    print(f"\nChoose either \'{calculator_options[0]}\' or \'{calculator_options[1]}\' from the menu below to proceed:\n" )
    format_option(calculator_options[0], "- to calculate the amount of interes you'll earn on interest",1)
    format_option(calculator_options[1], "- to calculate the amount you'll have to pay on a home loan",2)
    user_input = input(f"\n\n{text_selection}").lower()

    #returns true if user selected investment
    if user_input == calculator_options[0]:
        #the while loop prevents the code from reverting to a previous block if invalid input is provided - eg, a number like "0.00"
        while user_selection:
            #block displays deposit header and asks the user to enter the ammount of money they will be depositing
            format_option("\nDeposit","",0)
            deposit_option = "Please enter the total amount you'll be depositing in Rand."
            print(deposit_option)
            format_option("\nDeposit Example","- selecting \"1000.00\" would equal \"One Thousand Rand\"",1)
            user_deposit = valid_num_input(f"\n\n{text_selection}", deposit_option , "NA")
            #if the user attempts a deposit of R0.00 the following error message is displayed in yellow and bold
            if user_deposit <= 0:
                cprint(colored("\nYou need to deposit more than R0.00.\nPlease enter an ammount greater than or equal to R0.01 to continue.", "yellow", attrs=['bold']))
            
            #if the user enters a valid number the following else statement returns true 
            #the next block of code displayes the interest rate header and requests a percentage between 0 and 100 from the user
            #the while loop prevents the code from reverting to a previous block if invalid input is provided - eg, a number like "101"
            else:
                while user_selection:
                    format_option("\nInterest Rate","",0)
                    interest_option = "Please enter your interest rate percentage."
                    print(interest_option)
                    format_option("\nInterest Rate Example"," - selecting \"8\" would equal an \"Eight Percent Interest Rate\".",0)
                    user_interest_rate = valid_num_input(f"\n\n{text_selection}",interest_option, "int")
                    if user_interest_rate > 100 or user_interest_rate < 1:
                        cprint(colored("\nPlease only enter a number between 1 and 100.\n", "yellow",attrs=['bold'])) 
                    
                    #the elif statement returns true if a number equal to or lower than 100 is provided 
                    #the user is asked what they would like their investment period to be.
                    #no while loop is needed because of the build in loop in the "valid_num_input" function
                    elif user_interest_rate <= 100:
                        user_interest_rate = user_interest_rate /100  #interest percentage is calculated after the "if" and "elif" condtion to prevent unwanted results 
                        format_option("\nInvestment Period","",0)
                        investment_option = "Please enter the number of years you'd like to invest for."
                        print(investment_option)
                        format_option("\nInvestment Period Example"," - selecting \"2\" would equal a total investment period of \"Two Years\".",0)
                        user_investment_duration = valid_num_input(f"\n\n{text_selection}",investment_option, "int")
                        #the user is asked what type of interest they would like
                        #a while loop is added to prevent the code from return to the previous block if incorrect input is provided
                        while user_selection:
                            format_option("\nInterest Type","",0)
                            print(f"Please select if you'd like \'{interest_types[0]}\' or \'{interest_types[1]}\' interest.")
                            format_option(f"\n{interest_types[0]}","- interest is calculated only on the initial amount invested.",2)
                            format_option(f"{interest_types[1]}","- interest is calculated on the accumulated amount.",1)
                            user_interest_type = input(f"\n\n{text_selection}").lower()
                            
                            #if the user selects simple interest the following condition returns true
                            #annual interest is calcualted and then the total interest is calculated with the use of the annual interest
                            #interest without the deposited ammount is also calculated to display to the user 
                            if user_interest_type == interest_types[0]:
                                #Calculations for annual interest, total interest and interest without the depositied ammount
                                annual_interest = round(user_deposit * user_interest_rate,2)
                                total_interest = user_deposit + (annual_interest * user_investment_duration)
                                only_interest = round(total_interest - user_deposit)
                                
                                #the following block displays the ammount in interest only the user will earn over the period selected 
                                format_option("\n\nTotal Interest Earned:","",0)
                                print("Your total interest will be ", end="")
                                change_format("R"+str(only_interest), "blue", True)
                                print(" for a total of ", end="") 
                                change_format(str(user_investment_duration)+" Years", "blue", True)
                                print(f" investment with {interest_types[0]} interest.\n")
                                
                                #The next two statements display the initial deposit and the total return with the deposit and the interest 
                                format_option("\nInitial Deposit:", "= R" +str(user_deposit),2)
                                format_option("Total return with interest:", "= R" +str(total_interest),1)
                                #user selection bool get's set to false to end the loop and terminate the app
                                user_selection = False
                            
                            
                            #if the user selects compound interest the following condition returns true
                            #the total interest is calculated along with the interest without the deposited ammount 
                            elif user_interest_type == interest_types[1]:
                                total_interest = round(user_deposit * math.pow(( 1 + user_interest_rate), user_investment_duration),2)
                                only_interest = round(total_interest - user_deposit)
                                
                                #the following block displays the ammount in interest only the user will earn over the period selected 
                                format_option("\n\nTotal Interest Earned:","",0)
                                print("Your total interest will be ", end="")
                                change_format("R"+str(only_interest), "blue", True)
                                print(" for a total of ", end="") 
                                change_format(str(user_investment_duration)+" Years", "blue", True)
                                print(f" investment with {interest_types[1]} interest.\n")
                                
                                #The next two statements display the initial deposit and the total return with the deposit and the interest 
                                format_option("\nInitial Deposit:", "= R" +str(user_deposit),2)
                                format_option("Total return with interest:", "= R" +str(total_interest),1)
                                #user selection bool get's set to false to end the loop and terminate the app
                                user_selection = False
                            
                            #the following error is displayed in yellow and bold if the user selects an invalid option 
                            else:
                                cprint(colored(f"\nPlease select only either \'{interest_types[0]}\' or \'{interest_types[1]}\' interest.", "yellow",attrs=['bold']))

                    
    #returns true if user selected bond
    elif user_input == calculator_options[1]:
        #the while loop prevents the code from reverting to a previous block if invalid input is provided - eg, a number like "0.00"
        while user_selection:
            #block displays bond header and asks the user to enter the value of the house in rand
            format_option("\nBond Value","",0)
            bond_option = "Please enter the total value of the house in Rand for the bond."
            print(bond_option)
            format_option("\nValue Example","- selecting \"1000.00\" would equal \"One Thousand Rand\"",1)
            user_bond_value = valid_num_input(f"\n\n{text_selection}" ,bond_option, "NA")
            if user_bond_value <= 0:
                cprint(colored("\nYou need to enter a value of more than R0.00.\nPlease enter an ammount greater than or equal to R0.01 to continue.", "yellow",attrs=['bold']))
            else:
                #if the user enters a valid number the following else statement returns true 
                #the next block of code displayes the interest rate header and requests a percentage between 0 and 100 from the user
                #the while loop prevents the code from reverting to a previous block if invalid input is provided - eg, a number like "101"
                while user_selection:
                    format_option("\nInterest Rate","",0)
                    interest_option = "Please enter your interest rate percentage."
                    print(interest_option)
                    format_option("\nInterest Rate Example"," - selecting \"8\" would equal an \"Eight Percent Interest Rate\".",0)
                    user_interest_rate = valid_num_input(f"\n\n{text_selection}",interest_option, "int")
                    
                    if user_interest_rate > 100 or user_interest_rate < 1:
                        cprint(colored("\nPlease only enter a number between 1 and 100.\n", "yellow",attrs=['bold'])) 
                    
                    #the elif statement returns true if a number equal to or lower than 100 is provided 
                    #the user is asked what they would like their investment period to be.
                    # while loop is needed due to potential for invalid input - eg. entering "0" as a tern
                    elif user_interest_rate <= 100:    
                        while user_selection:
                            #interest percentage is calculated after the "if" and "elif" condtion to prevent unwanted results 
                            user_interest_rate = user_interest_rate /100 
                            monthly_interest = user_interest_rate / 12
                            format_option("\nRepayment Term","",0)
                            repay_option = "Please enter the number of months over which you'd like to repay the bond."
                            print(repay_option)
                            format_option("\nRepayment Term Example ","- selecting \"12\" would equal \"Twelve Months\" or \"One Year\".",1)
                            user_repayment_duration = valid_num_input(f"\n\n{text_selection}",repay_option, "int")
                            #the following message is displayed if the user tries to enter a term of 0 or less.
                            if user_repayment_duration <= 0:
                                cprint(colored("\nYou you cannot have a bond for a time period of less than 1 month.\nPlease enter an ammount greater than or equal to 1 to continue.", "yellow",attrs=['bold']))
                            #if the user enters a valid ammount the bond is calculated and a string is printed with the total montly repayment and the duration fo the repayments 
                            else:
                                total_repayment = round(user_bond_value * (monthly_interest *(1 + monthly_interest)** user_repayment_duration) / ((1 + monthly_interest)** user_repayment_duration - 1),2)
                                format_option("\n\nMonthly Bond Payment:","",0)
                                print("Your monthly bond repayment will be ", end="")
                                change_format("R"+str(total_repayment), "blue", True)
                                print(" for a total of ", end="") 
                                change_format(str(user_repayment_duration)+" months\n\n", "blue", True)
                                user_selection = False

    #prints yellow error message if user entered an invalid selection for a calculator option
    else:
        cprint(colored(f"\nPlease selected either \'{calculator_options[0]}\' or \'{calculator_options[1]}\'.","yellow", attrs=['bold']))
        