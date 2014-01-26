import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class TutorSearch extends GUIDesign
  implements ActionListener
{
  JLabel courseCodeLabel = new JLabel("Course Code");
  JLabel enterKeyWordLabel = new JLabel("Enter a Keyword");
  JButton searchButton = new JButton("Search");

  JTextField courseCodeField = new JTextField(20);
  JTextField enterKeyWordField = new JTextField(20);

  String[] columns = { "Course Code", "Course Name", "Tutor Name", "Tutor Email Address" };
  int courses;
  Object[][] data;
  JPanel searchPanel = new JPanel();
  JPanel tablePanel = new JPanel();

  JButton backButton = new JButton("Back");
  JTable table;

  public TutorSearch(GTPort gport)
  {
    super("Search for tutors", 3);
    gtPort = gport;
    setLayout(new BoxLayout(this, 3));
    header = new JPanel();
    header.setSize(getWidth(), 30);
    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));

    header.setBackground(color);
    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);

    data = new Object[25][4];
    table = new JTable(data, columns);

    searchPanel.add(courseCodeLabel);
    searchPanel.add(courseCodeField);

    searchPanel.add(enterKeyWordLabel);
    searchPanel.add(enterKeyWordField);

    body.add(searchPanel);
    body.add(table.getTableHeader());
    body.add(table);

    backButton.addActionListener(this);
    searchButton.addActionListener(this);

    buttonPanel.add(backButton);
    buttonPanel.add(searchButton);

    add(header);
    add(body);
    add(buttonPanel);
  }

  public void actionPerformed(ActionEvent eve)
  {
    Object source = eve.getSource();

    if (source == searchButton) {
      try
      {
        Statement statement = gtPort.getConnection().createStatement();

        String code = courseCodeField.getText();
        String word = enterKeyWordField.getText();

        if (word.length() > 0) {
          word = "%" + word + "%";
        }
        String query = "SELECT C.Code , T.Title , T.Username , R.email FROM Code C, TutorFor T, RegularUser R WHERE ( Code = \"" + 
          code + "\" OR C.Title LIKE " + 
          "'" + word + "' AND T.Title = C.Title AND T.Username = R.Username) GROUP BY C.Code";

        System.out.println(query);
        statement.executeQuery(query);
        ResultSet result = statement.getResultSet();

        String[] values = new String[4];

        for (int i = 0; result.next(); i++)
        {
          values[0] = result.getString("Code");
          values[1] = result.getString("Title");
          values[2] = result.getString("Username");
          values[3] = result.getString("Email");

          for (int j = 0; j < 4; j++)
          {
            data[i][j] = values[j];
          }

        }

        table.repaint();
      }
      catch (Exception ex)
      {
        System.out.println("eerrors in tutr search");
        System.out.println(ex);
      }

    }

    if (source == backButton)
    {
      gtPort.changeCard("StudentServices Panel");
    }
  }
}