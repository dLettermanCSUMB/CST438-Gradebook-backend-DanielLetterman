package com.cst438.domain;

import java.util.ArrayList;

/*
 * a transfer object that is a list of assignment details
 */
public class CourseListDTO {

    public static class CourseDTO {

        public int courseId;
        public String title;
        public String instructor;
        public int year;
        public String semester;

        public CourseDTO(int courseId, String title, String instructor, int year, String semester) {
            this.courseId = courseId;
            this.title = title;
            this.instructor = instructor;
            this.year = year;
            this.semester = semester;
        }

        @Override
        public String toString() {
            return "[courseId=" + courseId + ", title=" + title + ", instructor="
                    + instructor + ", year=" + year + ", semester=" + semester + "]";
        }


        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            CourseDTO other = (CourseDTO) obj;
            if (courseId != other.courseId)
                return false;
            if (!title.equals(other.title))
                return false;
            if (!instructor.equals(other.instructor))
                return false;
            if (year != other.year)
                return false;
            if (!semester.equals(other.semester))
                return false;
            return true;
        }
//


    }

    public ArrayList<CourseDTO> courses = new ArrayList<>();

    @Override
    public String toString() {
        return "CourseListDTO " + courses ;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CourseListDTO other = (CourseListDTO) obj;
        if (courses == null) {
            if (other.courses != null)
                return false;
        } else if (!courses.equals(other.courses))
            return false;
        return true;
    }

}
