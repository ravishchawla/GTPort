import java.awt.Color;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class RegistrationComplete extends GUIDesign
{
  JLabel stringLabel;
  String[] columns = { "Course Code", "Title", "Section", "Mode of Grading" };
  int courses;
  Object[][] data;
  JTable table;
  JPanel termPanel = new JPanel();
  JPanel departmentPanel = new JPanel();

  JButton homeButton = new JButton("Go to Homepage");

  public RegistrationComplete()
  {
    super("Registration Complete", 3);
    setLayout(new BoxLayout(this, 3));
    header = new JPanel();
    header.setSize(getWidth(), 30);
    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));

    header.setBackground(Color.yellow);
    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);

    stringLabel = new JLabel("You have registered for the following courses");

    data = new Object[10][4];
    table = new JTable(data, columns);

    termPanel.add(stringLabel);

    body.add(termPanel);
    body.add(departmentPanel);

    body.add(table.getTableHeader());
    body.add(table);

    buttonPanel.add(homeButton);

    add(header);
    add(body);
    add(buttonPanel);
  }
}