
import java.util.*;

/*
 * Solves the 8-Puzzle Game (can be generalized to n-Puzzle)
 */

public class EightsPlayer{

    static Scanner scan = new Scanner(System.in);
    static int size=3; //size=3 for 8-Puzzle
    static int numiterations = 100;
    static int numnodes; //number of nodes generated
    static int nummoves; //number of moves required to reach goal


    static ArrayList<Node> solution_path; //stores backward path from goal to Init Node

    static int[][] hundred_iterations_data = new int[100][4]; //COLUMNS KEY- 0:Iteration,1:no of Moves,3:No of Nodes Generated

    public static void main(String[] args)
    {
        int boardchoice = getBoardChoice();
        int algchoice = getAlgChoice();

        int numsolutions = 0;

        Node initNode;


        if(boardchoice==0)
            numiterations = 1;

        for(int i=0; i<numiterations; i++){

            if(boardchoice==0)
                initNode = getUserBoard();
            else
                initNode = generateInitialState();//create the random board for a new puzzle

            boolean result=false; //whether the algorithm returns a solution

            switch (algchoice){
                case 0:
                    result = runBFS(initNode); //BFS
                    break;
                case 1:
                    result = runAStar(initNode, 0); //A* with Manhattan Distance heuristic
                    break;
                case 2:
                    result = runAStar(initNode, 1); //A* with your new heuristic
                    break;
            }


            //if the search returns a solution
            if(result){

                numsolutions++;
                printSolution(initNode);
                System.out.println("Number of nodes generated to solve: " + numnodes);
                System.out.println("Number of moves to solve: " + nummoves);
                System.out.println("Number of solutions so far: " + numsolutions);
                System.out.println("_______");

                hundred_iterations_data[numiterations-1][0] = numiterations;
                hundred_iterations_data[numiterations-1][1] = nummoves;
                hundred_iterations_data[numiterations-1][2] = numnodes;

                nummoves = 0;
                numnodes = 0;


            }
            else
                System.out.print(".");

        }//for



        System.out.println();
        System.out.println("Number of iterations: " +numiterations);

        if(numsolutions > 0){
            System.out.println("Average number of moves for "+numsolutions+" solutions: "+nummoves/numsolutions);
            System.out.println("Average number of nodes generated for "+numsolutions+" solutions: "+numnodes/numsolutions);
        }
        else
            System.out.println("No solutions in "+numiterations+" iterations.");

    }


    public static int getBoardChoice()
    {

        System.out.println("single(0) or multiple boards(1)");
        int choice = Integer.parseInt(scan.nextLine());

        return choice;
    }

    public static int getAlgChoice()
    {

        System.out.println("BFS(0) or A* Manhattan Distance(1) or A* RowColEval(2)");
        int choice = Integer.parseInt(scan.nextLine());

        return choice;
    }


