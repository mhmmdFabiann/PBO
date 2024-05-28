package controller;

import DAO.UserDAO;
import DAO.HutangDAO;
import iDAO.IHutangDAO;
import iDAO.IUserDAO;
import java.util.List;
import model.User;
import view.Login;
import view.SignUp;
import javax.swing.JOptionPane;
import model.Hutang;
import model.TabelModelHutang;
import view.HomePage;
import view.TambahHutang;

public class  UserController {
    Login frmUser;
    SignUp frmSup;
    HomePage hmpg2;
    UserDAO daoUser;
    IUserDAO idaoUser;
    HutangDAO daohutang;

    public UserController(Login frmUser, SignUp frmSup, HomePage hmpg) {
        this.frmUser = frmUser;
        this.frmSup = frmSup;
        this.hmpg2 = hmpg;
        this.daoUser = new UserDAO();
        idaoUser = new UserDAO();
        daohutang = new HutangDAO();
    }

    public void login() {
        String nama = frmUser.getTxtNama().getText();
        String password = new String(frmUser.getTxtPassword().getPassword());

        boolean res = daohutang.login(frmUser);
        
        if (res) {
            JOptionPane.showMessageDialog(null, "Login Success");
            navigateToHome(nama);
        } else {
            JOptionPane.showMessageDialog(null, "Login Failed");
        }
    }

    public void signup() {
        User user = new User(); //model
        user.setNik(frmSup.getTxtNik().getText());
        user.setNama(frmSup.getTxtNama().getText());
        user.setPassword(new String(frmSup.getTxtPassword().getText()));

        boolean res = idaoUser.signup(user);
        if (res)
            JOptionPane.showMessageDialog(null, "Signup Success");
        else
            JOptionPane.showMessageDialog(null, "Signup Failed");
    }

    public void reset() {
        frmSup.getTxtNik().setText("");
        frmSup.getTxtNama().setText("");
        frmSup.getTxtPassword().setText("");
    }

    private void navigateToHome(String nama) {
        HomePage hp = new HomePage();
        hp.setUser(nama);
        hp.setVisible(true);
        hp.pack();
        hp.setLocationRelativeTo(null);
        frmUser.dispose();
    }
    
    public void toSignup(){
        SignUp sign = new SignUp();
        sign.setVisible(true);
        sign.pack();
        sign.setLocationRelativeTo(null);
        frmUser.dispose();
    }
    
    public void toLogin(){
        Login log = new Login();
        log.setVisible(true);
        log.pack();
        log.setLocationRelativeTo(null);
        frmSup.dispose();
    }
    
    public void loadHutangData(HomePage homePage, String nama) {
        List<Hutang> hutangList = daohutang.getHutangsByKreditur(nama);
        TabelModelHutang model = new TabelModelHutang(hutangList);
        homePage.getTabelData().setModel(model);
    }

    public void toTambahHutang(){
        hmpg2.toBack();
        TambahHutang tbhtg = new TambahHutang();
        tbhtg.setVisible(true);
        tbhtg.toFront();
    }
    
    
}
