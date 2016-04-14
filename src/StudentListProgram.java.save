/**
 * Created by James on 3/30/2016.
 * This program reads in student data from a file, makes a list of Student objects,
 * and then manipulates the list in multiple ways.
 * Modified 4/5/16 - changed printOldestYoungest to call sub-method for printing
 * This version is pretty much the same as what I did during my on-site technical evaluation.
 */
import java.text.ParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;
import java.text.SimpleDateFormat;

public class StudentListProgram {

    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        SimpleDateFormat dateform = new SimpleDateFormat("MM/dd/yyyy");
        try {
            File inputFile = new File("students2.txt");
            Scanner fileIn = new Scanner(inputFile);
            System.out.println("Reading student data from file...");
            fileIn.nextLine();  // Skip through the headers
            String[] nextStudent;
            Date DOB;
            while (fileIn.hasNext()) {
                nextStudent = fileIn.nextLine().split("\t");
                try {
                    DOB = dateform.parse(nextStudent[5]);
                } catch (ParseException px) {
                    System.out.println("Unable to parse date");
                    DOB = new Date();
                }
                Student next = new Student(nextStudent[0], nextStudent[1], nextStudent[2], nextStudent[3],
                                           nextStudent[4], DOB, nextStudent[6], nextStudent[7]);
                students.add(next);  // Add the new student to the list
            }
            fileIn.close();
            System.out.println("--List of students generated--\n");
            // Now that the list has been read in, we get into the steps of the exercise
            // Step 1 - sort the list by the students' last and first names
            System.out.println("Sorting list by student names...");
            sort(students, 1);
            System.out.println("--Sorting complete--\n");
            // Step 2 - print counts of the male and female students
            countMaleFemale(students);
            // Step 3 - print the unique grade levels
            printGradeLevels(students);
            // Step 4 - print the unique grade levels and the number of students in each grade level
            printGradeStudents(students);
            // Step 5 - remove all the inactive students
            removeInactives(students);
            // Step 6 - find and print the oldest and youngest students on record
            printOldestAndYoungest(students, dateform);
            // Step 7 - sort the list by school ID, grade level, last name, and first name
            System.out.println("Sorting list by school ID, grade level, and student names...");
            sort(students, 2);
            System.out.println("--Sorting complete--\n");
        } catch (FileNotFoundException fnfx) {
            System.out.println("The file students2.txt was not found.");
        }
    }

    /*
     * This method sorts the list of students in various ways.
     * It is a simple bubble sort that sorts by the given sort key.
     * sortKey = 1 - sort by last name, first name, middle name
     * sortKey = 2 - sort by school ID, grade level, and then last, first, and middle name
     * sortKey = 3 - sort by grade level
     */
    private static void sort(ArrayList<Student> sortList, int sortKey){
        int sortSize = sortList.size();
        boolean sorted;
        Student current, next, buffer;
        do {
            int index = 0;
            sorted = true;
            while (index < sortSize - 1) {
                current = sortList.get(index);   // The item we're currently looking at
                next = sortList.get(index + 1);  // The item after it
                String key1 = current.getSortKey(sortKey);
                String key2 = next.getSortKey(sortKey);
                if (key1.compareTo(key2) > 0) {
                    sorted = false;  // We found two items out of order
                    buffer = next;
                    sortList.set(index + 1, current);
                    sortList.set(index, buffer);
                }
                index++;  // Step up to the next item
            }
            sortSize--;  // Decrease size of list we need to sort because the max has bubbled to the end
        } while (!sorted);
    }

    /*
     * This method traverses the list once through, keeping counts of male and female students,
     * then prints out the totals.
     */
    private static void countMaleFemale(ArrayList<Student> studList) {
        int males = 0, females = 0;  // Start both counters at 0
        int listSize = studList.size();
        for (int index = 0; index < listSize; index++) {
            Student thisStud = studList.get(index);
            if (thisStud.getGender().compareTo("M") == 0)
                males++;
            else
                females++;
        }
        System.out.println("Totals of male and female students in list:");
        System.out.println("Male:   " + males);
        System.out.println("Female: " + females);
        System.out.println();
    }

    /*
     * This method traverses the list of students and creates a new list of unique grade levels.
     */
    private static void printGradeLevels(ArrayList<Student> studList) {
        // Now, I will find and select all the unique grade level codes
        ArrayList<String> grades = new ArrayList<>();
        int listSize = studList.size();
        int gradeSize = 0;
        for (int index = 0; index < listSize; index++) {
            Student thisStud = studList.get(index);
            String thisGrade = thisStud.getGradeLevel();
            if (!grades.contains(thisGrade)) {
                grades.add(thisGrade);  // Found a new grade level, so add it to the list
                gradeSize++;
            }
        }
        // Now we convert it to a sorted array
        grades.sort(null);
        String[] gradeArray = grades.toArray(new String[gradeSize]);
        // Now print the resulting list
        System.out.println("Unique grade levels in student file:");
        for (int i = 0; i < gradeSize; i++) {
            System.out.println(gradeArray[i]);
        }
        System.out.println();
    }

    /*
     * This method traverses the list of students and creates a new list of unique grade levels.
     * It simultaneously keeps a list of the counts of students in each grade level.
     * Once it's done counting them up, it concatenates the results into meaningful output.
     */
    private static void printGradeStudents(ArrayList<Student> studList) {
        // Now, I will find and select all the unique grade level codes
        ArrayList<String> grades = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();
        int listSize = studList.size();
        int gradeSize = 0;
        int countIndex;
        for (int index = 0; index < listSize; index++) {
            Student thisStud = studList.get(index);
            String thisGrade = thisStud.getGradeLevel();
            if (!grades.contains(thisGrade)) {
                grades.add(thisGrade);  // Found a new grade level, so add it to the list
                gradeSize++;
                counts.add(1);  // Start a new counter at 1
            } else {
                countIndex = grades.indexOf(thisGrade);  // Find the index of the counter
                counts.set(countIndex, counts.get(countIndex) + 1);  // Increment the counter
            }
        }
        // Concatenate the grade levels and student counts together in the string list
        for (int coIndex = 0; coIndex < gradeSize; coIndex++) {
            String gradeCount = grades.get(coIndex) + ":\t" + counts.get(coIndex);
            grades.set(coIndex, gradeCount);
        }
        // Then convert it to a sorted array
        grades.sort(null);
        String[] gradeArray = grades.toArray(new String[gradeSize]);
        // Now print the resulting list
        System.out.println("Student counts by grade level:");
        for (int i = 0; i < gradeSize; i++) {
            System.out.println(gradeArray[i]);
        }
        System.out.println();
    }

    /*
     * This method traverses the arrayList of Students and removes any that have a
     * school ID of "INACT" - as in, the inactive students.
     */
    private static void removeInactives(ArrayList<Student> studList) {
        int listSize = studList.size();
        for (int ind = 0; ind < listSize; ind++) {
            Student thisStudent = studList.get(ind);
            if (thisStudent.getSchoolID().compareTo("INACT") == 0) {
                studList.remove(ind);  // Found an inactive student, so remove them
                listSize--;  // And decrease the size of the list to search
            }
        }
        System.out.println("--Inactive students removed.--\n");
    }

    /*
     * This method traverses the list, keeping track of the oldest and youngest
     * students in the list - and even accounts for the possibility of shared birth dates.
     * Once it finds the oldest and youngest students, it prints out who they are.
     */
    private static void printOldestAndYoungest(ArrayList<Student> studList, SimpleDateFormat dateform) {
        ArrayList<Student> oldest = new ArrayList<>(1);
        ArrayList<Student> youngest = new ArrayList<>(1);
        int listSize = studList.size();
        // Initialize both student trackers with the first student in the list.
        Student thisStud = studList.get(0);
        oldest.add(thisStud);
        youngest.add(thisStud);
        for (int ind = 1; ind < listSize; ind++) {
            thisStud = studList.get(ind);
            Date oldBD = oldest.get(0).getDateOfBirth();
            Date youngBD = youngest.get(0).getDateOfBirth();
            Date thisBD = thisStud.getDateOfBirth();
            if (thisBD.compareTo(oldBD) < 0) {
                // We found an older student
                oldest.clear();
                oldest.add(thisStud);
            } else if (thisBD.compareTo(oldBD) == 0) {
                // This student shares the oldest one's birth date, so append it to that list
                oldest.add(thisStud);
            }
            if (thisBD.compareTo(youngBD) > 0) {
                // We found a younger student
                youngest.clear();
                youngest.add(thisStud);
            } else if (thisBD.compareTo(youngBD) == 0) {
                // This student shares the youngest one's birth date, so append it to that list
                youngest.add(thisStud);
            }
        }
        // Now that we definitely have the oldest and youngest students, print out their info
        System.out.println("Oldest student(s) on record:");
        printList(oldest, dateform);
        System.out.println("Youngest student(s) on record:");
        printList(youngest, dateform);
    }

    /*
     * This sub-method takes in a list and prints out the students' info
     */
    private static void printList(ArrayList<Student> list, SimpleDateFormat dateform) {
        for (int i = 0; i < list.size(); i++) {
            Student nextOldest = list.get(i);
            System.out.println("Last Name:     " + nextOldest.getLastName());
            System.out.println("First Name:    " + nextOldest.getFirstName());
            System.out.println("Middle Name:   " + nextOldest.getMiddleName());
            String DOB = dateform.format(nextOldest.getDateOfBirth());
            System.out.println("Date of Birth: " + DOB);
            System.out.println();
        }
    }
}
