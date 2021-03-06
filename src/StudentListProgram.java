/**
 * Created by James on 3/30/2016.
 * This program reads in student data from a file, makes a list of Student objects,
 * and then manipulates the list in multiple ways.
 * Modified 4/5/16 - modified for loops as needed to match new format,
 * changing arrayList declaration to List, and using Collections.sort() to sort lists.
 * This version also makes use of the Comparator interface.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;  // These were 5 single class imports that got contracted

public class StudentListProgram {

    public static void main(String[] args) {
        // Changed list declaration from ArrayList to List
        List<Student> students = new ArrayList<>();
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
            // Changed from using my own sort to Collections.sort()
            // with my list and a NameComparator object
            //sort(students, 1);
            Collections.sort(students, new NameComparator());
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
            // Changed from using my own sort to Collections.sort()
            // with my list and a StudentComparator object
            //sort(students, 2);
            Collections.sort(students, new StudentComparator());
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
     */
    /*
     * After updating to use the Comparator interface, this sort method is no longer needed,
     * as I used the Collections.sort() method instead.
     */
    /*
    // Changed the list parameter type from ArrayList to List
    // to match the change in the declaration
    private static void sort(List<Student> sortList, int sortKey){
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
    */

    /*
     * This method traverses the list once through, keeping counts of male and female students,
     * then prints out the totals.
     */
    // Changed the list parameter type from ArrayList to List
    // to match the change in the declaration
    private static void countMaleFemale(List<Student> studList) {
        int males = 0, females = 0;  // Start both counters at 0
        // We don't need to track the list size in the improved for loop
        //int listSize = studList.size();
        // Changed for loop syntax to a better format for checking
        // each item in the list, so we don't need to use the size
        for (Student thisStud : studList) {
            //Student thisStud = studList.get(index);
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
    // Changed the list parameter type from ArrayList to List
    // to match the change in the declaration
    private static void printGradeLevels(List<Student> studList) {
        // Now, I will find and select all the unique grade level codes
        ArrayList<String> grades = new ArrayList<>();
        // We don't need to track the list size in the improved for loop
        //int listSize = studList.size();
        int gradeSize = 0;
        // Changed for loop syntax to a better format for checking
        // each item in the list, so we don't need to use the size
        for (Student thisStud : studList) {
            //Student thisStud = studList.get(index);
            String thisGrade = thisStud.getGradeLevel();
            if (!grades.contains(thisGrade)) {
                grades.add(thisGrade);  // Found a new grade level, so add it to the list
                gradeSize++;
            }
        }
        // Now we convert it to a sorted array
        // Using the Collections.sort() method I learned during peer review
        Collections.sort(grades);
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
    // Changed the list parameter type from ArrayList to List
    // to match the change in the declaration
    private static void printGradeStudents(List<Student> studList) {
        // Now, I will find and select all the unique grade level codes
        ArrayList<String> grades = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();
        // We don't need to track the list size in the improved for loop
        //int listSize = studList.size();
        int gradeSize = 0;
        int countIndex;
        // Changed for loop syntax to a better format for checking
        // each item in the list, so we don't need to use the size
        for (Student thisStud : studList) {
            //Student thisStud = studList.get(index);
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
        // Using the Collections.sort() method I learned during peer review
        Collections.sort(grades);
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
    // Changed the list parameter type from ArrayList to List
    // to match the change in the declaration
    private static void removeInactives(List<Student> studList) {
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
    // Changed the list parameter type from ArrayList to List
    // to match the change in the declaration
    private static void printOldestAndYoungest(List<Student> studList, SimpleDateFormat dateform) {
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
    /*
     * I changed the list parameter type from ArrayList to List,
     * changed for loop syntax to the improved format, and
     * improved variable names because this method works for any list,
     * not just the oldest or youngest.
     */
    private static void printList(List<Student> studList, SimpleDateFormat dateform) {
        // Changed for loop syntax to a better format for checking
        // each item in the list, so we don't need to use the size
        for (Student nextStud : studList) {
            //Student nextOldest = list.get(i);
            System.out.println("Last Name:     " + nextStud.getLastName());
            System.out.println("First Name:    " + nextStud.getFirstName());
            System.out.println("Middle Name:   " + nextStud.getMiddleName());
            String DOB = dateform.format(nextStud.getDateOfBirth());
            System.out.println("Date of Birth: " + DOB);
            System.out.println();
        }
    }
}
