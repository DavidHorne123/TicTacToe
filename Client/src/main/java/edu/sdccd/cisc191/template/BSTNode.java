package edu.sdccd.cisc191.template;

public class BSTNode {

    int row;
    int col;
    String player;  // "X" or "O"
    BSTNode left, right;

    public BSTNode(int row, int col, String player) {
        this.row = row;
        this.col = col;
        this.player = player;
        this.left = null;
        this.right = null;
    }
}
