package newage.game;

import com.google.inject.Module;

import newage.game.api.BetService;
import newage.game.rest.BetDAO;
import newage.game.rest.BetServiceImpl;

public class Binder implements Module{
	public void configure(com.google.inject.Binder binder) {
		binder.bind(BetService.class).to(BetServiceImpl.class);
		binder.bind(BetDAO.class);
	}
}
