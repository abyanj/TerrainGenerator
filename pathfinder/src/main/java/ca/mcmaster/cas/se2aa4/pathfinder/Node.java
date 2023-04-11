package ca.mcmaster.cas.se2aa4.pathfinder;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private String id;
    private Map<String, Object> attributes;

    public Node(String id) {
        this.id = id;
        this.attributes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }
}
