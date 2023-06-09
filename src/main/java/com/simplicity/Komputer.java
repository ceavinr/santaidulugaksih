package com.simplicity;

import javax.swing.JOptionPane;
import com.gui.Game;

import com.simplicity.AbstractClass.Furniture;

public class Komputer extends Furniture {
    public Komputer() {
        super("Komputer");
        setPanjang(1);
        setLebar(1);
        setHarga(100);
    }

    @Override
    public String getNamaAksi() {
        return "Nubes";
    }

    @Override
    public void aksi(Sim sim) {
        Integer nubesTime = 0;
        Boolean inputValid = false;
        while (!inputValid) {
            String input = "";
            try {
                input = JOptionPane.showInputDialog(null, "Mau nubes berapa lama? (detik): ");
                if (input == null) {
                    // Kalo pencet tombol close
                    return;
                } else {
                    nubesTime = Integer.parseInt(input);
                    inputValid = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Waktu nubes harus berbentuk bilangan bulat, lho!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        Game.getInstance().getCurrentSim().setActiveStatus(getNamaAksi());
        Game.getInstance().mulaiAksi(nubesTime);

        sim.getStats().kurangMood(30);
        sim.getStats().kurangKekenyangan(30);
        sim.getStats().kurangKesehatan(30);
        JOptionPane.showMessageDialog(null,
                "AAAAAAAAAAAAAAAAAAAAAAAAAAA\nAAAAAAAAAAAAAAAAAAAAAAAAAAA\nAAAAAAAAAAAAAAAAAAAAAAAAAAA\nAAAAAAAAAAAAAAAAAAAAAAAAAAA!",
                "Komputer",
                JOptionPane.INFORMATION_MESSAGE);
        Game.getInstance().trackSimsStats(nubesTime);
    }
}
