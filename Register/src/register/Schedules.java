/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author 2ndyrGroupC
 */
public class Schedules {

    String myDriver = "org.gjt.mm.mysql.Driver";
    String myUrl = "jdbc:mysql://localhost/Accounts";
    Scanner input;
    String subject;
    String schedule;
    int unit;
    int id;
    int account_id;
    List<Integer> ids;
    List<Integer> idSched;

    public Schedules() {
        input = new Scanner(System.in);
        ids = new ArrayList<>();
        idSched = new ArrayList<>();
    }

    public String checkString(String label, String name) {
        boolean status = true;
        while (status != false) {
            System.out.print(label + " : ");
            try {
                int b = input.nextInt();
            } catch (InputMismatchException e) {
                name = input.nextLine();
                status = false;
            }
        }
        return name;
    }

    public int checkUnit() {
        while (true) {
            System.out.println("Unit: ");
            String u_unit = input.nextLine();
            try {
                unit = (Integer.parseInt(u_unit));
                break;
            } catch (IllegalArgumentException e) {
                u_unit = input.nextLine();
            }
        }
        return unit;
    }

    public void checkSubject() throws IOException, ClassNotFoundException {
        int s = 1;
        boolean status1 = true;
        boolean status = true;
        while (status != false) {
            //                    System.out.print("Add subject : Yes/No?");
            try {
                int b = input.nextInt();
            } catch (InputMismatchException e) {
                while (status1 == true) {
                    System.out.print("Add subject : Yes/No? : ");
                    String res = input.nextLine().toLowerCase();
                    if (res.contains("yes")) {
                        System.out.print("number subject/s: ");
                        int i = input.nextInt();
                        while (i != 0) {
                            subject = this.checkString("subject", "subject");
                            this.checkUnit();
                            schedule = this.checkString("schedule", "schedule");
                            s++;
                            i--;
                            this.insertSched();
                        }
                        status1 = false;
                        status = false;
                    } else if (res.contains("no")) {
                        status1 = false;
                        status = false;
                    }
                }
            }
        }

    }

    public int getLastId(String label) throws ClassNotFoundException {
        Class.forName(myDriver);
        int idp = 0;
        try (Connection con = DriverManager.getConnection(myUrl, "root", "")) {
            Statement st = con.createStatement();
            String query = ("SELECT * FROM " + label + " ORDER BY Id DESC LIMIT 1;");
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                idp = rs.getInt("id");
                if (label.equalsIgnoreCase("schedules")) {
                    idp++;
                } else if (label.equalsIgnoreCase("usersaccount")) {
                    if (!(this.ids.contains(rs.getInt(1)))) {
                        this.ids.add(rs.getInt(1));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return idp;
    }

    public void add() throws IOException, ClassNotFoundException {
        try {
            Class.forName(myDriver);
            try (Connection conn = DriverManager.getConnection(myUrl, "root", "")) {
                System.out.println("id + " + id);
                String query = "insert into schedules  (id, account_id,subject,unit,schedule)"
                        + " values ('" + id + "', '" + account_id + "', '" + subject + "','" + unit + "', '" + schedule + "')";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.execute();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        System.out.println("Successfully added to database!");
    }

    public void insertSched() throws ClassNotFoundException, IOException {
        id = this.getLastId("schedules");
        account_id = this.getLastId("usersaccount");
        this.add();
    }

    public void addDataOnExistingAccount() throws ClassNotFoundException, IOException {
        this.retrieveSched("usersaccount",0);
        account_id = this.setInt("Account ID : ");
        for (Integer e : ids) {
            if (account_id == e) {
                id = this.getLastId("schedules");
                subject = this.checkString("subject", "subject");
                unit = this.checkUnit();
                schedule = this.checkString("schedule", "schedule");
                this.add();
            }
        }
    }

    public String setString(String label) {
        System.out.println(label + " : ");
        String result = input.next();
        return result;
    }

    public int setInt(String label) {
        System.out.println(label + " : ");
        int result = input.nextInt();
        return result;
    }

    public void updateSched() throws ClassNotFoundException, SQLException {
        this.retrieveSched("usersaccount",0);
        this.retrieveSched("schedules",1);
        int accId = setInt("Account ID : ");
        for (Integer i : ids) {
            if (i == accId) {
                int id = setInt("It might have more than one row\n Try to specify the ID no : ");
                for (Integer k : this.idSched) {
                    if (k == id) {
                        subject = this.checkString("subject", "subject");
                        this.checkUnit();
                        schedule = this.checkString("schedule", "schedule");
                        Class.forName(myDriver);
                        try (Connection con = DriverManager.getConnection(myUrl, "root", "")) {
                            String query = ("update schedules set subject='" + subject + "',unit='" + unit + "',schedule = '"
                                    + schedule + "' where account_id = " + accId + " AND ID = " + id);
                            PreparedStatement ps = con.prepareStatement(query);
                            ps.executeUpdate();
                            System.out.println("Updated!");
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                    }
                }

            }
        }

    }

    public void deleteSched() throws ClassNotFoundException {
        account_id = setInt("Account ID : ");
        id = setInt("It might have more than one row\n Try to specify the ID no : ");
        Class.forName(myDriver);
        try (Connection con = DriverManager.getConnection(myUrl, "root", "")) {
            String query = ("delete from schedules where account_id = " + account_id + " and id = " + id);
            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();
            System.out.println("Deleted Account no : " + account_id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void retrieveSched(String label, int num) {
        try (Connection con = DriverManager.getConnection(myUrl, "root", "")) {
            Class.forName(myDriver);
            try (Connection conn = DriverManager.getConnection(myUrl, "root", "")) {
                Statement st = con.createStatement();
                String query = "SELECT * FROM " + label;
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    if (label.equalsIgnoreCase("usersaccount") && num == 0) {
                        if (!ids.contains(rs.getInt(1))) {
                            ids.add(rs.getInt(1));
                        }
                    } else if (label.equalsIgnoreCase("schedules")&& num == 1) {
//                        System.out.println("\nID\tACCOUNT_ID\t  SUBJECT\t  UNIT\t\tSCHEDULES");
                        if (!(this.idSched.contains(rs.getInt(1)))) {
                            this.idSched.add(rs.getInt(1));
                        }
                    } else if (label.equalsIgnoreCase("schedules")&& num == 0) {
                        System.out.print("\n" + rs.getString(1) + "\t\t");
                        System.out.print(rs.getString(2) + "\t\t");
                        System.out.print(rs.getString(3) + "\t\t");
                        System.out.print(rs.getString(4) + "\t\t");
                        System.out.print(rs.getString(5));
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public void SearchSched() {
        int accId = this.setInt("Account id : ");
        try (Connection con = DriverManager.getConnection(myUrl, "root", "")) {
            Class.forName(myDriver);
            try (Connection conn = DriverManager.getConnection(myUrl, "root", "")) {
                Statement st = con.createStatement();
                String query = "SELECT * FROM SCHEDULES WHERE ACCOUNT_ID = " + accId;
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                ResultSet rs = st.executeQuery(query);
                System.out.println("\nID\tACCOUNT_ID\t  SUBJECT\t  UNIT\t\tSCHEDULES");
                while (rs.next()) {
                    System.out.print("\n" + rs.getString(1) + "\t\t");
                    System.out.print(rs.getString(2) + "\t\t");
                    System.out.print(rs.getString(3) + "\t\t");
                    System.out.print(rs.getString(4) + "\t\t");
                    System.out.print(rs.getString(5));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public void idsPrint() {
        ids.stream().forEach((a) -> {
            System.out.println(a);
        });
    }

}
