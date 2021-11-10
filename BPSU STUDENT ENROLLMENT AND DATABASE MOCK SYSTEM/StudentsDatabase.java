public class StudentsDatabase {
    private int batch = 1;
    private int studentCount = 1;
    private Queueueue removedIDs = new Queueueue();
    private final StudentsHashMap students;

    StudentsDatabase(int maximumStudentPerYear){
        students = new StudentsHashMap(maximumStudentPerYear);
    }

    // gives the student an ID, adds it to the hashmap and prints a notice if it is successful or not
    public void addStudent(Student student){
        boolean queueIsEmpty = removedIDs.isEmpty(); // for checking if the database is full

        String id = generateID();
        student.setId(id);

        System.out.println("\n----------------------------------------------------------------------------------------");
        boolean addingSuccess = students.add(student, queueIsEmpty);
        if(addingSuccess)
            System.out.println("\nSuccess! Student ID: " + id);
        else
            System.out.println("\nFailed: Maximum Students Capacity Reached.");
    }

    // Returns and ID, either newly created or reused from the queue
    private String generateID(){
        String id;

        if(!removedIDs.isEmpty()) {
            id = removedIDs.dequeue();
        } else {
            id = String.format("%02d-%05d", batch , studentCount); // adds leading zeros
            studentCount++;
        }// will still work even if the student count > 5 digits && Max no. > 5 digits

        return id;
    }

    // removes a student from the hashmap and store it in the queue if the id is from the current batch
    public void remove(String id){
        String studentToBeRemoved = students.remove(id);

        if(studentToBeRemoved == null){
            System.out.println("Invalid ID");
            return;
        }else if(id.substring(0,2).equals(String.format("%02d", batch))){
            removedIDs.enqueue(studentToBeRemoved);
        }

        System.out.println(id + " id is removed");
    }

    // increments the batch and reverts the student count back to 1
    public void nextBatch(){
        batch++;
        studentCount = 1;
        removedIDs = new Queueueue();
        students.expand();
        System.out.println("\n----------------------------------------------------------------------------------------");
        System.out.println("\nWe are now in batch " + batch);
    }

    // prints a student's info
    public void printStudentInfo(String id){
        Student student = getStudent(id);

        if(student == null){
            System.out.println("Invalid ID");
        }else {
            System.out.println("ID: " + id);
            System.out.println("Full Name: " + student.getFirstName() + " " + student.getMiddleName() + " " + student.getLastName());
            System.out.println("Gender: " + student.getGender());
            System.out.println("Birth Date: " + student.getBirthDate());
            System.out.println("Course/Section: " + student.getCourse());
            System.out.println("Age: " + student.getAge());
            System.out.println("Year Level: " + student.getYearLevel());
        }
    }

    // returns a student object
    public Student getStudent(String id){
        return students.get(id);
    }

}

