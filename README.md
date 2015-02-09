# cellsociety
###By Peter Moseley, Patrick Wickham, John Gilhuly, Jangsoon Park

Date Started: 1/25/15  
Date Finished: 2/8/15  
Hours Spent: 160 (40ish per person)  
  
**Roles:**  
Peter: Parser, configuration, XML Files, Exceptions  
John: GUI, visualization, Grid, findNeighbors  
Patrick: Simulations, Rules, hierarchy  
Jangsoon: Cell, State  
  
**Info:**  
Main Class: src/cellsociety_team01/Main.java  
Test Suit: XMLFiles/*.xml  
Resources: src/cellsociety_team01/resources  
  
**Use:**  
Run Program from main. Choose simulation type with File->Load XML and selecting a .xml file from the XMLFiles folder. Run the simulation with Play or step with Step. Pause will stop the simulation, and Reset will re-initialize the grid. The slider will change how fast the program runs.  
  
Options for changing parameters in the XML Files for testing are as follows:  
type: AntForaging, GameOfLife, PredatorPrey, Segregation, SlimeMolds, SpreadingOfFire, Sugarscape  
grid_shape: Square, Triangle  
grid_edge: Finite, Toroidal  
grid_outline: Yes, No  
cell_placement: Location, Percent, Random  
sim_color_scheme: Default, Water, Space  
sim_*simulation specific config variable*: any int/double  
grid width: any int  
grid height: any int  
*team options in grid*: empty, full, teamA, teamB, teamC  
xVals/yVals: space separated ints as locations in the grid  
population_percent: double between 0.0 and 1.0   
  
**Bugs:**  
We had the Segregation Simulation up and running, but when we tested it after merging to master, it was throwing a BadStateException for an unknown reason. We couldn't figure it out after 20 minutes of searching, so we assume something went wrong in the merging process but aren't sure what. It should be working though....
  
**Impressions:**  
We spent about 2-3 alone working out strictly gitHub related issues which slowed down our actual design and coding process quite a bit.  
