package newage.common.exception;

public class ErrorResponse {
	public static final String ERROR_CODE = "errorCode";
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String ERROR_CLASS = "errorClass";
	
	public final Integer errorCode;
	public final String errorMessage;
	public final Integer responseCode;
	public final String errorClassName;
	
	public ErrorResponse(Integer responseCode, Integer errorCode, String errorMessage, Class<? extends ServiceException> exceptionClass){
		this.responseCode = responseCode;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorClassName = exceptionClass.getName();
	}
}
