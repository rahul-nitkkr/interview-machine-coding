package processor.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Collection {
    private final String name;
    private final Set<File> files;
    private final Set<Collection> children;
    private final Set<Collection> parents;
    private Integer size;

    public Collection(String name) {
        this.name = name;
        this.files = new HashSet<>();
        this.children = new HashSet<>();
        this.parents = new HashSet<>();
        this.size = 0;
    }

    public String getName() {
        return name;
    }

    public Set<File> getFiles() {
        return files;
    }

    public Set<Collection> getChildren() {
        return children;
    }

    public Integer getSize() {
        return size;
    }

    public Set<Collection> getParents() {
        return parents;
    }

    public void incrementSize(Integer sizeOfChild) {
        this.size += sizeOfChild;
    }

    public void addFile(File file) {
        if (!files.contains(file)) {
            if (this.getParents().size() != 0) {
                Set<File> thisFile = new HashSet<>();
                thisFile.add(file);
                for (Collection parent : this.getParents())
                    incrementParentSize(parent, thisFile);
            }
            this.size += file.getSize();
            files.add(file);
        }
    }

    public void addParent(Collection parent) {
        this.parents.add(parent);
    }

    private void incrementParentSize(Collection parent, Set<File> childFiles) {
        if (parent.getParents().size() == 0) {
            Set<File> parentFiles = new HashSet<>(parent.getFiles());
            parentFiles.addAll(getFilesInChildren(parent));
            childFiles.removeAll(parentFiles);
            parent.incrementSize(childFiles.stream().mapToInt(File::getSize).sum());
            return;
        }

        for (Collection p : parent.getParents()) {
            incrementParentSize(p, childFiles);
            Set<File> parentFiles = new HashSet<>(parent.getFiles());
            parentFiles.addAll(getFilesInChildren(parent));
            childFiles.removeAll(parentFiles);
            parent.incrementSize(childFiles.stream().mapToInt(File::getSize).sum());
        }
    }

    public Collection addCollection(Collection child) {
        if (!children.contains(child)) {
            Set<File> childFiles = child.getFiles();
            if (this.getParents().size() != 0) {
                for (Collection parent : this.getParents())
                    incrementParentSize(parent, childFiles);
            }
            size = getSizeOfFilesInChildren(child);
            children.add(child);
        }


        return this;
    }

    private Integer getSizeOfFilesInChildren(Collection child) {
        Set<File> filesInCollection = new HashSet<>(getFilesInChildren(child));
        filesInCollection.addAll(files);
        return filesInCollection.stream().mapToInt(File::getSize).sum();
    }

    private List<File> getFilesInChildren(Collection collection) {
        if (collection.getChildren().size() == 0) {
            return new ArrayList<>(collection.getFiles());
        }
        List<File> filesInCollection = new ArrayList<>(collection.getFiles());
        for (Collection child : collection.getChildren()) {
            filesInCollection.addAll(getFilesInChildren(child));
        }

        return filesInCollection;
    }

    @Override
    public String toString() {
        return name;
    }
}
