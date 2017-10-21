package world.emir.sparkairlines;

/**
 * Created by phpwizz on 10/15/17.
 */

public class Flights {

    String first_destination,second_destination,price,date,time,author;

    public Flights(String first_destination, String second_destination, String price, String date, String time, String author) {
        this.first_destination = first_destination;
        this.second_destination = second_destination;
        this.price = price;
        this.date = date;
        this.time = time;
        this.author = author;
    }

    public String getFirst_destination() {
        return first_destination;
    }

    public void setFirst_destination(String first_destination) {
        this.first_destination = first_destination;
    }

    public String getSecond_destination() {
        return second_destination;
    }

    public void setSecond_destination(String second_destination) {
        this.second_destination = second_destination;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    Flights(){



    }


}
