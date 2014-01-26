import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TutorLogbook extends GUIDesign
  implements ActionListener
{
  JLabel tutorNameLabel = new JLabel("Tutor Name");
  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  Date time = new Date();

  JLabel timeLabel = new JLabel(dateFormat.format(time));

  JLabel courseLabel = new JLabel("Course Code");
  String[] courses = { "tada", "tralala" };
  JComboBox courseBox = new JComboBox(courses);
  JLabel studentIDLabel = new JLabel("Student ID");
  JTextField studentIDFieldField = new JTextField(20);
  JLabel studentNameLabel = new JLabel("Student Name");
  JTextField studentNameField = new JTextField(20);

  DefaultComboBoxModel model = new DefaultComboBoxModel();

  JPanel tutorNamePanel = new JPanel();
  JPanel courseCodePanel = new JPanel();
  JPanel studentIDPanel = new JPanel();
  JPanel studentNamePanel = new JPanel();
  JPanel timePanel = new JPanel();

  JButton backButton = new JButton("back");
  JButton submitButton = new JButton("Submit");

  String CRN = null;

  public TutorLogbook(GTPort gport)
  {
    super("Tutor Logbook", 3);

    gtPort = gport;

    setLayout(new BoxLayout(this, 3));

    courseBox.setModel(model);
    header = new JPanel();
    header.setSize(getWidth(), 30);
    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));

    header.setBackground(color);
    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);

    timePanel.add(timeLabel);

    tutorNamePanel.add(tutorNameLabel);
    courseCodePanel.add(courseLabel);
    courseCodePanel.add(courseBox);

    studentIDPanel.add(studentIDLabel);
    studentIDPanel.add(studentIDFieldField);

    studentNamePanel.add(studentNameLabel);
    studentNamePanel.add(studentNameField);

    backButton.addActionListener(this);
    submitButton.addActionListener(this);

    buttonPanel.add(backButton);
    buttonPanel.add(submitButton);

    body.add(tutorNamePanel);
    body.add(courseCodePanel);
    body.add(studentIDPanel);
    body.add(studentNamePanel);
    body.add(timePanel);

    add(header);
    add(body);
    add(buttonPanel);
  }

  public void refresh()
  {
    try
    {
      Statement statement = gtPort.getConnection().createStatement();

      statement.executeQuery("SELECT Count(*) FROM Tutor T WHERE T.Username = \"" + gtPort.userName + "\"");

      ResultSet result = statement.getResultSet();

      result.next();
      Integer ifTutor = Integer.valueOf(Integer.parseInt(result.getString(1)));

      if (ifTutor.intValue() == 0)
      {
        JOptionPane.showMessageDialog(this, "You are not registered as a tutor");
        gtPort.changeCard("StudentServices Panel");
      }

      result = statement.executeQuery("SELECT R.Name FROM RegularUser R WHERE R.Username = \"" + gtPort.userName + "\"");
      result.next();

      tutorNameLabel.setText("Tutor:::\t " + result.getString(1));

      statement.executeQuery("SELECT C.Code FROM TutorFor T, Code C WHERE (T.Username = \"" + gtPort.userName + "\" AND C.Title = T.Title)");

      result = statement.getResultSet();

      System.out.println("got result set");

      for (int i = 0; result.next(); i++)
      {
        model.addElement(result.getString(1));
      }
      System.out.println("added to model");
      courseBox = new JComboBox(model);
      revalidate();

      statement.executeQuery("SELECT S.CRN FROM TutorFor T, Code C, Section S WHERE C.Title = S.Title AND S.Title = T.Title");

      result = statement.getResultSet();
      result.next();
      CRN = result.getString(1);
    }
    catch (Exception exe)
    {
      System.out.println("erroed in tutor logbook");
      System.out.println(exe);
    }
  }

  public void actionPerformed(ActionEvent eve)
  {
    Object source = eve.getSource();

    if (source == submitButton)
    {
      try
      {
        String code = (String)courseBox.getSelectedItem();
        String student = studentIDFieldField.getText();
        String name = studentNameField.getText();

        Statement statement = gtPort.getConnection().createStatement();

        String query = "SELECT Username FROM RegularUser WHERE Username = \"" + student + "\" AND Name = \"" + name + "\" GROUP BY Username";
        System.out.println(query);

        ResultSet resultSet = statement.executeQuery(query);

        if (!resultSet.next()) {
          JOptionPane.showMessageDialog(this, "ID and Name fields don't match");
        }
        else {
          resultSet.next();
          query = "INSERT INTO LogsVisitDate VALUES (\"" + gtPort.userName + 
            "\", \"" + CRN + "\", \"" + 
            student + "\", \"" + 
            dateFormat.format(time) + "\")";
          statement.executeUpdate(query);

          System.out.println("crn: " + CRN + "\tstudent: " + student);
          System.out.println("executed the insert query");
        }

      }
      catch (Exception exe)
      {
        System.out.println("errored in tutor logbook submit");
        System.out.println(exe);
      }

    }

    if (source == backButton)
    {
      gtPort.changeCard("StudentServices Panel");
    }
  }
}