/*
 * Ask for Jabber-ID and password, connect to saros-con, and list
 * roster entries.
 */
package de.fu_berlin.inf.dpp.netbeans;

import de.fu_berlin.inf.dpp.net.stun.IStunService;
import de.fu_berlin.inf.dpp.net.stun.internal.StunServiceImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import de.fu_berlin.inf.dpp.net.upnp.IUPnPAccess;
import de.fu_berlin.inf.dpp.net.upnp.IUPnPService;
import de.fu_berlin.inf.dpp.net.upnp.internal.UPnPAccessImpl;
import de.fu_berlin.inf.dpp.net.upnp.internal.UPnPServiceImpl;
import de.fu_berlin.inf.dpp.net.xmpp.XMPPConnectionService;
import java.util.Collection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPException;
import org.openide.util.Exceptions;

@ActionID(
        category = "Window",
        id = "de.fu_berlin.inf.dpp.netbeans.SarosCoreTestAction")
@ActionRegistration(
        iconBase = "de/fu_berlin/inf/dpp/netbeans/saros_misc.png",
        displayName = "#CTL_SarosCoreTestAction")
@ActionReference(path = "Menu/Saros", position = 3333)
@Messages("CTL_SarosCoreTestAction=Action1")
public final class SarosCoreTestAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = "Jabber-ID: ";
        String title = "State your Jabber-ID!";
        NotifyDescriptor.InputLine inputJabberID = new NotifyDescriptor.InputLine(msg, title);
        inputJabberID.setInputText("My Jabber-ID");
        Object resultJabberID = DialogDisplayer.getDefault().notify(inputJabberID);
        if(resultJabberID != NotifyDescriptor.OK_OPTION){
            return;
        }
        
        String userJabberID = inputJabberID.getInputText();
        
        msg = "Password: ";
        title = "State your password!";
        NotifyDescriptor.InputLine inputPassword = new NotifyDescriptor.InputLine(msg,title);
        inputPassword.setInputText("My Password");
        Object resultPassword = DialogDisplayer.getDefault().notify(inputPassword);
        if(resultPassword != NotifyDescriptor.OK_OPTION){
            return;
        }
        
        String userPassword = inputPassword.getInputText();
        
        IUPnPAccess upnpAccess = new UPnPAccessImpl();
        IUPnPService upnp = new UPnPServiceImpl(upnpAccess);
        
        IStunService stun = new StunServiceImpl();
        XMPPConnectionService service = new XMPPConnectionService(upnp, stun);
        
        try{
            service.connect(new ConnectionConfiguration("saros-con.imp.fu-berlin.de"), userJabberID, userPassword);
            
            String values = "Your roster entries are: \n\n";
            
            final Roster roster = service.getRoster();
            final Collection<RosterEntry> entries = roster.getEntries();
            
            for (RosterEntry entry: entries){
                values += entry.getUser() + " \n";   
            }
            service.disconnect();        
            
            
            
            NotifyDescriptor thisDesriptor = new NotifyDescriptor.Message(values, NotifyDescriptor.INFORMATION_MESSAGE);
            DialogDisplayer.getDefault().notify(thisDesriptor);
        
        } catch (XMPPException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
