package com.nqphu.controller.web;

import com.nqphu.service.ProfileService;
import com.nqphu.utils.SessionUtil;
import com.phu.ProfileResult;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/profile-detail-page"})
public class ProfileDetailController extends HttpServlet {

    private final ProfileService profileService = new ProfileService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SessionUtil ss = new SessionUtil();

        String userAgent = req.getHeader("User-Agent");
        String ipAddress = req.getRemoteHost();

        // Check session exists in database?
        Cookie[] cookies = req.getCookies();
        boolean bl = false;

        if (cookies != null) {
            for (Cookie ck : cookies) {
                if (ck.getName().equals("session_id")) {
                    String session_id = ck.getValue();

                    if (session_id != null) {
                        SessionUtil ssUtil = ss.getSessionFromDB(session_id, userAgent, ipAddress);

                        if (ssUtil != null) {
                            int id = ssUtil.getUser_id();
                            ProfileResult result = profileService.getProfile(id);

                            if (result.getError() == 0) {
                                req.setAttribute("profile", result.getProfile());
                                req.getRequestDispatcher("views/web/profile-detail.jsp").forward(req, resp);

                                bl = true;
                            }

                        }

                        break;
                    }
                }
            }
        }

        if (bl == false) {
            resp.sendRedirect("api-login");
        }
    }

}
