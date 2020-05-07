// Introduction to Software Testing
// Authors: Paul Ammann & Jeff Offutt
// Chapter 3; page ??
// JUnit for Thermostat.java
//
// NOT COMPLETE (SEPT 2014)
/**
 * Edited by Scott Morgan, Amine Bourdi, and Nestor Maldonado
 */

import org.junit.*;

import static org.junit.Assert.*;

public class ThermostatTest
{
    private Thermostat thermo;
    private ProgrammedSettings settings;

    @Before // Set up - Called before every test method.
    public void setUp()
    {
        thermo   = new Thermostat();
        settings = new ProgrammedSettings();
        thermo.setThresholdDiff (1);
    }

    // if (((curTemp < dTemp - thresholdDiff) || (Override && curTemp < overTemp - thresholdDiff)) && (timeSinceLastRun > minLag))
    // Predicate: (a || (b && c)) && d

    @Test public void testTTTT()
    {
        settings.setSetting (Period.MORNING, DayType.WEEKDAY, 70);
        thermo.setPeriod (Period.MORNING);
        thermo.setDay (DayType.WEEKDAY);
        thermo.setCurrentTemp (68);
        thermo.setTimeSinceLastRun (10);
        thermo.setMinLag (5);
        thermo.setOverride (true);
        thermo.setOverTemp (72);
        assertTrue (thermo.turnHeaterOn (settings));
    }

    @Test public void testFFFF()
    {
        settings.setSetting (Period.MORNING, DayType.WEEKDAY, 70);
        thermo.setPeriod (Period.MORNING);
        thermo.setDay (DayType.WEEKDAY);
        thermo.setCurrentTemp (70);
        thermo.setTimeSinceLastRun (7);
        thermo.setMinLag (7);
        thermo.setOverride (false);
        thermo.setOverTemp (70);
        assertFalse (thermo.turnHeaterOn (settings));
    }

    @Test public void testPCtrue()
    {
        // Partial test for method turnHeaterOn() in class Thermostat
        // Criterion: PC
        // Value: True
        // Predicate: lines 28-30
        // Expected Output: true

        // Instantiate needed objects
        thermo   = new Thermostat();
        settings = new ProgrammedSettings();
        // Setting internal variable dTemp
        settings.setSetting (Period.MORNING, DayType.WEEKDAY, 69);
        thermo.setPeriod (Period.MORNING);
        thermo.setDay (DayType.WEEKDAY);
        // Clause a: curTemp < dTemp - thresholdDiff : true
        thermo.setCurrentTemp (63);
        thermo.setThresholdDiff (5);
        // Clause b: Override : true
        thermo.setOverride (true);
        // Clause c: curTemp < overTemp - thresholdDiff : true
        thermo.setOverTemp (70);
        // Clause d: timeSinceLastRun.greaterThan (minLag) : true
        thermo.setMinLag (10);
        thermo.setTimeSinceLastRun (12);
        // Run the test
        assertTrue (thermo.turnHeaterOn (settings));
    }

    /**
     *
     * FINDINGS:
     * It does not take into account if the thermostat user enters a higher override temperature, to change the value
     * back to a positive integer, instead returning -1(VALUE)
     * Time cannot be negative, so it should always return a positive number
     *
     * P_override:
     *      TT
     *      FT
     *
     * P_regulator:
     *      TT
     *      TF
     */


/*
    overTemp < currentTemp
 */
    @Test public void testCACC_TT1()
    {
        settings.setSetting (Period.MORNING, DayType.WEEKDAY, 70);
        thermo.setPeriod (Period.MORNING);
        thermo.setDay (DayType.WEEKDAY);
        thermo.setCurrentTemp (68);
        thermo.setTimeSinceLastRun (10);
        thermo.setMinLag (5);
        thermo.setOverride (true);
        thermo.setOverTemp (64);
        thermo.setRegulator(true);
        thermo.turnHeaterOn(settings);
        assertEquals (2, thermo.getRunTime());
    }
/*
    overTemp > currentTemp
 */
    @Test public void testCACC_TT2()
    {
        settings.setSetting (Period.MORNING, DayType.WEEKDAY, 70);
        thermo.setPeriod (Period.MORNING);
        thermo.setDay (DayType.WEEKDAY);
        thermo.setCurrentTemp (68);
        thermo.setTimeSinceLastRun (10);
        thermo.setMinLag (5);
        thermo.setOverride (true);
        thermo.setOverTemp (72);
        thermo.setRegulator(true);
        thermo.turnHeaterOn(settings);
        assertEquals (2, thermo.getRunTime());
    }
/*
    overTemp < currentTemp
 */
    @Test public void testCACC_TF1()
    {
        settings.setSetting (Period.MORNING, DayType.WEEKDAY, 70);
        thermo.setPeriod (Period.MORNING);
        thermo.setDay (DayType.WEEKDAY);
        thermo.setCurrentTemp (68);
        thermo.setTimeSinceLastRun (10);
        thermo.setMinLag (5);
        thermo.setOverride (true);
        thermo.setOverTemp (64);
        thermo.setRegulator(false);
        thermo.turnHeaterOn(settings);
        assertEquals (4, thermo.getRunTime());
    }
/*
    overTemp > currentTemp
 */
    @Test public void testCACC_TF2()
    {
        settings.setSetting (Period.MORNING, DayType.WEEKDAY, 70);
        thermo.setPeriod (Period.MORNING);
        thermo.setDay (DayType.WEEKDAY);
        thermo.setCurrentTemp (68);
        thermo.setTimeSinceLastRun (10);
        thermo.setMinLag (5);
        thermo.setOverride (true);
        thermo.setOverTemp (72);
        thermo.setRegulator(false);
        thermo.turnHeaterOn(settings);
        assertEquals (4, thermo.getRunTime());
    }
/*
    overTemp < currentTemp
 */
    @Test public void testCACC_FT1()
    {
        settings.setSetting (Period.MORNING, DayType.WEEKDAY, 70);
        thermo.setPeriod (Period.MORNING);
        thermo.setDay (DayType.WEEKDAY);
        thermo.setCurrentTemp (68);
        thermo.setTimeSinceLastRun (10);
        thermo.setMinLag (5);
        thermo.setOverride (false);
        thermo.setOverTemp (64);
        thermo.setRegulator(true);
        thermo.turnHeaterOn(settings);
        assertEquals (2, thermo.getRunTime());
    }
/*
    overTemp > currentTemp
 */
    @Test public void testCACC_FT2()
    {
        settings.setSetting (Period.MORNING, DayType.WEEKDAY, 70);
        thermo.setPeriod (Period.MORNING);
        thermo.setDay (DayType.WEEKDAY);
        thermo.setCurrentTemp (68);
        thermo.setTimeSinceLastRun (10);
        thermo.setMinLag (5);
        thermo.setOverride (false);
        thermo.setOverTemp (72);
        thermo.setRegulator(true);
        thermo.turnHeaterOn(settings);
        assertEquals (2, thermo.getRunTime());
    }

}