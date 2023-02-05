package controller.festival;

import java.util.Calendar;
import java.util.TimeZone;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import models.festival.Festival;

public class FestivalController {

    @FXML
    private JFXButton name;

    @FXML
    private JFXButton time;

    @FXML
    private JFXButton place;

	
	public void setData(Festival festival) {
		
		name.setText(festival.getName());
		
		if (festival.getDate() != null) {
			
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Vietnam"));
			
			cal.setTime(festival.getDate());
			
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH) + 1;
			
			String heldDate = Integer.toString(day) + "/" + Integer.toString(month); 
			time.setText(heldDate);
		} else {
			time.setText("");
		}

		place.setText(festival.getPlace());
	}
}
