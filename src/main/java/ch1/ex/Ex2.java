package ch1.ex;


/**
 * 编写一个程序求解字谜游戏问题
 *
 * @author pfjia
 * @since 2017/12/6 21:45
 */
public class Ex2 {

    /**
     * @param matrix 字母组成的二维数组
     * @param words  需要在字母数组中找出的单词
     */
    public void puzzle(char[][] matrix, String[] words) {
        int row = matrix.length;
        int column = matrix[0].length;
        for (String word : words) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    // TODO: 2017/12/6  
                }
            }
        }
    }


    public static void main(String[] args) {
        char[][] matrix = new char[][]{{'t', 'h', 'i', 's'},
                {'w', 'a', 't', 's'}, {'o', 'a', 'h', 'g'},
                {'f', 'g', 'd', 't'}
        };
    }
}
