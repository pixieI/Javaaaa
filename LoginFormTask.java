
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class User {
    private String username;
    private String password;
    private String role;

    User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

class WelcomePage extends Frame {
    WelcomePage(User user) {
        super("Welcome");

        setLayout(new GridLayout(2, 1));

        Label welcome = new Label(
                "Hello, " + user.getUsername(),
                Label.CENTER
        );

        Label roleLabel = new Label(
                "Role: " + user.getRole(),
                Label.CENTER
        );

        add(welcome);
        add(roleLabel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(300, 150);
        setLocationRelativeTo(null);
    }
}

public class LoginFormTask extends Frame {
    private ArrayList<User> users = new ArrayList<>();

    public LoginFormTask() {
        super("Login");
        loadUsers();

        setLayout(new GridBagLayout());
        Panel content = new Panel(new BorderLayout(10, 15));

        Label title = new Label("Welcome back", Label.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        content.add(title, BorderLayout.NORTH);

        Panel form = new Panel(new GridLayout(3, 2, 8, 8));

        form.add(new Label("Username:", Label.RIGHT));
        TextField username = new TextField(18);
        form.add(username);

        form.add(new Label("Password:", Label.RIGHT));
        TextField password = new TextField(18);
        password.setEchoChar('*');
        form.add(password);

        form.add(new Label("Role:", Label.RIGHT));
        Choice role = new Choice();
        role.add("Student");
        role.add("TA");
        role.add("Professor");
        form.add(role);

        content.add(form, BorderLayout.CENTER);

        Panel buttons = new Panel(
                new FlowLayout(FlowLayout.CENTER, 15, 0)
        );

        Button cancel = new Button("Cancel");
        Button login = new Button("Login");

        buttons.add(cancel);
        buttons.add(login);
        content.add(buttons, BorderLayout.SOUTH);

        add(content);

        login.addActionListener(e -> {
            String name = username.getText().trim();
            String pass = password.getText();
            String selectedRole = role.getSelectedItem();

            if (name.isEmpty() || pass.isEmpty()) {
                showMessage("Please fill in all fields.");
                return;
            }

            User user = findUser(name);

            if (user == null) {
                showMessage("Username not found.");
                return;
            }

            if (!user.getPassword().equals(pass)) {
                showMessage("Incorrect password.");
                return;
            }

            if (!user.getRole().equals(selectedRole)) {
                showMessage("Incorrect role.");
                return;
            }

            new WelcomePage(user).setVisible(true);
            dispose();
        });

        cancel.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(420, 340);
        setLocationRelativeTo(null);
    }

    private void loadUsers() {
        users.add(new User("student1", "1234", "Student"));
        users.add(new User("ta1", "abcd", "TA"));
        users.add(new User("prof1", "admin123", "Professor"));
    }

    private User findUser(String name) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(name)) {
                return user;
            }
        }

        return null;
    }

    private void showMessage(String message) {
        Dialog dialog = new Dialog(this, "Message", true);
        dialog.setLayout(new BorderLayout());

        dialog.add(new Label(message, Label.CENTER),
                BorderLayout.CENTER);

        Button ok = new Button("OK");
        ok.addActionListener(e -> dialog.dispose());

        Panel panel = new Panel();
        panel.add(ok);
        dialog.add(panel, BorderLayout.SOUTH);

        dialog.setSize(300, 120);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFormTask().setVisible(true);
    }
}
