package DSA.LinkedList;


class Node
{
    Node prev;

    Node next;

    int data;

    Node(int data)
    {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

}

public class DoubleLinkedList
{
    Node head;

    public static DoubleLinkedList insert(DoubleLinkedList list, int data)
    {
        Node newNode = new Node(data);

        if(list.head == null)
        {
            list.head = newNode;
        }
        else
        {
            Node last = list.head;
            while(last.next != null)
            {
                last = last.next;
            }
            last.next = newNode;
            newNode.prev = last;
            //list.head.prev = newNode;
        }
        return list;
    }

    public static DoubleLinkedList insertBefore(DoubleLinkedList list, int data, int x)
    {
        Node newNode = new Node(data);

        if(list.head == null)
        {
            list.head = newNode;
        }
        else
        {
            boolean exists = false;
            Node last = list.head;

            while(last.next != null)
            {
                if(last.data == x)
                {
                    exists = true;
                    break;
                }
                last = last.next;

            }
            if(exists)
            {
                Node prevOfLast = last.prev;
                newNode.prev = prevOfLast;
                prevOfLast.next = newNode;
                last.prev = newNode;
                newNode.next = last;
            }
            else
            {
                System.out.println(x + " not found");
            }
        }
        return list;
    }

    public static void printList(DoubleLinkedList list)
    {
        Node currrent = list.head;

        while(currrent != null)
        {
            System.out.println(currrent.data + " ");
            currrent = currrent.next;
        }

    }

    public static void main(String[] args)
    {
        DoubleLinkedList list = new DoubleLinkedList();
        list = insert(list, 1);
        list = insert(list, 2);
        //                list = insert(list, 3);
        list = insert(list, 4);
        list = insert(list, 5);
        list = insert(list, 8);

        list = insertBefore(list, 6, 9);
        printList(list);
    }
}

