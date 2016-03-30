package org.kairos.ibpnh.core.services.mail;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.kairos.ibpnh.core.dao.EntityManagerHolder;
import org.kairos.ibpnh.core.dao.configuration.mail.I_MailDao;
import org.kairos.ibpnh.core.fx.I_FxFactory;
import org.kairos.ibpnh.core.fx.I_MailFx;
import org.kairos.ibpnh.core.services.mail.job.GenericMailSenderJob;
import org.kairos.ibpnh.core.services.mail.job.MailSenderTask;
import org.kairos.ibpnh.core.utils.ErrorCodes;
import org.kairos.ibpnh.core.utils.exception.CodedException;
import org.kairos.ibpnh.core.vo.configuration.mail.MailVo;

/**
 * Mail Job Scheduler Interface Implementation using Quartz.
 * 
 * @author fgonzalez
 * 
 */
public class MailJobSchedulerQuartzImpl implements I_MailJobScheduler {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory
			.getLogger(MailJobSchedulerQuartzImpl.class);

	/**
	 * The Quartz Scheduler Object.
	 */
	@Autowired
	@Qualifier("quarzScheduler")
	private Scheduler quartzScheduler;

	/**
	 * Mail DAO.
	 */
	@Autowired
	private I_MailDao mailDao;

	/**
	 * Entity Manager Holder.
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/**
	 * FX Factory.
	 */
	@Autowired
	private I_FxFactory fxFactory;

	/**
	 * Spring Application Context
	 */
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Init-method of this bean that schedules all existent mails.
	 */
	public void scheduleMails() {
		this.logger
				.debug("executing MailJobSchedulerQuartzImpl.scheduleMails()");
		EntityManager em = this.getEntityManagerHolder().getEntityManager();

		try {
		List<MailVo> mails = this.getMailDao().listAll(em);

		Integer candidates = 0;
		Integer scheduled = 0;
		for (MailVo mail : mails) {
			if (mail.getEnabled() != null && mail.getEnabled()
					&& mail.getCron() != null && mail.getCron()) {
				candidates++;
				try {
					this.scheduleMail(mail);
					scheduled++;
				} catch (CodedException e) {
					this.logger.error("error scheduling mail at startup: "
							+ mail.getName(), e);
				}
			}
		}

		this.logger
				.debug("{} mails scheduled from {} candidates of {} total fetched mails",
						scheduled, candidates, mails.size());
		} catch (Exception e) {
			this.logger.error("unexpected error", e);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_MailJobScheduler#scheduleMail(org.kairos.ibpnh
	 * .core.vo.configuration.mail.MailVo)
	 */
	@Override
	public void scheduleMail(MailVo mailVo) throws CodedException {
		this.logger.debug("entering MailJobSchedulerQuartzImpl.scheduleMail()");

		// sets the job data map
		JobDataMap jdm = new JobDataMap();
		jdm.put("mailVo", mailVo);

		// defines a new trigger key based on the MailVo name
		TriggerKey triggerKey = this.triggerKey(mailVo);
		JobKey jobKey = this.jobKey(mailVo);

		try {
			Trigger oldTrigger = this.getQuartzScheduler().getTrigger(
					triggerKey);
			if (oldTrigger != null) {
				this.getQuartzScheduler().unscheduleJob(triggerKey);
			}
			this.getQuartzScheduler().deleteJob(jobKey);

		} catch (SchedulerException se) {
			this.logger.error("error getting or unscheduling job", se);
		}

		try {
			JobDetail jobDetail = JobBuilder.newJob(GenericMailSenderJob.class)
					.usingJobData(jdm).withIdentity(jobKey).build();
			Trigger newTrigger = TriggerBuilder.newTrigger()
					.withSchedule(this.getSchedulerBuilder(mailVo)).startNow()
					.withIdentity(triggerKey).build();

			this.getQuartzScheduler().scheduleJob(jobDetail, newTrigger);
		} catch (ParseException pe) {
			this.logger.error("error scheduling job with yearly cron", pe);
			throw new CodedException("error scheduling job with yearly cron",
					ErrorCodes.ERROR_PARSING_CRON_EXPRESSION, pe);
		} catch (SchedulerException se) {
			this.logger.error("error getting or scheduling job", se);
			throw new CodedException("error getting or scheduling job",
					ErrorCodes.ERROR_SCHEDULING_JOB, se);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_MailJobScheduler#unscheduleMail(org
	 * .ibpnh.core.vo.configuration.mail.MailVo)
	 */
	@Override
	public void unscheduleMail(MailVo mailVo) throws CodedException {
		TriggerKey triggerKey = this.triggerKey(mailVo);
		JobKey jobKey = this.jobKey(mailVo);
		this._unscheduleMail(triggerKey, jobKey);
	}

	/**
	 * 
	 */
	@Override
	public void unscheduleMail(String mailName) throws CodedException {
		TriggerKey triggerKey = new TriggerKey(mailName);
		JobKey jobKey = new JobKey(mailName);
		this._unscheduleMail(triggerKey, jobKey);
	}

	/**
	 * Unschedules a mail using the job and trigger keys.
	 * 
	 * @param triggerKey
	 *            triggerKey to use
	 * @param jobKey
	 *            jobKey to use
	 * @throws CodedException
	 */
	private void _unscheduleMail(TriggerKey triggerKey, JobKey jobKey)
			throws CodedException {
		try {
			Trigger oldTrigger = this.getQuartzScheduler().getTrigger(
					triggerKey);
			if (oldTrigger != null) {
				this.getQuartzScheduler().unscheduleJob(triggerKey);
			}
			this.getQuartzScheduler().deleteJob(jobKey);
		} catch (SchedulerException se) {
			this.logger.error("error getting or unscheduling job", se);
			throw new CodedException("error getting or unscheduling job",
					ErrorCodes.ERROR_UNSCHEDULING_JOB, se);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_MailJobScheduler#triggerMail(org.kairos.ibpnh
	 * .core.vo.configuration.mail.MailVo)
	 */
	@Override
	public void triggerMail(MailVo mailVo) throws CodedException {
		try {
			this.getQuartzScheduler().triggerJob(this.jobKey(mailVo));
		} catch (SchedulerException se) {
			this.logger.error("error triggering job", se);
			throw new CodedException("error triggering job",
					ErrorCodes.ERROR_TRIGGERING_JOB, se);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_MailJobScheduler#triggerMail(java.lang
	 * .String, I_MailFx)
	 */
	@Override
	public void triggerMail(String mailVoName, I_MailFx mailFx)
			throws CodedException {
		EntityManager em = this.getEntityManagerHolder().getEntityManager();
		
		try {
			MailVo mailVo = this.getMailDao().findByName(em, mailVoName);
	
			MailSenderTask mailSenderTask = this.getApplicationContext().getBean(
					MailSenderTask.class);
			mailSenderTask.setMailVo(mailVo);
			mailSenderTask.setMailFx(mailFx);
	
			new Thread(mailSenderTask).start();
		} catch (Exception e) {
			this.logger.error("unexpected error", e);
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
	}

	/**
	 * Returns the appropriate scheduler builder.
	 * 
	 * @return
	 */
	private CronScheduleBuilder getSchedulerBuilder(MailVo mailVo)
			throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mailVo.getSendTimestamp());

		switch (mailVo.getRepetition()) {
		case DAY: {
			return CronScheduleBuilder.dailyAtHourAndMinute(
					calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE));
		}
		case WEEK: {
			return CronScheduleBuilder.weeklyOnDayAndHourAndMinute(
					calendar.get(Calendar.DAY_OF_WEEK),
					calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE));
		}
		case MONTH: {
			return CronScheduleBuilder.monthlyOnDayAndHourAndMinute(
					calendar.get(Calendar.DAY_OF_MONTH),
					calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE));
		}
		case YEAR: {
			String cron = "0"; // we don't care about seconds
			cron += " " + calendar.get(Calendar.MINUTE);
			cron += " " + calendar.get(Calendar.HOUR_OF_DAY);
			cron += " " + calendar.get(Calendar.DAY_OF_MONTH);
			cron += " " + (calendar.get(Calendar.MONTH) + 1);
			cron += " ?"; // we don't care about the day of week
			cron += " *"; // we want every year

			return CronScheduleBuilder.cronSchedule(new CronExpression(cron));
		}
		default: {
			// should never got to here
			return null;
		}
		}
	}

