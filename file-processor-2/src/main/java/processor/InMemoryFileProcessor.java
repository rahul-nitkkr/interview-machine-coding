package processor;

import processor.model.Collection;
import processor.model.File;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryFileProcessor implements BatchFileProcessor {

    private final Map<String, File> files;
    private final Map<String, Collection> collections;
    private Integer fileSystemSize;

    public InMemoryFileProcessor() {
        this.files = new HashMap<>();
        this.collections = new HashMap<>();
        this.fileSystemSize = 0;
    }

    @Override
    public File addFile(String fileName, Integer size, String collection) {
        File file = addFile(fileName, size);
        Collection target = collections.getOrDefault(collection, new Collection(collection));
        target.addFile(file);
        collections.put(collection, target);
        return file;
    }

    @Override
    public File addFile(String fileName, Integer size, List<String> collections) {
        collections.forEach(collection -> addFile(fileName, size, collection));
        return files.get(fileName);
    }

    @Override
    public File addFile(String fileName, Integer size) {
        File file = files.getOrDefault(fileName, new File(fileName, size));
        if (files.get(fileName) == null) {
            fileSystemSize += file.getSize();
            files.put(fileName, file);
        }
        return file;
    }

    @Override
    public Collection addCollection(String source, String target) {
        Collection sourceCollection = collections.getOrDefault(source, new Collection(source));
        Collection targetCollection = collections.getOrDefault(target, new Collection(target));

        targetCollection = targetCollection.addCollection(sourceCollection);
        sourceCollection.addParent(targetCollection);

        return targetCollection;
    }

    @Override
    public List<Collection> findTopKCollections(Integer k) {
        List<Collection> collections = new ArrayList<>(this.collections.values());
        collections.sort(Comparator.comparingInt(Collection::getSize).reversed());
        return collections.stream().limit(k).collect(Collectors.toList());
    }

    @Override
    public Integer size() {
        return fileSystemSize;
    }
}
