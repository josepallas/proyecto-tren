package es.udc.tfg.trainticketsapp.web.pages.purchase;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.tfg.trainticketsapp.model.purchase.Purchase;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.web.util.PurchaseGridDataSource;
import es.udc.tfg.trainticketsapp.web.util.UserSession;

public class UserPurchases {
	private final static int ROWS_PER_PAGE = 10;

	private PurchaseGridDataSource purchaseGridDataSource;
   @SessionState(create=false)
    private UserSession userSession;
   
   @Inject
   private PurchaseService purchaseService;
      
   private Purchase purchase;
   
   @Inject
	private Locale locale;
   
   public PurchaseGridDataSource getPurchaseGridDataSource(){
	   return purchaseGridDataSource;
   }
   
   public Purchase getPurchase() {
	   return this.purchase;
   }
   
   public void setPurchase(Purchase purchase) {
	   this.purchase=purchase;
   }
   
	public DateFormat getDateFormat() {
		return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,locale);
	}
	

	public Format getNumberFormat() {
		return NumberFormat.getInstance(locale);
	}

	public int getRowsPerPage() {
		return ROWS_PER_PAGE;
	}
   void onActivate(){
   purchaseGridDataSource= new PurchaseGridDataSource(purchaseService, userSession.getUserProfileId());
   }
   
   
}
