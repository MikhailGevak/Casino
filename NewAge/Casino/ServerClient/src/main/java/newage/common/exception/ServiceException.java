package newage.common.exception;

import org.eclipse.jetty.http.HttpStatus;

public abstract class ServiceException extends Exception {
	
	public ServiceException(ServiceException exception){
		super(exception);
	}
	
	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable th) {
		super(th);
	}
	
	public abstract int getErrorUniqueCode();

	//You can overwrite it! It's inly default http-status
	public int getHttpStatus() {
		return HttpStatus.INTERNAL_SERVER_ERROR_500;
	}

	private static final long serialVersionUID = 1L;

}