	/**
	 * Generates the trigger key for a mail VO.
	 * 
	 * @param mailVo
	 *            the mail VO to generate the job key to
	 * 
	 * @return a TriggerKey
	 */
	private TriggerKey triggerKey(MailVo mailVo) {
		TriggerKey triggerKey = new TriggerKey(mailVo.getName());
		return triggerKey;
	}

	/**
	 * Generates the job key for a mail VO.
	 * 
	 * @param mailVo
	 *            the mail VO to generate the job key to
	 * 
	 * @return a JobKey
	 */
	private JobKey jobKey(MailVo mailVo) {
		JobKey jobKey = new JobKey(mailVo.getName() + "Job");
		return jobKey;
	}

	/**
	 * @return the quartzScheduler
	 */
	public Scheduler getQuartzScheduler() {
		return this.quartzScheduler;
	}

	/**
	 * @param quartzScheduler
	 *            the quartzScheduler to set
	 */
	public void setQuartzScheduler(Scheduler quartzScheduler) {
		this.quartzScheduler = quartzScheduler;
	}

	/**
	 * @return the mailDao
	 */
	public I_MailDao getMailDao() {
		return this.mailDao;
	}

	/**
	 * @param mailDao
	 *            the mailDao to set
	 */
	public void setMailDao(I_MailDao mailDao) {
		this.mailDao = mailDao;
	}

	/**
	 * @return the entityManagerHolder
	 */
	public EntityManagerHolder getEntityManagerHolder() {
		return this.entityManagerHolder;
	}

	/**
	 * @param entityManagerHolder
	 *            the entityManagerHolder to set
	 */
	public void setEntityManagerHolder(EntityManagerHolder entityManagerHolder) {
		this.entityManagerHolder = entityManagerHolder;
	}

	/**
	 * @return the fxFactory
	 */
	public I_FxFactory getFxFactory() {
		return this.fxFactory;
	}

	/**
	 * @param fxFactory
	 *            the fxFactory to set
	 */
	public void setFxFactory(I_FxFactory fxFactory) {
		this.fxFactory = fxFactory;
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	/**
	 * @param applicationContext
	 *            the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
