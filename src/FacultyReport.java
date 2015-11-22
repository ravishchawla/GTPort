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

public class FacultyReport extends GUIDesign
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

  public FacultyReport(GTPort gport)
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

      statement.executeUpdate("DROP VIEW IF EXISTS cs4400_Group32.Number_of_meetings");
      statement.executeUpdate("DROP VIEW IF EXISTS faculty_count_view");

      String query = "CREATE VIEW Number_of_meetings AS SELECT C.Code, C.Title, Count(*) AS Number_of_meetings, grade FROM Section S, Code C, LogsVisitDate L, Registers R WHERE (S.Instructor = \"" + gtPort.userName + "\"" + 
        " AND C.Title = S.Title AND L.CRN = S.CRN AND R.CRN = S.CRN) GROUP BY (C.Code)";

      System.out.println(query);

      statement.executeUpdate(query);

      statement.executeQuery("SELECT * FROM Number_of_meetings");
      ResultSet gradeResult = statement.getResultSet();
      System.out.println("got result sert?");

      Integer[] totalPoints = new Integer[100];

      int total = 0;
      Object[][] pseudoData = new Object[100][6];
      System.out.println(": grade view :");

      for (int i = 0; gradeResult.next(); i++)
      {
        for (int j = 1; j < 4; j++)
        {
          System.out.print(gradeResult.getString(j) + "\t");

          pseudoData[i][0] = Integer.valueOf(total + 1);
          pseudoData[i][j] = gradeResult.getString(j);
        }

        total++;

        System.out.println();

        query = "CREATE VIEW faculty_count_view AS SELECT code, grade, Count(*) AS faculty_Grade_count FROM Number_of_meetings GROUP BY code, grade";

        statement.executeUpdate(query);

        System.out.println("executed count_view");
        query = "SELECT * FROM faculty_count_view";

        statement.executeQuery(query);
        ResultSet result = statement.getResultSet();

        result.next();
        if (result.getString(2).equals("A"))
          pseudoData[i][4] = Integer.valueOf(4);
        else if (result.getString(2).equals("B"))
          pseudoData[i][4] = Integer.valueOf(3);
        else if (result.getString(2).equals("C"))
          pseudoData[i][4] = Integer.valueOf(2);
        else if (result.getString(2).equals("D"))
          pseudoData[i][4] = Integer.valueOf(1);
        else if (result.getString(2).equals("F")) {
          pseudoData[i][4] = Integer.valueOf(0);
        }
        pseudoData[i][5] = Integer.valueOf(Integer.parseInt(result.getString(3)));

        statement.executeQuery("SELECT * FROM faculty_count_view");
        result = statement.getResultSet();

        System.out.println("got result set");

        for (int i1 = 0; result.next(); i1++)
        {
          for (int j = 1; j < 4; j++) {
            System.out.print(result.getString(j) + "\t");
          }
          System.out.println();
        }

        System.out.println(": count view :");

        statement.executeQuery("SELECT * FROM faculty_count_view");
        result = statement.getResultSet();

        Object[][] finalData = new Object[100][7];
        String name = (String)pseudoData[1][1];
        pseudoData[5][4] = Integer.valueOf(4);
        int product = 0;
        for (int i1 = 1; i1 < pseudoData.length - 1; i1++)
        {
          if (pseudoData[i1][1] == null)
          {
            finalData[i1][3] = Integer.valueOf(product);
            finalData[i1][0] = name;
            finalData[i1][1] = pseudoData[i1][2];
            finalData[i1][2] = pseudoData[i1][3];

            result.next();
          }
          else if (((String)pseudoData[i1][1]).equals(name))
          {
            int first = ((Integer)pseudoData[i1][4]).intValue();
            int second = ((Integer)pseudoData[i1][5]).intValue();

            product += first * second;
          }
          else
          {
            System.out.println(product + "\t" + name);
            finalData[i1][5] = Integer.valueOf(product);
            finalData[i1][0] = name;
            finalData[i1][1] = pseudoData[i1][2];
            finalData[i1][2] = pseudoData[i1][3];
            result.next();
            finalData[i1][4] = result.getString(2);

            product = 0;
            name = (String)pseudoData[i1][1];

            int first = ((Integer)pseudoData[i1][4]).intValue();
            int second = ((Integer)pseudoData[i1][5]).intValue();

            product += first * second;
          }

          System.out.println(product);
        }

        for (int k = 0; k < finalData.length; k++)
        {
          if (finalData[k][1] != null)
          {
            String first = (String)finalData[k][4];
            Integer second = (Integer)finalData[k][5];

            int fInteger = Integer.parseInt(first);

            Object[] values = { finalData[k][0], finalData[k][1], finalData[k][2], Double.valueOf(second.intValue() / fInteger) };

            model.addRow(values);
          }

        }

        table = new JTable(model);
      }

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