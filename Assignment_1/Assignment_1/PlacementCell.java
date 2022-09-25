package Assignment_1;

import java.util.*;

public class PlacementCell {
    
    ArrayList<Company> registeredCompanies = new ArrayList<Company>();
    ArrayList<Student> registeredStudents = new ArrayList<Student>();
    ArrayList<ArrayList<Student>> applications;

    DateTime companyStartTime;
    DateTime companyEndTime;
    DateTime studentStartTime;
    DateTime studentEndTime;

    Boolean companyReg = false;
    Boolean studentReg = false;

    public void openStudentReg(Scanner sc){
        studentStartTime = new DateTime();
        System.out.println("Enter the start time for Student registration: ");
        studentStartTime.set_time(sc);
        studentEndTime = new DateTime();
        System.out.println("Enter the end time for Student registration: ");
        studentEndTime.set_time(sc);
        if(studentStartTime.compare(studentEndTime) == 1){
            System.out.println("Start time cannot be greater than end time");
            return;
        }
        applications = new ArrayList<ArrayList<Student>>();
        for(int i = 0; i < registeredCompanies.size(); i++){
            ArrayList<Student> temp = new ArrayList<Student>();
            applications.add(temp);
        }  
        System.out.println("Student Registrations have begun"); 
        this.studentReg = true;
        return;
    }

    public void openCompReg(Scanner sc){
        companyStartTime = new DateTime();
        System.out.println("Enter the start time for company registration: ");
        companyStartTime.set_time(sc);
        companyEndTime = new DateTime();
        System.out.println("Enter the end time for company registration: ");
        companyEndTime.set_time(sc);
        if(companyStartTime.compare(companyEndTime) == 1){
            System.out.println("Start time cannot be greater than end time");
            return;
        }
        System.out.println("Company Registrations have begun"); 
        this.companyReg = true;
        return;
    }

    public void getNumStudents(){
        System.out.printf("%d students have registered\n", registeredStudents.size());
    }

    public void getNumComp(){
        System.out.printf("%d companies have registered\n", registeredCompanies.size());
    }

    public void getStudentStatus(){
        int cnt_placed = 0, cnt_unplaced = 0, cnt_blocked = 0;
        for(int i = 0; i < registeredStudents.size(); i++){
            Student student = registeredStudents.get(i);
            switch(student.status){
                case "Accepted":
                    cnt_placed += 1;
                    break;
                case "Offered":
                    cnt_placed += 1;
                    break;
                case "Unoffered":
                    cnt_unplaced += 1;
                    break;
                case "Blocked":
                    cnt_blocked += 1;
                    break;
            }
        }
        System.out.printf("Number of Students Placed: %d\n", cnt_placed);
        System.out.printf("Number of Students Unplaced: %d\n", cnt_unplaced);
        System.out.printf("Number of Students Blocked: %d\n", cnt_blocked);
        return;
    }

    public void getStudentDetails(Scanner sc){
        System.out.print("Enter the Name: ");
        String name = sc.nextLine();
        System.out.print("Enter the Roll no.: ");
        int roll = sc.nextInt();
        sc.nextLine();
        System.out.println();
        for(int i = 0; i < registeredStudents.size(); i++){
            if((registeredStudents.get(i).name.equals(name)) && (registeredStudents.get(i).roll == roll)){
                registeredStudents.get(i).showDetails();
                return;
            }
        }
        System.out.println("No such student found");
        return;
    }

    public void getCompDetails(Scanner sc){
        System.out.print("Enter the Name: ");
        String name = sc.nextLine();
        System.out.println();
        for(int i = 0; i < registeredCompanies.size(); i++){
            if(registeredCompanies.get(i).name.equals(name)){
                registeredCompanies.get(i).showDetails();
                return;
            }
        }
        System.out.println("No such Company found");
        return;
    }

    public void getAvg(){
        int ans = 0, cnt = 0;
        for(int i = 0; i < registeredStudents.size(); i++){
            if((registeredStudents.get(i).status.equals("Accepted")) || (registeredStudents.get(i).status.equals("Offered"))){
                ans += registeredStudents.get(i).offer_ctc;
                cnt += 1;
            }
        }
        if(cnt == 0){
            System.out.println("No student has been placed yet");
        }else{
            System.out.printf("The Average CTC is %f", (float)ans/cnt);
        }
        return;
    }

