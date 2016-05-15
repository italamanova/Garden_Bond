package org.irina.model;

import java.io.Serializable;
import java.rmi.server.UID;
//import java.util.ArrayList;
import java.util.List;
//import java.util.UUID;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import org.irina.beans.Sensor;
import org.irina.beans.SessionBean;
import org.irina.dao.LotDAO;
import org.irina.dao.SensorDAO;
import org.irina.util.HandBook;


@ManagedBean
@ViewScoped
public class Sensors implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;

    public String[] hours;
    public String[] getHours() {
        return hours;
    }
    public void setHours(String[] hours) {
        this.hours = hours;
    }
    //generated by Map
    private static Map<String, Object> hoursValue;
    static
    {
        hoursValue = new LinkedHashMap<String, Object>();
        hoursValue.put("0h", "0");
        hoursValue.put("1h", "1");
        hoursValue.put("2h", "2");
        hoursValue.put("3h", "3");
        hoursValue.put("4h", "4");
        hoursValue.put("5h", "5");
        hoursValue.put("6h", "6");
        hoursValue.put("7h", "7");
        hoursValue.put("8h", "8");
        hoursValue.put("9h", "9");
        hoursValue.put("10h", "10");
        hoursValue.put("11h", "11");
        hoursValue.put("12h", "12");
        hoursValue.put("13h", "13");
        hoursValue.put("14h", "14");
        hoursValue.put("15h", "15");
        hoursValue.put("16h", "16");
        hoursValue.put("17h", "17");
        hoursValue.put("18h", "18");
        hoursValue.put("19h", "19");
        hoursValue.put("20h", "20");
        hoursValue.put("21h", "21");
        hoursValue.put("22h", "22");
        hoursValue.put("23h", "23");
    }
     
    public Map<String, Object> getHoursValue()
    {
        return hoursValue;
    }
    public String getHoursInString() {
        return Arrays.toString(hours);
    }
    public void emptySchedule()
    {
    	hours = null;
    }
    public void anySchedule()
    {
    	hours = new String[24];
    	for(int i = 0; i < 24; i++)
    		hours[i] = "" + i;
    }
    public void onSchedule()
    {
    	SensorDAO.setHours(lotId, hours);
    	//return "";
    }
    
    private List<Sensor> list;
    private Sensor sensor = new Sensor();
    private boolean edit;
    private String lotId;
    private String lotName;

    @PostConstruct
    public void init() {
        // list = dao.list();
        // Actually, you should retrieve the list from DAO. This is just for demo.
		HttpSession session = SessionBean.getSession();
		lotId = (String)session.getAttribute("lotid");
		lotName = (String)session.getAttribute("lotname");
		hours = SensorDAO.getHours(lotId);
        list = SensorDAO.getSensors(lotId);
    }
    public String getLotId()
    {
    	return lotId;
    }
    public String getLotName()
    {
    	return lotName;
    }
    public void add() {
        String key = getPrimaryKey();
        sensor.setId(key);
        SensorDAO.addSensor(lotId, sensor.getId(), sensor.getName(), sensor.getDescription(), sensor.getType(), sensor.getHost(), sensor.getPort(), sensor.getStatus());
        list.add(sensor);
        sensor = new Sensor(); // Reset placeholder.
    }

    public void edit(Sensor sensor) {
        this.sensor = sensor;
        edit = true;
    }

    public void save() {
    	SensorDAO.editSensor(sensor.getId(), sensor.getName(), sensor.getDescription(), sensor.getType(), sensor.getHost(), sensor.getPort(), sensor.getStatus());
        sensor = new Sensor(); // Reset placeholder.
        edit = false;
    }

    public void delete(Sensor sensor) {
    	SensorDAO.deleteSensor(sensor.getId());
        list.remove(sensor);
    }

    public List<Sensor> getList() {
        return list;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public boolean isEdit() {
        return edit;
    }
    private static String getPrimaryKey()
    {
    	return new UID().toString();
    }
	public Map<String,Object> getSensorTypeValue()
	{
		return HandBook.getSensorTypeValue();
	}
	public Map<String,Object> getSensorStatusValue()
	{
		return HandBook.getSensorStatusValue();
	}
}