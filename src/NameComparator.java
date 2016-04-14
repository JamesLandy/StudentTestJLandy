/**
 * Created by James on 4/5/2016.
 * This is a simple comparator class used to compare Students in the list for sorting
 */
import java.util.Comparator;

public class NameComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        // This compares two students on their last, first, and middle names
        String name1 = o1.getLastName().toUpperCase() + "\t" + o1.getFirstName().toUpperCase() + "\t" +
                       o1.getMiddleName().toUpperCase();
        String name2 = o2.getLastName().toUpperCase() + "\t" + o2.getFirstName().toUpperCase() + "\t" +
                       o2.getMiddleName().toUpperCase();
        return name1.compareTo(name2);
    }

}
