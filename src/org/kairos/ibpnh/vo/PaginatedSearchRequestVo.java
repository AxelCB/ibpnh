package org.kairos.ibpnh.vo;


/**
 * Value Object for a search request that is used with pagination.
 * 
 * @author fgonzalez
 *
 * @param <E> the type of objects to be listed
 */
public class PaginatedSearchRequestVo<E extends AbstractVo> extends PaginatedRequestVo {

	/**
	 * The data to use to filter the entities (could be null in case of a full list)
	 */
	private E vo;

	/**
	 * Constructor with VO. 
	 * 
	 * @param vo
	 */
	public PaginatedSearchRequestVo(E vo) {
		super();
		this.setVo(vo);
	}

	public E getVo() {
		return vo;
	}

	public void setVo(E vo) {
		this.vo = vo;
	}
}
