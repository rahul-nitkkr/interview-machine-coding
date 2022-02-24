package processor;

import processor.model.Collection;
import processor.model.File;

import java.util.List;

public interface BatchFileProcessor {

    public File addFile(String fileName , Integer size , String collection);

    public File addFile(String fileName , Integer size , List<String> collections);

    public File addFile(String fileName , Integer size);

    public Collection addCollection(String source , String target);

    public List<Collection> findTopKCollections(Integer k);

    public Integer size();

}
