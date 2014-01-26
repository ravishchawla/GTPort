import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class FacultyHomepage extends HomePage
  implements ItemListener
{
  public FacultyHomepage(GTPort gport)
  {
    super(new String[] { "Faculty Homepage", "Personal Information", "Faculty Services" });

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

    if (source == buttons[1]) {
      gtPort.changeCard("FacultyPersonalInformation Panel");
    }
    else if (source == buttons[2])
      gtPort.changeCard("FacultyServices Panel");
  }
}