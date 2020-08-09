package ma.fstt.Services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ma.fstt.models.ScrapRequest;


public class ScrapingService {
	 private final RestTemplate restTemplate;
	 private ScrapRequest scrapRequest;

	    public ScrapingService(ScrapRequest scrapRequest) {
	        this.restTemplate = new RestTemplate();
	        this.scrapRequest = scrapRequest;
	    }

	    

		public String getPostsPlainJSON() {
	        String url = "http://127.0.0.1:8080/api/scrapeTweets";
	        return this.restTemplate.postForObject(url, scrapRequest, String.class);
	    }

}
