package es.udc.tfg.trainticketsapp.model.trainService;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.car.CarDao;
import es.udc.tfg.trainticketsapp.model.route.Route;
import es.udc.tfg.trainticketsapp.model.route.RouteDao;
import es.udc.tfg.trainticketsapp.model.station.StationDao;
import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.stop.StopDao;
import es.udc.tfg.trainticketsapp.model.train.Train;
import es.udc.tfg.trainticketsapp.model.train.TrainDao;
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfile;
import es.udc.tfg.trainticketsapp.model.userservice.util.PasswordEncrypter;

@Service("trainService")
@Transactional
public class TrainServiceImpl implements TrainService  {


    @Autowired
    private TrainDao trainDao;
    @Autowired
    private CarDao carDao;
    @Autowired
    private RouteDao routeDao;
    @Autowired
    private StationDao stationDao;
    @Autowired
    private StopDao stopDao;
    
	public List<Stop> findTravels(Calendar travelDay,String origin, String destination){
		return stopDao.findTravels(travelDay, origin, destination);
	}
	public Train findTrain(Long id) throws InstanceNotFoundException {
		return trainDao.find(id);
	}

	public List<Train> findTrains() {
		return trainDao.findAllTrains();
	}
	
	public Route createRoute(String routeName, String routeDescription,Long trainId) throws DuplicateInstanceException, InstanceNotFoundException {
        try {
            routeDao.findByName(routeName);
            throw new DuplicateInstanceException(routeName,
                    Route.class.getName());
        } catch (InstanceNotFoundException e) {
        	Train train=trainDao.find(trainId);
            Route route=new Route(routeName,routeDescription,null,train);

            routeDao.save(route);
            return route;
        }
	}

}
