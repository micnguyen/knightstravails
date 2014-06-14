To run that from the command line (with Java 1.6 or greater installed):

java -jar knightstravails [ROW_SIZE] [COL_SIZE] [STARTING_LOCATION] [ENDING_LOCATION] [DEBUG_TOGGLE]

Where: 

[ROW_SIZE] & [COL_SIZE] are numbers. 
[STARTING_LOCATION] & [ENDING_LOCATION] are algebraic chess notation.
[DEBUG_TOGGLE] is 1 for on, 0 for off to print debug information (will slow down program on large ROW_SIZE & COL_SIZE)

Examples:

java -jar knightstravails.jar 8 8 A8 B7 0
java -jar knightstravails.jar 2 4 A1 B3 1
java -jar knightstravails.jar 26 26 Z4 W8 0

Known Issues:
- COL_SIZE of greater than 26 will produce letters after Z from the ASCII table, ie: [1 will appear after Z1