package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class GUI {
    private static JFrame frame = new JFrame("JAVA GRAPHICAL USER INTERFACE");
    private static Student student = new Student();
    private static ArrayList<String> courseList = new ArrayList<>(6);
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static JButton[] buttons = new JButton[3];
    private static JLabel[] labels = new JLabel[6];
    private static JTextField[] textFields = new JTextField[4];
    private static JTextArea results = new JTextArea( 8, 33);
    private static JScrollPane scrollPane = new JScrollPane(results);
    private static JPanel panel = new JPanel();
    private static SpringLayout sl = new SpringLayout();

    public static void main(String[] args) {
        constructUI();
        setUIEvents();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {

        }

        results.setEditable(false);
        frame.add(panel);
        frame.setSize(400,500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static boolean validStudent() throws NumberFormatException, InvalidParameterException {
        if ( Integer.parseInt(textFields[0].getText()) > 0
                || textFields[1].getText().length() > 0
                || textFields[2].getText().length() > 0 ) {
            GUI.student.setID( Integer.parseInt(textFields[0].getText()) ) ;
            GUI.student.setFirstName(textFields[1].getText());
            GUI.student.setLastName(textFields[2].getText());
            return true;
        }
        else return false;
    }

    public static void setUIEvents() throws NumberFormatException, InvalidParameterException {
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Student> storedData = new ArrayList<>();
                try {
                    storedData = Read.readStudentFromFile("data.txt");
                }
                catch (IOException | ClassNotFoundException error) {
                    JOptionPane.showMessageDialog(frame, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (new File("data.txt").length() > 0) {
                    results.setText("");
                    if (GUI.studentList.isEmpty()) {
                        GUI.studentList.addAll(storedData);
                    }
                    for (int i=0; i < GUI.studentList.size(); i++) {
                        results.append("Client number: " + (i+1) + "\n" +
                                GUI.studentList.get(i).toString() + "\nCity: ");
                        for (String course: GUI.studentList.get(i).getCoursesList()) {
                            results.append(course + " ");
                        }
                        results.append("\n\n");
                    }
                }
                else results.setText("File is empty!");
            }
        });

        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (validStudent() && textFields[3].getText().length() > 0) {
                        GUI.courseList.add(textFields[3].getText());
                        if (GUI.courseList.size() == 1) {
                            labels[4].setText(GUI.courseList.size() + " course added");
                        }
                        else {
                            labels[4].setText(GUI.courseList.size() + " courses added");
                        }
                        textFields[3].setText("");
                    }
                    else JOptionPane.showMessageDialog(frame, "Course code must not be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch(NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Please re-enter your ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch(InvalidParameterException e) {
                    JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

        });

        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (validStudent() && courseList.size() > 0){
                        GUI.student.setCourseList(GUI.courseList);
                        GUI.studentList.add(new Student(GUI.student.getID(),
                                GUI.student.getFirstName(), GUI.student.getLastName(),
                                GUI.student.getCoursesList()));
                        Write.saveStudentToFile(studentList, "data.txt");
                        System.out.println("\nData saved");
                        GUI.student.getCoursesList().clear();
                        GUI.courseList.clear();
                        textFields[0].setText("");
                        textFields[1].setText("");
                        textFields[2].setText("");
                        textFields[3].setText("");
                        labels[4].setText("");
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Please enter at least one course code", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(IOException | InvalidParameterException error) {
                    JOptionPane.showMessageDialog(frame, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch(NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Please re-enter your ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void constructUI() {
        panel.setLayout(sl);
        String[] buttonName = {"Save", "Load", "+"};
        for (int i=0; i < buttonName.length; i++) {
            buttons[i] = new JButton(buttonName[i]);
            panel.add(buttons[i]);
        }
        String[] labelName = {"Student ID:", "First Name:", "Last Name:", "Course Code:", "", "File Contents:"};
        for (int i=0; i < labelName.length; i++) {
            labels[i] = new JLabel(labelName[i]);
            panel.add(labels[i]);
        }

        for (int i=0; i < textFields.length; i++) {
            textFields[i] = new JTextField(10);
            panel.add(textFields[i]);
        }

        sl.putConstraint(SpringLayout.WEST, labels[0], 55, SpringLayout.WEST, panel);
        sl.putConstraint(SpringLayout.WEST, textFields[0], 130, SpringLayout.WEST, panel);

        sl.putConstraint(SpringLayout.SOUTH, labels[1], 50, SpringLayout.NORTH, labels[0]);
        sl.putConstraint(SpringLayout.WEST, labels[1], 50, SpringLayout.WEST, panel);

        sl.putConstraint(SpringLayout.SOUTH, textFields[1], 51, SpringLayout.NORTH, labels[0]);
        sl.putConstraint(SpringLayout.EAST, textFields[1], 117, SpringLayout.EAST, labels[1]);

        sl.putConstraint(SpringLayout.SOUTH, labels[2], 50, SpringLayout.NORTH, labels[1]);
        sl.putConstraint(SpringLayout.WEST, labels[2], 50, SpringLayout.WEST, panel);

        sl.putConstraint(SpringLayout.SOUTH, textFields[2], 51, SpringLayout.NORTH, labels[1]);
        sl.putConstraint(SpringLayout.EAST, textFields[2], 118, SpringLayout.EAST, labels[2]);

        sl.putConstraint(SpringLayout.SOUTH, labels[3], 50, SpringLayout.NORTH, labels[2]);
        sl.putConstraint(SpringLayout.WEST, labels[3], 37, SpringLayout.WEST, panel);

        sl.putConstraint(SpringLayout.SOUTH, textFields[3], 51, SpringLayout.NORTH, labels[2]);
        sl.putConstraint(SpringLayout.EAST, textFields[3], 118, SpringLayout.EAST, labels[3]);

        sl.putConstraint(SpringLayout.SOUTH, labels[5], 110, SpringLayout.NORTH, labels[3]);
        sl.putConstraint(SpringLayout.WEST, labels[5], 10, SpringLayout.WEST, panel);

        sl.putConstraint(SpringLayout.SOUTH, buttons[0], 70, SpringLayout.NORTH, labels[3]);
        sl.putConstraint(SpringLayout.EAST, buttons[0], 93, SpringLayout.EAST, labels[3]);

        sl.putConstraint(SpringLayout.SOUTH, buttons[1], 110, SpringLayout.NORTH, labels[3]);
        sl.putConstraint(SpringLayout.WEST, buttons[1], 147, SpringLayout.WEST, panel);

        sl.putConstraint(SpringLayout.SOUTH, buttons[2], 53, SpringLayout.NORTH, labels[2]);
        sl.putConstraint(SpringLayout.EAST, buttons[2], 50, SpringLayout.EAST, textFields[3]);

        panel.add(scrollPane);
        sl.putConstraint(SpringLayout.SOUTH, scrollPane, 205, SpringLayout.NORTH, buttons[0]);
        sl.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, panel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        sl.putConstraint(SpringLayout.SOUTH, labels[4], 33, SpringLayout.NORTH, labels[3]);
        sl.putConstraint(SpringLayout.EAST, labels[4], 130, SpringLayout.NORTH, textFields[3]);

    }


}
