import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddCourse extends GUIDesign
{
  JLabel cRNLabel = new JLabel("CRN");
  JLabel titleLabel = new JLabel("Title");
  JLabel courseCodeLabel = new JLabel("Course Code");
  JLabel sectionsLabel = new JLabel("Sections");
  JLabel instructorsLabel = new JLabel("Instructor");
  JLabel termLabel = new JLabel("Term");
  JLabel contactNumberLabel = new JLabel("Contact Number");
  JLabel emailSectionsLabel = new JLabel("Email Sections");
  JLabel departmentLabel = new JLabel("Department");
  JLabel creditsLabel = new JLabel("Credits: 3.0");
  JLabel daysLabel = new JLabel("days");
  JLabel timeLabel = new JLabel("Time");
  JLabel locationLabel = new JLabel("Location");

  JTextField cRNField = new JTextField(20);
  JTextField titleField = new JTextField(20);
  JTextField courseCodeField = new JTextField(20);

  JTextField sectionsField = new JTextField(20);
  JTextField instructorsField = new JTextField(20);
  String[] terms = { "Fall", "Spring", "Summer" };
  JComboBox termBox = new JComboBox(terms);

  JCheckBox[] departmentCheckBoxesBoxs = { new JCheckBox("Aerospace Engineering"), 
    new JCheckBox("Biology"), 
    new JCheckBox("Biomedical Engineering"), 
    new JCheckBox("Computer Science"), 
    new JCheckBox("Electrical & Comp. Engineering") };

  JTextField timeField = new JTextField(20);

  String[] days = { "MWF", "TR", "MTWR" };
  JComboBox daysBox = new JComboBox(terms);
  JTextField timeField2 = new JTextField(20);
  JTextField locationField = new JTextField(20);

  String[] positions = { "lala", "lol", "hehe" };
  JComboBox positionBox = new JComboBox(positions);

  String[] courses = { "courses", "box", "things" };
  JComboBox courseBox = new JComboBox(courses);

  String[] sections = { "section", "combo", "box" };
  JComboBox sectionBox = new JComboBox(sections);

  JPanel cRNPanel = new JPanel();
  JPanel titlePanel = new JPanel();
  JPanel courseCodePanel = new JPanel();
  JPanel sectionsPanel = new JPanel();
  JPanel instructorsPanel = new JPanel();
  JPanel termPanel = new JPanel();
  JPanel departmentsPanel = new JPanel();
  JPanel creditsPanel = new JPanel();
  JPanel daysPanel = new JPanel();
  JPanel timePanel = new JPanel();
  JPanel locationPanel = new JPanel();

  JPanel[] panels = { cRNPanel, titlePanel, courseCodePanel, instructorsPanel, termPanel, departmentsPanel, creditsPanel, daysPanel, timePanel };
  GTPort gPort;
  JButton resetButton = new JButton("reset");
  JButton addButton = new JButton("add");

  public AddCourse(GTPort gport) {
    super("Add Course", 10);
    gport = gport;

    setPreferredSize(new Dimension(400, 600));
    gPort = gport;
    setLayout(new BoxLayout(this, 3));
    header = new JPanel();
    header.setSize(getWidth(), 30);
    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));

    header.setBackground(Color.yellow);
    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);

    cRNPanel.add(cRNLabel);

    cRNPanel.add(cRNField);

    body.add(cRNPanel);

    titlePanel.add(titleLabel);
    titlePanel.add(titleField);
    body.add(titlePanel);

    courseCodePanel.add(courseCodeLabel);
    courseCodePanel.add(courseCodeField);
    body.add(courseCodePanel);

    sectionsPanel.add(sectionsLabel);
    sectionsPanel.add(sectionsField);
    body.add(sectionsPanel);

    instructorsPanel.add(instructorsLabel);
    instructorsPanel.add(instructorsField);
    body.add(instructorsPanel);

    termPanel.add(termLabel);
    termPanel.add(termBox);
    body.add(termPanel);

    departmentsPanel.add(departmentLabel);
    for (JCheckBox jbc : departmentCheckBoxesBoxs)
    {
      departmentsPanel.add(jbc);
    }
    departmentsPanel.add(new JLabel("<html>  <br><br><br>  </html"));
    body.add(departmentsPanel);

    creditsPanel.add(creditsLabel);
    body.add(creditsPanel);

    daysPanel.add(daysLabel);
    daysPanel.add(daysBox);
    body.add(daysPanel);

    timePanel.add(timeLabel);
    timePanel.add(timeField);
    body.add(timePanel);

    locationPanel.add(locationLabel);
    locationPanel.add(locationField);

    body.add(locationPanel);
    buttonPanel.add(resetButton);
    buttonPanel.add(addButton);

    add(header);
    add(body);
    add(buttonPanel);
  }
}