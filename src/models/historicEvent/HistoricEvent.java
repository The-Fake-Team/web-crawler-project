package models.historicEvent;

import java.util.ArrayList;
import java.util.List;
import models.duration.Duration;


public class HistoricEvent<T> {
	
    private String name;
    private Duration duration;
    private String references;
    private String description;
    private String relatedPlaces;
    private List<T> relatedFigures = new ArrayList<T>();

    public HistoricEvent() {
    	
    }
    public HistoricEvent(String name, Integer start, Integer end, String references, String description, String relatedPlaces) {
        this.name = name;
        this.duration = new Duration(start, end);
        this.references = references;
        this.description = description;
        this.relatedPlaces = relatedPlaces;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int start, int end) {
        this.duration = new Duration(start, end);
    }
    
    public void setDuration(Duration duration) throws CloneNotSupportedException {
        this.duration = (Duration) duration.clone();
    }
    
    public void setReference(String references) {
        this.references = references;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRelatedFigures(ArrayList<T> relatedFigures) {
        this.relatedFigures = relatedFigures;
    }

    public String getName() {
        return this.name;
    }

    public Duration getDuration () throws CloneNotSupportedException {
        return (Duration) this.duration.clone();
    }

    public String getReference() {
        return this.references;
    }

    public String getDescription() {
        return description;
    }

    public String getRelatedPlaces() {
        return this.relatedPlaces;
    }

    public List<T> getRelatedFigures() {
        return this.relatedFigures;
    }

    public void addRelatedFigure(T relatedFigure) {
        this.relatedFigures.add(relatedFigure);
    }

    public void removeRelatedFigure(T RelatedFigure) {
        this.relatedFigures.remove(RelatedFigure);
    }

}