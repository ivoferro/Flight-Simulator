/**
 * Package location for UI classes.
 */
package lapr.project.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.measure.quantity.Mass;
import javax.measure.unit.SI;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import lapr.project.datalayer.dao.FlightInfoDao;
import lapr.project.datalayer.dao.FlightSimulationDAO;
import lapr.project.datalayer.oracle.AirportOracle;
import lapr.project.datalayer.oracle.FlightInfoOracle;
import lapr.project.datalayer.oracle.FlightSimulationOracle;
import lapr.project.model.Airport;
import lapr.project.model.FlightInfo;
import lapr.project.model.FlightSimulation;
import lapr.project.model.Project;
import lapr.project.model.FlightSimulator;
import lapr.project.ui.components.CustomMenuBar;
import lapr.project.ui.components.TableModelFlightSimulation;
import lapr.project.utils.Consts;
import org.jscience.physics.amount.Amount;

/**
 * The main frame for the application.
 *
 * @author Daniel Gonçalves - 1151452
 * @author Eric Amaral - 1141570
 * @author Ivo Ferro - 1151159
 * @author Tiago Correia - 1151031
 */
public class MainFrame extends JFrame implements ProjectHandler {

    /**
     * The app simulator.
     */
    private final FlightSimulator simulator;

    /**
     * Active Project.
     */
    private Project activeProject;

    /**
     * Title for the frame.
     */
    private static final String WINDOW_TITLE = "Flight Simulator";

    /**
     * Padding border.
     */
    private static final EmptyBorder PADDING_BORDER = new EmptyBorder(50, 50, 50, 50);

    /**
     * Page padding border.
     */
    private static final EmptyBorder PAGE_PADDING_BORDER = new EmptyBorder(50, 50, 50, 50);

    /**
     * The button prefered size.
     */
    private static final Dimension BUTTON_PREFERED_SIZE = new Dimension(150, 30);

    /**
     * The simulations.
     */
    private List<FlightSimulation> simulations;

    /**
     * The project title label.
     */
    private JLabel projectTitleLabel;

    /**
     * The project description label.
     */
    private JLabel projectDescriptionLabel;

    /**
     * The simulations table.
     */
    private JTable simulationsTable;

    /**
     * Create flight info button.
     */
    private JButton createFlightInfoButton;

    /**
     * Create Simulation button.
     */
    private JButton createSimulationButton;

    /**
     * Create open simulation button.
     */
    private JButton openSimulationButton;

    /**
     * The export simulations button.
     */
    private JButton exportByAirportButton;

    /**
     * The export by aircraft button.
     */
    private JButton exportByAircraftButton;

    /**
     * The export by flight info button.
     */
    private JButton exportByFlightInfoButton;

    /**
     * The button to calculate exact fuel.
     */
    private JButton calculateExactFuel;

    /**
     * Creates an instance of the main frame.
     *
     * @param simulator the simulator
     */
    public MainFrame(FlightSimulator simulator) {
        super(WINDOW_TITLE);

        // Set simulator
        this.simulator = simulator;
        // Instanciate active project
        this.activeProject = null;

        // Set Custom Menu Bar
        CustomMenuBar customMenuBar = new CustomMenuBar(this, simulator);
        setJMenuBar(customMenuBar);

        createComponents();

        pack();
    }

    public void openProjectSelection() {
        ProjectSelectionDialog projectSelectionDialog = new ProjectSelectionDialog(this, simulator);
        projectSelectionDialog.setVisible(true);
        projectSelectionDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Creates the components for frame.
     */
    private void createComponents() {

        // TODO: Set main panel not visble
        JPanel componentsPanel = new JPanel(new BorderLayout(10, 10));

        componentsPanel.add(projectInfoPanel(), BorderLayout.NORTH);
        componentsPanel.add(createTablePanel(), BorderLayout.CENTER);
        componentsPanel.add(createButtonsPanel(), BorderLayout.SOUTH);

        componentsPanel.setBorder(PAGE_PADDING_BORDER);
        add(componentsPanel);
    }

    public Project getActiveProject() {
        return this.activeProject;
    }

    /**
     * Creates the project info panel.
     *
     * @return project info panel
     */
    private JPanel projectInfoPanel() {
        JPanel projectInfoPanel = new JPanel(new BorderLayout());

        projectTitleLabel = new JLabel("", SwingConstants.CENTER);
        projectTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        projectDescriptionLabel = new JLabel("", SwingConstants.CENTER);
        projectDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        projectInfoPanel.add(projectTitleLabel, BorderLayout.NORTH);
        projectInfoPanel.add(projectDescriptionLabel, BorderLayout.SOUTH);

        return projectInfoPanel;
    }

    /**
     * Creates the table panel.
     *
     * @return table panel
     */
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        simulationsTable = new JTable(new TableModelFlightSimulation(new ArrayList<>()));
        ListSelectionModel listSelectionModel = simulationsTable.getSelectionModel();
        listSelectionModel.addListSelectionListener((ListSelectionEvent lse) -> {
            openSimulationButton.setEnabled(!listSelectionModel.isSelectionEmpty());
            calculateExactFuel.setEnabled(!listSelectionModel.isSelectionEmpty());
        });

        JScrollPane scrollPane = new JScrollPane(simulationsTable);
        scrollPane.setBorder(PADDING_BORDER);

        tablePanel.add(scrollPane);
        simulationsTable.setVisible(false);

        return tablePanel;
    }

