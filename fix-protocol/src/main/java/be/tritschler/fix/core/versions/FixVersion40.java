package be.tritschler.fix.core.versions;

import java.util.Properties;

public interface FixVersion40 {
    // session init
    String startSession(String senderId, String receiverId, String heartBeatInterval);
    String startSession(Properties properties);
    // todo others
    void sendSingleOrder(String sessionId);
    void sendOrderCancelRequest(String sessionId, String orderId);
    void stopSession(String sessionId);
 
}
