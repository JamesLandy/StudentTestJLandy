/**
 * Created by James on 3/30/2016.
 * This class models a Student.
 */
import java.util.Date;

public class Student {

    private String studID;
    private String schoolID;
    private String lastName;
    private String firstName;
    private String middleName;
    private Date dateOfBirth;
    private String gender;
    private String gradeLevel;

    public Student(String studID, String schoolID, String lastName, String firstName, String middleName,
                   Date dateOfBirth, String gender, String gradeLevel) {
        this.studID = studID;
        this.schoolID = schoolID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.gradeLevel = gradeLevel;
    }

    public String getStudID() {
        return studID;
    }

    public void setStudID(String studID) {
        this.studID = studID;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    /*
     * This method is used by the original StudentListProgram.
     * It is not needed in the StudentListComparator program.
     */
    public String getSortKey(int keycode) {
        String sortKey = "";  // Start with an initial empty string, then check the sort key code
        if (keycode == 1) {
            sortKey = lastName.toUpperCase() + "\t" + firstName.toUpperCase() + "\t" + middleName.toUpperCase();
        } else if (keycode == 2) {
            sortKey = schoolID + "\t" + gradeLevel + "\t" + lastName.toUpperCase() + "\t" +
                    firstName.toUpperCase() + "\t" + middleName.toUpperCase();
        } else if (keycode == 3) {
            sortKey = gradeLevel;
        }
        return sortKey;
    }
}
