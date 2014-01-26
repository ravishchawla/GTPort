import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public abstract class HomePage extends GUIDesign
  implements ActionListener
{
  JRadioButton[] buttons;
  ButtonGroup radioGroup;
  JButton logout = new JButton("Logout");
  HomePage hPage;
  String[] strings;
  JButton backButton = new JButton("Back");

  public HomePage(String[] strings)
  {
    super(strings[0], 3);
    this.strings = strings;
    buttons = new JRadioButton[strings.length];

    setLayout(new BoxLayout(this, 3));
    header = new JPanel();
    header.setPreferredSize(new Dimension(getWidth(), 30));
    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));
    radioGroup = new ButtonGroup();
    header.setBackground(color);
    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);

    logout.addActionListener(this);

    buttonPanel.setLayout(new GridLayout(1, 2));

    JPanel submitPanel = new JPanel();

    submitPanel.add(logout);
    submitPanel.add(backButton);
    backButton.addActionListener(this);
    buttonPanel.add(submitPanel);
    add(header);
    add(body);
    add(buttonPanel);
  }

  public void refresh()
  {
    radioGroup.clearSelection();
  }

  public void actionPerformed(ActionEvent eve)
  {
    Object source = eve.getSource();

    if (source == logout)
    {
      gtPort.changeCard("Login Panel");
    }
  }

  public void itemListener()
  {
  }
}