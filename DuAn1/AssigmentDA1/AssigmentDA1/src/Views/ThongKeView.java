/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Models.HDCT;
import Models.HoaDon;
import Models.Mon;
import Service.HDCTService;
import Service.HDCTServiceImpl;
import Service.MonService;
import Service.MonServiceImpl;
import Service.ThongKeService;
import Service.ThongKeServiceImpl;
import Ulties.ShareHelper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.CardLayout;
import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author fuoc
 */
public class ThongKeView extends javax.swing.JFrame {

    ThongKeService thongKeService = new ThongKeServiceImpl();
    HDCTService hoaCTService = new HDCTServiceImpl();
    MonService monService = new MonServiceImpl();
    DefaultTableModel model;

    public ThongKeView() {

        initComponents();
        this.setLocationRelativeTo(null);
        loadhdhomnay();
        loadcbo();
        loadchar();
        model = (DefaultTableModel) tbhdhn.getModel();
        txttonghoadonhn.disable();
        txttongtienhomnay.disable();
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

    void loadhdhomnay() {
        DefaultTableModel model = (DefaultTableModel) tbhdhn.getModel();
        model.setRowCount(0);
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        List<HoaDon> tonghnay = thongKeService.getdthn(day, month, year);
        for (HoaDon hd : tonghnay) {
            txttonghoadonhn.setText("" + hd.getSoluong());
            txttongtienhomnay.setText("" + hd.getTongtien());
        }
        List<HoaDon> li = thongKeService.gethoadonhomnay(day, month, year);
        for (HoaDon hd : li) {
            lblngaylap.setText("" + hd.getNgaylap());
            Object[] row = {
                hd.getMahd(),
                hd.getManv(),
                hd.getNgaylap(),
                hd.getTongtien(),
                hd.getTrangthai() == false ? " Đã thanh toán" : ""
            };
            model.addRow(row);
        }
    }

    void xemcthd(String mahd) {
        DefaultTableModel m1 = (DefaultTableModel) tbhdct.getModel();
        m1.setRowCount(0);
        List<HDCT> hdct = hoaCTService.getById(mahd);
        for (HDCT hd : hdct) {
            List<Mon> m = monService.getIdMon(hd.getMaMon_FK());
            for (Mon mo : m) {
                Object[] row = {hd.getMaCTHD(), mo.getTenMon(), mo.getGia(), hd.getSoLuong(), (mo.getGia() * hd.getSoLuong())};
                m1.addRow(row);
            }
        }
    }

    void loadcbo() {
        //load ngày
        cbngay1.removeAllItems();
        cbngay2.removeAllItems();
        cbthang1.removeAllItems();
        cbthang2.removeAllItems();
        cbnam1.removeAllItems();
        cbnam2.removeAllItems();
        for (int i = 0; i < 31; i++) {
            if (i < 9) {
                cbngay1.addItem("0" + (i + 1));
                cbngay2.addItem("0" + (i + 1));
            } else {
                cbngay1.addItem("" + (i + 1));
                cbngay2.addItem("" + (i + 1));
            }
        }
        for (int i = 0; i < 12; i++) {
            if (i < 9) {
                cbthang1.addItem("0" + (i + 1));
                cbthang2.addItem("0" + (i + 1));
            } else {
                cbthang1.addItem("" + (i + 1));
                cbthang2.addItem("" + (i + 1));
            }
        }
        for (int i = 22; i < 30; i++) {
            cbnam1.addItem("20" + (i));
            cbnam2.addItem("20" + i);
        }
    }

    void loadchar() {
        DefaultCategoryDataset dct = new DefaultCategoryDataset();
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        for (int u = 1; u < 13; u++) {
            List<HoaDon> hf = thongKeService.getall(u, year);
            for (HoaDon hd : hf) {
                dct.setValue(hd.getTongtien(), "Tháng " + u, "");
            }
        }
        JFreeChart jtc = ChartFactory.createBarChart("Bảng Doanh Thu Tháng (Năm" + year + ")", "Ghi Chú", "Doanh Thu (vnđ)", dct, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot pl = jtc.getCategoryPlot();
        pl.setRangeGridlinePaint(Color.green);
        ChartPanel chartPanel = new ChartPanel(jtc);
    }

    void pdf() {
//        DefaultTableModel model = (DefaultTableModel) tbhdhn.getModel();
//        int a = model.getRowCount();
//        for (int i = 0; i < a; i++) {
//            String ten = (String) model.getValueAt(i, 2);
//            if (ten.length() < 45) {
//                for (int u = ten.length(); u < 45 - ten.length(); u++) {
//                    ten += " ";
//                }
//            }
//            float gia = (float) model.getValueAt(i, 3);
//            int soluong = (int) model.getValueAt(i, 4);
//            float thanhtien = (float) model.getValueAt(i, 5);
//        
//            
//        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("d:/thongke.pdf"));
            document.open();
            Paragraph p = new Paragraph();
            p.add("     THONG TIN HOA DON\r\n"
                    + "NHAN VIEN: " + ShareHelper.USER.getTenNV() + "\r\n"
                    + "------------------------------------------------\r\n"
                    + "TONG HOA DON: " + txttonghoadonhn.getText() + "  dong."
                    + "\r\n"
                    + "DOANH THU: " + txttongtienhomnay.getText() + "  dong."
                    + "\r\n");

            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.close();
        } catch (Exception e) {
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbhdhn = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txttonghoadonhn = new javax.swing.JTextField();
        txttongtienhomnay = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnguimail = new javax.swing.JButton();
        lblngaylap = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        xuatPDF = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbhdct = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbthongtinhd = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbthongtinhd1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        cbngay1 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbthang1 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cbnam1 = new javax.swing.JComboBox<>();
        btntimkiem = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cbngay2 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cbthang2 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cbnam2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(153, 51, 0));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Thống kê");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Back");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel6)
                .addGap(503, 503, 503)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tbhdhn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Nhân viên", "Ngày thanh toán", "Tổng tiền", "Trạng thái"
            }
        ));
        tbhdhn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbhdhnMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbhdhn);

        jLabel1.setText("Tổng hóa đơn hôm nay");

        jLabel2.setText("Tổng doanh thu hôm nay");

        txttonghoadonhn.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        txttongtienhomnay.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        btnguimail.setText("Gửi mail");
        btnguimail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguimailActionPerformed(evt);
            }
        });

        lblngaylap.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N

        jButton1.setText("Xuất Excel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        xuatPDF.setText("Xuất PDF");
        xuatPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xuatPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(xuatPDF)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnguimail)
                .addGap(50, 50, 50))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(26, 26, 26)
                            .addComponent(txttongtienhomnay, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txttonghoadonhn, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblngaylap, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lblngaylap, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txttonghoadonhn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(txttongtienhomnay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnguimail)
                            .addComponent(jButton1)
                            .addComponent(xuatPDF))
                        .addContainerGap())))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tbhdct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HDCT", "Món ", "Giá", "Số lượng", "Thành tiền"
            }
        ));
        jScrollPane2.setViewportView(tbhdct);

        tbthongtinhd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Tổng số hóa đơn", "Tổng tiền"
            }
        ));
        jScrollPane4.setViewportView(tbthongtinhd);

        jLabel13.setText("Thông tin hóa đơn");

        tbthongtinhd1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Nhân viên", "Ngày thanh toán", "Tổng tiền"
            }
        ));
        tbthongtinhd1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbthongtinhd1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbthongtinhd1);

        jLabel7.setText("Ngày");

        cbngay1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Tháng");

        cbthang1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setText("Năm");

        cbnam1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btntimkiem.setText("Tìm");
        btntimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimkiemActionPerformed(evt);
            }
        });

        jLabel8.setText("Ngày");

        cbngay2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Tháng");

        cbthang2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setText("Năm");

        cbnam2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(jLabel8))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel13)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(90, 90, 90)
                                    .addComponent(jLabel7))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cbngay2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(cbthang2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(cbnam2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cbngay1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(cbthang1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(cbnam1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(btntimkiem)
                        .addGap(39, 39, 39)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbngay1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(cbthang1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbnam1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btntimkiem)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbthang2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(cbnam2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(cbngay2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setText("Thông tin doanh thu hôm nay");

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 263, Short.MAX_VALUE)
        );

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel15.setText("Thông tin hóa đơn chi tiết");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(jLabel4)
                .addGap(311, 311, 311)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbhdhnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbhdhnMouseClicked
        int row = tbhdhn.getSelectedRow();
        String ma = (String) tbhdhn.getValueAt(row, 0);
        xemcthd(ma);
    }//GEN-LAST:event_tbhdhnMouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        MainFrame sp = new MainFrame();
        sp.setVisible(true);
        sp.pack();
        sp.setLocationRelativeTo(null);
        sp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void btntimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimkiemActionPerformed
        String c1 = (String) cbthang1.getSelectedItem() + "/" + (String) cbngay1.getSelectedItem() + "/" + (String) cbnam1.getSelectedItem();
        String c2 = (String) cbthang2.getSelectedItem() + "/" + (String) cbngay2.getSelectedItem() + "/" + (String) cbnam2.getSelectedItem();
        System.out.println(c1);
        try {
            List<HoaDon> hdtt = thongKeService.getngay(c1, c2);
            DefaultTableModel m2 = (DefaultTableModel) tbthongtinhd1.getModel();
            m2.setRowCount(0);
            double TongTien = 0;
            int Soluong = 0;
            DefaultTableModel m1 = (DefaultTableModel) tbthongtinhd.getModel();
            m1.setRowCount(0);
            for (HoaDon hd : hdtt) {
                Object[] row = {
                    hd.getMahd(), hd.getManv(), hd.getNgaylap(), hd.getTongtien()
                };
                Soluong += 1;
                TongTien += hd.getTongtien();
                m2.addRow(row);
            }

            Object[] row = {Soluong, TongTien};
            m1.addRow(row);
        } catch (Exception e) {
            alert("Xin lỗi bạn đã chọn sai ngày!");
        }
    }//GEN-LAST:event_btntimkiemActionPerformed

    private void tbthongtinhd1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbthongtinhd1MouseClicked
        int row = tbthongtinhd1.getSelectedRow();
        String ma = (String) tbthongtinhd1.getValueAt(row, 0);
        xemcthd(ma);
    }//GEN-LAST:event_tbthongtinhd1MouseClicked

    private void btnguimailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguimailActionPerformed

        try {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", 587);
            final String account = "minhhieunguyentrong@gmail.com";
            final String pass = "gcxywzfnidownqmp";
            Session s = Session.getInstance(p, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(account, pass);
                }
            });
            String from = "minhhieunguyentrong@gmail.com";
            String to = "minhhieunguyentrong@gmail.com";
            String sub = "BÁO CÁO DOANH THU HÔM NAY" + lblngaylap.getText();
            String mess = "NGÀY " + lblngaylap.getText() + "\n" + "\n" + " TỔNG HÓA ĐƠN:  " + txttonghoadonhn.getText() + "\n" + "\n"
                    + " DOANH THU: " + txttongtienhomnay.getText() + " VNĐ";

            Message msg = new MimeMessage(s);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(sub);
            msg.setText(mess);
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnguimailActionPerformed

    private void xuatPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xuatPDFActionPerformed
        // TODO add your handling code here:
        pdf();
    }//GEN-LAST:event_xuatPDFActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser excelFileChooser = new JFileChooser("E:\\FPT Polytechnic\\CacMonHocLai\\DuAn1\\Excel");
        excelFileChooser.setDialogTitle("SAVE AS");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showSaveDialog(null);

        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            FileOutputStream excelOuput = null;
            BufferedOutputStream excelBou = null;
            XSSFWorkbook exceJTableExporter = null;
            Cell cell = null;

            try {
                
                
                exceJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = exceJTableExporter.createSheet("Thống Kê");
                Row row = excelSheet.createRow(0);
                for (int k = 0; k < model.getColumnCount(); k++) {
                    cell = row.createCell(k);
                    cell.setCellValue(model.getColumnName(k));
                }
                
                for (int i = 0; i < model.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(model.getValueAt(i, j).toString());
                    }
                }

                

                excelOuput = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBou = new BufferedOutputStream(excelOuput);
                exceJTableExporter.write(excelBou);
                JOptionPane.showMessageDialog(null, "Exporterd Successfull !!!");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ThongKeView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ThongKeView.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (excelBou != null) {
                        excelBou.close();
                    }
                    if (excelOuput != null) {
                        excelOuput.close();
                    }
                    if (exceJTableExporter != null) {
                        exceJTableExporter.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ThongKeView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }


    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ThongKeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongKeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongKeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongKeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThongKeView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnguimail;
    private javax.swing.JButton btntimkiem;
    private javax.swing.JComboBox<String> cbnam1;
    private javax.swing.JComboBox<String> cbnam2;
    private javax.swing.JComboBox<String> cbngay1;
    private javax.swing.JComboBox<String> cbngay2;
    private javax.swing.JComboBox<String> cbthang1;
    private javax.swing.JComboBox<String> cbthang2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblngaylap;
    private javax.swing.JTable tbhdct;
    private javax.swing.JTable tbhdhn;
    private javax.swing.JTable tbthongtinhd;
    private javax.swing.JTable tbthongtinhd1;
    private javax.swing.JTextField txttonghoadonhn;
    private javax.swing.JTextField txttongtienhomnay;
    private javax.swing.JButton xuatPDF;
    // End of variables declaration//GEN-END:variables
}
