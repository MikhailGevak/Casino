package newage.tests;

import org.junit.Test;

import newage.common.client.WebClient;
import newage.wallet.api.Balance;
import newage.wallet.api.WalletService;
import newage.wallet.api.exception.AlreadyRegisteredPlayerException;
import newage.wallet.api.exception.PlayerNotFoundException;
import newage.wallet.api.exception.WalletException;
import newage.wallet.client.WalletServiceClient;

public class WalletServiceClientTest {
	private WebClient webClient = new WebClient("http://localhost:9999/wallet");
	private WalletService walletService = new WalletServiceClient(webClient);

	@Test
	public void registerPlayerTest() throws AlreadyRegisteredPlayerException, WalletException {
		Balance balance = walletService.registerPlayer(12345);
	}

	@Test
	public void getBalancePlayerTest() throws PlayerNotFoundException, WalletException {
		Balance balance = walletService.getBalance(12345);
		System.out.println(balance.getAmount());
	}
}
