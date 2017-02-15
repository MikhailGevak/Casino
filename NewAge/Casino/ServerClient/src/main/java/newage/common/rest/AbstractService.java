package newage.common.rest;

import newage.common.exception.ServiceException;

public abstract class AbstractService<E extends ServiceException> {
	abstract protected E createServiceException(Exception ex);

	protected <T> T exceptionHandle(ServiceFunction<T> serviceFunction) throws E {
		try {
			return serviceFunction.doFunction();
		} catch (Exception ex) {
			throw createServiceException(ex);
		}
	}

	@FunctionalInterface
	protected static interface ServiceFunction<T> {
		T doFunction() throws Exception;
	}
}
