package br.com.alura.leilao.model;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import br.com.alura.leilao.builder.LeilaoBuilder;
import br.com.alura.leilao.exception.LanceMenorQueOUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LeilaoTest {

    public static final double DELTA = 0.0001;
    private final Leilao CONSOLE = new Leilao("Console");
    private final Usuario ALEX = new Usuario("Alex");
    private final Usuario FRAN = new Usuario("Fran");

    @Test
    public void deve_DevolveDescricao_QuandoRecebeDescricao_() {
        String descricaoDevolvida = CONSOLE.getDescricao();
//        assertEquals("Console", descricaoDevolvida);
        assertThat(descricaoDevolvida, is(equalTo("Console")));
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        double maiorLance = CONSOLE.getMaiorLance();
//        assertEquals(200.0, maiorLance, DELTA);
        assertThat(maiorLance, closeTo(200.0, DELTA));
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        CONSOLE.propoe(new Lance(ALEX, 100.0));
        CONSOLE.propoe(new Lance(FRAN, 200.0));
        double maiorLance = CONSOLE.getMaiorLance();
        assertEquals(200.0, maiorLance, DELTA);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        double menorLance = CONSOLE.getMenorLance();
        assertEquals(200.0, menorLance, DELTA);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        CONSOLE.propoe(new Lance(ALEX, 100.0));
        CONSOLE.propoe(new Lance(FRAN, 200.0));
        double menorLance = CONSOLE.getMenorLance();
        assertEquals(100.0, menorLance, DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeExatosTresLances() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(FRAN, 300.0));
        CONSOLE.propoe(new Lance(ALEX, 400.0));
        List<Lance> tresMaioredsLances = CONSOLE.tresMaioresLances();
//        assertEquals(3, tresMaioredsLances.size());
//        assertThat(tresMaioredsLances, hasSize(equalTo(3)));
////        assertEquals(400.0, tresMaioredsLances.get(0).getValor(), DELTA);
////        assertThat(tresMaioredsLances, hasItem(new Lance(ALEX, 400.0)));
////        assertEquals(300.0, tresMaioredsLances.get(1).getValor(), DELTA);
////        assertEquals(200.0, tresMaioredsLances.get(2).getValor(), DELTA);
//        assertThat(tresMaioredsLances,
//                contains(new Lance(ALEX, 400.0),
//                        new Lance(FRAN, 300.0),
//                        new Lance(ALEX, 200.0)));
        assertThat(tresMaioredsLances,
                both(Matchers.<Lance>hasSize(equalTo(3)))
                        .and(contains(new Lance(ALEX, 400.0),
                                new Lance(FRAN, 300.0),
                                new Lance(ALEX, 200.0))));
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoNaoRecebeLances() {
        List<Lance> tresMaioredsLances = CONSOLE.tresMaioresLances();
        assertEquals(0, tresMaioredsLances.size());
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        List<Lance> tresMaioredsLances = CONSOLE.tresMaioresLances();
        assertEquals(1, tresMaioredsLances.size());
        assertEquals(200.0, tresMaioredsLances.get(0).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasDoisLance() {
        CONSOLE.propoe(new Lance(ALEX, 300.0));
        CONSOLE.propoe(new Lance(FRAN, 400.0));
        List<Lance> tresMaioredsLances = CONSOLE.tresMaioresLances();
        assertEquals(2, tresMaioredsLances.size());
        assertEquals(400.0, tresMaioredsLances.get(0).getValor(), DELTA);
        assertEquals(300.0, tresMaioredsLances.get(1).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeMaisDeTresLances() {
        CONSOLE.propoe(new Lance(ALEX, 100.0));
        CONSOLE.propoe(new Lance(FRAN, 200.0));
        CONSOLE.propoe(new Lance(ALEX, 300.0));
        CONSOLE.propoe(new Lance(FRAN, 400.0));
        final List<Lance> tresMaioredsLancesParaQuatroLances = CONSOLE.tresMaioresLances();
        assertEquals(3, tresMaioredsLancesParaQuatroLances.size());
        assertEquals(400.0, tresMaioredsLancesParaQuatroLances.get(0).getValor(), DELTA);
        assertEquals(300.0, tresMaioredsLancesParaQuatroLances.get(1).getValor(), DELTA);
        assertEquals(200.0, tresMaioredsLancesParaQuatroLances.get(2).getValor(), DELTA);
        CONSOLE.propoe(new Lance(ALEX, 700.0));
        final List<Lance> tresMaioredsLancesParaCincoLances = CONSOLE.tresMaioresLances();
        assertEquals(3, tresMaioredsLancesParaCincoLances.size());
        assertEquals(700.0, tresMaioredsLancesParaCincoLances.get(0).getValor(), DELTA);
        assertEquals(400.0, tresMaioredsLancesParaCincoLances.get(1).getValor(), DELTA);
        assertEquals(300.0, tresMaioredsLancesParaCincoLances.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverValorZeroParaMaiorLance_QuandoNaoTiverLances() {
        double maiorLance = CONSOLE.getMaiorLance();
        assertEquals(0.0, maiorLance, DELTA);
    }

    @Test
    public void deve_DevolverValorZeroParaMenorLance_QuandoNaoTiverLances() {
        double menorLance = CONSOLE.getMenorLance();
        assertEquals(0.0, menorLance, DELTA);
    }

    @Test(expected = LanceMenorQueOUltimoLanceException.class)
    public void deve_LancarException_QuandoReceberLanceMenorQueMaiorLance() {
//        exception.expect(LanceMenorQueOUltimoLanceException.class);
        CONSOLE.propoe(new Lance(ALEX, 500.0));
        CONSOLE.propoe(new Lance(FRAN, 400.0));
    }

    @Test(expected = LanceSeguidoDoMesmoUsuarioException.class)
    public void deve_LancarException_QuandoForOMesmoUsuarioDoUltimoLance() {
        CONSOLE.propoe(new Lance(ALEX, 500.0));
        CONSOLE.propoe(new Lance(new Usuario("Alex"), 600.0));
    }

    @Test(expected = UsuarioJaDeuCincoLancesException.class)
    public void deve_LancarException_QuandoUsuarioDerMaisDeCincoLances() {
        final Leilao console = new LeilaoBuilder("Console")
                .lance(ALEX, 100.0)
                .lance(FRAN, 200.0)
                .lance(ALEX, 300.0)
                .lance(FRAN, 400.0)
                .lance(ALEX, 500.0)
                .lance(FRAN, 600.0)
                .lance(ALEX, 700.0)
                .lance(FRAN, 800.0)
                .lance(ALEX, 900.0)
                .lance(FRAN, 1000.0)
                .lance(ALEX, 1100.0)
                .build();
    }

}
