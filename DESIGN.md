#CellSociety: Design Document

###Introduction:

For our CellSociety program, flexibility is key, and one of the biggest design issues we face. The program needs to be able to accommodate any set of rules with minimal changes to the actual code. In order to accomplish this, the program with be very compartmentalized. Ideally, none of the classes should be able to directly reference any other classes internals (as is good coding practice). The three main components of this program are the Parser, which handles the parsing of the input XML file, the GUI, and the Grid, which handles all of the logic and storage. This last component will most likely be the hardest to implement, mainly because it needs to maintain a level of flexibility in order to accommodate various rule sets.

###Overview:

As previously stated, the program will be split into three main parts. The first, the Parser, will be in charge of decoding the XML file. This file will contain the name of the simulation, which rule set to use, and any initial state of the grid. The Parser component will be made up of a single class, Parser. This class will decode the XML file and provide the GUI with the simulation name, and provide the Grid class (more on this later) with the initial state and rule set. Next, the GUI, is in charge of the display window. This will be represented by a single GUI class. This class will contain a number of utility methods as well that can be accessed by other classes. For example, the GUI class will have a public showErrorDialog method that takes an error message and displays a popup with that message. Lastly, the Grid component will be made up of four classes, Grid, Cell, Rule, and State. Grid will contain a 2d array of Cell objects and a number of utility methods (ie findNeighbors). Additionally, Grid will initialize the cells. Cell will contain its current State, as well as its Rule. 

In order to ensure parallelism, the stepping of each Cell’s state will be handled by two methods, State findNextState(), and void update() - this way, each Cell’s next State will only depend on its neighbors’ current states (more on this later). There will be an update method in Cell that supplies itself and its neighbors to its Rule, which then returns the next State for the Cell. None of this interaction will be seen by Grid. Therefore, Rule will contain a determineNextState() method that provides the next State for a given cell. Each different simulation will have a Rule subclass that extends this method. Lastly, State will contain information about the state of the cell (active boolean, color etc.). The main purpose of the State class is to enable more flexibility. For example, if the simulation requires that a cell can be one of ten different states, that can be achieved at least somewhat easily. Additionally, the only information the GUI class will receive from the Grid on a single update tick is a 2D array of States.

Rules will be implemented on a very basic level, as the Rule class is designed for extension. The fundamental components of any single rule for a generalized cellular automata (the way we broke it down) are:

1. the type of State that, if held by the neighbor cells, has influence on this Cell’s next State (i.e. if nearby cells are diseased, consider them, but if not, disregard them)
2. the Cells which are actually considered (how far a State’s effects are felt)
3. the resulting State (to be applied if the rule’s conditions are met), and 
4. a threshold, which will be an integer value determining the point at which a rule is actually applied (i.e. only if 5 neighbor states or more are active - threshold is the integer 5)	

Consequently, these components are all implemented in our Rule class. Additional mechanics (such as the addition of chance, for example) can be implemented in subclasses of this master. However, the master will contain implementation to allow for multiple different states to contribute to one rule - to accommodate, for example, for different degrees of neighboring illness to contribute to a healthy Cell’s becoming sick. Additionally, it will allow for a neighbor Cell’s effect to degrade with distance away from the target Cell, as if the effect of a State is felt less, the farther away the Cell is.

####Basic Workflow

For clarity’s sake, here is the progression of a single update tick. Again, the workflow of the code is arranged to ensure parallelism (to ensure that all updates happen simultaneously).

__Step 1 - Find Next States__
	1. Grid calls each Cell’s findNextState() (providing each neighbor Cell’s myCurState value)
	2. That cells update calls its Rule’s determineNextState()
	3. That Rule runs calculations and returns the next State of the Cell
	4. Cell stores its next state in State myNextState

__Step 2 - Apply Changes__
	1. Grid calls each Cell’s update()
	2. Cell changes its myCurState to the value of myNextState, thus applying changes
	3. The Grid ships 2d array of States to the GUI Class

###User Interface:

The GUI will have four main elements, the Grid, and Pause, Play, and Step buttons. The User can also click on any Cell to cycle through its possible States. Any errors will be communicated through popup dialog windows.

![Alt text](https://github.com/duke-compsci308-spring2015/cellsociety_team01/blob/master/gui_drawing.png "GUI Drawing")

Additionally, the User can supply an XML file to be read in at the start of the program. There will also be a menu bar along the top of the screen, through which the user can swap XML files while the program is running.


###Design Details:

* Class Grid
	* Properties
		* int myHeight
		* int myWidth
		* int myTiming
			* This will serve as our frame rate, unless the user chooses to step through the simulation
		* Cell[][] myCells
			* Fundamental grid object
	* Methods
		* void checkGrid()
			* checks each cell in the grid using myRules and the cell’s findNextState(Rule[] rules)
		* void updateGrid()
			* calls each cell’s update() method
		* void initialize()
			* makes myCells based on x and y
	* Setters
		* setHeight(int x)
		* setWidth(int y)
		* setTiming(int?? Timing)
* Class Cell
	* Properties
		* int myX
		* int myY
		* State myCurState
			* Current state, used by the rest of the grid when determining next States
		* State myNextState
			* Next state, to be copied into myCurState when update() is called
		* Rule myRule
	* Methods
		* State findNextState()
			* Checks rules and returns a next state, or the current one, if no change is needed 
		* void update()
			* reassigns the value of myNextState to myCurState, myNextState is now ready to be overwritten
	* Setters
		* setX(int x)
		* setY(int y)
		* setCurState(State s)
		* setNextState(State s)
* Class State
	* Properties
		* boolean myActive
		* Color myColor
	* Methods
		* boolean equals(State s)
		* int hashCode()
			* to be used in equals(State s)
	* Setters
		* void setActive(boolean active)
		* void setColor(Color color)
* Class Rule
	* Properties
		* int myPriority
			* Determines how important this rule is if multiple rules have conditions that are met
		* Point2D[] myFactors
			* Tells which cells (int relation to the current one) need to be considered, will be numbers like (-1,-1) ; (0, 1) ; etc.
		* int myLocality
			* Tells the depth of the considered cells, 0 by default. 
			* Used to populate myFactors if specific factors are not specified
		* State[] myCause 
			* The state(s) that, if held by neighbors, would contribute to changing this Cell’s myCurState
		* int[] myWeights
			* how heavily each possible cause affects the total threshold, each entry corresponds to same position in myCause 
		* State myEffect
			* resulting state for this cell if myThreshold is reached
		* int myThreshold
			* point at which the causes add up to trigger the effect
	* Methods
		* determineNextState()
* Class Parser
	* Properties
		* File inputFile
	* Methods
		* readInput()
			* Reads the given file.

###Design Considerations:

We spent a good deal of time discussing how best to handle the Rule and State classes. In the end we chose to implement them the way we have discussed, because although this way causes a lot of Classes to be involved in the update process, it keeps everything more compartmentalized, open, and clear.
We also discussed how the GUI would interact with the Grid and the Parser Class. The main issue was that we were unsure how to have the Parser pass initialization parameters to the Grid and GUI Classes. In the end we decided to conduct this through a menu option, which simply lets you choose an xml file to initialize from. This will allow the user to select any kind of simulation setup they would like to run. 

###Team Responsibilities:

Peter
* Primary - Parser Class
* Secondary - Rule Class

Patrick
* Primary - Rule Class
* Secondary - Cell Class

Jangsoon
* Primary - Cell
* Secondary - State

John
* Primary - GUI
* Secondary - Grid
