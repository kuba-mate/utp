/**
 *
 *  @author Matecki Jakub S24500
 *
 */




import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public
    class CustomersPurchaseSortFind {

    List<Purchase> list;

    public CustomersPurchaseSortFind(){
        list=new ArrayList<>();
    }

    public void readFile(String filename){
        int x;
        File file=new File(filename);
        StringBuilder sb=new StringBuilder();
        try {
            FileReader fr=new FileReader(file);
            while((x=fr.read())!=-1)
                sb.append((char)x);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] lines=sb.toString().split("\n");
        for (String value : lines){
            addNewPurchase(value);
        }
        for (Purchase purchase : list)
            System.out.println(purchase);
        System.out.println();
    }

    public void addNewPurchase(String value){
        String[] arr=value.split(";");
        Purchase purchase=new Purchase(arr[0], arr[1], arr[2], Double.parseDouble(arr[3]), Double.parseDouble(arr[4]));
        list.add(purchase);
    }

    public void showSortedBy(String pivot){
        List<Purchase> tmp;
        if(pivot.equals("Nazwiska")) {
            System.out.println(pivot);
            tmp=list.stream().sorted((x, y) -> {
                        if (x.getName().equals(y.getName()))
                            return x.getId().compareTo(y.getId());
                        else
                            return x.getName().compareTo(y.getName());
                    })
                    .collect(Collectors.toList());
            for(Purchase purchase : tmp){
                System.out.println(purchase);
            }
            System.out.println();
        } else if(pivot.equals("Koszty")){
            System.out.println(pivot);
            tmp=list.stream().sorted((x,y)->{
                if(x.getTotalPrice()==y.getTotalPrice())
                    return x.getId().compareTo(y.getId());
                else if(x.getTotalPrice()>y.getTotalPrice())
                    return -1;
                else
                    return 1;
            }).collect(Collectors.toList());
            for(Purchase purchase : tmp){
                System.out.println(purchase + " (koszt: " + purchase.getTotalPrice() + ")");
            }
            System.out.println();
        }
    }

    public void showPurchaseFor(String id){
        System.out.println("Klient " + id);
        for(Purchase purchase : list){
            if(purchase.getId().equals(id)){
                System.out.println(purchase);
            }
        }
        System.out.println();
    }


}
