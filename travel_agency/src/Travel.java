

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public
    class Travel {

    private Locale loc;
    private String country;
    private Date dateFrom;
    private Date dateTo;
    private String place;
    private double price;
    private String currency;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    NumberFormat numberFormat;

    public Travel(String loc, String country, String dateFrom, String dateTo, String place, String price, String currency) throws ParseException {
        this.loc = Locale.forLanguageTag(loc.replace("_", "-"));
        numberFormat=NumberFormat.getInstance(this.loc);
        this.country=country;
        this.dateFrom=dateFormat.parse(dateFrom);
        this.dateTo=dateFormat.parse(dateTo);
        this.place=place;
        this.price=numberFormat.parse(price).doubleValue();
        this.currency=currency;
    }

    public Locale getLoc() {
        return loc;
    }

    public String getCountry() {
        return country;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public String getPlace() {
        return place;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }


}
