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
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import lapr.project.model.FlightSimulator;

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
     * Padding border.
     */
    private final static EmptyBorder PADDING_BORDER = new EmptyBorder(10, 10, 10, 10);

    /**
     * The button preferred size.
     */
    private final static Dimension BUTTON_PREFERRED_SIZE = new Dimension(100, 30);
    
    /**
     * The orientation panel size.
     */
    private final static Dimension ORIENTATION_PANEL_SIZE = new Dimension(250, 600);

    /**
     * The main panel size.
     */
    private final static Dimension MAIN_PANEL_SIZE = new Dimension(750, 600);
    
    /**
     * The buttons panel size.
     */
    private final static Dimension BUTTONS_PANEL_SIZE = new Dimension(750, 75);
    
    /**
     * Title for the frame.
     */
    private static final String WINDOW_TITLE = "title";

    /**
     * The layout for the forms.
     */
    private CardLayout cl;

    /**
     * The current active form.
     */
    private int currentForm = 1;

    /**
     * The forms panel.
     */
    private JPanel formsPanel;

    /**
     * Creates an instance of the flight info dialog.
     *
     * @param parentWindow the parent window
     * @param simulator the simulator
     */
    public FlightInfoDialog(T parentWindow, FlightSimulator simulator) {
        super(parentWindow, WINDOW_TITLE);
        setModal(true);
        this.parentWindow = parentWindow;

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
        orientationPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        orientationPanel.setBackground(Color.LIGHT_GRAY);
        orientationPanel.add(createLabelsPanel(), BorderLayout.NORTH);

        return orientationPanel;
    }

    /**
     * Creates the labels panel
     *
     * @return the labels panel
     */
    private Component createLabelsPanel() {
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));

        labelsPanel.add(createSelectAircraftLabel());
        labelsPanel.add(createClassLabel());
        labelsPanel.add(createAddFlightInfoLabel());
        labelsPanel.add(createNameLabel());

        return labelsPanel;
    }

    /**
     * Creates the select aircraft label.
     *
     * @return the select aircraft label
     */
    private Component createSelectAircraftLabel() {
        JLabel selectAircraftLabel = new JLabel("Select Aircraft");
        selectAircraftLabel.setBackground(Color.LIGHT_GRAY);
        return selectAircraftLabel;
    }

    /**
     * Creates the class label.
     *
     * @return class label
     */
    private Component createClassLabel() {
        JLabel classLabel = new JLabel("Set Aircraft Classes");
        return classLabel;
    }

    /**
     * Creates the add flight info label.
     *
     * @return the add flight info label
     */
    private Component createAddFlightInfoLabel() {
        JLabel addFlightInfo = new JLabel("Add Flight Info");
        return addFlightInfo;
    }

    /**
     * Creates the name label.
     *
     * @return the name label
     */
    private Component createNameLabel() {
        JLabel nameLabel = new JLabel("Set Name");
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

        formsPanel.add(createSelectAircraftForm(), "1");
        formsPanel.add(createClassForm(), "2");
        formsPanel.add(createAddFlightInfo(), "3");
        formsPanel.add(createNameForm(), "4");

        return formsPanel;
    }

    private Component createSelectAircraftForm() {
        JPanel selectAircraftForm = new JPanel();
        selectAircraftForm.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        selectAircraftForm.setBackground(Color.yellow); // temp color - only to make sure panels are switching
        return selectAircraftForm;
    }

    private Component createClassForm() {
        JPanel classForm = new JPanel();
        classForm.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        classForm.setBackground(Color.blue); // temp color - only to make sure panels are switching
        return classForm;
    }

    private Component createAddFlightInfo() {
        JPanel addFlightInfo = new JPanel();
        addFlightInfo.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        addFlightInfo.setBackground(Color.green); // temp color - only to make sure panels are switching
        return addFlightInfo;
    }

    private Component createNameForm() {
        JPanel nameForm = new JPanel();
        nameForm.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        nameForm.setBackground(Color.red); // temp color - only to make sure panels are switching
        return nameForm;
    }

    private Component createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout());

        buttonsPanel.setPreferredSize(BUTTONS_PANEL_SIZE);
        buttonsPanel.setBackground(Color.LIGHT_GRAY);
        buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        buttonsPanel.add(createPreviousButton());
        buttonsPanel.add(createNextButton());
        buttonsPanel.add(createFinishButton());
        buttonsPanel.add(createCancelButton());
        return buttonsPanel;
    }

    private Component createPreviousButton() {
        JButton previousButton = new JButton("Previous");
        previousButton.setPreferredSize(BUTTON_PREFERRED_SIZE);

        previousButton.addActionListener((ActionEvent ae) -> {
            cl.previous(formsPanel);
            currentForm = currentForm--;
        });
        return previousButton;
    }

    private Component createNextButton() {
        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(BUTTON_PREFERRED_SIZE);

        nextButton.addActionListener((ActionEvent ae) -> {
            cl.next(formsPanel);
            currentForm = currentForm++;
        });
        return nextButton;
    }

    private Component createFinishButton() {
        JButton finishButton = new JButton("Finish");
        finishButton.setPreferredSize(BUTTON_PREFERRED_SIZE);

        finishButton.addActionListener((ActionEvent ae) -> {
            // TODO
        });
        return finishButton;
    }

    private Component createCancelButton() {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(BUTTON_PREFERRED_SIZE);

        cancelButton.addActionListener((ActionEvent ae) -> {
            // TODO
        });
        return cancelButton;
    }

}
