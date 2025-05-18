[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/w1uqWHUp)

# Exercise 1 - log analyzer

## Design Patterns used:
1. Singletons - Properties manager, main data center, and factories are singleton classes.
2. Factory - Analyzer objects and storage objects are created using factory methods.
3. Strategy - Strategy pattern is used to select the different export strategy for the data, for expanding all that is 
necessary to do is to implement a new class that implements the Exporter interface.
4. Decorator - the decorator pattern is used to stack log analyzing methods, this allows for a single object to analyze every method 
   and to add new methods without changing the existing code. to add a new method, all that is necessary is to extend the LogAnalyzerDecorator class.

## Student
Name: Yehu Raccah  
login: yehura@edu.jmc.ac.il

