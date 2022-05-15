/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject2;
import java.io.*;
import java.net.*; 
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import javax.swing.*;

/**
 *
 * @author sanidhyasitaula
 */


class JoinAction implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent arg){
            String ip = ChatClient.jt_port.getText(); 
            ChatClient.ip = ip; 
            String username = ChatClient.jt_username.getText();
            ChatClient.username = username; 
            ChatClient.createChatWindow();
        }
    }

class SendAction implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent arg){
        
        String message = ChatClient.my_messages.getText();
        String msg = finalproject2.my_message_field.getText();
        ChatClient.sendMessage(msg);
    }
}



public class ChatClient{

    
    private static Socket socket;
    public static String ip; 
    private static BufferedReader bufferedReader; 
    private static BufferedWriter bufferedWriter; 
    public static String username = "";
    
    
    public static JFrame jf; 
    public static JTextField jt_port; 
    public static JTextField jt_username; 
    
    public static JTextArea chat_field = new JTextArea(10,1); 
    
    public static String all_chats; 
    
    public static JTextArea my_messages = new JTextArea(7, 1);
    
    public static JButton sendButton; 
    
    public static JButton jb; 
    

    
    /**
     * @param args the command line arguments
     */
    
    
    public static void createChatWindow(){
        jf.setVisible(false);
        jf.dispose();
        
        jf = new JFrame("Chat Window");
        jf.setLayout(new BorderLayout(15,15));
        jf.setSize(500, 500); 
        jf.setBackground(Color.DARK_GRAY); 
        
        chat_field.setPreferredSize(new Dimension(400, 300));
        chat_field.setEditable(false);
        jf.add(chat_field, BorderLayout.PAGE_START); 
        
        my_messages.setPreferredSize(new Dimension(350, 300));
        jf.add(my_messages, BorderLayout.CENTER); 
        sendButton = new JButton("Send");
        sendButton.addActionListener(new SendAction()); 
        sendButton.setSize(10,10);
        jf.add(sendButton, BorderLayout.PAGE_END);
        jf.setVisible(true);
    }
    
    
    
    
    public ChatClient(String username){
        try{
            ChatClient.socket = new Socket("127.0.0.1", 5190); 
            ChatClient.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
            ChatClient.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ChatClient.username = username; 
            bufferedWriter.write(username);

            bufferedWriter.newLine();
            bufferedWriter.flush();
            this.listenForMessage();
        }
        catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter); 
        }
    }
    
    public static void sendMessage(String message){
        try{
            Scanner scanner = new Scanner(System.in);
            bufferedWriter.write(username + ": " + message); 
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch(IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter); 
        }
    }
    
    public void listenForMessage(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String msgFromGroupChat;
                while (!socket.isClosed()){
                    try{
                        msgFromGroupChat = bufferedReader.readLine();
                        finalproject2.chat_field.append(msgFromGroupChat + "\n");
                        chat_field.append(msgFromGroupChat + "\n");
                    }
                    catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter); 
                    }
                }
            }
        }).start();
    }
    
    public static void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        }
        catch (IOException e){
            e.printStackTrace(); 
        }
    }
    
    public static void displayInitialScreen(){
           
        jf = new JFrame("Join a Server");    
        JPanel jp = new JPanel();
   
        JLabel jl = new JLabel("Enter the server IP: ");
        JLabel jl2 = new JLabel("Enter your username: "); 
        
        jt_port = new JTextField(10);
        jt_username = new JTextField(10); 
        
        jb = new JButton("Join"); 
        jb.addActionListener(new JoinAction());
    
        jp.add(jl);
        jp.add(jt_port);
        jp.add(jl2);
        jp.add(jt_username);
        
        jp.add(jb);
        jf.add(jp);
        
        jf.setLayout(new GridLayout(0,1));
        jf.setSize(350, 350);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    
}
