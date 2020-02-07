package bu.met.cs622;

import java.util.ArrayList;

public class GenericStack<T> {

    private ArrayList<T> stackArray;

    /**
     * constructor that creates a generic stack
     */
    public GenericStack() {
        stackArray = new ArrayList<T>();
    }

    /**
     * pushes an item onto the generic stack
     * @return  the LIFO object
     */
    public void push(T item) {
//        System.out.println("adding to stack... " + item);
        stackArray.add(item);
    }

    /**
     * removes an element from the generic stack
     * @return  the LIFO object
     */
    public T pop() {
        int stackSize = stackArray.size();
        if (stackSize == 0) {
            return null; // the stack is already empty
        } else {
            // remove the last element to be added to the stack
            return stackArray.remove(stackArray.size() - 1);
        }
    }

    /**
     * gets the last item that was added to the top of the generic stack
     * @return  the LIFO object
     */
    public T peek() {
        T lastStackElement = null;
        try {
            lastStackElement = stackArray.get(stackArray.size() - 1);
            System.out.printf("lastStackElement... ", lastStackElement);
            if (lastStackElement != null)
                return lastStackElement;
        }
        catch (IndexOutOfBoundsException e) {
            System.out.printf("Stack is empty ", e.getMessage());
        }
           return lastStackElement;
    }

    /**
     * gets the number of elements in the generic stack
     * @return  a total count of all elements
     */
    public int stackSize() {
        return stackArray.size();
    }

}
