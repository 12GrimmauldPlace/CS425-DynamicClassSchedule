<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="databaseBean" class="edu.jsu.mcis.cs425.lab6.RegistrationDatabase" scope="session"/>
<jsp:useBean id="parameterBean" class="edu.jsu.mcis.cs425.lab6.ParameterBean" scope="session"/>
<jsp:setProperty name="databaseBean" property="*"/>
<jsp:setProperty name="parameterBean" property="*"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="scripts/ajaxrequests.js"></script>
        <script type="text/javascript" src="scripts/jquery-3.6.0.min.js"></script>
        
        <title>Registration Page</title>
    </head>
    <body>
        <header style="background-color: #F47174; padding:10px; color: white; margin-top:10px; margin-bottom:10px;">
            <p>You are logged in as: <%= request.getRemoteUser() %>!</p>
            <p>Termid: <%= parameterBean.getTermid() %></p>
            <h1 style="text-align: center">Jacksonville State University </br>Student Registration and Course Schedule Page</h1>
        </header>
        
        <p>
            <input type="button" value="Return to Landing Page" onclick="window.open('<%= request.getContextPath() %>/secure/index.jsp', '_self', false);" />
            <input type="button" value="Return to Previous Page" onclick="window.open('<%= request.getContextPath() %>/secure/schedulesearch.jsp', '_self', false);" />
        </p>
        <p>        
            <input type="button" value="Logout" onclick="window.open('<%= request.getContextPath() %>/secure/logout.jsp', '_self', false);" />
        </p>
        
        <form> 
                <fieldset>
                    <legend style="font-weight:bold; font-size:1.5em;">Register for Courses</legend>
                    
                    <p>
                        <label for="crn">Enter CRN:</label>
                        <input type="text" onfocus="this.value=''" size="6" maxlength="6" name="crn" id="crn" required="required"> 
                    </p>

                    <p>
                        <input type="button" value="Register" onclick="request.registerCourse(<%= parameterBean.getTermid() %>)"/>
                        <input type="button" value="Drop Course" onclick="request.dropCourse(<%= parameterBean.getTermid() %>)"/>
                        <input type="reset" value="Reset"/>
                    </p>
  
                </fieldset>
        </form>
        
        <h3>Student Schedule:</h3>
        
        <div id="registeredcourses"></div> 
                    
        <script> 
            request.getCourses(<%=  parameterBean.getTermid() %>); 
        </script>
    </body>
</html>
