import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GTPort extends JFrame
{
  public String userName;
  public String userType;
  public String selectedDepartment;
  public Connector connect;
  private CardLayout cLayout = new CardLayout();
  private JPanel card = new JPanel();

  public ArrayList<Object[]> selectedData = new ArrayList();

  public static void main(String[] args)
  {
    GTPort gprt = new GTPort();
  }

  public GTPort()
  {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(3);

    connect = new Connector();

    cLayout = new CardLayout();
    card = new JPanel();
    card.setLayout(cLayout);

    Login loginPanel = new Login(this);
    Registration registrationPanel = new Registration(this);

    AdminHomepage adminHomepagePanel = new AdminHomepage(this);

    StudentHomepage studentHomepagePanel = new StudentHomepage(this);
    StudentPersonalInformation studentPersonalInformationPanel = new StudentPersonalInformation(this);
    StudentServices studentServicesPanel = new StudentServices(this);

    FacultyHomepage facultyHomepagePanel = new FacultyHomepage(this);
    FacultyPersonalInformation facultyPersonalInformationPanel = new FacultyPersonalInformation(this);
    FacultyServices facultyServicesPanel = new FacultyServices(this);
    GradeAssignment gradeAssignmentPanel = new GradeAssignment(this);
    TutorSearch tutorSearchPanel = new TutorSearch(this);
    TutorAssignment tutorAssignmentPanel = new TutorAssignment(this);
    TutorLogbook tutorLogbookPanel = new TutorLogbook(this);

    RegistrationView registrationViewPanel = new RegistrationView(this);

    StudentReport studentReportPanel = new StudentReport(this);
    CourseSelection courseSelectionPanel = new CourseSelection(this);

    FacultyReport facultyReportPanel = new FacultyReport(this);

    SelectDepartment selectDepartmentPanel = new SelectDepartment(this);

    AdminReport adminReport = new AdminReport(this);

    card.add("Login Panel", loginPanel);
    card.add("Registration Panel", registrationPanel);
    card.add("AdminHomepage Panel", adminHomepagePanel);
    card.add("FacultyHomepage Panel", facultyHomepagePanel);
    card.add("StudentHomepage Panel", studentHomepagePanel);

    card.add("StudentServices Panel", studentServicesPanel);
    card.add("StudentPersonalInformation Panel", studentPersonalInformationPanel);

    card.add("SelectDepartment Panel", selectDepartmentPanel);

    card.add("TutorSearch Panel", tutorSearchPanel);
    card.add("TutorAssignment Panel", tutorAssignmentPanel);
    card.add("TutorLogbook Panel", tutorLogbookPanel);
    card.add("FacultyPersonalInformation Panel", facultyPersonalInformationPanel);
    card.add("FacultyServices Panel", facultyServicesPanel);

    card.add("CourseSelection Panel", courseSelectionPanel);
    card.add("RegistrationView Panel", registrationViewPanel);
    card.add("GradeAssignment Panel", gradeAssignmentPanel);
    card.add("AdminReport Panel", adminReport);
    card.add("StudentReport Panel", studentReportPanel);
    card.add("FacultyReport Panel", facultyReportPanel);
    cLayout.show(card, "Login Panel");

    frame.getContentPane().add(card);
    frame.setResizable(false);
    frame.pack();
    frame.setVisible(true);
  }

  public void setUsername(String name)
  {
    userName = name;
    System.out.println("Username: " + userName);
  }

  public void setUsertype(String type) {
    userType = type;
  }

  public void changeCard(String cCard)
  {
    cLayout.show(card, cCard);

    JPanel panel = getCurrentCard();
    System.out.println("panel\t " + panel.toString());
    if (panel.getClass().getName().equals("GuiDesign"));
    GUIDesign gPanel = (GUIDesign)panel;
    gPanel.refresh();
  }

  public JPanel getCurrentCard()
  {
    JPanel rightCard = null;

    for (Component comp : card.getComponents()) {
      if (comp.isVisible()) {
        rightCard = (JPanel)comp;
        System.out.println("card\t" + rightCard.getName());
      }
    }
    System.out.println();

    return rightCard;
  }

  public Connection getConnection()
  {
    return connect.getConnection();
  }
}