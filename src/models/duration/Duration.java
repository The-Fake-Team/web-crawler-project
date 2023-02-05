package models.duration;

public class Duration {

	private Integer start;
	private Integer end;

	public Duration(Integer start) {
		this.start = start;
		this.end = null;
	}

	public Duration(Integer start, Integer end) {

		this.start = start;
		this.end = end;
	}

	public Integer getStart() {
		return this.start;
	}

	public Integer getEnd() {
		return this.end;
	}

	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}
}
