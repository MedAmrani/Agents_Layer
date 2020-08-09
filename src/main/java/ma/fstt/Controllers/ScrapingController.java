package ma.fstt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jade.wrapper.StaleProxyException;
import ma.fstt.Services.JadeService;
import ma.fstt.models.ScrapRequest;


@RestController
@RequestMapping(path = "/scraping")
public class ScrapingController {
	@Autowired
	private JadeService jade;
	
    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> scrape(@RequestBody ScrapRequest request) {
		try {
			jade.getContainerController().createNewAgent("scraping-"+request.getText_query(), "ma.fstt.agents.ScrapingAgent", new String[] {request.getSince_date(),request.getUntil_date(),request.getText_query(),request.getCount(),}).start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("cant start new ScrapingAgent!");
		}
		return ResponseEntity.ok("Job Started!");
	}
	
	
	

}
