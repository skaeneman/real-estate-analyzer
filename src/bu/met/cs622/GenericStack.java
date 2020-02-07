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
     * gets the last item that was added to the top of the generic stack
     * @return  the LIFO object
     */
    public T peek() {
        return stackArray.get(stackArray.size()-1);
    }


}
