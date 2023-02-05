package models.historicalSite;

import java.util.Date;

public class HistoricalSite {

    private int id;
    private String name;
    private Date recognitionDate;
    private String place;
    private String summary;
    private String detail;

    public HistoricalSite() {
    }

    public HistoricalSite(int id, String name, Date recognitionDate, String place, String summary, String detail) {
        this.id = id;
        this.name = name;
        this.recognitionDate = recognitionDate != null ? (Date) recognitionDate.clone() : null;
        this.place = place;
        this.summary = summary;
        this.detail = detail;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getId() {
        return this.id;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getDetail() {
        return this.detail;
    }

}