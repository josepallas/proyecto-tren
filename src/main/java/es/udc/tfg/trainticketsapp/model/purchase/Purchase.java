package es.udc.tfg.trainticketsapp.model.purchase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import es.udc.tfg.trainticketsapp.model.ticket.Ticket;
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfile;

@Entity
public class Purchase {
	public enum PaymentMethod {
		EFECTIVO, PAYPAL
	};

	private Long purchaseId;
	private Calendar purchaseDate;
	private PaymentMethod paymentMethod;
	private UserProfile userProfile;
	private List<Ticket> tickets = new ArrayList<Ticket>();

	public Purchase() {
	}

	public Purchase(Calendar purchaseDate, PaymentMethod paymentMethod,
			UserProfile userProfile) {
		this.purchaseDate = purchaseDate;
		this.paymentMethod = paymentMethod;
		this.userProfile = userProfile;
	}

	@SequenceGenerator(name = "PurchaseIdGenerator", sequenceName = "PurchaseSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PurchaseIdGenerator")
	public Long getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Calendar getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Calendar purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "usrId")
	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	@OneToMany(mappedBy = "purchase")
	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public void addTicket(Ticket ticket) {
		tickets.add(ticket);
		ticket.setPurchase(this);
	}

	public void removeTicket(Ticket ticket) {
		tickets.remove(ticket);
		ticket.setPurchase(null);
	}

}
