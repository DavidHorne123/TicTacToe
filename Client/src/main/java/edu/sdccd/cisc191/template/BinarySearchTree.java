package edu.sdccd.cisc191.template;

public class BinarySearchTree {

    BSTNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    // Insert a new node in the BST
    public void insert(int row, int col, String player) {
        root = insertRecursive(root, row, col, player);
    }

    private BSTNode insertRecursive(BSTNode node, int row, int col, String player) {
        // Base case: if the current node is null, create a new one
        if (node == null) {
            return new BSTNode(row, col, player);
        }

        // Compare the current node's position (row, col) with the new one
        if (row < node.row || (row == node.row && col < node.col)) {
            node.left = insertRecursive(node.left, row, col, player);
        } else if (row > node.row || (row == node.row && col > node.col)) {
            node.right = insertRecursive(node.right, row, col, player);
        }
        return node;
    }

    // In-order traversal to print the BST
    public void inorderTraversal() {
        inorderTraversalRecursive(root);
    }


    private void inorderTraversalRecursive(BSTNode node) {
        if (node != null) {
            inorderTraversalRecursive(node.left);
            System.out.println("Player " + node.player + " clicked at (" + node.row + ", " + node.col + ")");
            inorderTraversalRecursive(node.right);
        }
    }
}
