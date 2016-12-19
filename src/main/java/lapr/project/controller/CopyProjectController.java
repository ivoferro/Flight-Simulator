/**
 * Package location for Coontroller concepts.
 */
package lapr.project.controller;

import lapr.project.model.Project;
import lapr.project.model.FlightSimulator;

/**
 * Represents a CopyProjectController.
 *
 * @author Daniel Gonçalves - 1151452
 * @author Eric Amaral - 1141570
 * @author Ivo Ferro - 1151159
 * @author João Pereira - 1151241
 * @author Tiago Correia - 1151031
 */
public class CopyProjectController {

    /**
     * The copy of a project.
     */
    private final Project projectCopy;

    /**
     * The simulator.
     */
    private final FlightSimulator simulator;

    /**
     * Creates an instance of the controller.
     *
     * @param simulator the simulator
     * @param project the selected project
     */
    public CopyProjectController(FlightSimulator simulator, Project project) {
        this.simulator = simulator;
        projectCopy = new Project(project);
    }

    /**
     * Sets additional project data.
     *
     * @param name the name of the project copy
     * @param description the description of the project copy
     * @return true if the project's copy is added to the project list and false
     * otherwise
     */
    public boolean setCopyProjectData(String name, String description) {
        if (projectCopy.validate(name)) {
            projectCopy.setName(name);
            projectCopy.setDescription(description);
            return true;
        }
        return false;
    }
    
    public boolean addProjectCopy(){
        return simulator.addProject(projectCopy);
    }
}
