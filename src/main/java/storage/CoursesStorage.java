package storage;

import courseDescription.Course;
import java.util.ArrayList;
import java.util.List;

/**
 * The class stores the list of courses
 */
public class CoursesStorage {
    private List<Course> coursesPoool;

    public CoursesStorage() {
        coursesPoool = new ArrayList<Course>();
    }

    public void addCourse(Course course){
        coursesPoool.add(course);
    }

    public int getAmountCourses(){
       return coursesPoool.size();
    }
}
