import java.util.*;

public class GRP4_CS_SD2A {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        System.out.println("\n----------------------------------------------------------------------------------------");
        System.out.print("\nEnter maximum student entry per year: ");

        // restarts the main method if input is < 1 or invalid
        StudentsDatabase database = null;
        try {
            int size = input.nextInt();
            if(size < 1) throw new Exception(); // If input < 1, restarts the main method.
            database = new StudentsDatabase(size);
        } catch(Exception e){ 
            System.out.println("\n----------------------------------------------------------------------------------------");
            System.out.println("\nInvalid Input");
            main(args);
        }

        // main menu
        assert database != null;
        do {
            try {
                System.out.println("\n----------------------------------------------------------------------------------------");
                System.out.println("\nNote: Only enter the number");
                System.out.println("1. Create Account/Student\n2. Find Account/Student\n3. Remove Student\n4. Next Batch");
                System.out.print("Do: ");
                int option = input.nextInt();
                input.nextLine(); //  scanner issue

                if (option == 1) {
                    System.out.println("\n----------------------------------------------------------------------------------------");
                    Student student = new Student();
                    System.out.println("\nPlease provide the info of student below: ");

                    System.out.print("First Name: ");
                    student.setFirstName(input.nextLine().toUpperCase().trim());

                    System.out.print("Middle Name: ");
                    student.setMiddleName(input.nextLine().toUpperCase().trim());

                    System.out.print("Last Name: ");
                    student.setLastName(input.nextLine().toUpperCase().trim());

                    System.out.print("Gender (M/F): ");
                    student.setGender(input.nextLine().toUpperCase().trim());

                    System.out.print("Birth Date (MM/DD/YYYY): ");
                    student.setBirthDate(input.nextLine().trim());

                    System.out.print("Course/Section: ");
                    student.setCourse(input.nextLine().toUpperCase().trim());

                    System.out.print("Age: ");
                    student.setAge(input.nextInt());

                    System.out.print("Year Level (1/2/3/4/5): ");
                    student.setYearLevel(input.nextInt());
                    input.nextLine(); // resolving scanner issue

                    database.addStudent(student);
                } else if (option == 2) {
                    System.out.println("\n----------------------------------------------------------------------------------------");
                    System.out.print("\nID: ");
                    database.printStudentInfo(input.next());
                } else if (option == 3) {
                    System.out.println("\n----------------------------------------------------------------------------------------");
                    System.out.print("\nID: ");
                    database.remove(input.next());
                } else if (option == 4) {
                    database.nextBatch();
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Input");
                input.nextLine(); // resolving scanner issue
            }
        }while(true); // to run the code
    }// end of main method
}
