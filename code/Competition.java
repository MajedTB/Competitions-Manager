package code;
import java.util.Date;

public abstract class Competition {

    private String name, link;
    private Date date;

    public Competition() {

    }

    public Competition(String name, String link, Date date) {
        this.name = name;
        this.link = link;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public Date getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return name;
    }
}