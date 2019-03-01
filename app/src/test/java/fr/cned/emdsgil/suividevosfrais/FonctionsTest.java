package fr.cned.emdsgil.suividevosfrais;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import fr.cned.emdsgil.suividevosfrais.Outils.Fonctions;

public class FonctionsTest {
    @Test
    public void estMoisActuel() throws Exception{
        assertEquals(Fonctions.estMoisActuel("201903"), true);
    }

    @Test
    public void getMoisPrecedent() throws Exception{
        assertEquals(Fonctions.getMoisPrecedent("201903"),"201902");
        assertEquals(Fonctions.getMoisPrecedent("201901"),"201812");
        assertEquals(Fonctions.getMoisPrecedent("201911"),"201910");
    }

    @Test
    public void getFormatMois() throws Exception{
        assertEquals(Fonctions.getFormatMois(2019,1), "201901");
    }
}
