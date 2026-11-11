import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ControleFinanceiro controle = new ControleFinanceiro();

    private static final DateTimeFormatter formatoData =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        int opcao;

        do {

            System.out.println("\n===== CONTROLE FINANCEIRO =====");
            System.out.println("1 - Cadastrar lancamento");
            System.out.println("2 - Listar lancamentos");
            System.out.println("3 - Consultar por ID");
            System.out.println("4 - Alterar lancamento");
            System.out.println("5 - Excluir lancamento");
            System.out.println("6 - Filtrar por tipo");
            System.out.println("7 - Consultar por periodo");
            System.out.println("8 - Exibir resumo financeiro");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> cadastrarLancamento();
                case 2 -> controle.listarLancamentos();
                case 3 -> consultarLancamento();
                case 4 -> alterarLancamento();
                case 5 -> excluirLancamento();
                case 6 -> filtrarPorTipo();
                case 7 -> consultarPorPeriodo();
                case 8 -> exibirResumo();
                case 0 -> System.out.println("Programa encerrado.");
                default -> System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    private static void cadastrarLancamento() {

        System.out.print("Descricao: ");
        String descricao = scanner.nextLine();

        while (descricao.trim().isEmpty()) {
            System.out.print("Descricao nao pode ser vazia. Digite novamente: ");
            descricao = scanner.nextLine();
        }

        BigDecimal valor = lerValor();

        LocalDate data = lerData();

        TipoLancamento tipo = lerTipo();

        Categoria categoria = lerCategoria();

        int id = controle.gerarId();

        Lancamento lancamento = new Lancamento(
                id, descricao, valor, data, tipo, categoria);

        controle.adicionarLancamento(lancamento);

        System.out.println("Lancamento cadastrado com sucesso!");
    }

    private static void consultarLancamento() {

        System.out.print("Digite o ID: ");
        int id = lerInteiro();

        Lancamento lancamento = controle.buscarPorId(id);

        if (lancamento != null) {
            System.out.println(lancamento);
        } else {
            System.out.println("Lancamento nao encontrado.");
        }
    }

    private static void alterarLancamento() {

        System.out.print("Digite o ID do lancamento: ");
        int id = lerInteiro();

        Lancamento lancamento = controle.buscarPorId(id);

        if (lancamento == null) {
            System.out.println("Lancamento nao encontrado.");
            return;
        }

        System.out.print("Nova descricao: ");
        String descricao = scanner.nextLine();

        while (descricao.trim().isEmpty()) {
            System.out.print("Descricao nao pode ser vazia. Digite novamente: ");
            descricao = scanner.nextLine();
        }

        BigDecimal valor = lerValor();

        LocalDate data = lerData();

        TipoLancamento tipo = lerTipo();

        Categoria categoria = lerCategoria();

        boolean alterado = controle.alterarLancamento(
                id, descricao, valor, data, tipo, categoria);

        if (alterado) {
            System.out.println("Lancamento alterado com sucesso!");
        } else {
            System.out.println("Nao foi possivel alterar.");
        }
    }

    private static void excluirLancamento() {

        System.out.print("Digite o ID do lancamento: ");
        int id = lerInteiro();

        boolean excluido = controle.excluirLancamento(id);

        if (excluido) {
            System.out.println("Lancamento excluido com sucesso!");
        } else {
            System.out.println("Lancamento nao encontrado.");
        }
    }

    private static void filtrarPorTipo() {

        TipoLancamento tipo = lerTipo();

        ArrayList<Lancamento> resultado = controle.filtrarPorTipo(tipo);

        if (resultado.isEmpty()) {
            System.out.println("Nenhum lancamento encontrado.");
        } else {
            for (Lancamento lancamento : resultado) {
                System.out.println(lancamento);
            }
        }
    }

    private static void consultarPorPeriodo() {

        System.out.println("Digite a data inicial:");
        LocalDate dataInicial = lerData();

        System.out.println("Digite a data final:");
        LocalDate dataFinal = lerData();

        if (dataInicial.isAfter(dataFinal)) {
            System.out.println("A data inicial nao pode ser posterior a data final.");
            return;
        }

        ArrayList<Lancamento> resultado =
                controle.consultarPorPeriodo(dataInicial, dataFinal);

        if (resultado.isEmpty()) {
            System.out.println("Nenhum lancamento encontrado nesse periodo.");
        } else {
            for (Lancamento lancamento : resultado) {
                System.out.println(lancamento);
            }
        }
    }

    private static void exibirResumo() {

        BigDecimal receitas = controle.calcularTotalReceitas();

        BigDecimal despesas = controle.calcularTotalDespesas();

        BigDecimal saldo = controle.calcularSaldo();

        System.out.println("\n===== RESUMO FINANCEIRO =====");
        System.out.println("Total de receitas: R$ " + receitas);
        System.out.println("Total de despesas: R$ " + despesas);
        System.out.println("Saldo: R$ " + saldo);
    }

    private static int lerInteiro() {

        while (true) {

            try {
                int numero = Integer.parseInt(scanner.nextLine());
                return numero;

            } catch (NumberFormatException e) {
                System.out.print("Digite um numero inteiro valido: ");
            }
        }
    }

    private static BigDecimal lerValor() {

        while (true) {

            try {

                System.out.print("Valor: R$ ");

                String entrada = scanner.nextLine().replace(",", ".");

                BigDecimal valor = new BigDecimal(entrada);

                if (valor.compareTo(BigDecimal.ZERO) > 0) {
                    return valor;
                }

                System.out.println("O valor deve ser maior que zero.");

            } catch (NumberFormatException e) {
                System.out.println("Digite um valor valido.");
            }
        }
    }

    private static LocalDate lerData() {

        while (true) {

            try {

                System.out.print("Data (dd/MM/yyyy): ");

                String entrada = scanner.nextLine();

                return LocalDate.parse(entrada, formatoData);

            } catch (Exception e) {
                System.out.println("Data invalida. Tente novamente.");
            }
        }
    }

    private static TipoLancamento lerTipo() {

        TipoLancamento[] tipos = TipoLancamento.values();

        while (true) {

            System.out.println("Escolha o tipo:");

            for (int i = 0; i < tipos.length; i++) {
                System.out.println((i + 1) + " - " + tipos[i]);
            }

            int opcao = lerInteiro();

            if (opcao >= 1 && opcao <= tipos.length) {
                return tipos[opcao - 1];
            }

            System.out.println("Opcao invalida.");
        }
    }

    private static Categoria lerCategoria() {

        Categoria[] categorias = Categoria.values();

        while (true) {

            System.out.println("Escolha a categoria:");

            for (int i = 0; i < categorias.length; i++) {
                System.out.println((i + 1) + " - " + categorias[i]);
            }

            int opcao = lerInteiro();

            if (opcao >= 1 && opcao <= categorias.length) {
                return categorias[opcao - 1];
            }

            System.out.println("Opcao invalida.");
        }
    }
}