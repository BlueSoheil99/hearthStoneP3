package edu.sharif.student.bluesoheil.ap98.hearthstone.util.log;

public class Log {

    private LogTypes type;
    private String description;
    //you may need to add the time parameter

    protected Log(LogTypes type , String description){
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public LogTypes getType() {
        return type;
    }
}
