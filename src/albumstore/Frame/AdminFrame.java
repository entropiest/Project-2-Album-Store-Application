/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package albumstore.Frame;

import albumstore.Controller.AdminController;
import albumstore.Controller.OrderController;
import albumstore.Controller.UserController;
import albumstore.Helper.Helper;
import albumstore.Model.AdminModel;
import albumstore.Model.UserModel;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Prio
 */
public class AdminFrame extends javax.swing.JFrame {

    AdminModel model = new AdminModel();
    AdminController controller = new AdminController();

    UserModel um = new UserModel();
    UserController uc = new UserController();

    OrderController oc = new OrderController();

    Helper helper = new Helper();

    DefaultTableModel defaultModel;
    ResultSet rs;
    ResultSet rs1;
    ResultSet rs2;
    String album_id;
    String order_id;

    /**
     * Creates new form MainFrame
     */
    public AdminFrame(UserModel userModel) {
        initComponents();
        this.clear();

        String labelMessage = "Selamat Datang, " + userModel.getName();
        this.label_name.setText(labelMessage);
    }
    
    public void clear() {
        tf_title.setText("");
        tf_price.setText("");
        tf_stock.setText("");
        tf_years.setText("");
        tf_artist.setText("");
        cb_type.setSelectedIndex(0);
    }     

    private String validationUpdate(){
        List<String> flag = new ArrayList<String>();
        String alert = "";

    String title = tf_title.getText();
        if(title.isEmpty()){
            flag.add("Title cannot be blank!");
        }

        if(cb_type.getSelectedIndex()== 0){
            flag.add("Type cannot be blank!");
        }

    String artist = tf_artist.getText();
           if(artist.isEmpty()){
            flag.add("Artist cannot be blank!");
        }

    String years = tf_years.getText();
           if(years.isEmpty()){
            flag.add("Year cannot be blank!");
        }

           else if(!(years.length()== 4)){
            flag.add("Please enter a spesific year!");
        }

           else if(!years.matches("[0-9]+")){
            flag.add("Year contains number only!");
        }

    String price = tf_price.getText();
           if(price.isEmpty()){
             flag.add("Price cannot be blank!");
        }

           else if(!price.matches("[0-9]+")){
            flag.add("Price contains number only!");
        }

    String stock = tf_stock.getText();
           if(stock.isEmpty()){
              flag.add("Stock cannot be blank!");
        }

           if(stock.equals(0)){
              flag.add("Stock cannot be empty!");
        }

           else if(!stock.matches("[0-9]+")){
            flag.add("Stock contains number only!");
        }

        if (flag.size() > 0) {
            for (String msg : flag) {
                alert += (msg + "\n");
            }
        }

        return alert;
    }

    private String validationSubmit() {
        List<String> flag = new ArrayList<String>();
        String alert = "";
        
    String title = tf_title.getText();
    String years = tf_years.getText();
    Boolean check = (this.checkAlbum(title, years));
        if(check.equals(true)){
            flag.add("The data already exist.");
        }

        if (flag.size() > 0) {
            for (String msg : flag) {
                alert += (msg + "\n");
            }
        }

        return alert;
    }

