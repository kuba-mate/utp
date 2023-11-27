package noStream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class ListCreator<T> {

    private List<T> list;

    public ListCreator(List<T> value){
        this.list=value;
    }

    public static<T> ListCreator<T> collectFrom(List<T> value){
        return new ListCreator<>(value);
    }

    public ListCreator<T> when(Predicate<T> predicate){
        List<T> tmp=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(predicate.test(list.get(i)))
                tmp.add(list.get(i));
        }
        list=tmp;
        return this;
    }

    public<K> List<K> mapEvery(Function<T,K> function){
        List<K> tmp=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            tmp.add(function.apply(list.get(i)));
        }
        return tmp;
    }



}
