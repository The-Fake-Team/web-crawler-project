package models.festival;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Festival<T> {
	
	private int id;
    private String name;
    private Date date;
    private String place;
    private String firstHeld;
    private List<T> relatedCharacters = new ArrayList<T>();
    
    public Festival() {
    	
    }
    
    public Festival(String name, Date date, String place, String firstHeld) {
        this.name = name;
        this.date = (Date) date.clone();
        this.place = place;
        this.firstHeld = firstHeld;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setDate(Date Date) {
    	this.date = (Date) date.clone();
    }
    
    public void setPlace(String place) {
        this.place = place;
    }
    
    public void setFirstHeld(String firstHeld) {
        this.firstHeld = firstHeld;
    }
    
    public void setRelatedCharacters(List<T> relatedCharacters) {
        this.relatedCharacters = new ArrayList<T>(relatedCharacters);
    }
    
    public String getName() {
        return this.name;
    }
    
    public Date getDate() {
    	return (Date) this.date.clone();
    }
    
    public String getPlace() {
        return this.place;
    }
    
    public String getFirstHeld() {
        return this.firstHeld;
    }
    
    public List<T> getRelatedCharacters() {
    	
    	return this.relatedCharacters; //TODO: cloning
    }
    
    public void addRelatedFigure(T relatedCharacter) {
    	
        relatedCharacters.add(relatedCharacter);
    }
    
    public void removeRelatedFigure(T relatedCharacter) {
    	
        relatedCharacters.remove(relatedCharacter);
    } 
}