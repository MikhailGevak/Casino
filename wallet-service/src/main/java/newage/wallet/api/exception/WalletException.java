package newage.wallet.api.exception;


import newage.common.exception.ServiceException;

public class WalletException extends ServiceException {
	
	protected WalletException(String message, Integer errorCode) {
		super(message, errorCode);
	}
	
	public WalletException(ServiceException exception) {
		super(exception);
	}
	
	public WalletException(String message) {
		super(message);
	}

	public WalletException(Throwable th) {
		super(th);
	}

	@Override
	public int getErrorUniqueCode(){
		return 110;
	}
	
	private static final long serialVersionUID = 1L;
}
