package newage.game.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import org.eclipse.jetty.http.HttpStatus;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.client.ClientResponse;

import newage.common.client.WebClient;
import newage.game.api.Bet;
import newage.game.api.BetService;
import newage.game.api.exception.BetServiceException;
import newage.game.api.exception.PlayerNotFoundException;

@Singleton
public class BetServiceClient implements BetService {
	private final WebClient webClient;

	@Inject
	public BetServiceClient(WebClient webClient) {
		this.webClient = webClient;
	}
	
	//bet/{player_id}/{game_id}
	@Override
	public Bet placeBet(Integer playerId, Integer gameId, BigDecimal amount) throws BetServiceException {
		return handleResponse(Bet.class, () -> {
			return webClient.doPostRequest("bet/" + playerId + "/" + gameId + "/" + amount, ClientResponse.class, amount);
		});
	}

	//bets/{player_id}
	@SuppressWarnings("unchecked")
	@Override
	public List<? extends Bet> getBets(Integer playerId) throws PlayerNotFoundException, BetServiceException {
		return handleResponse(List.class, () -> {
			return webClient.doGetRequest("bets/" + playerId, ClientResponse.class);
		});
	}


	private <T> T handleResponse(Class<T> resultClass, Supplier<ClientResponse> function) throws BetServiceException {
		ClientResponse response = function.get();

		if (response.getStatus() == HttpStatus.OK_200)
			return response.getEntity(resultClass);
		throw response.getEntity(BetServiceException.class);
	}
}
