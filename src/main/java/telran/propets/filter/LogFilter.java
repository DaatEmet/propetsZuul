package telran.propets.filter;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import telran.propets.dto.LogDto;

public class LogFilter extends ZuulFilter {

  private static Logger log = LoggerFactory.getLogger(LogFilter.class);
  
  @Autowired
  KafkaTemplate<String, LogDto> kafka;
  
  @Value("${cloudkarafka.topicLogger}")
  String topicLogger;

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 40;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest(); 
    //System.out.println(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
	log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
	LogDto log = new LogDto(request.getMethod(), request.getRequestURL().toString(), LocalDateTime.now());
	kafka.send(topicLogger, log);
	return null;
  }
}