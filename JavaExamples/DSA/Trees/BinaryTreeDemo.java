package DSA.Trees;

import java.util.Scanner;

class Node
{
    Node left, right;

    int data;

    public Node(int data)
    {
        this.data = data;
    }
}

public class BinaryTreeDemo
{
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        Node root = createBinaryTree();


        System.out.print("Inorder Traversal: ");
        inOrder(root);
        System.out.println();

        System.out.print("Preorder Traversal: ");
        preOrder(root);
        System.out.println();

        System.out.print("Postorder Traversal: ");
        postOrder(root);
        System.out.println();

        System.out.println("Height of the tree is: " + calcHeight(root));
        System.out.println("Size of the tree is: " + sizeTree(root));
        System.out.println("Max node value: " + maxValueOfTree(root));
        System.out.println("Min node value: " + minValueOfTree(root));

        sc.close();
    }

    static Node createBinaryTree()
    {

        Node root = null;
        System.out.print("Enter data: ");
        int data = sc.nextInt();

        if(data == -1)
            return null;

        root = new Node(data);


        System.out.print("Enter value for left of " + data + " ");
        root.left = createBinaryTree();
        System.out.print("Enter value for right of " + data + " ");
        root.right = createBinaryTree();

        return root;
    }

    static void inOrder(Node root)
    {
        if(root == null)
            return;

        inOrder(root.left); // left
        System.out.print(root.data + " "); // root
        inOrder(root.right); // right
    }

    static void preOrder(Node root)
    {
        if(root == null)
            return;

        System.out.print(root.data + " "); // root
        preOrder(root.left); // left
        preOrder(root.right); // right
    }

    static void postOrder(Node root)
    {
        if(root == null)
            return;

        postOrder(root.left); // left
        postOrder(root.right); // right
        System.out.print(root.data + " "); // root
    }

    static int calcHeight(Node root)
    {
        if(root == null)
            return 0;

        return Math.max(calcHeight(root.left), calcHeight(root.right)) + 1;
    }

    static int sizeTree(Node root)
    {
        if(root == null)
            return 0;

        return sizeTree(root.left) + sizeTree(root.right) + 1;
    }

    static int maxValueOfTree(Node root)
    {
        if(root == null)
            return Integer.MIN_VALUE;

        return Math.max(root.data, Math.max(maxValueOfTree(root.left), maxValueOfTree(root.right)));
    }

    static int minValueOfTree(Node root)
    {
        if(root == null)
            return Integer.MAX_VALUE;

        return Math.min(root.data, Math.min(minValueOfTree(root.left), minValueOfTree(root.right)));
    }
}
