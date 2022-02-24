package router.model;

import java.util.Map;
import java.util.TreeMap;

public class PathNode {
    private final String path;
    private final Map<String, PathNode> children;

    public PathNode(String path) {
        this.path = path;
        this.children = new TreeMap<>();
    }

    public String getPath() {
        return path;
    }

    public Map<String, PathNode> getChildren() {
        return children;
    }

    public void addChild(String child) {
        children.put(child , new PathNode(child));
    }

}
