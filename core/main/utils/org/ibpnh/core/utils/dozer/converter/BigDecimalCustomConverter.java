package org.ibpnh.core.utils.dozer.converter;

import java.math.BigDecimal;

import org.dozer.DozerConverter;
import org.ibpnh.core.utils.BigDecimalWithoutTypeAdapting;

public class BigDecimalCustomConverter extends DozerConverter<BigDecimal, BigDecimalWithoutTypeAdapting> {

	public BigDecimalCustomConverter() {
		super(BigDecimal.class, BigDecimalWithoutTypeAdapting.class);
	}

	@Override
	public BigDecimal convertFrom(BigDecimalWithoutTypeAdapting bigDecimalWithoutTypeAdapting,
			BigDecimal bigDecimal) {
		return (BigDecimal)bigDecimalWithoutTypeAdapting;
	}

	@Override
	public BigDecimalWithoutTypeAdapting convertTo(BigDecimal bigDecimal,
			BigDecimalWithoutTypeAdapting bigDecimalWithoutTypeAdapting) {
		if(bigDecimal !=null){
			return new BigDecimalWithoutTypeAdapting(bigDecimal);
		}else{
			return null; 
		}
				
	}

}
