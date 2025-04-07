package Project.BE_BlockChain_Wallet.dto.request;

public class IntrospectRequest {

    private String token;

    // Constructor
    public IntrospectRequest(String token) {
        this.token = token;
    }

    // Getter and Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
