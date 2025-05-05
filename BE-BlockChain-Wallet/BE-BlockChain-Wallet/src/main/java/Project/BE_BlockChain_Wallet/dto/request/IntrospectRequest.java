package Project.BE_BlockChain_Wallet.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class IntrospectRequest {

    String token;

}