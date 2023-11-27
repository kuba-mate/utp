/**
 *
 *  @author Matecki Jakub S24500
 *
 */



import java.util.ArrayList;
import java.util.List;

public class ListCreator<T> {

    private List<T> list;

    public ListCreator(List<T> value){
        this.list=value;
    }

    public static<T> ListCreator<T> collectFrom(List<T> value){
        return new ListCreator<>(value);
    }

    public ListCreator<T> when(Selector<T> selector){
        List<T> tmp=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(selector.select(list.get(i)))
                tmp.add(list.get(i));
        }
        list=tmp;
        return this;
    }

    public<K> List<K> mapEvery(Mapper<T,K> mapper){
        List<K> tmp=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            tmp.add(mapper.map(list.get(i)));
        }
        return tmp;
    }

}  
