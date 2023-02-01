package models.HistoricEvent;

import java.util.ArrayList;
import java.util.List;
import models.HistoricalFigure.*;
import models.HistoricalPeriod.HistoricalPeriod;
import models.duration.Duration;

import java.util.Date;
import java.time.LocalDate;

public class HistoricEvent<T> {
	
    private String name;
    private Duration duration;
    private String references;
    private String description;
    private String relatedPlaces;
    private ArrayList<T> RelatedFigures = new ArrayList<T>();

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

    public void setDuration(int startTime) {
        this.StartTime = StartTime;
    }

    public void setEndTime(int EndTime) {
        this.EndTime = EndTime;
    }

    public void setReference(String Reference) {
        this.Reference = Reference;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setRelatedFigures(ArrayList<T> RelatedFigures) {
        this.RelatedFigures = RelatedFigures;
    }

    public String getName() {
        return Name;
    }

    public int getStartTime() {
        return StartTime;
    }

    public int getEndTime() {
        return EndTime;
    }

    public String getReference() {
        return Reference;
    }

    public String getDescription() {
        return Description;
    }

    public String getRelatedSites() {
        return RelatedSites;
    }

    public ArrayList<T> getRelatedFigures() {
        return RelatedFigures;
    }

    public void addRelatedFigure(T RelatedFigure) {
        RelatedFigures.add(RelatedFigure);
    }

    public void removeRelatedFigure(T RelatedFigure) {
        RelatedFigures.remove(RelatedFigure);
    }

}