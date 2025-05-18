
# Exercise - log analyzer
## Details:
This program was made as part of Internet Programming and the Java
Programming Language course.  
In this exercise I learned:  
1.how OOP design works in java.  
2.how multithreading can be implemented in java.  


## Design Patterns used:
1. Singletons - Properties manager, main data center, and factories are singleton classes.
2. Factory - Analyzer objects and storage objects are created using factory methods.
3. Strategy - Strategy pattern is used to select the different export strategy for the data, for expanding all that is 
necessary to do is to implement a new class that implements the Exporter interface.
4. Decorator - the decorator pattern is used to stack log analyzing methods, this allows for a single object to analyze every method 
   and to add new methods without changing the existing code. to add a new method, all that is necessary is to extend the LogAnalyzerDecorator class.

## Contact
Name: Yehu Raccah  
Email: yhrcch@gmail.com