    public void saveAlbum(){
        try {
            String title = tf_title.getText();
            String artist = tf_artist.getText();
            String years = tf_years.getText();
            String price = tf_price.getText();
            String stock = tf_stock.getText();
            String type = cb_type.getSelectedItem().toString();
            

            model.setTitle(title);
            model.setArtist(artist);
            model.setYears(years);
            model.setType(type);
            model.setPrice(price);
            model.setStock(stock);

            Boolean result = controller.create(model);
            
            String msg = "Gagal menambahkan data!";
            if(result) {
                msg = "Berhasil menambahkan data";
            }
            
            JOptionPane.showMessageDialog(null, msg);
            this.clear();
            this.getAllData();

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateAlbum(){
        try {
            String title = tf_title.getText();
            String artist = tf_artist.getText();
            String years = tf_years.getText();
            String price = tf_price.getText();
            String stock = tf_stock.getText();
            String type = cb_type.getSelectedItem().toString();
            

            model.setTitle(title);
            model.setArtist(artist);
            model.setYears(years);
            model.setType(type);
            model.setPrice(price);
            model.setStock(stock);

            Boolean result = controller.update(this.album_id, model);
            
            String msg = "";
            if(result) {
                msg = "Berhasil mengubah data";
            }
            
            JOptionPane.showMessageDialog(null, msg);
            this.clear();
            this.getAllData();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }  
    }

    public void getAllData() {
        this.rs = controller.get();
        this.albumTable(this.rs);

        this.rs1 = oc.get();
        this.orderTable(this.rs1);

        this.rs2 = uc.get();
        this.userTable(this.rs2);
    }
    
    public void albumTable(ResultSet rs) {
        tb_album.setModel(DbUtils.resultSetToTableModel(rs));
    }

    public void orderTable(ResultSet rs1) {
        tb_orders.setModel(DbUtils.resultSetToTableModel(rs1));
    }

    public void userTable(ResultSet rs2) {
        tb_users.setModel(DbUtils.resultSetToTableModel(rs2));
    }

    private void searchAlbum(String type, String query) {
        try {
            if(query.isEmpty()) {
                this.rs = controller.get();
            } else if (type.equals("id_album") && !query.isEmpty()) {
                this.rs = controller.showById(query);
            } else if (type.equals("title") && !query.isEmpty()) {
                this.rs = controller.showByTitle(query);
            }
            
            this.albumTable(this.rs);
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void searchOrder(String type, String query) {
        try {
            if(query.isEmpty()) {
                this.rs1 = oc.get();
            } else if (type.equals("id_user") && !query.isEmpty()) {
                this.rs1 = oc.showByIdBuyer(query);
            } else if (type.equals("id_order") && !query.isEmpty()) {
                this.rs1 = oc.showByIdOrder(query);
            } else if (type.equals("id_album") && !query.isEmpty()) {
                this.rs1 = oc.showByIdAlbum(query);
            }
            
            this.orderTable(this.rs1);
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void searchUser(String type, String query) {
        try {
            if(query.isEmpty()) {
                this.rs2 = uc.get();
            } else if (type.equals("id_user") && !query.isEmpty()) {
                this.rs2 = uc.showById(query);
            } else if (type.equals("type") && !query.isEmpty()) {
                this.rs2 = uc.showByType(query);
            }
            
            this.userTable(this.rs2);
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Boolean checkAlbum(String title, String years) {
        Boolean album_exist = false;
	try {
		this.rs = controller.checkAlbum(title, years);

		if (this.rs.next()) {
		album_exist = true;
            	}
	}
	catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

	return album_exist;
    }

    public void deleteAlbum(){
        try {
            Boolean result = controller.delete(this.album_id);
            
            String msg = "Gagal menghapus data!";
            if(result) {
                msg = "Berhasil menghapus data";
            }
            
            JOptionPane.showMessageDialog(null, msg);
            
            this.clear();
            this.getAllData();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tf_title = new javax.swing.JTextField();
        tf_price = new javax.swing.JTextField();
        tf_artist = new javax.swing.JTextField();
        tf_stock = new javax.swing.JTextField();
        tf_years = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_album = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        btn_submit = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        tf_search = new javax.swing.JTextField();
        btn_search_title = new javax.swing.JButton();
        btn_search_id = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        label_name = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cb_type = new javax.swing.JComboBox<>();
        tf_search_order = new javax.swing.JTextField();
        bt_search_order_idOrder = new javax.swing.JButton();
        tf_search_id_buyer = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb_orders = new javax.swing.JTable();
        BTN_EXIT = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        bt_search_user = new javax.swing.JButton();
        tf_search_user = new javax.swing.JTextField();
        cb_user_type = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_users = new javax.swing.JTable();
        bt_logout = new javax.swing.JButton();
        bt_search_user_type = new javax.swing.JButton();
        bt_search_id_album = new javax.swing.JButton();
        btn_delete_order = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel1.setText("DETAIL ORDER");

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel3.setText("Judul Album");

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel4.setText("Artis");

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel5.setText("Tahun Rilis");

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel6.setText("Harga Album");

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel7.setText("Stok Album");

        tf_title.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        tf_price.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        tf_artist.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        tf_stock.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        tf_years.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        jLabel8.setText("MENU ADMIN");

        tb_album.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_album.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_albumMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_album);

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel9.setText("DETAIL ALBUM");

        btn_submit.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btn_submit.setText("Submit");
        btn_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submitActionPerformed(evt);
            }
        });

        btn_update.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_delete.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        tf_search.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        btn_search_title.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btn_search_title.setText("Cari Judul Album");
        btn_search_title.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search_titleActionPerformed(evt);
            }
        });

        btn_search_id.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btn_search_id.setText("Cari ID Album");
        btn_search_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search_idActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel10.setText("Pencarian");

        label_name.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        label_name.setText("                  ");

        jLabel11.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel11.setText("Jenis Album");

        cb_type.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        cb_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Tipe", "CD", "Vinyl", "Tape Recorder" }));

        bt_search_order_idOrder.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        bt_search_order_idOrder.setText("Cari  ID Order");
        bt_search_order_idOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_search_order_idOrderActionPerformed(evt);
            }
        });

