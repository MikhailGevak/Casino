package newage.wallet.api;

import java.math.BigDecimal;

import newage.common.exception.ParseAnswerException;
import newage.wallet.api.exception.AlreadyRegisteredPlayerException;
import newage.wallet.api.exception.InsufficientFundsException;
import newage.wallet.api.exception.WalletPlayerNotFoundException;
import newage.wallet.api.exception.WalletException;

public interface WalletService {
	Balance registerPlayer(Integer playerId) throws AlreadyRegisteredPlayerException, WalletException, ParseAnswerException;
	Balance depositBalance(Integer playerId, BigDecimal amount) throws WalletPlayerNotFoundException, WalletException, ParseAnswerException;
	Balance withdrawBalance(Integer playerId, BigDecimal amount) throws WalletPlayerNotFoundException, InsufficientFundsException, WalletException, ParseAnswerException;
	Balance getBalance(Integer playerId) throws WalletPlayerNotFoundException, WalletException, ParseAnswerException;
	Boolean removeBalance(Integer playerId) throws WalletPlayerNotFoundException, WalletException, ParseAnswerException;	
}
