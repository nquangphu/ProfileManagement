package com.nqphu.controller.web.api;

import com.nqphu.model.ProfileModel;
import com.nqphu.service.ProfileService;
import com.nqphu.utils.HttpUtil;
import com.nqphu.utils.SessionUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author phu
 */
@WebServlet(urlPatterns = {"/api-register"})
public class RegisterApi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("views/web/register.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProfileService profileService = new ProfileService();
        // ObjectMapper mapper = new ObjectMapper();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        BufferedReader br;
        br = req.getReader();
        HttpUtil of = HttpUtil.of(br);
        ProfileModel pro = of.toModel(ProfileModel.class);
        // mapper.writeValue(resp.getOutputStream(), pro);
        
        String username = pro.getUsername();
        int id = profileService.findByUserName(username);
        
        if (id >= 0) {
            PrintWriter bw = resp.getWriter();
            bw.write("{\"error\":0}");
        } else {
            id = profileService.save(pro);
            pro = profileService.getProfile(id);
            
            HttpSession session = req.getSession();
            String ssId = session.getId();
            
            int userId = pro.getId();
            session.setAttribute("userId", userId);
            
            String userAgent = req.getHeader("User-Agent");
            session.setAttribute("UserAgent", userAgent);
            
            String ipAddress = req.getRemoteAddr();
            session.setAttribute("IPAddress", ipAddress);
            
            SessionUtil ssUtil = new SessionUtil(ssId, userId, userAgent, ipAddress);
            ssUtil.addSessionToDB();

            Cookie cookie = new Cookie("session_id", ssId);
            cookie.setMaxAge(600);
            resp.addCookie(cookie);

            PrintWriter bw = resp.getWriter();
            bw.write("{\"error\":1}");
        }
    }

}
