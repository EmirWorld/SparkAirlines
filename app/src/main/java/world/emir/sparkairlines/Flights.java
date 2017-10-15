package world.emir.sparkairlines;

/**
 * Created by phpwizz on 10/15/17.
 */

public class Flights {

    String author,date,date_of_flight_created,description,name_of_destination,price,thumb_image,time;

    Flights(){

    }

    public Flights(String author, String date, String date_of_flight_created, String description, String name_of_destination, String price, String thumb_image, String time) {
        this.author = author;
        this.date = date;
        this.date_of_flight_created = date_of_flight_created;
        this.description = description;
        this.name_of_destination = name_of_destination;
        this.price = price;
        this.thumb_image = thumb_image;
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_of_flight_created() {
        return date_of_flight_created;
    }

    public void setDate_of_flight_created(String date_of_flight_created) {
        this.date_of_flight_created = date_of_flight_created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName_of_destination() {
        return name_of_destination;
    }

    public void setName_of_destination(String name_of_destination) {
        this.name_of_destination = name_of_destination;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
