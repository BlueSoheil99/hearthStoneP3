package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.starter;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPanel extends StartPanel {


    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton signUpBtn;
    private JButton loginBtn;
    private JLabel userLabel;
    private JLabel pass1Label;
    private JLabel pass2Label;
    private JLabel message;

    public SignUpPanel() {
        super();
    }

    @Override
    protected void createFields() {
        usernameField = new JTextField(20);
        passwordField1 = new JPasswordField(20);
        passwordField2 = new JPasswordField(20);
        signUpBtn = new JButton("Sign Up");
        loginBtn = new JButton("login");
        userLabel = new JLabel("username: ");
        pass1Label = new JLabel("password: ");
        pass2Label = new JLabel("password: ");
        message = new JLabel("");
    }

    @Override
    protected void init() {
        super.init();

        userLabel.setBounds(x1, y0, 80, 25);
        pass1Label.setBounds(x1, y0 = y0 + 30, 80, 25);
        pass2Label.setBounds(x1, y0 = y0 + 30, 80, 25);
        usernameField.setBounds(x2, y0 = getYInset(), 150, 25);
        passwordField1.setBounds(x2, y0 = y0 + 30, 150, 25);
        passwordField2.setBounds(x2, y0 = y0 + 30, 150, 25);
        signUpBtn.setBounds(x1, y0 = y0 + 30, 230, 25);
        loginBtn.setBounds(x1, y0 = y0 + 30, 230, 25);

        userLabel.setForeground(Color.white);
        pass1Label.setForeground(Color.white);
        pass2Label.setForeground(Color.white);

        add(userLabel);
//        usernameField.setBackground(new Color(0,0,0,70));
//        usernameField.setForeground(Color.white);
        add(usernameField);
        add(pass1Label);
        add(passwordField1);
        add(pass2Label);
        add(passwordField2);
        add(signUpBtn);
        loginBtn.setForeground(Color.white);
        loginBtn.setBackground(new Color(114, 59, 38));
        loginBtn.setBorderPainted(false);
        add(loginBtn);
        add(message);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Administer.getInstance().runLogin();
            }
        });

        signUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String pass1 = passwordField1.getText();
                String pass2 = passwordField2.getText();
                tryToSignUp(username, pass1, pass2);
            }
        });

    }

    private void tryToSignUp(String username, String pass1, String pass2) {
        if (username.length() > 5 && pass1.length() > 5 && pass2.length() > 5) {
            if (!pass1.equals(pass2)) {
                message.setText("Your passwords don't match. try again");
                message.setForeground(Color.RED);
                message.setBounds(x1 + 5, y0 + 25, 230, 25);
            } else {
                try {
                    Administer.getInstance().signUp(username, pass1);
                    message.setText("Your Account has been made successfully");
                    message.setForeground(Color.GREEN);
                    message.setBounds(x1 - 5, y0 + 25, 300, 25);
                } catch (Exception ex) {
                    message.setText(ex.getMessage());
                    message.setForeground(Color.RED);
                    message.setBounds(x1 + 50, y0 + 25, 300, 25);
                }
            }
        } else {
            message.setText("You must enter at least 6 characters in each field");
            message.setForeground(Color.RED);
            message.setBounds(x1 - 25, y0 + 25, 300, 25);
        }
    }
}





