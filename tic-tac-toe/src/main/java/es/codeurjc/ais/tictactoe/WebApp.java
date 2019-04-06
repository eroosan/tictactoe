package es.codeurjc.ais.tictactoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@SpringBootApplication
@EnableWebSocket
public class WebApp implements WebSocketConfigurer {

	private static ConfigurableApplicationContext app;
	
	public static void main(String[] args) {
		SpringApplication.run(WebApp.class, args);
	}

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propsConfig
          = new PropertySourcesPlaceholderConfigurer();
        propsConfig.setLocation(new ClassPathResource("git.properties"));
        propsConfig.setIgnoreResourceNotFound(true);
        propsConfig.setIgnoreUnresolvablePlaceholders(true);
        return propsConfig;
    }

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new TicTacToeHandler(), "/tictactoe");
	}
	    
    public static void start() {
    	start(new String[] {});
    }

	private static void start(String[] args) {
		if(app == null) {
    		app = SpringApplication.run(WebApp.class, args);
    	} 
	}    
	
	public static void stop() {
		if(app != null) {
			app.close();
		}
	}
}
