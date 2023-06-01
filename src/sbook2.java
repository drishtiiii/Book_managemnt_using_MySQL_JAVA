
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.Toolkit;

public class sbook2 extends javax.swing.JFrame {

    private final String DB_URL = "jdbc:mysql://localhost:3306/book_management_db";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "Jenish@3116";

    /**
     * Creates new form BookSearchForm
     */
    public sbook2() {
        initComponents();
        setWindowSizeToScreenSize();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void setWindowSizeToScreenSize() {
        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Set the JFrame size to match the screen size
        setSize(screenSize.width, screenSize.height);
    }
  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
   private void initComponents() {
    searchButton = new javax.swing.JButton();
    searchTextField = new javax.swing.JTextField();
    searchOptionButtonGroup = new javax.swing.ButtonGroup();
    bookIdRadioButton = new javax.swing.JRadioButton();
    bookNameRadioButton = new javax.swing.JRadioButton();
    genreRadioButton = new javax.swing.JRadioButton();
    authorsRadioButton = new javax.swing.JRadioButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    searchResultTable = new javax.swing.JTable();
    jButton3 = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Book Search");

   jButton3.setText("Back");
jButton3.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton3ActionPerformed(evt);
    }
});

// Update this line
searchButton.setText("Find Books");
searchButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        searchButtonActionPerformed(evt);
    }
});

    searchOptionButtonGroup.add(bookIdRadioButton);
    bookIdRadioButton.setSelected(true);
    bookIdRadioButton.setText("Book ID");

    searchOptionButtonGroup.add(bookNameRadioButton);
    bookNameRadioButton.setText("Book Name");

    searchOptionButtonGroup.add(genreRadioButton);
    genreRadioButton.setText("Genre");

    searchOptionButtonGroup.add(authorsRadioButton);
    authorsRadioButton.setText("Authors");

    searchResultTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Book ID", "Book Name", "Genre", "Authors"
        }
    ));
    jScrollPane1.setViewportView(searchResultTable);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bookIdRadioButton)
                        .addComponent(bookNameRadioButton)
                        .addComponent(genreRadioButton)
                        .addComponent(authorsRadioButton))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(bookIdRadioButton))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(bookNameRadioButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(genreRadioButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(authorsRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addContainerGap())
    );

    pack();
}
 private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        String searchBy = "";
        if (bookIdRadioButton.isSelected()) {
            searchBy = "Book_id";
        } else if (bookNameRadioButton.isSelected()) {
            searchBy = "Book_title";
        } else if (genreRadioButton.isSelected()) {
            searchBy = "Genre";
        } else if (authorsRadioButton.isSelected()) {
            searchBy = "Author";
        } else {
            JOptionPane.showMessageDialog(this, "Please select a search option.", "Invalid Option", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String searchQuery = searchTextField.getText();
        DefaultTableModel model = (DefaultTableModel) searchResultTable.getModel();
        model.setRowCount(0);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String sql = "SELECT * FROM books_info WHERE " + searchBy + " LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + searchQuery + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt("Book_id");
                String bookName = rs.getString("Book_title");
                String genre = rs.getString("Genre");
                String authors = rs.getString("Author");
                

                model.addRow(new Object[]{bookId, bookName, genre, authors});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }                                            
private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
              setVisible(false);
              new home().setVisible(true);
    }                                        

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
            java.util.logging.Logger.getLogger(sbook2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sbook2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sbook2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sbook2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new sbook2().setVisible(true);
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JRadioButton authorsRadioButton;
    private javax.swing.JRadioButton bookIdRadioButton;
    private javax.swing.JRadioButton bookNameRadioButton;
    private javax.swing.JRadioButton genreRadioButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton searchButton;
    private javax.swing.ButtonGroup searchOptionButtonGroup;
    private javax.swing.JTable searchResultTable;
    private javax.swing.JTextField searchTextField;
     private javax.swing.JButton jButton3;
    // End of variables declaration                   
}

