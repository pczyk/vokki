package de.mupitu.vokki.presentation;

import de.mupitu.vokki.business.statistics.boundary.ExamActionManager;
import de.mupitu.vokki.business.statistics.boundary.LoginActionManager;
import de.mupitu.vokki.business.statistics.entity.ExamAction;
import de.mupitu.vokki.business.statistics.entity.LoginAction;
import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.presentation.session.UserSession;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class Statistics extends BaseController {

    @Inject
    private LoginActionManager loginActionManager;
    
    @Inject
    private ExamActionManager examActionManager;
    
    @Inject
    private UserSession userSession;
    
    public List<LoginAction> getLoginActions() {
        final User user = userSession.getCurrentUser();
        
        return loginActionManager.getLoginActionsForUser(user);
    }
    
    public List<ExamAction> getExamActions() {
        final User user = userSession.getCurrentUser();
        
        return examActionManager.getLoginActionsForUser(user);
    }
}
