
package edu.jsu.mcis.cs425.lab6;

import java.io.Serializable;
import java.util.HashMap;

public class ParameterBean implements Serializable {


    private HashMap<String, String> parameters = null;
    private String termid;
    
    public ParameterBean(){
        parameters = new HashMap<>();
    }
     
    public void clear(){ 
        parameters.clear();
    }
    
    public HashMap<String, String> getParameters(){
        return parameters;
    }
    
    public String getTermid() {
        return this.termid;
    }
    
    public String getLevelid() {
        return parameters.get("levelid");
    }

    public String getSubjectid() {
        return parameters.get("subjectid");
    }

    public String getNum() {
        return parameters.get("num");
    }

    public String getScheduletypeid() {
        return parameters.get("scheduletypeid");
    }
    
    public String getStart() {
        return parameters.get("start");
    }
    
    public String getEnd() {
        return parameters.get("end");
    }
    
    public String getDays() {
        return parameters.get("days");
    }
    
    
    public void setTermid(String termid) {
        this.termid = termid;
    }
    
    public void setLevelid(String levelid) {
        parameters.put("levelid", levelid);
    }

    public void setSubjectid(String subjectid) {
        parameters.put("subjectid", subjectid);
    }

    public void setNum(String num) {
         parameters.put("num", num);
    }

    public void setScheduletypeid(String scheduletypeid) {
        parameters.put("scheduletypeid", scheduletypeid);
    }

    public void setStart(String start) {
        parameters.put("start", start);
    }
    
    public void setEnd(String end) {
        parameters.put("end", end);
    }
    
    
    public void setDays(String days) {
        parameters.put("days", days);
    }
   

       
}
