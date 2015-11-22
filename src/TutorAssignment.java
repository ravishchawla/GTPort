import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TutorAssignment extends GUIDesign
  implements ActionListener
{
  String[] names = new String[100];
  JLabel tutorNamesLabel = new JLabel("Students");
  JComboBox tutorNamesBox = new JComboBox(names);

  DefaultComboBoxModel model = new DefaultComboBoxModel();

  String[] usernames = new String[100];
  JButton addTutorButton = new JButton(">>");

  JTextArea tutorNamesArea = new JTextArea();
  JButton submitButton = new JButton("Submit");

  JButton refreshButton = new JButton("Refresh");
  JPanel selectNamesPanel = new JPanel();
  JPanel selectedNamesPanel = new JPanel();

  JButton backButton = new JButton("Back");

  public TutorAssignment(GTPort gport)
  {
    super("Tutor Assignments", 3);

    gtPort = gport;
    setLayout(new BoxLayout(this, 3));

    header = new JPanel();
    header.setSize(getWidth(), 30);
    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));

    header.setBackground(color);
    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);

    addTutorButton.addActionListener(this);
    submitButton.addActionListener(this);

    selectNamesPanel.add(tutorNamesLabel);
    selectNamesPanel.add(tutorNamesBox);
    selectNamesPanel.add(addTutorButton);
    body.add(selectNamesPanel);

    tutorNamesBox.setModel(model);

    selectedNamesPanel.add(tutorNamesArea);
    body.add(selectedNamesPanel);

    refreshButton.addActionListener(this);
    backButton.addActionListener(this);

    buttonPanel.add(backButton);
    buttonPanel.add(submitButton);

    add(header);
    add(body);
    add(buttonPanel);
  }

  public void refresh()
  {
    try {
      Statement statement = gtPort.getConnection().createStatement();
      String query = "SELECT DISTINCT U.Name, T.Username FROM AppliedToTutor T, Section S, RegularUser U WHERE U.Username = T.Username AND S.Instructor = \"" + 
        gtPort.userName + "\" " + 
        "AND T.Title IN (SELECT Title FROM Section WHERE Instructor = \"" + 
        gtPort.userName + "\")";
      System.out.println(query);
      statement.executeQuery(query);

      System.out.println("executed query");
      ResultSet result = statement.getResultSet();

      for (int i = 0; result.next(); i++)
      {
        usernames[i] = result.getString(2);

        System.out.println(names[i]);

        model.addElement(result.getString(1));
      }

      result.close();
      tutorNamesBox.setModel(model);
    }
    catch (Exception exe)
    {
      System.out.println("errored in tutor assignment");
      System.out.println(exe);
    }
  }

  public void actionPerformed(ActionEvent eve)
  {
    Object source = eve.getSource();

    if (source == addTutorButton)
    {
      try
      {
        tutorNamesArea.append("\n" + (String)tutorNamesBox.getSelectedItem());

        Statement statement = gtPort.getConnection().createStatement();

        String query = "SELECT Title FROM AppliedToTutor WHERE Username = \"" + usernames[tutorNamesBox.getSelectedIndex()] + "\"";

        System.out.println(query);
        ResultSet result = statement.executeQuery(query);

        System.out.println("executed select statement");

        result.next();

        String title = result.getString(1);

        query = "INSERT INTO TutorFor VALUES (\"" + usernames[tutorNamesBox.getSelectedIndex()] + "\", \"" + title + "\")";

        System.out.println(query);
        System.out.println("writing query");

        statement.executeUpdate(query);
        statement.executeUpdate("INSERT INTO Tutor VALUES (\"" + usernames[tutorNamesBox.getSelectedIndex()] + "\")");

        statement.executeUpdate("DELETE FROM AppliedToTutor WHERE Username = \"" + usernames[tutorNamesBox.getSelectedIndex()] + "\"");

        System.out.println("executed update statemnt");
      }
      catch (Exception e)
      {
        System.out.println("erroed in tutorAssignment");
        System.out.println(e);
      }

    }

    if (source == backButton)
    {
      gtPort.changeCard("FacultyServices Panel");
    }
  }
}