
package com.phu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;

/**
 *
 * @author phu
 */
public class ProfileServiceHandler implements ProfileThriftService.Iface {

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/ManageProfile";
            String user = "root";
            String pwd = "quangphu2805";
            try {
                return DriverManager.getConnection(url, user, pwd);
            } catch (SQLException ex) {
                Logger.getLogger(ProfileServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProfileServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public ProfileThrift getProfile(int id) throws TException {
        Connection conn = this.getConnection();

        String sql = "SELECT * FROM Profile WHERE id=?";
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        ProfileThrift pro = null;
        if (conn != null) {
            try {
                preStatement = conn.prepareStatement(sql);
                preStatement.setInt(1, id);
                resultSet = preStatement.executeQuery();

                if (resultSet.next()) {
                    pro = new ProfileThrift();
                    pro.setId(resultSet.getInt("id"));
                    pro.setUsername(resultSet.getString("username"));
                    pro.setPwd(resultSet.getString("pwd"));
                    pro.setFullname(resultSet.getString("fullname"));
                    pro.setEmail(resultSet.getString("email"));
                    pro.setDob(resultSet.getString("dob"));
                    pro.setGender(resultSet.getString("gender"));
                    pro.setAddress(resultSet.getString("address"));
                    pro.setPhone(resultSet.getString("phone"));
                }

                conn.close();

                if (preStatement != null) {
                    preStatement.close();
                }

                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProfileServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return pro;
    }

    @Override
    public Map<Integer, ProfileThrift> multiGetProfile(List<Integer> lst_id) throws TException {
        Map<Integer, ProfileThrift> result = new HashMap<>();

        for (int i : lst_id) {
            result.put(i, this.getProfile(i));
        }

        return result;
    }

    @Override
    public ProfileThrift findByUserNameAndPassword(String username, String password) throws TException {
        Connection conn = this.getConnection();

        String sql = "SELECT * FROM Profile WHERE username=? AND pwd=?";
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        ProfileThrift pro = null;
        if (conn != null) {
            try {
                preStatement = conn.prepareStatement(sql);
                preStatement.setString(1, username);
                preStatement.setString(2, password);
                resultSet = preStatement.executeQuery();

                if (resultSet.next()) {
                    pro = new ProfileThrift();
                    pro.setId(resultSet.getInt("id"));
                    pro.setUsername(resultSet.getString("username"));
                    pro.setPwd(resultSet.getString("pwd"));
                    pro.setFullname(resultSet.getString("fullname"));
                    pro.setEmail(resultSet.getString("email"));
                    pro.setDob(resultSet.getString("dob"));
                    pro.setGender(resultSet.getString("gender"));
                    pro.setAddress(resultSet.getString("address"));
                    pro.setPhone(resultSet.getString("phone"));
                }

                conn.close();

                if (preStatement != null) {
                    preStatement.close();
                }

                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProfileServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return pro;
    }

    @Override
    public int findByUserName(String username) throws TException {
        Connection conn = this.getConnection();

        String sql = "SELECT id FROM Profile WHERE username=?";
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        ProfileThrift pro = null;
        
        int id_result = -1;
        if (conn != null) {
            try {
                preStatement = conn.prepareStatement(sql);
                preStatement.setString(1, username);
                resultSet = preStatement.executeQuery();

                if (resultSet.next()) {
                    id_result = resultSet.getInt("id");
                }

                conn.close();

                if (preStatement != null) {
                    preStatement.close();
                }

                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProfileServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return id_result;
    }

    @Override
    public int save(ProfileThrift profile) throws TException {
        Connection conn = this.getConnection();

        String sql = "insert into Profile (username, fullname, pwd, email, gender, dob, address, phone) values(?,?,?,?,?,?,?,?)";
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        int id_result = -1;
        
        if (conn != null) {
            try {
                preStatement = conn.prepareStatement(sql);
                preStatement.setString(1, profile.getUsername());
                preStatement.setString(2, profile.getFullname());
                preStatement.setString(3, profile.getPwd());
                preStatement.setString(4, profile.getEmail());
                preStatement.setString(5, profile.getGender());
                preStatement.setString(6, profile.getDob());
                preStatement.setString(7, profile.getAddress());
                preStatement.setString(8, profile.getPhone());

                preStatement.executeUpdate();
                
                sql = "select id from Profile where username =?";
                preStatement = conn.prepareStatement(sql);
                preStatement.setString(1, profile.getUsername());
                resultSet = preStatement.executeQuery();
                
                if (resultSet.next()) {
                    id_result = resultSet.getInt("id");
                }
                
                conn.close();

                if (preStatement != null) {
                    preStatement.close();
                }
                
                if (resultSet != null) {
                    resultSet.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return id_result;
    }

    @Override
    public void remove(int id) throws TException {
        Connection conn = this.getConnection();

        String sql = "DELETE FROM Profile WHERE id=?";
        PreparedStatement preStatement = null;

        if (conn != null) {
            try {
                preStatement = conn.prepareStatement(sql);
                preStatement.setInt(1, id);

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

    @Override
    public void setProfile(ProfileThrift p) throws TException {
    }

}
