package models.king;

import models.historicalFigure.HistoricalFigure;
import models.historicalPeriod.HistoricalPeriod;

public class King extends HistoricalFigure {

	public King(String name, String otherName, int birthYear, int deathYear, String place, String description,
			HistoricalPeriod period) {
		super(name, otherName, birthYear, deathYear, place, description, period);
		//TODO Auto-generated constructor stub
	}
	private String ThuyHieu;
	private String NienHieu;
	private String MieuHieu;
	private int StartTriVi;
	private int EndTriVi;
	private String TheThu;
	private String HoangDe;

}
