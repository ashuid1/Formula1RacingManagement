import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ChampionshipManager {

    void addDriver(String driverName, String teamName);

    void deleteDriver(String driverToDelete);
    List<Driver.Formula1Driver> getDrivers();

    void saveToFile(String fileName) throws IOException;

    void loadFromFile(String fileName) throws IOException, ClassNotFoundException;


}

