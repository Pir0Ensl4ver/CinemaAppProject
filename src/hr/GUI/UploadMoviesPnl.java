/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.GUI;

import hr.algebra.utils.MessageUtils;
import hr.dal.Repo;
import hr.dal.RepoFactory;
import hr.dal.SQL.MovieRepo;
import hr.model.Movie;
import hr.parser.MovieParser;
import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author User
 */
public class UploadMoviesPnl extends javax.swing.JPanel {

    private static final String DIR = "assets";
    
    
    private DefaultListModel<Movie> moviesModel;
    private Repo repository;
    /**
     * Creates new form UploadMoviesPnl
     */
    public UploadMoviesPnl() {
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnUpload = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        lsMovies = new javax.swing.JList<>();

        btnUpload.setText("Upload movies");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        btnRemove.setBackground(java.awt.Color.darkGray);
        btnRemove.setForeground(java.awt.Color.white);
        btnRemove.setText("Delete movies");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lsMovies, javax.swing.GroupLayout.PREFERRED_SIZE, 1074, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lsMovies, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        new Thread(() -> {
            try {
                List<Movie> movies = MovieParser.parse();
                repository.create(movies);
                loadModel();
            } catch (Exception ex) {
                Logger.getLogger(UploadMoviesPnl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        try {
            deleteAllMovies();
        } catch (Exception ex) {
            Logger.getLogger(UploadMoviesPnl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRemoveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnUpload;
    private javax.swing.JList<hr.model.Movie> lsMovies;
    // End of variables declaration//GEN-END:variables

    private void init() {
        
        try {
            moviesModel = new DefaultListModel<>();
            repository = RepoFactory.getMovieRepo();
            new Thread(() -> {
                try {
                    loadModel();
                } catch (Exception ex) {
                    Logger.getLogger(UploadMoviesPnl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
        } catch (Exception ex) {
            Logger.getLogger(UploadMoviesPnl.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to load form");
            System.exit(1);
        }
        
    }

    private void loadModel() {
        try {
            List<Movie> movies = repository.select();
            moviesModel.clear();
            movies.forEach(moviesModel::addElement);
            lsMovies.setModel(moviesModel);
        } catch (Exception ex) {
            Logger.getLogger(UploadMoviesPnl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteAllMovies() throws Exception {
        
        Arrays.stream(new File(DIR).listFiles()).forEach(File::delete);
        MovieRepo.deleteMovies();
        loadModel();
        
    }
}
