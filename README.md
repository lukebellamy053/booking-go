# BookingGo Coding Challenge
Here you will find my code for the BookingGo code challenge.

There are a few small assumptions made in this code:

1) Both parts of task 1 are to be kept in the same code base but can be called separately from one another using different commands

2) The API can just call the JAR file from part 1 and parse the results rather than sending the requests itself.

## Part 1

To build the code you will need a machine running Maven and Java
1) Navigate to the ConsoleApp directory and open a terminal
2) Execute the following command to get maven to compile the code into a JAR file

 ```sh
 mvn clean compile assembly:single
 ```

This will generate single JAR file in the target directory

This jar file can be used to execute the application using the following command

### Dave's Taxis

 ```sh
 java -jar app-1-jar-with-dependencies.jar -pickup 3.410632,-2.157533 -dropoff 3.410632,-2.157533
 ```
 
This will execute the code for the Dave's Taxis part of the task

If the API has not returned any data, you will be told there is no data to show. 
Otherwise the results will be listed in rows using the following format:

{car type} - {price}



### Passengers

The second part of task 1 can be triggered by adding the passengers parameter to the above request like so
 ```sh
java -jar app-1-jar-with-dependencies.jar -pickup 3.410632,-2.157533 -dropoff 3.410632,-2.157533 -passengers 4
 ```

This code was written using the Intellij IDEA Ultimate Edition 2018.2.5 IDE 
 ## Part 2 (API)
 
 The API for this task was written in NodeJS due to the simplicity of it. To start the server, please follow the following steps:
 
 1) Open the API directory in a terminal
 2) Run the following command
 ```sh
 npm i
 ```
 
 3) If NPM installs the dependencies correctly, you can run
  ```sh
  node index.js
  ```
  To start the server on port 8080 of your machine
  
  ### Accessing the API
  
  The API can be accessed by using any HTTP Method that follows the following path:
  
```sh
http://localhost:8080/pickup/{pickup}/dropoff/{dropoff}/passengers/{passengers}
```
Example
```sh
http://localhost:8080/pickup/3.410632,-2.157533/dropoff/3.410632,-2.157533/passengers/3
```
  
  Replace the {pickup}, {dropoff} and {passengers} tags with your values to see the API response
  
  This code was written using Visual Studio Code 1.29.1
  