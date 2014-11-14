CEG-4110-Checkers-Client
========================

Checkers client for CEG 4110 Software Engineering. Team Members: Scott, Brad, Colin

Instructions
========================

Using the command line cd into the bin directory and type rmiregistry to start the rmi
If you get command not found add the jre to your path for windows users by

setx PATH “%PATH%;C:\<path to the bin directory of your JRE>\”

or through the control panel->system->advanced->environment variables

then,

run the RMIConnectionEngine.java

and then run the CheckersLobby.java and connect to the server at 137.99.11.115 with a username.

Usage
========================

In the lobby you can chat with other players logged on, create a table, join a table, or observe a table. When you
create a table you go to a ready screen to wait for another player to join. Once both players are ready
a game is started. The game window displays who you are playing against and useful statistics such as the 
number of moves and pieces left and taken. In the game you may chat with the other player. 
Double jumping is not allowed.
