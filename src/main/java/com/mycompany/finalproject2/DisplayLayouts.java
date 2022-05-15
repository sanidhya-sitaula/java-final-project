/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject2;

import static com.mycompany.finalproject2.finalproject2.current_user;
import static com.mycompany.finalproject2.finalproject2.displayUserProfile;
import static com.mycompany.finalproject2.finalproject2.getAllPosts;
import static com.mycompany.finalproject2.finalproject2.getSpecificUserPosts;
import static com.mycompany.finalproject2.finalproject2.jf_draw;
import static com.mycompany.finalproject2.finalproject2.jp;
import static com.mycompany.finalproject2.finalproject2.output;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 *
 * @author sanidhyasitaula
 */
public class DisplayLayouts {

    static JFrame getHomePageLayout(JFrame jf) {
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

        JPanel jp_top = new JPanel();
        FlowLayout topLayout = new FlowLayout();

        JPanel news_feed = new JPanel();

        jp_top.setLayout(topLayout);

        JLabel jl = new JLabel("Welcome, " + current_user.name + "!");
        jl.setForeground(new Color(60, 173, 186));
        jl.setFont(new Font("SansSerif", Font.BOLD, 17));

        JLabel title = new JLabel("Your Feed");

        JButton logout_button = new JButton("Log Out");

        JButton profile_button = new JButton("View My Profile");
        profile_button.addActionListener(new ButtonListener() {
            public void actionPerformed(ActionEvent arg) {
                displayUserProfile(current_user.username);
            }

        });
        profile_button.setPreferredSize(new Dimension(40, 40));
        JButton chat_button = new JButton("Chat with Users");
        JButton create_new_post = new JButton("Create a New Post");
        create_new_post.addActionListener(new CreateNewPostButtonListener());

        // Get main container
        Container mainContainer = jf.getContentPane();
        mainContainer.setLayout(new BorderLayout(5, 6));
        // Set a gray-ish backgroune
        mainContainer.setBackground(new Color(230, 230, 230));
        // Set a border to the main panel
        jf.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(76, 228, 245)));

        JPanel topPanel = new JPanel();
        topPanel.setBorder(new LineBorder(Color.BLACK, 1));
        topPanel.setBackground(new Color(255, 255, 255));
        topPanel.setLayout(new FlowLayout(5));

        topPanel.add(jl);
        topPanel.add(logout_button);
        mainContainer.add(topPanel, BorderLayout.NORTH);

        // Middle Panel 
        JPanel middlePanel = new JPanel();
        middlePanel.setBorder(new LineBorder(Color.BLACK, 1));
        middlePanel.setLayout(new FlowLayout(4, 4, 4));
        middlePanel.setBackground(Color.WHITE);

        
        JPanel gridPanelBox = new JPanel();
        
        gridPanelBox.setLayout(new BoxLayout(gridPanelBox, BoxLayout.PAGE_AXIS));
        
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(2, 1, 5, 5));
        gridPanel.setBorder(new LineBorder(Color.BLACK, 1));

        gridPanel.add(create_new_post);
        gridPanel.add(profile_button);
