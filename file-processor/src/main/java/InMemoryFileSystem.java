import model.Collection;
import model.File;

import java.util.*;
import java.util.stream.Collectors;

/**
 * File can be apart of multiple colelctions
 * <p>
 * Heirarchy of collections -> D1 -> D2 -> D3 , add file D3 ,total size of D1 should reflect
 */
public class InMemoryFileSystem implements FileSystem {
    private static final String ROOT_DIRECTORY = "/";

    private final Map<String, File> files;
    private final Map<String, Collection> directories;
    private final PriorityQueue<Collection> collectionHeap;
    private Integer fileSystemSize;

    public InMemoryFileSystem() {
        files = new HashMap<>();
        directories = new HashMap<>();
        collectionHeap = new PriorityQueue<>(Comparator.comparingInt(Collection::getSize));
        fileSystemSize = 0;
    }

    @Override
    public File addFile(String f, Integer size, String dir) {
        File file = addFile(f, size);
        Collection collection = directories.getOrDefault(dir, new Collection(dir));
        collection.addFile(file);
//        collectionHeap.remove(collection);
//        collectionHeap.add(collection);
        directories.put(dir, collection);

        return file;
    }

    @Override
    public File addFile(String f, Integer size) {
        File file = files.getOrDefault(f, new File(f, size));
        if (files.get(f) == null) {
            files.put(f, file);
            fileSystemSize += size;
        }
        return file;
    }

    @Override
    public Collection addCollection(String source, String target) {
        Collection sourceCollection = directories.getOrDefault(source, new Collection(source));
        Collection targetCollection = directories.getOrDefault(target, new Collection(target));
        return targetCollection.addCollection(sourceCollection);
    }

    @Override
    public File addFile(String f, Integer size, List<String> dirs) {
        dirs.forEach(dir -> addFile(f, size, dir));
        return files.get(f);
    }

    @Override
    public List<Collection> listTopKDirectories(Integer k) {
        List<Collection> topKDirectories = new ArrayList<>();
        Collection[] collectionArray = collectionHeap.toArray(new Collection[0]);
        while (k > 0 && !collectionHeap.isEmpty()) {
            topKDirectories.add(collectionHeap.poll());
            k--;
        }
        collectionHeap.addAll(topKDirectories);
        return topKDirectories;
    }

    @Override
    public Integer getSize() {
        return fileSystemSize;
    }
}
