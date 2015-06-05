package org.kairos.ibpnh.dao;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.vo.*;

import java.util.List;

/**
 * General Interfaces for all Daos.
 *
 * @author AxelCollardBovy ,created on 27/02/2015.
 *
 *  * @param <E>
 *            the VO class that this DAO returns
 */
public interface I_Dao<E extends AbstractVo> {

    /**
     * Gets the class of the VO that this DAO has as it parameterized type.
     *
     * @return
     */
    public Class<E> getVoClazz();

    /**
     * Flag to set the policy that the DAO will take upon complex objects.
     *
     * If setted to true (default) it will map using the non-classified default
     * mappings on the dozer configuration file, i.e, it will bring all the
     * complex object that the root object references.
     *
     * If setted to false, it will use the "non-deep" mapping ID and rely on it
     * to get only the object's primitive types mapped. (This will be subject to
     * the user's mapping in the file).
     */
    public void setDeepMapping(Boolean deepMapping);

    /**
     * This one indicates a subclass of the specified VO to use as the mapping
     * classificator.
     *
     * @param deepMapping
     * @param clazz
     */
    public void setDeepMapping(Boolean deepMapping, Class<? extends E> clazz);

    /**
     * Returns the current status of the flag.
     */
    public Boolean getDeepMapping();

    /**
     * Lists all entities
     *
     * @param pm
     *            the persistence manager
     * @param orderVo
     *            the order to add
     *
     * @return a list of VOs
     */
    public List<E> listAll(JDOPersistenceManager pm, OrderVo orderVo);

    /**
     * Lists all entities (using the subclassed-DAO defined order)
     *
     * @param pm
     *            the persistence manager
     *
     * @return a list of VOs
     */
    public List<E> listAll(JDOPersistenceManager pm);

    /**
     * Counts all entities
     *
     * @param pm
     *            the persistence manager
     *
     * @return total count of elements
     */
    public Long countAll(JDOPersistenceManager pm);

    /**
     * Counts all entities filtered for a search
     *
     * @param pm
     *            the persistence manager
     * @param vo
     *            the data to filter the entities with
     *
     * @return total count of elements
     */
    public Long filteredCountAll(JDOPersistenceManager pm, E vo);

    /**
     * Lists a page
     *
     * @param pm
     *            the persistence manager
     * @param paginatedRequestVo
     *            a list request with the pagination info
     * @params itemsPerPage the total items to show per page
     * @params orderVo the order to use
     *
     * @return a list of VOs
     */
    public PaginatedListVo<E> listPage(JDOPersistenceManager pm,
                                       PaginatedRequestVo paginatedRequestVo, Long itemsPerPage,
                                       OrderVo orderVo);

    /**
     * Lists a page (using the subclassed-DAO defined order)
     *
     * @param pm
     *            the persistence manager
     * @param paginatedRequestVo
     *            a list request with the pagination info
     * @params itemsPerPage the total items to show per page
     * @params orderVo the order to use
     *
     * @return a list of VOs
     */
    public PaginatedListVo<E> listPage(JDOPersistenceManager pm,
                                       PaginatedRequestVo paginatedRequestVo, Long itemsPerPage);

    /**
     * Gets an entity by its ID.
     *
     * @param id
     *            to get
     * @param pm
     *            the persistence manager
     *
     * @return entity VO or null
     */
    public E getById(JDOPersistenceManager pm, String id);

    /**
     * Persists a new entity.
     *
     * @param entityVo
     *            the entity to persist
     * @param pm
     *            the persistence manager
     */
    public E persist(JDOPersistenceManager pm, E entityVo);

    /**
     * Deletes an entity.
     *
     * @param entityVo
     *            the entity to persist
     * @param pm
     *            the persistence manager
     */
    public void delete(JDOPersistenceManager pm, E entityVo);

    /**
     * Searches the entities using the data passed in the VO as filters.
     *
     * @param pm
     *            the persistence manager
     * @param vo
     *            vo with filter data
     * @param orderVo
     *            the order to use
     *
     * @return the list of matching entities
     */
    public List<E> search(JDOPersistenceManager pm, E vo, OrderVo orderVo);

    /**
     * Searches the entities using the data passed in the VO as filters. (using
     * the subclassed-DAO defined order)
     *
     * @param pm
     *            the persistence manager
     * @param vo
     *            vo with filter data
     *
     * @return the list of matching entities
     */
    public List<E> search(JDOPersistenceManager pm, E vo);

    /**
     * Searches the entities using the data passed in the VO as filters and
     * returns the requested page.
     *
     * @param pm
     *            the persistence manager
     * @param paginatedSearchRequestVo
     *            paginated request with filter and page data
     * @param itemsPerPage
     *            the total items to show per page
     * @param orderVo
     *            the order to use
     *
     * @return the list of matching entities
     */
    public PaginatedListVo<E> searchPage(JDOPersistenceManager pm,
                                         PaginatedSearchRequestVo<E> paginatedSearchRequestVo,
                                         Long itemsPerPage, OrderVo orderVo);

    /**
     * Searches the entities using the data passed in the VO as filters and
     * returns the requested page. (using the subclassed-DAO defined order)
     *
     * @param pm
     *            the persistence manager
     * @param paginatedSearchRequestVo
     *            paginated request with filter and page data
     * @param itemsPerPage
     *            the total items to show per page
     *
     * @return the list of matching entities
     */
    public PaginatedListVo<E> searchPage(JDOPersistenceManager pm,
                                         PaginatedSearchRequestVo<E> paginatedSearchRequestVo,
                                         Long itemsPerPage);

    /**
     * Sets all the searches to add a distinct clause.
     *
     * Should be setted as false, by default.
     *
     * @param distinct
     *            flag indicating to add or not a distinct clause
     *
     */
    public void searchDistinct(Boolean distinct);
}
