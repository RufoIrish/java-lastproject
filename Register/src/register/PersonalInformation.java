/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author 2ndyrGroupC
 */
public class PersonalInformation {
PreparedStatement ps;
    String myDriver = "org.gjt.mm.mysql.Driver";
    String myUrl = "jdbc:mysql://localhost/Accounts";
    List<Integer> ids;
    List<Integer> pINfoids;
    Scanner input;
    String fname;
    String lname;
    int age;
    int id;
    int accountId;

    public PersonalInformation() {
        input = new Scanner(System.in);
        ids = new ArrayList<>();
        pINfoids = new ArrayList<>();
    }

    public String checkString(String label, String name) {
        boolean status = true;
        while (status != false) {
            System.out.print(label + " : ");
            try {
                int b = input.nextInt();
            } catch (InputMismatchException e) {
                name = input.next();
                status = false;
            }
        }
        return name;
    }

    public int checkAge() {
        while (true) {
            System.out.println("Enter your age: ");
            String in = input.nextLine();
            try {
                age = (Integer.parseInt(in));
                break;
            } catch (IllegalArgumentException e) {
                in = input.nextLine();
            }
        }
        return age;
    }
    public int getLastId(String label) throws ClassNotFoundException, SQLException {
         ResultSet rs = null ;
        Class.forName(myDriver);
        int idp = 0;
        try (Connection con = DriverManager.getConnection(myUrl, "root", "")) {
            Statement st = con.createStatement();
            String query = ("SELECT * FROM " + label + " ORDER BY Id DESC LIMIT 1;");
            rs = st.executeQuery(query);
            if (rs.next()) {
                idp = rs.getInt("id");
                if (label.equalsIgnoreCase("personalInformation")) {
                    idp++;
                } else if (label.equalsIgnoreCase("usersaccount")) {
                    if (!(this.ids.contains(rs.getInt(1)))) {
                        this.ids.add(rs.getInt(1));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);;
        }finally{
            rs.close();
        }
        return idp;
    }

    public void add() throws ClassNotFoundException, SQLException {
        this.getLastId("personalInformation");
        try {
            Class.forName(myDriver);
            try (Connection conn = DriverManager.getConnection(myUrl, "root", "")) {
                System.out.println("id + " + id);
                String query = "insert into personalInformation  (id, account_id,firstname,lastname,age)"
                        + " values ('" + id + "', '" + accountId + "', '" + fname + "','" + lname + "', '" + age + "')";
               ps = conn.prepareStatement(query);
                ps.execute();
                System.out.println("Successfully added to database!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }finally{
            ps.close();
        }
    }

    public void insertPInfo() throws ClassNotFoundException, SQLException {
        fname = this.checkString("first name", "fname");
        lname = this.checkString("last name", "lname");
        age = this.checkAge();
        id = this.getLastId("personalInformation");
        accountId = this.getLastId("usersAccount");
        this.add();
    }

    public void addDataOnExistingAccount() throws ClassNotFoundException, SQLException {
        this.retrievePInfo("usersaccount",1);
        accountId = this.setInt("Account ID : ");
        for (Integer e : this.ids) {
            if (e == accountId) {
                id = this.getLastId("personalinformation");
                lname = this.checkString("last name", "lname");
                fname = this.checkString("first name", "fname");
                age = this.checkAge();
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

    public void updatePInfo() throws ClassNotFoundException, SQLException {
        this.retrievePInfo("usersaccount",0);
        this.retrievePInfo("personalinformation",1);
        int accId = setInt("Account ID : ");
        for (Integer e : ids) {
            if (e == accId) {
                int idP = setInt("It might have more than one row\n Try to specify the ID no : ");
                for (Integer k : this.pINfoids) {
                    if (idP == k) {
                        String f_name = setString("First name : ");
                        String l_name = setString("Last name : ");
                        int Updatedage = setInt("Age : ");
                        Class.forName(myDriver);
                        try (Connection con = DriverManager.getConnection(myUrl, "root", "")) {
                            String query = ("update personalInformation set firstname='" + f_name + "',lastname='" + l_name
                                    + "',age = " + Updatedage + " where account_id = " + accId + " and id = " + idP);
                            ps = con.prepareStatement(query);
                            ps.executeUpdate();
                            System.out.println("Updated!");
                        } catch (Exception err) {
                            System.err.println(err);
                        }finally{
                            ps.close();
                        }
                    }
                }
            }
        }
    }

    public void deletePInfo() throws ClassNotFoundException, SQLException {
        int accId = setInt("Account ID : ");
        System.out.println(age);
        Class.forName(myDriver);
        try (Connection con = DriverManager.getConnection(myUrl, "root", "")) {
            String query = ("delete from personalInformation where account_id = " + accId);
          ps = con.prepareStatement(query);
            ps.executeUpdate();
            System.out.println("Deleted Account no : " + accId);
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            ps.close();
        }
    }

    public void retrievePInfo(String label,int num) throws SQLException {
        ResultSet rs = null;
        try (Connection con = DriverManager.getConnection(myUrl, "root", "")) {
            Class.forName(myDriver);
            try (Connection conn = DriverManager.getConnection(myUrl, "root", "")) {
                Statement st = con.createStatement();
                String query = "SELECT * FROM " + label;
                ps = conn.prepareStatement(query);
               rs = st.executeQuery(query);
                while (rs.next()) {
                    if (label.equalsIgnoreCase("usersaccount") && num ==0) {
                        if (!ids.contains(rs.getInt(1))) {
                            ids.add(rs.getInt(1));
                        }
                    } else if (label.equalsIgnoreCase("personalinformation") && num == 1) {
                        if (!(pINfoids.contains(rs.getInt(1)))) {
                            pINfoids.add(rs.getInt(1));
                        }
                    } else if(label.equalsIgnoreCase("personalinformation") && num == 0){
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
        }finally{
            ps.close();
            rs.close();
        }

    }

    public void SearchPInfo() throws SQLException {
         ResultSet rs = null;
        int accId = this.setInt("Account id : ");
        try (Connection con = DriverManager.getConnection(myUrl, "root", "")) {
            Class.forName(myDriver);
            try (Connection conn = DriverManager.getConnection(myUrl, "root", "")) {
                Statement st = con.createStatement();
                String query = "SELECT * FROM PERSONALINFORMATION WHERE ACCOUNT_ID = " + accId;
                ps = conn.prepareStatement(query);
                rs = st.executeQuery(query);

                System.out.println("\nID\tACCOUNT_ID\tFIRST NAME\tLAST NAME\t\tAGE");
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
            ps.close();
            rs.close();
        }
    }

    public void idsPrint() {
        ids.stream().forEach((a) -> {
            System.out.println(a);
        });
    }

}
