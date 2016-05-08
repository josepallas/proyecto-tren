package es.udc.tfg.trainticketsapp.model.purchase;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("purchaseDao")
public class PurchaseDaoHibernate extends GenericDaoHibernate<Purchase, Long> implements PurchaseDao {
	@SuppressWarnings("unchecked")
	public List<Purchase> findPurchasesByUser(Long userId, int startIndex, int count) {
		return getSession().createQuery("SELECT p FROM Purchase p WHERE " +
				"p.userProfile.userProfileId = :userId ORDER BY p.purchaseDate DESC")
				.setParameter("userId", userId)
				.setMaxResults(count)
				.setFirstResult(startIndex)
				.list();
	}


	public int getNumberOfPurchases(Long userProfileId) {
		long numberOfPurchases = (Long) getSession().createQuery(
                "SELECT COUNT(o) FROM Purchase o WHERE " +
                "o.userProfile.userProfileId = :userProfileId  ").
                setParameter("userProfileId", userProfileId).
                uniqueResult();

        return (int) numberOfPurchases;

	}

}
