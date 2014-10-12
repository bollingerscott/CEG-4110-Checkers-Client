package RMIConnection.Interfaces;

/**
 * Defines codes sent through TCP describing messages.
 * @author derk 8.14.08
 */
public interface TCPMsg {
	public String endOfMsg="<EOM>";
	//testing
	 /**TCP messages from clients
     */
    public int MSG_ALL = 101;        //client sends message <1> to everyone in lobby
    public int MSG_C = 102;          //client sends message <2> to client <1>
    public int MAKE_TBL = 103;       //client wants to make and sit at a table
    public int JOIN_TBL = 104;       //client wants to join table id <1>
    public int READY = 105;          //client is ready for game to start
    public int MOVE = 106;           //client moves from <1> to <2> 
    public int LEAVE_TBL = 107;      //client leaves the table
    public int QUIT = 108;           //client leaves the server
    public int ASK_TBL_STATUS = 109;  //client <1> is asking for the status of table <2>
    public  int OBSERVE_TBL = 110;     //client <1> wants to observer table <2>
    public  int STOP_OBSERVING = 115;  //client <1> wants to stop observing table <2>;
    public  int REGISTER = 111;        //client <1> is registering, with password <2>
    public  int LOGIN = 112;           //client <1> is logging in, using password <2>
    public  int UPDATE_PROFILE = 113;  //client <1> is updating profile. 
    public  int GET_PROFILE = 114;     //client <1> wants to get the profile for client <2>

    /** Go Messages to server */
    public int GO_MAKE_TBL = 116;     //client <1> wants to make and sit at a table to play go
    public int GO_MOVE = 117;         //client <1> places a stone at <2>. 

     /** TCP messages to clients
     */
    public int CONN_OK = 200;        //connection to server establised
    public int IN_LOBBY = 218;       //client has joined the lobby
    public int OUT_LOBBY = 213;      //client has left the lobby
    public int MSG = 201;            //client received message <3> from <1>. If <2> =1, it is private.
    public int NEW_TBL = 202;        //a new table has been created with id <1> 
    public int GAME_START = 203;     //The game at the table has begun
    public int COLOR_BLACK = 204;    //client is playing as black 
    public int COLOR_RED = 205;      //client is playing as red
    public int OPP_MOVE = 206;       //opponent has moved from <1> to <2>
    public int BOARD_STATE = 207;  //the board state on table <1> is <2>
    public int GAME_WIN = 208;       //client has won their game
    public int GAME_LOSE = 209;      //client has lost their game
    public int TBL_JOINED = 210;     //client has joined table <1>
    public int TBL_LEFT = 222;		 //client has left table <1>
    public int WHO_IN_LOBBY = 212;   //the clients <1> <2> ... <n> are in the server
    public int NOW_IN_LOBBY = 214;    //client <1> is now in the lobby
    public int WHO_ON_TBL = 219;     //the clients <2> <3> are on table with tid <1>. <2> is black. <3> is red. If either is -1, the seat is open.   
    public int TBL_LIST = 216;       //the current tables are <1> <2> ... <n>. Clients will have to request status.
    public int NOW_LEFT_LOBBY = 217;	//the client <1> has left the lobby.
    public int OPP_LEFT_TABLE = 220;   //your opponent has left the game.
    public int YOUR_TURN = 221;       //it is now your turn.
    public  int NOW_OBSERVING = 230;   //you are now observing table <1>. 
    public  int STOPPED_OBSERVING = 235;   //you stopped observing table <1>.
    public  int REGISTER_OK = 231;     //your registration is complete.
    public  int LOGIN_OK = 232;        //you have logged in successfully.
    public  int PROFILE_UPDATED = 233; //your profile has been updated.
    public  int USER_PROFILE = 234;    //the profile for <1>. <4> is its description, <2> is wins, <3> is losses.


    /** Go messages to clients */
    public int TABLE_GAME = 240;      //The table with id <1> is playing game <2>. <2> is the string "go" or "checkers".
    public int GO_COLOR_BLACK = 236;  //client is playing as black (go)  
    public int GO_COLOR_WHITE = 237;  //client is playing as white (go)
    public int GO_BOARD_STATE = 238;  //the Go board state on table <1> is <2>
    public int GO_OPP_MOVE = 239;     //opponent has placed a stone at <1>. 

    /** Error messages
     */
    public int NET_EXCEPTION = 400;  //network exception
    public int NAME_IN_USE = 401;    //client name already in use
    public int BAD_NAME = 408;       //the user name requested is bad.
    public int ILLEGAL = 402;        //illegal move
    public int TBL_FULL = 403;       //table you tried to join is full
    public int NOT_IN_LOBBY = 404;  //If you are not in the lobby, you can't join a table.
    public int BAD_MESSAGE = 405;   //some part of a message the server got from a client is bad.
    public int ERR_IN_LOBBY = 406;    //you cannot perform the requested operation because you are in the lobby.
    public int OPP_NOT_READY = 409;   //you made a move, but your opponent is not ready to play yet
    public int NOT_YOUR_TURN = 410;   //you made a move, but its not your turn.
    public int TBL_NOT_EXIST = 411;	  //table queried does not exist.
    public int GAME_NOT_CREATED = 412;    //called if you say you are ready on a table with no current game.
    public int ALREADY_REGISTERED = 413;  //this name is already registered.
    public int LOGIN_FAIL = 414;          //authentication failed.        
    public int NOT_OBSERVING = 415;       //client is not observing a table.
}
