package iDAO;
import model.Hutang;
import view.Login;
import java.util.List;

public interface IHutangDAO {
    public List<Hutang> getHutangsByKreditur(String nama);
    public boolean insert(Hutang htg);
    public void update(Hutang htg);
    public void delete(int kodeHutang);
    public boolean login(Login login);
}
