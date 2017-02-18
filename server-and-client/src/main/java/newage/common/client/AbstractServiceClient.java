package newage.common.client;

import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.jetty.http.HttpStatus;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import newage.common.exception.ParseAnswerException;
import newage.common.exception.ServiceException;

public abstract class AbstractServiceClient {

	protected <T, E extends ServiceException> T handleResponse(Class<T> resultClass, Class<E> exceptionClass,
			Supplier<ClientResponse> function) throws E, ParseAnswerException {
		return handleResponse(response -> response.getEntity(resultClass), exceptionClass, function);
	}

	protected <T, E extends Throwable> T handleResponse(GenericType<T> resultGeneric, Class<E> exceptionClass,
			Supplier<ClientResponse> function) throws E, ParseAnswerException {
		return handleResponse(response -> response.getEntity(resultGeneric), exceptionClass, function);
	}

	protected <T, E extends Throwable> T handleResponse(Function<ClientResponse, T> getFunction,
			Class<E> exceptionClass, Supplier<ClientResponse> function) throws E, ParseAnswerException {
		ClientResponse response = function.get();
		if (response.getStatus() == HttpStatus.OK_200)
			return getFunction.apply(response);
		E exception = null;
		try {
			exception = response.getEntity(exceptionClass);
		} catch (Exception ex) {
			throw new ParseAnswerException(response);
		}
		throw exception;
	}
}
