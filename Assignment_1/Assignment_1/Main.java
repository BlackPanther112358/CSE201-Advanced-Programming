package Assignment_1;

import java.util.*;

public class Main{

    Scanner sc = new Scanner(System.in);

    PlacementCell placementcell = new PlacementCell();
    ArrayList<Company> companies = new ArrayList<Company>();
    ArrayList<Student> students = new ArrayList<Student>();

    public static void main(String[] args){
        Main menu = new Main();
        menu.preMenu();
    }

    void preMenu(){
        ArrayList<String> menuargs = new ArrayList<String>();
        menuargs.add("Welcome to FutureBuilder");
        menuargs.add("Enter the Application");
        menuargs.add("Exit the Application");
        int choice = this.makeMenu(menuargs);
        if(choice == 1){
            this.mainMenu();
        }else{
            sc.close();
            return;
        }
        this.preMenu();
    }

    void mainMenu(){
        ArrayList<String> menuargs = new ArrayList<String>();
        menuargs.add("Choose the mode to enter in:-");
        menuargs.add("Enter as Student mode");
        menuargs.add("Enter as Company mode");
        menuargs.add("Enter as Placement Cell mode");
        menuargs.add("Return to Main Application");
        int choice = this.makeMenu(menuargs);
        switch(choice){
            case 1:
                this.studentPreMenu();
                break;
            case 2: 
                this.companyPreMenu();
                break;
            case 3: 
                this.placementCellMenu();
                break;
            case 4: 
                return;
        }
        this.mainMenu();
    }

    void placementCellMenu(){
        ArrayList<String> menuargs = new ArrayList<String>();
        menuargs.add("Welcome to IIITD Placement Cell");
        menuargs.add("Open Student Registrations");
        menuargs.add("Open Company Registrations");
        menuargs.add("Get Number of Student Registrations");
        menuargs.add("Get Number of Company Registrations");
        menuargs.add("Get Number of Offered/Unoffered/Blocked Students");
        menuargs.add("Get Student Details");
        menuargs.add("Get Company Details");
        menuargs.add("Get Average Package");
        menuargs.add("Get Company Process Results");
        menuargs.add("Back");
        int choice = this.makeMenu(menuargs);
        switch(choice){
            case 1:
                placementcell.openStudentReg(sc);
                break;
            case 2:
                placementcell.openCompReg(sc);
                break;
            case 3:
                placementcell.getNumStudents();
                break;
            case 4:
                placementcell.getNumComp();
                break;
            case 5:
                placementcell.getStudentStatus();
                break;
            case 6:
                placementcell.getStudentDetails(sc);
                break;
            case 7:
                placementcell.getCompDetails(sc);
                break;
            case 8:
                placementcell.getAvg();
                break;
            case 9:
                placementcell.getCompProcessResults(sc);
                break;
            case 10:
                return;
        }
        this.placementCellMenu();
    }

    void companyPreMenu(){
        ArrayList<String> menuargs = new ArrayList<String>();
        menuargs.add("Choose the Company Query to perform-");
        menuargs.add("Add Company and Details");
        menuargs.add("Choose Company");
        menuargs.add("Get Available Companies");
        menuargs.add("Back");
        int choice = this.makeMenu(menuargs);
        switch(choice){
            case 1:
                this.addCompany();
                break;
            case 2:
                this.chooseCompany();
                break;
            case 3:
                this.getComps();
                break;
            case 4:
                return;
        }
        this.companyPreMenu();
    }

    void addCompany(){
        Company company = new Company();
        company.constructor(sc);
        companies.add(company);
    }

    void chooseCompany(){
        ArrayList<String> menuargs = new ArrayList<String>();
        menuargs.add("Choose To enter into mode of Available Companies:-");
        for(int i = 0; i < companies.size(); i++){
            menuargs.add(companies.get(i).name);
        }
        int choice = this.makeMenu(menuargs) - 1;
        this.companyMenu(companies.get(choice));
    }

