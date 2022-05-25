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


public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int termid, numericstudentid;
        
        response.setContentType("application/json");
        
        String jsonString;
        
        RegistrationDatabase rdb;
        
        try(PrintWriter out = response.getWriter();){

            // Get Reference to Registration Database 
            rdb = new RegistrationDatabase();
            
            // Get termid from client
            termid = Integer.parseInt(request.getParameter("termid")); 
            
            // Get student id
            numericstudentid = rdb.getUsernameId(request.getRemoteUser()); 
            
            // Convert to JSON object
            jsonString = rdb.getResultSetAsJSONString(rdb.getStudentRegistration(numericstudentid, termid));
                    
            // Print to console
            System.out.println(jsonString);
            
            // Send to client
            out.write(jsonString);  
        }
       
        catch (Exception e) { 
            e.printStackTrace(); 
        }
   
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int crn, termid, numericstudentid;
        
        BufferedReader br = null;
        
        response.setContentType("application/json;charset=UTF-8");
        
        String jsonString;
        
        RegistrationDatabase rdb;
        
        try(PrintWriter out = response.getWriter()){
            
            br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String p = URLDecoder.decode(br.readLine().trim(), Charset.defaultCharset());
            
            HashMap<String, String> parameters = new HashMap<>();
            
            String[] pairs = p.trim().split("&");
            
            for (int i= 0; i< pairs.length; ++i) {
                String[] pair = pairs[i].split("=");
                parameters.put(pair[0], pair[1]);
            }
            
            // Get Reference to Registration Database 
            rdb = new RegistrationDatabase();

            // Get parameters from client
            crn = Integer.parseInt(parameters.get("crn")); 
            termid = Integer.parseInt(parameters.get("termid")); 

            // Get user id
            numericstudentid = rdb.getUsernameId(request.getRemoteUser());

            // Register student for a course
            rdb.registerStudent(numericstudentid, termid, crn);
                
            // Get updated student courses as JSON object
            jsonString = rdb.getResultSetAsJSONString(rdb.getStudentRegistration(numericstudentid, termid));
            
            // Print to console
            System.out.println(jsonString);
            
            // Send to client
            out.write(jsonString); 
 
        }
        
        catch(Exception e) { e.printStackTrace(); } 
        
    }

    @Override
    public String getServletInfo() {
        return "The RegisterServlet registers students for courses. Accepts PUT requests.";
    }

}
