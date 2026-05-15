package viktor.tsvetkov.ip_scanner.model;

import javafx.scene.Scene;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"scene"})
public class SceneEntity {
    private String sceneName;
    private Scene scene;
}
