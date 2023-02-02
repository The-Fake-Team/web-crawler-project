package readData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public interface ReadData<T> {
    public List<T> readData() throws IOException, FileNotFoundException;
}
