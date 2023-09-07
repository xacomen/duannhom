/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Models.LoaiMon;
import Models.Mon;
import Service.LoaiMonService;
import Service.LoaiMonServiceImpl;
import Service.MonService;
import Service.MonServiceImpl;
import Ulties.DialogHelper;
import Ulties.ShareHelper;
import Views.MainFrame;
import Views.help;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fuoc
 */
public class SanPhamView extends javax.swing.JFrame {

    /**
     * Creates new form SanPhamView
     */
    public SanPhamView() {
        initComponents();
        this.setLocationRelativeTo(null);
        loadlist();
        loadtablemon();
    }
    MonService monService = new MonServiceImpl();
    LoaiMonService loaiMonService = new LoaiMonServiceImpl();
    int index = 0;
    String itemlistcbo = "";

    void loadlist() {
        DefaultListModel model = new DefaultListModel();
        try {
            itemlistcbo = "";
            List<LoaiMon> list = loaiMonService.getAll();
            cbLM.removeAllItems();
            for (LoaiMon nv : list) {
                model.addElement(nv.getTenLoaiMon() + "         -" + nv.getMaLoaiMon());
                cbLM.addItem(nv.getTenLoaiMon() + "             -" + nv.getMaLoaiMon());
                itemlistcbo += nv.getTenLoaiMon() + "            -" + nv.getMaLoaiMon() + "chiakey";
            }
            listLM.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void editlistmon() {
        String manv = (String) listLM.getSelectedValue();
        String[] key = manv.split("-");
        DefaultTableModel modelbang = (DefaultTableModel) tbsp.getModel();
        modelbang.setRowCount(0);
        try {
            LoaiMon model = loaiMonService.getone(key[1]);
            if (model != null) {
                this.setModellistmon(model);
            }
        } catch (Exception e) {
            alert("Lỗi");
        }
        try {
            List<Mon> list = monService.getIdmenu(key[1]);
            for (Mon nv : list) {
                Object[] row = {
                    nv.getMaLoaiMon(),
                    nv.getTenMon(),
                    nv.getGia(),
                    nv.getHinh()
                };
                modelbang.addRow(row);
            }
        } catch (Exception e) {
            alert("Lỗi");
        }
    }

    void selectImage() {
        JFileChooser FileChooser = new JFileChooser();
        if (FileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            if (ShareHelper.saveLogo(file)) {
                // Hiển thị hình lên form
                BufferedImage img = null;
                try {
                    img = ImageIO.read(new File("E:\\FPT Polytechnic\\CacMonHocLai\\DuAn1\\logos", file.getName()));
                    Image dimg = img.getScaledInstance(lblanh.getWidth(), lblanh.getHeight(),
                            Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(dimg);
                    lblanh.setIcon(imageIcon);
                    lblanh.setToolTipText(file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    void setModellistmon(LoaiMon model) {
        txtmaLoai.setText(model.getMaLoaiMon());
        txtTenLoai.setText(model.getTenLoaiMon());

    }
//Thêm Danh Mục Thực Đơn

    void insertDMM() {
        LoaiMon model = getModelLoaiMon();
        try {
            loaiMonService.insert(model);
            this.loadlist();
            alert("Thêm mới thành công!");
            clear();
        } catch (Exception e) {
            alert("Mã Món Này Đã Có! Hãy Dùng Mã Khác!");
        }
    }

    //Sửa Danh Mục Thực Đơn
    void updateDMM() {
        LoaiMon model = getModelLoaiMon();
        try {
            loaiMonService.update(model);
            this.loadlist();
            alert("Cập nhật thành công!");
            clear();
        } catch (Exception e) {
            alert("Cập nhật thất bại");

        }
    }

    //Xóa Danh Mục Thực Đơn
    void deleteDMM() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa loại món này?")) {
            String manv = txtmaLoai.getText();
            try {
                loaiMonService.delete(manv);
                this.loadlist();
                alert("Xóa thành công!");
                clear();
            } catch (Exception e) {
                alert("Xóa Thất bại.");
            }
        }
    }

    LoaiMon getModelLoaiMon() {
        LoaiMon model = new LoaiMon();
        model.setMaLoaiMon(txtmaLoai.getText());
        model.setTenLoaiMon(txtTenLoai.getText());
        return model;

    }
    //LOAD MÓN LÊN BẢNG

    void loadtablemon() {
        DefaultTableModel model = (DefaultTableModel) tbsp.getModel();
        model.setRowCount(0);
        try {
            List<Mon> list = monService.getAll();
            for (Mon nv : list) {
                String hinh = "";
                if (nv.getHinh().equals("")) {
                    hinh = "Không có hình";
                } else {
                    hinh = "Đã có hình";
                }
                Object[] row = {
                    nv.getMaLoaiMon(),
                    nv.getTenMon(),
                    nv.getGia(),
                    hinh
                };
                model.addRow(row);

            }

        } catch (Exception e) {

        }
    }

    void setModelThucDon(Mon model) {
        double dongia = model.getGia();
        String don = String.valueOf(dongia);
        txttenmon.setText(model.getTenMon());
        txtmamon.setText(model.getMaMon());
        txtgia.setText(don);
        if (model.getHinh() != null) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("E:\\FPT Polytechnic\\CacMonHocLai\\DuAn1\\logos", model.getHinh()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image dimg = img.getScaledInstance(lblanh.getWidth(), lblanh.getHeight(),
                    Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            lblanh.setIcon(imageIcon);
            lblanh.setToolTipText(model.getHinh());
        } else {
            lblanh.setIcon(null);
        }
        String[] key = itemlistcbo.split("chiakey");
        for (int i = 0; i < key.length; i++) {
            if (key[i].indexOf(model.getMaLoaiMon()) != -1) {
                cbLM.setSelectedItem(key[i]);
            }
        }

    }

    Mon getModelThucDon() {
        Mon model = new Mon();
        String key = (String) cbLM.getSelectedItem();
        String[] keys = key.split("-");
        model.setMaMon(txtmamon.getText());
        model.setTenMon(txttenmon.getText());
        model.setHinh(lblanh.getToolTipText());
        model.setGia(Float.valueOf(txtgia.getText()));
        model.setMaLoaiMon(keys[1]);
        return model;

    }
//Thêm Thực Đơn

    void insertmon() {
        Mon model = getModelThucDon();
        try {
            monService.insert(model);
            this.loadtablemon();
            alert("Thêm mới thành công!");
            clear();
        } catch (Exception e) {
            alert("Mã Món Này Đã Có, Vui Lòng Nhập Mã Khác!");
        }
    }

    //Chỉnh sửa Thực Đơn
    void updateMon() {
        Mon model = getModelThucDon();
        try {
            monService.update(model);
            this.loadtablemon();
            alert("Cập nhật thành công!");
            clear();

        } catch (Exception e) {
            alert("Cập nhật thất bại");
        }
    }

    //Xóa Món
    void deleteMon() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa món này?")) {
            String manv = txtmamon.getText();
            try {
                monService.delete(manv);
                this.loadtablemon();
                alert("Xóa thành công!");
                clear();
            } catch (Exception e) {
                alert("Xóa Thất bại.");
            }
        }
    }

    void editMon() {
        try {
            String manv = (String) tbsp.getValueAt(this.index, 0);
            Mon model = monService.getone(manv);
            if (model != null) {
                this.setModelThucDon(model);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void clear() {
        txtmamon.setText("");
        cbLM.setSelectedIndex(0);
        txttenmon.setText("");
        txtgia.setText("");
        txtHat.setText("");
        txtNguyenLieu.setText("");

    }

    void clear1() {
        txtTenLoai.setText("");
        txtmaLoai.setText("");

    }
    boolean ch = false;

    void checkdau() {
        String ten = txtTenLoai.getText();
        String tenkv = txttenmon.getText();
        if (ten.length() > 0) {
            for (int i = 0; i < ten.length(); i++) {
                if (ten.substring(i, i + 1).equals("-")) {
                    ch = true;
                }
            }
        }
        if (tenkv.length() > 0) {
            for (int i = 0; i < tenkv.length(); i++) {
                if (tenkv.substring(i, i + 1).equals("-")) {
                    ch = true;
                }
            }
        }
    }
    help tb;

    void alert(String loi) {
        if (tb != null) {
            tb.setVisible(false);
            tb = new help(loi);
            tb.setVisible(true);
        } else {
            tb = new help(loi);
            tb.setVisible(true);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblback = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listLM = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbsp = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtmaLoai = new javax.swing.JTextField();
        txtTenLoai = new javax.swing.JTextField();
        btnThemLM = new javax.swing.JButton();
        btnSuaLM = new javax.swing.JButton();
        btnXoaLM = new javax.swing.JButton();
        btnLamMoiLM = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnAnh = new javax.swing.JButton();
        lblanh = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtmamon = new javax.swing.JTextField();
        txttenmon = new javax.swing.JTextField();
        cbLM = new javax.swing.JComboBox<>();
        txtgia = new javax.swing.JTextField();
        btnThemMon = new javax.swing.JButton();
        btnXoaMon = new javax.swing.JButton();
        btnSuaMon = new javax.swing.JButton();
        btnLamMoiMon = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtHat = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNguyenLieu = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        cbbSize = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(153, 51, 0));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Quản lý sản phẩm");

        lblback.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblback.setForeground(new java.awt.Color(255, 255, 255));
        lblback.setText("Back");
        lblback.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblbackMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lblback)
                .addGap(427, 427, 427)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblback)
                    .addComponent(jLabel1))
                .addGap(27, 27, 27))
        );

        listLM.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        listLM.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listLM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listLMMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listLM);

        jLabel2.setText("Loại Món");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        tbsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Loại", "Tên món", "Size", "Giá", "Hình"
            }
        ));
        tbsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbspMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbsp);

        jLabel3.setText("Loại Món");

        jLabel4.setText("Mã loại món");

        jLabel5.setText("Tên loại món");

        btnThemLM.setText("Thêm");
        btnThemLM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLMActionPerformed(evt);
            }
        });

        btnSuaLM.setText("Sửa");
        btnSuaLM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaLMActionPerformed(evt);
            }
        });

        btnXoaLM.setText("Xóa");
        btnXoaLM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaLMActionPerformed(evt);
            }
        });

        btnLamMoiLM.setText("Làm mới");
        btnLamMoiLM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiLMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(txtmaLoai)
                            .addComponent(txtTenLoai, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                            .addComponent(jLabel5)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnThemLM, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                            .addComponent(btnXoaLM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLamMoiLM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSuaLM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmaLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemLM)
                    .addComponent(btnSuaLM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoiLM)
                    .addComponent(btnXoaLM))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        btnAnh.setText("Ảnh");
        btnAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(btnAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel7.setText("Mã món");

        jLabel8.setText("Tên món");

        jLabel9.setText("Loại món");

        jLabel10.setText("Giá");

        cbLM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnThemMon.setText("Thêm");
        btnThemMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMonActionPerformed(evt);
            }
        });

        btnXoaMon.setText("Xóa");
        btnXoaMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaMonActionPerformed(evt);
            }
        });

        btnSuaMon.setText("Sửa");
        btnSuaMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaMonActionPerformed(evt);
            }
        });

        btnLamMoiMon.setText("Làm mới");
        btnLamMoiMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiMonActionPerformed(evt);
            }
        });

        jLabel6.setText("Hạt");

        jLabel11.setText("Nguyên liệu");

        txtNguyenLieu.setColumns(20);
        txtNguyenLieu.setRows(5);
        jScrollPane3.setViewportView(txtNguyenLieu);

        jLabel12.setText("Size");

        cbbSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S", "M", "L" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThemMon, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(41, 41, 41)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnXoaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLamMoiMon))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtmamon)
                                .addComponent(txttenmon)
                                .addComponent(txtHat)
                                .addComponent(cbLM, 0, 208, Short.MAX_VALUE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtgia, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(66, 66, 66)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cbLM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtmamon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txttenmon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtHat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoaMon)
                            .addComponent(btnLamMoiMon)
                            .addComponent(btnSuaMon)
                            .addComponent(btnThemMon)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(jLabel3))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemLMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLMActionPerformed

        if (ch == true) {
            alert("Vui lòng không nhập ký tự \"-\" vào mục tên");
        } else {
            insertDMM();
        }
        ch = false;
    }//GEN-LAST:event_btnThemLMActionPerformed

    private void btnSuaLMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaLMActionPerformed
        if (ch == true) {
            alert("Vui lòng không nhập ký tự \"-\" vào mục tên");
        } else {
            updateDMM();
        }
        ch = false;
    }//GEN-LAST:event_btnSuaLMActionPerformed

    private void btnXoaLMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLMActionPerformed
        deleteDMM();
    }//GEN-LAST:event_btnXoaLMActionPerformed

    private void btnLamMoiLMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiLMActionPerformed
        clear1();
    }//GEN-LAST:event_btnLamMoiLMActionPerformed

    private void btnThemMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMonActionPerformed
        if (ch == true) {
            alert("Vui lòng không nhập ký tự \"-\" vào mục tên");
        } else {
            insertmon();
        }
        ch = false;
    }//GEN-LAST:event_btnThemMonActionPerformed

    private void btnSuaMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaMonActionPerformed
        if (ch == true) {
            alert("Vui lòng không nhập ký tự \"-\" vào mục tên");
        } else {
            updateMon();
        }
        ch = false;
    }//GEN-LAST:event_btnSuaMonActionPerformed

    private void btnXoaMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaMonActionPerformed
        deleteMon();
    }//GEN-LAST:event_btnXoaMonActionPerformed

    private void btnLamMoiMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiMonActionPerformed
        clear();
    }//GEN-LAST:event_btnLamMoiMonActionPerformed

    private void tbspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbspMouseClicked
        if (evt.getClickCount() == 1) {
            this.index = tbsp.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                editMon();
            }
        }
    }//GEN-LAST:event_tbspMouseClicked

    private void listLMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listLMMouseClicked
        editlistmon();
    }//GEN-LAST:event_listLMMouseClicked

    private void lblbackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblbackMouseClicked
        MainFrame sp = new MainFrame();
        sp.setVisible(true);
        sp.pack();
        sp.setLocationRelativeTo(null);
        sp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_lblbackMouseClicked

    private void btnAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnhActionPerformed
        selectImage();
    }//GEN-LAST:event_btnAnhActionPerformed

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
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SanPhamView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnh;
    private javax.swing.JButton btnLamMoiLM;
    private javax.swing.JButton btnLamMoiMon;
    private javax.swing.JButton btnSuaLM;
    private javax.swing.JButton btnSuaMon;
    private javax.swing.JButton btnThemLM;
    private javax.swing.JButton btnThemMon;
    private javax.swing.JButton btnXoaLM;
    private javax.swing.JButton btnXoaMon;
    private javax.swing.JComboBox<String> cbLM;
    private javax.swing.JComboBox<String> cbbSize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblanh;
    private javax.swing.JLabel lblback;
    private javax.swing.JList<String> listLM;
    private javax.swing.JTable tbsp;
    private javax.swing.JTextField txtHat;
    private javax.swing.JTextArea txtNguyenLieu;
    private javax.swing.JTextField txtTenLoai;
    private javax.swing.JTextField txtgia;
    private javax.swing.JTextField txtmaLoai;
    private javax.swing.JTextField txtmamon;
    private javax.swing.JTextField txttenmon;
    // End of variables declaration//GEN-END:variables
}
