import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Connector
{
  Connection con = null;
  Statement s;

  public static void main(String[] args)
  {
    Connector connec = new Connector();
  }

  public Connector()
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group32", "cs4400_Group32", "fhYsXG6P");

      if (!con.isClosed())
        System.out.println("Succesfully connected to MySQL server using TCP/IP...");
    }
    catch (Exception e)
    {
      System.err.println("Exception: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public Connection getConnection()
  {
    return con;
  }
}