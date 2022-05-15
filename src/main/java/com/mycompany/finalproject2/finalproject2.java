package com.mycompany.finalproject2;

import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import javax.swing.border.LineBorder;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author sanidhyasitaula
 */
public class finalproject2 {

    public static BufferedImage output;

    public static JFrame jf;
    public static JFrame jf_draw;

    public static JPanel jp_register = new JPanel();
    public static JTextField username_textfield = new JTextField();
    public static JTextField name_textfield = new JTextField();
    public static JTextField password_textfield = new JTextField();

    public static JTextField login_username_textfield = new JTextField();
    public static JTextField login_password_textfield = new JTextField();

    public static User current_user;

    public static JTextArea chat_field = new JTextArea(); 
    public static JTextArea my_message_field = new JTextArea();
    
    public static DrawPanel jp;

    public static Connection conn = null;

    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    static JFrame getLoadingScreen() {
        jf.dispose();
        jf.setVisible(false);
        jf = new JFrame("Please Wait");
        jf.add(new JLabel("Please wait while your drawing is getting posted..."));
        jf.setSize(300, 200);
        jf.setVisible(true);
        return jf;
    }

    static void waitToReload() {

        Thread newThread = new Thread();
        new Thread() {
            @Override
            public void run() {
                try {
                    newThread.sleep(3000);
                    jf = getLoadingScreen();
                } catch (Exception e) {

                }
            }
        }.start();

        jf.dispose();
        jf = displayHomePageScreen();

    }

    static JFrame displayUserProfile(String thisUsername) {
        User user;
        if (thisUsername == current_user.username) {
            user = current_user;
        } else {
            user = DatabaseFunctions.getUserDetails(thisUsername);
        }
        JFrame jf_profile = new JFrame(user.username + "'s Profile");
        jf_profile = DisplayLayouts.getUserProfileLayout(jf_profile, user);
        return jf;
    }

    static JScrollPane getSpecificUserPosts(String thisUsername) {

        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

        ArrayList<Post> allPosts = DatabaseFunctions.getPosts();
      
        Collections.sort(allPosts, new CustomComparator());

        for (Post p : allPosts) {
            if (p.username.equals(thisUsername)) {
                JPanel sub_jp = DisplayLayouts.getPostLayout(p, "Profile");
                jp.add(sub_jp);
                jp.add(Box.createVerticalStrut(20));   // n pixels of vertical space.
            }
        }

        JScrollPane scrollFrame = new JScrollPane(jp);

        jp.setAutoscrolls(true);
        return scrollFrame;
    }

    static JScrollPane getAllPosts() {

        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

        ArrayList<Post> allPosts = DatabaseFunctions.getPosts();

        Collections.sort(allPosts, new CustomComparator());

        for (Post p : allPosts) {
            JPanel sub_jp = DisplayLayouts.getPostLayout(p, "Homepage");
            jp.add(sub_jp);
            jp.add(Box.createVerticalStrut(20));   // n pixels of vertical space.
        }

        JScrollPane scrollFrame = new JScrollPane(jp);

        jp.setAutoscrolls(true);
        return scrollFrame;

    }

    static JFrame displayHomePageScreen() {
        JFrame jf = new JFrame("Home");

        jf = DisplayLayouts.getHomePageLayout(jf);
        jf.setSize(1000, 800);

        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        return jf;
    }

    static JFrame createRegisteredSuccessfullyScreen() {
        JFrame jf = new JFrame("Success");
        jf.setSize(300, 300);
        JPanel jp = new JPanel();
        JLabel message = new JLabel("Registered Successfully. Please log in now.");
        JButton goToLoginButton = new JButton("Go to Log In Screen");
        goToLoginButton.addActionListener(new RouteToLoginButtonListener());
        jp.add(message);
        jp.add(goToLoginButton);
        jf.add(jp);
        jf.setVisible(true);
        return jf;
    }

    static JFrame displayErrorScreen(String message) {
        JFrame jf = new JFrame("Error");
        jf.setSize(500, 300);
        JPanel jp = new JPanel();
        JLabel title = new JLabel("There was an error processing your request.");
        JLabel sub_message = new JLabel(message);

        JButton goToHomeButton = new JButton("Go to Main Screen");
        goToHomeButton.addActionListener(new RouteToHomeButtonListener());
        jp.add(title);
        jp.add(sub_message);
        jp.add(goToHomeButton);

        jf.add(jp);

        jf.setVisible(true);
        return jf;
    }

    static JFrame displayDrawScreen() {

        jf_draw = new JFrame("Create a New Drawing");
        JPanel outer_panel = DisplayLayouts.getDrawScreenLayout();
        jf_draw.add(outer_panel);
        jf_draw.setLocationRelativeTo(null);

        jf_draw.setVisible(true);
        return jf_draw;
    }

