package pg.eti.jee.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pg.eti.jee.datastore.component.DataStore;
import pg.eti.jee.serialization.component.CloningUtility;

@WebListener
public class CreateDataSource implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("datasource", new DataStore(new CloningUtility()));
    }

}
