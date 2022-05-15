/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject2;

import java.awt.Image;
import java.net.URL;
import java.util.Comparator;
import javax.swing.ImageIcon;

/**
 *
 * @author sanidhyasitaula
 */
public class Util {

    protected ImageIcon createImageIcon(String path,
            String description) {
        java.net.URL imgURL = getClass().getResource(path);

        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public java.net.URL getURL(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return imgURL;
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    public ImageIcon getImage(String url){
        try {
            URL imageurl = new URL(url);
            ImageIcon icon = new ImageIcon(imageurl);
    
            Image image = icon.getImage(); // transform it 
            Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            ImageIcon resizedImage = new ImageIcon(newimg);
            return resizedImage;
        }
        
        catch (Exception e){
            return null;
        }
       
    }

}

class CustomComparator implements Comparator<Post> {

    @Override
    public int compare(Post p1, Post p2) {
        return p1.ID > p2.ID ? -1 : 1;
    }
}
