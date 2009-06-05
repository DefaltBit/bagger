
package gov.loc.repository.bagger.ui.handlers;

import gov.loc.repository.bagger.Contact;
import gov.loc.repository.bagger.Organization;
import gov.loc.repository.bagger.Profile;
import gov.loc.repository.bagger.Project;
import gov.loc.repository.bagger.bag.BaggerOrganization;
import gov.loc.repository.bagger.bag.impl.DefaultBag;
import gov.loc.repository.bagger.bag.impl.DefaultBagInfo;
import gov.loc.repository.bagger.ui.BagView;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.richclient.application.Application;
import org.springframework.richclient.application.ApplicationPage;
import org.springframework.richclient.application.PageComponent;

public class ProjectListHandler extends AbstractAction {
	private static final Log log = LogFactory.getLog(ProjectListHandler.class);
   	private static final long serialVersionUID = 1L;
	BagView bagView;
	DefaultBag bag;

	public ProjectListHandler(BagView bagView) {
		super();
		this.bagView = bagView;
	}

	public void actionPerformed(ActionEvent e) {
		this.bag = bagView.getBag();

    	JComboBox jlist = (JComboBox)e.getSource();
    	String selected = (String) jlist.getSelectedItem();
    	log.info("BagView.projectList valueChanged: " + selected);
    	if (selected != null && !selected.isEmpty() && selected.equalsIgnoreCase(bagView.getPropertyMessage("bag.project.edeposit"))) {
    		bag.setIsEdeposit(true);
      		bag.setIsNoProject(false);
    	} else {
    		bag.setIsEdeposit(false);
    	}
    	if (selected != null && !selected.isEmpty() && selected.equalsIgnoreCase(bagView.getPropertyMessage("bag.project.ndnp"))) {
    		bag.setIsNdnp(true);
      		bag.setIsNoProject(false);
    	}
    	if (selected == null || selected.equalsIgnoreCase(bagView.getPropertyMessage("bag.project.noproject"))) {
      		bag.setIsNoProject(true);
    	} else {
    		bag.getInfo().setLcProject(selected);
    		bag.setIsNdnp(false);
    	}
    	bagView.setBag(bag);
        bagView.bagInfoInputPane.verifyForms(bag);
    	bagView.updateBaggerRules();
    	changeProject(selected);
	}

    private void changeProject(String selected) {
        bagView.updateProfile();

    	Object[] project_array = bagView.userProjects.toArray();
        for (int i=0; i < bagView.userProjects.size(); i++) {
        	Project project = (Project)project_array[i];
        	if (selected != null && !selected.isEmpty() && selected.equalsIgnoreCase(project.getName())) {
        		log.info("bagProject: " + project.getId());
        		bag.setProject(project);
        		Object[] profiles = bagView.userProfiles.toArray();
        		for (int j=0; j < profiles.length; j++) {
        			Profile profile = (Profile) profiles[j];
        			if (profile.getProjectId() == project.getId()) {
               			// TODO: user is org contact
        				Contact person = profile.getPerson();
        				if (person == null) person = new Contact();
                   		Organization org = person.getOrganization();
                   		if (org == null) org = new Organization();
                   		DefaultBagInfo bagInfo = bag.getInfo();
                   		BaggerOrganization bagOrg = bagInfo.getBagOrganization();
                   		Contact contact = profile.getContact();
                   		if (contact == null) {
                   			contact = new Contact();
                   		}
                   		bagOrg.setContact(contact);
                   		bagOrg.setSourceOrganization(org.getName());
                   		bagOrg.setOrganizationAddress(org.getAddress());
                   		bagInfo.setBagOrganization(bagOrg);
                   		bag.setInfo(bagInfo);
                   		bagView.setBag(bag);
                   		bagView.projectContact = profile.getPerson();
                		bag.getInfo().setBag(bag);
                		//bag.getInfo().updateExistingFieldList(enabled);
                   		bagView.bagInfoInputPane.populateForms(bag, true);
                   		bagView.bagInfoInputPane.updateSelected(bag);
                   	}
        		}
        	}
        }
    }
}