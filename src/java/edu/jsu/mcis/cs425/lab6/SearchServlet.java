package edu.jsu.mcis.cs425.lab6;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Parameter Map
        HashMap<String, String> parameters = new HashMap<>(); //could also get query string using .getQueryString();

        RegistrationDatabase rdb; 
        
        String jsonString;
        
        response.setContentType("application/json");
        
        try(PrintWriter out = response.getWriter()){

            rdb = new RegistrationDatabase(); 
            
            parameters.put("termid", request.getParameter("termid"));
            parameters.put("levelid", request.getParameter("levelid"));
            parameters.put("subjectid", request.getParameter("subjectid"));
            parameters.put("num", request.getParameter("num"));
            parameters.put("scheduletypeid", request.getParameter("scheduletypeid"));
            parameters.put("start", request.getParameter("start"));
            parameters.put("end", request.getParameter("end"));
            parameters.put("days", request.getParameter("days"));
            
            // Get ResultSet object and convert it to a JSON Object
            jsonString = rdb.getResultSetAsJSONString(rdb.getCourseList(parameters));
            System.out.println(jsonString);
            
            // Send response with JSON object to client
            out.write(jsonString);   
            
        } // End Try block
 
       catch(Exception e) { e.printStackTrace(); }     
    }

    @Override
    public String getServletInfo() {
        return "The SearchServlet allows students to search for courses. Accepts GET requests.";
    }

}

