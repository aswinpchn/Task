# Log generation and querying tool!

## Generation Logic - (Each one runs in around 1.2 seconds.)
1. We get input from user regarding the `folder name` that you want the log to be.
2. For each minute of the day, 
    1. We convert them to epoch seconds format.
    2. Generate IP.
    3. Generate random cpu utilization.

## Query Logic - (Each query runs genrally under 0.1 seconds.)
1. We get input from user regarding the `folder name` that you want to query in.
2. There are two choices.
    1. `exit` - Exit from the console.
    2. `query` - Query for logs within a given time for a given IP.
        1. Data will be stored in LinkedHashMap.
        2. Validate the inputs.
        3. From the starting time to ending time.
            1. search for data using, `ip`, `cpu-id`.
# Algorithms.
* Storing data in a `LinkedHashMap` efficiently once the user enters a folder name in `Query`, This helps in fast data retrieval compromising space.
* In the future, `Concurrency programming` techniques like threads can be used to do concurrent processing to increase the speed of the execution even further. Note: More research has to be done though.
* `Buffered Reader` is being used to do the Reading of the large query file efficiently. This method is chosen after analysing speeds of various ways of file reading packages.
* Thorough analysis was done before choosing any key component in the tool as a small inefficiency can cause large delays in throughput.

# References
* https://www.baeldung.com/reading-file-in-java
* https://www.baeldung.com/java-write-to-file
* https://stackoverflow.com/questions/19433366/running-java-in-package-from-command-line
* https://www.baeldung.com/java-8-date-time-intro
* https://stackoverflow.com/questions/22990067/how-to-extract-epoch-from-localdate-and-localdatetime
* https://www.baeldung.com/java-read-lines-large-file
* https://www.baeldung.com/java-read-lines-large-file

## Running the program
* clone/download the repository.
* `cd` into the root folder of the repostiory.
* `javac ./src/com/aswin/*.java` - This will complile all the java files.
* `java -cp ./src com.aswin.Generate` - This will run the generation logic. (meaning, get into source folder, then execute a specific class in the package using package path and not the actual file path.>)
* `java -cp ./src com.aswin.Query` - This will run the query flow.

## Sample query
* `Query 192.168.0.0 0 2014-10-31 00:00 2014-10-31 01:00`