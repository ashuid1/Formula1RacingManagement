import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Formula1ChampionshipGUI extends JFrame {

    private Furmula1ChampionshipManager championshipManager;

    private Driver driver;

        private Formula1ChampionshipGUI manager;

        private JTable driversTable;
        private JButton sortByPointsButton;
        private JButton sortByFirstPositionsButton;
        private JButton generateRandomRaceButton;

        public Formula1ChampionshipGUI() {
            this.manager = new Formula1ChampionshipGUI();
            

            // Add action listeners to buttons
            sortByPointsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Logic to sort table by points
                    sortTableByPoints();
                }
            });

            sortByFirstPositionsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Logic to sort table by first positions
                    sortTableByFirstPositions();
                }
            });

            generateRandomRaceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Logic to generate random race and update table
                    generateRandomRaceAndUpdateTable();
                }
            });
            
            
        }
    private void sortTableByPoints() {
        DefaultTableModel model = (DefaultTableModel) driversTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        driversTable.setRowSorter(sorter);

        // Define the sorting order based on the "Total Points" column
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(6, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    private void sortTableByFirstPositions() {
        DefaultTableModel model = (DefaultTableModel) driversTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        driversTable.setRowSorter(sorter);

        // Define the sorting order based on the "First Positions" column
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(3, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    private void generateRandomRaceAndUpdateTable() {
        // Simulate a race
        Map<Driver, Integer> racePositions = new HashMap<>();
        Random random = new Random();

        DefaultTableModel model = (DefaultTableModel) driversTable.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            // Assign random positions (1 to 10) to drivers
            int position = random.nextInt(10) + 1;
            racePositions.put((Driver) model.getValueAt(i, 0), position);
        }

        // Add the race and update the table
        championshipManager.addRace(racePositions);
        updateTable();
    }

    private void updateTable() {

        List<Driver.Formula1Driver> drivers = championshipManager.getDrivers();

        DefaultTableModel model = (DefaultTableModel) driversTable.getModel();
        model.setRowCount(0); // Clear existing rows

        for (Driver.Formula1Driver driver : drivers) {
            // Add a row for each driver with their statistics
            Object[] rowData = {driver, driver.getTeam(), driver.getRacesParticipated(),
                    driver.getFirstPositions(), driver.getSecondPositions(), driver.getThirdPositions(),
                    driver.getTotalPoints()};
            model.addRow(rowData);
        }
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Furmula1ChampionshipManager manager = new Furmula1ChampionshipManager();
        manager.runMenu();
        SwingUtilities.invokeLater(() -> {
            Formula1ChampionshipGUI gui = new Formula1ChampionshipGUI();
            gui.setSize(800, 600);
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setVisible(true);
        });
    }
}
