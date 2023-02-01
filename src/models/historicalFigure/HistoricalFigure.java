package models.historicalFigure;

import java.util.ArrayList;
import java.util.Date;
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
    
    public void setPeriod(HistoricalPeriod period)
    {
        this.period = period;
    }
    public String getName()
    {
        return Name;
    }
    public String getOtherName()
    {
        return OtherName;
    }
    public int getBirthYear()
    {
        return BirthYear;
    }
    public int getDeathYear()
    {
        return DeathYear;
    }
    public String getBirthPlace()
    {
        return BirthPlace;
    }
    public String getDescription()
    {
        return Description;
    }
    public HistoricalPeriod getPeriod()
    {
        return period;
    }
    public void show()
    {
        System.out.println("Name: " + Name);
        System.out.println("Other Name: " + OtherName);
        System.out.println("Birth Year: " + BirthYear);
        System.out.println("Death Year: " + DeathYear);
        System.out.println("Birth Place: " + BirthPlace);
        System.out.println("Description: " + Description);
        System.out.println("Period: " + period.getName());
    }

    
}