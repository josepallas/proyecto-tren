package es.udc.tfg.trainticketsapp.web.components;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import es.udc.tfg.trainticketsapp.model.userprofile.UserProfile.TypeUser;
import es.udc.tfg.trainticketsapp.web.pages.Index;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;
import es.udc.tfg.trainticketsapp.web.util.CookiesManager;
import es.udc.tfg.trainticketsapp.web.util.UserSession;

@Import(library = { "tapestry5/bootstrap/js/collapse.js",
		"tapestry5/bootstrap/js/dropdown.js" }, stylesheet = "context:css/site.css")
public class Layout {

	@Property
	@Parameter(required = true, defaultPrefix = "message")
	private String title;

	@Parameter(defaultPrefix = "literal")
	private Boolean showTitleInBody;

	@Property
	@SessionState(create = false)
	private UserSession userSession;

	@Inject
	private Cookies cookies;

	public boolean getShowTitleInBody() {

		if (showTitleInBody == null) {
			return true;
		} else {
			return showTitleInBody;
		}

	}

	public boolean getClient() {
		return (userSession.getTypeUser() == TypeUser.CLIENT);
	}

	public boolean getAdministrator() {
		return (userSession.getTypeUser() == TypeUser.ADMINISTRATOR);
	}

	public boolean getSalesman() {
		return (userSession.getTypeUser() == TypeUser.SALESMAN);
	}

	@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
	Object onActionFromLogout() {
		userSession = null;
		CookiesManager.removeCookies(cookies);
		return Index.class;
	}

}
