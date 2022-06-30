package com.nqphu.utils;

import com.phu.ProfileThriftService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author phu
 */
public class ConnectionUtil {

    private String host;
    private String port;
    private List<ProfileThriftService.Client> connectionPool;
    public List<ProfileThriftService.Client> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 10;

    public ConnectionUtil() {
    }

    public ConnectionUtil(String host, String port, List<ProfileThriftService.Client> connectionPool) {
        this.host = host;
        this.port = port;
        this.connectionPool = connectionPool;
    }
    
    

    public static ConnectionUtil create(String host, String port) {
        TTransport transport = null;
        List<ProfileThriftService.Client> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        
        try {
            transport = new TSocket(host, Integer.parseInt(port));
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);

            ProfileThriftService.Client client = null;
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                client = new ProfileThriftService.Client(protocol);
                pool.add(client);

            }
        } catch (TTransportException ex) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ConnectionUtil(host, port, pool);

    }

    public ProfileThriftService.Client getConnection() {
        ProfileThriftService.Client client = connectionPool
                .remove(connectionPool.size() - 1);
        usedConnections.add(client);
        return client;
    }

    public boolean releaseConnection(ProfileThriftService.Client client) {
        connectionPool.add(client);
        return usedConnections.remove(client);
    }


    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
}
