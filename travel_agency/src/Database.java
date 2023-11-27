

import java.sql.*;
import java.text.SimpleDateFormat;

class Database {


    private String url;
    private TravelData travelData;
    private Connection connection;

    Database(String url, TravelData travelData) {
        this.travelData = travelData;
        this.url = url;
        try {
            connection=DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void insertInto() {

        Statement statement = null;
        try {
            statement = connection.createStatement();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String insert;

            for (Travel travel : travelData.getList()) {
                insert = "INSERT INTO travels" + "\n" +
                        "(locale, country, dateFrom, dateTo, place, price, currency)" + "\n" +
                        "VALUES ( " +
                        travel.getLoc() + ",\n" +
                        travel.getCountry() + ",\n" +
                        dateFormat.format(travel.getDateFrom()) + ",\n" +
                        dateFormat.format(travel.getDateTo()) + ",\n" +
                        travel.getPlace() + ",\n" +
                        travel.getPrice() + ",\n" +
                        travel.getCurrency() +
                        ");";
                statement.executeUpdate(insert);
                 }
            statement.close();
            } catch(SQLException e){
            System.out.println(e.getMessage());
            }
    }

    void create() {
        String drop = "drop table if exists traveldata";

        String create = "create table travels" + "\n" +
                "( " + "\n" +
                "travelId int auto_increment" + "\n" +
                "locale varchar(255) null," + "\n" +
                "country varchar(255) null, " + "\n" +
                "dateFrom date null," + "\n" +
                "dateTo date null, " + "\n" +
                "place varchar(255) null, " + "\n" +
                "price float null," + "\n" +
                "currency char(3) null," + "\n" +
                "primary key (travelId)" + "\n" +
                ");";

        try {

            Statement statement=connection.createStatement();
            statement.executeUpdate(drop);
            statement.executeUpdate(create);
            insertInto();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showGui(){
        new GUI(travelData.getList());
    }

}