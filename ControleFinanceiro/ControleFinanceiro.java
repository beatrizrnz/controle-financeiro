import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControleFinanceiro {

    private final ArrayList<Lancamento> lancamentos;
    private int proximoId;

    public ControleFinanceiro() {
        lancamentos = new ArrayList<>();
        proximoId = 1;
    }

    public void adicionarLancamento(Lancamento lancamento) {
        lancamentos.add(lancamento);
    }

    public int gerarId() {
        return proximoId++;
    }

    public Lancamento buscarPorId(int id) {

        for (Lancamento lancamento : lancamentos) {

            if (lancamento.getId() == id) {
                return lancamento;
            }
        }

        return null;
    }

    public void listarLancamentos() {

        if (lancamentos.isEmpty()) {
            System.out.println("Nenhum lancamento cadastrado.");
            return;
        }

        for (Lancamento lancamento : lancamentos) {
            System.out.println(lancamento);
        }
    }

    public boolean excluirLancamento(int id) {

        Lancamento lancamento = buscarPorId(id);

        if (lancamento != null) {
            lancamentos.remove(lancamento);
            return true;
        }

        return false;
    }

    public boolean alterarLancamento(int id, String descricao,
            BigDecimal valor, LocalDate data,
            TipoLancamento tipo, Categoria categoria) {

        Lancamento lancamento = buscarPorId(id);

        if (lancamento != null) {

            lancamento.setDescricao(descricao);
            lancamento.setValor(valor);
            lancamento.setData(data);
            lancamento.setTipo(tipo);
            lancamento.setCategoria(categoria);

            return true;
        }

        return false;
    }

    public ArrayList<Lancamento> filtrarPorTipo(TipoLancamento tipo) {

        ArrayList<Lancamento> resultado = new ArrayList<>();

        for (Lancamento lancamento : lancamentos) {

            if (lancamento.getTipo() == tipo) {
                resultado.add(lancamento);
            }
        }

        return resultado;
    }

    public ArrayList<Lancamento> consultarPorPeriodo(
            LocalDate dataInicial, LocalDate dataFinal) {

        ArrayList<Lancamento> resultado = new ArrayList<>();

        for (Lancamento lancamento : lancamentos) {

            if (!lancamento.getData().isBefore(dataInicial)
                    && !lancamento.getData().isAfter(dataFinal)) {

                resultado.add(lancamento);
            }
        }

        return resultado;
    }

    public BigDecimal calcularTotalReceitas() {

        BigDecimal total = BigDecimal.ZERO;

        for (Lancamento lancamento : lancamentos) {

            if (lancamento.getTipo() == TipoLancamento.RECEITA) {
                total = total.add(lancamento.getValor());
            }
        }

        return total;
    }

    public BigDecimal calcularTotalDespesas() {

        BigDecimal total = BigDecimal.ZERO;

        for (Lancamento lancamento : lancamentos) {

            if (lancamento.getTipo() == TipoLancamento.DESPESA) {
                total = total.add(lancamento.getValor());
            }
        }

        return total;
    }

    public BigDecimal calcularSaldo() {

        return calcularTotalReceitas().subtract(calcularTotalDespesas());
    }
}