    static void connectToDatabase() {
        try {
            Class.forName("org.h2.Driver").newInstance();
            String url = "jdbc:h2:~/test;AUTO_SERVER=TRUE";
            String username = "";
            String password = "";
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static JFrame displayRegisterScreen() {
        jf = new JFrame("Register");
        jp_register.setLayout(new BoxLayout(jp_register, BoxLayout.Y_AXIS));

        JLabel name_label = new JLabel("Name:");
        JLabel username_label = new JLabel("Username:");
        JLabel password_label = new JLabel("Password:");

        JButton register_button = new JButton("Register");

        jp_register.add(Box.createVerticalStrut(20));   // n pixels of vertical space.

        jp_register.add(name_label);
        jp_register.add(Box.createVerticalStrut(5));   // n pixels of vertical space.
        jp_register.add(name_textfield);
        jp_register.add(Box.createVerticalStrut(5));   // n pixels of vertical space.
        jp_register.add(username_label);
        jp_register.add(Box.createVerticalStrut(5));   // n pixels of vertical space.
        jp_register.add(username_textfield);
        jp_register.add(Box.createVerticalStrut(5));   // n pixels of vertical space.
        jp_register.add(password_label);
        jp_register.add(Box.createVerticalStrut(5));   // n pixels of vertical space.
        jp_register.add(password_textfield);
        jp_register.add(Box.createVerticalStrut(5));   // n pixels of vertical space.

        register_button.setPreferredSize(new Dimension(40, 40));
        register_button.setAlignmentX(Component.CENTER_ALIGNMENT);

        register_button.addActionListener(new RegisterButtonListener());
        jp_register.add(register_button);

        jf.add(jp_register);

        jf.setSize(450, 250);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.setLocationRelativeTo(null);

        jf.setVisible(true);

        return jf;
    }

    static JFrame displayLoginScreen() {

        jf = new JFrame("Log In");
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

        JLabel username_label = new JLabel("Username:");
        JLabel password_label = new JLabel("Password:");

        JButton login_button = new JButton("Login");
        login_button.addActionListener(new LoginButtonListener());

        jp.add(Box.createVerticalStrut(20));   // n pixels of vertical space.

        jp.add(username_label);
        jp.add(Box.createVerticalStrut(10));   // n pixels of vertical space.

        jp.add(login_username_textfield);
        jp.add(Box.createVerticalStrut(10));   // n pixels of vertical space.

        jp.add(password_label);
        jp.add(Box.createVerticalStrut(10));   // n pixels of vertical space.

        jp.add(login_password_textfield);
        jp.add(Box.createVerticalStrut(30));   // n pixels of vertical space.

        login_button.setPreferredSize(new Dimension(40, 40));

        login_button.setAlignmentX(Component.CENTER_ALIGNMENT);

        jp.add(login_button);

        jf.add(jp);

        jf.setSize(450, 250);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.setLocationRelativeTo(null);

        jf.setVisible(true);

        return jf;
    }

    static JFrame displayWelcomeScreen() {
        jf = new JFrame("Start Screen!");
        JPanel jp = new JPanel();

        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

        JLabel welcome_message = new JLabel("Welcome!");

        JLabel welcome_submessage = new JLabel("Please choose an action.");

        JButton login = new JButton("Log In");
        login.addActionListener(new ButtonListener());
        JButton register = new JButton("Register");
        register.addActionListener(new ButtonListener());

        login.setPreferredSize(new Dimension(40, 40));
        register.setPreferredSize(new Dimension(40, 40));

        // Center Everything
        welcome_message.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcome_submessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        register.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add to jpanel
        jp.add(Box.createVerticalStrut(20));   // n pixels of vertical space.

        jp.add(welcome_message);
        jp.add(welcome_submessage);
        jp.add(Box.createVerticalStrut(100));   // n pixels of vertical space.

        jp.add(login);
        jp.add(register);

        jf.add(jp);
        jf.setSize(350, 350);
        jf.setLocationRelativeTo(null);

        jf.setVisible(true);

        return jf;
    }

    public static void main(String[] args) throws IOException{
        connectToDatabase();
        jf = displayWelcomeScreen();
       
        ServerSocket socket = new ServerSocket(5190);
        ChatServer server = new ChatServer(socket);
        server.startServer();
       
        
        
    }
}

class Point {

    int x;
    int y;

    Point(int newx, int newy) {
        x = newx;
        y = newy;
    }

    Point() {
        this(0, 0);
    }
}

class PointColor {

    Point point;
    Color color;

    PointColor(Point newPoint, Color newColor) {
        point = newPoint;
        color = newColor;
    }
}

class DrawPanel extends JPanel {

    public Color selectedColor = Color.WHITE;

    ArrayList<Point> points;
    ArrayList<PointColor> points_colors;

    Graphics gImage;

    DrawPanel(BufferedImage output) {
        gImage = output.createGraphics();
        points = new ArrayList();
        points_colors = new ArrayList();
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.BLACK);
        gImage.setColor(Color.BLACK);
        g.fillRect(0, 0, getSize().width, getSize().height);
        gImage.fillRect(0, 0, getSize().width, getSize().height);

        for (PointColor p : points_colors) {
            g.setColor(p.color);
            gImage.setColor(p.color);
            g.fillOval(p.point.x - 5, p.point.y - 5, 10, 10);
            gImage.fillOval(p.point.x - 5, p.point.y - 5, 10, 10);
        }

    }

}
