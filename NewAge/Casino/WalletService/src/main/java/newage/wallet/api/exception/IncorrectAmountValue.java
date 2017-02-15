package newage.wallet.api.exception;

import org.eclipse.jetty.http.HttpStatus;

public class IncorrectAmountValue extends WalletException {
	public IncorrectAmountValue(String message) {
		super(message);
	}
	
	@Override
	public int getHttpStatus() {
		return HttpStatus.PRECONDITION_FAILED_412;
	}
	
	@Override
	public int getErrorUniqueCode(){
		return 130;
	}
	
	private static final long serialVersionUID = 1L;
}
