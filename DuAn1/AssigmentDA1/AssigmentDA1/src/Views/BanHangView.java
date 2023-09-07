/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Models.Ban;
import Models.HDCT;
import Models.HoaDon;
import Models.KhuVuc;
import Models.LoaiMon;
import Models.Mon;
import Service.BanService;
import Service.BanServiceImpl;
import Service.HDCTService;
import Service.HDCTServiceImpl;
import Service.HoaDonService;
import Service.HoaDonServiceImpl;
import Service.KhuVucService;
import Service.KhuVucServiceImpl;
import Service.LoaiMonService;
import Service.LoaiMonServiceImpl;
import Service.MonService;
import Service.MonServiceImpl;
import Ulties.DateHelper;
import Ulties.DialogHelper;
import Ulties.ShareHelper;
import Views.MainFrame;
import Views.MainFrame;
import Views.Ordermon;
import Views.Ordermon;
import Views.gheptachhoadon;
import Views.gheptachhoadon;
import Views.help;
import Views.help;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Font;
import static java.awt.Frame.DEFAULT_CURSOR;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author fuoc
 */
public class BanHangView extends javax.swing.JFrame {

    /**
     * Creates new form BanHangView
     */
    public BanHangView() {
        initComponents();
        loadtablbanorder();
        this.setLocationRelativeTo(null);
    }
    NumberFormat chuyentien = new DecimalFormat("#,###,###");
    MonService monService = new MonServiceImpl();
    BanService banService = new BanServiceImpl();
    HoaDonService hoaDonService = new HoaDonServiceImpl();
    KhuVucService khuVucService = new KhuVucServiceImpl();
    HDCTService hDCTService = new HDCTServiceImpl();
    LoaiMonService loaiMonService = new LoaiMonServiceImpl();
    HDCT cthd = new HDCT();
    Label MaHD = new Label();
    JPanel jps;
    JPanel jplm;
    Label lblnew;
    List<Label> listlabel = new ArrayList<Label>();
    List<JPanel> listjp = new ArrayList<JPanel>();
    List<JButton> buttonlist = new ArrayList<JButton>();
    List<JPanel> jpanlmlist = new ArrayList<JPanel>();
    int row;

