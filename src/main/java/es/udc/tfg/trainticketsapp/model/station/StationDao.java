package es.udc.tfg.trainticketsapp.model.station;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface StationDao extends GenericDao<Station, Long> {
	public List<Station> findAllStations();

}
