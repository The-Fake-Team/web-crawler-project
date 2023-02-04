package models.king;

import models.duration.Duration;
import models.historicalFigure.HistoricalFigure;
import models.historicalPeriod.HistoricalPeriod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class King extends HistoricalFigure {

	private String thuyHieu;
	private String nienHieu;
	private String mieuHieu;
	private Duration triVi;
	private String theThu;
	private String hoangDe;
	private List<String> tenHuy;

	public King() {
		super();
	}

	public King(int id, String name, String otherName, Integer birthYear, Integer deathYear, String place,
			String description, List<HistoricalPeriod> period) {
		super(id, name, otherName, birthYear, deathYear, place, description, period);
	}

	public King(String thuyHieu, String nienHieu, String mieuHieu, Integer startTriVi, Integer endTriVi, String theThu,
			String hoangDe, List<String> tenHuy) {
		this.thuyHieu = thuyHieu;
		this.nienHieu = nienHieu;
		this.mieuHieu = mieuHieu;
		this.triVi = new Duration(startTriVi, endTriVi);
		this.theThu = theThu;
		this.hoangDe = hoangDe;
		this.tenHuy = tenHuy;
	}

	public void setThuyHieu(String thuyHieu) {
		this.thuyHieu = thuyHieu;
	}

	public String getThuyHieu() {
		return thuyHieu;
	}

	public void setNienHieu(String nienHieu) {
		this.nienHieu = nienHieu;
	}

	public String getNienHieu() {
		return nienHieu;
	}

	public void setMieuHieu(String mieuHieu) {
		this.mieuHieu = mieuHieu;
	}

	public String getMieuHieu() {
		return mieuHieu;
	}

	public void setTriVi(Integer startTriVi, Integer endTriVi) {
		this.triVi = new Duration(startTriVi, endTriVi);
	}

	public Duration getTriVi() {
		return triVi;
	}

	public void setTheThu(String theThu) {
		this.theThu = theThu;
	}

	public String getTheThu() {
		return theThu;
	}

	public void setHoangDe(String hoangDe) {
		this.hoangDe = hoangDe;
	}

	public String getHoangDe() {
		return hoangDe;
	}

	public void setTenHuy(List<String> tenHuy) {
		this.tenHuy = tenHuy;
	}

	public List<String> getTenHuy() {
		return new ArrayList<String>(tenHuy);
	}

	@Override
	public String toString() {
		return "King [mieuHieu=" + mieuHieu + ", nienHieu=" + nienHieu + ", theThu=" + theThu + ", thuyHieu=" + thuyHieu
				+ ", triVi=" + triVi.getStart() + "-" + triVi.getEnd() + ", hoangDe=" + hoangDe + ", Name=" + "]";
	}

}
