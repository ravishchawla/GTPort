import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StudentPersonalInformation extends GUIDesign
  implements ActionListener
{
  JLabel nameLabel = new JLabel("Name");
  JLabel dobLabel = new JLabel("Date of Birth");
  JLabel genderLabel = new JLabel("Gender");
  JLabel addressLabel = new JLabel("Address");
  JLabel permanentAddressLabel = new JLabel("Permanent Address");
  JLabel contactNumberLabel = new JLabel("Contact Number");
  JLabel emailAddressLabel = new JLabel("Email Address");
  JLabel willingToTutorLabel = new JLabel("Willing to Tutor");
  JLabel selectCoursesLabel = new JLabel("<html>If Yes, select the courses<br> you would like to tutor for</html>");

  JLabel majorLabel = new JLabel("major");
  JLabel degreeLabel = new JLabel("Degree");

  JTextField nameField = new JTextField(20);
  JTextField dobField = new JTextField(20);

  String[] sexes = { "M", "F" };
  JComboBox genderBox = new JComboBox(sexes);

  JTextField addressField = new JTextField(20);
  JTextField permanentAddressField = new JTextField(20);
  JTextField contactNumberField = new JTextField(20);
  JTextField emailAddressField = new JTextField(20);
  JTextField willingToTutorField = new JTextField(20);

  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-ddhh:mm:ss.SSS-Z");

  java.util.Date time = new java.util.Date();

  String[] courses = { "1", "2", "Intro Aerospace Engineering" };
  JComboBox selectCoursesBox = new JComboBox(courses);
  DefaultComboBoxModel coursesModel = new DefaultComboBoxModel();
  JButton addCourse = new JButton("+");
  String theCourses = "";
  JLabel coursesField = new JLabel();

  String[] majors = { "Aerospace Engineering", "Biology", "Biomedical Engineering", "Computer Science", "Electrical and Computer Engineering" };
  JComboBox majorBox = new JComboBox(majors);

  String[] degrees = { "BS", "MS", "Ph.D" };
  JComboBox degreeBox = new JComboBox(degrees);

  JPanel namePanel = new JPanel();
  JPanel dobPanel = new JPanel();
  JPanel genderPanel = new JPanel();
  JPanel addressPanel = new JPanel();
  JPanel permanentAddressPanel = new JPanel();
  JPanel contactNumberPanel = new JPanel();
  JPanel emailAddressPanel = new JPanel();
  JPanel willingToTutorPanel = new JPanel();
  JPanel selectCoursesPanel = new JPanel();
  JPanel majorsDegreePanel = new JPanel();

  JPanel previousEducationPanel = new JPanel();
  JLabel previousEducationLabel = new JLabel("Previous Education");

  JLabel institutionNameLabel = new JLabel("Name of Institution Attended");
  JLabel educationMajorLabel = new JLabel("Major");
  JLabel educationDegreeLabel = new JLabel("Degree");
  JLabel graduationYearLabel = new JLabel("Year of Graduation");
  JLabel gPALabel = new JLabel("GPA");

  JTextField institutionNameField = new JTextField(20);
  JTextField graduationYearField = new JTextField(20);
  JTextField gPAField = new JTextField(20);
  JTextField educationMajorField = new JTextField(20);
  JComboBox educationDegreeBox = new JComboBox(degrees);

  JPanel institutionNamePanel = new JPanel();
  JPanel majorPanel = new JPanel();
  JPanel degreePanel = new JPanel();
  JPanel graduationyearPenel = new JPanel();
  JPanel gPAPanel = new JPanel();
  JButton addButton = new JButton("+");

  JButton backButton = new JButton("Back");
  JButton submitButton = new JButton("Submit");
  JButton refreshButton = new JButton("refresh");
  String name;
  String dob;
  String gender;
  String address;
  String permanentAddress;
  String contactNumber;
  String emailAddress;
  String willingToTutor;
  String major;
  String degree;
  String selectedCourse;
  String institution;
  String yearGrad;
  String educationDegree;
  String educationMajor;
  String gpa;

  public StudentPersonalInformation(GTPort gport)
  {
    super("Student Personal Information", 3);
    gtPort = gport;
    setLayout(new BoxLayout(this, 3));
    header = new JPanel();
    header.setSize(getWidth(), 30);
    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));

    header.setBackground(color);
    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);
    namePanel.add(nameLabel);
    namePanel.add(nameField);
    body.add(namePanel);

    dobPanel.add(dobLabel);

    dobPanel.add(dobField);
    body.add(dobPanel);

    genderPanel.add(genderLabel);

    genderPanel.add(genderBox);
    body.add(genderPanel);

    addressPanel.add(addressLabel);

    addressPanel.add(addressField);
    body.add(addressPanel);

    permanentAddressPanel.add(permanentAddressLabel);
    permanentAddressPanel.add(permanentAddressField);
    body.add(permanentAddressPanel);

    contactNumberPanel.add(contactNumberLabel);
    contactNumberPanel.add(contactNumberField);
    body.add(contactNumberPanel);

    emailAddressPanel.add(emailAddressLabel);
    emailAddressPanel.add(emailAddressField);
    body.add(emailAddressPanel);

    willingToTutorPanel.add(willingToTutorLabel);
    willingToTutorPanel.add(willingToTutorField);
    body.add(willingToTutorPanel);

    selectCoursesPanel.add(selectCoursesLabel);
    selectCoursesPanel.add(selectCoursesBox);

    selectCoursesBox.setModel(coursesModel);
    addCourse.addActionListener(this);
    selectCoursesPanel.add(addCourse);
    selectCoursesPanel.add(coursesField);
    selectCoursesBox.addActionListener(this);
    body.add(selectCoursesPanel);

    majorsDegreePanel.add(majorLabel);
    majorsDegreePanel.add(majorBox);
    majorsDegreePanel.add(new JLabel("     "));
    majorsDegreePanel.add(degreeLabel);
    majorsDegreePanel.add(degreeBox);
    body.add(majorsDegreePanel);

    previousEducationPanel.setLayout(new BoxLayout(previousEducationPanel, 1));
    previousEducationPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    previousEducationLabel.setFont(new Font("Helvetica", 1, 10));
    previousEducationPanel.add(previousEducationLabel);
    institutionNameField.addActionListener(this);
    educationMajorField.addActionListener(this);
    educationDegreeBox.addActionListener(this);
    graduationYearField.addActionListener(this);
    gPAField.addActionListener(this);

    institutionNamePanel.add(institutionNameLabel);
    institutionNamePanel.add(institutionNameField);
    previousEducationPanel.add(institutionNamePanel);

    majorPanel.add(educationMajorLabel);
    majorPanel.add(educationMajorField);
    previousEducationPanel.add(majorPanel);

    degreePanel.add(educationDegreeLabel);
    degreePanel.add(educationDegreeBox);
    previousEducationPanel.add(degreePanel);

    graduationyearPenel.add(graduationYearLabel);
    graduationyearPenel.add(graduationYearField);
    previousEducationPanel.add(graduationyearPenel);

    gPAPanel.add(gPALabel);
    gPAPanel.add(gPAField);
    previousEducationPanel.add(gPAPanel);

    addButton.addActionListener(this);
    previousEducationPanel.add(addButton);

    body.add(previousEducationPanel);

    refreshButton.addActionListener(this);
    submitButton.addActionListener(this);
    backButton.addActionListener(this);

    buttonPanel.add(refreshButton);
    buttonPanel.add(backButton);
    buttonPanel.add(submitButton);

    add(header);
    add(body);

    add(buttonPanel);
  }

  public void refresh()
  {
    try
    {
      System.out.println("starting");
      Statement statement = gtPort.getConnection().createStatement();

      System.out.println("statement");

      String user = gtPort.userName;
      System.out.println("usered");
      String query = "SELECT * FROM RegularUser WHERE Username = \"" + user + "\" AND Username IS NOT NULL";
      System.out.println(query);
      statement.executeQuery(query);
      System.out.println("executed the personal info");

      ResultSet result = statement.getResultSet();

      while (result.next())
      {
        nameField.setText(result.getString("Name"));
        dobField.setText(result.getDate("DOB").toString());
        genderBox.setSelectedItem(result.getString("gender"));
        addressField.setText(result.getString("Address"));
        permanentAddressField.setText(result.getString("PermanentAddress"));
        contactNumberField.setText(result.getString("ContactNumber"));
        emailAddressField.setText(result.getString("Email"));
      }

      query = "SELECT * FROM Student WHERE Username = \"" + user + "\" AND Username IS NOT NULL";
      statement.executeQuery(query);
      result = statement.getResultSet();

      while (result.next())
      {
        majorBox.setSelectedItem(result.getString("Major"));
        degreeBox.setSelectedItem(result.getString("Degree"));
      }

      query = "SELECT S.Title, R.Grade FROM Section S, Registers R WHERE S.CRN = R.CRN AND R.Username = \"" + gtPort.userName + "\" AND Username NOT IN (SELECT Username FROM TutorFor WHERE Username = \"" + gtPort.userName + "\" AND Title = S.Title" + ")";
      System.out.println(query);
      result = statement.executeQuery(query);
      System.out.println("statement for dropbox executed");

      if (result == null)
      {
        System.out.println("result is null");
      }
      ArrayList added = new ArrayList();
      while (result.next())
      {
        System.out.println(result.getString(1) + "\t" + result.getString(2));
        if ((result.getString(2).equals("A")) || (result.getString(2).equals("B")))
        {
          boolean found = false;

          for (String s : added)
          {
            if (s.equals(result.getString(1))) {
              found = true;
            }
          }
          System.out.println("here");
          if (!found)
          {
            added.add(result.getString(1));
            coursesModel.addElement(result.getString(1));
          }
        }
      }
      selectCoursesBox = new JComboBox(coursesModel);
    }
    catch (Exception e)
    {
      System.out.println("errored in personal info");
      System.out.println(e);
    }
  }

  public void actionPerformed(ActionEvent eve)
  {
    Object source = eve.getSource();

    if (source == backButton)
    {
      gtPort.changeCard("StudentHomepage Panel");
    }

    if (source == refreshButton)
    {
      refresh();
    }

    if (source == submitButton)
    {
      name = nameField.getText();

      dob = dobField.getText();
      System.out.println(dob);
      try {
        java.util.Date dobDate = inputDateFormat.parse(dob);
        dob = dateFormat.format(dobDate);
      }
      catch (Exception c)
      {
        System.out.println("err");
      }

      gender = ((String)genderBox.getSelectedItem());

      address = addressField.getText();

      permanentAddress = permanentAddressField.getText();

      contactNumber = contactNumberField.getText();

      emailAddress = emailAddressField.getText();

      willingToTutor = willingToTutorField.getText();
      selectedCourse = ((String)selectCoursesBox.getSelectedItem());

      major = ((String)majorBox.getSelectedItem());
      degree = ((String)degreeBox.getSelectedItem());
      try
      {
        Statement statement = gtPort.getConnection().createStatement();
        String query = "UPDATE RegularUser SET Email = \"" + emailAddress + 
          "\", Name = \"" + name + 
          "\", Address = \"" + address + 
          "\", DOB = \"" + dob + 
          "\", PermanentAddress = \"" + permanentAddress + 
          "\", Gender = \"" + gender + 
          "\", ContactNumber = \"" + contactNumber + 
          "\" WHERE Username = \"" + gtPort.userName + "\"";
        System.out.println(query);
        statement.executeUpdate(query);

        query = "Update Student SET Major = \"" + major + 
          "\", degree = \"" + degree + 
          "\" WHERE Username = \"" + gtPort.userName + "\"";
        statement.executeUpdate(query);

        if (willingToTutor.equals("Yes"))
        {
          ResultSet result = statement.executeQuery("SELECT Username FROM TutorFor WHERE Username = \"" + gtPort + "\" AND Title = \"" + selectedCourse + "\"");

          if (result != null)
          {
            query = "INSERT INTO AppliedToTutor VALUES (\"" + gtPort.userName + 
              "\", \"" + selectedCourse + "\")";
            System.out.println(query);
            statement.executeUpdate(query);
          }

        }

      }
      catch (Exception e)
      {
        System.out.println("erroerd at update personal info");
        System.out.println(e);
      }

    }

    if (source == addCourse)
    {
      theCourses = (theCourses + (String)selectCoursesBox.getSelectedItem() + " , ");
      coursesField.setText(theCourses);
      revalidate();
    }

    if (source == addButton)
    {
      institution = institutionNameField.getText();
      yearGrad = graduationYearField.getText();
      educationMajor = educationMajorField.getText();
      educationDegree = ((String)educationDegreeBox.getSelectedItem());
      gpa = gPAField.getText();
      try {
        Statement statement = gtPort.getConnection().createStatement();

        String query = "INSERT INTO Education_History VALUES (\"" + gtPort.userName + 
          "\", \"" + institution + 
          "\", \"" + yearGrad + 
          "\", \"" + educationDegree + 
          "\", \"" + educationMajor + 
          "\", \"" + gpa + "\")";

        System.out.println(query);
        statement.executeUpdate(query);

        institutionNameField.setText("");
        graduationYearField.setText("");
        gPAField.setText("");
        educationMajorField.setText("");
      }
      catch (Exception e)
      {
        System.out.println("erroed in education history");
        System.out.println(e);
      }
    }
  }

  public void itemStateChanged(ItemEvent eve)
  {
  }

  private class previousEducation
  {
    public String institutionName;
    public String educationMajor;
    public String educationDegree;
    public String graduationYear;
    public String GPA;

    private previousEducation()
    {
    }
  }
}