package processor;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestInMemoryFileProcessor {

    @Test
    public void testInMemFileProcessor_Simple() {
        BatchFileProcessor processor = new InMemoryFileProcessor();
        processor.addFile("a" , 100);
        processor.addFile("g" , 400);
        processor.addFile("b" , 100 , "d2");
        processor.addFile("c" , 200 , "d1");
        processor.addFile("d" , 400 , "d3");
        processor.addFile("e" , 500 , "d3");
        processor.addFile("f" , 300 , "d4");

        System.out.println(processor.size());
        System.out.println(processor.findTopKCollections(1));
        System.out.println(processor.findTopKCollections(2));
        System.out.println(processor.findTopKCollections(3));
        System.out.println(processor.findTopKCollections(4));
        System.out.println(processor.findTopKCollections(5));

        processor.addFile("h" , 1000 , List.of("d1" , "d4"));
        processor.addCollection("d1", "d4");
        System.out.println(processor.findTopKCollections(4));
        processor.addCollection("d3", "d1");
        System.out.println(processor.findTopKCollections(4));
        processor.addFile("k" , 3000 , "d3");
        System.out.println(processor.findTopKCollections(4));
        processor.addFile("j" , 10000 , "d5");
        processor.addCollection("d5" , "d1");
        System.out.println(processor.findTopKCollections(6));



    }

    @Test
    public void testSet() {
        Set<String> s1 = new HashSet<>(List.of("a" , "b"));
        Set<String> s2 = new HashSet<>(List.of("a"));
        Set<String> s3 = new HashSet<>(List.of("d" , "e" , "f"));

        s1.removeAll(s2);
        System.out.println(s1);
        s1 = new HashSet<>(List.of("a" , "b"));
        s1.removeAll(s3);
        System.out.println(s1);

    }
}
