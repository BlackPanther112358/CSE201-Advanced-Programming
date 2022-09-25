package Assignment_1;

import java.util.*;

public class Company {

    String name;
    String role;
    int ctc;
    float cutoff_cgpa;
    DateTime startTime;
    DateTime endTime;

    void constructor(Scanner sc){
        System.out.print("Enter the name: ");
        this.name = sc.nextLine();
        System.out.print("Enter the role: ");
        this.role = sc.nextLine();
        System.out.print("Enter the CTC: ");
        this.ctc = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the CGPA required: ");
        this.cutoff_cgpa = sc.nextFloat();
        sc.nextLine();
    }

    void updateRole(Scanner sc){
        System.out.print("Enter the role: ");
        this.role = sc.nextLine();
    }

    void updateCTC(Scanner sc){
        System.out.print("Enter the CTC: ");
        this.ctc = sc.nextInt();
    }

    void updateCGPA(Scanner sc){
        System.out.print("Enter the CGPA required: ");
        this.cutoff_cgpa = sc.nextFloat();
    }

    void showDetails(){
        System.out.printf("Name: %s\n", this.name);
        System.out.printf("Package.: %d\n", this.ctc);
        System.out.printf("CGPA: %f\n", this.cutoff_cgpa);
        System.out.printf("Role: %s\n", this.role);
        return;
    }

}

class DateTime{
    int date, month, year, hrs, min;
}