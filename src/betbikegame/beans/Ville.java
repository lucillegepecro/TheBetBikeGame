package betbikegame.beans;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;



/**
 * 
 * @author anna
 *
 */
@PersistenceCapable
public class Ville {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String name;
	
	@Persistent
	private List<String> stations;
	
	@Persistent
	private Integer available_bike_stands;
	
	@Persistent
	private Double km;


	/**
	 * 
	 * @param name
	 * @param stations
	 * @param available_bike_stands
	 */
	public Ville(String name, Double km) {
		super();
		this.name = name;
		this.km=km;

	}
	
	/**
	 * 
	 */
	public Ville(){
		
	}

	/*
	 * GETTERS
	 */
	public Key getKey() {
		return key;
	}

	public String getName() {
		return name;
	}
	
	public List<String> getStations() {
		return stations;
	}
	
	public Integer getAvailable_bike_stands() {
		return available_bike_stands;
	}
	
	public Double getKilometers() {
		return km;
	}
	
	/*
	 * SETTERS
	 */
	public void setName(String name) {
		this.name = name;
	}
	public void setStations(List<String> stations) {
		this.stations = stations;
	}

	public void setAvailable_bike_stands(Integer available_bike_stands) {
		this.available_bike_stands = available_bike_stands;
	}

	public void setKilometers(Double km) {
		this.km = km;
	}
	
	
	
}
