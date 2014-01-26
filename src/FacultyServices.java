import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.PrintStream;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class FacultyServices extends HomePage
  implements ItemListener
{
  public FacultyServices(GTPort gport)
  {
    super(new String[] { "Faculty Services", "Update personal information", "Assign Tutors", "View student Performance" });

    backButton.setText("back");
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
      gtPort.changeCard("FacultyPersonalInformation Panel");
    }
    else if (source == buttons[2]) {
      gtPort.changeCard("TutorAssignment Panel");
    }
    else if (source == buttons[3]) {
      gtPort.changeCard("FacultyReport Panel");
    }
    else if (source == buttons[4]) {
      gtPort.changeCard("Tutor Logbook");
    }
    else if (source == buttons[5])
      gtPort.changeCard("FacultyReport Panel");
  }
}