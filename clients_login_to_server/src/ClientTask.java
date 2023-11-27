/**
 * @author Kryzhanivskyi Denys S18714
 */


import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ClientTask extends FutureTask<String> {

    private ClientTask(Callable<String> callable) {
        super(callable);
    }

    public static ClientTask create(Client c, List<String> reqList, boolean showRes) {
        return new ClientTask(() -> {
            c.connect();
            c.send("login " + c.getId());
            for (String req : reqList) {
                String res = c.send(req);
                if (showRes) System.out.println(res);
            }
            return c.send("bye and log transfer");
        });
    }
}