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
        Socket sock = null;
        try {
            Scanner scan = new Scanner(System.in);

            System.out.println("Enter IP: ");
            String ip = scan.nextLine();

            System.out.println("Enter port number: ");
            int port = scan.nextInt();

            //InetAddress ip=InetAddress.getByName("localhost");
            sock = new Socket(ip, port);

            DataInputStream dis = new DataInputStream(sock.getInputStream());
            DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
            boolean loop = true;

            while (loop) {
                //System.out.println("first");
                System.out.println(dis.readUTF());
                String option = scan.nextLine();
                dos.writeUTF(option);

                switch (option) {
                    case "1":
                        //System.out.println("second");
                        if (dis.readUTF().equals("READY")) {
                            String value = "CREATEFAILED";
                            while (value.equals("CREATEFAILED")) {
                                boolean validate = false;
                                String idpw = "";
                                while (!validate) {

                                    System.out.println(dis.readUTF());
                                    idpw = scan.nextLine();

                                    String[] fullIdPw = idpw.split("\\*");
                                    String id = fullIdPw[0];
                                    String pw = fullIdPw[1];
                                    System.out.println(id + "  " + pw);

                                    String idRegex = "[a-zA-Z0-9]";
                                    Pattern pat1 = Pattern.compile(idRegex);
                                    Matcher match1 = pat1.matcher(id);

                                    String pwRegex = "[a-zA-Z0-9!#%$]";
                                    Pattern pat2 = Pattern.compile(pwRegex);
                                    Matcher match2 = pat2.matcher(pw);

                                    if (match1.find()) {
                                        System.out.println("true");

                                        if (match2.find()) {
                                            System.out.println("true");
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
                                    case "1":
                                        System.out.println(dis.readUTF());
                                        String fileName = scan.nextLine();
                                        dos.writeUTF(fileName);

                                        if (dis.readUTF().equals("FOUND")) {
                                            dos.writeUTF("READY");
                                            System.out.println(dis.readUTF());
                                            dos.writeUTF("DOWNLOADCOMPLETED");
                                            down = true;
                                        } else {
                                            down = true;
                                        }

                                        
                                        break;
                                    case "2":
                                        System.out.println(dis.readUTF());
                                        String contents = scan.nextLine();
                                        while (contents != null) {

                                            dos.writeUTF(contents);
                                            contents = scan.nextLine();
                                        }
                                      
                                        break;
                                    case "3":
                                        System.out.println(dis.readUTF());
                                       
                                        break;
                                    case "4":
                                        sock.close();
                                        loop = false;
                                        break;
                                    default:
                                        break;
                                }

                            }
                        }

                    case "2":
                        int trial = 0;
                        String login = "LOGINERROR";
                        //System.out.println("third");
                        if (dis.readUTF().equals("READY")) {
                            while (trial < 3 && login.equals("LOGINERROR")) {
                                System.out.println(dis.readUTF());

                                String idpw = scan.nextLine();
                                dos.writeUTF(idpw);
                                login = dis.readUTF();
                                trial++;

                            }
                            if (trial > 2) {
                                System.out.println("Terminate");
                            } else {
//                        System.out.println(dis.readUTF());
//                        String pw=scan.nextLine();
//                        dos.writeUTF(pw);
//                        //file access phase:
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
                                                down = true;
                                            } else {
                                                down = true;
                                            }

                                            break;
                                        case "2":
                                            System.out.println(dis.readUTF());
                                            String contents = scan.nextLine();
                                            while (contents != null) {

                                                dos.writeUTF(contents);
                                                contents = scan.nextLine();
                                            }
                                          
                                            break;
                                        case "3":
                                            System.out.println(dis.readUTF());
                                          
                                            break;
                                        case "4":
                                            sock.close();
                                            loop = false;
                                            break;
                                        default:
                                            break;
                                    }

                                    
                                }
                            }
                        }

                    case "3":
                        sock.close();
                        loop = false;
                        break;
                    default:
                        break;

                }

            }

            //dis.close();
            //dos.close();
        } catch (Exception e) {
            sock.close();
            e.printStackTrace();
        }

    }

}
