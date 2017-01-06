/**
 * Package location for Model concepts.
 */
package lapr.project.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import javax.measure.quantity.Mass;
import javax.measure.unit.SI;
import org.jscience.physics.amount.Amount;

/**
 * Represents a flight simulation.
 *
 * @author Daniel Gonçalves - 1151452
 * @author Eric Amaral - 1141570
 * @author Ivo Ferro - 1151159
 * @author João Pereira - 1151241
 * @author Tiago Correia - 1151031
 */
public class FlightSimulation implements Comparable<FlightSimulation> {

    /**
     * The flight's id.
     */
    private int id;

    /**
     * The flight's information.
     */
    private FlightInfo flightInfo;

    /**
     * The flight's scheduled arrival date.
     */
    private Calendar scheduledArrival;

    /**
     * The flight's departure date.
     */
    private Calendar departureDate;

    /**
     * The flight's effective number of crew members.
     */
    private int effectiveCrew;

    /**
     * The flight's effective cargo weight (SI: kg).
     */
    private Amount<Mass> effectiveCargo;

    /**
     * The flight's effective fuel weight (SI: kg).
     */
    private Amount<Mass> effectiveFuel;

    /**
     * The flight plan.
     */
    private LinkedList<Segment> flightplan;

    /**
     * Default flight id.
     */
    private static final int DEFAULT_ID = 0;

    /**
     * Default date.
     */
    private static final Calendar DEFAULT_DATE = new GregorianCalendar(2017, 1, 1);

    /**
     * Default flight crew members.
     */
    private static final int DEFAULT_CREW = 0;

    /**
     * Default flight's effective cargo.
     */
    private static final Amount<Mass> DEFAULT_CARGO = Amount.valueOf(0d, SI.KILOGRAM);

    /**
     * Default flight's effective fuel.
     */
    private static final Amount<Mass> DEFAULT_FUEL = Amount.valueOf(0d, SI.KILOGRAM);

    /**
     * Default constructor of a flight simulation instance.
     */
    public FlightSimulation() {

        this.id = DEFAULT_ID;
        this.flightInfo = new FlightInfo();
        this.scheduledArrival = DEFAULT_DATE;
        this.departureDate = DEFAULT_DATE;
        this.effectiveCrew = DEFAULT_CREW;
        this.effectiveCargo = DEFAULT_CARGO;
        this.effectiveFuel = DEFAULT_FUEL;
        this.flightplan = new LinkedList<>();
    }

    /**
     * Constructs a flight simulation instance.
     *
     * @param id id
     * @param flightInfo flight info
     * @param scheduledArrival scheduled arrival date
     * @param departureDate departure date
     * @param effectiveCrew effective crew members
     * @param effectiveCargo effective cargo
     * @param effectiveFuel effective fuel
     * @param flightplan the flight plan
     */
    public FlightSimulation(int id, FlightInfo flightInfo, Calendar scheduledArrival,
            Calendar departureDate, int effectiveCrew, Amount<Mass> effectiveCargo,
            Amount<Mass> effectiveFuel, List<Segment> flightplan) {

        this.id = id;
        this.flightInfo = flightInfo;
        this.scheduledArrival = scheduledArrival;
        this.departureDate = departureDate;
        this.effectiveCrew = effectiveCrew;
        this.effectiveCargo = effectiveCargo;
        this.effectiveFuel = effectiveFuel;
        this.flightplan = new LinkedList<>(flightplan);
    }

    /**
     * Copy constructor of a flight simulation instance.
     *
     * @param other flght to copy
     */
    public FlightSimulation(FlightSimulation other) {

        this.id = other.id;
        this.flightInfo = other.flightInfo;
        this.scheduledArrival = other.scheduledArrival;
        this.departureDate = other.departureDate;
        this.effectiveCrew = other.effectiveCrew;
        this.effectiveCargo = other.effectiveCargo;
        this.effectiveFuel = other.effectiveFuel;
        this.flightplan = new LinkedList<>(other.getFlightplan());
    }

    /**
     * Obtains the flight's id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the flight's id.
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtains the flight's information.
     *
     * @return the flightInfo
     */
    public FlightInfo getFlightInfo() {
        return flightInfo;
    }

    /**
     * Sets the flight's information.
     *
     * @param flightInfo the flightInfo to set
     */
    public void setFlightInfo(FlightInfo flightInfo) {
        this.flightInfo = flightInfo;
    }

    /**
     * Obtains the flight's scheduled arrival date.
     *
     * @return the scheduledArrival
     */
    public Calendar getScheduledArrival() {
        return scheduledArrival;
    }

    /**
     * Sets the flight's scheduled arrival date.
     *
     * @param scheduledArrival the scheduledArrival to set
     */
    public void setScheduledArrival(Calendar scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    /**
     * Obtains the flight's departure date.
     *
     * @return the departureDate
     */
    public Calendar getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the flight's departure date.
     *
     * @param departureDate the departureDate to set
     */
    public void setDepartureDate(Calendar departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Obtains the flight's effective number of crew members.
     *
     * @return the effectiveCrew
     */
    public int getEffectiveCrew() {
        return effectiveCrew;
    }

    /**
     * Sets the flight's effective number of crew members.
     *
     * @param effectiveCrew the effectiveCrew to set
     */
    public void setEffectiveCrew(int effectiveCrew) {
        this.effectiveCrew = effectiveCrew;
    }

    /**
     * Obtains the flight's effective cargo weight (SI: kg).
     *
     * @return the effectiveCargo
     */
    public Amount<Mass> getEffectiveCargo() {
        return effectiveCargo;
    }

    /**
     * Sets the flight's effective cargo weight (SI: kg).
     *
     * @param effectiveCargo the effectiveCargo to set
     */
    public void setEffectiveCargo(Amount<Mass> effectiveCargo) {
        this.effectiveCargo = effectiveCargo;
    }

    /**
     * Obtains the flight's effective fuel weight (SI: kg).
     *
     * @return the effectiveFuel
     */
    public Amount<Mass> getEffectiveFuel() {
        return effectiveFuel;
    }

    /**
     * Sets the flight's effective fuel weight (SI: kg).
     *
     * @param effectiveFuel the effectiveFuel to set
     */
    public void setEffectiveFuel(Amount<Mass> effectiveFuel) {
        this.effectiveFuel = effectiveFuel;
    }

    /**
     * Obtains the flight plan.
     *
     * @return the flightplan
     */
    public LinkedList<Segment> getFlightplan() {
        return flightplan;
    }

    /**
     * Sets the flight plan.
     *
     * @param flightplan the flightplan to set
     */
    public void setFlightplan(LinkedList<Segment> flightplan) {
        this.flightplan = flightplan;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final FlightSimulation other = (FlightSimulation) obj;

        return this.id == other.id;
    }

    @Override
    public int compareTo(FlightSimulation o) {

        return (this.id == o.id) ? 0 : (this.id > o.id) ? 1 : -1;
    }

    @Override
    public String toString() {
        return "FlightSimulation{" + "id=" + id + ", flightInfo=" + flightInfo + ", scheduledArrival=" + scheduledArrival + ", departureDate=" + departureDate + ", effectiveCrew=" + effectiveCrew + ", effectiveCargo=" + effectiveCargo + ", effectiveFuel=" + effectiveFuel + ", flightplan=" + flightplan + '}';
    }

}
