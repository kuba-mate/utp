package functions_1;

import java.util.function.Function;

public
    class InputConverter<T> {

    private T fileName;

    public InputConverter (T fileName){
        this.fileName=fileName;
    }


    public <T> T convertBy(Function... functions){
        Object in=fileName;
        Object out=null;
        for(Function f:functions){
            out=f.apply(in);
            in=out;
        }
        return (T)in;
    }






}
