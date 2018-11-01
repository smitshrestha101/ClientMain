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
                        while (!validate) {

                            System.out.println(dis.readUTF());
                            String idpw = scan.nextLine();

                            String[] fullIdPw = idpw.split("*");
                            String id = fullIdPw[0];
                            String pw = fullIdPw[1];
                            
                            

                        }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
