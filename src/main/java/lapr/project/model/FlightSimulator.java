/**
 * Package location for Model concepts.
 */
package lapr.project.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lapr.project.datalayer.DbConnection;
import oracle.jdbc.OracleTypes;

/**
 * Represents the simulator to store projects and make the simulations.
 *
 * @author Daniel Gonçalves - 1151452
 * @author Eric Amaral - 1141570
 * @author Ivo Ferro - 1151159
 * @author Tiago Correia - 1151031
 */
public class FlightSimulator {

    /**
     * Gets the projects.
     *
     * @return projects available projects
     * @throws java.sql.SQLException error on sql connection
     */
    public List<Project> getProjects() throws SQLException {
        ArrayList<Project> projects = new ArrayList<>();

        String query = "{?= call FC_GET_PROJECTS}";

        try (Connection connection = DbConnection.getConnection();
                CallableStatement callableStatement = connection.prepareCall(query)) {

            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.executeUpdate();

            try (ResultSet resultSet = (ResultSet) callableStatement.getObject(1)) {
                while (resultSet.next()) {
                    int serieNumber = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);

                    projects.add(new Project(serieNumber, name, description));
                }
            }
        }

        return projects;
    }

    /**
     * Creates a project receiving their name and description.
     *
     * @param name project's name
     * @param description project's description
     * @return created project
     */
    public Project createProject(String name, String description) {
        return new Project(name, description);
    }

    /**
     * Checks if the projects exists.
     *
     * @param name project's name to validate
     * @return true if the name is valid, false otherwise
     * @throws SQLException error in database
     */
    public boolean validateProjectName(String name) throws SQLException {
        List<Project> projects = getProjects();

        for (Project project : projects) {
            if (project.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Adds a given project.
     *
     * @param project project to add
     * @return true if it is successfully added, false otherwise
     * @throws java.sql.SQLException database error
     */
    public boolean addProject(Project project) throws SQLException {
        String query = "INSERT INTO PROJECT (NAME, DESCRIPTION) VALUES (?, ?)";

        try (Connection connection = DbConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.executeUpdate();

        }

        return true;
    }

    /**
     * Updates a given project.
     *
     * @param project project to update
     * @throws SQLException database error
     */
    public void updateProjectNameAndDescription(Project project) throws SQLException {
        String query = "{call PC_EDIT_PROJECT_N_D (?, ?, ?)}";

        try (Connection connection = DbConnection.getConnection(); CallableStatement callableStatement = connection.prepareCall(query)) {

            callableStatement.setDouble(1, project.getSerieNumber());
            callableStatement.setString(2, project.getName());
            callableStatement.setString(3, project.getDescription());

            callableStatement.executeUpdate();
        }
    }

    /**
     * Creates an empty project, returning the project.
     *
     * @return created project
     * @throws java.sql.SQLException
     */
    public Project createEmptyProject() throws SQLException {
        String query = "{call PC_CREATE_EMPTY_PROJECT (?, ?, ?)}";

        try (Connection connection = DbConnection.getConnection(); CallableStatement callableStatement = connection.prepareCall(query)) {

            callableStatement.registerOutParameter(1, OracleTypes.INTEGER);
            callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
            callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);

            callableStatement.executeUpdate();

            int serieNumber = callableStatement.getInt(1);
            String name = callableStatement.getString(2);
            String description = callableStatement.getString(3);

            return new Project(serieNumber, name, description);
        }
    }

    /**
     * Deletes a given project.
     *
     * @param serieNumber serie number of the project to delete
     * @throws java.sql.SQLException
     */
    public void deleteProject(int serieNumber) throws SQLException {
        String query = "{call PC_DELETE_PROJECT (?)}";

        try (Connection connection = DbConnection.getConnection(); CallableStatement callableStatement = connection.prepareCall(query)) {

            callableStatement.setDouble(1, serieNumber);
            callableStatement.executeUpdate();
        }
    }
}
