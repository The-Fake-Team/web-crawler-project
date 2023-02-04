package models.historicalFigure;

import java.util.ArrayList;
import java.util.List;

import models.duration.Duration;
import models.historicalPeriod.HistoricalPeriod;

public class HistoricalFigure {

    private int id;
    private String name;
    private String otherName;
    private Duration birthYeardeathYear;
    private String place;
    private String description;
    private boolean role;
    private List<HistoricalPeriod> periods = new ArrayList<HistoricalPeriod>();

    public HistoricalFigure() {

    }

    public HistoricalFigure(int id, String name, String otherName, int birthYear, int deathYear, String place,
            String description, List<HistoricalPeriod> periods) {

        this.id = id;
        this.name = name;
        this.otherName = otherName;
        this.birthYeardeathYear = new Duration(birthYear, deathYear);
        this.place = place;
        this.description = description;
        this.role = false;
        this.periods = new ArrayList<HistoricalPeriod>(periods);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public void setBirthYear(Duration duration) {
        this.birthYeardeathYear = duration;
    }

    public void setBirthPlace(String place) {
        this.place = place;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public void setRoleTrue() {
        this.role = true;
    }

    public void setPeriod(List<HistoricalPeriod> periods) {
        this.periods = new ArrayList<HistoricalPeriod>(periods);
    }

    public void addPeriod(HistoricalPeriod period) {
        this.periods.add(period);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getOtherName() {
        return this.otherName;
    }

    public Integer getBirthYear() {
        return this.birthYeardeathYear.getStart();
    }

    public Integer getDeathYear() {
        return this.birthYeardeathYear.getEnd();
    }

    public Duration getBirthYearDeathYear() {
        return this.birthYeardeathYear;
    }

    public String getplace() {
        return this.place;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getRole() {
        return this.role;
    }

    public List<HistoricalPeriod> getPeriod() throws CloneNotSupportedException {

        List<HistoricalPeriod> listReturn = new ArrayList<HistoricalPeriod>();

        for (HistoricalPeriod period : this.periods) {

            listReturn.add((HistoricalPeriod) period.clone());
        }

        return listReturn;
    }
}