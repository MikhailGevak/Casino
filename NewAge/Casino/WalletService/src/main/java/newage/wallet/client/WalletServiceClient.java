package newage.wallet.client;

import java.math.BigDecimal;
import java.util.function.Supplier;

import org.eclipse.jetty.http.HttpStatus;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.client.ClientResponse;

import newage.common.client.WebClient;
import newage.wallet.api.Balance;
import newage.wallet.api.WalletService;
import newage.wallet.api.exception.AlreadyRegisteredPlayerException;
import newage.wallet.api.exception.InsufficientFundsException;
import newage.wallet.api.exception.PlayerNotFoundException;
import newage.wallet.api.exception.WalletException;

@Singleton
public class WalletServiceClient implements WalletService {
	private final WebClient webClient;

	@Inject
	public WalletServiceClient(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public Balance registerPlayer(Integer playerId) throws AlreadyRegisteredPlayerException, WalletException {
		return handleResponse(Balance.class, () -> {
			return webClient.doPostRequest("register/" + playerId, ClientResponse.class, null);
		});
	}

	@Override
	public Balance depositBalance(Integer playerId, BigDecimal amount) throws PlayerNotFoundException, WalletException {
		return handleResponse(Balance.class, () -> {
			return webClient.doPostRequest("deposit/" + playerId, ClientResponse.class, amount);
		});
	}

	@Override
	public Balance withdrawBalance(Integer playerId, BigDecimal amount)
			throws PlayerNotFoundException, InsufficientFundsException, WalletException {
		return handleResponse(Balance.class, () -> {
			return webClient.doPostRequest("withdraw/" + playerId, ClientResponse.class, amount);
		});
	}

	@Override
	public Balance getBalance(Integer playerId) throws PlayerNotFoundException, WalletException {
		return handleResponse(Balance.class, () -> {
			return webClient.doGetRequest("balance/" + playerId, ClientResponse.class);
		});
	}

	@Override
	public Boolean removeBalance(Integer playerId) throws PlayerNotFoundException, WalletException {
		return handleResponse(Boolean.class, () -> {
			return webClient.doPostRequest("balance/" + playerId, ClientResponse.class, null);
		});
	}

	private <T> T handleResponse(Class<T> resultClass, Supplier<ClientResponse> function) throws WalletException {
		ClientResponse response = function.get();

		if (response.getStatus() == HttpStatus.OK_200)
			return response.getEntity(resultClass);
		throw response.getEntity(WalletException.class);
	}

}
