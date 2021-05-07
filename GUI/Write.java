package GUI;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Write{
    public static void saveStudentToFile(ArrayList<Student> students,
                                         String filename ) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(students);
        fileOut.close();
        objectOut.flush();
        objectOut.close();
    }

    public static Student getStudentInfo(Scanner[] studentInfoScanners,
                                         ArrayList<String> courses, int studentCount){
        int studentID = 0, courseCount = 0;
        String firstName = null, lastName = null;
        System.out.println("\nstudent " + studentCount + "info");
        System.out.print("Student ID: ");
        studentID = studentInfoScanners[0].nextInt();
        System.out.print("First name: ");
        firstName = studentInfoScanners[1].nextLine();
        System.out.print("last name: ");
        lastName = studentInfoScanners[2].nextLine();

        System.out.print("Number of courses for this semester: ");
        courseCount = studentInfoScanners[3].nextInt();
        for (int i = 0; i < courseCount; i++){
            System.out.print("Course " + (i + 1) + ": ");
            courses.add(studentInfoScanners[4].nextLine());
        }
        return new Student(studentID, firstName, lastName, courses);
    }

    public static void main(String[] args){
        ArrayList<String> courses = new ArrayList<>();
        ArrayList<Student> serializedStudents = new ArrayList<>();
        boolean validInput = false;
        int numOfStudents = 0;
        while(!validInput){
            try{
                Scanner[] studentInfoScanners = new Scanner[5];
                for (int i = 0; i < studentInfoScanners.length; i++){
                    studentInfoScanners[i] = new Scanner(System.in);
                }
                System.out.print("Number of students: ");
                numOfStudents = studentInfoScanners[0].nextInt();
                serializedStudents.ensureCapacity(numOfStudents);
                for (int studentCount = 0; studentCount < numOfStudents; studentCount++){
                    courses.clear();
                    serializedStudents.add(getStudentInfo(studentInfoScanners, courses, studentCount + 1));
                }
                validInput = true;
                saveStudentToFile(serializedStudents, args[0]);
                System.out.println("\nInformation saved");
                for (int i = 0; i < studentInfoScanners.length; i++){
                    if (studentInfoScanners[i] != null){
                        studentInfoScanners[i].close();
                    }
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input!\n");
            }
            catch (InvalidParameterException e){
                System.out.println(e.getMessage());
            }
            catch (IOException e) {
                System.out.println("Error. Description: ");
                e.printStackTrace();
            }
        }
    }
}
