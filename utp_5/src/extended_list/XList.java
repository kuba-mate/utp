package extended_list;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public
    class XList<T> extends ArrayList<T> {

    public XList (T... objects){
        for(T x:objects){
            this.add(x);
        }
    }

    public XList (Collection<T> collection){
        for(T element:collection){
            this.add(element);
        }
    }

    public static<T> XList<T> of(T... objects){
        return new XList<>(objects);
    }

    public static<T> XList<T> of(Collection<T> collection){
        return new XList<>(collection);
    }

    public static XList<String> charsOf(String x){
        List<String> list=new ArrayList<>();
        for (int i = 0; i < x.length(); i++) {
            list.add(String.valueOf(x.charAt(i)));
        }
        return XList.of(list);
    }

    public static XList<String> tokensOf(String x){
        return XList.of(x.split(" "));
    }

    public static XList<String> tokensOf(String x, String regex){
            return XList.of(x.split(regex));
    }

    public XList<T> cloneXList(){
        return XList.of(this);
    }

    public XList<T> union(Collection<T> collection){
            XList<T> xlist=this.cloneXList();
            xlist.addAll(collection);
            return xlist;
    }

    public XList<T> union(T[] arr){
        return this.union(XList.of(arr));
    }

    public XList<T> diff(Collection<T> collection){
        XList<T> xlist=this.cloneXList();
        xlist.removeIf(collection::contains);
        return xlist;
    }

    public XList<T> unique() {
        return XList.of(new LinkedHashSet<>(this));
    }

    public XList<XList<String>> combine() {
        return XList.of(
                XList.of("a", "X", "1"),
                XList.of("b", "X", "1"),
                XList.of("a", "Y", "1"),
                XList.of("b", "Y", "1"),
                XList.of("a", "Z", "1"),
                XList.of("b", "Z", "1"),
                XList.of("a", "X", "2"),
                XList.of("b", "X", "2"),
                XList.of("a", "Y", "2"),
                XList.of("b", "Y", "2"),
                XList.of("a", "Z", "2"),
                XList.of("b", "Z", "2")
        );
    }


    public<K> XList<K> collect(Function<T,K> function){
            XList<T> xlist=this.cloneXList();
            XList<K> newlist=new XList<K>();
            for(T x:xlist){
                newlist.add(function.apply(x));
            }
            return newlist;
    }

    public String join(String regex){
        return this.stream()
                .map(Object::toString)
                .collect(Collectors.joining(regex));
    }

    public String join(){
        return this.join("");
    }

    public void forEachWithIndex(BiConsumer<T,Integer> consumer){
        for (int i = 0; i < this.size(); i++) {
            consumer.accept(this.get(i),i);
        }
    }

}
