<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="databaseBean" class="edu.jsu.mcis.cs425.lab6.RegistrationDatabase" scope="session"/>
<jsp:useBean id="parameterBean" class="edu.jsu.mcis.cs425.lab6.ParameterBean" scope="session"/>
<%-- Set Bean Properties --%>
<jsp:setProperty name="databaseBean" property="*" />

<!DOCTYPE html>
<html>
    
    <head>
        
        <title>Search By Term</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
    </head>
    
    <body>
        <header style="background-color: red; padding:10px; color: white; margin-top:10px; margin-bottom:10px;">
            <p>Welcome, <%= request.getRemoteUser() %>!</p>
            <h1 style="text-align: center">Jacksonville State University </br>Dynamic Schedule</h1>
        </header>

            <form style="text-align: center"  action="<%= request.getContextPath() %>/secure/schedulesearch.jsp" method="GET"> 
                <fieldset>
                    <legend style="font-weight:bold; font-size:1.5em;">Select a Term to Search Course Offerings and Register, Drop, and View Your Courses</legend>
                    
                    <%= databaseBean.getDropdownListofTerms()%>
                    
                    <p>
                        <input type="submit" value="Submit" />
                        <input type="reset" value="Reset"/>
                    </p>
                </fieldset>
            </form>
                    
   
    <p style="text-align: center"><input  type="button" value="Logout" onclick="window.open('<%= request.getContextPath() %>/secure/logout.jsp', '_self', false);" /></p>
        
    </body>
</html>

