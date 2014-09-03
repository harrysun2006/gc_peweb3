package com.gc.log4j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import flex.messaging.log.AbstractTarget;
import flex.messaging.log.LogEvent;

public class BlazeDSLogger extends AbstractTarget {

  public void logEvent(LogEvent event) {
    Log log = LogFactory.getLog(event.logger.getCategory());

    if (event.level >= LogEvent.ERROR)
        log.error(event.message, event.throwable);
    else if (event.level >= LogEvent.WARN)
        log.warn(event.message, event.throwable);
    else if (event.level >= LogEvent.INFO)
         log.info(event.message, event.throwable);
    else if (event.level >= LogEvent.DEBUG)
         log.debug(event.message, event.throwable);
    else
         log.trace(event.message, event.throwable);
}

}
