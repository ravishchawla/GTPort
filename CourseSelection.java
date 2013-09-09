import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class CourseSelection extends GUIDesign
  implements ActionListener
{
  JLabel termLabel;
  String term;
  String[] columns = { "Select", "CRN", "Title", "Instructor", "Time", "Days", "Location", "Section", "Code", "Mode of Grading" };
  int courses;
  Object[][] data;
  String[] modes = { "Audit", "Pass/Fail", "Letter Grding" };
  JTable table;
  DefaultTableModel model = new DefaultTableModel();

  JPanel termPanel = new JPanel();
  JPanel departmentPanel = new JPanel();

  JButton backButton = new JButton("Back");
  JButton registerButton = new JButton("Register");
  JButton refreshButton = new JButton("Refresh");
  JButton submitButton = new JButton("Submit");

  public CourseSelection(GTPort gPort)
  {
    super("Course Selection", 3);
    setLayout(new BoxLayout(this, 3));
    header = new JPanel();
    header.setSize(getWidth(), 30);
    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));

    header.setBackground(color);
    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);

    termLabel = new JLabel("Term:        " + term);

    data = new Object[25][10];
    model = new DefaultTableModel(data, columns)
    {
      public Class getColumnClass(int clmns)
      {
        if (clmns == 0) {
          return Boolean.class;
        }
        return Object.class;
      }
    };
    table = new JTable(model);
    table.setModel(model);

    termPanel.add(termLabel);

    JComboBox box = new JComboBox(modes);

    table.getColumnModel().getColumn(9).setCellEditor(new DefaultCellEditor(box));

    body.add(termPanel);
    body.add(departmentPanel);

    body.add(table.getTableHeader());
    body.add(table);

    buttonPanel.add(backButton);
    buttonPanel.add(refreshButton);
    buttonPanel.add(registerButton);

    backButton.addActionListener(this);
    refreshButton.addActionListener(this);
    registerButton.addActionListener(this);
    submitButton.addActionListener(this);

    buttonPanel.add(registerButton);
    buttonPanel.add(submitButton);
    add(header);
    add(buttonPanel);
    add(body);

    gtPort = gPort;
  }

  public Class getColumnClass(int column)
  {
    if (column == 1) {
      return Boolean.class;
    }
    return Object.class;
  }

  public void refresh()
  {
    try
    {
      Statement statement = gtPort.getConnection().createStatement();
      String query = "SELECT CRN, S.Title, Name, Time, Day, Location, Letter, Code FROM Section S, Code C, RegularUser U WHERE CRN NOT IN (SELECT R.CRN FROM Registers R WHERE Username = \"" + 
        gtPort.userName + "\") AND C.Title = S.Title AND U.Username = S.Instructor";

      System.out.println(query);
      statement.executeQuery(query);
      System.out.println("Statemetne execute ");

      ResultSet result = statement.getResultSet();

      System.out.println("created result");
      data = new Object[10][10];

      System.out.println("created data");
      Object[] values = new Object[10];

      System.out.println("created values");

      if (result == null)
      {
        System.out.println("result is null");
      }

      String[] titles = new String[100];

      model.setRowCount(0);
      table = new JTable(model);

      model.setRowCount(35);
      for (int i = 1; result.next(); i++)
      {
        values[0] = Boolean.FALSE;
        for (int j = 1; j < 9; j++) {
          values[j] = result.getString(j);
        }
        titles[i] = result.getString(2);
        model.insertRow(i, values);
      }

      String[] codes = new String[table.getColumnCount()];
      System.out.println("count: " + titles.length);

      System.out.println("before table");
      table = new JTable(model);

      System.out.println("created table");
    }
    catch (Exception exe)
    {
      System.out.println("errored in course selection");
      System.out.println(exe);
    }
  }

  public void actionPerformed(ActionEvent eve)
  {
    Object source = eve.getSource();

    if (eve.getSource().getClass().equals(JCheckBox.class))
    {
      JCheckBox checkBox = (JCheckBox)eve.getSource();
      Integer row = Integer.valueOf(Integer.parseInt(eve.getActionCommand()));

      System.out.println("row: \t" + row);

      if (checkBox.getParent().getClass().equals(JTable.class))
      {
        JTable localJTable = (JTable)checkBox.getParent();
      }

    }

    if (source == backButton) {
      gtPort.changeCard("StudentServices Panel");
    }
    if (source == refreshButton) {
      refresh();
    }
    if (source == submitButton)
    {
      gtPort.changeCard("RegistrationView Panel");
    }

    if (source == registerButton)
    {
      ArrayList selected = new ArrayList();
      boolean valBoolean;
      for (int i = 1; i < table.getColumnCount(); i++)
      {
        System.out.println("in loop");

        valBoolean = false;

        valBoolean = ((Boolean)table.getValueAt(i, 0)).booleanValue();

        if (valBoolean)
        {
          Object[] books = new Object[10];
          for (int k = 0; k < 10; k++)
          {
            books[k] = table.getValueAt(i, k);
          }

          selected.add(books);

          System.out.println("in if clause");

          String username = gtPort.userName;
          String CRN = (String)table.getValueAt(i, 1);

          String grade_Mode = (String)table.getValueAt(i, 9);

          System.out.println("grae " + grade_Mode);

          if (grade_Mode == null)
          {
            JOptionPane.showMessageDialog(this, "Choose a grade mode");
            break;
          }
          grade_Mode = Character.toString(grade_Mode.charAt(0));

          System.out.println("user: \t" + username + "\t" + CRN + "\t" + grade_Mode);
          try
          {
            System.out.println("in try clause");
            Statement statement = gtPort.getConnection().createStatement();

            System.out.println(statement.executeUpdate("INSERT INTO Registers (Username, CRN, Grade_Mode) VALUES (\"" + username + "\", \"" + CRN + "\", \"" + grade_Mode + "\")"));
          }
          catch (Exception exe)
          {
            System.out.println("erored in execute update course selection");
            System.out.println(exe);
          }

        }

      }

      gtPort.selectedData = selected;

      for (Object[] objects : gtPort.selectedData)
      {
        for (int i = 0; i < 10; i++) {
          System.out.print(objects[i] + "\t");
        }
        System.out.println();
      }

      refresh();
    }
  }
}