    public void getCompProcessResults(Scanner sc){
        System.out.print("Enter the Name: ");
        String name = sc.nextLine();
        System.out.println();
        for(int i = 0; i < registeredCompanies.size(); i++){
            if(registeredCompanies.get(i).name.equals(name)){
                System.out.println("Following students have been accepted:");
                for(int j = 0; j < applications.get(i).size(); j++){
                    applications.get(i).get(j).offer(registeredCompanies.get(i).name, registeredCompanies.get(i).role, registeredCompanies.get(i).ctc);
                    applications.get(i).get(j).showDetails();
                }
            }
        }
        System.out.println("No such Company found");
        return;
    }

    public void getAvailableComp(Student student){
        int cnt = 0;
        ArrayList<Company> names = new ArrayList<Company>();
        for(int i = 0; i < registeredCompanies.size(); i++){
            if(can_apply(student, registeredCompanies.get(i))){
                cnt += 1;
                names.add(registeredCompanies.get(i));
            }
        }  
        if(cnt == 0){
            System.out.println("Cannot apply to any of the companies available");
            return;
        }
        for(int i = 0; i < cnt; i++){
            names.get(i).showDetails();
            System.out.println();
        }
        return;
    }

    public void registerCompany(Company company, Scanner sc){
        if(companyReg == false){
            System.out.println("Start the Company registration first");
            return;
        }
        DateTime cur_time = new DateTime();
        System.out.println("Enter the current time");
        cur_time.set_time(sc);
        if((cur_time.compare(companyStartTime) == -1) || (cur_time.compare(companyEndTime) == 1)){
            System.out.println("The registrations are closed");
            return;
        }
        registeredCompanies.add(company);
        System.out.println("The company has been registered");
        return;
    }

    public void registerStudent(Student student, Scanner sc){
        if(studentReg == false){
            System.out.println("Start the student registration first");
            return;
        }
        DateTime cur_time = new DateTime();
        System.out.println("Enter the current time");
        cur_time.set_time(sc);
        if((cur_time.compare(studentStartTime) == -1) || (cur_time.compare(studentEndTime) == 1)){
            System.out.println("The registrations are closed");
            return;
        }
        registeredStudents.add(student);
        System.out.println("The student has been registered");
        return;
    }

    public Boolean can_apply(Student student, Company company){
        if(student.cgpa < company.cutoff_cgpa){
            return false;
        }else{
            return true;
        }
    }

    public void apply(Student student, Scanner sc){
        if(student.status.equals("Blocked")){
            System.out.println("You have been blocked");
            return;
        }
        System.out.print("Enter the Company Name: ");
        String name = sc.nextLine();
        System.out.println();
        for(int i = 0; i < registeredCompanies.size(); i++){
            if(registeredCompanies.get(i).name.equals(name)){
                if(can_apply(student, registeredCompanies.get(i))){
                    if(student.status.equals("Accepted")){
                        if(registeredCompanies.get(i).ctc >= 3*student.offer_ctc){
                            applications.get(i).add(student);
                            System.out.println("You have been registered.");
                        }else{
                            System.out.println("You have already accepted an offer.");    
                        }
                    }else{
                        applications.get(i).add(student);
                        System.out.println("You have been registered.");
                    }
                }else{
                    System.out.println("You cannot apply for this company.");
                }
                return;
            }
        }
        System.out.println("No such Company found");
        return;
    }

    public void updateStudentCGPA(Student student, Scanner sc){
        System.out.print("Enter the old CGPA: ");
        if(student.cgpa == sc.nextFloat()){
            sc.nextLine();
            System.out.print("Enter the old CGPA: ");
            float new_cgpa = sc.nextFloat();
            student.updateCGPA(new_cgpa);
        }else{
            System.out.println("The old GPA doesn't match, try again later");
        }
        sc.nextLine();
        return;
    }

}

class DateTime{
    int date, month, year, hrs, min;

    void set_time(Scanner sc){
        System.out.print("Please enter the date: ");
        this.date = sc.nextInt();
        sc.nextLine();
        System.out.print("Please enter the month: ");
        this.month = sc.nextInt();
        sc.nextLine();
        System.out.print("Please enter the year: ");
        this.year = sc.nextInt();
        sc.nextLine();
        System.out.print("Please enter the hours: ");
        this.hrs = sc.nextInt();
        sc.nextLine();
        System.out.print("Please enter the minutes: ");
        this.min = sc.nextInt();
        sc.nextLine();
        System.out.println();
    }

    int compare(DateTime another){
        if(this.year < another.year) return -1;
        if(this.year > another.year) return 1;
        if(this.month < another.month) return -1;
        if(this.month > another.month) return 1;
        if(this.date < another.date) return -1;
        if(this.date > another.date) return 1;
        if(this.hrs < another.hrs) return -1;
        if(this.hrs > another.hrs) return 1;
        if(this.min < another.min) return -1;
        if(this.min > another.min) return 1;
        return 0;
    }
}