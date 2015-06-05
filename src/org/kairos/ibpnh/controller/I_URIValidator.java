package org.kairos.ibpnh.controller;

/**
 * Decides if an URI needs to ve validated.
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
public interface I_URIValidator {

	/**
	 * Checks if a URI needs to be validated.
	 * 
	 * @param uri the URI to check
	 * 
	 * @return true if the URI needs to be checked
	 */
	public Boolean validate(String uri);
	
}
