package newage.common.server.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import newage.common.exception.ServiceException;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException>{

	@Override
	public Response toResponse(ServiceException exception) {
		return Response.status(exception.getHttpStatus()).entity(exception).build();
	}
}
