package GUI;


import java.io.*;
import java.util.ArrayList;

public class Read {

    public static void printStudentInfo(ArrayList<Student> deserializedStudent){
        for (int i=0; i < deserializedStudent.size(); i++){
            if (!deserializedStudent.isEmpty()){
                System.out.println("Student " + (i+1) + ": ");
                System.out.println(deserializedStudent.get(i));
                System.out.print("Courses: ");
                for (String s: deserializedStudent.get(i).getCoursesList()){
                    System.out.print(s + " ");
                }
            }
            System.out.println();
            System.out.println();
        }
    }

    public static ArrayList<Student> readStudentFromFile(String filename)
            throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream objIn = new ObjectInputStream(fileIn);
        ArrayList<Student> deserializedStudent = (ArrayList<Student>)objIn.readObject();
        printStudentInfo(deserializedStudent);
        fileIn.close();
        objIn.close();
        return deserializedStudent;
    }

    public static void main(String[] args){
        try {
            printStudentInfo( readStudentFromFile(args[0]) );
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Error while reading file: " + e.getMessage());
        }
    }
}
