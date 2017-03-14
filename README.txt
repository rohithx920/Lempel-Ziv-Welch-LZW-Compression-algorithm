README:
programming language: JAVA
compiler version: jdk1.8.0_71
How to run the program:
Encoder:
1)To compile 
javac Encoder.java
2)To Run
java Encoder filename.txt bit_length EX:java Encoder input.txt 12

Decoder:
1)To compile 
javac Decoder.java
2)To Run
java Decoder filename.txt bit_length EX:java Encoder input.lzw 12


NOTE: Input files must be in the working the directory.

Encoder design:

Encoder class has two methods one is compress() and UTF16().

1)compress method attributes:
List<Integer> compress(String uncompressed, double maxsize);

2)UTF16():
void UTF16(List<Integer> mylist, String location);

Logic:

1)Build the ASCII dictionary from 0 to 255 and map them using HashMap
2)If present_string + next_string key in table present_string=present_string + next_string;
3)Else check for table.size() < maxsize if true add the present_string to list and map to table 
and assign present_string to next_string

UTF16 method:

Creates the lzw file and uses UTF16BE encoding and writes that into the file.
                    
Decoder design:

Read the UTF16BE fie and decode them and supply compressed list to decode method.

Decoder class has three methods:
 
void createFile(String location, String decode)
List<Integer> decimalList(String location) 
String Decoder(List<Integer> compressed)   

1)createFile() does creates the decoder file and writes data to that file.
2)decimalList() does return the list with ASCII values or decimal numbers.
3)Decoder() does docoding and bring the orginal text.

Storing encoded data in specified format works good
Code works good for the given example files.



 
        