package io;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class CharBufferDemo {
    public static void main(String[] args) throws IOException {
        CharBuffer buffer = CharBuffer.allocate(8);
        System.out.println("初始状态:");
        printState(buffer);
        buffer.put('a').put('b').put('c');
        System.out.println("写入3个字符后:");
        printState(buffer);
        buffer.flip();
        System.out.println("flip后:");
        printState(buffer);
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        buffer.clear();
        System.out.println("调用clear后:");
        printState(buffer);
    }


    private static void printState(CharBuffer buffer) {
        System.out.println("capacity:" + buffer.capacity());
        System.out.println("limit:" + buffer.limit());
        System.out.println("position:" + buffer.position());
        System.out.println("mark:" + buffer.mark());
        System.out.println();
    }
}
