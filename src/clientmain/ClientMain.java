/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientmain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        Socket sock = null;
         DataInputStream dis = null;
         DataOutputStream dos = null;
        try {

            Scanner scan = new Scanner(System.in);
            while (true) {
                System.out.println("Enter IP: ");
                String ip = scan.next();

                System.out.println("Enter port number: ");
                int port = scan.nextInt();

                sock = new Socket(ip, port);
                dis = new DataInputStream(sock.getInputStream());
                dos = new DataOutputStream(sock.getOutputStream());
                String read=dis.readUTF();
                if (read.equals("READY")){
                    break;
                }
            }

            
            boolean loop = true;

            while (loop) {
               
                System.out.println(dis.readUTF());
                
                String option = scan.nextLine();
                dos.writeUTF(option);

                switch (option) {
                    case "1":                                                   //NEW USER

                        if (dis.readUTF().equals("READY")) {
                            String value = "CREATEFAILED";
                            while (value.equals("CREATEFAILED")) {
                                boolean validate = false;
                                String idpw = "";
                                while (!validate) {
                                                                                    //VALIDATE ID AND PASSWORD FOR ALPHANUMERIC CHARACTERS
                                    System.out.println(dis.readUTF());
                                    idpw = scan.nextLine();

                                    String[] fullIdPw = idpw.split("\\*");
                                    String id = fullIdPw[0];
                                    String pw = fullIdPw[1];
                                    //System.out.println(id + "  " + pw);

                                    String idRegex = "[a-zA-Z0-9]";
                                    Pattern pat1 = Pattern.compile(idRegex);
                                    Matcher match1 = pat1.matcher(id);

                                    String pwRegex = "[a-zA-Z0-9!#%$]";
                                    Pattern pat2 = Pattern.compile(pwRegex);
                                    Matcher match2 = pat2.matcher(pw);

                                    if (match1.find()) {
                                       // System.out.println("true");

                                        if (match2.find()) {
                                           // System.out.println("true");
                                            validate = true;

                                        } else {
                                            System.out.println("Password should be alphanumeric charaters or !,#,$,%");
                                        }

                                    } else {
                                        System.out.println("Id should be only alphanumeric charaters!");
                                    }

                                }

                                dos.writeUTF(idpw);
                                value = dis.readUTF();
                                if (value.equals("CREATEFAILED")) {
                                    System.out.println(value);
                                }
                            }
                            //file access phase:
                            boolean down = true;
                            while (down) {
                                System.out.println(dis.readUTF());

                                String choice = scan.nextLine();
                                dos.writeUTF(choice);

                                switch (choice) {
                                    case "1":                                   //DOWNLOAD 
                                        System.out.println(dis.readUTF());
                                        String fileName = scan.nextLine();
                                        dos.writeUTF(fileName);

                                        if (dis.readUTF().equals("FOUND")) {
                                            dos.writeUTF("READY");
                                            System.out.println(dis.readUTF());
                                            dos.writeUTF("DOWNLOADCOMPLETED");
                                            //down = true;
                                        } else {
                                            
                                           // down = true;
                                        }

                                        break;
                                    case "2":                                   //UPLOAD
                                        if (dis.readUTF().equals("READY")) {
                                            System.out.println(dis.readUTF());
                                            String fileName1 = scan.nextLine();
                                            if (fileName1 != null) {

                                                dos.writeUTF(fileName1);

                                                if (dis.readUTF().equals("CONTINUE")) {
                                                    System.out.println(dis.readUTF());
                                                    String contents = scan.nextLine();
                                                    dos.writeUTF(contents);
                                                }

                                            } else {
                                                dos.writeUTF("ERROR");
                                            }
                                        }
                                        break;
                                    case "3":                                   //FILE LIST
                                        System.out.println(dis.readUTF());

                                        break;
                                    case "4":                                   //DISCONNECT
                                        sock.close();
                                        loop = false;
                                        down = false;
                                        break;
                                    default:
                                        break;
                                }

                            }
                        }
                        break;

                    case "2":                                                   //EXISTING USER
                        int trial = 0;
                        String login = "LOGINERROR";

                        if (dis.readUTF().equals("READY")) {
                            while (trial < 3 && login.equals("LOGINERROR")) {   //LIMIT NUMBER OF TRIALS FOR ENTERING ID AND PASSWORD
                                System.out.println(dis.readUTF());

                                String idpw = scan.nextLine();
                                dos.writeUTF(idpw);
                                login = dis.readUTF();
                                trial++;

                            }
                            if (trial > 2) {

                                break;
                            } else {

                                //file access phase:
                                boolean down = true;
                                while (down) {
                                    System.out.println(dis.readUTF());

                                    String choice = scan.nextLine();
                                    dos.writeUTF(choice);

                                    switch (choice) {
                                        case "1":
                                            System.out.println(dis.readUTF());
                                            String fileName = scan.nextLine();
                                            dos.writeUTF(fileName);

                                            if (dis.readUTF().equals("FOUND")) {
                                                dos.writeUTF("READY");
                                                System.out.println(dis.readUTF());
                                                dos.writeUTF("DOWNLOADCOMPLETED");
                                                //down = true;
                                            } else {
                                                //System.out.println(dis.readUTF());
                                                //down = true;
                                            }

                                            break;
                                        case "2":
                                            if (dis.readUTF().equals("READY")) {
                                                System.out.println(dis.readUTF());
                                                String fileName1 = scan.nextLine();
                                                if (fileName1 != null) {

                                                    dos.writeUTF(fileName1);

                                                    if (dis.readUTF().equals("CONTINUE")) {
                                                        System.out.println(dis.readUTF());
                                                        String contents = scan.nextLine();
                                                        dos.writeUTF(contents);
                                                    }

                                                } else {
                                                    dos.writeUTF("ERROR");
                                                }
                                            }
                                            break;
                                        case "3":
                                            System.out.println(dis.readUTF());
                                            dos.writeUTF("SUCCESSFUL");
                                            break;
                                        case "4":
                                            sock.close();
                                            loop = false;
                                            down = false;
                                            break;
                                        default:
                                            break;
                                    }

                                }
                            }
                        }
                        break;

                    case "3":
                        sock.close();
                        loop = false;
                        break;
                    default:
                        break;

                }

            }

        } catch (Exception e) {
            sock.close();
            e.printStackTrace();
        }

    }

}
