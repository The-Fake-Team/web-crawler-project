package models.historicalPeriod;

import models.duration.Duration;

public class HistoricalPeriod {
	
    private String nationalName;
    private Duration duration;

    public HistoricalPeriod(String nationalName, Integer start, Integer end) {
    	
        this.nationalName = nationalName;
        this.duration = new Duration(start, end);
    }

    public HistoricalPeriod() {
    	
        this.nationalName = "";
        this.duration = new Duration(null, null);
    }

    public String getNationalName() {
        return nationalName;
    }

    public Duration getDuration() {
        return this.duration;
    }
    
    

}