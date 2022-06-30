package com.phu;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class ProfileThriftServer {

    public static ProfileServiceHandler handler;

    public static ProfileThriftService.Processor processor;

    public static void main(String[] args) {
        try {
            handler = new ProfileServiceHandler();
            processor = new ProfileThriftService.Processor(handler);

            Runnable simple = new Runnable() {
                public void run() {
                    simple(processor);
                }
            };

            new Thread(simple).start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void simple(ProfileThriftService.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the thread pool server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
