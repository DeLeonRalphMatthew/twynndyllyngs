public class StudentsHashMap {
    private Student[] students;
    private final int maximumStudentsPerYear;

    // sets the length of students as the maximumStudentsPerYear
    StudentsHashMap(int maximumStudentsPerYear){
        this.maximumStudentsPerYear = maximumStudentsPerYear;
        students = new Student[maximumStudentsPerYear];
    }

    // returns false if the array is full,
    // returns true if operation is successful
    // Queue - { }
    // HashMap - {01-00001, 01-00001, 01-00003}
    public boolean add(Student student, boolean queueIsEmpty){
        if(queueIsEmpty && students[students.length-1] != null) // if full
            return false;

        int index = generateIndex(student.getId());
        students[index] = student;
        return true;
    }

    // returns the student with the given ID
    // returns null if the ID is non-existent or invalid
    public Student get(String id){
        int index = generateIndex(id);
        if(index >= students.length)
            return null;

        return students[index] == null || !students[index].getId().equals(id) ? null : students[index];
    }

    // removes the student from the array and returns the removed student's ID
    // returns null if the ID is non-existent or invalid
    public String remove(String id){
        int index = generateIndex(id);
        if(index >= students.length)
            return null;

        Student studentTobeRemoved = students[index];
        if(studentTobeRemoved == null || !studentTobeRemoved.getId().equals(id))
            return null;

        students[index] = null;
        return studentTobeRemoved.getId();
    }

    // expands the array - creates a larger array and transfers the initial values before overwriting
    public void expand() {
        Student[] newStudentArray = new Student[students.length + maximumStudentsPerYear];
        System.arraycopy(students, 0, newStudentArray, 0, students.length);
        students = newStudentArray;
    }

    // custom hashing
    // generates and returns an index from the given ID
    // returns an integer greater than the highest index if ID is invalid
    private int generateIndex(String id){
        try {
            int batch = Integer.parseInt(id.split("-")[0]);
            int studentCount = Integer.parseInt(id.split("-")[1]);
            int base = (batch * maximumStudentsPerYear) - maximumStudentsPerYear;
            return base + studentCount - 1;
        }catch(Exception e){
            return students.length;
        } //19-a0001 a @
    }
}
