/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.stuff;

import javax.swing.JPanel;

/**
 *
 * @author Hassan
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     *
     * @param username
     */
    public Home(String username, String responsibility) {
        initComponents();
        if (responsibility.equals("empolyee")) {
            switchPanelTo(empolyeePanel);
        } else if (responsibility.equals("student")) {
            switchPanelTo(studentPanel);
        }
        this.setVisible(true);
    }

    private Home() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void switchPanelTo(JPanel panel) {
        homePanel.removeAll();
        homePanel.repaint();
        homePanel.revalidate();
        homePanel.add(panel);
        homePanel.repaint();
        homePanel.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        closeTabButton = new javax.swing.JButton();
        homePanel = new javax.swing.JPanel();
        empolyeePanel = new javax.swing.JPanel();
        addStudentButton = new javax.swing.JButton();
        addEmpolyeeButton = new javax.swing.JButton();
        studentPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        closeTabButton.setText("Close Tab");
        closeTabButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeTabButtonActionPerformed(evt);
            }
        });

        homePanel.setLayout(new java.awt.CardLayout());

        addStudentButton.setText("Add student");
        addStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentButtonActionPerformed(evt);
            }
        });

        addEmpolyeeButton.setText("Add Empolyee");
        addEmpolyeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmpolyeeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout empolyeePanelLayout = new javax.swing.GroupLayout(empolyeePanel);
        empolyeePanel.setLayout(empolyeePanelLayout);
        empolyeePanelLayout.setHorizontalGroup(
            empolyeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empolyeePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(empolyeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(addStudentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addEmpolyeeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        empolyeePanelLayout.setVerticalGroup(
            empolyeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empolyeePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addEmpolyeeButton)
                .addGap(18, 18, 18)
                .addComponent(addStudentButton)
                .addContainerGap(658, Short.MAX_VALUE))
        );

        homePanel.add(empolyeePanel, "card2");

        jButton1.setText("Show Exam Result");

        javax.swing.GroupLayout studentPanelLayout = new javax.swing.GroupLayout(studentPanel);
        studentPanel.setLayout(studentPanelLayout);
        studentPanelLayout.setHorizontalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addContainerGap())
        );
        studentPanelLayout.setVerticalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(701, Short.MAX_VALUE))
        );

        homePanel.add(studentPanel, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(homePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(closeTabButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(homePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeTabButton)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
 Register r = new Register("student");
    private void addEmpolyeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmpolyeeButtonActionPerformed
        Register r = null;
        if (r.createdStuffNum == 0) {
            jTabbedPane1.add("Add Empolyee", new Register("empolyee"));
        }
    }//GEN-LAST:event_addEmpolyeeButtonActionPerformed

    private void addStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentButtonActionPerformed
        Register r = null;
        if (r.createdStudentNum == 1) {
            jTabbedPane1.add("Add Student", new Register("student"));
        }
    }//GEN-LAST:event_addStudentButtonActionPerformed

    private void closeTabButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeTabButtonActionPerformed
        jTabbedPane1.remove(jTabbedPane1.getSelectedIndex());
    }//GEN-LAST:event_closeTabButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEmpolyeeButton;
    private javax.swing.JButton addStudentButton;
    private javax.swing.JButton closeTabButton;
    private javax.swing.JPanel empolyeePanel;
    private javax.swing.JPanel homePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel studentPanel;
    // End of variables declaration//GEN-END:variables
}
