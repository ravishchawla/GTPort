import java.awt.Color;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class GradeAssignment extends GUIDesign
{
  private String classString;
  private JLabel rosterForLabel = new JLabel("Roster For: ");

  String[] columns = { "Student ID", "Name", "Grade" };
  int courses;
  Object[][] data;
  JTable table;
  JPanel termPanel = new JPanel();
  JPanel tablePanel = new JPanel();

  public GradeAssignment(GTPort gport)
  {
    super("Grade Assignment", 3);

    gtPort = gport;
    setLayout(new BoxLayout(this, 3));
    header = new JPanel();
    header.setSize(getWidth(), 30);
    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));

    header.setBackground(Color.yellow);
    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);

    rosterForLabel.setText("Roster For: " + classString);
    termPanel.add(rosterForLabel);
    body.add(termPanel);

    data = new Object[10][3];
    table = new JTable(data, columns);
    body.add(table.getTableHeader());
    body.add(table);
    table.setEditingColumn(2);

    add(header);
    add(body);
    add(buttonPanel);
  }
}