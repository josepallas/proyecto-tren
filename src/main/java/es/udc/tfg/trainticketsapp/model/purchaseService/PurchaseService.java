package es.udc.tfg.trainticketsapp.model.purchaseService;

import java.util.Calendar;
import java.util.List;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;
import es.udc.tfg.trainticketsapp.model.fare.Fare;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase.PaymentMethod;
import es.udc.tfg.trainticketsapp.model.ticket.Ticket;
import es.udc.tfg.trainticketsapp.model.util.exceptions.NotEmpySeatsException;
import es.udc.tfg.trainticketsapp.model.util.exceptions.TimeoutTicketException;

public interface PurchaseService {

	/**
	 * Método que devuelve todas las tarifas
	 */
	public List<Fare> findFares();

	/**
	 * Método que devuelve una lista de tarifas que concidan con un determinado tipo
	 */
	public List<Fare> findFareBytype(String type);

	/**
	 * Método que actualiza la tarifa
	 * @throws InstanceNotFoundException
	 * En caso de que no exista la tarifa que se quiera actualizar
	 */
	public void updateFare(Long fareId, String fareName,
			String fareDescription, String fareType, int discount)
			throws InstanceNotFoundException;

	/**
	 * Método que crea una nueva tarifa a partir de un nombre, una descripción, un tipo y el descuento
	 * que proporcionará(negativo o positivo)
	 * @throws DuplicateInstanceException
	 */
	public Fare createFare(String fareName, String fareDescription,
			String fareType, int discount) throws DuplicateInstanceException;

	/**
	 * Método que crea una compra, además de crear los billetes y los pasajeros
	 * En caso de que la fecha de vuelta no sea nula, se crearan también el billete de vuelta
	 * a partir de los campos origen y destino de vuelta
	 * @throws InstanceNotFoundException
	 * @throws NotEmpySeatsException
	 * En caso de que no queden asientos libres para asignar
	 */
	public Purchase buyTickets(PaymentMethod paymentMethod, Long userId,
			Calendar ticketsDate, Calendar ticketsDateReturn, Long origin,
			Long destination, Long originReturn, Long destinationReturn,
			List<TicketDetails> tickets,CarType carType,CarType carTypeReturn) throws InstanceNotFoundException,NotEmpySeatsException;

	/**
	 * Método que cancela un billete a partir de su id
	 * @throws TimeoutTicketException
	 * En caso de que se intente cancelar con una antelación menos a 2 horas de la salida del itinerario
	 */
	public void cancelTicket(Long ticketId) throws InstanceNotFoundException,
			TimeoutTicketException;

	/**
	/**
	 * Método que devuelve todos los billetes que aún están vigentes(el tren aún no ha salido)
	 */
	public TicketBlock showUserTickets(Long userId, int startIndex, int count);

	/**
	 * Método que devuelve el historial de compra de un usuario con un número limitado de dichas compras
	 */
	public List<Purchase> findPurchaseByUserId(Long userId, int startIndex,
			int count) throws InstanceNotFoundException;

	/**
	 * Devuelve el número de compras que ha relizado el usuario
	 */
	public int getNumberOfPurchases(Long userId)
			throws InstanceNotFoundException;

	/**
	 * Método que busca todos los vagones para un determinado itinerario y fecha y devuelve
	 * un objeto con los vagones y con una lista con todos los asientos vacios marcados con un 0
	 * y los ocupados con un 1
	 */
	public List<CarInfo> findCars(Calendar ticketDate, CarType carType,
			Long stopId) throws InstanceNotFoundException;

	/**
	 * Método que envía todos los billetes al email proporcionado
	 */
	public void sendTickets(Long purchaseId)
			throws InstanceNotFoundException;

	/**
	 * Método que calcula el precio total que cuesta un determinado trayecto(incluido los de ida y vuelta)
	 * a partir de todos los pasajeros y sus tarifas
	 */
	public float calculatePayment(Long id, Long idReturn,
			List<TicketDetails> ticketDetails,CarType carType,CarType carTypeReturn) throws InstanceNotFoundException;
	
	public Ticket findTicket(Long ticketId) throws InstanceNotFoundException;
	
	public Fare findFare(Long id) throws InstanceNotFoundException;

	public Purchase findPurchase(Long purchaseId)
			throws InstanceNotFoundException;
}
