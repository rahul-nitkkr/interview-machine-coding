package model;

public class File {
    private final String name;
    private final Integer size;

    public File(String name, Integer size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        File that = (File) obj;
        return this.name.equals(that.name) && this.size == that.size;
    }
}
