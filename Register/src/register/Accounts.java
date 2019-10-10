/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author 2ndyrGroupC
 */
public class Accounts {
PreparedStatement preparedStmt = null;
    String myDriver = "org.gjt.mm.mysql.Driver";
    String myUrl = "jdbc:mysql://localhost/Accounts";
    int id;
    String pass;
    String username;
    Scanner input;

    public Accounts() {
        input = new Scanner(System.in);
    }

    public String checkString(String label) {
        boolean status = true;
        while (status != false) {
            System.out.print(label + " : ");
            try {
                int b = input.nextInt();
            } catch (InputMismatchException e) {
                username = input.nextLine();
                status = false;
            }
        }
        return username;
    }

    public String checkPass() {
        String confirmPass = "";
        boolean status = true;
        boolean status1 = true;

        while (status == true) {
            System.out.print("Password: ");
            try {
                pass = input.nextLine();
                if (pass.length() >= 8) {
                    status = false;
                    while (status1 == true) {
                        System.out.println("confirm password: ");
                        try {
                            confirmPass = input.nextLine();
                            if (pass.equals(confirmPass)) {
                                status1 = false;
                            }
                        } catch (InputMismatchException e) {
                            confirmPass = input.nextLine();
                        }
                    }
                };
            } catch (InputMismatchException e) {
                pass = input.nextLine();
                status = true;
            }
        }
        return pass;
    }

    public void getLastId() throws ClassNotFoundException, SQLException {
         ResultSet rs = null ;
        Class.forName(myDriver);
        try (Connection con = DriverManager.getConnection(myUrl, "root", "")) {
            Statement st = con.createStatement();
            String query = ("SELECT * FROM usersaccount ORDER BY Id DESC LIMIT 1;");
             rs = st.executeQuery(query);
            if (rs.next()) {
                id = rs.getInt("id");
                id++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            rs.close();
        }
    }

    public void insertAccounts() throws ClassNotFoundException, SQLException {
        checkString("username");
        checkPass();
        getLastId();
        try {
            Class.forName(myDriver);
            try (Connection conn = DriverManager.getConnection(myUrl, "root", "")) {
                String query = "insert into usersaccount  (id, username,password)"
                        + " values ('" + id + "', '" + username + "', '" + pass + "')";
                preparedStmt = conn.prepareStatement(query);
                preparedStmt.execute();
                System.out.println("username '" + username + "' is successfully added to database!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }finally{
            preparedStmt.close();
        }
    }

    public void retrieveAccounts() throws SQLException {
        try (Connection con = DriverManager.getConnection(myUrl, "root", "")) {
            Class.forName(myDriver);
            try (Connection conn = DriverManager.getConnection(myUrl, "root", "")) {
                Statement st = con.createStatement();
                String query = "SELECT * FROM USERSACCOUNT";
                preparedStmt = conn.prepareStatement(query);
                ResultSet rs = st.executeQuery(query);
                System.out.println("\nACCOUNT_ID\t  USERNAME\t  PASSWORD");
                while (rs.next()) {
                    System.out.print("\n" + rs.getString(1) + "\t\t");
                    System.out.print(rs.getString(2) + "\t\t");
                    System.out.print(rs.getString(3));
                }

            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }finally{
            preparedStmt.close();
        }
    }
    public void SearchaCCOUNTS() throws SQLException {
        System.out.println("Account id : ");
        int accId = input.nextInt();
        try (Connection con = DriverManager.getConnection(myUrl, "root", "")) {
            Class.forName(myDriver);
            try (Connection conn = DriverManager.getConnection(myUrl, "root", "")) {
                Statement st = con.createStatement();
                String query = "SELECT * FROM SCHEDULES WHERE USERS = " + accId;
               preparedStmt = conn.prepareStatement(query);
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
        }finally{
            preparedStmt.close();
        }
    }
}
