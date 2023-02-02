package models.historicalSite;

import java.util.Date;

public class HistoricalSite {

    private String name;
    private Date recognitionDate;
    private String place;
    private String summary;
    private String detail;

    public HistoricalSite() {
    }

    public HistoricalSite(String name, Date recognitionDate, String place, String summary, String detail) {
        this.name = name;
        this.recognitionDate = recognitionDate;
        this.place = place;
        this.summary = summary;
        this.detail = detail;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setRecognitionDate(Date recognitionDate) {
    	this.recognitionDate = (Date) recognitionDate.clone();
    }
    
    public void setPlace(String place) {
        this.place = place;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Date getRecognitionDate() {
        return (Date) this.recognitionDate.clone();
    }
    
    public String getPlace() {
        return this.place;
    }
    
    public String getSummary() {
        return this.summary;
    }
    
    public String getDetail() {
        return this.detail;
    }

}