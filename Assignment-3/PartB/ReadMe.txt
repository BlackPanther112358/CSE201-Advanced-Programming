Both the questions are separate Maven projects and need to be imported in IntelliJ as Maven Projects separately

DIRECTORY STRUCTURE:

The source code can be found at PartB/src/main/java/org/example/Main.java
It is a single file containing all the required code

pom.xml file is present in PartB folder along with this file.
This is also where Runtimes_partB will be generated or is present depending on whether the code has been ran or not.
Runtimes_partB has the runtimes for operations on binary tree with 1, 2 or 4 threads, i.e., with or without parallelization.

EXECUTION:

To execute the code, ensure that PartB is the current working directory and execute the Main.java file using IntelliJ.
Also, if running the code repeatedly, it's best to delete or atleast close the textfile Runtimes_partB, even though code ideally should be able to overwrite it.

OUTPUT:

For larger lengths, the final results should indicate faster runtimes using parallelization.
Minimizing the background processes will lead to more consistent results.

While search times are consistently faster for 4 threads, with it being slowest for single thread, there can be some inconsistencies for time to create the binary tree
with 2 threads, but, the one using 4 threads should always be the fastest if not the one using 2 threads.
The tree heights are consistent for all threads and different randomly generated inputs, as they should for a balanced tree.
