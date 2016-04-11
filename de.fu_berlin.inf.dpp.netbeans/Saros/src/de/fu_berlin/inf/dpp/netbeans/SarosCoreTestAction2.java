/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fu_berlin.inf.dpp.netbeans;

import de.fu_berlin.inf.dpp.net.ConnectionState;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jivesoftware.smack.packet.StreamError;
import org.jivesoftware.smackx.ChatState;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.HtmlBrowser;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Build",
        id = "de.fu_berlin.inf.dpp.netbeans.SarosCoreTestAction2")
@ActionRegistration(
        iconBase = "de/fu_berlin/inf/dpp/netbeans/saros_misc.png",
        displayName = "#CTL_SarosCoreTestAction2")
@ActionReference(path = "Menu/Saros", position = 3233, separatorBefore = 3183)
@Messages("CTL_SarosCoreTestAction2=Action2")
public final class SarosCoreTestAction2 implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String values = "Found the following connection states in the Saros Core module: \n\n";
        
        for (ConnectionState val : ConnectionState.values()){
            values += val.toString() + "\n";
        }
        
        values += "\n\n And to test Smack's chat states: \n\n";
        
        for (ChatState val : ChatState.values()) {
            values +=  val.toString() + "\n";
        }
        
        values += "\n\n And a String from the Smack patches: \n\n" + StreamError.NAMESPACE;
        
        NotifyDescriptor thisDesriptor = new NotifyDescriptor.Message(values, NotifyDescriptor.INFORMATION_MESSAGE);
        DialogDisplayer.getDefault().notify(thisDesriptor);
    }
}
