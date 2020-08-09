package ma.fstt.agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import ma.fstt.Services.ScrapingService;
import ma.fstt.models.ScrapRequest;


public class ScrapingAgent extends Agent{
	private static final long serialVersionUID = 1L;
	
	
	private ScrapingService scrapingService;
	
	
	
	@Override
	protected void setup() {
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			ScrapRequest scraprequest = (ScrapRequest) args[0];
			
			addBehaviour(new Behaviour() {
				
				@Override
				public boolean done() {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public void action() {
					scrapingService = new ScrapingService(scraprequest);
					String text = scrapingService.getPostsPlainJSON();
					
					System.out.println(text);
					
					
					
					
				}
			});
		}
	}
	
	@Override
	protected void takeDown() {
		
	}
}

