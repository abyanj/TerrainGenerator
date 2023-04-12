package ca.mcmaster.cas.se2aa4.pathfinder;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private final String id;
    private int idx = 0;
    public Node(String id) {
        this.id = id;
    }

    public String getId() {
        return id;

    }
    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }


}

