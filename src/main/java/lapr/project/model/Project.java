/**
 * Package location for Model concepts.
 */
package lapr.project.model;

import java.util.Objects;

/**
 * Represents a Project.
 *
 * @author Daniel Gonçalves - 1151452
 * @author Eric Amaral - 1141570
 * @author Ivo Ferro - 1151159
 * @author Tiago Correia - 1151031
 */
public class Project implements Comparable<Project> {

    /**
     * Description of the Project
     */
    private String description;

    /**
     * Name of the Project
     */
    private String name;

    /**
     * Serie Number of the Project
     */
    private Integer serieNumber;

    /**
     * The airNetwork of the project.
     */
    private AirNetwork airNetwork;

    /**
     * The project's simulations.
     */
    private Simulation simulations;

    /**
     * Default value for Description
     */
    private final static String DEFAULT_DESCRIPTION = "No description";
    /**
     * Default value for Name
     */
    private final static String DEFAULT_NAME = "Untitled";

    /**
     * Counter to increment serie number.
     */
    private static Integer counter = 1;

    /**
     * Creates an instance of project with its default values
     */
    public Project() {
        this.serieNumber = counter++;
        this.name = DEFAULT_NAME;
        this.description = DEFAULT_DESCRIPTION;
        this.airNetwork = new AirNetwork();
        this.simulations = new Simulation();
    }

    /**
     * Creates an instance of a Project, receiving only the name and
     * description.
     *
     * @param name the given name
     * @param description the given description
     */
    public Project(String name, String description) {
        this.serieNumber = counter++;
        this.name = name;
        this.description = description;
        this.airNetwork = new AirNetwork();
        this.simulations = new Simulation();
    }

    /**
     * Creates a project receiving their attributes.
     *
     * @param serieNumber serie number
     * @param name name
     * @param description descriptions
     */
    public Project(int serieNumber, String name, String description) {
        this.serieNumber = serieNumber;
        this.name = name;
        this.description = description;
        counter++;
    }

    /**
     * Creates a project receiving another project
     *
     * @param otherProject other project to copy
     */
    public Project(Project otherProject) {
        this.serieNumber = counter++;
        this.airNetwork = otherProject.airNetwork;
        this.simulations = new Simulation(); // simulations are not supposed to be copied
    }

    /**
     * Gets the description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description
     *
     * @param description description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     *
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the serie number
     *
     * @return serie number
     */
    public int getSerieNumber() {
        return serieNumber;
    }

    /**
     * Sets the serie number
     *
     * @param serieNumber serie number to set
     */
    public void setSerieNumber(int serieNumber) {
        this.serieNumber = serieNumber;
    }

    /**
     * Gets the project's air network.
     *
     * @return the airNetwork
     */
    public AirNetwork getAirNetwork() {
        return airNetwork;
    }

    /**
     * Modifies the project's air network
     *
     * @param airNetwork the airNetwork to set
     */
    public void setAirNetwork(AirNetwork airNetwork) {
        this.airNetwork = airNetwork;
    }

    /**
     * Gets the project's simulations.
     *
     * @return the simulations
     */
    public Simulation getSimulations() {
        return simulations;
    }

    /**
     * Modifies the project's simulations.
     *
     * @param simulations the simulations to set
     */
    public void setSimulations(Simulation simulations) {
        this.simulations = simulations;
    }

    /**
     * Validates the project.
     *
     * @return true if it is valid, false otherwise
     */
    public boolean validateName() {
        return !this.name.trim().isEmpty();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.description);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + this.serieNumber;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Project other = (Project) obj;
        return this.serieNumber.equals(other.serieNumber);
    }

    @Override
    public String toString() {
        return String.format("Serie Number: %d\n"
                + "Name: %s\n"
                + "Description: %s\n"
                + "Air Network: %s\n"
                + "Simulations: %s", serieNumber, name, description, airNetwork, simulations);
    }

    @Override
    public int compareTo(Project otherProject) {
        return this.name.equals(otherProject.name) ? this.serieNumber.compareTo(otherProject.serieNumber) : this.name.compareToIgnoreCase(otherProject.name);
    }

}
