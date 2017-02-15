package newage.wallet.api.exception;

import org.eclipse.jetty.http.HttpStatus;

public class InsufficientFundsException extends WalletException {
	public InsufficientFundsException(Integer playerId) {
		super("Insufficient funds for operation (playerId:" + playerId + ")");
	}
	
	@Override
	public int getHttpStatus() {
		return HttpStatus.PRECONDITION_FAILED_412;
	}
	
	@Override
	public int getErrorUniqueCode(){
		return 150;
	}
	
	private static final long serialVersionUID = 1L;

}
