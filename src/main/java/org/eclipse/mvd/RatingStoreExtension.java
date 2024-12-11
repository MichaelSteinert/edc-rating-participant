package org.eclipse.mvd;

import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.edc.web.spi.WebService;

public class RatingStoreExtension implements ServiceExtension {

  @Inject
  WebService webService;

  @Override
  public String name() {
    return "Maintain rating store.";
  }

  @Override
  public void initialize(ServiceExtensionContext context) {
    webService.registerResource(new RatingStoreApiController(context.getMonitor()));
  }
}