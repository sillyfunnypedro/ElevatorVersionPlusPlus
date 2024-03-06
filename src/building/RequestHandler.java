package building;

import scanerzus.Request;

/**
 * This functional interface is used to handle the request.
 */
@FunctionalInterface
public interface RequestHandler {
  void handleRequest(Request request);
}
