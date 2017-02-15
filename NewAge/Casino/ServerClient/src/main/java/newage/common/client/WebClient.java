package newage.common.client;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import newage.common.server.providers.GsonJerseyProvider;

public class WebClient {
	private WebResource resource;
	
	public WebClient(String host, Set<Class<?>> providers){
		Set<Class<?>> allProviders = new HashSet<>(providers);
		allProviders.add(GsonJerseyProvider.class);
		resource = Client.create(new DefaultClientConfig(allProviders)).resource(host);
	}
	
	public WebClient(String host){
		this(host, new HashSet<>());
	}
	
	public <T> T doGetRequest(String path, Class<T> clazz){
		return resource.path(path).accept(MediaType.APPLICATION_JSON).get(clazz);
	}
	
	public <T> T doPostRequest(String path, Class<T> clazz, Object requestEntity){
		return resource.path(path).accept(MediaType.APPLICATION_JSON).post(clazz, requestEntity);
	}
}
		