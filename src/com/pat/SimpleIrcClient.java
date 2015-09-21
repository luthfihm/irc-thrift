package com.pat;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.Scanner;

/**
 * Created by luthfi on 21/09/15.
 */
public class SimpleIrcClient {
    public static void main(String Args[]) {
        try {
            TTransport transport;
            transport = new TSocket("localhost", 9090);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            SimpleIrcService.Client client = new SimpleIrcService.Client(protocol);
            perform(client);
            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }

    private static void perform(SimpleIrcService.Client client) throws TException {
        String user = client.login();
        Scanner in = new Scanner(System.in);
        String input;
        System.out.println("Welcome to Simple IRC....");
        boolean exit = false;
        do {
            System.out.print(user + " > ");
            input = in.nextLine();
            String args[] = input.split(" ");
            if (args[0].equals("exit")) {
                String response = client.exit(user);
                if (response.equals("OK")) {
                    exit = true;
                }
            } else if (args[0].equals("nick")) {
                String response = client.nick(user,args[1]);
                if (response.equals("OK")) {
                    user = args[1];
                } else {
                    System.out.println(response);
                }
            } else if (args[0].equals("join")) {
                String response = client.join(user, args[1]);
                if (response.equals("OK")) {
                    System.out.println("You have joined channel "+args[1]);
                } else {
                    System.out.println(response);
                }
            }
        } while (!exit);
    }
}
