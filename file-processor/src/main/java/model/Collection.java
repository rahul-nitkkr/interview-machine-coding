package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Collection {
    private final String name;
    private final Set<File> files;
    private final Set<Collection> collections;
    private Integer size;

    public Collection(String name) {
        this.name = name;
        this.files = new HashSet<>();
        this.collections = new HashSet<>();
        this.size = 0;
    }

    public String getName() {
        return name;
    }

    public List<File> getFiles() {

        return new ArrayList<>(files);
    }

    public Set<Collection> getCollections() {
        return collections;
    }

    public Integer getSize() {
        return size;
    }

    public void addFile(File f) {
        if (!files.contains(f)) {
            files.add(f);
            size += f.getSize();
        }
    }

    public Collection addCollection(Collection collection) {
        if (!collections.contains(collection)) {
            Set<File> filesInCollection = new HashSet<>(getFilesInCollection(collection));
            filesInCollection.addAll(files);
            size = filesInCollection.stream().mapToInt(File::getSize).sum();
            collections.add(collection);
        }
        return this;
    }

    private List<File> getFilesInCollection(Collection collection) {
        if (collection.getCollections().size() == 0) {
            return new ArrayList<>(collection.getFiles());
        }
        List<File> files = new ArrayList<>(collection.getFiles());
        for (Collection c : collection.getCollections()) {
            files.addAll(getFilesInCollection(c));
        }

        return files;
    }

    @Override
    public String toString() {
        return name;
    }
}
