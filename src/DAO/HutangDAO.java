package DAO;

import iDAO.IHutangDAO;
import java.util.List;
import model.User;
import model.Hutang;
import view.Login;
import helper.koneksiDB;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;

public class HutangDAO implements IHutangDAO {
    Connection con;
    
    private String strInsert = "INSERT INTO hutang (tggl_mulai_hutang, tenggat_hutang, jumlah_hutang, keterangan_hutang, id_kreditur, id_debitur) VALUES (?, ?, ?, ?, ?, ?);";    
    private String strUpdate = "UPDATE hutang SET tggl_mulai_hutang = ?, tenggat_hutang = ?, jumlah_hutang = ?, keterangan_hutang = ?, id_debitur = ? WHERE kode_hutang = ?;";
    private String strDelete = "DELETE FROM hutang WHERE kode_hutang = ?;";
    private String strLogin = "SELECT * FROM user WHERE Nama = ? AND Password = ?;";

    public HutangDAO() {
        con = koneksiDB.getConnection();
    }

    @Override
    public boolean login(Login loginfrm) {
        User usr;
        boolean result = true;
        PreparedStatement statement;
        try {
            usr = new User();
            statement = con.prepareStatement(strLogin);
            //System.out.println(loginfrm.getTxtNama().getText());
            statement.setString(1, loginfrm.getTxtNama().getText());
            statement.setString(2, new String(loginfrm.getTxtPassword().getPassword()));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                usr.setNik(rs.getString("Nik"));
                usr.setNama(rs.getString("Nama"));
                usr.setPassword(rs.getString("Password"));    
            } else {
                result = false;
            }
        } catch (SQLException e) {
            System.out.println(e); 
            result = false;
        }
        return result;
    }

    @Override
    public List<Hutang> getHutangsByKreditur(String nama) {
        List<Hutang> hutangList = new ArrayList<>();
        String nik = null;

        // Pertama, dapatkan NIK berdasarkan nama pengguna
        try{
            String queryNik = "SELECT Nik FROM user WHERE Nama = ?";
            PreparedStatement stmtNik = con.prepareStatement(queryNik);
            stmtNik.setString(1, nama);
            ResultSet rsNik = stmtNik.executeQuery();
            //System.out.println(rsNik);
            if (rsNik.next()) {
                nik = rsNik.getString("Nik");
                System.out.println(nik);
            }     
            if (nik != null) {
                    try {
                        String queryHutang = "SELECT h.kode_hutang, h.tggl_mulai_hutang, h.tenggat_hutang, h.jumlah_hutang, h.keterangan_hutang, d.nama_debitur " +
                                             "FROM hutang h JOIN debitur d ON h.id_debitur = d.nik_debitur " +
                                             "WHERE h.id_kreditur = ?";
                        PreparedStatement stmtHutang = con.prepareStatement(queryHutang);
                        stmtHutang.setString(1, nik);
                        ResultSet rsHutang = stmtHutang.executeQuery();
                        while (rsHutang.next()) {
                            Hutang hutang = new Hutang();
                            hutang.setKode_hutang(rsHutang.getString("kode_hutang"));
                            hutang.setTggl_mulai_hutang(rsHutang.getDate("tggl_mulai_hutang"));
                            hutang.setTenggat_hutang(rsHutang.getDate("tenggat_hutang"));
                            hutang.setJumlah_hutang(rsHutang.getDouble("jumlah_hutang"));
                            hutang.setKeterangan_hutang(rsHutang.getString("keterangan_hutang"));
                            hutang.setNamaDebitur(rsHutang.getString("nama_debitur"));
                            hutangList.add(hutang);
                        }
                    } catch (SQLException e) {
                        System.out.println("Error getting Hutang: " + e);
                    }
                } else {
                    System.out.println("NIK not found for user: " + nama);
                }
                } catch (SQLException e) {
                    System.out.println("Error getting NIK: " + e);
                    return hutangList;  // Kembalikan daftar kosong jika terjadi kesalahan
                }

        // Jika NIK ditemukan, dapatkan data hutang berdasarkan NIK
        

        return hutangList;
    }

    @Override
    public boolean insert(Hutang htg) { //isinyamodel
        boolean result = true;
        PreparedStatement statement = null;
        try {
            statement.setDate(1, new java.sql.Date(htg.getTggl_mulai_hutang().getTime()));
            statement.setDate(2, new java.sql.Date(htg.getTenggat_hutang().getTime()));
            statement.setDouble(3, htg.getJumlah_hutang());
            statement.setString(4, htg.getKeterangan_hutang());
            statement.setString(5, htg.getId_kreditur());
            statement.setString(6, htg.getId_debitur());
            statement.execute(); 
        } catch (SQLException e) {
            System.out.println("Insert failed: " + e);
            result = false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                System.out.println("Failed to close statement: " + ex);
                result = false;
            }
        }
        return result;
    }

    @Override
    public void update(Hutang htg) {
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(strUpdate);
            statement.setDate(1, new java.sql.Date(htg.getTggl_mulai_hutang().getTime()));
            statement.setDate(2, new java.sql.Date(htg.getTenggat_hutang().getTime()));
            statement.setDouble(3, htg.getJumlah_hutang());
            statement.setString(4, htg.getKeterangan_hutang());
            statement.setString(5, htg.getId_debitur());
            statement.setString(6, htg.getKode_hutang());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update failed: " + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                System.out.println("Failed to close statement: " + ex);
            }
        }
    }

    @Override
    public void delete(int kodeHutang) {
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(strDelete);
            statement.setInt(1, kodeHutang);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                System.out.println("Failed to close statement: " + ex);
            }
        }
    }
}