package es.udc.tfg.trainticketsapp.web.util;

import java.util.List;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;


public class PurchaseGridDataSource  implements GridDataSource {
	private PurchaseService purchaseService;
	private Long userId;
	private List<Purchase> purchases;
	private int startIndex;
	private boolean accountNotFound;


	public PurchaseGridDataSource(PurchaseService purchaseService,Long userId) {
		this.userId=userId;
		this.purchaseService=purchaseService;
		
	}
	
    public int getAvailableRows() {
			try {
				return purchaseService.getNumberOfPurchases(userId);
			} catch (InstanceNotFoundException e) {
				accountNotFound = true;
				return 0;
			}
		}
	
    public Class<Purchase> getRowType() {
        return Purchase.class;
    }
    public Object getRowValue(int index) {
        return purchases.get(index-this.startIndex);
    }
    
    public void prepare(int startIndex, int endIndex,
        	List<SortConstraint> sortConstraints) {

    	try {
			purchases = purchaseService.findPurchaseByUserId(
			    				userId, startIndex,
			    				endIndex-startIndex+1);
		} catch (InstanceNotFoundException e) {
		}
    	        this.startIndex = startIndex;


        }
    public boolean getAccountNotFound() {
    	return accountNotFound;
    }
    
}
