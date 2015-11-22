import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.PrintStream;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class StudentServices extends HomePage
  implements ItemListener
{
  public StudentServices(GTPort gport)
  {
    super(new String[] { "Student Services", "Register for courses", "Update personal information", "Find tutors", "Tutor logbook", "View grading Pattern" });
    gtPort = gport;

    for (int i = 1; i < buttons.length; i++)
    {
      buttons[i] = new JRadioButton(strings[i]);
      radioGroup.add(buttons[i]);
      buttons[i].addItemListener(this);
      body.add(buttons[i]);
    }
  }

  public void itemStateChanged(ItemEvent eve)
  {
    Object source = eve.getSource();

    System.out.println("item listenr fired");

    if (source == buttons[1]) {
      gtPort.changeCard("CourseSelection Panel");
    }
    else if (source == buttons[2]) {
      gtPort.changeCard("StudentPersonalInformation Panel");
    }
    else if (source == buttons[3]) {
      gtPort.changeCard("TutorSearch Panel");
    }
    else if (source == buttons[4]) {
      gtPort.changeCard("TutorLogbook Panel");
    }
    else if (source == buttons[5])
      gtPort.changeCard("StudentReport Panel");
  }

  public void actionPerformed(ActionEvent eve)
  {
    Object source = eve.getSource();

    if (source == backButton)
    {
      gtPort.changeCard("StudentHomepage Panel");
    }

    if (source == logout)
      gtPort.changeCard("Login Panel");
  }
}