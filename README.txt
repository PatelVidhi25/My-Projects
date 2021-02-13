VLSI databases commonly represent an integrated circuit (IC) as a list of rectangles. Typically, logic components are oriented rectilinearly (sides parallel to the x- and y-axis) on an IC; so, we can assume that all these rectangles are oriented accordingly. In such orientation, a rectangle can be represented by its minimum and maximum x- and y-axes. Now, an important problem in circuit design validation is to ensure that these rectangles do not overlap with each other. We can decide whether a pair of rectangles overlap in a constant time just by comparing the ranges of their x and y co-ordinates. If n rectangles are given, existence of an overlap among these can be decided in O(n^2) time. In this project, I have implemented Interval tree algorithm to obtain a(n lg n) time complexity.

This project is implmented using Eclipse IDE and built upon jdk14 environment.

Main class : 
Overlap_Rectangle.java

Execution steps as follows : 
    Location of Input file is exepcted to be in the same folder where above main java file is placed.
    Name of input file is expected to be "Input.txt"
    If not, open Overlap_Rectangle.java file and specify file name and location at line 449, inside variable 'input_file'.

- Using Command Line (it requires jdk to be installed and set as environment variable)
1. Open Command prompt.
1. In command line, go upto location where java file is placed
2. Compile it : 
	command : javac Overlap_Rectangle.java
3. No errors should be found and compilation should be successful.
4. Run it : 
	command : java Overlap_Rectangle
5. Output will be displayed on console as well as 'Output.txt' file will be extracted inside same directory.


