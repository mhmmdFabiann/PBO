package model;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

public class TabelModelHutang extends AbstractTableModel {
    private List<Hutang> lstHutang;

    public TabelModelHutang(List<Hutang> lstHutang) {
        this.lstHutang = lstHutang != null ? lstHutang : new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return this.lstHutang.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Kode Hutang";
            case 1:
                return "Nama Debitur";
            case 2:
                return "Tanggal Mulai Hutang";
            case 3:
                return "Tenggat Hutang";
            case 4:
                return "Jumlah Hutang";
            case 5:
                return "Keterangan Hutang";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Hutang hutang = lstHutang.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return hutang.getKode_hutang();
            case 1:
                return hutang.getNamaDebitur();
            case 2:
                return hutang.getTggl_mulai_hutang();
            case 3:
                return hutang.getTenggat_hutang();
            case 4:
                return hutang.getJumlah_hutang();
            case 5:
                return hutang.getKeterangan_hutang();
            default:
                return null;
        }
    }

    public void setRowCount(int rowCount) {
        if (rowCount < 0) {
            throw new IllegalArgumentException("Row count cannot be negative");
        }
        int currentSize = this.lstHutang.size();
        if (rowCount < currentSize) {
            for (int i = currentSize - 1; i >= rowCount; i--) {
                this.lstHutang.remove(i);
            }
        } else {
            for (int i = currentSize; i < rowCount; i++) {
                this.lstHutang.add(new Hutang()); // Add default instances or handle appropriately
            }
        }
        fireTableDataChanged();
    }

    public void addRow(Hutang hutang) {
        this.lstHutang.add(hutang);
        fireTableRowsInserted(this.lstHutang.size() - 1, this.lstHutang.size() - 1);
    }
}
