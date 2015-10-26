package org.kairos.ibpnh.dao.devotional;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ServingUrlOptions;
import org.kairos.ibpnh.dao.AbstractDao;
import org.kairos.ibpnh.model.devotional.DailyDevotional;
import org.kairos.ibpnh.vo.PaginatedListVo;
import org.kairos.ibpnh.vo.PaginatedRequestVo;
import org.kairos.ibpnh.vo.devotional.DailyDevotionalVo;
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
public class DailyDevotionalDaoObjectifyImpl extends AbstractDao<DailyDevotional,DailyDevotionalVo> implements I_DailyDevotionalDao{

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
	public Class<DailyDevotionalVo> getVoClazz() {
		return DailyDevotionalVo.class;
	}

	@Override
	public DailyDevotionalVo getByDate(Date date) {
		return null;
	}

	@Override
	public Boolean checkDateUniqueness(Date date, Long excludeId) {
		return true;
	}

	@Override
	public List<DailyDevotionalVo> listLastDevotionals(Long amount, Date date) {
		List<DailyDevotionalVo> dailyDevotionals = this.map(ofy().load().type(this.getClazz()).filter("date <=", date).limit(amount.intValue()).order("-date").list());
		for (DailyDevotionalVo dailyDevotional : dailyDevotionals){
			BlobKey blobKey = new BlobKey(dailyDevotional.getImageBlobKey());
			dailyDevotional.setImageUrl(imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey)));
		}
		return dailyDevotionals;
	}

	@Override
	public PaginatedListVo<DailyDevotionalVo> listPage(PaginatedRequestVo paginatedRequest, Long itemsPerPage) {
		PaginatedListVo<DailyDevotionalVo> paginatedList = super.listPage(paginatedRequest, itemsPerPage);
        for (DailyDevotionalVo dailyDevotional : paginatedList.getItems()){
            BlobKey blobKey = new BlobKey(dailyDevotional.getImageBlobKey());
            dailyDevotional.setImageUrl(imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey)));
        }
		return paginatedList;
	}

	@Override
	public List<DailyDevotionalVo> listAll() {
		List<DailyDevotionalVo> dailyDevotionals = this.listAll();
		for (DailyDevotionalVo dailyDevotional : dailyDevotionals){
			BlobKey blobKey = new BlobKey(dailyDevotional.getImageBlobKey());
			dailyDevotional.setImageUrl(imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey)));
		}
		return dailyDevotionals;
	}

    @Override
    public DailyDevotionalVo getById(Long id) {
        DailyDevotionalVo dailyDevotional = this.getById(id);
        BlobKey blobKey = new BlobKey(dailyDevotional.getImageBlobKey());
        dailyDevotional.setImageUrl(imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey)));
        return dailyDevotional;
    }
}