/**
 *
 *  @author Matecki Jakub S24500
 *
 */




public
    class Purchase {

    private String id,name,product;
    private double price,number;

    public Purchase(String id, String name, String product, double price, double number) {
        this.id = id;
        this.name = name;
        this.product = product;
        this.price = price;
        this.number = number;
    }

    public String toString(){
        return id + ";" + name + ";" + product + ";" + price + ";" + number;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public double getTotalPrice(){
        return price*number;
    }
}
