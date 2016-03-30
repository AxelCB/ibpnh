package org.kairos.ibpnh.core.services.mail;

import org.kairos.ibpnh.core.fx.I_MailFx;
import org.kairos.ibpnh.core.utils.exception.CodedException;
import org.kairos.ibpnh.core.vo.configuration.mail.MailVo;

/**
 * Interface for scheduling mails.
 * 
 * @author fgonzalez
 * 
 */
public interface I_MailJobScheduler {

	/**
	 * Schedules a mail definition.
	 * 
	 * @param mailVo
	 *            mail VO with the mail data to schedule
	 */
	public void scheduleMail(MailVo mailVo) throws CodedException;

	/**
	 * Unschedules a mail definition.
	 * 
	 * @param mailVo
	 *            mail VO with the mail data to unschedule
	 */
	public void unscheduleMail(MailVo mailVo) throws CodedException;

	/**
	 * Unschedules a mail definition.
	 * 
	 * @param mailName
	 *            mail VO with the mail data to unschedule
	 */
	public void unscheduleMail(String mailName) throws CodedException;

	/**
	 * Manually triggers a mail.
	 * 
	 * @param mailVo
	 *            mail VO with the mail data to trigger
	 */
	public void triggerMail(MailVo mailVo) throws CodedException;

	/**
	 * Manually triggers a mail that it's not scheduled to be executed.
	 * 
	 * @param mailVoName
	 *            mail name to send
	 * @param mailFx
	 *            the mail FX that this mail needs to execute
	 */
	public void triggerMail(String mailVoName, I_MailFx mailFx)
			throws CodedException;

}
