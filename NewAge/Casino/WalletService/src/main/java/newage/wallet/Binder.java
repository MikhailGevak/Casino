package newage.wallet;

import com.google.inject.Module;

import newage.wallet.api.WalletService;
import newage.wallet.rest.WalletDAO;
import newage.wallet.rest.WalletServiceImpl;

public class Binder implements Module{

	@Override
	public void configure(com.google.inject.Binder binder) {
		binder.bind(WalletService.class).to(WalletServiceImpl.class);
		binder.bind(WalletDAO.class);
	}

}
