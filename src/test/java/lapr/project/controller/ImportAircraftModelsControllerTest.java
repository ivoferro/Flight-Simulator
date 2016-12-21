/**
 * Package location for Application Controllers tests.
 */
package lapr.project.controller;

import java.io.File;
import lapr.project.model.Project;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Integration tests for import aircraft models controller.
 *
 * @author Daniel Gonçalves - 1151452
 * @author Eric Amaral - 1141570
 * @author Ivo Ferro - 1151159
 * @author João Pereira - 1151241
 * @author Tiago Correia - 1151031
 */
public class ImportAircraftModelsControllerTest {

    /**
     * The controller instance to be set.
     */
    private ImportAircraftModelsController instance;

    /**
     * Project to use on tests.
     */
    private Project project;

    @Before
    public void setUp() {
        project = new Project();
        instance = new ImportAircraftModelsController(project);
    }

    /**
     * Test 1 of importFile method, of class ImportAircraftModelsController.
     *
     * @throws java.lang.Exception exception
     */
    @Test
    public void testImportFile1() throws Exception {
        System.out.println("importFile 1");

        File file = new File("xml_files" + File.separator + "TestSet01_Aircraft.xml");

        assertTrue(instance.importFile(file));
    }

    /**
     * Test 2 of importFile method, of class ImportAircraftModelsController.
     *
     * @throws java.lang.Exception exception
     */
    @Test
    public void testImportFile2() throws Exception {
        System.out.println("importFile 2");

        File file = new File("xml_files" + File.separator + "TestSet01_Aircraft.xml");

        instance.importFile(file);

        assertTrue(project.getAircraftModelsRegister().getAircraftModels().size() > 0);
    }

}