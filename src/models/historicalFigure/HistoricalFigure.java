package models.historicalFigure;

import java.util.ArrayList;
import java.util.List;

import models.historicalPeriod.HistoricalPeriod;

public class HistoricalFigure {
	
	private int id;
    private String name;
    private String otherName;
    private int birthYear;
    private int deathYear;
    private String place;
    private String description;
    private List<HistoricalPeriod> periods = new ArrayList<HistoricalPeriod>();

    public HistoricalFigure(String name, String otherName, int birthYear, int deathYear, String place, String description, HistoricalPeriod period){
    	
        this.name = name;
        this.otherName = otherName;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.place = place;
        this.description = description;
        this.periods = new ArrayList<HistoricalPeriod>();;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setOtherName(String otherName){
        this.otherName = otherName;
    }
    
    public void setBirthYear(int birthYear){
        this.birthYear = birthYear;
    }
    
    public void setDeathYear(int deathYear){
        this.deathYear = deathYear;
    }
    
    public void setBirthPlace(String place){
        this.place = place;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setPeriod(List<HistoricalPeriod> periods){
        this.periods = new ArrayList<HistoricalPeriod>(periods);
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getOtherName(){
        return this.otherName;
    }
    
    public int getBirthYear(){
        return this.birthYear;
    }
    
    public int getDeathYear(){
        return this.deathYear;
    }
    
    public String getplace(){
        return this.place;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public List<HistoricalPeriod> getPeriod() throws CloneNotSupportedException{
    	
    	  List<HistoricalPeriod> listReturn = new ArrayList<HistoricalPeriod>();
    	  
    	  for (HistoricalPeriod period : this.periods) {
    		  
    	        listReturn.add((HistoricalPeriod) period.clone());
    	  }
    	 
    	  return listReturn;
    } 
}