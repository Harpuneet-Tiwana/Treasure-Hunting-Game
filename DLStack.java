public class DLStack<T> implements DLStackADT<T> {

    private DoubleLinkedNode<T> top; // declare private variable top
    private int numItems; // declare private int variable numItems

    public DLStack() { // constructor that initializes both private variables
        top = null;
        numItems = 0;
    }

    /**
     * @param dataItem - the node that is to be pushed into the stack
     */
    public void push(T dataItem) { // adds item to the top of the stack

        DoubleLinkedNode<T> pushNode = new DoubleLinkedNode<T>(dataItem);

        if (top != null) {
            top.setNext(pushNode);
            pushNode.setPrevious(top);
        }
        top = pushNode; // sets top to the node if stack is empty

        numItems++; // increment numItems since size of stack increased by 1

    }


    /**
     * @return topNodeData - variable of type T that represents the data of popped node
     */
    public T pop() throws EmptyStackException {

        T topNodeData; // declare topNodeData to represent data of node popped from stack

        if (isEmpty()) {
            throw new EmptyStackException("Stack is empty."); // throws EmptyStackException if stack is empty

        } else {
            topNodeData = top.getElement(); // sets the value of topNodeData
            top = top.getPrevious(); // sets the top of stack to the previous node

            if (top != null) {
                top.setNext(null); // since top is at the bottom, the next node should be null
            }
        }

        numItems--; // reduce numItems since size of stack decreased by 1
        return topNodeData; // return the data of the popped node

    }


    /**
     * @param k - indicates the index of the item we want to remove from the stack
     * @return T popNodeData - represents the data of the node that was popped
     */
    public T pop(int k) throws InvalidItemException {

        T popNodeData; // declare popNodeData

        DoubleLinkedNode<T> currNode = top; // initialize the current node to the top of the stack


        if (k <= 0 || k > numItems) {
            throw new InvalidItemException("Invalid index."); // throws exception if the index inputted is invalid
        }

        for (int i = 1; i < k; i++) {
            currNode = currNode.getPrevious(); // sets currNode to the node we want to remove
        }

        if (k == 1) {
            popNodeData = pop(); // if we want to remove top node, call pop method
            return popNodeData;

        } else if (k == numItems) { // if we are removing the "last in" node
            popNodeData = currNode.getElement();
            currNode = currNode.getNext();
            currNode.setPrevious(null);
            numItems--; // decrement numItems
            return popNodeData; // return data of popped node

        } else { // if we are removing node somewhere in between 2 other nodes
            popNodeData = currNode.getElement(); // set popNodeData
            currNode.getPrevious().setNext(currNode.getNext());
            currNode.getNext().setPrevious(currNode.getPrevious());
            numItems--; // decrement numItems
            return popNodeData; // return data of popped node
        }

    }


    /**
     * @return T headNodeData - represents data of node we are peeking
     */
    public T peek() throws EmptyStackException {

        T headNodeData; // declare headNodeData

        if (isEmpty()) {
            throw new EmptyStackException("Stack is empty."); // throw exception if stack is empty
        } else {
            headNodeData = top.getElement(); // sets the value of headNodeData
        }

        return headNodeData; // returns the data contained in the node

    }


    public boolean isEmpty() {
        return numItems == 0; // returns true if the stack has 0 elements, false otherwise
    }


    public int size() {
        return numItems; // returns the number of elements in the stack
    }


    public DoubleLinkedNode<T> getTop() {
        return top; // returns the top node in the stack
    }


    public String toString() {
        String str = ""; // initialize an empty string

        DoubleLinkedNode<T> currNode = top; // initialize the current node to point to the top

        str += "[";

        while (currNode != null) {
            if (currNode.getNext() == null) {
                str += currNode.getElement() + "]"; // adds closing bracket if currNode is last node in stack
            } else {
                str += currNode.getElement() + " "; // if the current node is not the last node in stack,
                // add slightly different string to the str variable
                currNode = currNode.getNext();
            }
        }
        return str; // return the String str
    }

}