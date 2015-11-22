import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class StudentHomepage extends HomePage
  implements ItemListener
{
  public StudentHomepage(GTPort gport)
  {
    super(new String[] { "Student HomePage", "Personal Information", "Student Services" });

    gtPort = gport;

    for (int i = 1; i < buttons.length; i++)
    {
      buttons[i] = new JRadioButton(strings[i]);
      radioGroup.add(buttons[i]);
      buttons[i].addItemListener(this);
      body.add(buttons[i]);
    }
  }

  public void actionPerformed(ActionEvent eve)
  {
    Object source = eve.getSource();

    if (source == backButton)
      gtPort.changeCard("Login Panel");
  }

  public void itemStateChanged(ItemEvent eve)
  {
    Object source = eve.getSource();

    if (source == buttons[1]) {
      gtPort.changeCard("StudentPersonalInformation Panel");
    }
    else if (source == buttons[2])
      gtPort.changeCard("StudentServices Panel");
  }
}