package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){

        try {
            var algoritmo = Algorithm.HMAC256(secret);
            //Cria um novo objeto JWT
            return JWT.create()
                    //Define o emissor do token
                    .withIssuer("API Voll.med")
                    //Define o assunto do token
                    .withSubject(usuario.getLogin())
                    //Define o tempo de expiração do token
                    .withExpiresAt(dataExpiracao())
                    //Assinatura do Token com o algoritmo HMAC 256
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
           throw new RuntimeException("erro ao gerar token jwt", exception);
        }
    }
    //Metodo para Recuperar o token do Cabeçalho
    public String getSubject(String tokenJWT){

            try {
                var algoritmo = Algorithm.HMAC256(secret);
                return JWT.require(algoritmo)
                        .withIssuer("API Voll.med")
                        .build()
                        .verify(tokenJWT)
                        .getSubject();
            } catch (JWTVerificationException exception) {
                throw new RuntimeException("Token JWT inválido ou expirado!");
            }
        }

    private Instant dataExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
