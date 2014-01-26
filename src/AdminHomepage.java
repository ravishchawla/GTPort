import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class AdminHomepage extends HomePage
  implements ItemListener
{
  public AdminHomepage(GTPort gport)
  {
    super(new String[] { "Administrator Homepage", "View Administrative Reports", "Add New Course (Coming Soon)" });

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
      gtPort.changeCard("AdminReport Panel");
    }

    if (source == buttons[2])
      gtPort.changeCard(" Panel");
  }
}