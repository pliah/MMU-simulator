##MMU PROJECT
###Description
This project stimulates the memory management unit of the computer, using different paging algorithms.
it divides into two parts:
* The server side (simulates the memory management unit) - which is in charge of getting a request from the client,
create a new thread to handle it - by passing the request to the memory's controller, 
and send a response back.
* The client side (simulates the OS)- which is in charge of the graphical user interface,
observe the user actions and send his requests to the server.
###Usage
open each part (the client and the server)
in different windows, and then run:

__first of  run  the server side__ by:

 * going to the com.hit.server and run CacheUnitDriver. 
 * typing ```START``` in the command line                                         

__second of all run the client side__ by:

* going to the com.hit.driver and run CacheUnitClientDriver.

* now you can send requests using the GUI.

to stop the current server, type ```SHUTDOWN``` in the server command line.
