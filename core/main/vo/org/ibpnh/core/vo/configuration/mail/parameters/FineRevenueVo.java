package org.ibpnh.core.vo.configuration.mail.parameters;

import java.math.BigDecimal;

/**
 * Helper Vo for the fine revenue query.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class FineRevenueVo {

	public BigDecimal parkingFineRevenue;
	public BigDecimal parkingFineNetRevenue;
	public BigDecimal parkingFineCommissionPercentage;
	public BigDecimal transitFineRevenue;
	public BigDecimal transitFineNetRevenue;
	public BigDecimal transitFineCommissionPercentage;

	/**
	 * @param parkingFineRevenue
	 * @param parkingFineNetRevenue
	 * @param parkingFineCommissionPercentage
	 * @param transitFineRevenue
	 * @param transitFineNetRevenue
	 * @param transitFineCommissionPercentage
	 */
	public FineRevenueVo(BigDecimal parkingFineRevenue,
			BigDecimal parkingFineNetRevenue,
			BigDecimal parkingFineCommissionPercentage,
			BigDecimal transitFineRevenue, BigDecimal transitFineNetRevenue,
			BigDecimal transitFineCommissionPercentage) {
		super();
		this.parkingFineRevenue = parkingFineRevenue;
		this.parkingFineNetRevenue = parkingFineNetRevenue;
		this.parkingFineCommissionPercentage = parkingFineCommissionPercentage;
		this.transitFineRevenue = transitFineRevenue;
		this.transitFineNetRevenue = transitFineNetRevenue;
		this.transitFineCommissionPercentage = transitFineCommissionPercentage;
	}

	/**
	 * @return the parkingFineRevenue
	 */
	public BigDecimal getParkingFineRevenue() {
		return this.parkingFineRevenue;
	}

	/**
	 * @param parkingFineRevenue
	 *            the parkingFineRevenue to set
	 */
	public void setParkingFineRevenue(BigDecimal parkingFineRevenue) {
		this.parkingFineRevenue = parkingFineRevenue;
	}

	/**
	 * @return the parkingFineNetRevenue
	 */
	public BigDecimal getParkingFineNetRevenue() {
		return this.parkingFineNetRevenue;
	}

	/**
	 * @param parkingFineNetRevenue
	 *            the parkingFineNetRevenue to set
	 */
	public void setParkingFineNetRevenue(BigDecimal parkingFineNetRevenue) {
		this.parkingFineNetRevenue = parkingFineNetRevenue;
	}

	/**
	 * @return the parkingFineCommissionPercentage
	 */
	public BigDecimal getParkingFineCommissionPercentage() {
		return this.parkingFineCommissionPercentage;
	}

	/**
	 * @param parkingFineCommissionPercentage
	 *            the parkingFineCommissionPercentage to set
	 */
	public void setParkingFineCommissionPercentage(
			BigDecimal parkingFineCommissionPercentage) {
		this.parkingFineCommissionPercentage = parkingFineCommissionPercentage;
	}

	/**
	 * @return the transitFineRevenue
	 */
	public BigDecimal getTransitFineRevenue() {
		return this.transitFineRevenue;
	}

	/**
	 * @param transitFineRevenue
	 *            the transitFineRevenue to set
	 */
	public void setTransitFineRevenue(BigDecimal transitFineRevenue) {
		this.transitFineRevenue = transitFineRevenue;
	}

	/**
	 * @return the transitFineNetRevenue
	 */
	public BigDecimal getTransitFineNetRevenue() {
		return this.transitFineNetRevenue;
	}

	/**
	 * @param transitFineNetRevenue
	 *            the transitFineNetRevenue to set
	 */
	public void setTransitFineNetRevenue(BigDecimal transitFineNetRevenue) {
		this.transitFineNetRevenue = transitFineNetRevenue;
	}

	/**
	 * @return the transitFineCommissionPercentage
	 */
	public BigDecimal getTransitFineCommissionPercentage() {
		return this.transitFineCommissionPercentage;
	}

	/**
	 * @param transitFineCommissionPercentage
	 *            the transitFineCommissionPercentage to set
	 */
	public void setTransitFineCommissionPercentage(
			BigDecimal transitFineCommissionPercentage) {
		this.transitFineCommissionPercentage = transitFineCommissionPercentage;
	}

}
