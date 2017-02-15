package newage.wallet.api.exception;

import org.eclipse.jetty.http.HttpStatus;

public class PlayerNotFoundException extends WalletException {

	public PlayerNotFoundException(Integer playerId) {
		super("Player not found (playerId:" + playerId + ")");
	}

	@Override
	public int getHttpStatus() {
		return HttpStatus.PRECONDITION_FAILED_412;
	}
	
	@Override
	public int getErrorUniqueCode(){
		return 140;
	}
	
	private static final long serialVersionUID = 1L;

}
