package es.udc.tfg.trainticketsapp.model.purchase;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface PurchaseDao extends GenericDao<Purchase, Long> {
	public List<Purchase> findPurchasesByUser(Long userId, int startIndex,
			int count);

	public int getNumberOfPurchases(Long userId);

}
