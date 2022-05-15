import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.print.DocFlavor.STRING;

public class App {
    public static void main(String[] args){
       
        Connection conn =null;
        try {
            conn =DriverManager.getConnection("jdbc:sqlite:movies.db");
            System.out.println("created db");

            
            
            

            try {
                createTable(conn);
                
            } catch (Exception crea) {
               deleteTable(conn);
               createTable(conn);
            }
            

            System.out.println();
            System.out.println("Insertng the data");
            insertMovie(conn,"Dr.strange","sam Raimi","Benedict Cumberbatch ","Elizebeth olsen",2022,"7.4");
            insertMovie(conn,"Jai Bhim","T J Gnanavel","Surya","Lijo Mol Jose",2021,"8.9");
            insertMovie(conn,"Anbe Sivan","Sundar C","Kamal Hasan","Kiran Rathod",2003,"8.4");
            insertMovie(conn,"3 idiots","Rajkumar Hirani","Amir Khan","Kareena Kapoor",2009,"8.3");
            insertMovie(conn,"Dangal","Nitesh Tiwari","Amir Khan","Sakshi Tanwar",2016,"8.3");
            insertMovie(conn, "Asuran","Vetrimaaran","Dhanush","Manju Warrier",2019,"8.5");
            insertMovie(conn,"Driahyam 2","Jeethu Joseph","Mohanlal","Meena",2021,"8.4");
            insertMovie(conn,"Anniyan","S. Shankar","Vikram","Sada",2005,"8.3");
            insertMovie(conn, "Vada Chennai","Vetrimaaran","Dhanush","Radha Ravi",2018,"8.4");
            insertMovie(conn, "Karnan","Mari Selvaraj","Dhanush","Rajisha Vijayan",2021,"8.1");
            insertMovie(conn,"PK","Rajkumar Hirani","Amir Khan","Anushka Sharma",2014,"8.1");
            





        while(true){

            System.out.println("1.Movies\n2.Filter\n\nPress ctrl+c to quit\n");
            Scanner sc= new Scanner(System.in);
            int a= sc.nextInt();
            if(a==1){
                System.out.println("Displaying Database");
                System.out.println();
                System.out.println();
                System.out.println();
                display(conn,"movies");
            }
            else{
                filter(conn,"movies");
            } 

        }
            




        } catch (SQLException e) {
           
            e.printStackTrace();
            System.out.println(e.getClass().getName() + ":" +e.getMessage());
            //TODO: handle exception
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage());
                    //TODO: handle exception
                }
            }
        }

    }

    private static void display(Connection conn, String tablename) throws SQLException {
        String selectSql="SELECT * from "+ tablename;
        Statement stmt =conn.createStatement();
        ResultSet rs =stmt.executeQuery(selectSql);

        System.out.println("........."+tablename+".........");
        while(rs.next()){
            System.out.println("Movie :"+rs.getString("Title")+ ",");
            System.out.println(rs.getString("Director") +",");
            System.out.println(rs.getString("Leadactor")+",");
            System.out.println(rs.getString("Leadactress")+",");
            System.out.println(rs.getInt("Year")+",");
            System.out.println(rs.getString("Rating"));
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }


        
    }

    private static void insertMovie(Connection conn,String title, String director,String leadactor,String leadactress,int year,String rating) throws SQLException {
        String insertSQL ="INSERT INTO movies(title,director,leadactor,leadactress,year,rating) VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt =conn.prepareStatement(insertSQL);
        pstmt.setString(1,title);
        pstmt.setString(2, director);
        pstmt.setString(3, leadactor);
        pstmt.setString(4, leadactress);
        pstmt.setInt(5, year);
        pstmt.setString(6, rating);
        pstmt.executeUpdate();

    }


    private static void createTable(Connection conn) throws SQLException{
        String createTablesql =""+
        "CREATE TABLE movies "+
        "( "+
        "title varchar(255),"+
        "director varchar(255),"+
        "leadactor varchar(255),"+
        "leadactress varchar(255),"+
        "year int(255),"+
        "rating varchar(255)"+
        ");"+
        "";

        Statement stmt =conn.createStatement();
        stmt.execute(createTablesql);
        
    }

    private static void deleteTable(Connection conn) throws SQLException {
        String deleteTableSQL ="DROP TABLE Movies";
        Statement stmt =conn.createStatement();
        stmt.execute(deleteTableSQL);
    }


    private static void filter(Connection conn,String table) throws SQLException {
        System.out.println("\n\nFilter accordingly..");
        System.out.println("1.Actor\n2.Rating\n3.Director\n");
        Scanner sc= new Scanner(System.in);
        int b=sc.nextInt();
        switch (b) {
            case 1:
                System.out.println("\nSelect actor\n");
                Scanner ab= new Scanner(System.in);
                String a=ab.nextLine();
                String fil ="SELECT * from movies"
                +" WHERE leadactor =\'"+a+"\';";
                Statement stmt =conn.createStatement();
                ResultSet rs= stmt.executeQuery(fil);
                while(rs.next()){
                    System.out.println("Movie :"+rs.getString("Title")+ ",");
                    System.out.println(rs.getString("Director") +",");
                    System.out.println(rs.getString("Leadactor")+",");
                    System.out.println(rs.getString("Leadactress")+",");
                    System.out.println(rs.getInt("Year")+",");
                    System.out.println(rs.getString("Rating"));
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                }
                break;

            case 2:
                System.out.println("The movies rated above..\n");
                Scanner m= new Scanner(System.in);
                Float id=m.nextFloat();
                String fil2 ="SELECT * from movies"
                +" WHERE rating > "+id+";";
                Statement stamt =conn.createStatement();
                ResultSet rs2= stamt.executeQuery(fil2);
                while(rs2.next()){
                    System.out.println("Movie :"+rs2.getString("Title")+ ",");
                    System.out.println(rs2.getString("Director") +",");
                    System.out.println(rs2.getString("Leadactor")+",");
                    System.out.println(rs2.getString("Leadactress")+",");
                    System.out.println(rs2.getInt("Year")+",");
                    System.out.println(rs2.getString("Rating"));
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                }
                break;

            case 3:
                System.out.println("\nSelect actor");
                Scanner ab2= new Scanner(System.in);
                String a2=ab2.nextLine();
                String fil3 ="SELECT * from movies"
                +" WHERE director =\'"+a2+"\';";
                Statement stmt2 =conn.createStatement();
                ResultSet rs3= stmt2.executeQuery(fil3);
                while(rs3.next()){
                    System.out.println("Movie :"+rs3.getString("Title")+ ",");
                    System.out.println(rs3.getString("Director") +",");
                    System.out.println(rs3.getString("Leadactor")+",");
                    System.out.println(rs3.getString("Leadactress")+",");
                    System.out.println(rs3.getInt("Year")+",");
                    System.out.println(rs3.getString("Rating"));
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }
            break;
                
        
            default:
                break;
        }
        
    }

    
}