    void companyMenu(Company company){
        ArrayList<String> menuargs = new ArrayList<String>();
        menuargs.add("Welcome " + company.name);
        menuargs.add("Update Role");
        menuargs.add("Update Package");
        menuargs.add("Update CGPA criteria");
        menuargs.add("Register To Institute Drive");
        menuargs.add("Back");
        int choice = this.makeMenu(menuargs);
        switch(choice){
            case 1:
                company.updateRole(sc);
                break;
            case 2:
                company.updateCTC(sc);
                break;
            case 3:
                company.updateCGPA(sc);
                break;
            case 4:
                placementcell.registerCompany(company, sc);
                break;
            case 5:
                return;
        }
        this.companyMenu(company);
    }

    void getComps(){
        if(this.companies.size() == 0){
            System.out.println("No Companies Available");
            return;
        }
        for(int i = 0; i < this.companies.size(); i++){
            System.out.printf("%d) %s\n", i + 1, companies.get(i).name);
        }
        return;
    }

    void studentPreMenu(){
        ArrayList<String> menuargs = new ArrayList<String>();
        menuargs.add("Choose the Student Query to perform-");
        menuargs.add("Enter as a Student(Give Student Name, and Roll No.)");
        menuargs.add("Add students");
        menuargs.add("Get Available Students");
        menuargs.add("Back");
        int choice = this.makeMenu(menuargs);
        switch(choice){
            case 1:
                this.chooseStudent();
                break;
            case 2:
                this.addStudent();
                break;
            case 3:
                this.getStudents();
                break;
            case 4:
                return;
        }
        this.studentPreMenu();
    }

    void addStudent(){
        Student student = new Student();
        student.constructor(sc);
        this.students.add(student);
        System.out.println("Student has been added");
    }

    void chooseStudent(){
        if(students.size() == 0){
            System.out.println("No student has registered yet");
            return;
        }
        System.out.print("Enter the Name: ");
        String name = sc.nextLine();
        System.out.print("Enter the Roll no.: ");
        int roll = sc.nextInt();
        sc.nextLine();
        System.out.println();
        for(int i = 0; i < students.size(); i++){
            if((name.equals(students.get(i).name))&& (students.get(i).roll == roll)){
                studentMenu(students.get(i));
                return;
            }
        }
        System.out.println("No such student found");
        return;
    }

    void studentMenu(Student student){
        ArrayList<String> menuargs = new ArrayList<String>();
        menuargs.add("Welcome " + student.name);
        menuargs.add("Register For Placement Drive");
        menuargs.add("Register For Company");
        menuargs.add("Get All available companies");
        menuargs.add("Get Current Status");
        menuargs.add("Update CGPA");
        menuargs.add("Accept offer");
        menuargs.add("Reject offer");
        menuargs.add("Back");
        int choice = this.makeMenu(menuargs);
        switch(choice){
            case 1:
                placementcell.registerStudent(student, sc);
                break;
            case 2:
                placementcell.apply(student, sc);
                break;
            case 3:
                placementcell.getAvailableComp(student);
                break;
            case 4:
                student.getCurrStatus();
                break;
            case 5:
                placementcell.updateStudentCGPA(student, sc);
                break;
            case 6:
                student.acceptOffer();
                break;
            case 7:
                student.rejectOffer();
                break;
            case 8:
                return;
        }
        this.studentMenu(student);
    }

    void getStudents(){
        if(this.students.size() == 0){
            System.out.println("No Students Available");
            return;
        }
        for(int i = 0; i < this.students.size(); i++){
            System.out.printf("%d) %s\n", i + 1, students.get(i).name);
        }
        return;
    }

    int makeMenu(ArrayList<String> menu){
        int option = 0;
        System.out.println();
        System.out.println(menu.get(0));
        for(int i = 1; i < menu.size(); i++){
            System.out.printf("%d) %s\n", i, menu.get(i));
        }
        System.out.print("Choose an option: ");
        option = sc.nextInt();
        sc.nextLine();
        System.out.println();
        return option;
    }
}