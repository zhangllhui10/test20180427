package com.hui10.app.common.utils;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.hui10.common.utils.PropertiesUtils;
import com.hui10.exception.CommonException;

/**
 * @ClassName: ValidatorUtils.java
 * @Description:
 * @author wengf
 * @date 2016年11月28日 上午11:38:11
 */
public class ValidatorUtils {

	private static Validator validator;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	public static <T> void checkBean(T bean, int errorCode) {
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean);

		if (constraintViolations.size() > 0) {
			Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();

			StringBuffer errorMessage = new StringBuffer();

			while (iterator.hasNext()) {
				ConstraintViolation<T> constraintViolation = iterator.next();

				errorMessage = errorMessage.append(constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage() + ";");
			}
			throw new CommonException(errorCode, PropertiesUtils.get(errorCode)  + errorMessage );
		}

	}

}
