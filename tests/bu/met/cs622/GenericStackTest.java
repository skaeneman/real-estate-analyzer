package bu.met.cs622;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericStackTest {


    @Test
    void push() {
        GenericStack<String> genStack = new GenericStack<>();
        genStack.push("one");
        genStack.push("two");
        genStack.push("three");

        int size = genStack.stackSize();
        assertEquals(3, size);
    }

    @Test
    void peek() {
        GenericStack<String> genStack = new GenericStack<>();

        genStack.push("first");
        genStack.push("second");
        genStack.push("third");

        String peekABoo = genStack.peek();
        assertEquals("third", peekABoo);
    }

    @Test
    void pop() {
        GenericStack<String> genStack = new GenericStack<>();
        genStack.push("first item");
        genStack.push("second item");
        assertEquals(2, genStack.stackSize());

        genStack.pop();
        assertEquals(1, genStack.stackSize());

        genStack.pop();
        assertEquals(0, genStack.stackSize());
    }

    @Test
    void stackSize() {
        GenericStack<String> genStack = new GenericStack<>();
        genStack.push("first item");
        assertEquals(1, genStack.stackSize());
    }

}