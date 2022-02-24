import model.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fs ->
 *      /d1 ->
 *              foo1
 *              foo2
 *              foo3
 *      /d2 ->
 *              foo2
 *              foo4
 *              foo1
 *      foo5
 *      foo6
 *      foo7
 */
public class TestInMemoryFileSystem {
    private Map<String , Integer> files;
    private Map<String , List<String>> directories;
    private FileSystem fs;

    @BeforeEach
    public void setup() {
        files = new HashMap<>();
        files.put("foo1.txt" , 100);
        files.put("foo2.txt" , 700);
        files.put("foo3.txt" , 900);
        files.put("foo4.txt" , 200);
        files.put("foo5.txt" , 100);
        files.put("foo6.txt" , 400);
        files.put("foo7.txt" , 600);

        directories = new HashMap<>();
        directories.put("d1" , List.of("foo1.txt" ,"foo2.txt" , "foo3.txt"));
        directories.put("d2" , List.of("foo1.txt" ,"foo2.txt" , "foo4.txt"));

        fs = new InMemoryFileSystem();
    }
    @Test
    public void testInMemFS_Basic() {
        for(String dir : directories.keySet()) {
            for(String file: directories.get(dir)) {
                fs.addFile(file , files.get(file) , dir);
            }
        }

        for(String file: files.keySet()) {
            fs.addFile(file , files.get(file));
        }

        System.out.println(fs.listTopKDirectories(1));
        System.out.println(fs.getSize());

        System.out.println(fs.listTopKDirectories(3));
        fs.addFile("foo3.txt" , files.get("foo3.txt") , "d2");
        System.out.println(fs.listTopKDirectories(3));
        System.out.println(fs.getSize());
    }

    @Test
    public void testInMem_Empty() {
        System.out.println(fs.listTopKDirectories(2));
        System.out.println(fs.getSize());
    }

    @Test
    public void testInMem_OnlyFiles() {
        for(String file: files.keySet()) {
            fs.addFile(file , files.get(file));
        }

        System.out.println(fs.listTopKDirectories(1));
        System.out.println(fs.getSize());
    }

    @Test
    public void testInMem_FileInMultiCollection() {
        fs.addFile("foo6.txt" , files.get("foo6.txt") , List.of("d1" , "d2"));
        System.out.println(fs.getSize());
        System.out.println(fs.listTopKDirectories(2));

    }

    @Test
    public void testInMem_GraphOfCollection() {
        fs.addFile("a" , 200 , "d1");
        fs.addFile("b" , 300 , "d1");
        fs.addFile("c" , 200 , "d2");
        fs.addFile("d" , 400 , "d2");
        fs.addFile("e" , 200 , "d3");

        fs.addFile("f" , 400 , List.of("d1" , "d2"));
        fs.addFile("g" , 400 , List.of("d2" , "d3"));

        fs.addCollection("d3" , "d2");
        fs.addFile("i" , 300 , "d3");

        System.out.println(fs.getSize());
        System.out.println(fs.listTopKDirectories(2));

        fs.addCollection("d1" , "d3");
        System.out.println(fs.getSize());
        System.out.println(fs.listTopKDirectories(2));

    }
}
