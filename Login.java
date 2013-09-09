import java.awt.Dimension;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends GUIDesign
  implements ActionListener
{
  public JLabel userLabel = new JLabel("Username");
  public JLabel passLabel = new JLabel("Password");

  public JTextField userField = new JTextField(20);
  public JPasswordField passField = new JPasswordField(20);

  public JButton submitButton = new JButton("Submit");
  public JButton registerButton = new JButton("Register");

  public JPanel userPanel = new JPanel();
  public JPanel passPanel = new JPanel();
  public JPanel submitPanel = new JPanel();
  public GTPort gPort;
  Statement statement;

  public Login(GTPort gport)
  {
    super("Login Screen", 3);
    gPort = gport;
    setLayout(new BoxLayout(this, 3));
    header = new JPanel();
    header.setPreferredSize(new Dimension(getWidth(), 30));

    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));

    header.setBackground(color);

    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);

    userField.addActionListener(this);
    passField.addActionListener(this);

    userPanel.add(userLabel);
    userPanel.add(userField);

    passPanel.add(passLabel);
    passPanel.add(passField);

    body.add(userPanel);
    body.add(passPanel);

    submitButton.addActionListener(this);
    registerButton.addActionListener(this);
    submitPanel.add(submitButton);
    submitPanel.add(registerButton);
    body.add(submitPanel);

    add(header);

    add(body);
  }

  public void refresh()
  {
    userField.setText("");
    passField.setText("");
  }

  public void actionPerformed(ActionEvent eve)
  {
    Object source = eve.getSource();

    if (source == registerButton) {
      gPort.changeCard("Registration Panel");
    }
    else if (source == submitButton)
    {
      try
      {
        gPort.setUsername(userField.getText());
        char[] passChars = passField.getPassword();
        String passWord = new String(passChars);

        System.out.println("Usaername: " + gPort.userName);
        statement = gPort.getConnection().createStatement();

        String query = "SELECT Username, Type FROM User\tWHERE Username = \"" + gPort.userName + "\" AND Password = \"" + passWord + "\"";

        statement.executeQuery(query);

        ResultSet result = statement.getResultSet();

        while (result.next())
        {
          String user = result.getString("Username");
          String type = result.getString("Type");
          System.out.println("got username:\t" + user);
          if (user.equals(gPort.userName))
          {
            if (type.equals("S")) {
              gPort.changeCard("StudentHomepage Panel");
            }
            else if (type.equals("F")) {
              gPort.changeCard("FacultyHomepage Panel");
            }
            else if (type.equals("A"))
              gPort.changeCard("AdminHomepage Panel");
          }
          else {
            System.out.println("nothing found");
          }
        }

        result.close();
        statement.close();
      }
      catch (Exception e)
      {
        System.out.println(e);

        System.out.println("erroerd");
      }
    }
  }
}