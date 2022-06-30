
package com.nqphu.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phu
 */
public class SessionUtil {
    private String session_id;
    private int user_id;
    private String user_agent;
    private String ip_address;

    public SessionUtil() {
    }

    public SessionUtil(String session_id, int user_id, String user_agent, String ip_address) {
        this.session_id = session_id;
        this.user_id = user_id;
        this.user_agent = user_agent;
        this.ip_address = ip_address;
    }

    public String getSession_id() {
        return session_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }
    
    
    
    public Connection getConnection () {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Session";
            String user = "root";
            String pwd = "quangphu2805";
            try {
                return DriverManager.getConnection(url, user, pwd);
            } catch (SQLException ex) {
                Logger.getLogger(SessionUtil.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SessionUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public SessionUtil getSessionFromDB (String session_id, String user_agent, String ip_address) {
        SessionUtil ss = null;
        Connection conn = this.getConnection();
        
        String sql = "SELECT * FROM Session WHERE session_id=? AND user_agent=? AND ip_address=?";
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        
        if (conn != null) {
            try {
                preStatement = conn.prepareStatement(sql);
                preStatement.setString(1, session_id);
                preStatement.setString(2, user_agent);
                preStatement.setString(3, ip_address);
                resultSet = preStatement.executeQuery();
                
                if (resultSet.next()) {
                    ss = new SessionUtil(resultSet.getString("session_id"), resultSet.getInt("user_id"), resultSet.getString("user_agent"), resultSet.getString("ip_address"));
                }
                
                conn.close();
                
                if (preStatement != null) {
                    preStatement.close();
                }
                
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SessionUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return ss;
        
    }
    
    public void addSessionToDB() {
        Connection conn = this.getConnection();
        
        String sql = "insert into Session(session_id, user_id, user_agent, ip_address) values(?,?,?,?)";
        PreparedStatement preStatement = null;
        
        if (conn != null) {
            try {
                preStatement = conn.prepareStatement(sql);
                preStatement.setString(1, this.session_id);
                preStatement.setInt(2, this.user_id);
                preStatement.setString(3, this.user_agent);
                preStatement.setString(4, this.ip_address);
                
                preStatement.executeUpdate();
                
                conn.close();
                
                if (preStatement != null) {
                    preStatement.close();
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public void updateSessionToDB() {
        Connection conn = this.getConnection();
        
        String sql = "UPDATE Session SET user_id=?, user_agent=?, ip_address=? WHERE session_id=?";
        PreparedStatement preStatement = null;
        
        if (conn != null) {
            try {
                preStatement = conn.prepareStatement(sql);
                preStatement.setInt(1, this.user_id);
                preStatement.setString(2, this.session_id);
                preStatement.setString(3, this.user_agent);
                preStatement.setString(4, this.ip_address);
                
                preStatement.executeUpdate();
                
                conn.close();
                
                if (preStatement != null) {
                    preStatement.close();
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public void removeSessionDB(String session_id) {
        Connection conn = this.getConnection();
        
        String sql = "DELETE FROM Session WHERE session_id=?";
        PreparedStatement preStatement = null;
        
        if (conn != null) {
            try {
                preStatement = conn.prepareStatement(sql);
                preStatement.setString(1, session_id);
                
                preStatement.executeUpdate();
                
                conn.close();
                
                if (preStatement != null) {
                    preStatement.close();
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
}
