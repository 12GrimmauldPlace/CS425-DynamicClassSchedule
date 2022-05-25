package edu.jsu.mcis.cs425.lab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DropServlet extends HttpServlet {


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int crn, termid, numericstudentid;
        
        response.setContentType("application/json;charset=UTF-8");
        
        BufferedReader br = null;
        
        RegistrationDatabase rdb; 
        
        boolean courseDropped;
        
        String jsonString;
        
        try (PrintWriter out = response.getWriter()){

            br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String p = URLDecoder.decode(br.readLine().trim(), Charset.defaultCharset());
            
            HashMap<String, String> parameters = new HashMap<>();
            
            String[] pairs = p.trim().split("&");
            
            for (int i= 0; i< pairs.length; ++i) {
                String[] pair = pairs[i].split("=");
                parameters.put(pair[0], pair[1]);
            }
            
            rdb = new RegistrationDatabase(); 
            
            crn = Integer.parseInt(parameters.get("crn"));
            termid = Integer.parseInt(parameters.get("termid")); 
            
            numericstudentid = rdb.getUsernameId(request.getRemoteUser()); 
            
            courseDropped = rdb.deleteRegistration(numericstudentid, termid, crn);
            
            System.out.println("success: " + courseDropped);
            
            jsonString = rdb.getResultSetAsJSONString(rdb.getStudentRegistration(numericstudentid, termid));
                    
            out.write(jsonString);
        
        }
        
        catch(Exception e) { e.printStackTrace(); } 
        
        
    }

    @Override
    public String getServletInfo() {
        return "The DropServlet allows students to drop a previous registration for a course. Accepts DELETE requests. ";
    }

}
