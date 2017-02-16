package newage.common.exception;

import org.eclipse.jetty.http.HttpStatus;

public abstract class ServiceException extends Exception {
	protected final int errorCode;
	
	protected ServiceException(String message, Integer errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public ServiceException(ServiceException exception){
		super(exception.getMessage(), exception);
		this.errorCode = exception.errorCode;
	}
	
	public ServiceException(String message) {
		super(message);
		errorCode = getErrorUniqueCode();
	}

	public ServiceException(Throwable th) {
		super(th);
		errorCode = getErrorUniqueCode();
	}
	
	public int getErrorCode(){
		return errorCode;
	}
	
	protected abstract int getErrorUniqueCode();

	//You can overwrite it! It's inly default http-status
	public int getHttpStatus() {
		return HttpStatus.INTERNAL_SERVER_ERROR_500;
	}

	private static final long serialVersionUID = 1L;

}
