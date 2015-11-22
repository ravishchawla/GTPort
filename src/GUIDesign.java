import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class GUIDesign extends JPanel
{
  JPanel header;
  JPanel body;
  JPanel buttonPanel;
  GTPort gtPort;
  JLabel title = new JLabel();

  Color color = new Color(37, 114, 235);

  public GUIDesign(String title, int entries)
  {
    setLayout(new GridLayout(3, 1));
    header = new JPanel();

    this.title = new JLabel(title);
    this.title.setFont(new Font("Helvetica", 1, 10));
    header.add(this.title);

    body = new JPanel();
    body.setLayout(new GridLayout(entries, 1));

    buttonPanel = new JPanel();
  }

  public void refresh()
  {
  }
}