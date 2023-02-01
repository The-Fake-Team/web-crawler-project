package models.duration;

public class Duration {
	
	private Integer start;
	private Integer end;
	
	public Duration(Integer start) {
		this.start = start;
		this.end = null;
	}
	
	public Duration(Integer start,Integer end) {
		
		this.start = start;
		this.end = end;
	}
	
	public Integer getStart() {
		return this.start;
	}
	
	public Integer getEnd() {
		return this.end;
	}
	
	public static void main (String[] args) {
		
		Duration duration = new Duration (1);
		Duration duration1 = new Duration (1,2);
		Duration duration2 = new Duration (null, null);

		System.out.printf("Check: %d - %d", duration.getStart(), duration.getEnd());
		System.out.printf("Check: %d - %d", duration1.getStart(), duration1.getEnd());
		System.out.printf("Check: %d - %d", duration2.getStart(), duration2.getEnd());

	}
}
