package newage.common.exception;

import com.sun.jersey.api.client.ClientResponse;

public class ParseAnswerException extends ServiceException {
	private static final long serialVersionUID = 1L;

	protected ParseAnswerException(String message, Integer errorCode) {
		super(message, errorCode);
	}

	public ParseAnswerException(ClientResponse response){
		super("Can't parse response: " + response.toString());
	}
	@Override
	protected int getErrorUniqueCode() {
		return 999;
	}
}
