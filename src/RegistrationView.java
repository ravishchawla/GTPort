import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RegistrationView extends GUIDesign
  implements ActionListener
{
  String[] columns = { "Select", "CRN", "Title", "Instructor", "Time", "Days", "Location", "Section", "Code", "Mode of Grading" };
  int courses;
  Object[][] data;
  DefaultTableModel model = new DefaultTableModel();

  JPanel tablePanel = new JPanel();
  JTable table;
  JButton backButton = new JButton("Back");
  JButton refreshButton = new JButton("Refresh");

  public RegistrationView(GTPort gport)
  {
    super("Student Report", 3);

    gtPort = gport;

    setLayout(new BoxLayout(this, 3));
    setPreferredSize(new Dimension(500, 800));

    header = new JPanel();
    header.setSize(getWidth(), 30);
    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));

    header.setBackground(color);
    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);

    data = new Object[15][4];

    model = new DefaultTableModel(data, columns);

    table = new JTable(model);

    JScrollPane scrollPane = new JScrollPane();

    body.add(table.getTableHeader());
    body.add(table);

    backButton.addActionListener(this);
    buttonPanel.add(backButton);

    buttonPanel.add(backButton);
    add(header);
    add(body);
    add(buttonPanel);
  }

  public void refresh()
  {
    ArrayList selectedData = gtPort.selectedData;

    model.setRowCount(0);
    table = new JTable(model);
    model.setRowCount(35);

    for (Object[] objects : selectedData)
    {
      model.insertRow(0, objects);
      for (int i = 0; i < 10; i++) {
        System.out.print(objects[i] + "\t");
      }

      System.out.println();
    }

    table = new JTable(model);
  }

  public void actionPerformed(ActionEvent eve)
  {
    Object source = eve.getSource();

    if (source == backButton)
    {
      gtPort.changeCard("StudentServices Panel");
    }
  }
}