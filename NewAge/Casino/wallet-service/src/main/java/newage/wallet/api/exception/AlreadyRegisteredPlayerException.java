package newage.wallet.api.exception;

import org.eclipse.jetty.http.HttpStatus;

public class AlreadyRegisteredPlayerException extends WalletException {

	protected AlreadyRegisteredPlayerException(String message, Integer errorCode) {
		super(message, errorCode);
	}
	
	protected AlreadyRegisteredPlayerException(String message){
		super(message);
	}
	
	public AlreadyRegisteredPlayerException(Integer playerId) {
		super("Player already registered (playerId:" + playerId + ")");
	}

	@Override
	public int getHttpStatus() {
		return HttpStatus.PRECONDITION_FAILED_412;
	}
	

	@Override
	public int getErrorUniqueCode(){
		return 120;
	}
	
	private static final long serialVersionUID = 1L;
}
