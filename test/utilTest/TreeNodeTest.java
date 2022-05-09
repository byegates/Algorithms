package utilTest;

import org.junit.jupiter.api.Test;
import util.TreeNode;

import static org.junit.jupiter.api.Assertions.*;

class TreeNodeTest {
    String[] A1 = new String[]{"2", "1", "3"};
    int[] res1 = new int[]{2, 1, 3};
    Integer[] A2 = new Integer[]{4, 3, 19, 1, null, 16, 35, null, null, 9, 17, 30, 43, null, 15, null, 18, null, 34, null, null, 11};
    int[] res2 = new int[]{4, 3, 1, 19, 16, 9, 15, 11, 17, 18, 35, 30, 34, 43};
    Integer[] A3 = new Integer[]{11, 1, null, null, -1, -9, 10, -5, 6, -7, null, null, -6, 15};
    int[] res3 = new int[]{11, 1, -1, -9, -5, -6, 6, 15, 10, -7};
    Integer[] A4 = new Integer[]{12, 15, -5, 1, 2, -14, -12, -16, null, -3, 7, null, null, null, -1, -2, 9, null, null, 3, 10};
    int[] res4 = new int[]{12, 15, 1, -16, -2, 9, 2, -3, 7, 3, 10, -5, -14, -12, -1};
    Integer[] A5 = new Integer[]{-13, 0, -1, 2, 14, -12, 3, 10, null, -6, 1, 6, -4, 11, 5, 16};
    int[] res5 = new int[]{-13, 0, 2, 10, 16, 14, -6, 1, -1, -12, 6, -4, 3, 11, 5};
    Integer[] A6 = new Integer[]{9, null, 6, -16, null, -3, 12, 13, -5, -14, 0, 8, 4, -5, 13};
    int[] res6 = new int[]{9, 6, -16, -3, 13, 8, 4, -5, -5, 13, 12, -14, 0};
    TreeNode R1 = TreeNode.fromLevelOrderSpecial(A1);
    TreeNode R2 = TreeNode.fromLevelOrder(A2);
    TreeNode R3 = TreeNode.fromLevelOrder(A3);
    TreeNode R4 = TreeNode.fromLevelOrder(A4);
    TreeNode R5 = TreeNode.fromLevelOrder(A5);
    TreeNode R6 = TreeNode.fromLevelOrder(A6);

    @Test
    void case1() {assertArrayEquals(R1.preOrder().stream().mapToInt(i -> i).toArray(), res1);}
    @Test
    void case2() {assertArrayEquals(R2.preOrder().stream().mapToInt(i -> i).toArray(), res2);}
    @Test
    void case3() {assertArrayEquals(R3.preOrder().stream().mapToInt(i -> i).toArray(), res3);}
    @Test
    void case4() {assertArrayEquals(R4.preOrder().stream().mapToInt(i -> i).toArray(), res4);}
    @Test
    void case5() {assertArrayEquals(R5.preOrder().stream().mapToInt(i -> i).toArray(), res5);}
    @Test
    void case6() {assertArrayEquals(R6.preOrder().stream().mapToInt(i -> i).toArray(), res6);}
    @Test
    void case2level() {assertArrayEquals(R2.levelOrder().toArray(Integer[]::new), A2);}
    @Test
    void case3level() {assertArrayEquals(R3.levelOrder().toArray(Integer[]::new), A3);}
    @Test
    void case4level() {assertArrayEquals(R4.levelOrder().toArray(Integer[]::new), A4);}
    @Test
    void case5level() {assertArrayEquals(R5.levelOrder().toArray(Integer[]::new), A5);}
    @Test
    void case6level() {assertArrayEquals(R6.levelOrder().toArray(Integer[]::new), A6);}
    @Test
    void postOrder1() {assertEquals(R1.postOrder(), R1.postOrderIterative());}
    @Test
    void postOrder2() {assertEquals(R2.postOrder(), R2.postOrderIterative());}
    @Test
    void postOrder3() {assertEquals(R3.postOrder(), R3.postOrderIterative());}
    @Test
    void postOrder4() {assertEquals(R4.postOrder(), R4.postOrderIterative());}
    @Test
    void postOrder5() {assertEquals(R5.postOrder(), R5.postOrderIterative());}
    @Test
    void postOrder6() {assertEquals(R6.postOrder(), R6.postOrderIterative());}

    // MaxPathSum two nodes plus tests
    Integer[] allNeg = new Integer[]{-1, -2, -9, -5, null, -4, -3, null,-6};
    Integer[] allPos = new Integer[]{1, 2, 9, 5, null, 4, 3, null, 6};
    Integer[] pos1 = new Integer[]{1};
    Integer[] pos2 = new Integer[]{1, 2};
    Integer[] mixed2 = new Integer[]{1, -2};
    Integer[] mixed3 = new Integer[]{1, -2, -3};
    Integer[] mixed4 = new Integer[]{1, -2, -3, 9};

    @Test
    void AllNeg() {assertEquals(-3, TreeNode.fromLevelOrder(allNeg).maxPathSum2PlusNodes());}
    @Test
    void Allpos() {assertEquals(27, TreeNode.fromLevelOrder(allPos).maxPathSum2PlusNodes());}
    @Test
    void pos1() {assertEquals(Integer.MIN_VALUE, TreeNode.fromLevelOrder(pos1).maxPathSum2PlusNodes());}
    @Test
    void pos2() {assertEquals(3, TreeNode.fromLevelOrder(pos2).maxPathSum2PlusNodes());}
    @Test
    void mixed2() {assertEquals(-1, TreeNode.fromLevelOrder(mixed2).maxPathSum2PlusNodes());}
    @Test
    void mixed3() {assertEquals(-1, TreeNode.fromLevelOrder(mixed3).maxPathSum2PlusNodes());}
    @Test
    void mixed4() {assertEquals(8, TreeNode.fromLevelOrder(mixed4).maxPathSum2PlusNodes());}
    @Test
    void nullInput() {assertEquals(Integer.MIN_VALUE, TreeNode.maxPathSum2PlusNodes(null));}
}