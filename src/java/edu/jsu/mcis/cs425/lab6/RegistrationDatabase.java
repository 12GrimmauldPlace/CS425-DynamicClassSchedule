package edu.jsu.mcis.cs425.lab6;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.sql.*;
import org.json.simple.*;

public class RegistrationDatabase {


    Context envContext = null, initContext = null;
    DataSource ds = null;
    Connection conn = null;

    public RegistrationDatabase() {
        
        try {
            
            envContext = new InitialContext();
            initContext = (Context)envContext.lookup("java:/comp/env");
            ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();
            
        }        
        catch (Exception e) { e.printStackTrace(); }

    }

    public void closeConnection() {
        
        if (conn != null) {
            
            try {
                conn.close();
            }            
            catch (Exception e) { e.printStackTrace(); }
            
        }
    
    }
    
     public Connection getConnection() { return conn; }  
     
     
     
    /* Called from SearchServlet */
    public ResultSet getCourseList(HashMap<String, String> parameters){

        ResultSet resultset = null; 
        PreparedStatement pstatement;
        String sql; 
        boolean hasresults; 

        try {
            
            sql = "SELECT term.name, term.start, term.end, section.*,  scheduletype.description, course.description, course.credits, level.description, subject.name " 
                    + "FROM section " 
                    + "JOIN scheduletype ON scheduletype.id = section.scheduletypeid "
                    + "JOIN course ON (course.subjectid = section.subjectid AND course.num = section.num) "
                    + "JOIN term ON term.id = section.termid "
                    + " JOIN level ON level.id = course.levelid " 
                    + " JOIN subject ON subject.id = course.subjectid "
                    + " WHERE ((section.days REGEXP ?  AND (section.days NOT LIKE 'TBA')) OR ? is null) " 
                    + " AND (course.levelid = ? or ? is null) AND (subject.id = ? or ? is null) " 
                    + " AND (section.num = ? or ? is null) AND (scheduletype.id = ? or ? is null) " 
                    + " AND (section.start >= ? or ? is null) AND (section.end <= ? or ? is null) "
                    + " AND (term.id = ? or ? is null) "
                    + "ORDER BY section.subjectid, section.num, section.section ASC"; 
            
            pstatement = this.conn.prepareStatement(sql);
            
            String days = parameters.get("days");
            String daysregex;
            if(days != null){
                String[] daysarray = days.split("");
                daysregex = "";
                for (int i = 0; i < days.length(); i++){
                    if (i != days.length()-1){
                        daysregex = daysregex + daysarray[i] + "|";
                    }
                    else{
                        daysregex = daysregex + daysarray[i];
                    }
                }
            }
            else{
                daysregex = null;
            }
            pstatement.setString(1, daysregex);                    
            pstatement.setString(2, daysregex);
            
            
            
            
            if(parameters.get("levelid") == null){
                pstatement.setString(3, null);
                pstatement.setString(4, null);
            }
            else{
                pstatement.setInt(3, Integer.parseInt(parameters.get("levelid"))); //NULL POINTER EXCEPTION WHEN PARSE TO INT
                pstatement.setInt(4, Integer.parseInt(parameters.get("levelid"))); //NULL POINTER EXCEPTION  WHEN PARSE TO INT              
            }
            
            
            pstatement.setString(5, parameters.get("subjectid"));
            pstatement.setString(6, parameters.get("subjectid"));
            
            
            pstatement.setString(7, parameters.get("num"));
            pstatement.setString(8, parameters.get("num"));
            
            
            pstatement.setString(9, parameters.get("scheduletypeid"));
            pstatement.setString(10, parameters.get("scheduletypeid"));
            
            
            
            if(parameters.get("start") == null){
                pstatement.setTime(11, null); // NULL POINTER EXCEPTION java.util.Objects.requireNonNull(
                pstatement.setTime(12, null);
            }
            else{
                pstatement.setTime(11, Time.valueOf(LocalTime.parse(parameters.get("start")))); // NULL POINTER EXCEPTION java.util.Objects.requireNonNull(
                pstatement.setTime(12, Time.valueOf(LocalTime.parse(parameters.get("start")))); // NULL POINTER EXCEPTION 
            }
            
            
            if(parameters.get("end") == null){
                pstatement.setTime(13, null); 
                pstatement.setTime(14, null); 
            }
            else{
                pstatement.setTime(13, Time.valueOf(LocalTime.parse(parameters.get("end")))); // NULL POINTER EXCEPTION 
                pstatement.setTime(14, Time.valueOf(LocalTime.parse(parameters.get("end")))); // NULL POINTER EXCEPTION
            }
            
            
            if(parameters.get("termid") == null){
                pstatement.setString(15, null);
                pstatement.setString(16, null);
            }
            else{
                pstatement.setInt(15, Integer.parseInt(parameters.get("termid"))); //NULL POINTER EXCEPTION WHEN PARSE TO INT
                pstatement.setInt(16, Integer.parseInt(parameters.get("termid"))); //NULL POINTER EXCEPTION  WHEN PARSE TO INT
            }
            
            
            
            
            hasresults = pstatement.execute();
            
            while ( hasresults || pstatement.getUpdateCount() != -1 ) {
                if ( hasresults ) {
                    resultset = pstatement.getResultSet();
                    return resultset; 
                }
                else {
                    if ( pstatement.getUpdateCount() == -1 ) {
                        break;
                    } 
                }

                hasresults = pstatement.getMoreResults();
            }
        }
        
        catch(SQLException e) { e.printStackTrace(); }

        return resultset; 
        
    }
    
    
    /* Called from RegisterServlet */
     public int getUsernameId(String currentusername){

        ResultSet resultset; 
        PreparedStatement pstatement;
        String sql; 
        boolean hasresults; 
        int id = -1;

        try {
            
            sql = "SELECT id from student WHERE username = ?";
            
            pstatement = this.conn.prepareStatement(sql);
            pstatement.setString(1, currentusername);
            
            hasresults = pstatement.execute();
            
            while ( hasresults || pstatement.getUpdateCount() != -1 ) {
                if ( hasresults ) {
                    resultset = pstatement.getResultSet();
                    resultset.next();
                    id = resultset.getInt("id");
                    return id; 
                }
                else {
                    if ( pstatement.getUpdateCount() == -1 ) {
                        break;
                    } 
                }

                hasresults = pstatement.getMoreResults();
            }
        }
        
        catch(SQLException e) { e.printStackTrace(); }

        return id; 
        
    }
    

