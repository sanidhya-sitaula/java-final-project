/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject2;


import static com.mycompany.finalproject2.finalproject2.current_user;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
/**
 *
 * @author sanidhyasitaula
 */

class ColorSelectorButtonListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent arg){
        JButton button = (JButton) arg.getSource();
        if (button.getText() == "Green"){
            finalproject2.jp.selectedColor = Color.GREEN; 
        }
        if (button.getText() == "Red"){
            finalproject2.jp.selectedColor = Color.RED; 
        }
        if (button.getText() == "White"){
            finalproject2.jp.selectedColor = Color.WHITE; 
        }
         if (button.getText() == "Blue"){
            finalproject2.jp.selectedColor = Color.BLUE; 
        }
         

    }
}

class LikePostButtonListener implements ActionListener{
    
    Post p;
    JLabel jl;
    
    LikePostButtonListener(Post newP, JLabel newJl){
        p = newP;
        jl = newJl; 
    }
    
    @Override
    public void actionPerformed(ActionEvent arg){
        JButton jb = (JButton) arg.getSource(); 
        
        if (jb.getText().equals("Like")){
            DatabaseFunctions.pushLike(current_user, p);
            jb.setText("Unlike");
            jl.setText(Integer.toString(Integer.parseInt(jl.getText()) + 1)); 
        }
        
        else if (jb.getText().equals("Unlike")){
            DatabaseFunctions.pushUnlike(current_user, p);
            jb.setText("Like");
            jl.setText(Integer.toString(Integer.parseInt(jl.getText()) - 1));
        }
        
    }
}


class SavePostButtonListener implements ActionListener{
    
   
    @Override
    public void actionPerformed(ActionEvent arg){
        try {
        
        BufferedImage output = finalproject2.output; 
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(output, "jpg", baos);
        
        byte[] bytes = baos.toByteArray();
                
        
        SimpleDateFormat formatter = new SimpleDateFormat("s", Locale.getDefault());  
        
        java.util.Date rightNow; 
        
        SimpleDateFormat currentDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        
        String currentDate = currentDateFormatter.format(new Date());
        
        int current_second;
        int current_minute;
        int current_hour;  
        
        rightNow = new java.util.Date();  
        
        formatter.applyPattern("s");  
        current_second = Integer.parseInt(formatter.format(rightNow));  
        formatter.applyPattern("m");  
        current_minute = Integer.parseInt(formatter.format(rightNow));  
        formatter.applyPattern("h");  
        current_hour = Integer.parseInt(formatter.format(rightNow)); 
        
        
        currentDate += " " + current_hour + ":" + current_minute;
        
        DatabaseFunctions.insertPost(finalproject2.current_user.username, 0, currentDate, 0, bytes);
        
        }
        catch (Exception e){
            
        }
    }
}


class CreateNewPostButtonListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent arg){
        finalproject2.displayDrawScreen();
    }
}


class ButtonListener implements ActionListener{
    
    ButtonListener(){}
    @Override
    public void actionPerformed(ActionEvent arg) {
        finalproject2.jf.dispose();
        JButton jb = (JButton) arg.getSource();
        String type = jb.getText();
        if (type == "Log In"){
            finalproject2.jf = finalproject2.displayLoginScreen();
        }
        else {
            finalproject2.jf = finalproject2.displayRegisterScreen();
        }

    }
}


class RouteToLoginButtonListener implements ActionListener{
    
    RouteToLoginButtonListener(){}
    
    @Override
    public void actionPerformed(ActionEvent arg){
        finalproject2.jf.dispose();
        finalproject2.jf = finalproject2.displayLoginScreen();
    }
}


class RouteToHomeButtonListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent arg){
        finalproject2.jf.dispose();
        finalproject2.jf = finalproject2.displayWelcomeScreen();
    }
}

class RegisterButtonListener implements ActionListener{

    RegisterButtonListener(){}
    
    @Override
    public void actionPerformed(ActionEvent arg){
        finalproject2.jf.dispose(); 
        String name = finalproject2.name_textfield.getText();
        String username = finalproject2.username_textfield.getText();
        String password = finalproject2.password_textfield.getText(); 
        System.out.println("name: " + name);
        System.out.println("username: " + username);
        System.out.println(password);
        boolean result = DatabaseFunctions.createUser(name, username, password);
        if (result){
            finalproject2.jf = finalproject2.createRegisteredSuccessfullyScreen();
        }
        else {
            finalproject2.jf = finalproject2.displayErrorScreen("Username is taken. Please try a different username.");
        }
    }
}


class LoginButtonListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent arg){
        finalproject2.jf.dispose();
        String username = finalproject2.login_username_textfield.getText();
        String password = finalproject2.login_password_textfield.getText();
        boolean result = DatabaseFunctions.logUserIn(username, password);
        if (result){
            finalproject2.jf = finalproject2.displayHomePageScreen(); 
        }
        else {
            finalproject2.jf = finalproject2.displayErrorScreen("Please check your username/password combination and try again.");
        }
        
    }
}

class LogoutButtonListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent arg){
        finalproject2.jf.dispose();
        finalproject2.current_user = null;
        finalproject2.jf = finalproject2.displayWelcomeScreen();
    }
}

