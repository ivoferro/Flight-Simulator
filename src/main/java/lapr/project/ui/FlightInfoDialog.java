/**
 * Package location for UI classes.
 */
package lapr.project.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import lapr.project.model.AircraftModel;
import lapr.project.model.Airport;
import lapr.project.model.FlightInfo;
import lapr.project.model.FlightSimulator;
import lapr.project.model.FlightType;
import lapr.project.model.Project;
import lapr.project.ui.components.ListCellRendererAircraftModel;
import lapr.project.ui.components.ListCellRendererAirport;

/**
 * The frame to create project.
 *
 * @author Daniel Gonçalves - 1151452
 * @author Eric Amaral - 1141570
 * @author Ivo Ferro - 1151159
 * @author Tiago Correia - 1151031
 *
 * @param <T> window that extend ProjectHandler
 *
 */
public class FlightInfoDialog<T extends Window & ProjectHandler> extends JDialog {

    /**
     * The parent window.
     */
    private final T parentWindow;

    /**
     * The active project.
     */
    private final Project project;

    /**
     * The flight info.
     */
    private FlightInfo flightInfo;

    /**
     * The flight type.
     */
    private FlightType flightType;

    /**
     * The layout for the forms.
     */
    private CardLayout cl;

    /**
     * The current active form.
     */
    private int currentForm = 1;

    /**
     * The selected aircraft model.
     */
    private AircraftModel aircraftModel;

    /**
     * The forms panel.
     */
    private JPanel formsPanel;

    /**
     * The selectAircraft label.
     */
    private JLabel aircraftInfoLabel;

    /**
     * The class label.
     */
    private JLabel aircraftConfigLabel;

    /**
     * The addFlightInfo label.
     */
    private JLabel addFlightInfoLabel;

    /**
     * The name label.
     */
    private JLabel nameLabel;

    /**
     * The next button.
     */
    private JButton nextButton;

    /**
     * The previous button.
     */
    private JButton previousButton;

    /**
     * The number of classes text field.
     */
    private JTextField txtNumClasses;

    /**
     * The company name text field.
     */
    private JTextField txtCompanyName;

    /**
     * The flight info name text field.
     */
    private JTextField txtFlightInfoName;

    /**
     * The maximun number of crew elements text field.
     */
    private JTextField txtMaxCrew;

    /**
     * The maximum cargo text field.
     */
    private JTextField txtMaxCargo;

    /**
     * The selected origin airport.
     */
    private Airport originAirport;

    /**
     * The selected destination airport.
     */
    private Airport destinationAirport;

    /**
     * Padding border.
     */
    private final static EmptyBorder PADDING_BORDER = new EmptyBorder(10, 10, 10, 10);

    /**
     * The default grey line border for the panels.
     */
    private final static Border DEFAULT_GREY_LINE_BORDER = BorderFactory.createLineBorder(Color.DARK_GRAY);

    /**
     * The button preferred size.
     */
    private final static Dimension BUTTON_PREFERRED_SIZE = new Dimension(100, 30);

    /**
     * The preferred size for bigger buttons.
     */
    private final static Dimension BUTTON_PREFERRED_BIG_SIZE = new Dimension(200, 30);

    /**
     * The label preferred size.
     */
    private final static Dimension LABEL_PREFERRED_SIZE = new Dimension(100, 35);

    /**
     * The orientation panel size.
     */
    private final static Dimension ORIENTATION_PANEL_SIZE = new Dimension(225, 380);

    /**
     * The main panel size.
     */
    private final static Dimension MAIN_PANEL_SIZE = new Dimension(600, 380);

    /**
     * The buttons panel size.
     */
    private final static Dimension BUTTONS_PANEL_SIZE = new Dimension(600, 50);

    /**
     * The plain label font.
     */
    private final static Font PLAIN_LABEL_FONT = new Font("Helvetica", Font.PLAIN, 15);

    /**
     * The bold label font.
     */
    private final static Font BOLD_LABEL_FONT = new Font("Helvetica", Font.BOLD, 15);

    /**
     * The plain label font.
     */
    private final static Font FORM_LABEL_FONT = new Font("Arial", Font.BOLD, 13);

    /**
     * The color for the dialog panels.
     */
    private final static Color DEFAULT_COLOR = new Color(220, 220, 220);

