package viktor.tsvetkov.ip_scanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NetworkNode {
    private String ipAddress;
    private String online;
    private String log;
    private String lastOnlineTime;
}
