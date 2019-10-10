/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author 2ndyrGroupC
 */
public class Register {
        public void setBorder(){
        for(int i=0;i<100;i++){
            System.out.print("*");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        
        Scanner input = new Scanner(System.in);
        Accounts a = new Accounts();
//        a.insertAccounts();
        PersonalInformation p = new PersonalInformation();
        Schedules s = new Schedules();
        boolean statusT = true;
        while (statusT == true) {
            System.out.println("\nPress 1: to retrieve\nPress 2: Add new account \nPress 3: Updata\nPress 4: Add Data "
                    + "on existing account \n"
                    + "Press 5: Delete\nPress 6: Search\nPress 7: Exit");
            System.out.println("choice :\t");
            String choice = input.nextLine();
            switch (choice) {
                case "1":
                    boolean status = true;
                    while (status == true) {
                        System.out.println("\nRETRIEVE\nPress 1: user information\tPress 2: Schedule\tPress 3: accounts\tPress "
                                + "4: Exit");
                        String chosen = input.nextLine();
                        switch (chosen) {
                            case "1":
                                System.out.println("Users' informations");
                                p.retrievePInfo("usersaccount",0);
                                p.retrievePInfo("personalInformation",0);
                                break;
                            case "2":
                                System.out.println("Schedules");
                                s.retrieveSched("usersaccount",0);
                                s.retrieveSched("schedules",0);
                                break;
                            case "3":
                                System.out.println("Accounts");
                                a.retrieveAccounts();
                                break;
                            case "4":
                                status = false;
                                break;
                            default:
                                System.out.println("invalid input!");
                                break;
                        }
                    }
                    break;
                case "4":
                    boolean status1 = true;
                    while (status1 == true) {
                        System.out.println("\nADD DATA ON EXISTING ACCOUNT\nPress 1: user information\tPress 2: Schedule\tPress 3: Exit");
                        System.out.println("choice :\t");
                        String chosen = input.nextLine();
                        switch (chosen) {
                            case "1":
                                System.out.println("Users' informations");
                                p.retrievePInfo("usersaccount",0);
                                p.addDataOnExistingAccount();
                                break;
                            case "2":

                                System.out.println("Schedules");
                                s.retrieveSched("usersaccount",0);
                                s.addDataOnExistingAccount();
                                break;
                            case "3":
                                status1 = false;
                                break;

                            default:
                                System.out.println("invalid input!");
                        }
                    }
                    break;
                case "3":
                    boolean status2 = true;
                    while (status2 == true) {
                        System.out.println("\nUPDATE\nPress 1: user information\tPress 2: Schedule\tPress 3 : Exit");
                        System.out.print("choice :\t");
                        String chosen = input.nextLine();
                        switch (chosen) {
                            case "1":
                                System.out.println("Users' informations");
                                p.retrievePInfo("usersaccount",0);
                                p.updatePInfo();
                                break;
                            case "2":
                                System.out.println("Schedules");
                                s.retrieveSched("usersaccount",0);
                                s.updateSched();
                                break;
                            case "3":
                                status2 = false;
                                break;
                            default:
                                System.out.println("invalid input!");
                        }
                    }
                    break;
                case "5":
                    boolean status3 = true;
                    while (status3 == true) {
                        System.out.println("\nDELETE\nPress 1: user information\tPress 2: Schedule\tPress 3:  Exit");
                        String chosen = input.nextLine();
                        switch (chosen) {
                            case "1":
                                System.out.println("Users' informations");
                                p.retrievePInfo("usersaccount",0);
                                p.deletePInfo();
                                break;
                            case "2":
                                System.out.println("Schedules");
                                s.retrieveSched("usersaccount",0);
                                s.deleteSched();
                                break;
                            case "3":
                                status3 = false;
                                break;
                            default:
                                System.out.println("invalid input!");
                        }
                        break;
                    }
                case "6":
                    boolean status4 = true;
                    while (status4 == true) {
                        System.out.println("\nSEARCH\nPress 1: user information\tPress 2: Schedule\tPress 3: accounts\tPress 4: Exit");
                        System.out.println("choice :\t");
                        String chosen = input.nextLine();
                        switch (chosen) {
                            case "1":
                                System.out.println("Users' informations");
                                p.retrievePInfo("usersaccount",0);
                                p.SearchPInfo();
                                break;
                            case "2":
                                System.out.println("Schedules");
                                s.retrieveSched("usersaccount",0);
                                s.SearchSched();
                                break;
                            case "3":
                                System.out.println("Accounts");
                                a.SearchaCCOUNTS();
                                break;

                            case "4":
                                status4 = false;
                                break;
                            default:
                                System.out.println("invalid input!");
                        }
                        break;
                    }
                    break;
                case "2":
                    System.out.println("\nADD NEW ACCOUNT\n");
                    a.insertAccounts();
                    System.out.print("\nADD PERSONAL INFORMATION? Y/n\t:\t");
                    String ans = input.next();
                    if (ans.equalsIgnoreCase("y")) {
                        p.insertPInfo();
                    }
                    System.out.print("\nADD SCHEDULE/S? Y/n\t:\t");
                    String ans1 = input.next();
                    if (ans.equalsIgnoreCase("y")) {
                        s.checkSubject();
                    }
                    boolean status5 = true;
                    while (status5 == true) {
                        System.out.println("\tPress 2: Exit");
                        System.out.println("choice :\t");
                        String chosen = input.next();
                        switch (chosen) {
                            case "2":
                                status5 = false;
                                break;
                            default:
                                System.out.println("invalid input!");
                        }
                        break;
                    }
                    break;
                case "7":
                    statusT = false;
                    break;
                default:
                    System.out.println("tINVALID INPUT!");
                    break;
            }

        }
    }
}
