package WebSocket;

import spark.Route;
import util.Path;
import util.ViewUtil;

import java.util.HashMap;

public class WebSocketController {
    public static Route socketTest =(request, response) -> {
        HashMap<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.WEBSOCKET_TEST);

    };
}