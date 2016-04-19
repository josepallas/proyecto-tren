package es.udc.tfg.trainticketsapp.model.ticket;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import es.udc.tfg.trainticketsapp.model.car.Car;
import es.udc.tfg.trainticketsapp.model.passenger.Passenger;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase;
import es.udc.tfg.trainticketsapp.model.stop.Stop;

@Entity
public class Ticket {
	private Long ticketId;
	private Float realPrice;
	private String seat;
	private Calendar ticketDate;
	private Purchase purchase;
	private Car car;
	private Passenger passenger;
	private Stop destination;
	private Stop origin;
	
	public Ticket() {
	}

	public Ticket(Float realPrice, String seat, Calendar ticketDate,
			 Car car, Passenger passenger, Stop destination,
			Stop origin) {
		this.realPrice = realPrice;
		this.seat = seat;
		this.ticketDate = ticketDate;
		this.car = car;
		this.passenger = passenger;
		this.destination = destination;
		this.origin = origin;
	}

	@SequenceGenerator(name = "TicketIdGenerator", sequenceName = "TicketSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "TicketIdGenerator")		
	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Float getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(Float realPrice) {
		this.realPrice = realPrice;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public Calendar getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(Calendar ticketDate) {
		this.ticketDate = ticketDate;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="purchaseId")		
	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="carId")	
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="passengerId")		
	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="destinationId")	
	public Stop getDestination() {
		return destination;
	}

	public void setDestination(Stop destination) {
		this.destination = destination;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="originId")		
	public Stop getOrigin() {
		return origin;
	}

	public void setOrigin(Stop origin) {
		this.origin = origin;
	}

	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", realPrice=" + realPrice
				+ ", seat=" + seat + ", ticketDate=" + ticketDate
				+ ", purchase=" + purchase + ", car=" + car + ", passenger="
				+ passenger + ", destination=" + destination + ", origin="
				+ origin + "]";
	}

	
}
