package GUI;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.InputMismatchException;

public class Student implements Serializable{
    private int stdID;
    private String firstName,
            lastName;
    private ArrayList<String> courses = new ArrayList<>();

    public Student(){
        this.stdID = 0;
        this.firstName = null;
        this.lastName = null;
        this.courses = new ArrayList<>();
    }

    public Student(int stdID, String firstName,
                   String lastName, ArrayList<String> courses) throws InvalidParameterException{
        if (stdID > 0 && firstName.length() > 0 && lastName.length() > 0 && !courses.isEmpty()){
            this.stdID = stdID;
            this.firstName = firstName;
            this.lastName = lastName;
            this.courses.addAll(courses);
        }else throw new InvalidParameterException("One or more invalid inputs detected; Please re-enter information: \n");
    }

    public void setID(int id) throws InvalidParameterException{
        if (id > 0 ){
            this.stdID = id;
        }else throw new InvalidParameterException("Please re-enter the ID: \n\n");
    }

    public int getID(){
        return this.stdID;
    }

    public void setFirstName(String firstName) throws InvalidParameterException{
        if (firstName.length() > 0){
            this.firstName = firstName;
        }else throw new InvalidParameterException("Please re-enter your first name: \n\n");
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setLastName(String lastName) throws InvalidParameterException{
        if (lastName.length() > 0){
            this.lastName = lastName;
        }else throw new InvalidParameterException("Please re-enter your last name: \n\n");
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setCourseList(ArrayList<String> courseList) throws InvalidParameterException{
        if (!courseList.isEmpty()){
            this.courses.addAll(courseList);
        }
        else throw new InvalidParameterException("Please re-enter the courses you are taking: \n\n");
    }

    public ArrayList<String> getCoursesList(){
        return this.courses;
    }

    public boolean isEmpty() {
        if (this.stdID == 0 && this.firstName.length() == 0 &&
                this.lastName.length() == 0 && this.courses.size() == 0){
            return true;
        }
        else return false;
    }

    @Override
    public String toString(){
        return ("ID: " + this.getID() + "; First Name: " +
                this.getFirstName() + "; Last Name: " + this.getLastName());
    }

}