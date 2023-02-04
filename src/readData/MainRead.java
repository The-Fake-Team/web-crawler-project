package readData;

import java.util.List;

import models.festival.Festival;
import models.historicEvent.HistoricEvent;
import models.historicalFigure.HistoricalFigure;
import models.historicalPeriod.HistoricalPeriod;
import models.historicalSite.HistoricalSite;
import models.king.King;

public class MainRead {
    public static void main(String[] args) {
        ReadPeriod readPeriod = new ReadPeriod("data//period.json");
        List<HistoricalPeriod> periods = readPeriod.readData();

        ReadFigure readFigure = new ReadFigure("data//historicalFigure.json");
        List<HistoricalFigure> figures = readFigure.readData();

        ReadKing readKing = new ReadKing("data//king.json");
        List<King> kings = readKing.readData();
        figures = readKing.mergeData(figures, kings);

        ReadEvent readEvent = new ReadEvent("data//historicalEvent.json");
        List<HistoricEvent> events = readEvent.readData(figures);

        ReadFestival readFestival = new ReadFestival("data//festival.json");
        List<Festival> festivals = readFestival.readData(figures);

        ReadSite readSite = new ReadSite("data//historicalSite.json");
        List<HistoricalSite> sites = readSite.readData();

    }
}