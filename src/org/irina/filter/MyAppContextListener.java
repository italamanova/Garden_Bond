package org.irina.filter;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;

import org.irina.beans.Item;
import org.irina.beans.SessionBean;
import org.irina.dao.LotDAO;


@WebListener
public class MyAppContextListener implements ServletContextListener {
    //private ScheduledExecutorService scheduler;
	
    
    @Override
    public void contextInitialized(ServletContextEvent event) {

    	startSubscriber();
        //init sheduler
        /*scheduler = Executors.newSingleThreadScheduledExecutor();
        //!!!Shedule task
        Runnable tasks = new org.irina.util.Tasks();
        
        scheduler.scheduleAtFixedRate(tasks, 0, 1, TimeUnit.MINUTES);*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        //scheduler.shutdownNow();
    }
    public void startSubscriber(){
		List<Item> list = LotDAO.getAllLots();
		for(Item item:list){
			//System.out.println("URL:  " + item.getBroker_url());
			//System.out.println("Domain: " + item.getDomain());

			Subscriber subscriber = new Subscriber(item.getBroker_url(),item.getDomain());
	    	subscriber.runClient();
		}
		
	}
}