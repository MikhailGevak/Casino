package newage.wallet.rest;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import newage.common.rest.AbstractService;

import newage.wallet.api.Balance;
import newage.wallet.api.WalletService;
import newage.wallet.api.exception.AlreadyRegisteredPlayerException;
import newage.wallet.api.exception.IncorrectAmountValue;
import newage.wallet.api.exception.InsufficientFundsException;
import newage.wallet.api.exception.PlayerNotFoundException;
import newage.wallet.api.exception.WalletException;

@Path("/wallet")
@Singleton
public class WalletServiceImpl extends AbstractService<WalletException> implements WalletService {
	private final WalletDAO dao;

	@Inject
	public WalletServiceImpl(WalletDAO dao) throws SQLException {
		this.dao = dao;
		dao.initialPrepareData();
	}

	@POST
	@Path("register/{player_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	synchronized public Balance registerPlayer(@PathParam("player_id") Integer playerId) throws WalletException {
		return exceptionHandle(() -> {
			checkIfNotExist(playerId);
			BalanceDBImpl balance = new BalanceDBImpl(playerId, BigDecimal.valueOf(0));
			dao.create(balance);

			return balance;
		});
	}

	@POST
	@Path("deposit/{player_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	synchronized public Balance depositBalance(@PathParam("player_id") Integer playerId, BigDecimal amount)
			throws PlayerNotFoundException, IncorrectAmountValue, WalletException {
		return exceptionHandle(() -> {
			checkAmount(amount);
			Balance balance = getBalance(playerId);
			BalanceDBImpl newBalance = new BalanceDBImpl(playerId, balance.getAmount().add(amount));
			checkIfNotInsufficientFunds(newBalance);
			dao.update(newBalance);

			return newBalance;
		});
	}

	@POST
	@Path("withdraw/{player_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	synchronized public Balance withdrawBalance(@PathParam("player_id") Integer playerId, BigDecimal amount)
			throws PlayerNotFoundException, InsufficientFundsException, IncorrectAmountValue, WalletException {
		return exceptionHandle(() -> {
			checkAmount(amount);
			Balance balance = getBalance(playerId);
			BalanceDBImpl newBalance = new BalanceDBImpl(playerId, balance.getAmount().subtract(amount));
			checkIfNotInsufficientFunds(newBalance);
			dao.update(newBalance);

			return newBalance;
		});
	}

	@GET
	@Path("balance/{player_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Balance getBalance(@PathParam("player_id") Integer playerId)
			throws PlayerNotFoundException, WalletException {
		return exceptionHandle(() -> {
			checkIfExist(playerId);
			return dao.queryForId(playerId);
		});
	}

	@POST
	@Path("remove/{player_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Boolean removeBalance(@PathParam("player_id") Integer playerId)
			throws PlayerNotFoundException, WalletException {
		return exceptionHandle(() -> {
			checkIfExist(playerId);
			return (dao.deleteById(playerId) > 0);
		});
	}

	private void checkIfExist(Integer playerId) throws PlayerNotFoundException, SQLException {
		if (!dao.idExists(playerId))
			throw new PlayerNotFoundException(playerId);
	}

	private void checkIfNotExist(Integer playerId) throws AlreadyRegisteredPlayerException, SQLException {
		if (dao.idExists(playerId))
			throw new AlreadyRegisteredPlayerException(playerId);
	}

	private void checkIfNotInsufficientFunds(Balance balance) throws InsufficientFundsException {
		if (balance.getAmount().compareTo(BigDecimal.valueOf(0)) < 0)
			throw new InsufficientFundsException(balance.getPlayerId());
	}

	private void checkAmount(BigDecimal amount) throws IncorrectAmountValue {
		if (amount.compareTo(BigDecimal.valueOf(0)) <= 0)
			throw new IncorrectAmountValue("Amount must be greater than zero (" + amount + ")");
	}

	private static @FunctionalInterface interface ServiceFunction<T> {
		T doFunction() throws WalletException, SQLException;
	}

	@Override
	protected WalletException createServiceException(Exception ex){
		if (ex instanceof WalletException) return (WalletException)ex;
		
		return new WalletException(ex);
	}
}