    /**
     * Title for the frame.
     */
    private static final String WINDOW_TITLE = "Create Flight Info";

    /**
     * Creates an instance of the flight info dialog.
     *
     * @param parentWindow the parent window
     * @param simulator the simulator
     * @param project
     */
    public FlightInfoDialog(T parentWindow, FlightSimulator simulator, Project project) {
        super(parentWindow, WINDOW_TITLE);
        setModal(true);
        this.parentWindow = parentWindow;
        this.project = project;
        this.setResizable(false);

        createComponents();

        pack();
        setMinimumSize(new Dimension(getWidth(), getHeight()));
        setLocationRelativeTo(parentWindow);
    }

    /**
     * Creates the visual components.
     */
    private void createComponents() {
        JPanel componentsPanel = new JPanel(new BorderLayout(10, 10));

        componentsPanel.add(createOrientationPanel(), BorderLayout.WEST);
        componentsPanel.add(createMainPanel(), BorderLayout.EAST);

        componentsPanel.setBorder(PADDING_BORDER);
        add(componentsPanel);
    }

    /**
     * Creates the orientation panel.
     *
     * @return orientation panel
     */
    private Component createOrientationPanel() {
        JPanel orientationPanel = new JPanel(new BorderLayout());
        orientationPanel.setPreferredSize(ORIENTATION_PANEL_SIZE);
        orientationPanel.setBorder(DEFAULT_GREY_LINE_BORDER);
        orientationPanel.setBackground(DEFAULT_COLOR);
        orientationPanel.add(createLabelsPanel(), BorderLayout.NORTH);

        return orientationPanel;
    }

    /**
     * Creates the labels panel that contains the labels describing the
     * different form stages.
     *
     * @return the labels panel
     */
    private Component createLabelsPanel() {
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
        labelsPanel.setBackground(DEFAULT_COLOR);
        labelsPanel.setBorder(PADDING_BORDER);

        labelsPanel.add(createAircraftInfoLabel());
        labelsPanel.add(createAircraftConfigurationLabel());
        labelsPanel.add(createAddFlightInfoLabel());
        labelsPanel.add(createFlightNameLabel());

        return labelsPanel;
    }

    /**
     * Creates the aircraft info label.
     *
     * @return the aircraft info label
     */
    private Component createAircraftInfoLabel() {
        aircraftInfoLabel = new JLabel("Aircraft Information");
        aircraftInfoLabel.setPreferredSize(LABEL_PREFERRED_SIZE);
        aircraftInfoLabel.setFont(BOLD_LABEL_FONT);
        aircraftInfoLabel.setBorder(PADDING_BORDER);
        return aircraftInfoLabel;
    }

    /**
     * Creates the set aircraft configuration label.
     *
     * @return aircraft configuration label
     */
    private Component createAircraftConfigurationLabel() {
        aircraftConfigLabel = new JLabel("Set Aircraft Configuration");
        aircraftConfigLabel.setPreferredSize(LABEL_PREFERRED_SIZE);
        aircraftConfigLabel.setFont(PLAIN_LABEL_FONT);
        aircraftConfigLabel.setBorder(PADDING_BORDER);
        return aircraftConfigLabel;
    }

    /**
     * Creates the add flight info label.
     *
     * @return the add flight info label
     */
    private Component createAddFlightInfoLabel() {
        addFlightInfoLabel = new JLabel("Add Flight Info");
        addFlightInfoLabel.setPreferredSize(LABEL_PREFERRED_SIZE);
        addFlightInfoLabel.setFont(PLAIN_LABEL_FONT);
        addFlightInfoLabel.setBorder(PADDING_BORDER);
        return addFlightInfoLabel;
    }

    /**
     * Creates the flight name label.
     *
     * @return the flight name label
     */
    private Component createFlightNameLabel() {
        nameLabel = new JLabel("Set Flight Name");
        nameLabel.setPreferredSize(LABEL_PREFERRED_SIZE);
        nameLabel.setFont(PLAIN_LABEL_FONT);
        nameLabel.setBorder(PADDING_BORDER);
        return nameLabel;
    }

