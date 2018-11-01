/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientmain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author smits
 */
public class ClientMain {

    public static void main(String[] args) throws IOException {
        try {
            Scanner scan = new Scanner(System.in);

            System.out.println("Enter IP: ");
            String ip = scan.nextLine();

            System.out.println("Enter port number: ");
            int port = scan.nextInt();

            //InetAddress ip=InetAddress.getByName("localhost");
            Socket sock = new Socket(ip, port);

            DataInputStream dis = new DataInputStream(sock.getInputStream());
            DataOutputStream dos = new DataOutputStream(sock.getOutputStream());

            while (true) {
                System.out.println(dis.readUTF());
                String option = scan.nextLine();
                dos.writeUTF(option);

                switch (option) {
                    case "1":
                        boolean validate = false;
                        String idpw="";
                        while (!validate) {

                            System.out.println(dis.readUTF());
                            idpw = scan.nextLine();

                            String[] fullIdPw = idpw.split("\\*");
                            String id = fullIdPw[0];
                            String pw = fullIdPw[1];
                            System.out.println(id +"  "+ pw);
                            
                            String idRegex="[a-zA-Z0-9]";
                            Pattern pat1=Pattern.compile(idRegex);
                            Matcher match1=pat1.matcher(id);
                            
                            String pwRegex="[a-zA-Z0-9!#%$]";
                            Pattern pat2=Pattern.compile(pwRegex);
                            Matcher match2=pat2.matcher(pw);
                            
                            if(match1.find()){
                                System.out.println("true");
                         
                                  
                                if(match2.find()){
                                    System.out.println("true");
                                validate=true;
                               
                                
                                }else{
                                     System.out.println("Password should be alphanumeric charaters or !,#,$,%");
                                }
                                
                              
                            }else{
                                System.out.println("Id should be only alphanumeric charaters!");
                            }
                            
                         

                        }
                        dos.writeUTF(idpw);
                    
                    case "2":
                        
                        
                        
            

                }
                sock.close();
                        dis.close();
                        dos.close();

            }
            
            
          
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
