package edu.sdccd.cisc191.template;

public class BinarySearchTree {
    class Node {
        int key; // Unique key for each move
        String player; // The player who made the move (X or O)
        Node left, right;

        Node(int key, String player) {
            this.key = key;
            this.player = player;
            left = right = null;
        }
    }

    private Node root;
    private boolean isXTurn = true; // Tracks if it's X's turn (starts as X)

    // Insert a move into the tree
    public void insert(int row, int col, String currentTurn) {
        int key = row * 3 + col; // Generate unique key
        String player = isXTurn ? "X" : "O"; // Determine the player
        root = insertRec(root, key, player);
        isXTurn = !isXTurn; // Toggle turn after insertion
    }

    private Node insertRec(Node root, int key, String player) {
        if (root == null) {
            return new Node(key, player);
        }
        if (key < root.key) {
            root.left = insertRec(root.left, key, player);
        } else if (key > root.key) {
            root.right = insertRec(root.right, key, player);
        }
        return root;
    }

    // In-order traversal (to display moves in order)
    public void inOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(Node root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.println("Key: " + root.key + ", Player: " + root.player);
            inOrderRec(root.right);
        }
    }
}
