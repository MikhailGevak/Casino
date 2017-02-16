package newage.wallet.api;

import java.math.BigDecimal;

import newage.common.exception.ParseAnswerException;
import newage.wallet.api.exception.AlreadyRegisteredPlayerException;
import newage.wallet.api.exception.InsufficientFundsException;
import newage.wallet.api.exception.PlayerNotFoundException;
import newage.wallet.api.exception.WalletException;

public interface WalletService {
	Balance registerPlayer(Integer playerId) throws AlreadyRegisteredPlayerException, WalletException, ParseAnswerException;
	Balance depositBalance(Integer playerId, BigDecimal amount) throws PlayerNotFoundException, WalletException, ParseAnswerException;
	Balance withdrawBalance(Integer playerId, BigDecimal amount) throws PlayerNotFoundException, InsufficientFundsException, WalletException, ParseAnswerException;
	Balance getBalance(Integer playerId) throws PlayerNotFoundException, WalletException, ParseAnswerException;
	Boolean removeBalance(Integer playerId) throws PlayerNotFoundException, WalletException, ParseAnswerException;	
}
