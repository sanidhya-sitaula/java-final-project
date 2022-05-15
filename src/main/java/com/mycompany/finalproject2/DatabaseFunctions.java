/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject2;

import static com.mycompany.finalproject2.finalproject2.conn;
import static com.mycompany.finalproject2.finalproject2.current_user;
import static com.mycompany.finalproject2.finalproject2.displayHomePageScreen;
import static com.mycompany.finalproject2.finalproject2.jf;
import static com.mycompany.finalproject2.finalproject2.jf_draw;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author sanidhyasitaula
 */
class User {

    public int ID; 
    public String name;
    public String username;

    User(int newID, String newName, String newUsername) {
        ID = newID; 
        name = newName;
        username = newUsername;
    }
}

class Post {

    public int ID;
    public String username;
    public int numLikes;
    public String time;
    public int numComments;
    public ImageIcon image;

    Post(int newID, String newUsername, int newNumLikes, String newTime, int newNumComments, ImageIcon newImage) {
        ID = newID;
        username = newUsername;
        numLikes = newNumLikes;
        numComments = newNumComments;
        time = newTime;
        image = newImage;
    }
}

public class DatabaseFunctions {

    static boolean createUser(String user_name, String user_username, String user_password) {
        try {
            Statement s = conn.createStatement();
            String query = "INSERT INTO USERS(name, username, password) VALUES('" + user_name.toString() + "','" + user_username.toString() + "','" + user_password.toString() + "')";
            s.execute(query);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    static ArrayList<Post> getPosts() {
        ArrayList<Post> posts = new ArrayList<Post>();

        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM POSTS;");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String username = rs.getString("username");
                String time = rs.getString("date_posted");
                int numLikes = rs.getInt("num_likes");
                int numComments = rs.getInt("num_comments");
                byte[] image = rs.getBytes("photo");
                Image img = Toolkit.getDefaultToolkit().createImage(image);
                ImageIcon icon = new ImageIcon(img);
                Post thisPost = new Post(ID, username, numLikes, time, numComments, icon);
                posts.add(thisPost);
            }
            return posts;

        } catch (Exception e) {
            return null;
        }
    }

    static void insertPost(String username, int numLikes, String timestamp, int numComments, byte[] image) {
        try {

            jf_draw.dispose();

            jf.setVisible(false);

            PreparedStatement s = conn.prepareStatement("INSERT INTO POSTS(USERNAME, DATE_POSTED, NUM_LIKES, NUM_COMMENTS, PHOTO) VALUES (?, ?, ?, ?, ?)");

            ByteArrayInputStream inputStream = new ByteArrayInputStream(image);

            s.setString(1, username);
            s.setString(2, timestamp);
            s.setInt(3, numLikes);
            s.setInt(4, numComments);
            s.setBlob(5, inputStream);

            s.execute();

            System.out.println("Inserted succesffuly");

            jf = displayHomePageScreen();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static boolean logUserIn(String user_username, String user_password) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM USERS WHERE USERNAME=?");
            statement.setString(1, user_username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String password_in_database = rs.getString("password");
                if (user_password.equals(password_in_database)) {
                    current_user = new User(rs.getInt("ID"), rs.getString("name"), rs.getString("username"));
                    ChatClient client = new ChatClient(current_user.username);
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    static User getUserDetails(String user_username) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM USERS WHERE USERNAME=?");
            statement.setString(1, user_username);
            ResultSet rs = statement.executeQuery();
            User thisUser = null;

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String name = rs.getString("name");
                thisUser = new User(ID, name, user_username);
                return thisUser;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
    
    static ArrayList<Integer> getTotalUserPostsAndLikes(String user_username){
        
        ArrayList<Integer> posts_and_likes = new ArrayList();
        posts_and_likes.add(0);
        posts_and_likes.add(0);
       
        try{
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) AS NUM_POSTS, SUM(NUM_LIKES) AS TOTAL_LIKES FROM POSTS WHERE USERNAME=?");
            statement.setString(1, user_username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int num_posts = rs.getInt("NUM_POSTS");
                int num_likes = rs.getInt("TOTAL_LIKES"); 
                posts_and_likes.set(0, num_posts);
                posts_and_likes.set(1, num_likes);
                return posts_and_likes;
            }
        }
        catch (Exception e){
            System.out.println(e);
            return posts_and_likes;
        }
        
        return posts_and_likes; 
    }
    
    
    static void pushLike(User user, Post p){
        try{
            
            PreparedStatement statement = conn.prepareStatement("INSERT INTO POSTS_LIKES(POSTID, LIKEDBYUSERID) VALUES (?, ?)"); 
            PreparedStatement statement2 = conn.prepareStatement("UPDATE POSTS SET NUM_LIKES = ? WHERE ID = ?");
            statement.setInt(1, p.ID);
            statement.setInt(2, user.ID); 
            statement2.setInt(1, p.numLikes + 1);
            statement2.setInt(2, p.ID);
            
            statement.execute();
            statement2.execute(); 
            
        }
        
        catch (Exception e){
            System.out.println(e);
            return; 
        }
    }
    
    static void pushUnlike(User user, Post p){
        try{            
            PreparedStatement statement = conn.prepareStatement("DELETE FROM POSTS_LIKES WHERE POSTID = ? AND LIKEDBYUSERID = ?"); 
            PreparedStatement statement2 = conn.prepareStatement("UPDATE POSTS SET NUM_LIKES = ? WHERE ID = ?");
            statement.setInt(1, p.ID);
            statement.setInt(2, user.ID); 
            statement2.setInt(1, p.numLikes - 1);
            statement2.setInt(2, p.ID);
            
            statement.execute();
            statement2.execute(); 
        }
        catch (Exception e){
            System.out.println(e);
            return; 
        }
    }
    
    static boolean checkLikedStatusOnAPost(User user, Post post){
        try{
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM POSTS_LIKES WHERE POSTID=? AND LIKEDBYUSERID=?");
            
            statement.setInt(1, post.ID);
            statement.setInt(2, user.ID); 
            
            ResultSet rs = statement.executeQuery();
            
            if (!rs.next()){
                return false;
            }
        
            return true;
        }
        catch (Exception e){
            
            return false;
        }
    }
}
