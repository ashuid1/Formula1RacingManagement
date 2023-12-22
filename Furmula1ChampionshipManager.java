import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;
import java.util.Date;


public class Furmula1ChampionshipManager implements ChampionshipManager {
    Furmula1ChampionshipManager manager = new Furmula1ChampionshipManager();

    private List<Driver.Formula1Driver> drivers;

    public Furmula1ChampionshipManager() throws IOException, ClassNotFoundException {
        drivers = new ArrayList<>();
    }

    public void runMenu() throws IOException, ClassNotFoundException {
    Scanner sc = new Scanner(System.in);

    while(true){

        System.out.println("Menu:");
        System.out.println("1. Create a new driver");
        System.out.println("2. Delete a driver and their team");
        System.out.println("3. Change the driver for an existing team");
        System.out.println("4. Display statistics for a selected driver");
        System.out.println("5. Display Formula 1 Driver Table");
        System.out.println("6. Add a completed race");
        System.out.println("7. Save to file");
        System.out.println("8. Load from file");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter driver name: ");
                String driverName = sc.nextLine();
                System.out.print("Enter team name: ");
                String teamName = sc.nextLine();
                manager.addDriver(driverName, teamName);
                break;

            case 2:
                System.out.print("Enter driver name to delete: ");
                String driverToDelete = sc.nextLine();
                manager.deleteDriver(driverToDelete);
                break;

            case 3:
                System.out.print("Enter team name to change driver: ");
                String teamToChange = sc.nextLine();
                System.out.print("Enter new driver name: ");
                String newDriverName = sc.nextLine();
                manager.changeDriverForTeam(teamToChange, newDriverName);
                break;

            case 4:
                System.out.print("Enter driver name to display statistics: ");
                String driverToDisplayStats = sc.nextLine();
                manager.displayDriverStatistics(driverToDisplayStats);
                break;

            case 5:
                manager.displayDriverTable();
                break;

            case 6:
                System.out.print("Enter race date (yyyy-MM-dd): ");
                String raceDateStr = sc.nextLine();
                Date raceDate = DateUtils.parseDate(raceDateStr);
                Map<Driver, Integer> racePositions = new HashMap<>();

                // Assume 5 drivers for simplicity, you may extend this based on your requirements
                for (int i = 0; i < 5; i++) {
                    System.out.print("Enter position for driver " + (i + 1) + ": ");
                    int position = sc.nextInt();
                    racePositions.put(manager.getDrivers().get(i), position);
                }
                manager.addRace(racePositions);
                break;

            case 7:
                System.out.print("Enter file name to save: ");
                String saveFileName = sc.nextLine();
                manager.saveToFile(saveFileName);
                break;

            case 8:
                System.out.print("Enter file name to load: ");
                String loadFileName = sc.nextLine();
                manager.loadFromFile(loadFileName);
                break;

            case 0:
                System.out.println("Exiting the program. Goodbye!");
                System.exit(0);
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
        }
    }

    public void loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            drivers = (List<Driver.Formula1Driver>) ois.readObject();

        }
    }

    public void saveToFile(String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(drivers);
        }
    }

    private void displayDriverTable() {
        JFrame frame = new JFrame("Formula 1 Driver Table");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create column names
        String[] columnNames = {"Driver", "Team", "Races Participated", "First Positions", "Second Positions", "Third Positions", "Total Points"};

        // Create data for the table
        Object[][] data = new Object[drivers.size()][7];
        for (int i = 0; i < drivers.size(); i++) {
            Driver.Formula1Driver driver = drivers.get(i);
            data[i][0] = driver.getName();
            data[i][1] = driver.getTeam();
            data[i][2] = driver.getRacesParticipated();
            data[i][3] = driver.getFirstPositions();
            data[i][4] = driver.getSecondPositions();
            data[i][5] = driver.getThirdPositions();
            data[i][6] = driver.getTotalPoints();
        }

        // Create a table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Create a JTable with the model
        JTable table = new JTable(model);

        // Create a scroll pane to contain the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        frame.getContentPane().add(scrollPane);

        // Set frame properties
        frame.setSize(800, 600);
        frame.setVisible(true);


    }

    public void addRace(Map<Driver, Integer> racePositions) {
        for (Map.Entry<Driver, Integer> entry : racePositions.entrySet()) {
            Driver drivers = entry.getKey();
            int position = entry.getValue();
            drivers.awardPoints(position);
        }
        System.out.println("Race added successfully!");
    }

    private void displayDriverStatistics(String driverName) {
        Optional<Driver.Formula1Driver> optionalDriver = drivers.stream()
                .filter(driver -> driver.getName().equalsIgnoreCase(driverName))
                .findFirst();

        if (optionalDriver.isPresent()) {
            Driver.Formula1Driver driver = optionalDriver.get();
            driver.displayStatistics();
        } else {
            System.out.println("Driver not found: " + driverName);
        }
    }

    public List<Driver.Formula1Driver> getDrivers() {
        return drivers;
    }

    private void changeDriverForTeam(String teamName, String newDriverName) {
        for (Driver.Formula1Driver driver : drivers) {
            if (driver.getTeam().equals(teamName)) {
                driver.setName(newDriverName);
                System.out.println("Driver for team " + teamName + " changed to " + newDriverName);
                return;
            }
        }
        System.out.println("Team " + teamName + " not found or no driver assigned.");
    }

    @Override
    public void addDriver(String driverName, String teamName) {
        Driver.Formula1Driver driver = new Driver.Formula1Driver(driverName, teamName);
        drivers.add(driver);
    }

    @Override
    public void deleteDriver(String driverName) {
        Iterator<Driver.Formula1Driver> iterator = drivers.iterator();
        while (iterator.hasNext()) {
            Driver.Formula1Driver driver = iterator.next();
            if (driver.getName().equals(driverName)) {
                iterator.remove();
                System.out.println("Driver " + driverName + " and their team have been deleted.");
                return;
            }
        }
        System.out.println("Driver " + driverName + " not found.");

    }
}
