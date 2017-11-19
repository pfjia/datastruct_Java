package ch5;

/**
 * @author pfjia
 * @since 2017/11/19 9:20
 */
public interface HashFamily<E> {
    int hash(E x,int which);
    int getNumberOfFunctions();
    void  generateNewFunctions();
}
