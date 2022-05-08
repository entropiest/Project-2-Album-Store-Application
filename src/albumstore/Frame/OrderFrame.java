/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package albumstore.Frame;


import albumstore.Controller.AdminController;
import albumstore.Controller.OrderController;
import albumstore.Helper.Helper;
import albumstore.Model.AdminModel;
import albumstore.Model.OrderModel;
import albumstore.Model.UserModel;
import albumstore.Query.OrderQuery;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Prio
 */
public class OrderFrame extends javax.swing.JFrame {
    // menyimpan data order
    OrderController oc = new OrderController();

    // untuk menampilkan data album
    AdminController controller = new AdminController();
    ResultSet rs;
    ResultSet rs1;
    DefaultTableModel defaultmodel;

    //  update stock album
    AdminModel am = new AdminModel();
    String album_id;

    // permasalahan tanggal
    Helper helper = new Helper();

    // simpan data ke database
    OrderQuery query = new OrderQuery();

    /**
     * Creates new form UserFrame
     */
    public OrderFrame(UserModel userModel) {
        initComponents();

        // set label username
        String labelMessage = "Selamat Datang, " + userModel.getUsername();
        this.label_username.setText(labelMessage);

        //set label id
        String labelid = userModel.getId_user();
        this.label_id.setText(labelid);

        // set label name
        String labelname = userModel.getName();
        this.label_name.setText(labelname);

        tf_id_album.setEditable(false);
        tf_title_album.setEditable(false);
        tf_price_album.setEditable(false);
        tf_total_price.setEditable(false);
    }

     private String validation() {
        List<String> flag = new ArrayList<String>();
        String alert = "";
        
        String amount = tf_amount_album.getText();
        
        if(amount.isEmpty()) {
            flag.add("Amount cannot be blank!");
        }

        if(!amount.matches("[0-9]+")){
            flag.add("Amount contains number only!");
        }

        if(dp_date.getDate()== null){
            flag.add("Date cannot be blank!");
        }

        int stock = Integer.parseInt(helper.getValueRows(tb_album, 6));
        int buy = Integer.parseInt(amount);

        if(buy == 0){
              flag.add("Amount cannot be empty!");
        }

        if(buy > stock){
            flag.add("Lack of stock!");
        }

        if (flag.size() > 0) {
            for (String msg : flag) {
                alert += (msg + "\n");
            }
        }

        return alert;
    }

    public void reduceAlbumStock(){
        try {
            int stock, amount, total;
            stock = Integer.parseInt(helper.getValueRows(tb_album, 6));
            amount = Integer.parseInt(tf_amount_album.getText());
            total = stock - amount;

            String update = String.valueOf(total);

            String id_album = helper.getValueRows(tb_album, 0);
            this.album_id = id_album;

            am.setStock(update);

            controller.updateStock(this.album_id, am);
            this.getDataAlbum();

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }  
    }

    public void clear() {
        tf_id_album.setText("");
        tf_title_album.setText("");
        tf_price_album.setText("");
        tf_amount_album.setText("");
        tf_total_price.setText("");
        dp_date.setDate(null);
    }   

    public void getDataAlbum() {
        this.rs = controller.get();
        this.loadTable(this.rs);
    }

    public void getDataOrder() {
        this.rs1 = oc.get();
    }
    
    public void loadTable(ResultSet rs) {
        tb_album.setModel(DbUtils.resultSetToTableModel(rs));
    }

    private void totalPrice(){
        int price, amount, result;
        price = Integer.parseInt(tf_price_album.getText());
        amount = Integer.parseInt(tf_amount_album.getText());
        result = price * amount;
        tf_total_price.setText(String.valueOf(result));
    }

    private void saveDataOrder(){
        try {
            String id_album = helper.getValueRows(tb_album, 0);
            String amount = tf_amount_album.getText();
            String total = tf_total_price.getText();
            String id_user = label_id.getText();

            Date date = dp_date.getDate();
            
            OrderModel model = new OrderModel();
            model.setId_album(id_album);
            model.setTotal_price(total);
            model.setAmount(amount);
            model.setOrder_date(date);
            model.setId_user(id_user);

            Boolean result = oc.create(model);
            
            String msg = "Gagal menambahkan data!";
            if(result) {
                msg = "Berhasil menambahkan pesanan Anda.";
            }
            
            JOptionPane.showMessageDialog(null, msg);
            this.getDataOrder(); 
            this.reduceAlbumStock();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void searchAlbum(String type, String query) {
        try {
            if(query.isEmpty()) {
                this.rs = controller.get();
            } else if (type.equals("title") && !query.isEmpty()) {
                this.rs = controller.showByTitle(query);
            } else if (type.equals("artist") && !query.isEmpty()) { 
                this.rs = controller.showByArtist(query); }
            
            this.loadTable(this.rs);
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tf_id_album = new javax.swing.JTextField();
        tf_title_album = new javax.swing.JTextField();
        tf_price_album = new javax.swing.JTextField();
        tf_amount_album = new javax.swing.JTextField();
        tf_total_price = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btn_reset = new javax.swing.JButton();
        btn_submit = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_album = new javax.swing.JTable();
        tf_search = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btn_search_title = new javax.swing.JButton();
        dp_date = new org.jdesktop.swingx.JXDatePicker();
        jLabel12 = new javax.swing.JLabel();
        label_username = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        label_id = new javax.swing.JLabel();
        label_name = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        BT_LOGOUT = new javax.swing.JButton();
        bt_search_artist = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel3.setText("ID Album");

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel4.setText("Judul Album");

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel5.setText("Harga Album");

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel6.setText("Jumlah");

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel7.setText("Total Harga");

        tf_amount_album.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_amount_albumKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel8.setText("Tanggal");

        btn_reset.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btn_reset.setText("Reset");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        btn_submit.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btn_submit.setText("Submit");
        btn_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submitActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel9.setText("Daftar Album");

        tb_album.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Judul", "Harga", "Jumlah"
            }
        ));
        tb_album.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_albumMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_album);

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel10.setText("Pencarian");

