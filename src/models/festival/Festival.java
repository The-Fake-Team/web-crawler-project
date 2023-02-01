package models.festival;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.person.Person;

public class Festival {
    private String name;
    private Date date;
    private String place;
    private String firstHeld;
    private List<Person> relatedCharacters = new ArrayList<Person>();
    public Festival() {
    }
    public Festival(String name, Date date, String place, String firstHeld) {
        this.name = name;
        this.date = date;
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
    
    public void setFirstHeldYear(String firstHeld) {
        this.firstHeld = firstHeld;
    }
    
    public void setRelatedCharacters(List<Person> RelatedCharacters) {
        this.relatedCharacters.addAll(RelatedCharacters);
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
    
    public List<Person> getRelatedFigures() {
    	List<Person> listReturn = new ArrayList<Person>();
    	
        for (Person character : this.relatedCharacters) {
            listReturn.add((Person) character.clone());
        }
 
        return listReturn;
    }
    
    public void addRelatedFigure(Person relatedCharacter) {
        relatedCharacters.add(relatedCharacter);
    }
    
    public void removeRelatedFigure(Person relatedCharacter) {
        relatedCharacters.remove(relatedCharacter);
    }
    
    public void printRelatedCharacters() {
    	
        for (Person relatedCharacter : relatedCharacters) {
        	
            System.out.println(relatedCharacter);
        }
    }

    
}