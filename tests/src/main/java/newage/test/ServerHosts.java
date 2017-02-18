package newage.test;

public class ServerHosts {
	public static String WALLET_HOST = System.getProperty("wallet.rest.server", "http://localhost:9999");
	public static String BET_HOST = System.getProperty("game.rest.server", "http://localhost:9998");
}