        btn_search_title.setText("Cari Judul Album");
        btn_search_title.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search_titleActionPerformed(evt);
            }
        });

        dp_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_dateActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        jLabel12.setText("ORDER MENU");

        label_username.setText("                             ");

        label_id.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        label_id.setText("label_id");

        label_name.setText(" ");

        jLabel11.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel11.setText("Pembelian");

        BT_LOGOUT.setFont(new java.awt.Font("sansserif", 3, 18)); // NOI18N
        BT_LOGOUT.setText("LOGOUT");
        BT_LOGOUT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_LOGOUTActionPerformed(evt);
            }
        });

        bt_search_artist.setText("Cari Artis");
        bt_search_artist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_search_artistActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_username, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_name, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(tf_id_album, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(186, 186, 186)
                        .addComponent(jLabel1)))
                .addGap(41, 41, 41)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(tf_title_album))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(tf_price_album, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(51, 51, 51)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)))
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tf_total_price, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tf_amount_album, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btn_submit)
                                                .addGap(81, 81, 81)
                                                .addComponent(btn_reset))
                                            .addComponent(dp_date, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(46, 46, 46)))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tf_search, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_search_title)
                                .addGap(18, 18, 18)
                                .addComponent(bt_search_artist, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_id, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BT_LOGOUT))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(label_username)
                        .addGap(6, 6, 6)
                        .addComponent(label_name))
                    .addComponent(label_id, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tf_id_album, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel9)))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_title_album, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_price_album, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_amount_album, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(dp_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tf_total_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_reset)
                            .addComponent(btn_submit)
                            .addComponent(BT_LOGOUT)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_search_title)
                            .addComponent(tf_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_search_artist))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tb_albumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_albumMouseClicked
        try {
        String id_album = helper.getValueRows(tb_album, 0);
        String title = helper.getValueRows(tb_album, 1);
        String price = helper.getValueRows(tb_album, 5);

        tf_id_album.setText(id_album);
        tf_title_album.setText(title);
        tf_price_album.setText(price);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_tb_albumMouseClicked

    private void btn_submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submitActionPerformed
        String validation = this.validation();
        if(validation.length() > 0) {
            JOptionPane.showMessageDialog(null, validation, "Validation Error!", 
            JOptionPane.INFORMATION_MESSAGE);
            return;
        } 
        else {
            this.saveDataOrder();
            this.clear();
        }
    }//GEN-LAST:event_btn_submitActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        // TODO add your handling code here:
        this.clear();
    }//GEN-LAST:event_btn_resetActionPerformed

    private void btn_search_titleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search_titleActionPerformed
        // TODO add your handling code here:
        this.searchAlbum("title", tf_search.getText());
    }//GEN-LAST:event_btn_search_titleActionPerformed

    private void dp_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dp_dateActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
      this.getDataAlbum();
    }//GEN-LAST:event_formWindowOpened

    private void tf_amount_albumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_amount_albumKeyReleased
      this.totalPrice();
    }//GEN-LAST:event_tf_amount_albumKeyReleased

    private void BT_LOGOUTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_LOGOUTActionPerformed
        // TODO add your handling code here:
        RegisterFrame frame = new RegisterFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BT_LOGOUTActionPerformed

    private void bt_search_artistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_search_artistActionPerformed
        // TODO add your handling code here:
        this.searchAlbum("artist", tf_search.getText());
    }//GEN-LAST:event_bt_search_artistActionPerformed


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
            java.util.logging.Logger.getLogger(OrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderFrame(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BT_LOGOUT;
    private javax.swing.JButton bt_search_artist;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_search_title;
    private javax.swing.JButton btn_submit;
    private org.jdesktop.swingx.JXDatePicker dp_date;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_id;
    private javax.swing.JLabel label_name;
    private javax.swing.JLabel label_username;
    private javax.swing.JTable tb_album;
    private javax.swing.JTextField tf_amount_album;
    private javax.swing.JTextField tf_id_album;
    private javax.swing.JTextField tf_price_album;
    private javax.swing.JTextField tf_search;
    private javax.swing.JTextField tf_title_album;
    private javax.swing.JTextField tf_total_price;
    // End of variables declaration//GEN-END:variables
}
