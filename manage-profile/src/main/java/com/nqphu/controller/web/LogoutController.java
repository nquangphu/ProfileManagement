package com.nqphu.controller.web;

import com.nqphu.utils.SessionUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author phu
 */
@WebServlet(urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUtil ssUtil = new SessionUtil();
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie ck : cookies) {
                if (ck.getName().equals("session_id")) {
                    if (ck.getValue() != null) {
                        ssUtil.removeSessionDB(ck.getValue());

                        Cookie cookie = new Cookie("session_id", null);
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    }
                    break;
                }
            }

            resp.sendRedirect("api-login");
        }
    }

}
