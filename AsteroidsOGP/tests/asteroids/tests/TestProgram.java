package asteroids.tests;

import asteroids.model.Bullet;
import asteroids.util.ModelException;
import com.sun.javafx.sg.prism.NGShape;
import org.junit.Before;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import asteroids.model.Asteroid;
import asteroids.model.Bullet;
import asteroids.model.Planetoid;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.facade.IFacade;
import asteroids.model.Program;
import asteroids.model.program.ProgramFactory;
import asteroids.part3.programs.IProgramFactory;
import asteroids.part3.programs.internal.ProgramParser;
import asteroids.util.ModelException;

/**
 * Created by Maarten Doclo on 12/05/2017.
 */
public class TestProgram {

    private static final double EPSILON = 0.0001;

    static int nbStudentsInTeam;
    IFacade facade;
    IProgramFactory<?, ?, ?, Program> programFactory = new ProgramFactory();
    World filledWorld;
    Ship ship1, ship2, ship3;
    Bullet bullet1;
    static int score = 0;
    static int max_score = 0;

    @AfterClass
    public static void tearDownAfterClass() {
        System.out.println("Score: " + score + "/" + max_score);
    }

    @Before
    public void setUp() throws ModelException {
        facade = new asteroids.facade.Facade();
        nbStudentsInTeam = facade.getNbStudentsInTeam();
        filledWorld = facade.createWorld(2000, 2000);
        ship1 = facade.createShip(100, 120, 10, 5, 50, 0, 1.0E20);
        for (int i = 1; i < 10; i++) {
            Bullet bulletToLoad = facade.createBullet(100, 120, 0, 0, 10);
            facade.loadBulletOnShip(ship1, bulletToLoad);
        }
        facade.addShipToWorld(filledWorld, ship1);
        ship2 = facade.createShip(200, 220, 10, 5, 50, 0, 1.0E20);
        facade.addShipToWorld(filledWorld, ship2);
        bullet1 = facade.createBullet(300, 320, 10, 5, 50);
        facade.addBulletToWorld(filledWorld, bullet1);
    }

    @Test
    public void testMultipleReturnStatements() throws ModelException {
        String code = "def function { " + "  a := 1.0; " + "  t := 2.0; " + "  return t; " + "return a;" + "} "
                + "print function(); ";
        Program program = ProgramParser.parseProgramFromString(code, programFactory);
        facade.loadProgramOnShip(ship1, program);
        List<Object> results = facade.executeProgram(ship1, 0.3);
        Object[] expecteds = {2.0};
        assertArrayEquals(expecteds, results.toArray());
    }

    @Test
    public void testPrintInFunctionBody() throws ModelException {
        try {
            max_score += 3;
            String code = "def function { " + "  a := 1.0; " + "  t := 2.0; " + "  return t; " + " print a;" + "} "
                    + "print function(); ";
            Program program = ProgramParser.parseProgramFromString(code, programFactory);
            facade.loadProgramOnShip(ship1, program);
            List<Object> results = facade.executeProgram(ship1, 0.3);
            Object[] expecteds = {1.0, 2.0};
            assertArrayEquals(expecteds, results.toArray());
            fail();
        }
        catch (ModelException e){
            score += 3;
        }
    }
}