//        
//        JTextArea chat_display = new JTextArea();
//        chat_display.setPreferredSize(new Dimension(40, 50));
//        
//        gridPanel.add(chat_display);
//        gridPanel.add(new JTextArea());
//        gridPanel.add(new JButton("Send"));


        JLabel chat_label = new JLabel("Chat");
        
        //         jl.setFont(new Font("SansSerif", Font.BOLD, 17));


        chat_label.setFont(new Font("SansSerif", Font.BOLD, 13));

        finalproject2.chat_field = new JTextArea();
        finalproject2.chat_field.setEditable(false);
        finalproject2.chat_field.setBorder(new LineBorder(Color.BLACK, 1));
        
        
        finalproject2.my_message_field = new JTextArea("Type your message here...");
        finalproject2.my_message_field.setLineWrap(true);
        finalproject2.my_message_field.setWrapStyleWord(true);
        finalproject2.my_message_field.setBorder(new LineBorder(Color.BLACK, 1));
        
        JScrollPane scroll_display = new JScrollPane(finalproject2.chat_field);
        scroll_display.setPreferredSize(new Dimension(30, 400));
        
        //scroll_display.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollPane scroll_message = new JScrollPane(finalproject2.my_message_field); 
        scroll_message.setPreferredSize(new Dimension(30, 60));

        JPanel buttonGridPanel = new JPanel();
        buttonGridPanel.setLayout(new GridLayout(1, 1, 5, 5));
        
        JButton sendButton = new JButton("Send"); 
        sendButton.addActionListener(new SendAction());
        sendButton.setPreferredSize(new Dimension(200, 40));
        buttonGridPanel.add(sendButton);
    
        gridPanelBox.add(gridPanel);
        
        scroll_display.setAutoscrolls(true);
        scroll_message.setAutoscrolls(true);
        gridPanelBox.add(Box.createVerticalStrut(10));
        gridPanelBox.add(chat_label);
        gridPanelBox.add(Box.createVerticalStrut(10));
        gridPanelBox.add(scroll_display);
        gridPanelBox.add(Box.createVerticalStrut(10));
    
        gridPanelBox.add(scroll_message);
        gridPanelBox.add(buttonGridPanel);
        
        JScrollPane gridPanelScrollable = new JScrollPane(gridPanelBox);
        
        gridPanelScrollable.setPreferredSize(new Dimension(270, 690));
        
        gridPanelScrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        gridPanelBox.setAutoscrolls(true);
        
        middlePanel.add(gridPanelScrollable);
        mainContainer.add(title);
        mainContainer.add(getAllPosts());
        mainContainer.add(middlePanel, BorderLayout.WEST);

        logout_button.addActionListener(new LogoutButtonListener());
        
        return jf;

    }

    static JPanel getPostLayout(Post p, String postLocation) {

        JPanel sub_jp = new JPanel();
        sub_jp.setLayout(new BoxLayout(sub_jp, BoxLayout.Y_AXIS));
        JPanel top_panel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        JLabel username = new JLabel(p.username);
        username.setFont(new Font("SansSerif", Font.BOLD, 14));
        JLabel time = new JLabel("at " + p.time);
        time.setForeground(new Color(156, 156, 156));
        top_panel.add(username);
        top_panel.add(time);
        top_panel.setBackground(Color.WHITE);

        top_panel.setBorder(new LineBorder(new Color(203, 203, 203), 1));
        
        JLabel numLikesLabel = new JLabel(Integer.toString(p.numLikes));
                
        JLabel likesString = new JLabel("like(s)");
        
        JButton like_unlike_button;
        
        if (!DatabaseFunctions.checkLikedStatusOnAPost(current_user, p)){
            like_unlike_button = new JButton("Like");
        }
        else {
            like_unlike_button = new JButton("Unlike"); 
        }
        
              
        like_unlike_button.addActionListener(new LikePostButtonListener(p, numLikesLabel));
        
        JButton view_user_profile = new JButton("View " + p.username + "'s Profile");
        JLabel photo = new JLabel();
        photo.setIcon(p.image);
        photo.setPreferredSize(new Dimension(p.image.getIconWidth(), p.image.getIconHeight()));

        JPanel bottom_panel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        bottom_panel.add(numLikesLabel);
        bottom_panel.add(likesString);
        
        bottom_panel.add(like_unlike_button);
        

        if (current_user.username != p.username && postLocation.equals("Homepage")) {
            view_user_profile.addActionListener( new ButtonListener() {
            public void actionPerformed(ActionEvent arg) {
                displayUserProfile(p.username);
            }
        });
            bottom_panel.add(view_user_profile);
            
        }
        bottom_panel.setBackground(Color.WHITE);
        bottom_panel.setBorder(new LineBorder(new Color(203, 203, 203), 1));

        JPanel mid_panel = new JPanel();
        mid_panel.setLayout(new BoxLayout(mid_panel, BoxLayout.X_AXIS));

        top_panel.setPreferredSize(new Dimension(100, 50));
        sub_jp.add(top_panel);
        
        sub_jp.add(Box.createVerticalStrut(20));
        
        mid_panel.add(photo);
        sub_jp.add(mid_panel);
        
        sub_jp.add(Box.createVerticalStrut(20));

        sub_jp.add(bottom_panel);
        sub_jp.setBorder(new LineBorder(new Color(209, 209, 209), 1));
        sub_jp.setPreferredSize(new Dimension(400, 630));

        sub_jp.setBackground(Color.WHITE);

        return sub_jp;
    }
    
    
    static JPanel getDrawScreenLayout(){
        JPanel outer_panel = new JPanel();
        outer_panel.setLayout(new BoxLayout(outer_panel, BoxLayout.Y_AXIS));

        jf_draw.setSize(500, 572);

        output = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);

        jp = new DrawPanel(output);

        jp.setBackground(Color.BLACK);

        jp.setPreferredSize(new Dimension(500, 700));

        jp.setBorder(new LineBorder(new Color(209, 209, 209), 1));

        jp.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                PointColor thisPointColor = new PointColor(new Point(e.getX(), e.getY()), jp.selectedColor);
                jp.points_colors.add(thisPointColor);
                jp.repaint();

            }
        });

        jp.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                PointColor thisPointColor = new PointColor(new Point(e.getX(), e.getY()), jp.selectedColor);
                jp.points_colors.add(thisPointColor);
                jp.repaint();
            }
        });

        JPanel color_panel = new JPanel();
        color_panel.setLayout(new FlowLayout());

        JPanel bottom_panel = new JPanel();
        bottom_panel.setLayout(new FlowLayout());

        JButton post_button = new JButton("Post");
        post_button.addActionListener(new SavePostButtonListener());

        JButton red_color_button = new JButton("Red");
        red_color_button.setBackground(Color.RED);
        red_color_button.setForeground(Color.RED);
        red_color_button.setOpaque(true);
        red_color_button.setBorderPainted(false);
        red_color_button.setPreferredSize(new Dimension(25, 25));

        JButton blue_color_button = new JButton("Blue");
        blue_color_button.setBackground(Color.BLUE);
        blue_color_button.setForeground(Color.BLUE);
        blue_color_button.setOpaque(true);
        blue_color_button.setBorderPainted(false);
        blue_color_button.setPreferredSize(new Dimension(25, 25));

        JButton green_color_button = new JButton("Green");
        green_color_button.setBackground(Color.GREEN);
        green_color_button.setForeground(Color.GREEN);
        green_color_button.setOpaque(true);
        green_color_button.setBorderPainted(false);
        green_color_button.setPreferredSize(new Dimension(25, 25));

        JButton white_color_button = new JButton("White");
        white_color_button.setBackground(Color.WHITE);
        white_color_button.setForeground(Color.WHITE);
        white_color_button.setOpaque(true);
        white_color_button.setBorderPainted(false);
        white_color_button.setPreferredSize(new Dimension(25, 25));

        JButton clear_button = new JButton("Clear");
        clear_button.addActionListener(new ButtonListener() {
            @Override
            public void actionPerformed(ActionEvent arg) {
                jp.points_colors.clear();
                jp.removeAll();
                jp.repaint();
            }
        }
        );

        white_color_button.addActionListener(new ColorSelectorButtonListener());
        blue_color_button.addActionListener(new ColorSelectorButtonListener());
        green_color_button.addActionListener(new ColorSelectorButtonListener());
        red_color_button.addActionListener(new ColorSelectorButtonListener());

        color_panel.add(white_color_button);
        color_panel.add(red_color_button);
        color_panel.add(blue_color_button);
        color_panel.add(green_color_button);
        color_panel.add(clear_button);

        post_button.setPreferredSize(new Dimension(50, 50));
        bottom_panel.add(post_button);
        outer_panel.add(jp);
        outer_panel.add(color_panel);
        outer_panel.add(new JLabel("Instructions: Slowly click and drag your mouse to draw."));

        outer_panel.add(bottom_panel);
        
        return outer_panel;
    }
    
    static JFrame getUserProfileLayout(JFrame jf_profile, User user){
        JPanel jp_top = new JPanel();
        FlowLayout topLayout = new FlowLayout();

        jp_top.setLayout(topLayout);

        JLabel jl = new JLabel(user.username);
        jl.setForeground(new Color(60, 173, 186));
        jl.setFont(new Font("SansSerif", Font.BOLD, 17));

        JLabel title = new JLabel("All Posts");

        // Get main container
        Container mainContainer = jf_profile.getContentPane();
        mainContainer.setLayout(new BorderLayout(8, 6));
        // Set a gray-ish backgroune
        mainContainer.setBackground(new Color(230, 230, 230));
        // Set a border to the main panel
        jf_profile.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(76, 228, 245)));

        JPanel topPanel = new JPanel();
        topPanel.setBorder(new LineBorder(Color.BLACK, 1));
        topPanel.setBackground(new Color(255, 255, 255));
        topPanel.setLayout(new FlowLayout(5));

        topPanel.add(jl);
        mainContainer.add(topPanel, BorderLayout.NORTH);

        // Middle Panel 
        JPanel middlePanel = new JPanel();
        middlePanel.setBorder(new LineBorder(Color.BLACK, 1));
        middlePanel.setLayout(new FlowLayout(4, 4, 4));
        middlePanel.setBackground(Color.WHITE);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 1, 5, 5));
        gridPanel.setBorder(new LineBorder(Color.BLACK, 1));
        gridPanel.add(new JLabel("Name: " + user.name));
        gridPanel.add(new JLabel("Total Posts: " + DatabaseFunctions.getTotalUserPostsAndLikes(user.username).get(0)));
        gridPanel.add(new JLabel("Total Likes Received: " + DatabaseFunctions.getTotalUserPostsAndLikes(user.username).get(1)));

        middlePanel.add(gridPanel);
        mainContainer.add(title);
        mainContainer.add(getSpecificUserPosts(user.username));
        mainContainer.add(middlePanel, BorderLayout.WEST);

        jf_profile.setSize(800, 700);

        jf_profile.setLocationRelativeTo(null);
        jf_profile.setVisible(true);
        
        return jf_profile;
    }

}
