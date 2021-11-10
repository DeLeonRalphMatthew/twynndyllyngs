using System;

class LinkedListNiBetty
{
    public Node Head, Tail, MiddleNode;
    public int Count = 0;

    public class Node
    {
        public Node Next, Parent;
        public int Data;

        public Node(int Data)
        {
            this.Data = Data;
            Next = null;
        }
    }

    public void Add(int Data)
    {
        if (Head == null) // if empty // Initially, the first node added will be the Head, Mid, and Tail, 
        {
            Head = new Node(Data);

            Head.Parent = Tail;

            Tail = Head;
            Tail.Parent = Head;

            MiddleNode = Head;
            MiddleNode.Next = Tail;
        }
        else  // adds a node at the tail of the list 
        {
            Tail.Next = new Node(Data);

            Tail.Next.Parent = Tail;
            Tail = Tail.Next;
            Tail.Next = Head;

            Head.Parent = Tail;

            // To keep track of the middle node. // Moves the middle pointer by one at the right when Count or Length is ODD
            if (Count % 2 == 0)
                MiddleNode = MiddleNode.Next;
        }

        Count += 1;
    }

    public int Get(int Index)
    {
        if (Index < 0 || Index >= Count || Count == 0) // if index given is invalid or list is empty.
            throw new Exception();

        Node StartingNode;

        int MiddleIndex = (Count - 1) / 2;

        if (Index > MiddleIndex) // higher partition // Mid + 1 -> tail
        {
            int HalfOfHigherPartition = Convert.ToInt32(Math.Ceiling(Count * .75)) - 1;

            if (Index > HalfOfHigherPartition) // if index is closer to tail
            {
                StartingNode = Tail;

                for (int i = 0; i < (Count - 1) - Index; i++)
                    StartingNode = StartingNode.Parent;
            }
            else // if index is closer to middle
            {
                StartingNode = MiddleNode.Next;

                for (int i = 0; i < Index - (MiddleIndex + 1); i++)
                    StartingNode = StartingNode.Next;
            }
        }
        else // lower partition // head -> mid
        {
            int Half_Of_This_Half = Convert.ToInt32(Math.Ceiling(Count * .25)) - 1;

            if (Index > Half_Of_This_Half) // if index is closer to mid
            {
                StartingNode = MiddleNode;

                for (int i = 0; i < MiddleIndex - Index; i++)
                    StartingNode = StartingNode.Parent;
            }
            else // if index is closer to head
            {
                StartingNode = Head;

                for (int i = 0; i < Index; i++)
                    StartingNode = StartingNode.Next;
            }
        }

        return StartingNode.Data;
    }

    public int Pop() // removes and returns the value of the tail
    {
        if (isEmpty())
            throw new Exception();

        int data = Tail.Data;

        if (Tail == Head)
            Head = null;
        else
        {
            Tail = Tail.Parent;
            Tail.Next = Head;
            Head.Parent = Tail;
        }

        Count -= 1;
        return data;
    }

    public int Dequeue() // removes and returns the value of the head
    {
        if (isEmpty())
            throw new Exception();

        int data = Head.Data;

        if (Head == Tail)
            Head = null;
        else
        {
            Head = Head.Next;
            Head.Parent = Tail;
            Tail.Next = Head;
        }

        Count -= 1;
        return data;
    }

    public bool isEmpty()
    {
        return Count == 0;
    }
}