    /**
     * Creates the buttons panel.
     *
     * @return buttons panel
     */
    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new BorderLayout(10, 10));
        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel bottomButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        openSimulationButton = new JButton("Open Simulation");
        openSimulationButton.setPreferredSize(BUTTON_PREFERED_SIZE);
        openSimulationButton.setEnabled(false);
        openSimulationButton.addActionListener((ActionEvent ae) -> {
            ShowSimulation showSimulation = new ShowSimulation(this, simulations.get(simulationsTable.getSelectedRow()), activeProject.getSerieNumber());
            showSimulation.setVisible(true);
        });

        createFlightInfoButton = new JButton("New Flight Info");
        createFlightInfoButton.setPreferredSize(BUTTON_PREFERED_SIZE);
        createFlightInfoButton.setEnabled(false);
        createFlightInfoButton.addActionListener((ActionEvent ae) -> {
            FlightInfoDialog flightInfoDialog = new FlightInfoDialog(this, getActiveProject());
            flightInfoDialog.setVisible(true);
        });

        createSimulationButton = new JButton("New Simulation");
        createSimulationButton.setPreferredSize(BUTTON_PREFERED_SIZE);
        createSimulationButton.setEnabled(false);
        createSimulationButton.addActionListener((ActionEvent ae) -> {
            SimulateFlightDialog simulateFlightDialog = new SimulateFlightDialog(this, activeProject);
            simulateFlightDialog.setVisible(true);
        });

        calculateExactFuel = new JButton("Calculate Exact Fuel");
        calculateExactFuel.setPreferredSize(BUTTON_PREFERED_SIZE);
        calculateExactFuel.setEnabled(false);
        calculateExactFuel.addActionListener((ActionEvent ae) -> {
            FlightSimulation flightSimulation = simulations.get(simulationsTable.getSelectedRow());
            Amount<Mass> exactFuel = Amount.valueOf(145571d, SI.KILOGRAM);
            try {
                exactFuel = flightSimulation.getExactFuel(activeProject.getSerieNumber());
            } catch (Exception ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(
                    null,
                    String.format("The exact fuel with reserve for this flight should be %.0f liters.", exactFuel.doubleValue(SI.KILOGRAM) / Consts.LITER_CONV),
                    "Exact Fuel",
                    JOptionPane.WARNING_MESSAGE);
        });

        exportByAirportButton = new JButton("Export By Airport");
        exportByAirportButton.setPreferredSize(BUTTON_PREFERED_SIZE);
        exportByAirportButton.setEnabled(false);
        exportByAirportButton.addActionListener((ActionEvent ae) -> {
            AirportOracle airportDAO = new AirportOracle(activeProject.getSerieNumber());
            List<Airport> airports = new ArrayList<>();
            try {
                airports = airportDAO.getAirports();
            } catch (SQLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            FilterAirportsDialog filterAirportsDialog = new FilterAirportsDialog(this, airports, simulations);
            filterAirportsDialog.setVisible(true);
        });

        exportByAircraftButton = new JButton("Export By Aircraft");
        exportByAircraftButton.setPreferredSize(BUTTON_PREFERED_SIZE);
        exportByAircraftButton.setEnabled(false);
        exportByAircraftButton.addActionListener((ActionEvent ae) -> {
            FilterAircraftsDialog filterAircraftsDialog
                    = new FilterAircraftsDialog(this, simulations,
                            activeProject.getSerieNumber());
            filterAircraftsDialog.setVisible(true);
        });

        exportByFlightInfoButton = new JButton("Export By Flight Info");
        exportByFlightInfoButton.setPreferredSize(BUTTON_PREFERED_SIZE);
        exportByFlightInfoButton.setEnabled(false);
        exportByFlightInfoButton.addActionListener((ActionEvent ae) -> {
            FlightInfoDao flightInfoDAO = new FlightInfoOracle(activeProject.getSerieNumber());
            List<FlightInfo> flights = new ArrayList();
            try {
                flights = flightInfoDAO.getFlightsInfo();
            } catch (Exception ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            SelectFlightInfosDialog flightInfosDialog = new SelectFlightInfosDialog(this, flights);
            flightInfosDialog.setVisible(true);
        });

        topButtons.add(openSimulationButton);
        topButtons.add(calculateExactFuel);
        topButtons.add(createFlightInfoButton);
        topButtons.add(createSimulationButton);
        bottomButtons.add(exportByAirportButton);
        bottomButtons.add(exportByAircraftButton);
        bottomButtons.add(exportByFlightInfoButton);

        buttonsPanel.add(topButtons, BorderLayout.NORTH);
        buttonsPanel.add(bottomButtons, BorderLayout.CENTER);

        return buttonsPanel;
    }

    /**
     * Activate a given project.
     *
     * @param project project to activate
     */
    @Override
    public void activateProject(Project project) {
        this.activeProject = project;
        refreshProject();
    }

    /**
     * Refresh the main frame fields.
     */
    private void refreshProject() {
        this.projectTitleLabel.setText(activeProject.getName());
        this.projectDescriptionLabel.setText(activeProject.getDescription());
        this.simulationsTable.setVisible(true);
        createFlightInfoButton.setEnabled(true);
        createSimulationButton.setEnabled(true);
        exportByAirportButton.setEnabled(true);
        exportByAircraftButton.setEnabled(true);
        exportByFlightInfoButton.setEnabled(true);
        refreshFlighSimulations();
    }

    /**
     * Refresh the flight simulations.
     */
    public void refreshFlighSimulations() {
        FlightSimulationDAO flightSimulationDAO = new FlightSimulationOracle(activeProject.getSerieNumber());
        simulations = new ArrayList<>();
        try {
            simulations = flightSimulationDAO.getFlightSimulations();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Can't load simulations. The server is busy. Try later.",
                    "Database busy",
                    JOptionPane.WARNING_MESSAGE);
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        simulationsTable.setModel(new TableModelFlightSimulation(simulations));
    }
}
