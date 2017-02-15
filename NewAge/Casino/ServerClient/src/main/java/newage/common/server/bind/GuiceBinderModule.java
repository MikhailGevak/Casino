package newage.common.server.bind;

import java.util.Properties;

import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import newage.common.server.ServletParameters;

public class GuiceBinderModule extends JerseyServletModule {
	final private ServletParameters servletParameters;

	public GuiceBinderModule(Properties properties){
		this.servletParameters = new ServletParameters(properties);
	}

	@Override
	protected final void configureServlets() {
		serve("/*").with(GuiceContainer.class, servletParameters.getParameters());
	}
}
