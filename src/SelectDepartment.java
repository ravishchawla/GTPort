import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectDepartment extends GUIDesign
  implements ActionListener
{
  JLabel termLabel;
  String term = null;
  JLabel departmentLabel = new JLabel("Department");
  String[] departments = { "Breaking", "Bad" };
  JComboBox departmentBox = new JComboBox(departments);

  JPanel departmentPanel = new JPanel();

  JButton backButton = new JButton("Back");
  JButton nextButton = new JButton("Next");
  String department;

  public SelectDepartment(GTPort gport)
  {
    super("Select Departments", 3);
    gtPort = gport;

    setLayout(new BoxLayout(this, 3));
    header = new JPanel();
    header.setSize(getWidth(), 30);
    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));

    header.setBackground(color);
    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);

    departmentPanel.add(departmentLabel);
    departmentPanel.add(departmentBox);
    body.add(departmentPanel);

    backButton.addActionListener(this);
    nextButton.addActionListener(this);

    buttonPanel.add(backButton);
    buttonPanel.add(nextButton);

    add(header);
    add(body);
    add(buttonPanel);
  }

  public void actionPerformed(ActionEvent eve)
  {
    Object source = eve.getSource();

    if (source == nextButton)
    {
      try
      {
        gtPort.selectedDepartment = ((String)departmentBox.getSelectedItem());

        gtPort.changeCard("CourseSelection Panel");
      }
      catch (Exception ex)
      {
        System.out.println("errored at select dept");
      }

    }

    if (source == backButton)
    {
      gtPort.changeCard("StudentServices Panel");
    }
  }
}