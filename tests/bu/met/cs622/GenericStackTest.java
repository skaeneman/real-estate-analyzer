package bu.met.cs622;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericStackTest {

    GenericStack<String> genStack = new GenericStack<>();

    @Test
    void push() {
    }

    @Test
    void peek() {
        genStack.push("first");
        genStack.push("second");
        genStack.push("third");

        String peekABoo = genStack.peek();
        assertEquals("third", peekABoo);
    }

}