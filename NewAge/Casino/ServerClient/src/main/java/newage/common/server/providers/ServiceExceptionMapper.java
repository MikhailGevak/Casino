package newage.common.server.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import newage.common.exception.ErrorResponse;
import newage.common.exception.ServiceException;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException>{

	@Override
	public Response toResponse(ServiceException exception) {
		ErrorResponse error = toErrorResponse(exception);
		return Response.status(error.responseCode).entity(error).build();
	}
	
	private ErrorResponse toErrorResponse(ServiceException exception){
		return new ErrorResponse(exception.getHttpStatus(), exception.getErrorUniqueCode(), exception.getMessage(), exception.getClass());
	}

}
