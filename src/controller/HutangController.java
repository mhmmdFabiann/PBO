package controller;

import DAO.HutangDAO;
import DAO.UserDAO;
import iDAO.IHutangDAO;
import model.Hutang;
import model.TabelModelHutang;
import view.HomePage;
import javax.swing.JOptionPane;
import java.util.List;
import view.Login;
import view.TambahHutang;

public class HutangController {
    HomePage homePage;
    IHutangDAO idaoHutang;
    TambahHutang tmhHutang;
    List<Hutang> listHutang;
    UserDAO daoUser;
    Login frmUser;
    


    public HutangController(HomePage homePage) {
        this.homePage = homePage;
        idaoHutang = new HutangDAO();
    }

    public void isiTable() {
        String nama = frmUser.getTxtNama().getText();
        
        listHutang = idaoHutang.getHutangsByKreditur(nama);
        TabelModelHutang tableModel = new TabelModelHutang(listHutang);
        homePage.getTabelData().setModel(tableModel);
    }
    
    public void tambahHutang(){
        Hutang htg = new Hutang();
        htg.setNamaDebitur(tmhHutang.getNamaDebitur().getText());
        String jumlahHutangStr = tmhHutang.getJumlahHutang().getText();
        try {
            double jumlahHutang = Double.parseDouble(jumlahHutangStr);
            htg.setJumlah_hutang(jumlahHutang);
        } catch (NumberFormatException e) {
            System.out.println("Input jumlah hutang tidak valid");
            // Tindakan lain sesuai kebutuhan, misalnya menampilkan pesan kesalahan
        }
        htg.setKeterangan_hutang(tmhHutang.getKeterangan().getText());
        htg.setTggl_mulai_hutang(tmhHutang.getTgglMulai().getDate());
        htg.setTenggat_hutang(tmhHutang.getTenggat().getDate());
        htg.setId_debitur(tmhHutang.getNikDebitur().getText());
    }
    
}