        tf_search_id_buyer.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        tf_search_id_buyer.setText("Cari ID Buyer");
        tf_search_id_buyer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_search_id_buyerActionPerformed(evt);
            }
        });

        tb_orders.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tb_orders);

        BTN_EXIT.setFont(new java.awt.Font("sansserif", 3, 18)); // NOI18N
        BTN_EXIT.setText("EXIT");
        BTN_EXIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_EXITActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel12.setText("USER INFO");

        jLabel13.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel13.setText("Pencarian");

        bt_search_user.setText("Cari ID");
        bt_search_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_search_userActionPerformed(evt);
            }
        });

        cb_user_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "U" }));
        cb_user_type.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cb_user_typeMouseClicked(evt);
            }
        });

        tb_users.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tb_users);

        bt_logout.setFont(new java.awt.Font("sansserif", 3, 18)); // NOI18N
        bt_logout.setText("LOG OUT");
        bt_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_logoutActionPerformed(evt);
            }
        });

        bt_search_user_type.setText("Cari Tipe");
        bt_search_user_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_search_user_typeActionPerformed(evt);
            }
        });

        bt_search_id_album.setText("Cari ID Album");
        bt_search_id_album.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_search_id_albumActionPerformed(evt);
            }
        });

        btn_delete_order.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btn_delete_order.setText("Delete");
        btn_delete_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_orderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel11))
                    .addComponent(jLabel6)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel7)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_submit)
                                .addGap(32, 32, 32)
                                .addComponent(btn_update))
                            .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_years, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_type, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_price, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 818, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(tf_title, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(label_name)
                                            .addComponent(jLabel8))))
                                .addGap(282, 282, 282))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(tf_artist, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(tf_search, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(btn_search_id, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_search_title))
                            .addComponent(jLabel9)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_delete_order, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(bt_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(BTN_EXIT))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tf_search_order, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(bt_search_order_idOrder)
                                        .addGap(18, 18, 18)
                                        .addComponent(tf_search_id_buyer)
                                        .addGap(18, 18, 18)
                                        .addComponent(bt_search_id_album)))
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tf_search_user, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(6, 6, 6)
                                .addComponent(bt_search_user)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cb_user_type, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bt_search_user_type)
                                .addGap(4, 4, 4)))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_name)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel3)))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(tf_artist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(6, 6, 6)
                        .addComponent(tf_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btn_search_id))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btn_search_title)))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel5)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel11)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tf_years, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cb_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(tf_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(tf_stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_submit)
                            .addComponent(btn_update))
                        .addGap(6, 6, 6)
                        .addComponent(btn_delete)))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel1))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_search_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(bt_search_user)
                                .addComponent(jLabel13))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cb_user_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bt_search_user_type))))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_search_order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_search_order_idOrder)
                            .addComponent(tf_search_id_buyer)
                            .addComponent(bt_search_id_album))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTN_EXIT)
                    .addComponent(bt_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete_order))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submitActionPerformed
         // TODO add your handling code here:
        String validationUp = this.validationUpdate();
        if(validationUp.length() > 0) {
            JOptionPane.showMessageDialog(null, validationUp, "Validation Error!", 
            JOptionPane.INFORMATION_MESSAGE);
            return;
        } 
        String validationSub = this.validationSubmit();
        if(validationSub.length() > 0) {
            JOptionPane.showMessageDialog(null, validationSub, "Validation Error!", 
            JOptionPane.INFORMATION_MESSAGE);
            return;
        } 
        else {
            this.saveAlbum();
        }
    }//GEN-LAST:event_btn_submitActionPerformed

    private void tb_albumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_albumMouseClicked
        try {
        String id_album = helper.getValueRows(tb_album, 0);
        String title = helper.getValueRows(tb_album, 1);
        String artist = helper.getValueRows(tb_album, 2);
        String years = helper.getValueRows(tb_album, 3);
        String type = helper.getValueRows(tb_album, 4);
        String price = helper.getValueRows(tb_album, 5);
        String stock = helper.getValueRows(tb_album, 6);

        btn_update.setEnabled(true);
        btn_delete.setEnabled(true);

        this.album_id = id_album;
        tf_title.setText(title);
        tf_artist.setText(artist);
        tf_years.setText(years);
        cb_type.setSelectedItem(type);
        tf_price.setText(price);
        tf_stock.setText(stock);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_tb_albumMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.getAllData();
    }//GEN-LAST:event_formWindowOpened

    private void btn_search_titleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search_titleActionPerformed
        // TODO add your handling code here:
        this.searchAlbum("title", tf_search.getText());
    }//GEN-LAST:event_btn_search_titleActionPerformed

    private void btn_search_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search_idActionPerformed
        // TODO add your handling code here:
        this.searchAlbum("id_album", tf_search.getText());
    }//GEN-LAST:event_btn_search_idActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
            String validation = this.validationUpdate();
        if(validation.length() > 0) {
            JOptionPane.showMessageDialog(null, validation, "Validation Error!", 
            JOptionPane.INFORMATION_MESSAGE);
            return;
        } 
        else {
            this.updateAlbum();
        }                                   
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        this.deleteAlbum();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void BTN_EXITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_EXITActionPerformed
        System.exit(0);
    }//GEN-LAST:event_BTN_EXITActionPerformed

    private void bt_search_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_search_userActionPerformed
        // TODO add your handling code here:
        this.searchUser("id_user", tf_search_user.getText());
    }//GEN-LAST:event_bt_search_userActionPerformed

    private void cb_user_typeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cb_user_typeMouseClicked
        // TODO add your handling code here
    }//GEN-LAST:event_cb_user_typeMouseClicked

    private void bt_search_user_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_search_user_typeActionPerformed
        // TODO add your handling code here:
        this.searchUser("type", cb_user_type.getSelectedItem().toString());
    }//GEN-LAST:event_bt_search_user_typeActionPerformed

    private void bt_search_order_idOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_search_order_idOrderActionPerformed
        // TODO add your handling code here:
        this.searchOrder("id_order", tf_search_order.getText());
    }//GEN-LAST:event_bt_search_order_idOrderActionPerformed

    private void tf_search_id_buyerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_search_id_buyerActionPerformed
        // TODO add your handling code here:
        this.searchOrder("id_user", tf_search_order.getText());
    }//GEN-LAST:event_tf_search_id_buyerActionPerformed

    private void bt_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_logoutActionPerformed
        // TODO add your handling code here:
        RegisterFrame frame = new RegisterFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_bt_logoutActionPerformed

    private void btn_delete_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_orderActionPerformed
        // TODO add your handling code here:
    try {
            String id_order = helper.getValueRows(tb_orders, 0);
            this.order_id = id_order;

            Boolean result = oc.delete(this.order_id);
            
            String msg = "Gagal menghapus data!";
            if(result) {
                msg = "Berhasil menghapus data";
            }
            
            JOptionPane.showMessageDialog(null, msg);
            
            this.clear();
            this.getAllData();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btn_delete_orderActionPerformed

    private void bt_search_id_albumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_search_id_albumActionPerformed
        // TODO add your handling code here:
        this.searchOrder("id_album", tf_search_order.getText());
    }//GEN-LAST:event_bt_search_id_albumActionPerformed

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
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminFrame(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTN_EXIT;
    private javax.swing.JButton bt_logout;
    private javax.swing.JButton bt_search_id_album;
    private javax.swing.JButton bt_search_order_idOrder;
    private javax.swing.JButton bt_search_user;
    private javax.swing.JButton bt_search_user_type;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_delete_order;
    private javax.swing.JButton btn_search_id;
    private javax.swing.JButton btn_search_title;
    private javax.swing.JButton btn_submit;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> cb_type;
    private javax.swing.JComboBox<String> cb_user_type;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel label_name;
    private javax.swing.JTable tb_album;
    private javax.swing.JTable tb_orders;
    private javax.swing.JTable tb_users;
    private javax.swing.JTextField tf_artist;
    private javax.swing.JTextField tf_price;
    private javax.swing.JTextField tf_search;
    private javax.swing.JButton tf_search_id_buyer;
    private javax.swing.JTextField tf_search_order;
    private javax.swing.JTextField tf_search_user;
    private javax.swing.JTextField tf_stock;
    private javax.swing.JTextField tf_title;
    private javax.swing.JTextField tf_years;
    // End of variables declaration//GEN-END:variables
}
