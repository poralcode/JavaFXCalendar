package com.seynalkim.org.controller;

import com.jfoenix.controls.JFXRippler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {

    @FXML
    Label labelMonth;
    @FXML
    ImageView janNav, febNav, marNav, aprNav, mayNav, junNav, julNav, augNav, sepNav, octNav, novNav, decNav;
    @FXML
    GridPane gridPane;
    @FXML
    Label labelYear;

    private String selectedMonth;
    private  ArrayList<AnchorPaneNode> dateList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        hideShowNav(getCurrentMonth()); //get the current month and show only the navigation on it.
        labelMonth.setText(String.valueOf(LocalDate.now().getMonth()));
        selectedMonth = String.valueOf(LocalDate.now().getMonth());

        //Add AnchorPane to GridView
        for (int i = 0; i < 6; i++) { //Row has 6, means we only shows six weeks on calendar, change it to your needs.
            for (int j = 0; j < 7; j++) { //Column has 7, for 7 days a week
                //Layout of AnchorPane
                AnchorPaneNode anchorPane = new AnchorPaneNode();
                anchorPane.setPrefSize(200,200);
                anchorPane.setPadding(new Insets(10));

                JFXRippler rippler = new JFXRippler(anchorPane);
                rippler.setRipplerFill(Paint.valueOf("#CCCCCC"));
                gridPane.add(rippler, j, i);

                dateList.add(anchorPane); //add the AnchorPane in a list
            }
        }

        populateDate(YearMonth.now());

    }

    /**Method that populate the date of moth in GridPane**/
    private void populateDate(YearMonth yearMonthNow){
        YearMonth yearMonth = yearMonthNow;
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (AnchorPaneNode anchorPane : dateList) {
            if (anchorPane.getChildren().size() != 0) {
                anchorPane.getChildren().clear(); //remove the label in AnchorPane
            }

            anchorPane.setDate(calendarDate); //set date into AnchorPane

            Label label = new Label();
            label.setText(String.valueOf(calendarDate.getDayOfMonth()));
            label.setFont(Font.font("Roboto",16)); //set the font of Text
            label.getStyleClass().add("notInRangeDays");
            if(isDateInRange(yearMonth, anchorPane.getDate())){
                label.getStyleClass().remove("notInRangeDays");
            }
            if (anchorPane.getDate().equals(LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth()))){
                label.getStyleClass().remove("notInRangeDays");
            }

            anchorPane.setTopAnchor(label, 5.0);
            anchorPane.setLeftAnchor(label, 5.0);
            anchorPane.getChildren().add(label);
            anchorPane.getStyleClass().remove("selectedDate"); //remove selection on date change
            anchorPane.getStyleClass().remove("dateNow"); //remove selection on current date
            if(anchorPane.getDate().equals(LocalDate.now())){ //if date is equal to current date now, then add a defualt color to pane
                anchorPane.getStyleClass().add("dateNow");
            }
            anchorPane.setOnMouseClicked(event -> { //Handle click event of AnchorPane
                System.out.println(anchorPane.getDate());
                for(AnchorPaneNode anchorPaneNode : dateList){
                    anchorPaneNode.getStyleClass().remove("selectedDate");
                }
                anchorPane.getStyleClass().add("selectedDate");
            });

            calendarDate = calendarDate.plusDays(1);
            System.out.println(anchorPane.getDate());

        }
    }

    /**Method that return TRUE/FALSE if the specified date is in range of the current month**/
    private boolean isDateInRange(YearMonth yearMonth, LocalDate currentDate){
        LocalDate start = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        LocalDate stop = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());

        return ( ! currentDate.isBefore( start ) ) && ( currentDate.isBefore( stop ) ) ;
    }

    /**Method that call the method populateDate(year, month) to change the calendar according to selected month and year**/
    private void changeCalendar(int year, String month){
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("yyyy MMMM")
                .toFormatter(Locale.ENGLISH);
        populateDate(YearMonth.parse(year + " " + month, formatter));
        selectedMonth = month;
    }

    @FXML
    private void onButtonJanuaryClicked(ActionEvent event){
        labelMonth.setText("JANUARY");
        hideShowNav(0);
        changeCalendar(Integer.parseInt(labelYear.getText()), "January");
    }

    @FXML
    private void onButtonFebruaryClicked(ActionEvent event){
        labelMonth.setText("FEBRUARY");
        hideShowNav(1);
        changeCalendar(Integer.parseInt(labelYear.getText()), "February");
    }

    @FXML
    private void onButtonMarchClicked(ActionEvent event){
        labelMonth.setText("MARCH");
        hideShowNav(2);
        changeCalendar(Integer.parseInt(labelYear.getText()), "March");
    }

    @FXML
    private void onButtonAprilClicked(ActionEvent event){
        labelMonth.setText("APRIL");
        hideShowNav(3);
        changeCalendar(Integer.parseInt(labelYear.getText()), "April");
    }

    @FXML
    private void onButtonMayClicked(ActionEvent event){
        labelMonth.setText("MAY");
        hideShowNav(4);
        changeCalendar(Integer.parseInt(labelYear.getText()), "May");
    }

    @FXML
    private void onButtonJuneClicked(ActionEvent event){
        labelMonth.setText("JUNE");
        hideShowNav(5);
        changeCalendar(Integer.parseInt(labelYear.getText()), "June");
    }

    @FXML
    private void onButtonJulyClicked(ActionEvent event){
        labelMonth.setText("JULY");
        hideShowNav(6);
        changeCalendar(Integer.parseInt(labelYear.getText()), "July");
    }

    @FXML
    private void onButtonAugustClicked(ActionEvent event){
        labelMonth.setText("AUGUST");
        hideShowNav(7);
        changeCalendar(Integer.parseInt(labelYear.getText()), "August");
    }

    @FXML
    private void onButtonSeptemberClicked(ActionEvent event){
        labelMonth.setText("SEPTEMBER");
        hideShowNav(8);
        changeCalendar(Integer.parseInt(labelYear.getText()), "September");
    }

    @FXML
    private void onButtonOctoberClicked(ActionEvent event){
        labelMonth.setText("OCTOBER");
        hideShowNav(9);
        changeCalendar(Integer.parseInt(labelYear.getText()), "October");
    }

    @FXML
    private void onButtonNovemberClicked(ActionEvent event){
        labelMonth.setText("NOVEMBER");
        hideShowNav(10);
        changeCalendar(Integer.parseInt(labelYear.getText()), "November");
    }

    @FXML
    private void onButtonDecemberClicked(ActionEvent event){
        labelMonth.setText("DECEMBER");
        hideShowNav(11);
        changeCalendar(Integer.parseInt(labelYear.getText()), "December");
    }

    @FXML
    private void onButtonLastYearClicked(ActionEvent event){
        labelYear.setText(String.valueOf(Integer.parseInt(labelYear.getText()) - 1));
        changeCalendar(Integer.parseInt(labelYear.getText()), selectedMonth);
    }

    @FXML
    private void onButtonNextYearClicked(ActionEvent event){
        labelYear.setText(String.valueOf(Integer.parseInt(labelYear.getText()) + 1));
        changeCalendar(Integer.parseInt(labelYear.getText()), selectedMonth);
    }

    /**Method that returns the current month**/
    private int getCurrentMonth(){
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        if(month > 0){
            return month -1;
        }
        return month;
    }

    /**Method that hides/shows the navigation of selected month**/
    private void hideShowNav(int month){
        janNav.setVisible(false);
        febNav.setVisible(false);
        marNav.setVisible(false);
        aprNav.setVisible(false);
        mayNav.setVisible(false);
        junNav.setVisible(false);
        julNav.setVisible(false);
        augNav.setVisible(false);
        sepNav.setVisible(false);
        octNav.setVisible(false);
        novNav.setVisible(false);
        decNav.setVisible(false);

        switch (month){
            case 0:
                janNav.setVisible(true);
                break;
            case 1:
                febNav.setVisible(true);
                break;
            case 2:
                marNav.setVisible(true);
                break;
            case 3:
                aprNav.setVisible(true);
                break;
            case 4:
                mayNav.setVisible(true);
                break;
            case 5:
                junNav.setVisible(true);
                break;
            case 6:
                julNav.setVisible(true);
                break;
            case 7:
                augNav.setVisible(true);
                break;
            case 8:
                sepNav.setVisible(true);
                break;
            case 9:
                octNav.setVisible(true);
                break;
            case 10:
                novNav.setVisible(true);
                break;
            case 11:
                decNav.setVisible(true);
                break;
                default:break;
        }
    }
}
