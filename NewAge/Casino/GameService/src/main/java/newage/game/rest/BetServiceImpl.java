package newage.game.rest;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import newage.common.rest.AbstractService;
import newage.game.api.Bet;
import newage.game.api.BetService;
import newage.game.api.exception.BetServiceException;
import newage.wallet.api.WalletService;

@Singleton
public class BetServiceImpl extends AbstractService<BetServiceException> implements BetService {
	private final BetDAO dao;
	WalletService walletService;// It's a special implementation of
								// WalletService which has the api and works
								// with Wallet's microservice using web client

	@Inject
	public BetServiceImpl(BetDAO dao, WalletService walletService) throws SQLException {
		this.dao = dao;
		this.walletService = walletService;
		dao.initialPrepareData();
	}

	@POST
	@Path("bet/{player_id}/{game_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	synchronized public Bet placeBet(@PathParam("player_id") Integer playerId, @PathParam("game_id") Integer gameId,
			BigDecimal amount) throws BetServiceException {
		try {
			walletService.withdrawBalance(playerId, amount);
		} catch (Exception ex2) {
			throw new BetServiceException(ex2);
		}

		try {
			return exceptionHandle(() -> {
				BetImpl bet = new BetImpl(playerId, gameId, amount);
				dao.create(bet);
				return bet;
			});
		} catch (Exception ex) {
			// It have be more effective mechanism...
			// But for demo it's ok, I think
			try {
				walletService.depositBalance(playerId, amount);
			} catch (Exception ex2) {
				throw new BetServiceException(ex2);
			}

			throw new BetServiceException(ex);
		}
	}

	@GET
	@Path("bets/{player_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<? extends Bet> getBets(@PathParam("player_id") Integer playerId) throws BetServiceException {
		return exceptionHandle(() -> {
			return dao.queryForEq(BetImpl.PLAYER_ID_COLUMN_NAME, playerId);
		});
	}

	@Override
	protected BetServiceException createServiceException(Exception ex) {
		return new BetServiceException(ex);
	}
}
