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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Registration extends GUIDesign
  implements ActionListener
{
  JLabel title = new JLabel("Registration");

  JLabel userLabel = new JLabel("Username");
  JLabel passLabel = new JLabel("Password");
  JLabel confirmLabel = new JLabel("Confirm Password");
  JLabel userTypeLabel = new JLabel("Type of account");

  JTextField userField = new JTextField(20);
  JPasswordField passField = new JPasswordField(20);
  JPasswordField confirmField = new JPasswordField(20);

  JPanel userPanel = new JPanel();
  JPanel passPanel = new JPanel();
  JPanel confirmPanel = new JPanel();
  JPanel userTypePanel = new JPanel();
  JPanel submitPanel = new JPanel();

  String[] accounts = { "Student", "Faculty", "Admin" };
  JComboBox userTypeBox = new JComboBox(accounts);

  JButton submitButton = new JButton("Submit");
  JButton backButton = new JButton("Back");

  public Registration(GTPort gport)
  {
    super("Registratin Screen", 3);
    gtPort = gport;
    setLayout(new BoxLayout(this, 3));
    header = new JPanel();
    header.setPreferredSize(new Dimension(getWidth(), 30));

    body = new JPanel();
    body.setLayout(new BoxLayout(body, 1));

    header.setBackground(color);
    title.setFont(new Font("Helvetica", 1, 20));
    header.add(title);

    userPanel.add(userLabel);
    userPanel.add(userField);
    body.add(userPanel);

    passPanel.add(passLabel);
    passPanel.add(passField);
    body.add(passPanel);

    confirmPanel.add(confirmLabel);
    confirmPanel.add(confirmField);
    body.add(confirmPanel);

    userTypePanel.add(userTypeLabel);
    userTypePanel.add(userTypeBox);
    body.add(userTypePanel);

    backButton.addActionListener(this);
    submitButton.addActionListener(this);
    submitPanel.add(backButton);
    submitPanel.add(submitButton);

    add(header);
    add(body);

    add(submitPanel);
  }

  public void actionPerformed(ActionEvent eve)
  {
    Object source = eve.getSource();

    if (source == backButton) {
      gtPort.changeCard("Login Panel");
    }
    if (source == submitButton)
    {
      String pass = new String(passField.getPassword());
      if (!pass.equals(new String(confirmField.getPassword()))) {
        JOptionPane.showMessageDialog(gtPort, new String("Password fields do not match"));
      }
      else {
        String user = userField.getText();
        String type = (String)userTypeBox.getSelectedItem();
        type = Character.toString(type.charAt(0));
        try {
          Statement statement = gtPort.getConnection().createStatement();

          String query = "SELECT Count(Username) FROM User WHERE Username = \"" + user + "\" AND Username IS NOT NULL";
          System.out.println(query);
          statement.executeQuery(query);
          System.out.println("executed");

          ResultSet result = statement.getResultSet();

          int total = 0;

          while (result.next())
          {
            total = Integer.parseInt(result.getString("Count(Username)"));
            System.out.println(total);
          }

          if (total == 0)
          {
            query = "INSERT INTO User VALUES (\"" + user + "\", \"" + pass + "\", \"" + type + "\")";
            System.out.println(query);
            statement.executeUpdate(query);

            query = "INSERT INTO RegularUser (Username) VALUES (\"" + user + "\")";
            System.out.println(query);
            statement.executeUpdate(query);

            query = "INSERT INTO Student (Username) VALUES (\"" + user + "\")";
            System.out.println(query);
            statement.executeUpdate(query);

            gtPort.changeCard("LoginScreen panel");
          }
          else
          {
            JOptionPane.showMessageDialog(this, "Username alreaddy exists");
          }
          System.out.println("got out of the if block");
        }
        catch (Exception ex)
        {
          System.out.println("errored");
          System.out.println(ex);
        }
      }
    }
  }
}