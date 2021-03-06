/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.GUI;

import hr.algebra.utils.JAXBUtils;
import hr.algebra.utils.MessageUtils;
import hr.dal.Repo;
import hr.dal.RepoFactory;
import hr.dal.SQL.ActorRepo;
import hr.dal.SQL.DirectorRepo;
import hr.model.Movie;
import hr.model.MovieArchive;
import hr.model.MoviePersonel;
import hr.model.MovieTableModel;
import hr.model.Person;
import hr.model.PersonAddable;
import hr.model.PersonEditable;
import hr.model.PersonTransferable;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import static javax.swing.TransferHandler.COPY;
import static javax.swing.TransferHandler.MOVE;
import javax.xml.bind.JAXBException;

/**
 *
 * @author User
 */
public class EditPersonsPnl extends javax.swing.JPanel implements PersonAddable, PersonEditable {

    private Repo movieRepo;
    private Repo personRepo;
    private Repo actorRepo;
    private Repo directorRepo;
    
    private MovieTableModel tableModel;
    private Movie selectedMovie;
    
    private final Set<Person> persons = new TreeSet<>();
    private final List<Person> actors = new ArrayList<>();
    private final List<Person> directors = new ArrayList<>();

    private final DefaultListModel<Person> personsModel = new DefaultListModel<>();
    private final DefaultListModel<Person> actorsModel = new DefaultListModel<>();
    private final DefaultListModel<Person> directorsModel = new DefaultListModel<>();
    /**
     * Creates new form EditPersonsPnl
     */
    public EditPersonsPnl() {
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

        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        lsTrash = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbMovies = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        lsDirectors = new javax.swing.JList<>();
        btnAddPerson = new javax.swing.JButton();
        btnUpdatePerson = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        lsActors = new javax.swing.JList<>();
        btnDeletePerson = new javax.swing.JButton();
        btnXML = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnSaveChanges = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lsPersons = new javax.swing.JList<>();

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Frag and Drop person here to remove it");

        jScrollPane5.setViewportView(lsTrash);

        tbMovies.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbMovies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMoviesMouseClicked(evt);
            }
        });
        tbMovies.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbMoviesKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbMovies);

        jScrollPane1.setViewportView(lsDirectors);

        btnAddPerson.setText("Add person");
        btnAddPerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPersonActionPerformed(evt);
            }
        });

        btnUpdatePerson.setText("Update person");
        btnUpdatePerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatePersonActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(lsActors);

        btnDeletePerson.setText("Delete person");
        btnDeletePerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletePersonActionPerformed(evt);
            }
        });

        btnXML.setText("Get XML");
        btnXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXMLActionPerformed(evt);
            }
        });

        jLabel2.setText("Directors");

        btnSaveChanges.setText("Save changes");
        btnSaveChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveChangesActionPerformed(evt);
            }
        });

        jLabel1.setText("Actors");

        jLabel3.setText("Persons");

        jScrollPane4.setViewportView(lsPersons);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnAddPerson, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpdatePerson, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(btnDeletePerson, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXML, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btnSaveChanges, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddPerson, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdatePerson, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeletePerson, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXML, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaveChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane5)))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbMoviesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMoviesMouseClicked
        showMovie();
    }//GEN-LAST:event_tbMoviesMouseClicked

    private void tbMoviesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbMoviesKeyReleased
        showMovie();
    }//GEN-LAST:event_tbMoviesKeyReleased

    private void btnAddPersonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPersonActionPerformed
        new AddPersonDialog(this,(JFrame)this.getRootPane().getParent(), true).setVisible(true);
    }//GEN-LAST:event_btnAddPersonActionPerformed

    private void btnUpdatePersonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatePersonActionPerformed
        Person person = lsPersons.getSelectedValue();
        if(person == null)
        {
            MessageUtils.showErrorMessage("Error", "Please choose a person!");
            return;
        }
        new EditPersonDialog(this, person, (JFrame)this.getRootPane().getParent(), true).setVisible(true);
    }//GEN-LAST:event_btnUpdatePersonActionPerformed

    private void btnDeletePersonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletePersonActionPerformed
        Person person = lsPersons.getSelectedValue();
        if(person == null) {
            MessageUtils.showErrorMessage("Error", "Please choose a person!");
            return;
        }
        if(MessageUtils.showConfirmDialog("Delete", "Are you sure?") == JOptionPane.YES_OPTION) {
            try {
                if(selectedMovie.getPicturePath() != null) {
                    Files.deleteIfExists(Paths.get(selectedMovie.getPicturePath()));
                }
                persons.remove(person);
                personRepo.delete(person.getId());
                loadPersonsModel();
            } catch (Exception ex) {
                Logger.getLogger(EditMoviesPnl.class.getName()).log(Level.SEVERE, null, ex);
                MessageUtils.showErrorMessage("Error", "Can't delete person connected to movie!");
            }
        }
    }//GEN-LAST:event_btnDeletePersonActionPerformed

    private static final String ARCHIVEXML = "movies.xml";
    
    private void btnXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXMLActionPerformed
        try {
            JAXBUtils.save(new MovieArchive(movieRepo.select()), ARCHIVEXML);
        } catch (JAXBException ex) {
            Logger.getLogger(EditPersonsPnl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EditPersonsPnl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnXMLActionPerformed

    private void btnSaveChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveChangesActionPerformed
        saveChanges();
    }//GEN-LAST:event_btnSaveChangesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPerson;
    private javax.swing.JButton btnDeletePerson;
    private javax.swing.JButton btnSaveChanges;
    private javax.swing.JButton btnUpdatePerson;
    private javax.swing.JButton btnXML;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JList<Person> lsActors;
    private javax.swing.JList<Person> lsDirectors;
    private javax.swing.JList<Person> lsPersons;
    private javax.swing.JList<Person> lsTrash;
    private javax.swing.JTable tbMovies;
    // End of variables declaration//GEN-END:variables


    private void init() {
        
        try {
            initRepo();
            initTable();
            persons.clear();
            persons.addAll(personRepo.select());
            loadPersonsModel();
            initDragNDrop();
        } catch (Exception ex) {
            Logger.getLogger(EditPersonsPnl.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to load form");
            System.exit(1);
        }
        
    }

    private void initRepo() throws Exception {
        
        movieRepo = RepoFactory.getMovieRepo();
        personRepo = RepoFactory.getPersonRepo();
        actorRepo = RepoFactory.getActorRepo();
        directorRepo = RepoFactory.getDirectorRepo();
    }
    
     private void initTable() throws Exception {
        tbMovies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbMovies.setAutoCreateRowSorter(true);
        tbMovies.setRowHeight(25);
        new Thread(() -> {
            try {
                tableModel = new MovieTableModel(movieRepo.select());
            } catch (Exception ex) {
                Logger.getLogger(EditPersonsPnl.class.getName()).log(Level.SEVERE, null, ex);
            }
            tbMovies.setModel(tableModel);
        }).start();
    }

    private void showMovie() {
        
        int selectedRow = tbMovies.getSelectedRow();
        int rowIndex = tbMovies.convertRowIndexToModel(selectedRow);
        int id = (int)tableModel.getValueAt(rowIndex, 0);
        
        try {
            Optional<Movie> optMovie = movieRepo.select(id);
            if(optMovie.isPresent()){
                selectedMovie = optMovie.get();
                FillForm();
            }
        } catch (Exception ex) {
            Logger.getLogger(EditPersonsPnl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                                             

    private void FillForm() throws Exception {
        actors.clear();
        directors.clear();
        directors.addAll(DirectorRepo.selectByMovie(selectedMovie.getId()));
        actors.addAll(ActorRepo.selectByMovie(selectedMovie.getId()));
        loadActorsModel();
        loadDirectorsModel();
    }

    private void loadPersonsModel() {
        loadModel(personsModel, persons, lsPersons);
    }
    
    private void loadActorsModel() {
        loadModel(actorsModel, actors, lsActors);
    }
    
    private void loadDirectorsModel() {
        loadModel(directorsModel, directors, lsDirectors);
    }

    private void loadModel(DefaultListModel<Person> model, Collection<Person> list, JList uiList) {
        model.clear();
        list.forEach(model::addElement);
        uiList.setModel(model);
    }

    private void initDragNDrop() {
        lsPersons.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsPersons.setDragEnabled(true);
        lsPersons.setTransferHandler(new ExportHandler());

        lsActors.setDropMode(DropMode.ON);
        lsActors.setDragEnabled(true);
        lsActors.setTransferHandler(new ActorsHandler());
        
        lsDirectors.setDropMode(DropMode.ON);
        lsDirectors.setDragEnabled(true);
        lsDirectors.setTransferHandler(new DirectorsHandler());
        
        lsTrash.setDropMode(DropMode.ON);
        lsTrash.setTransferHandler(new TrashImportHandler());
        
    }

    @Override
    public boolean addPerson(Person person) {
       if (persons.add(person)) {
            try {
                personRepo.create(person);
            } catch (Exception ex) {
                Logger.getLogger(EditPersonsPnl.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadPersonsModel();
            return true;
        }
        return false;
    }

    @Override
    public boolean editPerson(Person oldPerson, Person newPerson) {
        if (persons.remove(oldPerson)) {
            try {
                persons.add(newPerson);
                personRepo.update(oldPerson.getId(), newPerson);
            } catch (Exception ex) {
                Logger.getLogger(EditPersonsPnl.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadPersonsModel();
            return true;
        }
        return false;
    }

    private void saveChanges() {
        
        selectedMovie.setActors(actors);
        selectedMovie.setDirectors(directors);
        
        try {
            ActorRepo.deleteByMovie(selectedMovie.getId());
            DirectorRepo.deleteByMovie(selectedMovie.getId());
        } catch (Exception ex) {
            Logger.getLogger(EditPersonsPnl.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(selectedMovie.getDirectors() != null){
            selectedMovie.getDirectors().forEach(d -> {
                try {
                    int personId = personRepo.create(d);
                    int movieID = selectedMovie.getId();
                    directorRepo.create(new MoviePersonel(personId, movieID));
                } catch (Exception ex) {
                    Logger.getLogger(EditPersonsPnl.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        
        if(selectedMovie.getActors() != null){
            selectedMovie.getActors().forEach(d -> {
                try {
                    int personId = personRepo.create(d);
                    int movieID = selectedMovie.getId();
                    actorRepo.create(new MoviePersonel(personId, movieID));
                } catch (Exception ex) {
                    Logger.getLogger(EditPersonsPnl.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    private class TrashImportHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(PersonTransferable.PERSON_FLAVOR);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            Transferable transferable = support.getTransferable();
            try {
                Person data = (Person) transferable.getTransferData(PersonTransferable.PERSON_FLAVOR);
                if (directors.remove(data)) {
                    loadDirectorsModel();
                    return true;
                }
                else if (actors.remove(data)) {
                    loadActorsModel();
                    return true;
                }
            } catch (UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(EditPersonsPnl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
    }
    
    private class ExportHandler extends TransferHandler {
        @Override
        public int getSourceActions(JComponent c) {
            return COPY;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            return new PersonTransferable(lsPersons.getSelectedValue());
        }

    }

    private class ActorsHandler extends TransferHandler{

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(PersonTransferable.PERSON_FLAVOR);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            Transferable transferable = support.getTransferable();
            try {
                Person data = (Person) transferable.getTransferData(PersonTransferable.PERSON_FLAVOR);
                if (actors.add(data)) {
                    loadActorsModel();
                    return true;
                }
            } catch (UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(EditPersonsPnl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
       
        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            return new PersonTransferable(lsActors.getSelectedValue());
        }
        
    }
    
    private class DirectorsHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(PersonTransferable.PERSON_FLAVOR);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            Transferable transferable = support.getTransferable();
            try {
                Person data = (Person) transferable.getTransferData(PersonTransferable.PERSON_FLAVOR);
                if (directors.add(data)) {
                    loadDirectorsModel();
                    return true;
                }
            } catch (UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(EditPersonsPnl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            return new PersonTransferable(lsDirectors.getSelectedValue());
        }
    }
}
