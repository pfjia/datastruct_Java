package ch9;

import java.util.LinkedList;
import java.util.List;

/**
 * @author pfjia
 * @since 2017/11/21 21:25
 */
public class Graph {

    public List<Vertex> getVertices(){
        return new LinkedList<>();
    }

    public int getVerticesNum(){
        return getVertices().size();
    }

    public void  unweighted(Vertex s){
        for (Vertex vertex : getVertices()) {
            vertex.distance=Integer.MAX_VALUE;
            vertex.known=false;
        }

        s.distance=0;

        final int NUM_VERTICES=getVerticesNum();
        for (int currDist=0;currDist<NUM_VERTICES;currDist++){
            for (Vertex vertex : getVertices()) {
                if (!vertex.known&& vertex.distance==currDist){
                    vertex.known=true;
                }
            }
        }
    }
}
