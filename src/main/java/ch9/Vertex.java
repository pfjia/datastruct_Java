package ch9;

import java.util.List;

/**
 * @author pfjia
 * @since 2017/11/21 21:17
 */
public class Vertex {
    /**Adjacency list*/
    public List adj;
    public boolean known;
    /**DistType is probably int*/
    public int distance;
    public Vertex path;
}
