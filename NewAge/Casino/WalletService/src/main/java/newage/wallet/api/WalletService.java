package newage.wallet.api;

import java.math.BigDecimal;

import newage.wallet.api.exception.AlreadyRegisteredPlayerException;
import newage.wallet.api.exception.InsufficientFundsException;
import newage.wallet.api.exception.PlayerNotFoundException;
import newage.wallet.api.exception.WalletException;

public interface WalletService {
	Balance registerPlayer(Integer playerId) throws AlreadyRegisteredPlayerException, WalletException;
	Balance depositBalance(Integer playerId, BigDecimal amount) throws PlayerNotFoundException, WalletException;
	Balance withdrawBalance(Integer playerId, BigDecimal amount) throws PlayerNotFoundException, InsufficientFundsException, WalletException;
	Balance getBalance(Integer playerId) throws PlayerNotFoundException, WalletException;
	Boolean removeBalance(Integer playerId) throws PlayerNotFoundException, WalletException;	
}
