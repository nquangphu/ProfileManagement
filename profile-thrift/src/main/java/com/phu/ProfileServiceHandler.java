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
    public ProfileResult getProfile(int id) {

        // Sai requirement => error = 3
        if (id <= 0) {
            return new ProfileResult(null, 3);
        }

        Connection conn = this.getConnection();

        String sql = "SELECT * FROM Profile WHERE id=?";
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;

        // default result = null => error = 1
        ProfileResult result = new ProfileResult(null, 1);

        if (conn != null) {
            try {
                preStatement = conn.prepareStatement(sql);
                preStatement.setInt(1, id);
                resultSet = preStatement.executeQuery();

                if (resultSet.next()) {
                    Profile pro = new Profile();
                    pro.setId(resultSet.getInt("id"));
                    pro.setUsername(resultSet.getString("username"));
                    pro.setPwd(resultSet.getString("pwd"));
                    pro.setFullname(resultSet.getString("fullname"));
                    pro.setEmail(resultSet.getString("email"));
                    pro.setDob(resultSet.getString("dob"));
                    pro.setGender(resultSet.getString("gender"));
                    pro.setAddress(resultSet.getString("address"));
                    pro.setPhone(resultSet.getString("phone"));

                    // set result = 0 => success
                    result.setError(0);
                    result.setProfile(pro);
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

        return result;
    }

    @Override
    public MapResult multiGetProfile(List<Integer> lst_id) {
        // param = null => error = 2
        if (lst_id == null) {
            return new MapResult(null, 2);
        }

        // sai requirement (lst_id is empty) => error = 3
        if (lst_id.isEmpty()) {
            return new MapResult(null, 3);
        }

        String lstString = listToString(lst_id);

        Map<Integer, ProfileResult> profileMap = new HashMap<>();
        Connection conn = this.getConnection();
        String sql = "SELECT * from Profile where user_id IN (?)";

        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        Profile pro = null;

        if (conn != null) {
            try {
                preStatement = conn.prepareStatement(sql);
                preStatement.setString(1, lstString);
                resultSet = preStatement.executeQuery();

                while (resultSet.next()) {
                    pro = new Profile();
                    pro.setId(resultSet.getInt("id"));
                    pro.setUsername(resultSet.getString("username"));
                    pro.setPwd(resultSet.getString("pwd"));
                    pro.setFullname(resultSet.getString("fullname"));
                    pro.setEmail(resultSet.getString("email"));
                    pro.setDob(resultSet.getString("dob"));
                    pro.setGender(resultSet.getString("gender"));
                    pro.setAddress(resultSet.getString("address"));
                    pro.setPhone(resultSet.getString("phone"));

                    profileMap.put(pro.getId(), new ProfileResult(pro, 0));
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

        // result = null => error = 1
        if (profileMap.isEmpty()) {
            return new MapResult(null, 1);
        } else {
            // success => error = 0
            return new MapResult(profileMap, 0);
        }

    }

    @Override
    public ProfileResult findByUserNameAndPassword(String username, String password) {
        // params = null => error = 2
        if (username == null || password == null) {
            return new ProfileResult(null, 2);
        }

        // params is empty => error = 3
        if (username.equals("") || password.equals("")) {
            return new ProfileResult(null, 3);
        }

        Connection conn = this.getConnection();
        String sql = "select * from Profile where username = ? and pwd=MD5(?)";
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;

        // Default result = null => error = 1
        ProfileResult result = new ProfileResult(null, 1);

        if (conn != null) {
            try {
                preStatement = conn.prepareStatement(sql);
                preStatement.setString(1, username);
                preStatement.setString(2, password);

                resultSet = preStatement.executeQuery();

                if (resultSet.next()) {
                    Profile pro = new Profile();
                    pro.setId(resultSet.getInt("id"));
                    pro.setUsername(resultSet.getString("username"));
                    pro.setPwd(resultSet.getString("pwd"));
                    pro.setFullname(resultSet.getString("fullname"));
                    pro.setEmail(resultSet.getString("email"));
                    pro.setDob(resultSet.getString("dob"));
                    pro.setGender(resultSet.getString("gender"));
                    pro.setAddress(resultSet.getString("address"));
                    pro.setPhone(resultSet.getString("phone"));

                    result.setError(0);
                    result.setProfile(pro);
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

        return result;
    }

    @Override
    public int findByUserName(String username) {
        // params = null => return -2
        if (username == null) {
            return -2;
        }

        // params is empty => return -3
        if (username.equals("")) {
            return -3;
        }
        Connection conn = this.getConnection();

        String sql = "SELECT id FROM Profile WHERE username=?";
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        Profile pro = null;

        //Default result = -1 (Not found)
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
    public int save(Profile profile) {

        if (profile == null) {
            return -2;
        }

        Connection conn = this.getConnection();

        String sql = "insert into Profile (username, fullname, pwd, email, gender, dob, address, phone) values(?,?,MD5(?),?,?,?,?,?)";
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;

        //Default result = null (Not found)
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
    public void remove(int id) {

        if (id <= 0) {
            return;
        }

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

    public String listToString(List<Integer> lst_id) {
        String lstString = "";
        if (!lst_id.isEmpty()) {
            for (int i = 0; i < lst_id.size(); i++) {
                lstString += lst_id.get(i);
                if (i != lst_id.size() - 1) {
                    lstString += ", ";
                }
            }
        }

        return lstString;
    }

}
