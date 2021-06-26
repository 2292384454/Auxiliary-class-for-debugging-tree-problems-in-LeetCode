package leetcode.editor.cn;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author huangkaiyan
 * @date 20200917
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    // Encodes a tree to a single string.
    private String serialize(TreeNode root) {
        if (root == null) return null;
        String ans = "[";
        LinkedList<String> array = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                array.add(String.valueOf(node.val));
                queue.add(node.left);
                queue.add(node.right);
            } else {
                array.add("null");
            }
        }
        while (("null").equals(array.getLast())) {
            array.removeLast();
        }
        ans += String.join(",", array);
        ans += "]";
        System.out.println(ans);
        return ans;
    }

    // Decodes your encoded data to tree.
    private static TreeNode deserialize(Integer[] nums) {
        if (nums.length == 0) {
            return null;
        }
        TreeNode root;
        Queue<Integer> numsQueue = new LinkedList<>(Arrays.asList(nums));
        Queue<TreeNode> queue = new LinkedList<>();
        // 创建一个根节点
        root = new TreeNode(numsQueue.remove());
        queue.add(root);
        TreeNode cur;
        // 记录当前行节点的数量（注意不一定是2的幂，而是上一行中非空节点的数量乘2）
        int lineNodeNum = 2;
        // 记录当前行中数字在数组中的开始位置
        int startIndex = 1;
        while (!numsQueue.isEmpty()) {
            // 只有最后一行可以不满，其余行必须是满的
            for (int i = startIndex; i < startIndex + lineNodeNum; i = i + 2) {
                cur = queue.remove();
                for (int j = 0; j < 2; j++) {
                    if (numsQueue.isEmpty()) {
                        return root;
                    }
                    Integer val = numsQueue.remove();
                    if (j == 0 && val != null) {
                        cur.left = new TreeNode(val);
                        queue.add(cur.left);
                    } else if (val != null) {
                        cur.right = new TreeNode(val);
                        queue.add(cur.right);
                    }
                }
            }
            startIndex += lineNodeNum;
            lineNodeNum = queue.size() * 2;
        }
        return root;
    }

    /**
     * 根据输入的层次遍历数组创建二叉树
     *
     * @param nums 输入的Integer数组，要求树只有最后一行可以不满，其余行必须是满的，空节点用null表示
     * @return 返回根据nums数组创建的二叉树
     */
    public static TreeNode constructTree(Integer[] nums) {
        return deserialize(nums);
    }

    @Override
    public String toString() {
        return serialize(this);
    }
}
