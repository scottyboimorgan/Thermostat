// Authors: Paul Ammann & Jeff Offutt
// Modified April 2020 by Offutt (regulator) // update April 2020
// Programmable Thermostat
// Chapter 8
// See ThermostatTest.java for JUnit tests

import java.io.*;
import java.util.*;

// Programmable Thermostat
public class Thermostat
{
    private int curTemp;          // current temperature reading
    private int thresholdDiff;    // temp difference until we turn heater on
    private int timeSinceLastRun; // time since heater stopped
    private int minLag;           // how long I need to wait
    private boolean override;     // has user overridden the program
    private int overTemp;         // overriding temperature
    private int runTime;          // output of turnHeaterOn - how long to run
    private boolean heaterOn;     // output of turnHeaterOn - whether to run
    private Period period;        // morning, day, evening, or night
    private DayType day;          // week day or weekend day
    private boolean regulator;    // Company is regulating // update April 2020

    // Decide whether to turn the heater on, and for how long.
    public boolean turnHeaterOn (ProgrammedSettings pSet)
    {
        int dTemp = pSet.getSetting(period, day);

        if (((curTemp < dTemp - thresholdDiff) ||
                (override && curTemp < overTemp - thresholdDiff)) &&
                (timeSinceLastRun > minLag))
        {  // Turn on the heater
            // How long? Assume 1 minute per degree (Fahrenheit)
            int timeNeeded = curTemp - dTemp;
            if (override && regulator)              // update April 2020
                timeNeeded = (curTemp - overTemp)/2; // update April 2020
            else if (override)                      // update April 2020
                timeNeeded = curTemp - overTemp;
            setRunTime(timeNeeded);
            setHeaterOn(true);
            return(true);
        }
        else
        {
            setHeaterOn(false);
            return(false);
        }
    } // End turnHeaterOn

    public void setCurrentTemp(int temperature)  { curTemp = temperature; }
    public void setThresholdDiff(int delta)      { thresholdDiff = delta; }
    public void setTimeSinceLastRun(int minutes) { timeSinceLastRun = minutes; }
    public void setMinLag(int minutes)           { minLag = minutes; }
    public void setOverride(boolean value)       { override = value; }
    public void setOverTemp(int temperature)     { overTemp = temperature; }
    public void setRegulator(boolean value)      { regulator = value; } // update April 2020

    // for the ProgrammedSettings
    public void setDay(DayType curDay)      { day = curDay; }
    public void setPeriod(Period curPeriod) { period = curPeriod; }

    // outputs from turnHeaterOn
    void    setRunTime(int minutes)    { runTime = minutes; }
    int     getRunTime()               { return runTime; }
    void    setHeaterOn(boolean value) { heaterOn = value; }
    boolean getHeaterOn()              { return heaterOn; }
} // End Thermostat class