package newage.game.api.exception;

import org.eclipse.jetty.http.HttpStatus;

public class PlayerNotFoundException extends BetServiceException {

	protected PlayerNotFoundException(String message, Integer errorCode) {
		super(message, errorCode);
	}

	public PlayerNotFoundException(Integer playerId) {
		super("Player not found (playerId:" + playerId + ")");
	}

	@Override
	public int getHttpStatus() {
		return HttpStatus.PRECONDITION_FAILED_412;
	}

	@Override
	public int getErrorUniqueCode() {
		return 220;
	}

	private static final long serialVersionUID = 1L;

}
