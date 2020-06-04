package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.starter;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends StartPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JLabel userLabel;
    private JLabel passLabel;
    private JButton signUpBtn;
    private  JLabel message;


    public LoginPanel(){
        super();
    }

    @Override
     protected void createFields() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginBtn = new JButton("login");
        userLabel = new JLabel("username: ");
        passLabel = new JLabel("password: ");
        signUpBtn = new JButton("Create Account");
        message = new JLabel("");
    }

    @Override
    protected void init(){
        super.init();

        userLabel.setBounds(x1, y0, 80, 25);
        passLabel.setBounds(x1, y0 = y0 + 30, 80, 25);
        usernameField.setBounds(x2, y0 = getYInset(), 150, 25);
        passwordField.setBounds(x2, y0 = y0 + 30, 150, 25);
        loginBtn.setBounds(x1, y0 = y0 + 30, 230, 25);
        signUpBtn.setBounds(x1, y0 = y0 + 30, 230, 25);

        userLabel.setForeground(Color.white);
        passLabel.setForeground(Color.white);
        message.setBounds(x1+20, y0 +30 , 230 , 25);

        add(userLabel);
        add(usernameField);
        add(passLabel);
        add(passwordField);
        add(loginBtn);
        signUpBtn.setBorderPainted(false);
        signUpBtn.setForeground(Color.WHITE);
        signUpBtn.setBackground(new Color(114, 59, 38));
        add(signUpBtn);
        add(message);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                tryToLogin(username , password);
            }
        });

        signUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Administer.getInstance().runSignUp();
            }
        });
    }

    private void tryToLogin(String username , String password){
        try {
            Administer.getInstance().login(username , password);
//            message.setText("hello "+username.toUpperCase() );
//            message.setForeground(Color.GREEN);
            Administer.getInstance().runMenu();

        } catch (Exception e) {
            e.printStackTrace();
            message.setText("username or password incorrect");
            message.setForeground(Color.RED);
        }
    }

}
