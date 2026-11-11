
import java.math.BigDecimal; // trabalhar com valores monetarios
import java.time.LocalDate; // trabalhar com datas

public class Lancamento {
    // define as informaoces de um lancamento que ira receber
    private final int id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private TipoLancamento tipo;
    private Categoria categoria;

    // Construtor da classe Lancamento que recebe as informacoes necessarias para criar um lancamento
    public Lancamento(int id, String descricao, BigDecimal valor,
                      LocalDate data, TipoLancamento tipo, Categoria categoria) {
        // this significa que o objeto esta sendo criado
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + " | Descricao: " + descricao
                + " | Valor: R$ " + valor
                + " | Data: " + data
                + " | Tipo: " + tipo
                + " | Categoria: " + categoria;
    }
}