    /**
     * Creates the main panel.
     *
     * @return the main panel
     */
    private Component createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setPreferredSize(MAIN_PANEL_SIZE);
        mainPanel.add(createFormsPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonsPanel(), BorderLayout.SOUTH);
        return mainPanel;
    }

    /**
     * Creates the forms panel.
     *
     * @return the forms panel
     */
    private Component createFormsPanel() {
        cl = new CardLayout();
        formsPanel = new JPanel();
        formsPanel.setLayout(cl);

        formsPanel.add(createAircraftInfoForm(), "1");
        formsPanel.add(createSetAircraftConfigurationForm(), "2");
        formsPanel.add(createAddFlightInfoForm(), "3");
        formsPanel.add(createNameForm(), "4");

        return formsPanel;
    }

    /**
     * Creates the aircraft info form panel.
     *
     * @return the aircraft info form
     */
    private Component createAircraftInfoForm() {
        JPanel aircraftInfoForm = new JPanel();
        GroupLayout layout = new GroupLayout(aircraftInfoForm);
        aircraftInfoForm.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        aircraftInfoForm.setBackground(DEFAULT_COLOR);
        aircraftInfoForm.setBorder(DEFAULT_GREY_LINE_BORDER);

        JLabel selectAircraftModelLabel = new JLabel("Select the Aircraft Model:");
        selectAircraftModelLabel.setFont(FORM_LABEL_FONT);

        JComboBox<AircraftModel> aircraftModelComboBox = new JComboBox<>();
        aircraftModelComboBox.setPreferredSize(new Dimension(300, 25));
        for (AircraftModel aircraftModel : project.getAircraftModelsRegister().getAircraftModels()) {
            aircraftModelComboBox.addItem(aircraftModel);
        }
        aircraftModelComboBox.setRenderer(new ListCellRendererAircraftModel());

        aircraftModelComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                aircraftModel = (AircraftModel) aircraftModelComboBox.getSelectedItem();
            }
        });

        JLabel numClassesLabel = new JLabel("Number of classes in the aircraft:");
        numClassesLabel.setFont(FORM_LABEL_FONT);

        txtNumClasses = new JTextField();
        txtNumClasses.setPreferredSize(new Dimension(30, 25));

        JLabel companyNameLabel = new JLabel("Company Name:");
        companyNameLabel.setFont(FORM_LABEL_FONT);

        txtCompanyName = new JTextField();
        txtCompanyName.setPreferredSize(new Dimension(300, 25));

        //align horizontally
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(selectAircraftModelLabel)
                        .addComponent(numClassesLabel)
                        .addComponent(companyNameLabel)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(aircraftModelComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNumClasses, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCompanyName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                )
        );

        //align vertically
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(selectAircraftModelLabel)
                        .addComponent(aircraftModelComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(numClassesLabel)
                        .addComponent(txtNumClasses, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(companyNameLabel)
                        .addComponent(txtCompanyName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                )
        );

        return aircraftInfoForm;
    }

    /**
     * Creates the aircraft configuration form panel.
     *
     * @return the aircraft configuration form panel
     */
    private Component createSetAircraftConfigurationForm() {
        JPanel aircraftConfigForm = new JPanel();
        GroupLayout layout = new GroupLayout(aircraftConfigForm);
        aircraftConfigForm.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        aircraftConfigForm.setBackground(DEFAULT_COLOR);
        aircraftConfigForm.setBorder(DEFAULT_GREY_LINE_BORDER);

        JLabel maxCrewLabel = new JLabel("Maximum Crew Elements:");
        maxCrewLabel.setFont(FORM_LABEL_FONT);

        txtMaxCrew = new JTextField();
        txtMaxCrew.setPreferredSize(new Dimension(30, 25));

        JLabel maxCargoLabel = new JLabel("Maximum Cargo Weight (Kg):");
        maxCargoLabel.setFont(FORM_LABEL_FONT);

        txtMaxCargo = new JTextField();
        txtMaxCargo.setPreferredSize(new Dimension(80, 25));

        //align horizontally
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(maxCrewLabel)
                        .addComponent(maxCargoLabel)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(txtMaxCrew, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMaxCargo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                )
        );

        //align vertically
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(maxCrewLabel)
                        .addComponent(txtMaxCrew, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(maxCargoLabel)
                        .addComponent(txtMaxCargo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                )
        );

        return aircraftConfigForm;
    }

    /**
     * Creates the add flight info form panel.
     *
     * @return the add flight info form
     */
    private Component createAddFlightInfoForm() {
        JPanel addFlightInfoForm = new JPanel(new BorderLayout());
        addFlightInfoForm.setBackground(DEFAULT_COLOR);
        addFlightInfoForm.setBorder(DEFAULT_GREY_LINE_BORDER);

        JPanel addFlightInfoGroupLayout = new JPanel();
        GroupLayout layout = new GroupLayout(addFlightInfoGroupLayout);
        addFlightInfoGroupLayout.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        addFlightInfoGroupLayout.setBackground(DEFAULT_COLOR);

        JLabel originAirportLabel = new JLabel("Origin Airport:");
        originAirportLabel.setFont(FORM_LABEL_FONT);

        JComboBox<Airport> originAirportComboBox = new JComboBox<>();
        originAirportComboBox.setPreferredSize(new Dimension(420, 25));
        //populate origin airport combobox
        for (Airport airport : project.getAirportsRegister().getAirports()) {
            originAirportComboBox.addItem(airport);
        }
        originAirportComboBox.setRenderer(new ListCellRendererAirport());
        originAirportComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                originAirport = (Airport) originAirportComboBox.getSelectedItem();
            }
        });

        JLabel destinationAirportLabel = new JLabel("Destination Airport:");
        destinationAirportLabel.setFont(FORM_LABEL_FONT);

        JComboBox<Airport> destinationAirportComboBox = new JComboBox<>();
        destinationAirportComboBox.setPreferredSize(new Dimension(420, 25));
        //populate destination airport combobox
        for (Airport airport : project.getAirportsRegister().getAirports()) {
            destinationAirportComboBox.addItem(airport);
        }
        destinationAirportComboBox.setRenderer(new ListCellRendererAirport());
        destinationAirportComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                destinationAirport = (Airport) destinationAirportComboBox.getSelectedItem();
            }
        });

        JLabel flightTypeLabel = new JLabel("Flight Type:");
        flightTypeLabel.setFont(FORM_LABEL_FONT);

        JComboBox<FlightType> flightTypeComboBox = new JComboBox<>(FlightType.values());
        flightTypeComboBox.setPreferredSize(new Dimension(100, 25));
        flightTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                flightType = (FlightType) flightTypeComboBox.getSelectedItem();
            }
        });

        JButton addTechStopButton = createAddTechStopButton();
        JButton addWaypointsButton = createAddWaypointButton();

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS)); // aligns the buttons vertically
        panelButtons.setBackground(DEFAULT_COLOR);
        panelButtons.add(addTechStopButton);
        panelButtons.add(Box.createRigidArea(new Dimension(0, 20))); //creates a gap between buttons
        panelButtons.add(addWaypointsButton);
        panelButtons.setBorder(new EmptyBorder(20, 200, 0, 200)); //centers the buttons in the panel
        addTechStopButton.setMaximumSize(BUTTON_PREFERRED_BIG_SIZE);
        addWaypointsButton.setMaximumSize(BUTTON_PREFERRED_BIG_SIZE);

        //align horizontally
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(originAirportLabel)
                        .addComponent(destinationAirportLabel)
                        .addComponent(flightTypeLabel)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(originAirportComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(destinationAirportComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(flightTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                )
        );

        //align vertically
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(originAirportLabel)
                        .addComponent(originAirportComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(destinationAirportLabel)
                        .addComponent(destinationAirportComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(flightTypeLabel)
                        .addComponent(flightTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
        );

        addFlightInfoForm.add(addFlightInfoGroupLayout, BorderLayout.NORTH);
        addFlightInfoForm.add(panelButtons, BorderLayout.CENTER);

        return addFlightInfoForm;
    }

    /**
     * Creates the add technical stop button used in the flight info form panel.
     *
     * @return the add technical stop button
     */
    private JButton createAddTechStopButton() {
        JButton addTechStopButton = new JButton("Add Technical Stop");
        addTechStopButton.addActionListener((ActionEvent ae) -> {
            //TODO
        });
        return addTechStopButton;
    }

    /**
     * Creates the add waypoints button used in the flight info form panel.
     *
     * @return the add waypoints button
     */
    private JButton createAddWaypointButton() {
        JButton addWaypointButton = new JButton("Add Waypoint");
        addWaypointButton.addActionListener((ActionEvent ae) -> {
            //TODO
        });
        return addWaypointButton;
    }

    /**
     * Creates the name form panel.
     *
     * @return the name form panel
     */
    private Component createNameForm() {
        JPanel nameForm = new JPanel();
        GroupLayout layout = new GroupLayout(nameForm);
        nameForm.setLayout(layout);
        nameForm.setBackground(DEFAULT_COLOR);
        nameForm.setBorder(DEFAULT_GREY_LINE_BORDER);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel insertNameLabel = new JLabel("Flight Info Name:");
        insertNameLabel.setFont(FORM_LABEL_FONT);

        txtFlightInfoName = new JTextField();
        txtFlightInfoName.setPreferredSize(new Dimension(300, 25));

        //align horizontally
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(insertNameLabel)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(txtFlightInfoName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                )
        );

        //align vertically
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(insertNameLabel)
                        .addComponent(txtFlightInfoName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
        );

        return nameForm;
    }

    /**
     * Creates the bottom buttons panel.
     *
     * @return the bottom buttons panel
     */
    private Component createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout());

        buttonsPanel.setPreferredSize(BUTTONS_PANEL_SIZE);
        buttonsPanel.setBackground(DEFAULT_COLOR);
        buttonsPanel.setBorder(DEFAULT_GREY_LINE_BORDER);

        buttonsPanel.add(createPreviousButton());
        buttonsPanel.add(createNextButton());
        buttonsPanel.add(createFinishButton());
        buttonsPanel.add(createCancelButton());

        return buttonsPanel;
    }

    /**
     * Creates the previous button.
     *
     * @return the previous button
     */
    private Component createPreviousButton() {
        previousButton = new JButton("Previous");
        previousButton.setPreferredSize(BUTTON_PREFERRED_SIZE);

        if (currentForm == 1) {
            previousButton.setEnabled(false);
        }
        previousButton.addActionListener((ActionEvent ae) -> {
            cl.previous(formsPanel);
            currentForm--;
            switch (currentForm) {
                case 1:
                    previousButton.setEnabled(false);
                    aircraftConfigLabel.setFont(PLAIN_LABEL_FONT);
                    aircraftInfoLabel.setFont(BOLD_LABEL_FONT);
                    break;
                case 2:
                    aircraftConfigLabel.setFont(BOLD_LABEL_FONT);
                    addFlightInfoLabel.setFont(PLAIN_LABEL_FONT);
                    break;
                case 3:
                    nextButton.setEnabled(true);
                    addFlightInfoLabel.setFont(BOLD_LABEL_FONT);
                    nameLabel.setFont(PLAIN_LABEL_FONT);
                    break;
            }
        });
        return previousButton;
    }

    /**
     * Creates the next button.
     *
     * @return the next button
     */
    private Component createNextButton() {
        nextButton = new JButton("Next");
        nextButton.setPreferredSize(BUTTON_PREFERRED_SIZE);

        nextButton.addActionListener((ActionEvent ae) -> {
            cl.next(formsPanel);
            currentForm++;
            switch (currentForm) {
                case 2:
                    previousButton.setEnabled(true);
                    aircraftInfoLabel.setFont(PLAIN_LABEL_FONT);
                    aircraftConfigLabel.setFont(BOLD_LABEL_FONT);
                    break;
                case 3:
                    aircraftConfigLabel.setFont(PLAIN_LABEL_FONT);
                    addFlightInfoLabel.setFont(BOLD_LABEL_FONT);
                    break;
                case 4:
                    nextButton.setEnabled(false);
                    addFlightInfoLabel.setFont(PLAIN_LABEL_FONT);
                    nameLabel.setFont(BOLD_LABEL_FONT);
                    break;
            }
        });
        return nextButton;
    }
    
    /**
     * Creates the finish button.
     * 
     * @return the finish button
     */
    private Component createFinishButton() {
        JButton finishButton = new JButton("Finish");
        finishButton.setPreferredSize(BUTTON_PREFERRED_SIZE);

        finishButton.addActionListener((ActionEvent ae) -> {
            // TODO
        });
        return finishButton;
    }

    /**
     * Creates the cancel button.
     * 
     * @return the cancel button
     */
    private Component createCancelButton() {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(BUTTON_PREFERRED_SIZE);

        cancelButton.addActionListener((ActionEvent ae) -> {
            //TODO
        });
        return cancelButton;
    }

}
