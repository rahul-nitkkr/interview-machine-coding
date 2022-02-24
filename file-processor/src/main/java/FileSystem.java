import model.Collection;
import model.File;

import java.util.List;

public interface FileSystem {

    public File addFile(String f, Integer size, String dir);

    public File addFile(String f, Integer size);

    public File addFile(String f, Integer size, List<String> dirs);

    /**
     * Add Source to target
     * @param target
     * @param source
     * @return
     */
    public Collection addCollection(String source, String target);

    public List<Collection> listTopKDirectories(Integer k);

    public Integer getSize();

}
