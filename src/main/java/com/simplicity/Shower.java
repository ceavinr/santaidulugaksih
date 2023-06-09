package com.simplicity;

import javax.swing.JOptionPane;
import com.gui.Game;
import com.simplicity.AbstractClass.Furniture;

public class Shower extends Furniture {
    public Shower() {
        super("Shower");
        setPanjang(1);
        setLebar(1);
        setHarga(75);
    }

    @Override
    public String getNamaAksi() {
        return "Mandi";
    }

    @Override
    public void aksi(Sim sim) {
        Integer showerTime = 0;
        while (showerTime < 10) {
            String input = "";
            try {
                input = JOptionPane.showInputDialog(null, "Masukkan waktu mandi (detik): ");
                if (input == null) {
                    // Kalo pencet tombol close
                    return;
                } else {
                    showerTime = Integer.parseInt(input);
                    if (showerTime < 10) {
                        JOptionPane.showMessageDialog(null, "Mandi gak boleh cepet-cepet! Minimal 10 detik", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Waktu mandi harus berbentuk bilangan bulat, lho!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        Game.getInstance().getCurrentSim().setActiveStatus(getNamaAksi());
        Game.getInstance().mulaiAksi(showerTime);

        if (showerTime > 30) {
            sim.getStats().tambahKesehatan(30);
            sim.getStats().kurangKesehatan((showerTime - 30) / 10 * 5);
        } else {
            sim.getStats().tambahKesehatan(showerTime / 10 * 10);
        }
        sim.getStats().tambahMood(showerTime / 10 * 10);
        sim.getStats().kurangKekenyangan(showerTime / 10 * 10);
        JOptionPane.showMessageDialog(null,
                "Seger!",
                "Shower",
                JOptionPane.INFORMATION_MESSAGE);
        Game.getInstance().trackSimsStats(showerTime);
    }
}
