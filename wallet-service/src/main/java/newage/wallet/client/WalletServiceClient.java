package newage.wallet.client;

import java.math.BigDecimal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.client.ClientResponse;

import newage.common.api.properties.PropertyService;
import newage.common.client.AbstractServiceClient;
import newage.common.client.WebClient;
import newage.common.exception.ParseAnswerException;
import newage.wallet.api.Balance;
import newage.wallet.api.BalanceImpl;
import newage.wallet.api.WalletService;
import newage.wallet.api.exception.AlreadyRegisteredPlayerException;
import newage.wallet.api.exception.InsufficientFundsException;
import newage.wallet.api.exception.WalletPlayerNotFoundException;
import newage.wallet.api.exception.WalletException;

@Singleton
public class WalletServiceClient extends AbstractServiceClient implements WalletService {
	private static String WALLET_HOST_PROPERTY = "wallet.rest.server";
	private final WebClient webClient;

	@Inject
	public WalletServiceClient(PropertyService propertyService) {
		this.webClient = new WebClient(propertyService.getPropertyValue(WALLET_HOST_PROPERTY));
	}

	public WalletServiceClient(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public Balance registerPlayer(Integer playerId) throws AlreadyRegisteredPlayerException, WalletException, ParseAnswerException {
		return handleResponse(BalanceImpl.class, WalletException.class, () -> {
			return webClient.doPostRequest("wallet/register/" + playerId, ClientResponse.class, null);
		});
	}

	@Override
	public Balance depositBalance(Integer playerId, BigDecimal amount) throws WalletPlayerNotFoundException, WalletException, ParseAnswerException {
		return handleResponse(BalanceImpl.class, WalletException.class, () -> {
			return webClient.doPostRequest("wallet/deposit/" + playerId, ClientResponse.class, amount);
		});
	}

	@Override
	public Balance withdrawBalance(Integer playerId, BigDecimal amount)
			throws WalletPlayerNotFoundException, InsufficientFundsException, WalletException, ParseAnswerException {
		return handleResponse(BalanceImpl.class, WalletException.class, () -> {
			return webClient.doPostRequest("wallet/withdraw/" + playerId, ClientResponse.class, amount);
		});
	}

	@Override
	public Balance getBalance(Integer playerId) throws WalletPlayerNotFoundException, WalletException, ParseAnswerException {
		return handleResponse(BalanceImpl.class, WalletException.class, () -> {
			return webClient.doGetRequest("wallet/balance/" + playerId, ClientResponse.class);
		});
	}

	@Override
	public Boolean removeBalance(Integer playerId) throws WalletPlayerNotFoundException, WalletException, ParseAnswerException {
		return handleResponse(Boolean.class, WalletException.class, () -> {
			return webClient.doPostRequest("wallet/remove/" + playerId, ClientResponse.class, null);
		});
	}
}
