package es.udc.tfg.trainticketsapp.model.station;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface StationDao extends GenericDao<Station, Long> {
	public List<Station> findAllStations();
	public Station findByName(String stationName) throws InstanceNotFoundException ;
	public List<String> findAllNameStations();
}