     /* Called from RegisterServlet */
     public void registerStudent(int numericstudentid, int termid, int crn){
        
        PreparedStatement pstatement; 
        String sql;
        
        
        try {
            sql = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)"; 
            
            pstatement = this.conn.prepareStatement(sql);
            
            pstatement.setInt(1, numericstudentid); 
            pstatement.setInt(2, termid);
            pstatement.setInt(3, crn);
            
            pstatement.executeUpdate(); 

        }
        catch(SQLException e) { e.printStackTrace(); }
        
    
     }
     
     
     /* Called from RegisterServlet */
     public ResultSet getStudentRegistration(int numericstudentid, int termid){

        ResultSet resultset = null; 
        PreparedStatement pstatement;
        String sql; 
        boolean hasresults; 

        try {
            
            sql = "SELECT registration.crn, section.subjectid, section.num, section.section, level.description, course.credits, course.description "  
                + "FROM registration JOIN section "
                + "ON registration.crn = section.crn " 
                + " JOIN course "  
                + " ON (section.subjectid = course.subjectid AND section.num = course.num) " 
                + " JOIN level "
                + " ON course.levelid = level.id "
                + " WHERE (registration.studentid = ? AND registration.termid = ?) "
                + " ORDER BY registration.crn";
            
            pstatement = this.conn.prepareStatement(sql);
            pstatement.setInt(1, numericstudentid);
            pstatement.setInt(2, termid);
            
            hasresults = pstatement.execute();
            
            while ( hasresults || pstatement.getUpdateCount() != -1 ) {
                if ( hasresults ) {
                    resultset = pstatement.getResultSet();
                    return resultset;
                }
                else {
                    if ( pstatement.getUpdateCount() == -1 ) {
                        break;
                    } 
                }

                hasresults = pstatement.getMoreResults();
            }
        }
        
        catch(SQLException e) { e.printStackTrace(); }

        return resultset; 
        
    }
     
     
    /* Called from DropServlet */
     public boolean deleteRegistration(int numericstudentid, int termid, int crn){
        
        PreparedStatement pstatement; 

        String query;
        
        int rowsAffected = 0;
        
        try {
            
            query = "DELETE FROM registration WHERE studentid = ? AND termid = ? AND crn = ?"; 
            
            pstatement = this.conn.prepareStatement(query);
            
            pstatement.setInt(1, numericstudentid); 
            pstatement.setInt(2, termid); 
            pstatement.setInt(3, crn); 
            
            rowsAffected = pstatement.executeUpdate(); 
            
        }
        
        catch(Exception e) { e.printStackTrace(); }
        
        return (rowsAffected == 1);
    }
     
     

     
     
     
    public String getResultSetAsJSONString(ResultSet resultset) {

        String results = "";
        
        try {
            
            ArrayList<LinkedHashMap> records = new ArrayList<>();
            ResultSetMetaData metadata = resultset.getMetaData();

            int numberOfColumns = metadata.getColumnCount();

            while (resultset.next()) {

                LinkedHashMap<String, Object> record = new LinkedHashMap<>();

                for (int i = 1; i <= numberOfColumns; i++) {

                    String tablename = metadata.getTableName(i);
                    String key = tablename + "." + metadata.getColumnLabel(i);
                    
                    int type = metadata.getColumnType(i);

                    if (type == java.sql.Types.INTEGER || type == java.sql.Types.TINYINT)
                        record.put(key, resultset.getInt(key));
                    
                    else if (type == java.sql.Types.VARCHAR || type == java.sql.Types.CHAR)
                        record.put(key, resultset.getString(key));
                    
                    else if (type == java.sql.Types.TIME)
                        record.put(key, resultset.getTime(key).toLocalTime().toString());  
                    
                    else if (type == java.sql.Types.DATE){
                        record.put(key, resultset.getDate(key).toLocalDate().toString());  
                    }
                }
                records.add(record);
            }
            results = JSONValue.toJSONString(records);  
        }
        catch (Exception e) { e.printStackTrace(); }
        return results;
    } 
    

    
    
    
    
    /* *********************** CS 415 LAB 7 ************************ */
    
    public ArrayList<HashMap> getTerms(){
        
        ArrayList<HashMap> termList = null;
        HashMap<String, String> terms = null;
        
                try{
                    
                   String sql = "select * from term ORDER BY name";
                   PreparedStatement pstatement = this.conn.prepareStatement(sql);
                   
                   boolean hasresults = pstatement.execute();
                   
                   if ( hasresults ) {
                   
                        termList = new ArrayList<>();
                            
                        ResultSet resultset = pstatement.getResultSet();
                            
                        while(resultset.next()){
                            
                            terms = new HashMap<>();
                            
                            String id = String.valueOf(resultset.getInt("id"));
                            String name = resultset.getString("name");
                            
                            terms.put("id", id);
                            terms.put("name", name);
                            
                            termList.add(terms);
                        } 
                        
                       
                    }
                }
                catch(SQLException e){ e.printStackTrace(); }
                
                return termList;
    } // end getTerms()
    
    
    public String getDropdownListofTerms(){
        
        ArrayList<HashMap> termList = getTerms();
        
        StringBuilder s = new StringBuilder();
        
        String selectOpenTag = "<select name=\"termid\" id=\"termid\" required=\"required\">";
        String defaultOption = "<option value=\"\" selected>Select Term</option>";
        String selectClosingTag = "</select>";

        s.append(selectOpenTag).append(defaultOption);
        
        for (HashMap<String,String> term : termList) {
            
            int id = Integer.parseInt(term.get("id"));
            String name = term.get("name");
            
            String htmlString = "<option " + "value=\"" + id + "\">" + name + "</option>";
            
            s.append(htmlString);
        }
        s.append(selectClosingTag);
        System.out.println(s.toString());
        return s.toString();
    }
    
    
    
    
    
  public ArrayList<HashMap> getSubjects(ParameterBean pb){
        
        ArrayList<HashMap> subjectList = null;
        HashMap<String, String> subjects = null;
        int termid = Integer.parseInt(pb.getTermid());
        
                try{
                    
                   String sql = "SELECT DISTINCT(section.subjectid), subject.name FROM subject JOIN section ON subject.id=section.subjectid WHERE section.termid=? ORDER BY subject.name;";
                   PreparedStatement pstatement = this.conn.prepareStatement(sql);
                   pstatement.setInt(1, termid);
                   
                   boolean hasresults = pstatement.execute();
                   
                   if ( hasresults ) {
                   
                        subjectList = new ArrayList<>();
                            
                        ResultSet resultset = pstatement.getResultSet();
                            
                        while(resultset.next()){
                            
                            subjects = new HashMap<>();
                            
                            String id = resultset.getString("subjectid");
                            String name = resultset.getString("name");
                            
                            subjects.put("subjectid", id);
                            subjects.put("name", name);
                            
                            subjectList.add(subjects);
                        } 
                        
                       
                    }
                }
                catch(SQLException e){ e.printStackTrace(); }
                
                return subjectList;
    } // end getSubjects()
                   
             
  
  public String getDropdownListofSubjects(ParameterBean pb){
        
        ArrayList<HashMap> subjectList = getSubjects(pb);
        
        // print dropdown menu using stringbuilder
        StringBuilder s = new StringBuilder();
        
        String selectOpenTag = "<select size=\"10\" name=\"subjectid\" id=\"subjectid\">";
        
        String selectClosingTag = "</select>";

        s.append(selectOpenTag);
        
        //iterate through the subjectsList to get the remaining options
        for (HashMap<String,String> subject : subjectList) {
            String id = subject.get("subjectid");
            String name = subject.get("name");
            String htmlString = "<option " + "value=\"" + id + "\">" + name + "(" + id + ")</option>";
            
            s.append(htmlString);
        }
        s.append(selectClosingTag);
        System.out.println(s.toString());
        return s.toString();
    }
  
  
  
  
  
    public ArrayList<HashMap> getScheduleTypes(ParameterBean pb){
        
        ArrayList<HashMap> scheduleTypeList = null;
        HashMap<String, String> scheduleTypes = null;
        
        int termid = Integer.parseInt(pb.getTermid());
        
                try{
                    
                   String sql = "SELECT DISTINCT(section.scheduletypeid), scheduletype.description FROM scheduletype JOIN section ON scheduletype.id=section.scheduletypeid WHERE section.termid=? ORDER BY scheduletype.description";
                   PreparedStatement pstatement = this.conn.prepareStatement(sql);
                   pstatement.setInt(1, termid);
                   
                   boolean hasresults = pstatement.execute();
                   
                   if ( hasresults ) {
                   
                        scheduleTypeList = new ArrayList<>();
                            
                        ResultSet resultset = pstatement.getResultSet();
                            
                        while(resultset.next()){
                            
                            scheduleTypes = new HashMap<>();
                            
                            String id = resultset.getString("scheduletypeid");
                            String description = resultset.getString("description");
                            
                            scheduleTypes.put("scheduletypeid", id);
                            scheduleTypes.put("description", description);
                            
                            scheduleTypeList.add(scheduleTypes);
                        } 
                        
                       
                    }
                }
                catch(SQLException e){ e.printStackTrace(); }
                
                return scheduleTypeList;
    } // end getScheduleTypes()
                   
             
  
  public String getDropdownListofScheduleTypes(ParameterBean pb){
        
        ArrayList<HashMap> scheduleTypeList = getScheduleTypes(pb);
        
        // print dropdown menu using stringbuilder
        StringBuilder s = new StringBuilder();
        
        String selectOpenTag = "<select size=\"5\" name=\"scheduletypeid\" id=\"scheduletypeid\">";
        String defaultOption = "<option value=\"\" selected>All</option>";
        String selectClosingTag = "</select>";

        s.append(selectOpenTag).append(defaultOption);
        
        //iterate through the scheduleTypeList to get the remaining options
        for (HashMap<String,String> scheduleType : scheduleTypeList) {
            
            String id = scheduleType.get("scheduletypeid");
            String description = scheduleType.get("description");
            
            String htmlString = "<option " + "value=\"" + id + "\">" + description + "</option>";
            
            s.append(htmlString);
        }
        s.append(selectClosingTag);
        
        System.out.println(s.toString());
        
        return s.toString();
    }
               
  
  public ArrayList<HashMap> getCourseLevels(){
        
        ArrayList<HashMap> courseLevelList = null;
        HashMap<String, String> courseLevels = null;
        
                try{
                   
                   String sql = "select * from level ORDER BY description";
                   PreparedStatement pstatement = this.conn.prepareStatement(sql);
                   
                   boolean hasresults = pstatement.execute();
                   
                   if ( hasresults ) {
                   
                        courseLevelList = new ArrayList<>();
                            
                        ResultSet resultset = pstatement.getResultSet();
                            
                        while(resultset.next()){
                            
                            courseLevels = new HashMap<>();
                            
                            String id = String.valueOf(resultset.getInt("id"));
                            String description = resultset.getString("description");
                            
                            courseLevels.put("id", id);
                            courseLevels.put("description", description);
                            
                            courseLevelList.add(courseLevels);
                        } 
                        
                       
                    }
                }
                catch(SQLException e){ e.printStackTrace(); }
                
                return courseLevelList;
    } // end getScheduleTypes()
                   
             
  
  public String getDropdownListofCourseLevels(){
        
        ArrayList<HashMap> courseLevelList = getCourseLevels();
        
        // print dropdown menu using stringbuilder
        StringBuilder s = new StringBuilder();
        
        String selectOpenTag = "<select size=\"3\" name=\"levelid\" id=\"levelid\">";
        String defaultOption = "<option value=\"\" selected>All</option>";
        String selectClosingTag = "</select>";

        s.append(selectOpenTag).append(defaultOption);
        
        //iterate through the scheduleTypeList to get the remaining options
        for (HashMap<String,String> courseLevel : courseLevelList) {
            
            int id = Integer.parseInt(courseLevel.get("id"));
            String description = courseLevel.get("description");
            
            String htmlString = "<option " + "value=\"" + id + "\">" + description + "</option>";
            
            s.append(htmlString);
        }
        s.append(selectClosingTag);
        System.out.println(s.toString());
        return s.toString();
    }
  
  
  
    public HashMap<String, String> getParameterHashMap(ParameterBean pb){
        
        HashMap<String, String> parameters = new HashMap<>();
        
        parameters.put("termid", pb.getTermid());
        parameters.put("levelid", pb.getLevelid());
        parameters.put("subjectid", pb.getSubjectid());
        parameters.put("num", pb.getNum());
        parameters.put("scheduletypeid", pb.getScheduletypeid());
        parameters.put("start", pb.getStart());
        parameters.put("end", pb.getEnd());
        parameters.put("days", pb.getDays());
        
        return parameters;

    }
  

      public String getResultSetTable(ResultSet resultset) throws ServletException, IOException {
        
        ResultSetMetaData metadata;
        
        StringBuilder table = new StringBuilder();
        StringBuilder tableheading = new StringBuilder();
        StringBuilder tablerow;
        
        String key;
        String tablename;
        String value;
        
        try {
            
          if(!resultset.isBeforeFirst()){
              System.out.println("Resultset was null");
              return "<p style=\"color:red;\"><strong>There are no courses that match your criteria.<strong></p>";
          }
          
            System.out.println("*** Getting Query Results ... ");

            metadata = resultset.getMetaData();

            int numberOfColumns = metadata.getColumnCount();
            
            table.append("<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"1\">");
            tableheading.append("<tr>");
            
            System.out.println("*** Number of Columns: " + numberOfColumns);
            
            for (int i = 1; i <= numberOfColumns; i++) {
            
                tablename = metadata.getTableName(i);
                key = (tablename + " " + metadata.getColumnLabel(i)).toUpperCase();
               
                tableheading.append("<th>").append(key).append("</th>");
            
            }
            
            tableheading.append("</tr>");
            
            table.append(tableheading.toString());
                        
            while(resultset.next()) {
                
                tablerow = new StringBuilder();                
                tablerow.append("<tr>");
                
                for (int i = 1; i <= numberOfColumns; i++) {

                    value = resultset.getString(i);

                    if (resultset.wasNull()) {
                        tablerow.append("<td></td>");
                    }

                    else {
                        tablerow.append("<td style=\"text-align:center;\">").append(value).append("</td>");
                    }
                    
                }
                
                tablerow.append("</tr>");
                
                table.append(tablerow.toString());
                
            }
            
            table.append("</table><br />");
          }
           
        
        catch (Exception e) {}
        
        return table.toString();
        
    } // End getResultSetTable()
  
  
      

                   
}//end


