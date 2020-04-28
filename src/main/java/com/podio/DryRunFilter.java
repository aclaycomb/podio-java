package com.podio;


import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;

/**
 * Used to put the API in testmode
 */
public class DryRunFilter implements ClientRequestFilter {

	@Override
	public void filter(ClientRequestContext cr) throws IOException {
		if (!cr.getUri().getPath().startsWith("/oauth")) {
			cr.getHeaders().putSingle("X-Podio-Dry-Run", "steYut44");
		}
	}
}
