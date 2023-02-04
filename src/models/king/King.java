package models.king;

import models.historicalFigure.HistoricalFigure;
import models.historicalPeriod.HistoricalPeriod;

import java.util.ArrayList;
import java.util.List;

public class King extends HistoricalFigure {

	private String thuyHieu;
	private String vua;
	private String nienHieu;
	private List<String> tenHuy;
	private String tietDoSu;
	private String thuLinh;
	private String mieuHieu;
	private String triVi;
	private String theThu;
	private String hoangDe;

	public King() {
		super();
	}

	public King(int id, String name, String otherName, Integer birthYear, Integer deathYear, String place,
			String description, List<HistoricalPeriod> period) {
		super(id, name, otherName, birthYear, deathYear, place, description, period);
	}

	public King(String vua, String thuyHieu, String nienHieu, String tietDoSu, String thuLinh, String mieuHieu,
			String triVi, String theThu, String hoangDe, List<String> tenHuy) {
		this.thuyHieu = thuyHieu;
		this.nienHieu = nienHieu;
		this.mieuHieu = mieuHieu;
		this.triVi = triVi;
		this.theThu = theThu;
		this.hoangDe = hoangDe;
		this.tenHuy = tenHuy;
		this.vua = vua;
		this.tietDoSu = tietDoSu;
		this.thuLinh = thuLinh;
	}

	public void setThuyHieu(String thuyHieu) {
		this.thuyHieu = thuyHieu;
	}

	public void setVua(String vua) {
		this.vua = vua;
	}

	public void setTriVi(String triVi) {
		this.triVi = triVi;
	}

	public void setTietDoSu(String tietDoSu) {
		this.tietDoSu = tietDoSu;
	}

	public void setThuLinh(String thuLinh) {
		this.thuLinh = thuLinh;
	}

	public void setTheThu(String theThu) {
		this.theThu = theThu;
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

	public String getTriVi() {
		return triVi;
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

	public String getVua() {
		return vua;
	}

	public String getTietDoSu() {
		return tietDoSu;
	}

	public String getThuLinh() {
		return thuLinh;
	}

	@Override
	public String toString() {
		return "King [thuyHieu=" + thuyHieu + ", vua=" + vua + ", nienHieu=" + nienHieu + ", tenHuy=" + tenHuy
				+ ", tietDoSu=" + tietDoSu + ", thuLinh=" + thuLinh + ", mieuHieu=" + mieuHieu + ", triVi=" + triVi
				+ ", theThu=" + theThu + ", hoangDe=" + hoangDe + "]";
	}

}
