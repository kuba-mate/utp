package functions_2;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public
    class Maybe<T> {

    private T value;

    public Maybe(T value){
        this.value=value;
    }

    public static<T> Maybe<T> of(T value){
        return new Maybe<>(value);
    }

    public void ifPresent(Consumer cons){
        if(value != null)
        cons.accept(value);
    }

    public<K> Maybe<K> map(Function<T,K> fun){
        if(value==null){
            return new Maybe<>(null);
        } else {
            return new Maybe<>(fun.apply(value));
        }
    }

    public T get(){
        if(value==null)
            throw new NoSuchElementException("maybe is empty");
         else
            return value;
    }

    public T orElse(T defVal){
        if(value==null)
            return value=defVal;
        else
            return value;
    }

    public Maybe<T> filter(Predicate<T> pred){
        if(pred.test(value)){
            return this;
        } else
            return Maybe.of(null);
    }

    public String toString(){
        if(value==null){
            return "Maybe is empty";
        } else
            return "Maybe has value " + value;
    }

}
