package newage.game.client;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import newage.common.client.AbstractServiceClient;
import newage.common.client.WebClient;
import newage.common.exception.ParseAnswerException;
import newage.game.api.Bet;
import newage.game.api.BetImpl;
import newage.game.api.BetService;
import newage.game.api.exception.BetServiceException;
import newage.game.api.exception.PlayerNotFoundException;
import newage.wallet.api.Balance;
import newage.wallet.api.BalanceImpl;

@Singleton
public class BetServiceClient extends AbstractServiceClient implements BetService {
	private final WebClient webClient;

	@Inject
	public BetServiceClient(WebClient webClient) {
		this.webClient = webClient;
	}

	// bet/{player_id}/{game_id}
	@Override
	public Balance placeBet(Integer playerId, Integer gameId, BigDecimal amount)
			throws BetServiceException, ParseAnswerException {
		return handleResponse(BalanceImpl.class, BetServiceException.class, () -> {
			return webClient.doPostRequest("bet/" + playerId + "/" + gameId, ClientResponse.class, amount);
		});
	}

	// bets/{player_id}
	@Override
	public List<Bet> getBets(Integer playerId)
			throws PlayerNotFoundException, BetServiceException, ParseAnswerException {
		List<BetImpl> bets = handleResponse(new GenericType<List<BetImpl>>() {
		}, BetServiceException.class, () -> {
			return webClient.doGetRequest("bets/" + playerId, ClientResponse.class);
		});

		return Collections.unmodifiableList(bets);
	}

	// remove/{player_id}
	@Override
	public Integer removeAllBets(Integer playerId)
			throws PlayerNotFoundException, BetServiceException, ParseAnswerException {
		return handleResponse(Integer.class, BetServiceException.class, () -> {
			return webClient.doPostRequest("remove/" + playerId, ClientResponse.class, null);
		});
	}
}
