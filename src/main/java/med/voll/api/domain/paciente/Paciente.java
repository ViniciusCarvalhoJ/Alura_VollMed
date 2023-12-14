package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "paciente")
@Entity(name = "Paciente")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Paciente(){

    }
    public Paciente(DadosCadastroPaciente dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.cpf = dados.cpf();
        this.endereco = new Endereco();

    }


    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
        if (dados.nome( ) !=null) {
            this.nome = dados.nome();
        }
        if(dados.telefone() !=null){
            this.telefone = dados.telefone();
        }
        if(dados.endereco() != null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    public void  excluir(){
        this.ativo = false;
    }
}
