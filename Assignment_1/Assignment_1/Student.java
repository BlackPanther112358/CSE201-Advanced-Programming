package Assignment_1;

import java.util.*;

public class Student {
    public String name;
    public int roll;
    public float cgpa;
    public String branch;
    public String status = "Unoffered";
    public String offer_comp = "-";
    public int offer_ctc = 0;
    public String offer_role = "-";

    void constructor(Scanner sc){
        System.out.print("Enter the Name: ");
        this.name = sc.nextLine();
        System.out.print("Enter the Roll no.: ");
        this.roll = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the CGPA: ");
        this.cgpa = sc.nextFloat();
        sc.nextLine();
        System.out.print("Enter the Branch: ");
        this.branch = sc.nextLine();
        System.out.println();
        return;
    }

    void showDetails(){
        System.out.printf("Name: %s\n", this.name);
        System.out.printf("Roll no.: %d\n", this.roll);
        System.out.printf("CGPA: %f\n", this.cgpa);
        System.out.printf("Branch: %s\n", this.branch);
        System.out.printf("Status: %s\n", this.status);
        return;
    }

    void getCurrStatus(){
        System.out.println(status);
        if(status.equals("Offered")){
            System.out.println("Offer Details:-");
            System.out.printf("Company: %s\n", this.offer_comp);
            System.out.printf("Role: %s\n", this.offer_role);
            System.out.printf("Package %d\n", this.offer_ctc);
        }
        return;
    }

    void updateCGPA(float new_cgpa){
        this.cgpa = new_cgpa;
    }

    void offer(String company_name, String role, int ctc){
        if(ctc > this.offer_ctc){
            this.offer_ctc = ctc;
            this.offer_comp = company_name;
            this.offer_role = role;
            this.status = "Offered";
        }
        return;
    }

    void acceptOffer(){
        this.status = "Accepted";
        System.out.println("The offer from " + this.offer_comp + " has been accepted.");
        return;
    }

    void rejectOffer(){
        this.offer_comp = "-";
        this.offer_ctc = 0;
        this.offer_role = "-";
        this.status = "Blocked";
        System.out.println("You have rejected the offer and are now blocked.");
        return;
    }

}
