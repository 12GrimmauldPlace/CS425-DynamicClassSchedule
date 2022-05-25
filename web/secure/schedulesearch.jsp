<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="databaseBean" class="edu.jsu.mcis.cs425.lab6.RegistrationDatabase" scope="session"/>
<jsp:useBean id="parameterBean" class="edu.jsu.mcis.cs425.lab6.ParameterBean" scope="session"/>
<%-- Set Bean Properties --%>
<jsp:setProperty name="parameterBean" property="*"/>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="scripts/jquery-3.6.0.min.js"></script>
        <script type="text/javascript" src="scripts/ScheduleSearch.js"></script>
        
        <title>Class Schedule Search</title>
    </head>
    <body>
        <header style="background-color: #F47174; padding:10px; color: white; margin-top:10px; margin-bottom:10px;">
            <p>You are logged in as: <%= request.getRemoteUser() %></p>
            <p>Termid:<%=parameterBean.getTermid()%></p>
            <h1>Class Schedule Search Page</h1>
            <h3>To search the dynamic course schedule on this page, use the form below. To add, drop, or view your courses instead, click on "Go to registration page".</h3>
        </header>
        <p>
            <input type="button" value="Return to Landing Page" onclick="window.open('<%= request.getContextPath() %>/secure/index.jsp', '_self', false);" />
        </p>
        
                <form action="register.jsp" method="GET"> 
                <fieldset>
                    <legend style="font-weight:bold; font-size:1.5em;">Add, drop, or view your courses:</legend>
                    
                    <p>
                        <input type="submit" value="Go to registration page""/>
                    </p>
  
                </fieldset>
            </form>
        
        <form action="schedulelisting.jsp" method="GET"> 
                <fieldset>
                    <legend style="font-weight:bold; font-size:1.5em;">Search the course catalog:</legend>
                        
                    <p><label for="subjectid">Subject:</label></p>    
                    <p> <%= databaseBean.getDropdownListofSubjects(parameterBean)%> </p>
                    
                    <p>
                        <label for="num">Course Number:</label>
                        <input type="text" size="6" maxlength="5" name="num" id="num"> 
                    </p>
                    
                    
                    <p><label for="scheduletypeid">Schedule Type:</label></p>
                    <p><%= databaseBean.getDropdownListofScheduleTypes(parameterBean)%> </p>
                    
                     <p><label for="levelid">Course Level:</label></p>
                    <p><%= databaseBean.getDropdownListofCourseLevels()%> </p>

                     <p>Start Time: </p>
                     <label for="starthour">Hour</label>
                        <select name="starthour" id="starthour" class="starttime">
                            <option value="00" selected> 00</option>
                            <option value="01"> 01</option>
                            <option value="02"> 02</option>
                            <option value="03"> 03</option>
                            <option value="04"> 04</option>
                            <option value="05"> 05</option>
                            <option value="06"> 06</option>
                            <option value="07"> 07</option>
                            <option value="08"> 08</option>
                            <option value="09"> 09</option>
                            <option value="10"> 10</option>
                            <option value="11"> 11</option>
                            <option value="12"> 12</option>
                        </select>
                     <label for="startminute">Minute</label>
                        <select name="startminute" id="startminute" class="starttime">
                            <option value="00" selected> 00</option>
                            <option value="05"> 05</option>
                            <option value="10"> 10</option>
                            <option value="15"> 15</option>
                            <option value="20"> 20</option>
                            <option value="25"> 25</option>
                            <option value="30"> 30</option>
                            <option value="35"> 35</option>
                            <option value="40"> 40</option>
                            <option value="45"> 45</option>
                            <option value="50"> 50</option>
                            <option value="55"> 55</option>
                        </select>
                     <label for="startampm">am/pm</label>
                        <select name="startampm" id="startampm" class="starttime">
                            <option value="a" selected>am</option>
                            <option value="p">pm</option>
                        </select>
                     
                     
                     
                     <p>End Time: </p>
                     <label for="endhour">Hour</label>
                        <select name="endhour" id="endhour" class="endtime">
                            <option value="00" selected> 00</option>
                            <option value="01"> 01</option>
                            <option value="02"> 02</option>
                            <option value="03"> 03</option>
                            <option value="04"> 04</option>
                            <option value="05"> 05</option>
                            <option value="06"> 06</option>
                            <option value="07"> 07</option>
                            <option value="08"> 08</option>
                            <option value="09"> 09</option>
                            <option value="10"> 10</option>
                            <option value="11"> 11</option>
                            <option value="12"> 12</option>
                        </select>
                     <label for="endminute">Minute</label>
                        <select name="endminute" id="endminute" class="endtime">
                            <option value="00" selected> 00</option>
                            <option value="05"> 05</option>
                            <option value="10"> 10</option>
                            <option value="15"> 15</option>
                            <option value="20"> 20</option>
                            <option value="25"> 25</option>
                            <option value="30"> 30</option>
                            <option value="35"> 35</option>
                            <option value="40"> 40</option>
                            <option value="45"> 45</option>
                            <option value="50"> 50</option>
                            <option value="55"> 55</option>
                        </select>
                     <label for="endampm">am/pm</label>
                        <select name="endampm" id="endampm" class="endtime">
                            <option value="a" selected>am</option>
                            <option value="p">pm</option>
                        </select>
                    
                     <input type="hidden" id="start" name="start">
                     <input type="hidden" id="end" name="end">
                     
                    <p>Days: </p>
                    <input type="checkbox"  id="m" name="m" class="checkbox" value="M">
                    <label for="m"> Mon </label>
                    
                    <input type="checkbox" id="t" name="t" class="checkbox" value="T">
                    <label for="t"> Tue </label>
                    
                    <input type="checkbox" id="w" name="w" class="checkbox" value="W">
                    <label for="w"> Wed </label>
                    
                    <input type="checkbox" id="r" name="r" class="checkbox" value="R">
                    <label for="r"> Thu </label>
                    
                    <input type="checkbox" id="f" name="f" class="checkbox" value="F">
                    <label for="f"> Fri </label>
                    
                    <input type="checkbox" id="s" name="s" class="checkbox" value="S">
                    <label for="s"> Sat </label>
                    
                    <input type="checkbox" id="u" name="u" class="checkbox" value="U">
                    <label for="u"> Sun </label>

                    <input type="hidden" id="days" name="days">
                    
                    <p>
                        <input type="submit" value="Class Search" onclick="return schedulesearch.validateSelection();"/>
                        <input type="reset" value="Reset"/>
                    </p>
  
                </fieldset>
            </form>
                    
            <p>        
                <input type="button" value="Logout" onclick="window.open('<%= request.getContextPath() %>/secure/logout.jsp', '_self', false);" />
            </p>
            
            <script>

            $('input[type="checkbox"]').on('change', function() {
                schedulesearch.addOrRemoveDay();
            }).change();
            
            $('.starttime').change(function () {
                var starthour = $('#starthour').val();
                if(starthour !== "00"){
                    var startminute = $('#startminute').val();
                    var startampm = $('#startampm').val();
                    var start = schedulesearch.get24HourTime(starthour, startminute, startampm);
                    console.log("start "+start);
                    $("#start").val(start);
                }
                else{
                    $("#start").val("");
                    console.log(starthour);
                }
            }).change();
            
            $('.endtime').change(function () {
                var endhour = $('#endhour').val();
                if(endhour !== "00"){
                    var endminute = $('#endminute').val();
                    var endampm = $('#endampm').val();
                    var end = schedulesearch.get24HourTime(endhour, endminute, endampm);
                    console.log("end "+ end);
                    $("#end").val(end);
                }
                else{
                    $("#end").val("");
                    console.log(endhour);
                }
            }).change();
            
            $(document).ready(function(){
                $("#num").val("");
                $("#start").val("");
                $("#end").val("");
            });
           
            </script> 
    </body>
</html>