    void loadtablbanorder() {
        btnchuyenban.setEnabled(false);
        btnthanhtoan.setEnabled(false);
        btnorder.setEnabled(false);
        btnhuy.setEnabled(false);
        btnGuiBar.setEnabled(false);
        txtNgay.setEnabled(false);
        txtgio.setEnabled(false);
        txtmaban.setEnabled(false);
        txtmahoadon.setEnabled(false);
        txtban.setEnabled(false);
        new Timer(1000, new ActionListener() {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

            public void actionPerformed(ActionEvent e) {
                txtgio.setText(format.format(new Date()));
                txtNgay.setText(date.format(new Date()));
            }
        }).start();
        btnorder.setEnabled(false);
        List<String> abc = new ArrayList<>();
        List<Ban> list = banService.getAll();
        try {
            List<KhuVuc> list1 = khuVucService.getAll();
            List<LoaiMon> listloaimon = loaiMonService.getAll();
            for (LoaiMon lm : listloaimon) {
                jplm = new JPanel();
                jplm.setName(lm.getTenLoaiMon());
                jpanlmlist.add(jplm);
                tabs2.add(jplm);
            }
            for (KhuVuc nv : list1) {
                jps = new JPanel();
                jps.setSize(300, DEFAULT_CURSOR);
                jps.setName(nv.getTenKhuVuc());
                listjp.add(jps);
                abc.add(nv.getTenKhuVuc() + "-" + nv.getMaKhuVuc());

            }
            for (Ban nv : list) {

                JButton button = new JButton();
                button.setText(nv.getTenBan());
                button.setFont(new Font("Times New Roman", Font.PLAIN, 30));
                button.setName(nv.getMaKV() + "-" + nv.getMaBan());
                button.setBackground(Color.white);
                button.setSize(100, 100);
                buttonlist.add(button);
                datban(button);
                button.addActionListener((ae) -> {
                    loadban(nv.getMaKV(), nv.getMaBan(), button);
                });

            }

            for (int i = 0; i < listjp.size(); i++) {
                String[] key = abc.get(i).split("-");
                if (key[0].equals(listjp.get(i).getName())) {
                    for (int a = 0; a < buttonlist.size(); a++) {
                        String keycode = buttonlist.get(a).getName();
                        String[] keycode2 = keycode.split("-");
                        if (keycode2[0].equals(key[1])) {
                            listjp.get(i).add(buttonlist.get(a));
                            tab.add(listjp.get(i));

                        }
                    }
                }
                tab.add(listjp.get(i));
                tab.repaint();
            }
            DefaultTableModel tabe = (DefaultTableModel) tblorder.getModel();
            tabe.setRowCount(0);
            String tenloaimon = tabs2.getTitleAt(0);
            LoaiMon mode = loaiMonService.getten(tenloaimon);
            if (mode != null) {
                List<Mon> listmonan = monService.getIdLoaiMon(mode.getMaLoaiMon());
                for (Mon nv : listmonan) {

                    Object[] row = {
                        nv.getMaMon(),
                        nv.getTenMon(),
                        nv.getGia(),};
                    tabe.addRow(row);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadlaiban() {
        int u = 0;
        List<Ban> listban = banService.getAll();
        List<KhuVuc> listkv = khuVucService.getAll();
        for (KhuVuc kv : listkv) {
            for (Ban nv1 : listban) {
                for (int i = 0; i < listjp.size(); i++) {

                    if (kv.getMaKhuVuc().equals(listjp.get(i).getName())) {
                        for (int a = 0; a < buttonlist.size(); a++) {
                            String keycode = buttonlist.get(a).getName();
                            String[] keycode2 = keycode.split("-");
                            if (keycode2[0].equals(nv1.getMaBan())) {
                                listjp.get(i).remove(buttonlist.get(a));
                                tab.remove(listjp.get(i));

                            }
                        }
                    }
                    tab.remove(listjp.get(i));

                }
            }
            tabs2.removeAll();
        }
        listjp.removeAll(listjp);
        buttonlist.removeAll(buttonlist);
        listlabel.removeAll(listlabel);
        loadtablbanorder();

    }

    void datban(JButton button) {
        listlabel.remove(listlabel);
        List<HoaDon> hd = hoaDonService.getAll();

        String key = button.getName();
        String[] key2 = key.split("-");

        for (HoaDon hd11 : hd) {
            if (hd11.getMaban() == null) {

            } else {
                if (hd11.getMaban().equals(key2[1])) {
                    lblnew = new Label();
                    lblnew.setName(hd11.getMaban() + "-" + hd11.getMahd());
                    listlabel.add(lblnew);
                    button.setBackground(Color.red);
                    button.setForeground(Color.white);

                } else {

                }
            }

        }

    }

    private void loadban(String maKV, String maBan1, JButton button) {

        KhuVuc model = khuVucService.getone(maKV);
        lbltongtien.setText("0");
        txttienkhachdua.setText("0");
        txtban.setText(button.getText());
        txtmahoadon.setText("TRỐNG");
        jPanel7.setVisible(false);
        menuorder.setVisible(true);
        btndatban.setEnabled(true);
        btnorder.setEnabled(false);
        btnhuy.setEnabled(false);
        btnGuiBar.setEnabled(false);
        lbltienthua.setText(" ");
        MaHD.setName(null);
        txtmaban.setText(maBan1);
        btnchuyenban.setEnabled(false);
        btnthanhtoan.setEnabled(false);
        for (int i = 0; i < listlabel.size(); i++) {
            String TTTT = listlabel.get(i).getName();
            String TTTT2 = button.getName();
            String[] XXXX2 = TTTT2.split("-");
            String[] XXXX = TTTT.split("-");
            String mabanbutton = XXXX2[1];
            String mabanlabel = XXXX[0];
            if (mabanlabel.equals(mabanbutton)) {
                MaHD.setName(XXXX[1]);
                txtmahoadon.setText(XXXX[1]);
                menuorder.setVisible(true);
                jPanel7.setVisible(true);
                btndatban.setEnabled(false);
                btnchuyenban.setEnabled(true);
                btnthanhtoan.setEnabled(true);
                btnhuy.setEnabled(true);
                 btnGuiBar.setEnabled(true);
                this.loadHDCT();
            }

        }
        if (txtmahoadon.getText().equals("TRỐNG")) {
            MaHD.setText(null);
        } else {
            MaHD.setText(txtmahoadon.getText());
        }
        this.loadHDCT();

    }

    void editMenu() {
        try {
            String manv = (String) tblorder.getValueAt(this.row, 0);
            Mon model = monService.getone(manv);

            if (model != null) {
                this.setModelMonorder(model);
            }

        } catch (Exception e) {

        }
    }

    void setModelMonorder(Mon model) {

        lblhinh.setText(model.getTenMon());
        lblten.setText(model.getTenMon());
        lblten.setName(model.getMaMon());
        lblgia.setText("" + model.getGia());
        if (model.getHinh() != null) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("E:\\FPT Polytechnic\\CacMonHocLai\\DuAn1\\logos", model.getHinh()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image dimg = img.getScaledInstance(lblhinh.getWidth(), lblhinh.getHeight(),
                    Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            lblhinh.setIcon(imageIcon);
        } else {
            lblhinh.setIcon(null);
        }
        checksanpham();
        getModelHDCT(model.getMaMon());

    }

    void checksanpham() {
        HDCTService hoaDCTService = new HDCTServiceImpl();
        try {
            List<HDCT> hd = hoaDCTService.check(txtmahoadon.getText(), lblten.getName());
            if (hd.size() >= 1) {
                btnorder.setEnabled(false);
            } else if (hd.size() <= 0) {
                btnorder.setEnabled(true);
            }
        } catch (Exception e) {
            System.out.println("lỗi" + txtmahoadon.getText());
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
    Ordermon suamon;
    int keyma;

    void seacrch() {
        DefaultTableModel model = (DefaultTableModel) tblorder.getModel();
        model.setRowCount(0);
        String ten = txtsearch.getText();
        List<Mon> monne = monService.getten(ten);
        for (Mon mone : monne) {
            Object[] row = {
                mone.getMaMon(), mone.getTenMon(), mone.getGia()
            };
            model.addRow(row);
        }
    }

    void loadHDCT() {

        DefaultTableModel model = (DefaultTableModel) tblchitietban.getModel();
        model.setRowCount(0);
        double Tongtien = 0;
        lbltongtien.setText(null);
        try {
            List<HDCT> list = hDCTService.getAll1(MaHD.getName());
            for (HDCT nv : list) {
                List<Mon> tenmon = monService.getIdMon(nv.getMaMon_FK());
                String tenthucpham = null;
                for (Mon mon : tenmon) {
                    tenthucpham = mon.getTenMon();

                }
                Object[] row = {
                    nv.getMaCTHD(),
                    nv.getMaMon_FK(),
                    tenthucpham,
                    nv.getDonGia(),
                    nv.getSoLuong(), (nv.getDonGia() * nv.getSoLuong())
                };
                Tongtien += (nv.getDonGia() * nv.getSoLuong());

                model.addRow(row);
            }
            String tt = String.valueOf(Tongtien);
            String tt2 = tt.replace(".0", "");
            lbltongtien.setText(tt2);
        } catch (Exception e) {
            alert("Lỗi truy vấn dữ liệu!");
        }
    }

    HDCT getModelHDCT(String mamon) {
        int Soluong = 1;
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        List<HDCT> hd = hDCTService.getAll();
        int Mahdct = (hd.size()) + 1;
        String Mahdstr = "K" + Mahdct + "O" + day + month + year + hour + minute + second;
        cthd.setMaCTHD(Mahdstr);
        cthd.setMaHD_FK(MaHD.getName());
        cthd.setDonGia(Float.parseFloat(lblgia.getText()));
        cthd.setSoLuong(Soluong);
        cthd.setMaMon_FK(mamon);
        return cthd;
    }

    void inserthdct() {
        try {
            if (cthd != null) {
                hDCTService.insert(cthd);
            } else {
                alert("Chưa chọn sản phẩm");
                return;
            }
            alert("Đã thêm!");
            this.loadHDCT();
            checksanpham();

        } catch (Exception e) {
            alert("Mã hóa đơn trống! hãy chọn lại bàn.");
            System.out.println(e);
        }
    }

    HoaDon getModelhoadon() {
        try {

            String mahd = txtmahoadon.getText();
            HoaDon model = new HoaDon();
            model.setMaban(txtmaban.getText());
            model.setManv(ShareHelper.USER.getMaNV());
            model.setMahd(mahd);
            model.setNgaylap(DateHelper.toDate(txtNgay.getText()));
            model.setTrangthai(false);
            model.setTongtien(Float.parseFloat(lbltongtien.getText()));
            return model;
        } catch (Exception e) {

        }
        return null;
    }

    boolean db = false;

    HoaDon getModelHD() {
        List<HoaDon> hd = hoaDonService.getAll1();
        int mahd = (hd.size()) + 1;
        String Mahdstr = "HD" + mahd;
        HoaDon model = new HoaDon();
        model.setMahd(Mahdstr);
        model.setMaban(txtmaban.getText());
        model.setTrangthai(true);
        return model;

    }

    void insertHD() {
        HoaDon model = getModelHD();
        try {

            hoaDonService.insert(model);
            btndatban.setEnabled(false);
//            alert("Chọn bàn thành công");
            db = true;

        } catch (Exception e) {
            db = false;
//            alert("Bạn chưa chọn bàn!");
            btndatban.setEnabled(true);

        }
    }

    void updatehdon() {
        DefaultTableModel model1 = (DefaultTableModel) tblchitietban.getModel();
        HoaDon model = getModelhoadon();
        try {
            hoaDonService.update(model);
            btndatban.setEnabled(true);
            loadlaiban();
            alert("Đã Thanh Toán");
            menuorder.setVisible(true);
            model1.setRowCount(0);
            double Tongtien = 0;
            lbltongtien.setText(null);
            try {
                List<HDCT> list = hDCTService.getAll1("TRỐNG");
                for (HDCT nv : list) {
                    List<Mon> tenmon = monService.getIdMon(nv.getMaMon_FK());
                    String tenthucpham = null;
                    for (Mon mon : tenmon) {
                        tenthucpham = mon.getTenMon();

                    }
                    Object[] row = {
                        nv.getMaCTHD(),
                        nv.getMaMon_FK(),
                        tenthucpham,
                        nv.getDonGia(),
                        nv.getSoLuong(), (nv.getDonGia() * nv.getSoLuong())
                    };
                    Tongtien += (nv.getDonGia() * nv.getSoLuong());

                    model1.addRow(row);
                }
                String tt = String.valueOf(Tongtien);
                String tt2 = tt.replace(".0", "");
                lbltongtien.setText(tt2);
                txttienkhachdua.setText(" ");
                lbltienthua.setText(" ");
            } catch (Exception e) {
                alert("Lỗi !");
            }

        } catch (Exception e) {

            alert("Lỗi!");
            e.printStackTrace();
            btndatban.setEnabled(false);

        }
    }

    void pdf() {
        String thongtinchi = "__________________________________\r\n"
                + "Ten mon            So luong            Thanh tien\r\n"
                + "--------------------------------------------------\r\n";

        DefaultTableModel model = (DefaultTableModel) tblchitietban.getModel();
        int a = model.getRowCount();
        for (int i = 0; i < a; i++) {
            String ten = (String) model.getValueAt(i, 2);
            if (ten.length() < 45) {
                for (int u = ten.length(); u < 45 - ten.length(); u++) {
                    ten += " ";
                }
            }
            float gia = (float) model.getValueAt(i, 3);
            int soluong = (int) model.getValueAt(i, 4);
            float thanhtien = (float) model.getValueAt(i, 5);
            thongtinchi += ten + " " + soluong + "            " + thanhtien + "\r\n";
            ;
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("d:/hoadon.pdf"));
            document.open();
            Paragraph p = new Paragraph();
            p.add("          HOA ĐON THANH TOAN\r\n"
                    + "Ngay: " + txtNgay.getText() + "                  "
                    + "Gio: " + txtgio.getText() + "\r\n"
                    + "Ma Hoa Don: " + txtmahoadon.getText() + "            "
                    + "Ban: " + txtban.getText() + "\r\n"
                    + "Nhan vien: " + ShareHelper.USER.getTenNV() + "\r\n"
                    + "------------------------------------------------\r\n"
                    + "            THONG TIN THANH TOAN\r\n"
                    + thongtinchi + "\r\n" + ""
                    + "Tong tien: " + lbltongtien.getText() + "  dong."
                    + "\r\n"
                    + "Tien tra lai khach: " + lbltienthua.getText() + "  dong."
                    + "\r\n"
                    + "------------  CAM ON QUY KHACH ---------");
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.close();
        } catch (Exception e) {
        }
    }

    void pdf1() {
        String thongtinchi = "____________________________\r\n"
                + "Ten mon                 So luong       \r\n"
                + "---------------------------------------\r\n";

        DefaultTableModel model = (DefaultTableModel) tblchitietban.getModel();
        int a = model.getRowCount();
        for (int i = 0; i < a; i++) {
            String ten = (String) model.getValueAt(i, 2);
            if (ten.length() < 45) {
                for (int u = ten.length(); u < 45 - ten.length(); u++) {
                    ten += " ";
                }
            }
            int soluong = (int) model.getValueAt(i, 4);
            thongtinchi += ten + "          " + soluong + "\r\n";
            ;
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("d:/hoadonlam.pdf"));
            document.open();
            Paragraph p = new Paragraph();
            p.add(
                    "------------------------------------------------\r\n"
                    + "            THONG TIN HOA DON \r\n"
                    + "Ngay: " + txtNgay.getText() + "                  "
                    + "Gio: " + txtgio.getText() + "\r\n"
                    + "Ban: " + txtban.getText() + "\r\n"
                    + thongtinchi + "\r\n");
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

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tab = new javax.swing.JTabbedPane();
        btndatban = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnhuy = new javax.swing.JButton();
        menuorder = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tabs2 = new javax.swing.JTabbedPane();
        menu = new javax.swing.JPanel();
        btntim = new javax.swing.JButton();
        txtsearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblorder = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        lblhinh = new javax.swing.JLabel();
        btnorder = new javax.swing.JButton();
        lblten = new javax.swing.JLabel();
        lblgia = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtmahoadon = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtban = new javax.swing.JTextField();
        txtmaban = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblchitietban = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        btnchuyenban = new javax.swing.JButton();
        btnthanhtoan = new javax.swing.JButton();
        txtNgay = new javax.swing.JLabel();
        txtgio = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txttienkhachdua = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbltongtien = new javax.swing.JLabel();
        lbltienthua = new javax.swing.JLabel();
        btnGuiBar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel4.setBackground(new java.awt.Color(153, 51, 0));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Bán hàng");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Back");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel2)
                .addGap(464, 464, 464)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tab.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btndatban.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        btndatban.setText("Vào bàn");
        btndatban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndatbanActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setText("Bàn");

        jLabel13.setText("Có khách");

        jLabel14.setText("Trống");

        jPanel2.setBackground(new java.awt.Color(255, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        btnhuy.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        btnhuy.setText("Hủy bàn");
        btnhuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btndatban)
                                .addGap(18, 18, 18)
                                .addComponent(btnhuy, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addGap(32, 32, 32)
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel15)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btndatban, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnhuy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49))
        );

        menuorder.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setText("Sản phẩm");

        tabs2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabs2MouseClicked(evt);
            }
        });

