package bu.met.cs622;

import java.util.ArrayList;
import java.util.NoSuchElementException;

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
     */
    public void push(T item) {
        stackArray.add(item);
    }

    /**
     * removes an element from the generic stack
     * @return  the LIFO element if the stack is not empty
     */
    public T pop() {
        int stackSize = stackArray.size();
        if (stackSize == 0) {
            return null; // the stack is already empty
        } else {
            // remove the last element that was added to the stack
            return stackArray.remove(stackArray.size() - 1);
        }
    }

    /**
     * returns the last item that was added to the top of the generic stack
     * @return  the LIFO element or null if the stack is empty
     */
    public T peek() {
        T lastStackElement = null;
        int stackSize = stackArray.size();
        if (stackSize > 0) {
            lastStackElement = stackArray.get(stackArray.size() - 1);
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
