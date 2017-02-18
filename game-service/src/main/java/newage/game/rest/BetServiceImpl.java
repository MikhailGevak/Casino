package newage.game.rest;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import newage.common.exception.ParseAnswerException;
import newage.common.rest.AbstractService;
import newage.game.api.Bet;
import newage.game.api.BetService;
import newage.game.api.exception.BetServiceException;
import newage.game.api.exception.GamePlayerNotFoundException;
import newage.wallet.api.Balance;
import newage.wallet.api.WalletService;

@Path("/bet")
@Singleton
public class BetServiceImpl extends AbstractService<BetServiceException> implements BetService {
	private final BetDAO dao;
	WalletService walletServiceClient;// It's a special implementation of
								// WalletService which has the api and works
								// with Wallet's microservice using web client

	@Inject
	public BetServiceImpl(BetDAO dao, WalletService walletServiceClient) throws SQLException {
		this.dao = dao;
		this.walletServiceClient = walletServiceClient;
		dao.initialPrepareData();
	}

	@POST
	@Path("create/{player_id}/{game_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public synchronized  Balance placeBet(@PathParam("player_id") Integer playerId, @PathParam("game_id") Integer gameId,
			BigDecimal amount) throws BetServiceException {

		return exceptionHandle(() -> {
			// It have be more effective mechanism to "rallback transaction"...
			// But for demo it's ok, I think
			Balance balance = null;

			try {
				balance = walletServiceClient.withdrawBalance(playerId, amount);
			} catch (Exception ex) {
				throw new BetServiceException(ex);
			}

			try {
				BetDBImpl bet = new BetDBImpl(playerId, gameId, amount);
				dao.create(bet);
			} catch (Exception e) {
				walletServiceClient.depositBalance(playerId, amount);
				throw new BetServiceException(e);
			}
			return balance;
		});
	}

	@GET
	@Path("get/{player_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<Bet> getBets(@PathParam("player_id") Integer playerId)
			throws BetServiceException {
		return exceptionHandle(() -> {
			List<Bet> bets = Collections.unmodifiableList(dao.queryForEq(BetDBImpl.PLAYER_ID_COLUMN_NAME, playerId));
			if (bets.isEmpty())
				throw new GamePlayerNotFoundException(playerId);
			return bets;
		});
	}

	@POST
	@Path("remove/{player_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public synchronized Integer removeAllBets(@PathParam("player_id") Integer playerId)
			throws BetServiceException, ParseAnswerException {
		return exceptionHandle(() -> {
			List<Bet> bets = getBets(playerId);
			int deleted = 0;
			for (Bet bet : bets) {
				deleted += dao.deleteById(bet.getId());
			}
			return deleted;
		});

	}

	@Override
	protected BetServiceException createServiceException(Exception ex) {
		if (ex instanceof BetServiceException)
			return (BetServiceException) ex;
		return new BetServiceException(ex);
	}

}
