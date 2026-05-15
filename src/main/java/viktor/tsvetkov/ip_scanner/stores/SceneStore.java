package viktor.tsvetkov.ip_scanner.stores;

import viktor.tsvetkov.ip_scanner.model.SceneEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SceneStore {
    private final Set<SceneEntity> sceneEntities;

    public SceneStore(Collection<SceneEntity> sceneEntities) {
        this.sceneEntities = new HashSet<>(sceneEntities);
    }

    public SceneStore() {
        sceneEntities = new HashSet<>();
    }

    public SceneEntity search(String sceneName) {
        return sceneEntities.stream().filter(sceneEntity -> sceneEntity.getSceneName().equals(sceneName))
                .findFirst().orElseThrow();
    }

    public void addSceneEntity(SceneEntity sceneEntity) {
        sceneEntities.add(sceneEntity);
    }
}