        menu.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btntim.setText("Tìm");
        btntim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimActionPerformed(evt);
            }
        });

        txtsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtsearchKeyPressed(evt);
            }
        });

        tblorder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã món", "Tên món", "Giá tiền"
            }
        ));
        tblorder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblorderMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblorder);

        btnorder.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnorderActionPerformed(evt);
            }
        });

        lblten.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        lblgia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblten, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnorder))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblgia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblhinh, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblhinh, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(lblten, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblgia, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(btnorder)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btntim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuLayout.createSequentialGroup()
                        .addGap(0, 31, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntim)
                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout menuorderLayout = new javax.swing.GroupLayout(menuorder);
        menuorder.setLayout(menuorderLayout);
        menuorderLayout.setHorizontalGroup(
            menuorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuorderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(177, 177, 177))
            .addGroup(menuorderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs2)
                .addContainerGap())
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        menuorderLayout.setVerticalGroup(
            menuorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuorderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabs2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setText("Mã hóa đơn");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setText("Đang chọn");

        txtmahoadon.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        txtmahoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmahoadonActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setText("Mã bàn");

        txtban.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N

        txtmaban.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N

        tblchitietban.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã HDCT", "Mã món", "Tên món", "Giá tiền", "Số lượng", "Thành tiền"
            }
        ));
        tblchitietban.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblchitietbanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblchitietban);

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("Tổng tiền");

        btnchuyenban.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        btnchuyenban.setText("Tách/Ghép đơn");
        btnchuyenban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnchuyenbanActionPerformed(evt);
            }
        });

        btnthanhtoan.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        btnthanhtoan.setText("Thanh toán");
        btnthanhtoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthanhtoanActionPerformed(evt);
            }
        });

        txtNgay.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtgio.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("VND");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setText("Tiền khách đưa");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel10.setText("Tiền thừa");

        txttienkhachdua.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txttienkhachdua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttienkhachduaActionPerformed(evt);
            }
        });
        txttienkhachdua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttienkhachduaKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel12.setText("VND");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel16.setText("VND");

        lbltongtien.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        lbltienthua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        btnGuiBar.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        btnGuiBar.setText("Gửi bar");
        btnGuiBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuiBarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtmahoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtmaban, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtban, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtgio, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel7)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbltongtien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txttienkhachdua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(lbltienthua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel5)
                            .addComponent(jLabel16))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGuiBar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnchuyenban)
                .addGap(18, 18, 18)
                .addComponent(btnthanhtoan)
                .addGap(34, 34, 34))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtmahoadon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtgio, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txtmaban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(lbltongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txttienkhachdua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbltienthua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel16))
                .addGap(60, 60, 60)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnchuyenban)
                    .addComponent(btnthanhtoan)
                    .addComponent(btnGuiBar))
                .addGap(59, 59, 59))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(menuorder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menuorder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblchitietbanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblchitietbanMouseClicked
        int golfkey = tblchitietban.rowAtPoint(evt.getPoint());
        keyma = tblchitietban.rowAtPoint(evt.getPoint());
        String mahdct = (String) tblchitietban.getValueAt(golfkey, 0);
        String mamon = (String) tblchitietban.getValueAt(golfkey, 1);
        String tenmon = (String) tblchitietban.getValueAt(golfkey, 2);
        float giatien = (float) tblchitietban.getValueAt(golfkey, 3);
        int soluong = (int) tblchitietban.getValueAt(golfkey, 4);
        String mahd = txtmahoadon.getText();

        if (suamon != null) {
            suamon.dispose();
            suamon = new Ordermon(this, mahdct, mamon, tenmon, giatien, soluong, mahd);
            suamon.setVisible(true);
        } else {
            suamon = new Ordermon(this, mahdct, mamon, tenmon, giatien, soluong, mahd);
            suamon.setVisible(true);
        }
    }//GEN-LAST:event_tblchitietbanMouseClicked

    private void btndatbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndatbanActionPerformed
        insertHD();
        if (db == true) {
            btndatban.setEnabled(false);
            loadlaiban();

        }
    }//GEN-LAST:event_btndatbanActionPerformed

    private void btnthanhtoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthanhtoanActionPerformed
        pdf();
        try {

            ByteArrayOutputStream out = QRCode.from(lbltongtien.getText()).to(ImageType.PNG).stream();

            String fName = lbltongtien.getText();
            String path_name = "D:\\";

            FileOutputStream fout = new FileOutputStream(new File(path_name + (fName + ".PNG")));
            fout.write(out.toByteArray());
            fout.flush();
        } catch (Exception e) {
        }
        float tkd = Float.parseFloat(txttienkhachdua.getText());
        float tongTien = Float.parseFloat(lbltongtien.getText());
        try {
            if (tkd == 0) {
                alert("Bạn phải nhập tiền khách đưa");

            } else if (tkd < tongTien) {
                alert("Vui lòng nhập lại tiền");
            } else {

                updatehdon();
            }
        } catch (Exception e) {
            alert("Vui lòng nhập lại số tiền");
        }


    }//GEN-LAST:event_btnthanhtoanActionPerformed
    gheptachhoadon tachghep;
    private void btnchuyenbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnchuyenbanActionPerformed
        String tenban = txtban.getText();
        String mahd = txtmahoadon.getText();
        String maban = txtmaban.getText();
        if (tachghep != null) {
            tachghep.dispose();
            tachghep = new gheptachhoadon(this, tenban, mahd, maban);

        } else {
            tachghep = new gheptachhoadon(this, tenban, mahd, maban);
        }
        tachghep.setVisible(true);
    }//GEN-LAST:event_btnchuyenbanActionPerformed

    private void tblorderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblorderMouseClicked
        if (evt.getClickCount() == 1) {
            this.row = tblorder.rowAtPoint(evt.getPoint());
            if (this.row >= 0) {
                editMenu();

            }
        }
        inserthdct();
    }//GEN-LAST:event_tblorderMouseClicked

    private void tabs2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabs2MouseClicked
        int i = tabs2.getSelectedIndex();
        DefaultTableModel tabe = (DefaultTableModel) tblorder.getModel();
        tabe.setRowCount(0);
        String tenloaimon = tabs2.getTitleAt(i);
        LoaiMon mode = loaiMonService.getten(tenloaimon);
        if (mode != null) {
            List<Mon> listmonan = monService.getIdLoaiMon(mode.getMaLoaiMon());
            for (Mon nv : listmonan) {

                Object[] row = {
                    nv.getMaMon(),
                    nv.getTenMon(),
                    nv.getGia(),
                    nv.getHinh()

                };
                tabe.addRow(row);
            }

        }
    }//GEN-LAST:event_tabs2MouseClicked

    private void btntimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimActionPerformed
        seacrch();
    }//GEN-LAST:event_btntimActionPerformed

    private void txtsearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchKeyPressed
        seacrch();
    }//GEN-LAST:event_txtsearchKeyPressed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        MainFrame sp = new MainFrame();
        sp.setVisible(true);
        sp.pack();
        sp.setLocationRelativeTo(null);
        sp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void txtmahoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmahoadonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmahoadonActionPerformed

    private void txttienkhachduaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttienkhachduaKeyReleased
        try {
            float tiendua = Float.parseFloat(txttienkhachdua.getText());
            float tongtien = Float.parseFloat(lbltongtien.getText());
            if (tiendua - tongtien >= 0) {
                lbltienthua.setText(String.valueOf(chuyentien.format(tiendua - tongtien)));
            }
        } catch (Exception e) {
            txttienkhachdua.setText("");
            lbltienthua.setText("");
        }

    }//GEN-LAST:event_txttienkhachduaKeyReleased

    private void btnhuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhuyActionPerformed

        String mahd = txtmahoadon.getText();
        try {
            hoaDonService.delete(mahd);
            btndatban.setEnabled(true);
            loadlaiban();
            alert("Hủy");
            menuorder.setVisible(true);
            double Tongtien = 0;
            lbltongtien.setText(null);
            txtmahoadon.setText("");
            txtban.setText("");
            String tt = String.valueOf(Tongtien);
            String tt2 = tt.replace(".0", "");
            lbltongtien.setText(tt2);
            txttienkhachdua.setText(" ");
            lbltienthua.setText(" ");

        } catch (Exception e) {

            alert("Lỗi!");
            e.printStackTrace();
            btndatban.setEnabled(false);

        }
    }//GEN-LAST:event_btnhuyActionPerformed

    private void btnorderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnorderActionPerformed
        inserthdct();
    }//GEN-LAST:event_btnorderActionPerformed

    private void txttienkhachduaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttienkhachduaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttienkhachduaActionPerformed

    private void btnGuiBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuiBarActionPerformed
        // TODO add your handling code here:
        pdf1();
    }//GEN-LAST:event_btnGuiBarActionPerformed
    void closemenuorder(Boolean x) {
        txtmahoadon.setText("TRỐNG");
        menuorder.setVisible(x);
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
            java.util.logging.Logger.getLogger(BanHangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BanHangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BanHangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BanHangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BanHangView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuiBar;
    private javax.swing.JButton btnchuyenban;
    private javax.swing.JButton btndatban;
    private javax.swing.JButton btnhuy;
    private javax.swing.JButton btnorder;
    private javax.swing.JButton btnthanhtoan;
    private javax.swing.JButton btntim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblgia;
    private javax.swing.JLabel lblhinh;
    private javax.swing.JLabel lblten;
    private javax.swing.JLabel lbltienthua;
    private javax.swing.JLabel lbltongtien;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel menuorder;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTabbedPane tabs2;
    private javax.swing.JTable tblchitietban;
    private javax.swing.JTable tblorder;
    private javax.swing.JLabel txtNgay;
    private javax.swing.JTextField txtban;
    private javax.swing.JLabel txtgio;
    private javax.swing.JTextField txtmaban;
    private javax.swing.JTextField txtmahoadon;
    private javax.swing.JTextField txtsearch;
    private javax.swing.JTextField txttienkhachdua;
    // End of variables declaration//GEN-END:variables
}
