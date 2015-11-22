import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FacultyPersonalInformation extends GUIDesign
  implements ActionListener
{
  JLabel nameLabel = new JLabel("Name");
  JLabel dobLabel = new JLabel("Date of Birth");
  JLabel genderLabel = new JLabel("Gender");
  JLabel addressLabel = new JLabel("Address");
  JLabel permanentAddressLabel = new JLabel("Permanent Address");
  JLabel contactNumberLabel = new JLabel("Contact Number");
  JLabel emailAddressLabel = new JLabel("Email Address");
  JLabel departmentLabel = new JLabel("Department");
  JLabel positionLabel = new JLabel("Position");
  JLabel courseLabel = new JLabel("Course");
  JLabel sectionLabel = new JLabel("Section");
  JLabel researchLabel = new JLabel("Research Intersts");

  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-ddhh:mm:ss.SSS-Z");

  java.util.Date time = new java.util.Date();

  JTextField nameField = new JTextField(20);
  JTextField dobField = new JTextField(20);

  String[] sexes = { "M", "F" };
  JComboBox genderBox = new JComboBox(sexes);

  JTextField addressField = new JTextField(20);
  JTextField permanentAddressField = new JTextField(20);
  JTextField contactNumberField = new JTextField(20);
  JTextField emailAddressField = new JTextField(20);

  String[] departments = { "Aerospace Engineering", "Biology", "Biomedical Engineering", "Computer Science", "Electrical and Computer Engineering" };
  JComboBox departmentBox = new JComboBox(departments);

  String[] positions = { "Professor", "Assistant", "Associate" };
  JComboBox positionBox = new JComboBox(positions);

  DefaultComboBoxModel coursesModel = new DefaultComboBoxModel();

  String[] courses = { "courses", "box", "things" };
  JComboBox courseBox = new JComboBox(courses);

  String[] sections = { "section", "combo", "box" };
  JComboBox sectionBox = new JComboBox(sections);
  DefaultComboBoxModel sectionsModel = new DefaultComboBoxModel();
  JTextField researchField = new JTextField(20);
  JButton addResearchButton = new JButton("+");
  JLabel researchesLabel = new JLabel("");

  JPanel namePanel = new JPanel();
  JPanel dobPanel = new JPanel();
  JPanel genderPanel = new JPanel();
  JPanel addressPanel = new JPanel();
  JPanel permanentAddressPanel = new JPanel();
  JPanel contactNumberPanel = new JPanel();
  JPanel emailAddressPanel = new JPanel();
  JPanel departmentPanel = new JPanel();
  JPanel positionPanel = new JPanel();
  JPanel coursePanel = new JPanel();
  JPanel sectionPanel = new JPanel();
  JPanel researchPanel = new JPanel();
  JPanel researchListPanel = new JPanel();
  GTPort gPort;
  JButton submitButton = new JButton("Submit");
  JButton backButton = new JButton("Back");
  JButton refreshButton = new JButton("Refresh");
  String name;
  String dob;
  String gender;
  String address;
  String permanentAddress;
  String contactNumber;
  String emailAddress;
  String department;
  String position;
  String course;
  String section;
  String researches = "";

  public FacultyPersonalInformation(GTPort gport)
  {
    super("FacultyPersonalInformation", 3);
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

    departmentPanel.add(departmentLabel);

    departmentPanel.add(departmentBox);
    body.add(departmentPanel);

    positionPanel.add(positionLabel);
    positionPanel.add(positionBox);
    body.add(positionPanel);

    courseBox.setModel(coursesModel);

    coursePanel.add(courseLabel);
    coursePanel.add(courseBox);
    body.add(coursePanel);

    sectionBox.setModel(sectionsModel);
    sectionPanel.add(sectionLabel);
    sectionPanel.add(sectionBox);
    body.add(sectionPanel);

    addResearchButton.addActionListener(this);
    researchPanel.add(researchLabel);
    researchPanel.add(researchField);
    researchPanel.add(addResearchButton);

    body.add(researchPanel);
    body.add(researchesLabel);

    backButton.addActionListener(this);

    refreshButton.addActionListener(this);
    submitButton.addActionListener(this);
    buttonPanel.add(refreshButton);
    buttonPanel.add(backButton);
    buttonPanel.add(submitButton);

    add(header);
    add(body);
    add(buttonPanel);

    gtPort = gport;
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

      query = "SELECT * FROM Faculty WHERE Username = \"" + user + "\" AND Username IS NOT NULL";
      statement.executeQuery(query);
      result = statement.getResultSet();
      System.out.println("executed one for dept id");
      int deptID = 0;

      result.next();
      deptID = Integer.parseInt(result.getString("Dept_Id"));
      position = result.getString("Position");

      query = "SELECT * FROM Department WHERE Dept_Id = \"" + Integer.toString(deptID) + "\" AND Dept_Id IS NOT NULL";
      statement.executeQuery(query);
      result = statement.getResultSet();
      System.out.println("executed one for depatment");
      result.next();
      department = result.getString("Name");

      statement.executeQuery("SELECT * FROM FacultyResearchInterest WHERE Username = \"" + gtPort.userName + "\"");
      result = statement.getResultSet();
      researchesLabel.setText("");
      while (result.next())
      {
        researchesLabel.setText(researchesLabel.getText() + " , " + result.getString(2));
      }

      departmentBox.setSelectedItem(department);
      positionBox.setSelectedItem(position);

      statement.executeQuery("SELECT T.Title, T.Letter FROM Section T WHERE T.Instructor = \"" + gtPort.userName + "\"");
      result = statement.getResultSet();

      while (result.next())
      {
        coursesModel.addElement(result.getString(1));
        sectionsModel.addElement(result.getString(2));

        System.out.println(result.getString(1));
        System.out.println(result.getString(2));
      }

      courseBox = new JComboBox(coursesModel);
      sectionBox = new JComboBox(sectionsModel);
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
      gtPort.changeCard("FacultyHomepage Panel");
    }

    if (source == refreshButton)
    {
      refresh();
    }

    if (source == addResearchButton) {
      try
      {
        String research = researchField.getText();
        Statement statement = gtPort.getConnection().createStatement();

        System.out.println(statement.executeUpdate("INSERT INTO FacultyResearchInterest VALUES (\"" + gtPort.userName + "\", \"" + research + "\")"));
      }
      catch (Exception exe)
      {
        System.out.println("erored in factuly research");
        System.out.println(exe);
      }
    }

    if (source == submitButton) {
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
      try
      {
        Statement statement = gtPort.getConnection().createStatement();
        String query = "UPDATE RegularUser SET Email = \"" + emailAddress + 
          "\", Name = \"" + name + 
          "\", DOB = \"" + dob + 
          "\", Address = \"" + address + 
          "\", PermanentAddress = \"" + permanentAddress + 
          "\", Gender = \"" + gender + 
          "\", ContactNumber = \"" + contactNumber + 
          "\" WHERE Username = \"" + gtPort.userName + "\"";
        System.out.println(query);
        statement.executeUpdate(query);
      }
      catch (Exception e)
      {
        System.out.println("erroed in faculty personal info update");
        System.out.println(e);
      }
    }
  }
}