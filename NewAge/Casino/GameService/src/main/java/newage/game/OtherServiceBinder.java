package newage.game;

import com.google.inject.Module;

import newage.wallet.api.WalletService;
import newage.wallet.client.WalletServiceClient;

public class OtherServiceBinder implements Module {

	@Override
	public void configure(com.google.inject.Binder binder) {
		binder.bind(WalletService.class).to(WalletServiceClient.class);
	}
}