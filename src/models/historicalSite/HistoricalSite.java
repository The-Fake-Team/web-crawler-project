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
        this.recognitionDate = recognitionDate;
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
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public Date getRecognitionDate() {
        return this.recognitionDate;
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
    public int getId() {
        return this.id;
    }

    
    public void show()
    {
        System.out.println("name: " + name);
        System.out.println("Recognition Date: " + recognitionDate);
        System.out.println("Place: " + place);
        System.out.println("Summary: " + summary);
        System.out.println("detail: " + detail);
    }
}
