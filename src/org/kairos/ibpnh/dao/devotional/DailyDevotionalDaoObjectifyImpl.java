package org.kairos.ibpnh.dao.devotional;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ServingUrlOptions;
import org.kairos.ibpnh.dao.AbstractDao;
import org.kairos.ibpnh.model.devotional.DailyDevotional;
import org.kairos.ibpnh.vo.PaginatedListVo;
import org.kairos.ibpnh.vo.PaginatedRequestVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Dao for the Daily Devotional's entities.
 *
 * Created on 9/25/15 by
 *
 * @author AxelCollardBovy.
 */
public class DailyDevotionalDaoObjectifyImpl extends AbstractDao<DailyDevotional> implements I_DailyDevotionalDao{

	/**
	 * Images Service
	 */
	@Autowired
	private ImagesService imagesService;

	@Override
	public Class<DailyDevotional> getClazz() {
		return DailyDevotional.class;
	}

	@Override
	public DailyDevotional getByDate(Date date) {
		return null;
	}

	@Override
	public Boolean checkDateUniqueness(Date date, Long excludeId) {
		return true;
	}

	@Override
	public List<DailyDevotional> listLastDevotionals(Long amount, Date date) {
		List<DailyDevotional> dailyDevotionals = ofy().load().type(this.getClazz()).filter("date <=",date).limit(amount.intValue()).order("-date").list();
		for (DailyDevotional dailyDevotional : dailyDevotionals){
			BlobKey blobKey = new BlobKey(dailyDevotional.getImageBlobKey());
			dailyDevotional.setImageUrl(imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey)));
		}
		return dailyDevotionals;
	}

	@Override
	public PaginatedListVo<DailyDevotional> listPage(PaginatedRequestVo paginatedRequest, Long itemsPerPage) {
		PaginatedListVo<DailyDevotional> paginatedList = super.listPage(paginatedRequest, itemsPerPage);
        for (DailyDevotional dailyDevotional : paginatedList.getItems()){
            BlobKey blobKey = new BlobKey(dailyDevotional.getImageBlobKey());
            dailyDevotional.setImageUrl(imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey)));
        }
		return paginatedList;
	}

	@Override
	public List<DailyDevotional> listAll() {
		List<DailyDevotional> dailyDevotionals = super.listAll();
		for (DailyDevotional dailyDevotional : dailyDevotionals){
			BlobKey blobKey = new BlobKey(dailyDevotional.getImageBlobKey());
			dailyDevotional.setImageUrl(imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey)));
		}
		return dailyDevotionals;
	}
}