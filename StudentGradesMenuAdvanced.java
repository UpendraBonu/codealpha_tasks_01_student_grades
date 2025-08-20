import java.util.*;

class Student {
    int id;
    String name;
    String className;
    HashMap<String, Double> subjects;

    public Student(int id, String name, String className, HashMap<String, Double> subjects) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.subjects = subjects;
    }

    public double getTotalScore() {
        double total = 0;
        for (double s : subjects.values()) {
            total += s;
        }
        return total;
    }

    public double getAverageScore() {
        return getTotalScore() / subjects.size();
    }

    public String getStrongestSubject() {
        String best = null;
        double max = Double.MIN_VALUE;
        for (String sub : subjects.keySet()) {
            if (subjects.get(sub) > max) {
                max = subjects.get(sub);
                best = sub;
            }
        }
        return best + " (" + max + ")";
    }

    public String getWeakestSubject() {
        String worst = null;
        double min = Double.MAX_VALUE;
        for (String sub : subjects.keySet()) {
            if (subjects.get(sub) < min) {
                min = subjects.get(sub);
                worst = sub;
            }
        }
        return worst + " (" + min + ")";
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Class: " + className + ", Avg: " + getAverageScore();
    }
}

public class StudentGradesMenuAdvanced {
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static List<String> subjectsList = Arrays.asList("Math", "Science", "English");
    public static void addStudent() {
    int id;
    while (true) {
        try {
            System.out.print("Enter Student ID: ");
            id = sc.nextInt();
            sc.nextLine();
            boolean exists = false;
            for (Student s : students) {
                if (s.id == id) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                throw new IllegalArgumentException("Student ID already exists! Please enter a unique ID.");
            }

            break; 
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    System.out.print("Enter Name: ");
    String name = sc.nextLine();

    System.out.print("Enter Class: ");
    String className = sc.nextLine();

    HashMap<String, Double> marks = new HashMap<>();
    for (String sub : subjectsList) {
        System.out.print("Enter score for " + sub + ": ");
        marks.put(sub, sc.nextDouble());
    }
    sc.nextLine();

    students.add(new Student(id, name, className, marks));
    System.out.println("Student added successfully!");
}

private static List<String> validClasses = new ArrayList<>(Arrays.asList(
    "FirstYear", "SecondYear", "ThirdYear", "FourthYear"
));

public static void displayClassStats() {
    System.out.println("\n--- Select Class ---");
    for (int i = 0; i < validClasses.size(); i++) {
        System.out.println((i + 1) + ". " + validClasses.get(i));
    }
    System.out.println((validClasses.size() + 1) + ". Add a New Class");
    System.out.println((validClasses.size() + 2) + ". Back to Main Menu");
    System.out.print("Choose: ");

    int choice = sc.nextInt();
    sc.nextLine();

    if (choice == validClasses.size() + 1) { 
        System.out.print("Enter new class name: ");
        String newClass = sc.nextLine();
        validClasses.add(newClass);
        System.out.println("Class added: " + newClass);
        return;
    } else if (choice == validClasses.size() + 2) {
        return; 
    }

    if (choice < 1 || choice > validClasses.size()) {
        System.out.println("No class found!");
        return;
    }

    String selectedClass = validClasses.get(choice - 1);
    ArrayList<Student> classStudents = new ArrayList<>();
    for (Student s : students) {
        if (s.className.equalsIgnoreCase(selectedClass)) {
            classStudents.add(s);
        }
    }

    if (classStudents.isEmpty()) {
        System.out.println("No students in " + selectedClass);
        return;
    }
    classStudents.sort((a, b) -> Double.compare(b.getAverageScore(), a.getAverageScore()));

    double totalAvg = 0;
    double highest = Double.MIN_VALUE, lowest = Double.MAX_VALUE;

    System.out.println("\n=== Class Report: " + selectedClass + " ===");
    System.out.printf("%-5s %-10s %-15s %-10s\n", "Rank", "ID", "Name", "Avg Score");

    int rank = 1;
    for (Student s : classStudents) {
        double avg = s.getAverageScore();
        totalAvg += avg;
        if (avg > highest) highest = avg;
        if (avg < lowest) lowest = avg;

        String star = (avg < 25.0) ? "*" : ""; 
        System.out.printf("%-5s %-10d %-15s %-10.2f %s\n", rank, s.id, s.name, avg, star);
        rank++;
    }

    double classAvg = totalAvg / classStudents.size();

    System.out.println("\nClass Average: " + classAvg);
    System.out.println("Highest Average Score: " + highest);
    System.out.println("Lowest Average Score: " + lowest);
}


    public static void displayStudentSummary() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Student target = null;
        for (Student s : students) {
            if (s.id == id) {
                target = s;
                break;
            }
        }

        if (target == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("\n=== Student Report ===");
        System.out.println("ID: " + target.id + ", Name: " + target.name + ", Class: " + target.className);
        for (String sub : subjectsList) {
            System.out.println(sub + ": " + target.subjects.get(sub));
        }
        System.out.println("Average Score: " + target.getAverageScore());
        System.out.println("Strongest Subject: " + target.getStrongestSubject());
        System.out.println("Weakest Subject: " + target.getWeakestSubject());
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Update Student");
            System.out.println("4. Display Class Status");

            System.out.println("5.Display Student Summary");
            System.out.println("6.Exit.");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: deleteStudent();break;
                case 3: updateStudent();break;
                case 4: displayClassStats(); break;
                case 5: displayStudentSummary(); break;
                case 6: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void updateStudent() {
    System.out.print("Enter Student ID to update: ");
    int id = sc.nextInt();
    sc.nextLine();

    Student target = null;
    for (Student s : students) {
        if (s.id == id) {
            target = s;
            break;
        }
    }

    if (target == null) {
        System.out.println("No student found with ID: " + id);
        return;
    }

    System.out.println("\nUpdating student: " + target);

    while (true) {
        System.out.println("\n--- Update Menu ---");
        System.out.println("1. Update Name");
        System.out.println("2. Update Class");
        System.out.println("3. Update Marks");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter new name: ");
                target.name = sc.nextLine();
                System.out.println("Name updated successfully!");
                break;

            case 2:
                System.out.print("Enter new class: ");
                target.className = sc.nextLine();
                System.out.println("Class updated successfully!");
                break;

            case 3:
                System.out.println("\nCurrent Marks:");
                System.out.println("+---------------+-------+");
                System.out.printf("| %-13s | %-5s |\n", "Subject", "Marks");
                System.out.println("+---------------+-------+");
                for (String sub : subjectsList) {
                    System.out.printf("| %-13s | %-5.2f |\n", sub, target.subjects.get(sub));
                }
                System.out.println("+---------------+-------+");

                for (String sub : subjectsList) {
                    System.out.print("Enter new marks for " + sub + " (or -1 to keep existing): ");
                    double newMark = sc.nextDouble();
                    if (newMark >= 0) {
                        target.subjects.put(sub, newMark);
                    }
                }
                sc.nextLine();
                System.out.println("Marks updated successfully!");
                break;

            case 4:
                return;

            default:
                System.out.println("Invalid choice! Try again.");
        }
    }
}


   private static void deleteStudent() {
    System.out.print("Delete by (1) ID or (2) Name? ");
    int choice = sc.nextInt();
    sc.nextLine();

    if (choice == 1) {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean exists = false;
        for (Student s : students) {
            if (s.id == id) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            System.out.println("Student doesn't exist with ID: " + id);
            return; 
        }

        students.removeIf(s -> s.id == id);
        System.out.println("Deleted student(s) with ID: " + id);

    } else if (choice == 2) {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        ArrayList<Student> matches = new ArrayList<>();
        for (Student s : students) {
            if (s.name.equalsIgnoreCase(name)) matches.add(s);
        }

        if (matches.isEmpty()) {
            System.out.println("No student found with name: " + name);
        } else if (matches.size() == 1) {
            students.remove(matches.get(0));
            System.out.println("Deleted: " + matches.get(0));
        } else {
            System.out.println("Multiple students found:");
            for (Student s : matches) {
                System.out.println(s);
            }
            System.out.print("Enter Class to identify: ");
            String className = sc.nextLine();

            boolean removed = students.removeIf(s -> s.name.equalsIgnoreCase(name)
                    && s.className.equalsIgnoreCase(className));

            if (removed) {
                System.out.println("Deleted student(s) with name: " + name + " and class: " + className);
            } else {
                System.out.println("No student matched that name and class.");
            }
        }
    }
}

}
