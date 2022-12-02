Both the questions are separate Maven projects and need to be imported in IntelliJ as Maven Projects separately

DIRECTORY STRUCTURE:

The source code can be found at PartA/src/main/java/org.example/Main.java
It is a single file containing all the required code

pom.xml file is present in PartA folder along with this file.
This is also where Runtimes_partA will be generated or is present depending on whether the code has been ran or not.
Runtimes_partA has the runtimes for sorting the randomly generated array with 1 or 2 threads, i.e., with or without parallelization.

EXECUTION:

To execute the code, ensure that PartA is the current working directory and execute the Main.java file using IntelliJ.
Also, if running the code repeatedly, it's best to delete or atleast close the textfile Runtimes_partA, even though code ideally should be able to overwrite it.

OUTPUT:

For smaller length arrays, the sorting is faster using single thread due to overhead of thread creation and context switching on the PCB
For larger lengths, the final results should indicate faster runtimes using parallelization.
Minimizing the background processes will lead to more consistent results.