    public static Node getUserBoard()
    {

        System.out.println("Enter board: ex. 012345678");
        String stbd = scan.nextLine();


        int[][] board = new int[size][size];

        int k=0;

        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                //System.out.println(stbd.charAt(k));
                board[i][j]= Integer.parseInt(stbd.substring(k, k+1));
                k++;
            }
        }


        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                //System.out.println(board[i][j]);
            }
            System.out.println();
        }


        Node newNode = new Node(null,0, board);

        return newNode;

    }




    /**
     * Generates a new Node with the initial board
     */
    public static Node generateInitialState()
    {
        int[][] board = getNewBoard();

        Node newNode = new Node(null,0, board);

        return newNode;
    }


    /**
     * Creates a randomly filled board with numbers from 0 to 8.
     * The '0' represents the empty tile.
     */
    public static int[][] getNewBoard()
    {

        int[][] brd = new int[size][size];
        Random gen = new Random();
        int[] generated = new int[size*size];
        for(int i=0; i<generated.length; i++)
            generated[i] = -1;

        int count = 0;

        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                int num = gen.nextInt(size*size);

                while(contains(generated, num)){
                    num = gen.nextInt(size*size);
                }

                generated[count] = num;
                count++;
                brd[i][j] = num;
            }
        }
		
		/*
		//Case 1: 12 moves
		brd[0][0] = 1;
		brd[0][1] = 3;
		brd[0][2] = 8;
		
		brd[1][0] = 7;
		brd[1][1] = 4;
		brd[1][2] = 2;
		
		brd[2][0] = 0;
		brd[2][1] = 6;
		brd[2][2] = 5;
		*/

        return brd;

    }

    /**
     * Helper method for getNewBoard()
     */
    public static boolean contains(int[] array, int x)
    {
        int i=0;
        while(i < array.length){
            if(array[i]==x)
                return true;
            i++;
        }
        return false;
    }


    /**
     * Prints out all the steps of the puzzle solution and sets the number of moves used to solve this board.
     * Traces path from current Node to Root Node
     */
    public static void printSolution(Node node) {

        int path_length = solution_path.size();
        if(path_length > 0) {
            nummoves = path_length - 1;
        }else {nummoves = 0;}

        Node current;

        //Read solution_path backwards
        for (int index = path_length-1;index >= 0;index--){
            //get board at current node and update value
            current = solution_path.get(index);
            current.print(current);
        }


    }

    private static void storeSolution(Node goal){ //Stores path to goal

        solution_path = new ArrayList<Node>();
        Node current_node = goal;

        while (current_node.getparent() != null){
            solution_path.add(current_node);
            current_node = current_node.getparent();
        }
        solution_path.add(current_node); //Adds root node
    }


    /**
     * TO DO:
     * Runs Breadth First Search to find the goal state.
     * Return true if a solution is found; otherwise returns false.
     */
    public static boolean runBFS(Node initNode)
    {
        Queue<Node> Frontier = new LinkedList<Node>();
        ArrayList<Node> Explored = new ArrayList<Node>();

        Frontier.add(initNode);
        int maxDepth = 13;

        while(!Frontier.isEmpty()){
            Node current  = Frontier.remove(); //Remove visited Node

            if (current.isGoal()){
                storeSolution(current);
                return  true;
            }

            else if (current.getdepth() <= maxDepth){

                Explored.add(current);

                ArrayList<int[][]> expanded_states = current.expand();
                for (int[][] possible_state : expanded_states){

                    Node state_node = new Node(current,current.getdepth()+1,possible_state);

                    if (!Frontier.contains(state_node) && !Explored.contains(state_node)){
                        Frontier.add(state_node);
                        numnodes++;
                    }
                }
            }

        }

        return false;

    }//BFS




    /***************************A* Code Starts Here ***************************/

    /**
     * TO DO:
     * Runs A* Search to find the goal state.
     * Return true if a solution is found; otherwise returns false.
     * heuristic = 0 for Manhattan Distance, heuristic = 1 for your new heuristic
     */
    public static boolean runAStar(Node initNode, int heuristic) {

            //NODE Comparator which compares Nodes according to their Heuristic sum
                    Comparator<Node> nodeComparator = new Comparator<Node>() {
                        @Override
                        public int compare(Node o1, Node o2) {
                            if (o1.gethvalue() > o2.gethvalue()) return 1;
                            if (o2.gethvalue() > o1.gethvalue()) return -1;
                            return 0;
                        }
                    };

        if (heuristic == 0) {

            PriorityQueue<Node> Frontier = new PriorityQueue<Node>(50,nodeComparator);
            Queue<Node> Explored = new LinkedList<Node>();
            HashMap<Node,Node> nodes_in_Frontier = new HashMap<Node, Node>();//Parallel Hashmap to increase time of contains() and get()

            initNode.setgvalue(0);
            initNode.sethvalue(initNode.evaluateHeuristic() + initNode.getgvalue());

            Frontier.add(initNode);
            nodes_in_Frontier.put(initNode,initNode);

            int maxDepth = 13;
            numnodes++;

            while (!nodes_in_Frontier.isEmpty()) {
                //Remove minimum Node from both Frontier and Nodes_in_Frontier; then add to Explored
                Node x = Frontier.poll();
                nodes_in_Frontier.remove(x);

                if(x.isGoal()){
                    storeSolution(x);
                    return true;
                }
                else if(x.getdepth() <= maxDepth){
                    //Arraylist Stores Expanded Node
                    Explored.add(x);
                    ArrayList<int[][]> expanded_states = x.expand();

                    //Looks at all Possible states emanating from current Node
                    for (int[][] possible_state : expanded_states){
                        Node c = new Node(x,x.getdepth()+1,possible_state);

                        //Sets costs, and parent, for current Node
                        if (!Explored.contains(c)){
                            c.setgvalue(x.getgvalue()+1);
                            c.sethvalue(c.evaluateHeuristic()+ c.getgvalue());
                            c.setparent(x);

                            if(nodes_in_Frontier.containsKey(c))
                            {
                                Node c_in_frontier = nodes_in_Frontier.get(c);

                                //Update Cost of c if cost is less than node c in Frontier
                                if(c.gethvalue() < c_in_frontier.gethvalue()) {
                                    c_in_frontier.setgvalue(c.getgvalue());
                                    c_in_frontier.sethvalue(c.gethvalue());
                                }
                            }
                            //Adds node to both frontier and nodes_in_frontier if not already existing
                            else if(!nodes_in_Frontier.containsKey(c))
                            {
                                Frontier.add(c);
                                nodes_in_Frontier.put(c,c);
                                numnodes++;
                            }
                        }
                        else{
                        System.out.println("True");}
                    }

                }
            }
        return false;
        }
        else if (heuristic == 1) {

            PriorityQueue<Node> Frontier =  new PriorityQueue<Node>(10,nodeComparator);
            Queue<Node> Explored = new LinkedList<Node>();
            HashMap<Node,Node> nodes_in_Frontier = new HashMap<Node, Node>(); //Parallel Hashmap to increase time of contains() and get()

            initNode.setgvalue(0);
            initNode.sethvalue(initNode.evaluateCustomHeuristic() + initNode.getgvalue());

            Frontier.add(initNode);
            nodes_in_Frontier.put(initNode,initNode);
            int maxDepth = 13;
            numnodes++;

            while (!nodes_in_Frontier.isEmpty()) {
                //Remove minimum Node from both Frontier and Nodes_in_Frontier; then add to Explored
                Node x = Frontier.poll();
                nodes_in_Frontier.remove(x);
                Explored.add(x);

                if(x.isGoal()){
                    storeSolution(x);
                    return true;
                }
                else if(x.getdepth() <= maxDepth){
                    //Arraylist Stores Expanded Node
                    ArrayList<int[][]> expanded_states = x.expand();

                    //Looks at all Possible states emanating from current Node
                    for (int[][] possible_state : expanded_states){

                        Node c = new Node(x,x.getdepth()+1,possible_state);


                        if (!Explored.contains(c)){
                            //Sets costs, and parent, for current Node
                            c.setgvalue(x.getgvalue()+1);
                            c.sethvalue(c.getgvalue()+c.evaluateCustomHeuristic());
                            c.setparent(x);

                            //Checks nodes_in_frontier (HashMap) for current value in O(1) time
                            if(nodes_in_Frontier.containsKey(c)){

                                //Gets value from HashMap to update costs in Frontier
                                Node c_in_frontier = nodes_in_Frontier.get(c);

                                //Update Cost of c if cost is less than node c in Frontier
                                if(c.gethvalue() < c_in_frontier.gethvalue()) {
                                    c_in_frontier.setgvalue(c.getgvalue());
                                    c_in_frontier.sethvalue(c.gethvalue());
                                }
                            }
                            //Adds node to both frontier and nodes_in_frontier if not already existing
                            else if(!nodes_in_Frontier.containsKey(c)){
                                Frontier.add(c);
                                nodes_in_Frontier.put(c,c);
                                numnodes++;
                            }
                        }

                    }
                }

            }
        }

        return false; //No solution Found
    }

}