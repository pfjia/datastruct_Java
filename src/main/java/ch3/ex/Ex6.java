package ch3.ex;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 约瑟夫斯问题:
 * @author pfjia
 * @since 2017/12/7 20:16
 */
public class Ex6 {

    /**
     * 使用list模拟操作过程
     * @param n 总人数
     * @param m 隔m人进行一次清除
     */
    public void  foo(int n,int m){
        int index=0;
        List<Integer> list= IntStream.rangeClosed(1,n).boxed().collect(Collectors.toList());
        //每次外层循环清除一个玩家
        while (list.size()>1){
            index=(index+m)%list.size();
            int removed= list.remove(index);
            System.out.println("removed:"+removed);
        }
        System.out.println(list.get(0));
    }

    public static void main(String[] args) {
        new Ex6().foo(5,0);
    }
}
