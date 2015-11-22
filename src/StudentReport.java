import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudentReport extends GUIDesign
  implements ActionListener
{
  String[] columns = { "Instructor", "Course Code", "Course Name", "Average Grade" };
  int courses;
  Object[][] data;
  DefaultTableModel model = new DefaultTableModel();

  JPanel tablePanel = new JPanel();
  JTable table;
  JButton backButton = new JButton("Back");
  JButton refreshButton = new JButton("Refresh");

  public StudentReport(GTPort gport)
  {
    super("Student Report", 3);

    gtPort = gport;

    setLayout(new BoxLayout(this, 3));
    setPreferredSize(new Dimension(500, 800));

    header = new JPanel();
    header.setSize(getWidth(), 30);
    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));

    header.setBackground(color);
    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);

    data = new Object[15][4];

    model = new DefaultTableModel(data, columns);

    table = new JTable(model);

    JScrollPane scrollPane = new JScrollPane();

    body.add(table.getTableHeader());
    body.add(table);

    backButton.addActionListener(this);
    buttonPanel.add(backButton);

    buttonPanel.add(backButton);
    add(header);
    add(body);
    add(buttonPanel);
  }

  public void refresh()
  {
    try
    {
      Statement statement = gtPort.getConnection().createStatement();

      statement.executeUpdate("DROP VIEW IF EXISTS cs4400_Group32.grade_view");
      statement.executeUpdate("DROP VIEW IF EXISTS count_view");

      String query = "CREATE VIEW grade_view AS SELECT F.Username AS Instructor, Code AS Course_Code, C.Title as Course_Name, R.Grade, Count(*) AS Total_points FROM Faculty F, Section S, Code C, Registers R where  (F.Username = S.Instructor AND C.Title = S.Title AND R.CRN = S.CRN) GROUP BY F.Username, R.Grade";

      System.out.println(query);

      statement.executeUpdate(query);

      statement.executeQuery("SELECT * FROM grade_view");
      ResultSet gradeResult = statement.getResultSet();
      System.out.println("got result sert?");

      Integer[] totalPoints = new Integer[100];

      int total = 0;
      Object[][] pseudoData = new Object[100][6];
      System.out.println(": grade view :");

      for (int i = 1; gradeResult.next(); i++)
      {
        for (int j = 1; j < 4; j++)
        {
          pseudoData[i][0] = Integer.valueOf(total + 1);
          pseudoData[i][j] = gradeResult.getString(j);
        }

        total++;
        System.out.println(gradeResult.getString(1) + "\t" + gradeResult.getString(2) + "\t" + gradeResult.getString(3) + "\t\t\t" + gradeResult.getString(4) + "\t" + gradeResult.getString(5));

        if (gradeResult.getString(4).equals("A"))
          pseudoData[i][4] = Integer.valueOf(4);
        else if (gradeResult.getString(4).equals("B"))
          pseudoData[i][4] = Integer.valueOf(3);
        else if (gradeResult.getString(4).equals("C"))
          pseudoData[i][4] = Integer.valueOf(2);
        else if (gradeResult.getString(4).equals("D"))
          pseudoData[i][4] = Integer.valueOf(1);
        else if (gradeResult.getString(4).equals("F")) {
          pseudoData[i][4] = Integer.valueOf(0);
        }
        pseudoData[i][5] = Integer.valueOf(Integer.parseInt(gradeResult.getString(5)));
      }

      System.out.println("executed grade_view");

      for (int i = 1; i < 25; i++)
      {
        for (int j = 0; j < 6; j++) {
          System.out.print(pseudoData[i][j] + "\t");
        }
        System.out.println();
      }

      query = "CREATE VIEW count_view AS SELECT Instructor, SUM(Total_Points) AS Student_Grade_count FROM grade_view GROUP BY Instructor";

      statement.executeUpdate(query);

      System.out.println("executed count_view");
      query = "SELECT * FROM count_view";

      statement.executeQuery(query);
      ResultSet result = statement.getResultSet();

      System.out.println("got result set");

      System.out.println(": count view :");

      Object[][] finalData = new Object[100][7];
      String name = (String)pseudoData[1][1];
      pseudoData[5][4] = Integer.valueOf(4);
      int product = 0;
      for (int i = 1; i < pseudoData.length - 1; i++)
      {
        System.out.println("gonna check for name");

        if (pseudoData[i][1] == null)
        {
          finalData[i][3] = Integer.valueOf(product);
          finalData[i][0] = name;
          finalData[i][1] = pseudoData[i][2];
          finalData[i][2] = pseudoData[i][3];

          System.out.println(result.next());
        }
        else if (((String)pseudoData[i][1]).equals(name))
        {
          int first = ((Integer)pseudoData[i][4]).intValue();
          int second = ((Integer)pseudoData[i][5]).intValue();

          product += first * second;
        }
        else
        {
          System.out.println(product + "\t" + name);
          finalData[i][5] = Integer.valueOf(product);
          finalData[i][0] = name;
          finalData[i][1] = pseudoData[i][2];
          finalData[i][2] = pseudoData[i][3];
          result.next();
          finalData[i][4] = result.getString(2);

          product = 0;
          name = (String)pseudoData[i][1];

          int first = ((Integer)pseudoData[i][4]).intValue();
          int second = ((Integer)pseudoData[i][5]).intValue();

          product += first * second;
        }

      }

      model.setRowCount(0);
      table = new JTable(model);
      model.setRowCount(15);
      for (int i = 0; i < finalData.length; i++)
      {
        if (finalData[i][1] != null)
        {
          String first = (String)finalData[i][4];
          Integer second = (Integer)finalData[i][5];

          int fInteger = Integer.parseInt(first);

          Object[] values = { finalData[i][0], finalData[i][1], finalData[i][2], Double.valueOf(second.intValue() / fInteger) };

          model.insertRow(0, values);
        }

      }

      table = new JTable(model);
    }
    catch (Exception exe)
    {
      System.out.println("errored in student report");
      System.out.println(exe);
    }
  }

  public void actionPerformed(ActionEvent eve)
  {
    Object source = eve.getSource();

    if (source == backButton)
    {
      gtPort.changeCard("StudentServices Panel");
    }
  }
}