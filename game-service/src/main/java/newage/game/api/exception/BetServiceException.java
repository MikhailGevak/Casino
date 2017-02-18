package newage.game.api.exception;

import newage.common.exception.ServiceException;

public class BetServiceException extends ServiceException {

	protected BetServiceException(String message, Integer errorCode) {
		super(message, errorCode);
	}
	
	public BetServiceException(ServiceException exception) {
		super(exception);
	}

	public BetServiceException(String message) {
		super(message);
	}

	public BetServiceException(Throwable th) {
		super(th);
	}

	@Override
	public int getErrorUniqueCode() {
		return 210;
	}

	private static final long serialVersionUID = 1L;
}
