
package com.nqphu.controller.web.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nqphu.service.ProfileService;
import com.nqphu.utils.HttpUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author phu
 */
@WebServlet(urlPatterns = {"/api-remove"})
public class RemoveApi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProfileService profileService = new ProfileService();

        ObjectMapper mapper = new ObjectMapper();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        BufferedReader br;
        br = req.getReader();
        HttpUtil httpUtil = HttpUtil.of(br);
        String json = httpUtil.getValue();

        Map<String, ArrayList<Integer>> readValues = mapper.readValue(json, new TypeReference<Map<String, ArrayList<Integer>>>() {
        });

        List<Integer> lst = new ArrayList<>();
        for (Map.Entry m : readValues.entrySet()) {
            if (m.getKey().toString().equals("list_id")) {
                lst = (ArrayList<Integer>) m.getValue();
                mapper.writeValue(resp.getOutputStream(), lst);
            }
        }

        for (int i : lst) {
            profileService.remove(i);
        }

    }

}
