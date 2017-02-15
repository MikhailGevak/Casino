package newage.wallet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import com.google.inject.Module;

import newage.common.server.RestServer;
import newage.common.server.bind.DatabaseModule;

public class Server {
	public static void main(String[] args) throws Exception {
		RestServer.builder().addModules(getModules()).runServer(new Properties());
	}

	protected static Collection<Module> getModules() {
		return Arrays.asList(new Binder(), new DatabaseModule());
	}
}
