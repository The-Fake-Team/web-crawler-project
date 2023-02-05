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
    private List<T> relatedFigures = new ArrayList<T>();

    public Festival() {

    }

    public Festival(int id, String name, Date date, String place, String firstHeld) {
        this.id = id;
        this.name = name;
        this.date = date != null ? (Date) date.clone() : null;
        this.place = place;
        this.firstHeld = firstHeld;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setRelatedFigures(List<T> relatedFigures) {
        this.relatedFigures = new ArrayList<T>(relatedFigures);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Date getDate() {
        return this.date != null ? (Date) this.date.clone() : null;
    }

    public String getPlace() {
        return this.place;
    }

    public String getFirstHeld() {
        return this.firstHeld;
    }

    public List<T> getRelatedFigures() {

        return this.relatedFigures; // TODO: cloning
    }

    public void addRelatedFigure(T relatedFigure) {

        relatedFigures.add(relatedFigure);
    }

    public void removeRelatedFigure(T relatedFigure) {

        relatedFigures.remove(relatedFigure);
    }
}