<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="databaseBean" class="edu.jsu.mcis.cs425.lab6.RegistrationDatabase" scope="session"/>
<jsp:useBean id="parameterBean" class="edu.jsu.mcis.cs425.lab6.ParameterBean" scope="session"/>
<%-- Set Bean Properties --%>
<jsp:setProperty name="parameterBean" property="*"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Class Schedule Listing</title>
    </head>
    <body>
        <header>
            <h1>Class Schedule Listing</h1>  
        </header>
        <p>You are logged in as: <%= request.getRemoteUser() %></p>
        <p>Termid:<%=parameterBean.getTermid()%></p>
        <p style="text-align: center">
                <input type="button" value="Return to Landing Page to Select a New Term" onclick="window.open('<%= request.getContextPath() %>/secure/index.jsp', '_self', false);" />
                 <input type="button" value="Return to Previous Page" onclick="window.open('<%= request.getContextPath() %>/secure/schedulesearch.jsp', '_self', false);" />
        </p>
        <p style="text-align: center"><input type="button" value="Logout" onclick="window.open('<%= request.getContextPath() %>/secure/logout.jsp', '_self', false);" /></p>

        
        
        
        <% 
         System.out.println("BEFORE PARAMETERS ARE CLEARED");
         System.out.println("Termid " + parameterBean.getTermid()); 
         System.out.println("Levelid " + parameterBean.getLevelid());
         System.out.println("Number " + parameterBean.getNum());
         System.out.println("Subject " + parameterBean.getSubjectid());
         System.out.println("Schedule Type " + parameterBean.getScheduletypeid());
         System.out.println("Start " + parameterBean.getStart());
         System.out.println("End " + parameterBean.getEnd());
         System.out.println("Days " + parameterBean.getDays()); 
         
        %>
       
        <%= databaseBean.getResultSetTable(databaseBean.getCourseList(databaseBean.getParameterHashMap(parameterBean)))%>
       
       
       <%-- Clear Bean Parameter Collection (while keeping termid until user changes it on landing page) --%>
       
        <% 
         parameterBean.clear(); 
         
         System.out.println("AFTER PARAMETERS ARE CLEARED");
         System.out.println("Termid " + parameterBean.getTermid()); 
         System.out.println("Levelid " + parameterBean.getLevelid());
         System.out.println("Number " + parameterBean.getNum());
         System.out.println("Subject " + parameterBean.getSubjectid());
         System.out.println("Schedule Type " + parameterBean.getScheduletypeid());
         System.out.println("Start " + parameterBean.getStart());
         System.out.println("End " + parameterBean.getEnd());
         System.out.println("Days " + parameterBean.getDays()); 
       %>
       
    </body>
</html>
