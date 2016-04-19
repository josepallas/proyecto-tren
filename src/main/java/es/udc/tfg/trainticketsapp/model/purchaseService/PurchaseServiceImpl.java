package es.udc.tfg.trainticketsapp.model.purchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.tfg.trainticketsapp.model.fare.FareDao;
import es.udc.tfg.trainticketsapp.model.passenger.PassengerDao;
import es.udc.tfg.trainticketsapp.model.purchase.PurchaseDao;
import es.udc.tfg.trainticketsapp.model.ticket.TicketDao;


@Service("purchaseService")
@Transactional
public class PurchaseServiceImpl implements PurchaseService{

    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private PassengerDao passengerDao;
    @Autowired
    private FareDao FareDao;
    @Autowired
    private TicketDao ticketDao;
}
