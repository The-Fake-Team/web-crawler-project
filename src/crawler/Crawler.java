package crawler;

import crawler.festival.Festival;
import crawler.historicalFigure.*;
import crawler.historicalPeriod.HistoricalPeriod;
import crawler.historicalSite.HistoricalSite;
import crawler.historyEvent.*;
import crawler.king.King;

public class Crawler {
	
	public static void crawlFestival () {
		Festival festival = new Festival();
		
		Thread t = new Thread(festival);
		t.start();
	}

	public static void getHistoricalFiguresUrl () {
		FigureLink figureLink = new FigureLink();
		
		Thread linkt = new Thread(figureLink);
		
		linkt.start();
		
		 try {             
			 linkt.join(); 
		 } catch (InterruptedException e) {
		       e.printStackTrace();
		 }
	}
	
	public static void crawlHistoricalFigure () {
		HistoricalFigure historicalFigure = new HistoricalFigure();
		
		Thread t1 = new Thread(historicalFigure);
		
		t1.start();
		
		 try {             
			 t1.join(); 
		 } catch (InterruptedException e) {
		       e.printStackTrace();
		 }
		
	}
	
	public static void crawlHistoricalSite () {
		HistoricalSite historicalSite = new HistoricalSite();
		
		Thread t2 = new Thread(historicalSite);
		
		t2.start();
	}
	
	public static void crawlHistoricalPeriod () {
		HistoricalPeriod historicalPeriod = new HistoricalPeriod();
		
		Thread t3 = new Thread(historicalPeriod);
		
		t3.start();
	}
	
	public static void getEventsUrl () {
		EventLink eventLink = new EventLink();
		
		Thread tn = new Thread(eventLink);
		
		tn.start();
		
		 try {             
			 tn.join(); 
		 } catch (InterruptedException e) {
		       e.printStackTrace();
		 }
	}
	
	public static void crawlHistoricEvent() {
		HistoricEvent historicEvent = new HistoricEvent();
		
		Thread t3 = new Thread(historicEvent);
		
		t3.start();
	}
	
	public static void crawlKing () {
		King king = new King();
		
		Thread t3 = new Thread(king);
		
		t3.start();
	}
	
	public static void main(String[] args) {
		
		getHistoricalFiguresUrl();
		crawlHistoricalFigure();
		crawlHistoricalSite();
		crawlHistoricalPeriod();
		getEventsUrl();
		crawlHistoricEvent();
		crawlKing();
		crawlFestival();
	}
}
